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
package io.wcm.samples.core.config.impl

import com.day.cq.wcm.api.Page
import com.google.common.collect.ImmutableList
import io.wcm.handler.media.markup.DummyImageMediaMarkupBuilder
import io.wcm.handler.media.spi.MediaHandlerConfig
import io.wcm.handler.media.spi.MediaMarkupBuilder
import io.wcm.handler.media.spi.MediaSource
import io.wcm.handler.mediasource.dam.DamMediaSource
import io.wcm.handler.mediasource.dam.markup.DamVideoMediaMarkupBuilder
import io.wcm.handler.mediasource.inline.InlineMediaSource
import io.wcm.samples.core.handler.ResponsiveImageMediaMarkupBuilder
import org.osgi.service.component.annotations.Component

/**
 * Media handler configuration
 */
@Component(service = [MediaHandlerConfig::class])
class MediaHandlerConfigImpl : MediaHandlerConfig() {

  override fun getSources(): List<Class<out MediaSource>> {
    return MEDIA_SOURCES
  }

  override fun getMarkupBuilders(): List<Class<out MediaMarkupBuilder>> {
    return MEDIA_MARKUP_BUILDERS
  }

  override fun getDamRootPath(page: Page): String {
    return DAM_ROOT
  }

  companion object {
    const val DAM_ROOT = "/content/dam/wcm-io-samples"

    private val MEDIA_SOURCES: List<Class<out MediaSource>> = ImmutableList.of(
        DamMediaSource::class.java,
        InlineMediaSource::class.java
    )

    private val MEDIA_MARKUP_BUILDERS: List<Class<out MediaMarkupBuilder>> = ImmutableList.of(
        ResponsiveImageMediaMarkupBuilder::class.java,
        DamVideoMediaMarkupBuilder::class.java,
        DummyImageMediaMarkupBuilder::class.java
    )
  }
}