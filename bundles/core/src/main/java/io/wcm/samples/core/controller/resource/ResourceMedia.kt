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
package io.wcm.samples.core.controller.resource

import io.wcm.handler.media.Media
import io.wcm.handler.media.MediaHandler
import org.apache.commons.lang3.StringUtils
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.api.resource.Resource
import org.apache.sling.models.annotations.Model
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute
import org.apache.sling.models.annotations.injectorspecific.Self
import org.apache.sling.models.annotations.injectorspecific.SlingObject
import javax.annotation.PostConstruct

/**
 * Generic resource-based media model.
 * The "mediaFormat" can be specified as parameter.
 *
 *
 * Compared to @link [io.wcm.handler.media.ui.ResourceMedia] this class
 * has additional support for imageWidth and imageHeight properties.
 *
 */
@Model(adaptables = [SlingHttpServletRequest::class])
class ResourceMedia {
  @RequestAttribute
  private lateinit var mediaFormat: String

  @RequestAttribute(injectionStrategy = InjectionStrategy.OPTIONAL)
  private var cssClass: String? = null

  @Self
  private lateinit var mediaHandler: MediaHandler

  @SlingObject
  private lateinit var resource: Resource

  /**
   * @return Media metadata
   */
  lateinit var metadata: Media
    private set

  @PostConstruct
  private fun activate() {
    metadata = mediaHandler[resource]
      .mediaFormatName(mediaFormat)
      .build()

    if (metadata.isValid && metadata.element != null) {
      val element = metadata.element
      if (StringUtils.isNotEmpty(cssClass)) {
        element.addCssClass(cssClass)
      }
      val props = resource.valueMap
      val imageWidth = props.get("imageWidth", 0)
      if (imageWidth > 0) {
        element.setAttribute("width", imageWidth.toString())
      }
      val imageHeight = props.get("imageWidth", 0)
      if (imageHeight > 0) {
        element.setAttribute("height", imageHeight.toString())
      }
    }
  }

  /**
   * @return Media is valid
   */
  val isValid: Boolean
    get() = metadata.isValid

  /**
   * @return Media markup
   */
  val markup: String
    get() = metadata.markup
}