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

import io.wcm.handler.link.Link;
import io.wcm.handler.link.LinkHandler;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

/**
 * Generic resource-based link model.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class ResourceLink {

  @Self
  private LinkHandler linkHandler;
  @SlingObject
  private Resource resource;

  private Link link;

  @PostConstruct
  protected void activate() {
    link = linkHandler.get(resource).build();
  }

  /**
   * @return Link
   */
  public Link getMetadata() {
    return link;
  }

  /**
   * @return Link is valid
   */
  public boolean isValid() {
    return link.isValid();
  }

  /**
   * @return Anchor attributes
   */
  public Map<String, String> getAttributes() {
    return link.getAnchorAttributes();
  }

}
