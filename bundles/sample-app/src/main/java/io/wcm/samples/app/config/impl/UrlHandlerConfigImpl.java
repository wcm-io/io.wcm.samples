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

import static io.wcm.samples.app.config.impl.ApplicationProviderImpl.PATH_PATTERN;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.resource.ConfigurationResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import io.wcm.handler.url.spi.UrlHandlerConfig;

/**
 * URL handler configuration
 */
@Component(service = UrlHandlerConfig.class)
public class UrlHandlerConfigImpl extends UrlHandlerConfig {

  @Reference
  private ConfigurationResourceResolver configResolver;

  @Override
  public int getSiteRootLevel(String contextPath, ResourceResolver resolver) {
    String siteRootpath = getSiteRootPath(contextPath, resolver);
    if (siteRootpath != null) {
      return getPageLevel(siteRootpath);
    }
    return 0;
  }

  private String getSiteRootPath(String contextPath, ResourceResolver resolver) {
    if (StringUtils.isNotEmpty(contextPath)) {
      Resource resource = resolver.getResource(contextPath);
      if (resource != null) {
        // assumption: inner-most context-aware configuration path is site root path
        return configResolver.getContextPath(resource);
      }
    }
    return null;
  }

  private int getPageLevel(String configurationId) {
    return StringUtils.split(configurationId, "/").length - 1;
  }

  @Override
  public boolean matches(Resource resource) {
    return resource != null && PATH_PATTERN.matcher(resource.getPath()).matches();
  }

}
