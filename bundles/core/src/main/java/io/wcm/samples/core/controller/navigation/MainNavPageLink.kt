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

import io.wcm.samples.core.business.navigation.NavigationManager
import io.wcm.samples.core.business.navigation.NavigationPageItem
import org.apache.sling.api.SlingHttpServletRequest
import org.apache.sling.models.annotations.Default
import org.apache.sling.models.annotations.Model
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute
import org.apache.sling.models.annotations.injectorspecific.Self
import javax.inject.Inject

/**
 * Controller for main navigation.
 */
@Model(adaptables = [SlingHttpServletRequest::class])
class MainNavPageLink @Inject constructor(
    @Self navigationManager: NavigationManager,
    @RequestAttribute(
        name = "levels",
        injectionStrategy = InjectionStrategy.OPTIONAL
    ) @Default(intValues = [1]) levels: Int
) {
  /**
   * @return Root navigation page item
   */
  val root: NavigationPageItem = navigationManager.getMainNavigation(levels)
}