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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;

/**
 * List of templates with special handling in code.
 */
public enum AppTemplate {

  /**
   * Framework: structure element
   */
  FRAMEWORK_STRUCTURE_ELEMENT("/apps/wcm-io-handler-sample-app/templates/framework/structureElement"),

  /**
   * Framework: redirect
   */
  FRAMEWORK_REDIRECT("/apps/wcm-io-handler-sample-app/templates/framework/redirect"),

  /**
   * Editorial: content
   */
  EDITORIAL_CONTENT("/apps/wcm-io-handler-sample-app/templates/content/content"),

  /**
   * Editorial: tickets
   */
  EDITORIAL_TICKETS("/apps/wcm-io-handler-sample-app/templates/content/tickets"),

  /**
   * Editorial: venue
   */
  EDITORIAL_VENUE("/apps/wcm-io-handler-sample-app/templates/content/venue"),

  /**
   * Editorial: homepage
   */
  EDITORIAL_HOMEPAGE("/apps/wcm-io-handler-sample-app/templates/content/homepage");

  private final String mTemplatePath;
  private final String mResourceType;

  private AppTemplate(String templatePath) {
    mTemplatePath = templatePath;

    // build resource type from template path
    String resourceType = null;
    final Pattern TEMPLATE_PATH_PATTERN = Pattern.compile("^/apps/([^/]+)/templates(/.*)?/([^/]+)$");
    Matcher templateParts = TEMPLATE_PATH_PATTERN.matcher(templatePath);
    if (templateParts.matches()) {
      resourceType = "/apps/" + templateParts.group(1) + "/components" + StringUtils.defaultString(templateParts.group(2))
          + "/page/" + templateParts.group(3);
    }

    mResourceType = resourceType;
  }

  private AppTemplate(String templatePath, String resourceType) {
    mTemplatePath = templatePath;
    mResourceType = resourceType;
  }

  /**
   * Template path
   * @return Path
   */
  public String getTemplatePath() {
    return mTemplatePath;
  }

  /**
   * Resource type
   * @return Path
   */
  public String getResourceType() {
    return mResourceType;
  }

  /**
   * Checks if the given page uses a specific template.
   * @param page CQ page
   * @param templates Template(s)
   * @return true if the page uses the template
   */
  public static boolean isTemplate(Page page, AppTemplate... templates) {
    if (page == null || templates == null || templates.length == 0) {
      return false;
    }
    String templatePath = page.getProperties().get(NameConstants.PN_TEMPLATE, String.class);
    for (AppTemplate template : templates) {
      if (template.getTemplatePath().equals(templatePath)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Lookup a template by the given template path.
   * @param templatePath Path of template
   * @return The {@link AppTemplate} instance or null for unknown template paths
   */
  public static AppTemplate forTemplatePath(String templatePath) {
    for (AppTemplate template : AppTemplate.values()) {
      if (StringUtils.equals(template.getTemplatePath(), templatePath)) {
        return template;
      }
    }
    return null;
  }

  /**
   * Lookup template for given page.
   * @param page Page
   * @return The {@link AppTemplate} instance or null for unknown template paths
   */
  public static AppTemplate forPage(Page page) {
    String templatePath = page.getProperties().get(NameConstants.PN_TEMPLATE, String.class);
    return forTemplatePath(templatePath);
  }

}
