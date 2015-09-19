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
* adjust position of links in teaserbar
*/
(function($) {
  $.fn.teaserBar = function(options) {
    // override the default options by parameter
    var opts = $.extend({}, $.fn.teaserBar.defaults, options);
    var $teaser;
    //
    var _calcPosition = function() {
      var maxHeight = 0;
      var req = $(this).innerWidth() > 600;
      $teaser.each(function(){
        $(this).find('.teaser-content').height('auto');
        if (req) {
          var thisHeight = $(this).find('.teaser-content').height();
          maxHeight = Math.max(thisHeight, maxHeight);
        }
      });
      if (req) {
        $teaser.each(function(){
          $(this).find('.teaser-content').height(maxHeight);
        });
      }
    };
    // listen for resize event
    $(window).on('resize', _calcPosition);
    //
    $(this).each(function() {
      var $self = $(this); // dom element
      $teaser = $self.find(opts.teaserContainer);
      _calcPosition();
    });
  };
  /**
   * Default settings
   */
  $.fn.teaserBar.defaults = {
    teaserContainer: ".teaser",
    teaserLink: ".link-teaser"
  };
})(jQuery);
