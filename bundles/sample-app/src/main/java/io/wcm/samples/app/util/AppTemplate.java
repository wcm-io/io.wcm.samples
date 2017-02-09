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
package io.wcm.samples.app.util;

import io.wcm.wcm.commons.util.Template;
import io.wcm.wcm.commons.util.TemplatePathInfo;

/**
 * List of templates with special handling in code.
 */
public enum AppTemplate implements TemplatePathInfo {

  /**
   * Framework: structure element
   */
  FRAMEWORK_STRUCTURE_ELEMENT("/apps/wcm-io-samples/sample-app/templates/framework/structureElement"),

  /**
   * Framework: redirect
   */
  FRAMEWORK_REDIRECT("/apps/wcm-io-samples/sample-app/templates/framework/redirect"),

  /**
   * Editorial: content
   */
  EDITORIAL_CONTENT("/apps/wcm-io-samples/sample-app/templates/content/content"),

  /**
   * Editorial: tickets
   */
  EDITORIAL_TICKETS("/apps/wcm-io-samples/sample-app/templates/content/tickets"),

  /**
   * Editorial: venue
   */
  EDITORIAL_VENUE("/apps/wcm-io-samples/sample-app/templates/content/venue"),

  /**
   * Editorial: homepage
   */
  EDITORIAL_HOMEPAGE("/apps/wcm-io-samples/sample-app/templates/content/homepage");

  private final String templatePath;
  private final String resourceType;

  AppTemplate(String templatePath) {
    this.templatePath = templatePath;
    this.resourceType = Template.getResourceTypeFromTemplatePath(templatePath);
  }

  AppTemplate(String templatePath, String resourceType) {
    this.templatePath = templatePath;
    this.resourceType = resourceType;
  }

  /**
   * Template path
   * @return Path
   */
  @Override
  public String getTemplatePath() {
    return templatePath;
  }

  /**
   * Resource type
   * @return Path
   */
  @Override
  public String getResourceType() {
    return resourceType;
  }

}
