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
package io.wcm.samples.core.business.navigation.impl

import io.wcm.samples.core.business.navigation.NavigationManager
import io.wcm.samples.core.business.navigation.NavigationPageItem
import io.wcm.samples.core.extension.adaptTo
import io.wcm.samples.core.testcontext.AppAemContext
import io.wcm.testing.mock.aem.junit5.AemContextExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.BufferedInputStream
import java.io.FileInputStream

@ExtendWith(AemContextExtension::class)
internal class NavigationManagerImplTest {
  private val context = AppAemContext.newAemContext()
  private var underTest: NavigationManager? = null

  @BeforeEach
  fun setUp() {
    underTest = context.request().adaptTo<NavigationManager>()
    Assertions.assertNotNull(underTest)
  }

  @Test
  @Throws(Exception::class)
  fun testGetMainNavigation() {
    FileInputStream("src/main/webapp/app-root/components/admin/page/redirect.json").use { fis ->
      BufferedInputStream(fis).use { bis ->
        context.load().json(bis, "/apps/wcm-io-samples/core/components/admin/page/redirect")
      }
    }
    val rootItem = underTest!!.getMainNavigation(2)
    assertLinkItem("HOME", "/content/wcm-io-samples/en.html", rootItem)
    val mainnavItems = rootItem.children
    Assertions.assertEquals(5, mainnavItems.size)
    assertLinkItem("CONFERENCE", "/content/wcm-io-samples/en/conference.html", mainnavItems[0])
    assertLinkItem("ARCHIVE", "/content/wcm-io-samples/en/archive.html", mainnavItems[4])
    val archiveItems = mainnavItems[4].children
    Assertions.assertEquals(2, archiveItems.size)
    assertLinkItem("Handler Sample 2012", "http://adapt.to/2012/", archiveItems[0])
    assertLinkItem("Handler Sample 2011", "http://adapt.to/2011/", archiveItems[1])
  }

  @Test
  fun testGetMainNavigation_1Levels() {
    val (_, _, _, mainnavItems) = underTest!!.getMainNavigation(1)
    Assertions.assertEquals(5, mainnavItems.size)
    val archiveItems = mainnavItems[4].children
    Assertions.assertEquals(0, archiveItems.size)
  }

  @Test
  fun testGetMainNavigation_0Levels() {
    val (_, _, _, mainnavItems) = underTest!!.getMainNavigation(0)
    Assertions.assertEquals(0, mainnavItems.size)
  }

  /**
   * Test footer navigation with separate definition at tools/footernav/ *
   */
  @Test
  fun testGetFooterNavigation_ToolsFooterNav() {
    val (_, _, _, footerNavItems) = underTest!!.getFooterNavigation()
    Assertions.assertEquals(3, footerNavItems.size)
    assertNoLinkItem("CONFERENCE", footerNavItems[0])
    assertNoLinkItem("ARCHIVE", footerNavItems[2])
    val confItems = footerNavItems[0].children
    Assertions.assertEquals(3, confItems.size)
    assertLinkItem("Conference", "/content/wcm-io-samples/en/conference.html", confItems[0])
    assertLinkItem("Schedule", "/content/wcm-io-samples/en/schedule.html", confItems[1])
  }

  /**
   * Test footer navigation with separate definition derived from main navigation
   */
  @Test
  fun testGetFooterNavigation_MainNav() {

    // delete footer navigation in sample content
    val footerNavResource =
      context.resourceResolver().getResource("/content/wcm-io-samples/en/tools/navigation/footernav")
    context.resourceResolver().delete(footerNavResource!!)

    val (_, _, _, footerNavItems) = underTest!!.getFooterNavigation()
    Assertions.assertEquals(5, footerNavItems.size)
    assertNoLinkItem("CONFERENCE", footerNavItems[0])
    assertNoLinkItem("ARCHIVE", footerNavItems[4])
    val confItems = footerNavItems[0].children
    Assertions.assertEquals(2, confItems.size)
    assertLinkItem("CONFERENCE", "/content/wcm-io-samples/en/conference.html", confItems[0])
    assertLinkItem("Call for Papers", "/content/wcm-io-samples/en/conference/call-for-papers.html", confItems[1])
  }

  private fun assertLinkItem(title: String, linkUrl: String, item: NavigationPageItem) {
    Assertions.assertEquals(title, item.title)
    Assertions.assertEquals(linkUrl, item.link!!.url)
  }

  private fun assertNoLinkItem(title: String, item: NavigationPageItem) {
    Assertions.assertEquals(title, item.title)
    Assertions.assertNull(item.link)
  }
}