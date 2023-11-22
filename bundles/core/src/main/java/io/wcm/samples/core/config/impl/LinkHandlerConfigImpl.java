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
package io.wcm.samples.core.config.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.Page;

import io.wcm.handler.link.spi.LinkHandlerConfig;
import io.wcm.handler.link.spi.LinkType;
import io.wcm.handler.link.type.ExternalLinkType;
import io.wcm.handler.link.type.InternalCrossContextLinkType;
import io.wcm.handler.link.type.InternalLinkType;
import io.wcm.handler.link.type.MediaLinkType;
import io.wcm.samples.core.config.AppTemplate;
import io.wcm.samples.core.config.WikipediaLinkType;
import io.wcm.wcm.commons.util.Template;

/**
 * Link handler configuration.
 */
@Component(service = LinkHandlerConfig.class)
public class LinkHandlerConfigImpl extends LinkHandlerConfig {

  private static final List<Class<? extends LinkType>> LINK_TYPES = List.of(
      InternalLinkType.class,
      InternalCrossContextLinkType.class,
      ExternalLinkType.class,
      MediaLinkType.class,
      WikipediaLinkType.class);

  /**
   * @return Supported link types
   */
  @Override
  public @NotNull List<Class<? extends LinkType>> getLinkTypes() {
    return LINK_TYPES;
  }

  @Override
  public boolean isValidLinkTarget(@NotNull Page page) {
    return !Template.is(page, AppTemplate.ADMIN_STRUCTURE_ELEMENT);
  }

  @Override
  public boolean isRedirect(@NotNull Page page) {
    return Template.is(page, AppTemplate.ADMIN_REDIRECT);
  }

  @Override
  public @Nullable String getLinkRootPath(@NotNull Page page, @NotNull String linkTypeId) {
    if (StringUtils.equals(linkTypeId, MediaLinkType.ID)) {
      return MediaHandlerConfigImpl.DAM_ROOT;
    }
    return super.getLinkRootPath(page, linkTypeId);
  }

}
