package com.timberglund.gradle.plugin.presentation

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction

class PresentationTask extends AsciidoctorTask {
  File frameworkDir = new File('src/framework')
  File templateDir  = new File(frameworkDir, 'asciidoctor-deck.js/templates')
  File deckjsDir    = new File(frameworkDir, 'deck.js')
  File buildDeckjsDir = new File(project.buildDir, 'asciidoc/deckjs/deck.js/extensions')
  def title
  def author
  def header

  @TaskAction
  def presentationAction() {
    project.file('src/slides/slides.adoc').withWriter { writer ->
      writer.println "= ${title}"
      writer.println ":author: ${author}"
      header.each { k,v ->
        writer.println ":${k}: ${v}"
      }
      writer.println ""
      project.presentation.modules.each { module ->
        writer.println "include::${module.file}[]"
      }
    }
  }

  PresentationTask() {
    dependsOn << 'framework'
    sourceDir 'src/slides'
    sources {
      include 'slides.adoc'
    }
    backends 'deckjs'
    options template_dirs : [new File(templateDir, 'haml').absolutePath ]
    options eruby: 'erubis'

    attributes 'source-highlighter': 'coderay'
    attributes idprefix: ''
    attributes idseparator: '-'

    resources {
       from(project.file('src')) {
         include 'images/**'
       }
       from(frameworkDir) {
         include 'deck.js/**'
       }
    }
  }
}
