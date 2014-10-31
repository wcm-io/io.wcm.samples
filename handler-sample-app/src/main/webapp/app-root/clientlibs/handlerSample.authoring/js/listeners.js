/**
 * Authoring GUI listener methods (for Touch UI)
 */
(function ($, window, undefined) {

  window.handlerSample = window.handlerSample || {};
  window.handlerSample.authoring = window.handlerSample.authoring || {};
  window.handlerSample.authoring.listener = window.handlerSample.authoring.listener || {};

  /**
   * Handles update component list event, which is issued whenever a new bar/area is rendered.
   * Fetches the allowed components via AJAX call based on allowed components in page component definition.
   */
  window.handlerSample.authoring.listener.refreshAjaxContentAfterEdit = function() {

    // call frontend method to apply its progressive enhancement
    var contentFrame = window.frames["ContentFrame"];
    if (contentFrame && contentFrame.contentWindow.handlerSample && contentFrame.contentWindow.handlerSample.util
        && contentFrame.contentWindow.handlerSample.util.refreshAjaxContent) {
      if (this && this.dom) {
        contentFrame.contentWindow.handlerSample.util.refreshAjaxContent(this.dom);
      }
    }

  };

}(jQuery, this));
