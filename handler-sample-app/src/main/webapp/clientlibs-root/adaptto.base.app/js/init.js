;/**
 * init.js
 * @author bbaumann
 */

// enable jQuery noConflict mode
jQuery.noConflict();

// enable underscore noConflict mode
//var adaptto_underscore = _.noConflict();

// set up namespace
var adaptto = adaptto || {};
// set configuration
adaptto.config = adaptto.config || {};
// whether site runs in debug mode
adaptto.config.debug = (location.search.match(/frontend-debug/i) !== null || location.hostname.match(/local/i) !== null)? true : false;
// start application
jQuery(document).ready(function ($) {
  if (jQuery.debug) {
    // use Logging proxy
    jQuery.debug(adaptto.config.debug);
  } else {
    // Else create dummy log function to prevent errors
    jQuery.extend({ log: function () {} });
    jQuery.fn.extend({ log: function () {} });
  }
  $('#teaserbar').teaserBar();
  $('#nav-main').navMenu();
  $('.tab-navigation').navTabs({'tab':window.location.hash});
});
