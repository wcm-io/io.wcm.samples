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
package io.wcm.samples.handler.handler;

import io.wcm.handler.commons.dom.HtmlElement;
import io.wcm.handler.media.Media;
import io.wcm.handler.media.markup.SimpleImageMediaMarkupBuilder;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * Image media markup builder which generates special markup for responsive image replacement.
 */
@Model(adaptables = {
    SlingHttpServletRequest.class, Resource.class
})
public class ResponsiveImageMediaMarkupBuilder extends SimpleImageMediaMarkupBuilder {

  @Override
  protected HtmlElement<?> getImageElement(Media media) {
    HtmlElement image = super.getImageElement(media);

    if (image != null) {
      // clear with/height attribute, size is controlled via CSS and JavaScript
      image.removeAttribute("width");
      image.removeAttribute("height");
    }

    return image;
  }

}
