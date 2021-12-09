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
package io.wcm.samples.core.controller.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.wcm.api.Page;

import io.wcm.handler.url.ui.SiteRoot;
import io.wcm.samples.core.config.AppTemplate;
import io.wcm.sling.models.annotations.AemObject;
import io.wcm.wcm.commons.util.Template;

/**
 * Controller for generation page titles.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class PageTitle {

  @AemObject
  private Page currentPage;
  @Self
  private SiteRoot siteRoot;

  /**
   * @return Recursive page title for HTML title
   */
  public String getRecursivePageTitle() {
    return getRecursivePageTitle(currentPage);
  }

  /**
   * Build html title from page titles up to site root page.
   * @param page Page
   * @return Html title
   */
  private String getRecursivePageTitle(Page page) {
    if (page == null) {
      return "";
    }
    if (siteRoot.isRootPage(page)) {
      return StringUtils.defaultString(page.getPageTitle(), page.getTitle());
    }
    else if (Template.is(page, AppTemplate.ADMIN_STRUCTURE_ELEMENT)) {
      return getRecursivePageTitle(page.getParent());
    }
    else {
      return StringUtils.defaultString(page.getPageTitle(), page.getTitle()) + " - " + getRecursivePageTitle(page.getParent());
    }
  }

  /**
   * @return Site root page title
   */
  public String getSiteRootPageTitle() {
    Page rootPage = siteRoot.getRootPage();
    if (rootPage != null) {
      return rootPage.getPageTitle();
    }
    else {
      return null;
    }
  }

}
