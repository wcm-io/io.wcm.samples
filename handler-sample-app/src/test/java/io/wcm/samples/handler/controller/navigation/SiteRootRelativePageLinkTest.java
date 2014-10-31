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
package io.wcm.samples.handler.controller.navigation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.wcm.samples.handler.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit.AemContext;

import org.junit.Rule;
import org.junit.Test;

public class SiteRootRelativePageLinkTest {

  @Rule
  public final AemContext context = AppAemContext.newAemContext();

  @Test
  public void testSiteRoot() {
    assertLink("HOME", "/content/handler/sample/en.html");
  }

  @Test
  public void testSiteRoot_PageTitle() {
    context.request().setAttribute("titleType", "pageTitle");
    assertLink("adaptTo() 2014", "/content/handler/sample/en.html");
  }

  @Test
  public void testImprint() {
    context.request().setAttribute("relativePath", "/tools/navigation/imprint");
    assertLink("Imprint", "/content/handler/sample/en/tools/navigation/imprint.html");
  }

  private void assertLink(String title, String linkUrl) {
    SiteRootRelativePageLink underTest = context.request().adaptTo(SiteRootRelativePageLink.class);
    assertEquals(title, underTest.getTitle());
    assertTrue(underTest.isValid());
    assertEquals(linkUrl, underTest.getMetadata().getUrl());
    assertEquals(linkUrl, underTest.getAttributes().get("href"));
  }

}
