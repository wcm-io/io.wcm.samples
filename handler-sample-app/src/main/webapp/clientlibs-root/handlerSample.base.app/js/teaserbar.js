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
