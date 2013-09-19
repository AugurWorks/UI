class JqplotGrailsPlugin {
  // the plugin version
  def version = "0.1"
  // the version or versions of Grails the plugin is designed for
  def grailsVersion = "1.3.5 > *"
  // the other plugins this plugin depends on
  def dependsOn = [
          jquery: '1.4.2.4 > *'
  ]
  // resources that are excluded from plugin packaging
  def pluginExcludes = [
          "grails-app/views/error.gsp"
  ]

  // TODO Fill in these fields
  def author = "Gert Wohlgemuth"
  def authorEmail = "berlinguyinca@gmail.com"
  def title = "JQPlot plugin"
  def description = '''\\
This plugin provides an easy way to integrate the jqplot library into your project
'''

  // URL to the plugin's documentation
  def documentation = "http://grails.org/plugin/jqplot"

  def doWithWebDescriptor = { xml ->
  }

  def doWithSpring = {
  }

  def doWithDynamicMethods = { ctx ->
  }

  def doWithApplicationContext = { applicationContext ->
  }

  def onChange = { event ->
  }

  def onConfigChange = { event ->
  }
}
