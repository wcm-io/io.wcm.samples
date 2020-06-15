/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2020 wcm.io
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
package io.wcm.samples.core.controller.content;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;

import io.wcm.handler.commons.dom.HtmlElement;
import io.wcm.handler.link.Link;
import io.wcm.handler.link.LinkHandler;
import io.wcm.handler.media.Media;
import io.wcm.handler.media.MediaHandler;
import io.wcm.samples.core.config.MediaFormats;

/**
 * Sponsor teaser item
 */
@Model(adaptables = SlingHttpServletRequest.class,
    adapters = { SponsorTeaserItem.class, ComponentExporter.class },
    resourceType = SponsorTeaserItem.RESOURCE_TYPE)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SponsorTeaserItem implements ComponentExporter {

  static final String RESOURCE_TYPE = "wcm-io-samples/core/components/content/aside/asideSponsorTeaserItem";

  @Self
  private MediaHandler mediaHandler;
  @Self
  private LinkHandler linkHandler;
  @SlingObject
  private Resource resource;

  private Media media;
  private Link link;

  @PostConstruct
  private void activate() {
    media = mediaHandler.get(resource)
        .mediaFormat(MediaFormats.SPONSOR_TEASER)
        .build();

    if (media.isValid() && media.getElement() != null) {
      HtmlElement element = media.getElement();
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

    link = linkHandler.get(resource).build();
  }

  /**
   * @return Media
   */
  public Media getMedia() {
    return media;
  }

  /**
   * @return Media
   */
  public Link getLink() {
    return link;
  }

  @Override
  public String getExportedType() {
    return RESOURCE_TYPE;
  }

}
