package com.timberglund.gradle.plugin.presentation

import org.gradle.api.NamedDomainObjectContainer

class ModuleExtension {
  final NamedDomainObjectContainer<Module> modules

  ModuleExtension(NamedDomainObjectContainer<Module> modules) {
    this.modules = modules
  }

  def modules(Closure closure) {
    modules.configure(closure)
  }
}
