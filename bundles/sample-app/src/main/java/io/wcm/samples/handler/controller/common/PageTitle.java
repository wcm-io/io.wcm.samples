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
package io.wcm.samples.handler.controller.common;

import io.wcm.samples.handler.util.AppTemplate;
import io.wcm.samples.handler.util.SiteHelper;
import io.wcm.sling.models.annotations.AemObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.wcm.api.Page;

/**
 * Controller for generation page titles.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class PageTitle {

  @AemObject
  private Page currentPage;
  @Self
  private SiteHelper siteHelper;

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
    if (siteHelper.isSiteRootPage(page)) {
      return StringUtils.defaultString(page.getPageTitle(), page.getTitle());
    }
    else if (AppTemplate.isTemplate(page, AppTemplate.FRAMEWORK_STRUCTURE_ELEMENT)) {
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
    return siteHelper.getSiteRootPage().getPageTitle();
  }

}
