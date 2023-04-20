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

import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.Page;

import io.wcm.handler.media.markup.DummyImageMediaMarkupBuilder;
import io.wcm.handler.media.spi.MediaHandlerConfig;
import io.wcm.handler.media.spi.MediaMarkupBuilder;
import io.wcm.handler.media.spi.MediaSource;
import io.wcm.handler.mediasource.dam.DamMediaSource;
import io.wcm.handler.mediasource.dam.markup.DamVideoMediaMarkupBuilder;
import io.wcm.handler.mediasource.inline.InlineMediaSource;
import io.wcm.samples.core.handler.ResponsiveImageMediaMarkupBuilder;

/**
 * Media handler configuration
 */
@Component(service = MediaHandlerConfig.class)
public class MediaHandlerConfigImpl extends MediaHandlerConfig {

  static final String DAM_ROOT = "/content/dam/wcm-io-samples";

  private static final List<Class<? extends MediaSource>> MEDIA_SOURCES = List.of(
      DamMediaSource.class,
      InlineMediaSource.class);

  private static final List<Class<? extends MediaMarkupBuilder>> MEDIA_MARKUP_BUILDERS = List.of(
      ResponsiveImageMediaMarkupBuilder.class,
      DamVideoMediaMarkupBuilder.class,
      DummyImageMediaMarkupBuilder.class);

  @Override
  public @NotNull List<Class<? extends MediaSource>> getSources() {
    return MEDIA_SOURCES;
  }

  @Override
  public List<Class<? extends MediaMarkupBuilder>> getMarkupBuilders() {
    return MEDIA_MARKUP_BUILDERS;
  }

  @Override
  public @NotNull String getDamRootPath(@NotNull Page page) {
    return DAM_ROOT;
  }

}
