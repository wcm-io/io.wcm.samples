/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
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
package io.wcm.samples.handler.util;

import io.wcm.samples.handler.config.UrlHandlerConfigImpl;
import io.wcm.sling.models.annotations.AemObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMMode;
import com.day.text.Text;

/**
 * Helper methods for templates/components
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class SiteHelper {

  private Page siteRootPage;

  @AemObject
  private Page currentPage;
  @AemObject
  private PageManager pageManager;
  @AemObject
  private WCMMode wcmMode;

  /**
   * Gets site root level path of a site.
   * @param page CQ Page of site
   * @return Site root path for the site. The path is not checked for validness.
   */
  public static String getSiteRootPath(Page page) {
    if (page == null) {
      return null;
    }
    return getSiteRootPath(page.getPath());
  }

  /**
   * Gets site root level path of a site.
   * @param path Path of page within the site
   * @return Site root path for the site. The path is not checked for validness.
   */
  public static String getSiteRootPath(String path) {
    return Text.getAbsoluteParent(path, UrlHandlerConfigImpl.SITE_ROOT_LEVEL);
  }

  /**
   * Gets site root level path of the current site.
   * @return Site root path for the current site. The path is not checked for validness.
   */
  public String getSiteRootPath() {
    return SiteHelper.getSiteRootPath(currentPage);
  }

  /**
   * Gets site root page of the current site.
   * @return Site root page for the current site.
   */
  public Page getSiteRootPage() {
    if (siteRootPage == null) {
      siteRootPage = pageManager.getPage(getSiteRootPath());
    }
    return siteRootPage;
  }

  /**
   * Gets the site root page of the given path
   * @param path
   * @return Site root page
   */
  public Page getSiteRootPage(String path) {
    return pageManager.getPage(getSiteRootPath(path));
  }

  /**
   * Get page relative to site root.
   * @param relativePath Path relative to site root
   * @return Page instance or null if not found
   */
  public Page getRelativePage(String relativePath) {
    String path = getSiteRootPath();
    if (!relativePath.startsWith("/")) {
      path += "/";
    }
    path += relativePath;
    return pageManager.getPage(path);
  }

  /**
   * Returns a special selector string in edit or preview mode to create different variations in browser cache, but allow caching at all.
   * This selector should be used for CSS and JS references within a page which are generated dynamically.
   * @return Empty string or selector string prefixed with "."
   */
  public String getEditPreviewIntegratorCacheSelector() {
    StringBuilder selectorString = new StringBuilder();
    if (wcmMode != WCMMode.DISABLED) {
      selectorString.append(".").append(wcmMode.name().toLowerCase());
    }
    return selectorString.toString();
  }

  /**
   * @param page Page
   * @return true if given page is the site root page
   */
  public boolean isSiteRootPage(Page page) {
    return StringUtils.equals(page.getPath(), getSiteRootPath());
  }

}
