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
package io.wcm.samples.app.config.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import io.wcm.caconfig.application.spi.annotations.Application;
import io.wcm.config.api.Configuration;
import io.wcm.handler.url.spi.UrlHandlerConfig;
import io.wcm.handler.url.spi.helpers.AbstractUrlHandlerConfig;

/**
 * URL handler configuration
 */
@Model(adaptables = {
    SlingHttpServletRequest.class, Resource.class
}, adapters = UrlHandlerConfig.class)
@Application(ApplicationProviderImpl.APPLICATION_ID)
public class UrlHandlerConfigImpl extends AbstractUrlHandlerConfig {

  @SlingObject
  private ResourceResolver resourceResolver;

  @Override
  public int getSiteRootLevel(String contextPath) {
    String siteRootpath = getSiteRootPath(contextPath);
    if (siteRootpath != null) {
      return getPageLevel(siteRootpath);
    }
    return 0;
  }

  private String getSiteRootPath(String contextPath) {
    if (StringUtils.isNotEmpty(contextPath)) {
      Resource resource = resourceResolver.getResource(contextPath);
      if (resource != null) {
        Configuration configuration = resource.adaptTo(Configuration.class);
        if (configuration != null) {
          // assumption: configuration id = configuration context root path = site root path
          return configuration.getConfigurationId();
        }
      }
    }
    return null;
  }

  private int getPageLevel(String configurationId) {
    return StringUtils.split(configurationId, "/").length - 1;
  }

}
