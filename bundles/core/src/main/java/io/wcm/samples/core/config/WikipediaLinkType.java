/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2021 wcm.io
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
package io.wcm.samples.core.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.wcm.handler.link.Link;
import io.wcm.handler.link.spi.LinkType;

/**
 * Example for a custom link type that supports linking to Wikipedia by providing a
 * wiki page name and wikipedia language.
 */
@Model(adaptables = {
    SlingHttpServletRequest.class, Resource.class
})
public final class WikipediaLinkType extends LinkType {

  /**
   * Link type ID
   */
  public static final String ID = "wikipedia";

  /**
   * Property for Wikipedia wiki page name.
   */
  public static final String PN_LINK_WIKIPEDIA_REF = "linkWikipediaRef";

  /**
   * Property for Wikipedia language.
   */
  public static final String PN_LINK_WIKIPEDIA_LANGUAGE = "linkWikipediaLanguage";

  private static final String DEFAULT_LANGUAGE = "en";

  /**
   * @return Link type ID (is stored as identifier in repository)
   */
  @Override
  public @NotNull String getId() {
    return ID;
  }

  @Override
  public @NotNull String getLabel() {
    return "Wikipedia";
  }

  @Override
  public String getPrimaryLinkRefProperty() {
    return PN_LINK_WIKIPEDIA_REF;
  }

  @Override
  public @Nullable String getEditComponentResourceType() {
    return "wcm-io-samples/core/components/global/linktype/wikipedia";
  }

  @Override
  public boolean accepts(@NotNull String linkRef) {
    // any value accepted, we cannot do any further validation of the the wiki page name
    return StringUtils.isNotBlank(linkRef);
  }

  @Override
  public @NotNull Link resolveLink(@NotNull Link link) {
    ValueMap props = link.getLinkRequest().getResourceProperties();

    // get wikipedia reference from link properties
    String wikiPageName = props.get(PN_LINK_WIKIPEDIA_REF, link.getLinkRequest().getReference());
    String language = props.get(PN_LINK_WIKIPEDIA_LANGUAGE, DEFAULT_LANGUAGE);

    // build link URL which is only an anchor tag in the preview.
    String linkUrl = null;
    if (StringUtils.isNoneBlank(wikiPageName, language)) {
      linkUrl = "https://" + language + ".wikipedia.org/wiki/" + wikiPageName;
    }

    // set link url
    link.setUrl(linkUrl);

    return link;
  }

  @Override
  public String toString() {
    return ID;
  }

}
