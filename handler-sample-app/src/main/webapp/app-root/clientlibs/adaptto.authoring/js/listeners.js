/**
 * Authoring GUI listener methods (for Touch UI)
 */
(function ($, window, undefined) {

  window.adaptto = window.adaptto || {};
  window.adaptto.authoring = window.adaptto.authoring || {};
  window.adaptto.authoring.listener = window.adaptto.authoring.listener || {};

  /**
   * Handles update component list event, which is issued whenever a new bar/area is rendered.
   * Fetches the allowed components via AJAX call based on allowed components in page component definition.
   */
  window.adaptto.authoring.listener.refreshAjaxContentAfterEdit = function() {

    // call frontend method to apply its progressive enhancement
    var contentFrame = window.frames["ContentFrame"];
    if (contentFrame && contentFrame.contentWindow.adaptto && contentFrame.contentWindow.adaptto.util
        && contentFrame.contentWindow.adaptto.util.refreshAjaxContent) {
      if (this && this.dom) {
        contentFrame.contentWindow.adaptto.util.refreshAjaxContent(this.dom);
      }
    }

  };

}(jQuery, this));
