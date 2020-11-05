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
package io.wcm.samples.core.config.impl

import com.day.cq.wcm.api.Page
import io.wcm.handler.link.spi.LinkHandlerConfig
import io.wcm.handler.link.type.MediaLinkType
import io.wcm.samples.core.config.AppTemplate
import io.wcm.wcm.commons.util.Template
import org.osgi.service.component.annotations.Component

/**
 * Link handler configuration.
 */
@Component(service = [LinkHandlerConfig::class])
class LinkHandlerConfigImpl : LinkHandlerConfig() {

  override fun isValidLinkTarget(page: Page): Boolean {
    return !Template.`is`(page, AppTemplate.ADMIN_STRUCTURE_ELEMENT)
  }

  override fun isRedirect(page: Page): Boolean {
    return Template.`is`(page, AppTemplate.ADMIN_REDIRECT)
  }

  override fun getLinkRootPath(page: Page, linkTypeId: String): String? {
    return if (MediaLinkType.ID == linkTypeId) {
      MediaHandlerConfigImpl.DAM_ROOT
    } else super.getLinkRootPath(page, linkTypeId)
  }
}