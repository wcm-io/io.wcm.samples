;/**
 * (Id: debug.js 676 2014-06-19 15:47:33Z sseifert )
 */
/**
 * Logs messages to the firebug console or creates an HTML div as alternative console.
 * The plugin is based on the "jQuery plugin for debugging".
 *
 * @copyright       (c) 2007 Ralf S. Engelschall <rse@engelschall.com>
 * @license      GPL <http://www.gnu.org/licenses/gpl.txt>
 * @see        http://trainofthoughts.org/blog/2007/03/16/jquery-plugin-debug/
 */

/*global
  jQuery
*/

/*
 * Wrapper function to use "$" instead of "jQuery"
 * For Aptana/Eclipse: Remove this function to get a Code Assitent and
 * see the outline or generate a documentation
 */
(function($) {
  /*
   * jQuery class extension methods
   */
    $.extend({
        /**
         * boolean status whether debugging is enabled
         */
        _debug$: null,

    /**
     * boolean instance of the console created or not
     */
    _instance$: false,

    /**
     * method for getting and setting debug status
     * @param boolean onoff
     */
        debug: function (onoff) {
            var old_value = ($._debug$ === true ? true : false);
            $._debug$ = (onoff ? true : false);
            return old_value;
        },

    /**
     * method for logging an object or message
     * @param string message
     */
        log: function () {
            if ($._debug$ === true) {
        //create alternative html console
        if($._instance$ === false) {
             $.createConsole();
          $._instance$ = true;
        }
        if(navigator.userAgent.toLowerCase().indexOf("msie") !== -1) {
          var str = "";
          for(var i=0; i<arguments.length; i++) {
            str += arguments[i] + " ";
          }
          console.log(str);
        } else {
          console.log.apply(console, arguments);
        }
      }
        },

    /**
     * creates the alternative html console and javascript interface
     */
    createConsole: function() {
      if (typeof window.console === "undefined") {
      /* minimum conversion of arbitrary object to text representation */
      function object2text (obj) {
        var text = null;
        if (typeof obj === "undefined") {
          text = "[undefined]";

        } else if (typeof obj === "boolean") {
          text = (obj ? "true" : "false");

        } else if (typeof obj === "number") {
          text = "" + obj;

        } else if (typeof obj === "string") {
          text = obj;

        } else if (typeof obj === "function") {
          text = obj;

        } else if (typeof obj === "object") {
          if (typeof obj.nodeType !== "undefined") {
            /* W3C DOM element node */
            if (obj.nodeType == 1) {
              text = '&lt;';
              text += obj.nodeName.toLowerCase();
              for (var i = 0; i < obj.attributes.length; i++) {
                text += ' ' + obj.attributes[i].nodeName.toLowerCase() +
                '="' +
                obj.attributes[i].nodeValue +
                '"';
              }
              text += '&gt;';

            /* W3C DOM attribute node */
                        } else if (obj.nodeType == 2) {
              text = obj.nodeName + '="' + obj.nodeValue;

            /* W3C DOM text node */
            } else if (obj.nodeType == 3) {
                text = obj.nodeValue;
            }

                    } else if (typeof obj.toJSONString !== "undefined") {
            text = obj.toJSONString();

          } else if (typeof obj.toString !== "undefined"){
            text = obj.toString();
          }
                }
                if (text === null) {
          text = "[unknown]";
        }
                return text;
            }

            /* create the logging <div> node */
            $("body").append('<div id="jQueryDebug"><h1>jQuery debug console</h1><ol class="debugList"></ol></div>');

            /* attach a function to each of the Firebug methods */
            var names = [
                "log", "debug", "info", "warn", "error", "assert",
                "dir", "dirxml", "group", "groupEnd", "time", "timeEnd",
                "count", "trace", "profile", "profileEnd"
            ];
      window.console = {};
      for (var i = 0; i < names.length; i++) {
        window.console[names[i]] = function() {
          $('#jQueryDebug')
            .css("display", $._debug$ === true ? "block" : "none");

          if(arguments.length == 1) {
            $('#jQueryDebug ol.debugList')
              .append('<li>' + object2text(arguments[0]) + '</li>');
          } else {
            var element = '<li><ul>';
            for(var k = 0; k < arguments.length; k++) {
              element += '<li>' + object2text(arguments[k]) + '</li>';
            }
            element += '</ul></li>';
            $('#jQueryDebug ol.debugList').append(element);
          }

        };
      }

      /* indicate that we are the one who is proving the Firebug interface */
      window.console.jQueryDebug = true;
    }

    }
  });

  /*
   * jQuery object extension methods
   */
  $.fn.extend({
    /**
     * method for logging all jQuery items
     * @param string message
     */
    log: function () {
      if ($._debug$ === true) {
        //create alternative html console
        if($._instance$ === false) {
             $.createConsole();
          $._instance$ = true;
        }

        //cache arguments in extra array
        var args = [];
        for(var i = 0; i < arguments.length; i++) {
          args.push(arguments[i]);
        }

        return this.each(function () {
          //if arguments exits, use them and add element
          if (args.length > 0) {
            //make an independent copy
            var args_temp = args.slice();
            args_temp.push([this]);
            $.log.apply($, args_temp);

          //else only use element
          } else {
            $.log(this);
          }
        });
      }
        }
    });

})(jQuery);

