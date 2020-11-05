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

import io.wcm.handler.link.Link
import io.wcm.handler.link.LinkHandler
import io.wcm.handler.url.ui.SiteRoot
import org.apache.commons.lang3.StringUtils
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.models.annotations.Default
import org.apache.sling.models.annotations.Model
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute
import org.apache.sling.models.annotations.injectorspecific.Self
import javax.inject.Inject

/**
 * Generic model for building a link to page addressed via a path relative to site root.
 * If not path is given, the site root is referenced.
 * The link title is set to navigation title / page title / title text (whatever is not empty).
 */
@Model(adaptables = [SlingHttpServletRequest::class])
class SiteRootRelativePageLink @Inject constructor(
    @Self siteRoot: SiteRoot,
    @Self linkHandler: LinkHandler,
    @RequestAttribute(
        name = "relativePath",
        injectionStrategy = InjectionStrategy.OPTIONAL
    ) relativePath: String?,
    @RequestAttribute(
        name = "titleType",
        injectionStrategy = InjectionStrategy.OPTIONAL
    ) @Default(values = ["navigationTitle"]) titleType: String?
) {
  /**
   * @return Link
   */
  val metadata: Link

  /**
   * @return Link title
   */
  val title: String?

  /**
   * @return Link is valid
   */
  val isValid: Boolean
    get() = metadata.isValid

  /**
   * @return Anchor attributes
   */
  val attributes: Map<String, String>
    get() = metadata.anchorAttributes

  init {
    val page = if (StringUtils.isNotEmpty(relativePath)) {
      siteRoot.getRelativePage(relativePath)
    } else {
      siteRoot.rootPage
    }
    if (page != null) {
      metadata = linkHandler[page].build()
      title = when (titleType) {
        "pageTitle" -> StringUtils.defaultString(page.pageTitle, page.title)
        "navigationTitle" -> StringUtils.defaultString(page.navigationTitle, page.title)
        else -> StringUtils.defaultString(page.navigationTitle, page.title)
      }
    } else {
      metadata = linkHandler.invalid()
      title = null
    }
  }
}