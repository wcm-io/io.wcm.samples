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
package io.wcm.samples.core.controller.navigation

import io.wcm.samples.core.extension.adaptTo
import io.wcm.samples.core.testcontext.AppAemContext
import io.wcm.testing.mock.aem.junit5.AemContextExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(AemContextExtension::class)
internal class SiteRootRelativePageLinkTest {
  private val context = AppAemContext.newAemContext()

  @Test
  fun testSiteRoot() {
    assertLink("HOME", "/content/wcm-io-samples/en.html")
  }

  @Test
  fun testSiteRoot_PageTitle() {
    context.request().setAttribute("titleType", "pageTitle")
    assertLink("Handler Sample 2014", "/content/wcm-io-samples/en.html")
  }

  @Test
  fun testImprint() {
    context.request().setAttribute("relativePath", "/tools/navigation/imprint")
    assertLink("Imprint", "/content/wcm-io-samples/en/tools/navigation/imprint.html")
  }

  private fun assertLink(title: String, linkUrl: String) {
    val underTest = context.request().adaptTo<SiteRootRelativePageLink>()

    Assertions.assertEquals(title, underTest!!.title)
    Assertions.assertTrue(underTest.isValid)
    Assertions.assertEquals(linkUrl, underTest.metadata.url)
    Assertions.assertEquals(linkUrl, underTest.attributes["href"])
  }
}