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
package io.wcm.samples.core.controller.common;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.wcm.samples.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class PageTitleTest {

  private final AemContext context = AppAemContext.newAemContext();

  @ParameterizedTest
  @CsvSource({
      "/content/wcm-io-samples/en,Handler Sample 2014",
      "/content/wcm-io-samples/en/conference,Conference - Handler Sample 2014",
      "/content/wcm-io-samples/en/conference/call-for-papers,Call for Papers - Conference - Handler Sample 2014",
      "/content/wcm-io-samples/en/tools/navigation/imprint,Imprint - Handler Sample 2014"
  })
  void testGetRecursivePageTitle(String path, String expectedTitle) {
    context.currentPage(path);
    PageTitle underTest = context.request().adaptTo(PageTitle.class);
    assertEquals(expectedTitle, underTest.getRecursivePageTitle());
  }

  @Test
  void testGetSiteRootPageTitle() {
    PageTitle underTest = context.request().adaptTo(PageTitle.class);
    assertEquals("Handler Sample 2014", underTest.getSiteRootPageTitle());
  }

}
