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
package io.wcm.samples.handler.controller.resource;

import io.wcm.handler.commons.dom.HtmlElement;
import io.wcm.handler.media.Media;
import io.wcm.handler.media.MediaArgs;
import io.wcm.handler.media.MediaHandler;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

/**
 * Generic resource-based media model.
 * The "mediaFormat" can be specified as parameter.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ResourceMedia {

  @RequestAttribute
  private String mediaFormat;

  @RequestAttribute(optional = true)
  private String cssClass;

  @Self
  private MediaHandler mediaHandler;
  @SlingObject
  private Resource resource;

  private Media media;

  @PostConstruct
  protected void activate() {
    media = mediaHandler.get(resource, new MediaArgs(mediaFormat)).build();

    if (media.isValid() && media.getElement() != null) {
      HtmlElement element = media.getElement();
      if (StringUtils.isNotEmpty(cssClass)) {
        element.addCssClass(cssClass);
      }
      ValueMap props = resource.getValueMap();
      int imageWidth = props.get("imageWidth", 0);
      if (imageWidth > 0) {
        element.setAttribute("width", Integer.toString(imageWidth));
      }
      int imageHeight = props.get("imageWidth", 0);
      if (imageHeight > 0) {
        element.setAttribute("height", Integer.toString(imageHeight));
      }
    }
  }

  /**
   * @return Media metadata
   */
  public Media getMetadata() {
    return media;
  }

  /**
   * @return Media is valid
   */
  public boolean isValid() {
    return media.isValid();
  }

  /**
   * @return Media markup
   */
  public String getMarkup() {
    return media.getMarkup();
  }

}
