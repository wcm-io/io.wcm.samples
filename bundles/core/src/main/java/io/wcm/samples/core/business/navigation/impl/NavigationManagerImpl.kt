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

import com.day.cq.wcm.api.Page
import com.day.cq.wcm.api.PageFilter
import io.wcm.handler.link.LinkHandler
import io.wcm.handler.url.ui.SiteRoot
import io.wcm.samples.core.business.navigation.NavigationManager
import io.wcm.samples.core.business.navigation.NavigationPageItem
import io.wcm.sling.models.annotations.AemObject
import org.apache.commons.lang3.StringUtils
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.models.annotations.Model
import org.apache.sling.models.annotations.injectorspecific.Self
import java.util.ArrayList

@Model(adaptables = [SlingHttpServletRequest::class], adapters = [NavigationManager::class])
class NavigationManagerImpl : NavigationManager {

  @Self
  private lateinit var siteRoot: SiteRoot

  @Self
  private lateinit var linkHandler: LinkHandler

  @AemObject
  private lateinit var currentPage: Page

  companion object {
    const val FOOTERNAV_RELATIVE_PATH = "/tools/navigation/footernav"
  }

  /**
   * Generation main navigation links.
   * @param maxLevels Max. navigation hierarchy levels
   * @return Root page for navigation
   */
  override fun getMainNavigation(maxLevels: Int): NavigationPageItem {
    val siteRootPage = siteRoot.rootPage ?: currentPage

    val rootItem = createLinkableItem(siteRootPage)
    if (maxLevels > 0) {
      rootItem.children = createChildItemsRecursively(siteRootPage, this::validLinkableItemCreator, 1, maxLevels)
    }
    return rootItem
  }

  /**
   * Generation footer navigation links and navigation structure.
   * If a page '/tools/navigation/footerNav" exists this page is used for the root navigation hierarchy, using the first
   * level of pages as column structure and the next level as link entries.
   * If not present the main navigation sections are used as column structure, and the main navigation pages itself
   * including their children as link entries.
   * @return Root page for navigation
   */
  override fun getFooterNavigation(): NavigationPageItem {
    val footerNavRoot = siteRoot.getRelativePage(FOOTERNAV_RELATIVE_PATH)
    return if (footerNavRoot != null) {
      getFooterNavigationSpecific(footerNavRoot)
    } else {
      val siteRootPage = siteRoot.rootPage ?: currentPage
      getFooterNavigationDerivedFromMainNav(siteRootPage)
    }
  }

  private fun getFooterNavigationSpecific(footerNavRoot: Page): NavigationPageItem {
    return createStructureItem(footerNavRoot).also {
      it.children = createChildItems(footerNavRoot) { childPage ->
        val columnItem = createStructureItem(childPage)
        columnItem.children = createChildItems(childPage, this::validLinkableItemCreator)
        if (columnItem.children.isEmpty()) {
          null
        } else {
          columnItem
        }
      }
    }
  }

  private fun getFooterNavigationDerivedFromMainNav(siteRootPage: Page): NavigationPageItem {
    return createStructureItem(siteRootPage).also {
      it.children = createChildItems(siteRootPage) { childPage ->
        val columnItem = createStructureItem(childPage)
        val linkItems = ArrayList<NavigationPageItem>()

        val mainnavLinkItem = createLinkableItem(childPage)
        if (mainnavLinkItem.link != null && mainnavLinkItem.link.isValid) {
          linkItems.add(mainnavLinkItem)
        }
        linkItems.addAll(createChildItems(childPage, this::validLinkableItemCreator))
        columnItem.children = linkItems
        if (columnItem.children.isEmpty()) {
          null
        } else {
          columnItem
        }
      }
    }
  }

  private fun createStructureItem(page: Page): NavigationPageItem {
    val title = getItemTitle(page)
    return NavigationPageItem(title)
  }

  private fun createLinkableItem(page: Page): NavigationPageItem {
    val title = getItemTitle(page)
    val isCurrentPage = StringUtils.equals(page.path, currentPage.path)
    val link = linkHandler[page].build()
    return NavigationPageItem(title, isCurrentPage, link)
  }

  private fun getItemTitle(page: Page): String {
    return StringUtils.defaultString(page.navigationTitle, page.title)
  }

  private fun createChildItems(parentPage: Page, itemCreator: (Page) -> NavigationPageItem?): List<NavigationPageItem> {
    val items = ArrayList<NavigationPageItem>()
    for (childPage in parentPage.listChildren(PageFilter(false, false))) {
      val item = itemCreator.invoke(childPage)
      if (item != null) {
        items.add(item)
      }
    }
    return items
  }

  private fun createChildItemsRecursively(
      parentPage: Page,
      itemCreator: (Page) -> NavigationPageItem?,
      level: Int,
      maxLevels: Int
  ): List<NavigationPageItem> {
    return createChildItems(parentPage) {
      val item = itemCreator.invoke(it)
      if (item != null && level < maxLevels) {
        item.children = createChildItemsRecursively(it, itemCreator, level + 1, maxLevels)
      }

      item
    }
  }

  private fun validLinkableItemCreator(page: Page): NavigationPageItem? {
    val item: NavigationPageItem = createLinkableItem(page)
    return if (item.link != null && item.link.isValid) {
      item
    } else {
      null
    }
  }
}