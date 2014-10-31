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

/**
 * Generates navigation page items based on a page hierarchy for navigation controllers.
 * Only pages that are valid and not hidden with valid links are included.
 * Optionally, structure element pages without links can be included as well.
 */
public interface NavigationManager {

  /**
   * Generation main navigation links.
   * @param maxLevels Max. navigation hierarchy levels
   * @return Root page for navigation
   */
  NavigationPageItem getMainNavigation(final int maxLevels);

  /**
   * Generation footer navigation links and navigation structure.
   * If a page '/tools/navigation/footerNav" exists this page is used for the root navigation hierarchy, using the first
   * level of pages as column structure and the next level as link entries.
   * If not present the main navigation sections are used as column structure, and the main navigation pages itself
   * including their children as link entries.
   * @return Root page for navigation
   */
  NavigationPageItem getFooterNavigation();

}
