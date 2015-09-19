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
package io.wcm.samples.handler.controller.navigation;

import io.wcm.handler.link.Link;
import io.wcm.handler.link.LinkHandler;
import io.wcm.samples.handler.util.SiteHelper;

import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.wcm.api.Page;

/**
 * Generic model for building a link to page addressed via a path relative to site root.
 * If not path is given, the site root is referenced.
 * The link title is set to navigation title / page title / title text (whatever is not empty).
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class SiteRootRelativePageLink {

  private Link link;
  private String title;

  /**
   * @param siteHelper
   * @param linkHandler
   * @param relativePath Relative path of page to link to
   * @param titleType Type of title to display: pageTitle or navigationTitle
   */
  @Inject
  public SiteRootRelativePageLink(
      @Self SiteHelper siteHelper,
      @Self LinkHandler linkHandler,
      @RequestAttribute(name = "relativePath", optional = true) String relativePath,
      @RequestAttribute(name = "titleType", optional = true) @Default(values = "navigationTitle") String titleType
      ) {
    Page page;
    if (StringUtils.isNotEmpty(relativePath)) {
      page = siteHelper.getRelativePage(relativePath);
    }
    else {
      page = siteHelper.getSiteRootPage();
    }
    if (page != null) {
      link = linkHandler.get(page).build();
      switch (titleType) {
        case "pageTitle":
          title = StringUtils.defaultString(page.getPageTitle(), page.getTitle());
          break;
        case "navigationTitle":
        default:
          title = StringUtils.defaultString(page.getNavigationTitle(), page.getTitle());
          break;
      }
    }
  }

  /**
   * @return Link
   */
  public Link getMetadata() {
    return link;
  }

  /**
   * @return Link is valid
   */
  public boolean isValid() {
    return link.isValid();
  }

  /**
   * @return Anchor attributes
   */
  public Map<String, String> getAttributes() {
    return link.getAnchorAttributes();
  }

  /**
   * @return Link title
   */
  public String getTitle() {
    return title;
  }

}
