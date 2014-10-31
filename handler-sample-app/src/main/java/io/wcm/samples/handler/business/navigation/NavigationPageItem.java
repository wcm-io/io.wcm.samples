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

import java.util.Collections;
import java.util.List;

/**
 * Navigation item with link metadata and title.
 */
public class NavigationPageItem {

  private final Link link;
  private final String title;
  private final boolean active;
  private List<NavigationPageItem> children = Collections.<NavigationPageItem>emptyList();

  /**
   * Navigation page item with link.
   * @param title Link title
   * @param active Page is current page
   * @param metadata Link metadata
   */
  public NavigationPageItem(String title, boolean active, Link metadata) {
    this.title = title;
    this.active = active;
    this.link = metadata;
  }

  /**
   * Navigation page item without link.
   * @param title Link title
   */
  public NavigationPageItem(String title) {
    this(title, false, null);
  }

  /**
   * @return Link
   */
  public Link getLink() {
    return this.link;
  }

  /**
   * @return Link title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * @return Page is current page
   */
  public boolean isActive() {
    return this.active;
  }

  /**
   * @return Child navigation page items
   */
  public List<NavigationPageItem> getChildren() {
    return this.children;
  }

  /**
   * @param pChildren Child navigation page items
   */
  public void setChildren(List<NavigationPageItem> pChildren) {
    this.children = pChildren;
  }

}
