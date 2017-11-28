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
/**
 * Authoring GUI listener methods (for Touch UI)
 */
(function ($, window, undefined) {

  window.sampleApp = window.sampleApp || {};
  window.sampleApp.authoring = window.sampleApp.authoring || {};
  window.sampleApp.authoring.listener = window.sampleApp.authoring.listener || {};

  /**
   * Handles update component list event, which is issued whenever a new bar/area is rendered.
   * Fetches the allowed components via AJAX call based on allowed components in page component definition.
   */
  window.sampleApp.authoring.listener.refreshAjaxContentAfterEdit = function() {

    // call frontend method to apply its progressive enhancement
    var contentFrame = window.frames["ContentFrame"];
    if (contentFrame && contentFrame.contentWindow.sampleApp && contentFrame.contentWindow.sampleApp.util
        && contentFrame.contentWindow.sampleApp.util.refreshAjaxContent) {
      if (this && this.dom) {
        contentFrame.contentWindow.sampleApp.util.refreshAjaxContent(this.dom);
      }
    }

  };

}(jQuery, this));
