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
package io.wcm.samples.core.controller.common

import com.day.cq.wcm.api.Page
import io.wcm.handler.url.ui.SiteRoot
import io.wcm.samples.core.config.AppTemplate
import io.wcm.sling.models.annotations.AemObject
import io.wcm.wcm.commons.util.Template
import org.apache.commons.lang3.StringUtils
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.models.annotations.Model
import org.apache.sling.models.annotations.injectorspecific.Self

/**
 * Controller for generation page titles.
 */
@Model(adaptables = [SlingHttpServletRequest::class])
class PageTitle {

  @AemObject
  private lateinit var currentPage: Page

  @Self
  private lateinit var siteRoot: SiteRoot

  /**
   * @return Recursive page title for HTML title
   */
  val recursivePageTitle: String
    get() = getRecursivePageTitle(currentPage)

  /**
   * @return Site root page title
   */
  val siteRootPageTitle: String?
    get() {
      return siteRoot.rootPage?.pageTitle
    }

  /**
   * Build html title from page titles up to site root page.
   * @param page Page
   * @return Html title
   */
  private fun getRecursivePageTitle(page: Page?): String {
    if (page == null) {
      return ""
    }
    return when {
      siteRoot.isRootPage(page) -> {
        StringUtils.defaultString(page.pageTitle, page.title)
      }
      Template.`is`(page, AppTemplate.ADMIN_STRUCTURE_ELEMENT) -> {
        getRecursivePageTitle(page.parent)
      }
      else -> {
        "${
          StringUtils.defaultString(
              page.pageTitle,
              page.title
          )
        } - ${getRecursivePageTitle(page.parent)}"
      }
    }
  }
}