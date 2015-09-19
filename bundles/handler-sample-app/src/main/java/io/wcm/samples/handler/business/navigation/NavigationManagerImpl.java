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
package io.wcm.samples.handler.business.navigation;

import io.wcm.handler.link.Link;
import io.wcm.handler.link.LinkHandler;
import io.wcm.samples.handler.util.SiteHelper;
import io.wcm.sling.models.annotations.AemObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;

/**
 * Generates navigation page items based on a page hierarchy for navigation controllers.
 * Only pages that are valid and not hidden with valid links are included.
 * Optionally, structure element pages without links can be included as well.
 */
@Model(adaptables = SlingHttpServletRequest.class, adapters = NavigationManager.class)
public class NavigationManagerImpl implements NavigationManager {

  @Self
  private SiteHelper siteHelper;
  @Self
  private LinkHandler linkHandler;
  @AemObject
  private Page currentPage;

  private static final String FOOTERNAV_RELATIVE_PATH = "/tools/navigation/footernav";

  /**
   * Generation main navigation links.
   * @param maxLevels Max. navigation hierarchy levels
   * @return Root page for navigation
   */
  @Override
  public NavigationPageItem getMainNavigation(final int maxLevels) {
    Page siteRootPage = siteHelper.getSiteRootPage();
    NavigationPageItem rootItem = createLinkableItem(siteRootPage);
    if (maxLevels > 0) {
      rootItem.setChildren(createChildItemsRecursively(siteRootPage, new ValidLinkableItemCreator(), 1, maxLevels));
    }
    return rootItem;
  }

  /**
   * Generation footer navigation links and navigation structure.
   * If a page '/tools/navigation/footerNav" exists this page is used for the root navigation hierarchy, using the first
   * level of pages as column structure and the next level as link entries.
   * If not present the main navigation sections are used as column structure, and the main navigation pages itself
   * including their children as link entries.
   * @return Root page for navigation
   */
  @Override
  public NavigationPageItem getFooterNavigation() {
    Page footerNavRoot = siteHelper.getRelativePage(FOOTERNAV_RELATIVE_PATH);
    if (footerNavRoot != null) {
      return getFooterNavigationSpecific(footerNavRoot);
    }
    else {
      return getFooterNavigationDerivedFromMainNav(siteHelper.getSiteRootPage());
    }
  }

  private NavigationPageItem getFooterNavigationSpecific(final Page footerNavRoot) {
    NavigationPageItem rootItem = createStructureItem(footerNavRoot);
    rootItem.setChildren(createChildItems(footerNavRoot, new ItemCreator() {
      @Override
      public NavigationPageItem create(final Page pPage) {
        NavigationPageItem columnItem = createStructureItem(pPage);
        columnItem.setChildren(createChildItems(pPage, new ValidLinkableItemCreator()));
        if (columnItem.getChildren().isEmpty()) {
          return null;
        }
        else {
          return columnItem;
        }
      }
    }));
    return rootItem;
  }

  private NavigationPageItem getFooterNavigationDerivedFromMainNav(final Page siteRootPage) {
    NavigationPageItem rootItem = createStructureItem(siteRootPage);
    rootItem.setChildren(createChildItems(siteRootPage, new ItemCreator() {
      @Override
      public NavigationPageItem create(final Page pPage) {
        NavigationPageItem columnItem = createStructureItem(pPage);
        List<NavigationPageItem> linkItems = new ArrayList<>();
        NavigationPageItem mainnavLinkItem = createLinkableItem(pPage);
        if (mainnavLinkItem.getLink().isValid()) {
          linkItems.add(mainnavLinkItem);
        }
        linkItems.addAll(createChildItems(pPage, new ValidLinkableItemCreator()));
        columnItem.setChildren(linkItems);
        if (columnItem.getChildren().isEmpty()) {
          return null;
        }
        else {
          return columnItem;
        }
      }
    }));
    return rootItem;
  }

  private NavigationPageItem createStructureItem(final Page page) {
    String title = getItemTitle(page);
    return new NavigationPageItem(title);

  }

  private NavigationPageItem createLinkableItem(final Page page) {
    String title = getItemTitle(page);
    boolean isCurrentPage = StringUtils.equals(page.getPath(), currentPage.getPath());
    Link link = linkHandler.get(page).build();
    return new NavigationPageItem(title, isCurrentPage, link);
  }

  private String getItemTitle(final Page page) {
    return StringUtils.defaultString(page.getNavigationTitle(), page.getTitle());
  }

  private List<NavigationPageItem> createChildItems(final Page parentPage, final ItemCreator itemCreator) {
    List<NavigationPageItem> items = new ArrayList<>();
    Iterator<Page> childPages = parentPage.listChildren(new PageFilter(false, false));
    while (childPages.hasNext()) {
      Page childPage = childPages.next();
      NavigationPageItem item = itemCreator.create(childPage);
      if (item != null) {
        items.add(item);
      }
    }
    return items;
  }

  private List<NavigationPageItem> createChildItemsRecursively(final Page parentPage, final ItemCreator itemCreator,
      final int level, final int maxLevels) {
    return createChildItems(parentPage, new ItemCreator() {
      @Override
      public NavigationPageItem create(final Page pPage) {
        NavigationPageItem item = itemCreator.create(pPage);
        if (item != null) {
          if (level < maxLevels) {
            item.setChildren(createChildItemsRecursively(pPage, itemCreator, level + 1, maxLevels));
          }
        }
        return item;
      }
    });
  }

  private interface ItemCreator {
    NavigationPageItem create(Page pPage);
  }

  private class ValidLinkableItemCreator implements ItemCreator {
    @Override
    public NavigationPageItem create(Page page) {
      NavigationPageItem item = createLinkableItem(page);
      if (item.getLink().isValid()) {
        return item;
      }
      else {
        return null;
      }
    }
  }

}
