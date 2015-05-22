/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 - 2015 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
;/**
 * init.js
 * @author bbaumann
 */

// enable jQuery noConflict mode
jQuery.noConflict();

// enable underscore noConflict mode
//var handlerSample_underscore = _.noConflict();

// set up namespace
var handlerSample = handlerSample || {};
// set configuration
handlerSample.config = handlerSample.config || {};
// whether site runs in debug mode
handlerSample.config.debug = (location.search.match(/frontend-debug/i) !== null || location.hostname.match(/local/i) !== null)? true : false;
// start application
jQuery(document).ready(function ($) {
  if (jQuery.debug) {
    // use Logging proxy
    jQuery.debug(handlerSample.config.debug);
  } else {
    // Else create dummy log function to prevent errors
    jQuery.extend({ log: function () {} });
    jQuery.fn.extend({ log: function () {} });
  }
  $('#teaserbar').teaserBar();
  $('#nav-main').navMenu();
  $('.tab-navigation').navTabs({'tab':window.location.hash});
});
