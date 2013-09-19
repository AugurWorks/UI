package jqplot

import grails.util.Environment

class JqplotTagLib {


  static namespace = 'jqplot'

  /**
   * includes the jqplot and generates the neccessary javascript
   */
  def resources = {  attrs ->

    //needed to use the compressed one
    def flavour = Environment.current == Environment.DEVELOPMENT ? '' : '.min'

    out << """
          <script type="text/javascript" src="${g.resource(plugin: 'jqplot', dir: 'js/jqplot', file: "jquery.jqplot${flavour}.js").encodeAsHTML()}"></script>
    """

    out << """
        <script type="text/javascript" src="${g.resource(plugin: 'jqplot', dir: 'js/jqplot', file: "excanvas${flavour}.js").encodeAsHTML()}"></script>
    """

    //stylesheets

    out << """
        <link rel="stylesheet" href="${g.resource(plugin: 'jqplot', dir: 'css/jqplot', file: "jquery.jqplot${flavour}.css").encodeAsHTML()}"/>
    """
  }

  /**
   * includes a plugin with the given name, the name has to be without prefix or postfix.
   *
   * For exmaple if the plugin file name is:
   *
   * jqplot.barRenderer.js
   *
   * the name of the plugin would be
   *
   * barRenderer
   *
   *
   *
   */
  def plugin = { attrs ->

    if (attrs.name == null) {
      throw new RuntimeException("you need to provide a name attribute")
    }
    else {
      def flavour = Environment.current == Environment.DEVELOPMENT ? '' : '.min'

      out << """
        <script type="text/javascript" src='${g.resource(plugin: "jqplot", dir: "js/jqplot/plugins", file: "jqplot.${attrs.name}${flavour}.js").encodeAsHTML()}'></script>
          """
    }
  }

}
