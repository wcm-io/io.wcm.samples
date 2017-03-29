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

import java.util.List;
import java.util.Set;

import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import io.wcm.handler.media.format.MediaFormat;
import io.wcm.handler.media.markup.DummyImageMediaMarkupBuilder;
import io.wcm.handler.media.spi.MediaHandlerConfig;
import io.wcm.handler.media.spi.MediaMarkupBuilder;
import io.wcm.samples.app.config.MediaFormats;
import io.wcm.samples.app.handler.ResponsiveImageMediaMarkupBuilder;

/**
 * Media handler configuration
 */
@Component(service = MediaHandlerConfig.class)
public class MediaHandlerConfigImpl extends MediaHandlerConfig {

  private static final Set<MediaFormat> DOWNLOAD_MEDIA_FORMATS = ImmutableSet.of(
      MediaFormats.DOWNLOAD
      );
  private static final List<Class<? extends MediaMarkupBuilder>> MEDIA_MARKUP_BUILDERS = ImmutableList.<Class<? extends MediaMarkupBuilder>>of(
      ResponsiveImageMediaMarkupBuilder.class,
      DummyImageMediaMarkupBuilder.class
      );

  @Override
  public List<Class<? extends MediaMarkupBuilder>> getMarkupBuilders() {
    return MEDIA_MARKUP_BUILDERS;
  }

  @Override
  public Set<MediaFormat> getDownloadMediaFormats() {
    return DOWNLOAD_MEDIA_FORMATS;
  }

  @Override
  public boolean matches(Resource resource) {
    return PathMatcher.matches(resource);
  }

}
