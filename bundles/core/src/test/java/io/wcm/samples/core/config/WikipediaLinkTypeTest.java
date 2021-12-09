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

import static io.wcm.handler.link.LinkNameConstants.PN_LINK_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;

import io.wcm.handler.link.Link;
import io.wcm.handler.link.LinkHandler;
import io.wcm.samples.core.testcontext.AppAemContext;
import io.wcm.sling.commons.adapter.AdaptTo;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class WikipediaLinkTypeTest {

  private final AemContext context = AppAemContext.newAemContext();

  private Page page;
  private LinkHandler linkHandler;

  @BeforeEach
  void setUp() {
    page = context.currentPage("/content/wcm-io-samples/en");
    linkHandler = AdaptTo.notNull(context.request(), LinkHandler.class);
  }

  @Test
  void testValidLink() {
    Resource linkResource = context.create().resource(page, "link",
        PN_LINK_TYPE, WikipediaLinkType.ID,
        WikipediaLinkType.PN_LINK_WIKIPEDIA_REF, "Adobe_Experience_Cloud",
        WikipediaLinkType.PN_LINK_WIKIPEDIA_LANGUAGE, "en");

    Link link = linkHandler.get(linkResource).build();
    assertTrue(link.isValid());
    assertEquals("https://en.wikipedia.org/wiki/Adobe_Experience_Cloud", link.getUrl());
  }

  @Test
  void testInvalidLink() {
    Resource linkResource = context.create().resource(page, "link",
        PN_LINK_TYPE, WikipediaLinkType.ID);

    Link link = linkHandler.get(linkResource).build();
    assertFalse(link.isValid());
  }

}
