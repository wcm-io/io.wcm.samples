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

import io.wcm.config.spi.ConfigurationFinderStrategy;
import io.wcm.config.spi.helpers.AbstractRootTemplateConfigurationFinderStrategy;
import io.wcm.samples.app.util.AppTemplate;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

/**
 * Application provider
 */
@Component(immediate = true)
@Service(ConfigurationFinderStrategy.class)
public class ConfigurationFinderStrategyImpl extends AbstractRootTemplateConfigurationFinderStrategy {

  private static final int MIN_LEVEL = 1;
  private static final int MAX_LEVEL = 5;

  /**
   * Detect via fixed site root level
   */
  public ConfigurationFinderStrategyImpl() {
    super(ApplicationProviderImpl.APPLICATION_ID, MIN_LEVEL, MAX_LEVEL,
        AppTemplate.EDITORIAL_HOMEPAGE.getTemplatePath());
  }

}
