package com.timberglund.gradle.plugin.presentation

import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask
import org.ajoberstar.grgit.Grgit

class FrameworkTask extends DefaultTask
{
  def repo
  def uri
  def frameworkDir
  def frameworkRoot = 'src/framework'

  @TaskAction
  def frameworkAction() {
    def frameworkPath = "${frameworkRoot}/${frameworkDir}"
    if(project.file(frameworkPath).exists()) {
      repo = Grgit.open(project.file(frameworkPath))
    }
    else {
      repo = Grgit.clone(dir: frameworkPath, uri: uri)
    }
  }
}
