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
package io.wcm.samples.core.business.navigation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.samples.core.business.navigation.NavigationManager;
import io.wcm.samples.core.business.navigation.NavigationPageItem;
import io.wcm.samples.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class NavigationManagerImplTest {

  private final AemContext context = AppAemContext.newAemContext();

  private NavigationManager underTest;

  @BeforeEach
  void setUp() {
    underTest = context.request().adaptTo(NavigationManager.class);
    assertNotNull(underTest);
  }

  @Test
  void testGetMainNavigation() throws Exception {
    try (FileInputStream fis = new FileInputStream("src/main/webapp/app-root/components/admin/page/redirect.json");
        BufferedInputStream bis = new BufferedInputStream(fis)) {
      context.load().json(bis, "/apps/wcm-io-samples/core/components/admin/page/redirect");
    }

    NavigationPageItem rootItem = underTest.getMainNavigation(2);
    assertLinkItem("HOME", "/content/wcm-io-samples/en.html", rootItem);

    List<NavigationPageItem> mainnavItems = rootItem.getChildren();
    assertEquals(5, mainnavItems.size());

    assertLinkItem("CONFERENCE", "/content/wcm-io-samples/en/conference.html", mainnavItems.get(0));
    assertLinkItem("ARCHIVE", "/content/wcm-io-samples/en/archive.html", mainnavItems.get(4));

    List<NavigationPageItem> archiveItems = mainnavItems.get(4).getChildren();
    assertEquals(2, archiveItems.size());

    assertLinkItem("Handler Sample 2012", "http://adapt.to/2012/", archiveItems.get(0));
    assertLinkItem("Handler Sample 2011", "http://adapt.to/2011/", archiveItems.get(1));
  }

  @Test
  void testGetMainNavigation_1Levels() throws Exception {
    NavigationPageItem rootItem = underTest.getMainNavigation(1);
    List<NavigationPageItem> mainnavItems = rootItem.getChildren();
    assertEquals(5, mainnavItems.size());
    List<NavigationPageItem> archiveItems = mainnavItems.get(4).getChildren();
    assertEquals(0, archiveItems.size());
  }

  @Test
  void testGetMainNavigation_0Levels() throws Exception {
    NavigationPageItem rootItem = underTest.getMainNavigation(0);
    List<NavigationPageItem> mainnavItems = rootItem.getChildren();
    assertEquals(0, mainnavItems.size());
  }

  /**
   * Test footer navigation with separate definition at tools/footernav/*
   */
  @Test
  void testGetFooterNavigation_ToolsFooterNav() throws Exception {
    NavigationPageItem rootItem = underTest.getFooterNavigation();
    List<NavigationPageItem> footerNavItems = rootItem.getChildren();
    assertEquals(3, footerNavItems.size());

    assertNoLinkItem("CONFERENCE", footerNavItems.get(0));
    assertNoLinkItem("ARCHIVE", footerNavItems.get(2));

    List<NavigationPageItem> confItems = footerNavItems.get(0).getChildren();
    assertEquals(3, confItems.size());

    assertLinkItem("Conference", "/content/wcm-io-samples/en/conference.html", confItems.get(0));
    assertLinkItem("Schedule", "/content/wcm-io-samples/en/schedule.html", confItems.get(1));
  }

  /**
   * Test footer navigation with separate definition derived from main navigation
   */
  @Test
  void testGetFooterNavigation_MainNav() throws Exception {

    // delete footer navigation in sample content
    Resource footerNavResource = context.resourceResolver().getResource("/content/wcm-io-samples/en/tools/navigation/footernav");
    context.resourceResolver().delete(footerNavResource);

    NavigationPageItem rootItem = underTest.getFooterNavigation();
    List<NavigationPageItem> footerNavItems = rootItem.getChildren();
    assertEquals(5, footerNavItems.size());

    assertNoLinkItem("CONFERENCE", footerNavItems.get(0));
    assertNoLinkItem("ARCHIVE", footerNavItems.get(4));

    List<NavigationPageItem> confItems = footerNavItems.get(0).getChildren();
    assertEquals(2, confItems.size());

    assertLinkItem("CONFERENCE", "/content/wcm-io-samples/en/conference.html", confItems.get(0));
    assertLinkItem("Call for Papers", "/content/wcm-io-samples/en/conference/call-for-papers.html", confItems.get(1));
  }

  private void assertLinkItem(String title, String linkUrl, NavigationPageItem item) {
    assertEquals(title, item.getTitle());
    assertEquals(linkUrl, item.getLink().getUrl());
  }

  private void assertNoLinkItem(String title, NavigationPageItem item) {
    assertEquals(title, item.getTitle());
    assertNull(item.getLink());
  }

}
