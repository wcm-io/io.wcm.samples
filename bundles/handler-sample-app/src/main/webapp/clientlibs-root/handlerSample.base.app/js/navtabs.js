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
* navTabs - Hide and show tabs
*/
(function($) {
  $.fn.navTabs = function(options) {
    // override the default options by parameter
    var opts = $.extend({}, $.fn.navTabs.defaults, options);
    var $this = this;
    $this.each(function() {
      var $self = $(this); // dom element
      // create target element for tabs
      $target = $('<div class="tab-nav-bar box-padding"></div>');
      $self.prepend($target);
      // collect tab content
      var $contents = $self.find(opts.tabContainer);
      $btns = $self.find(opts.tabLink).find('a');
      // move tab buttons to top and bind event to them
      $btns.each(function(){
        // bind click
        $(this).on('click', function() {
          // hide curently active element
          $contents.each(function(){
            $(this).removeClass(opts.tabActiveClass);
          });
          $btns.each(function(){
            $(this).removeClass(opts.tabActiveClass);
          });
          $(this).addClass(opts.tabActiveClass);
          // attach active class to related element
          $('#'+$(this).attr('rel')).addClass(opts.tabActiveClass);
        });
        // move button to tab navigation
        $target.append($(this));
      });
      // check whether tab is preselected
      if (opts.tab !== "") {         //.substr(1)
        $target.find('a[href='+opts.tab+']').trigger('click');
      }
    });
  };
  /**
   * Default settings
   */
  $.fn.navTabs.defaults = {
    tabLink: ".tab-opener",
    tabContainer: ".tab-wrapper",
    tabActiveClass: "active"
  };
})(jQuery);
