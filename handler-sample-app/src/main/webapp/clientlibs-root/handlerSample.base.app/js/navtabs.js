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
