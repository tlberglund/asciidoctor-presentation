package com.timberglund.gradle.plugin.presentation

import org.gradle.api.Project
import org.gradle.api.Plugin


class PresentationPlugin implements Plugin<Project> {
  void apply(Project project) {
    project.plugins.apply('org.asciidoctor.gradle.asciidoctor')
    project.plugins.apply('lesscss')
    project.tasks.create('slides', PresentationTask)
    project.tasks.create('presentation').configure {
      dependsOn << [ 'slides', 'lessc' ]
    }
    project.tasks.create('deckjs', FrameworkTask).configure {
      frameworkDir = 'deck.js'
      uri = 'https://github.com/imakewebthings/deck.js.git'
    }
    project.tasks.create('backend', FrameworkTask).configure {
      frameworkDir = 'asciidoctor-deck.js'
      uri = 'https://github.com/asciidoctor/asciidoctor-deck.js.git'
    }
    project.tasks.create('framework').configure {
      dependsOn << [ 'deckjs', 'backend' ]
    }

    project.tasks.getByName('lessc').configure {
      sourceDir 'src/less'
      include "**/*.less"
      destinationDir = "${project.buildDir}/asciidoc/deckjs/deck.js/themes/style"
      mustRunAfter project.tasks.getByName('slides')
      doLast {
        project.copy {
          from('src/less') {
            include '*.css'
          }
          into destinationDir
        }
      }
    }

    def modules = project.container(Module) { name ->
      new Module(name)
    }
    project.configure(project) {
      extensions.create("presentation", ModuleExtension, modules)
    }
  }
}
