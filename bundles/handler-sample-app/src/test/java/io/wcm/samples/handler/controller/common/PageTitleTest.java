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
package io.wcm.samples.handler.controller.common;

import static org.junit.Assert.assertEquals;
import io.wcm.samples.handler.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit.AemContext;

import org.junit.Rule;
import org.junit.Test;

public class PageTitleTest {

  @Rule
  public final AemContext context = AppAemContext.newAemContext();

  @Test
  public void testGetRecursivePageTitle_Root() throws Exception {
    context.currentPage("/content/handler/sample/en");
    PageTitle underTest = context.request().adaptTo(PageTitle.class);
    assertEquals("Handler Sample 2014", underTest.getRecursivePageTitle());
  }

  @Test
  public void testGetRecursivePageTitle_Conference() throws Exception {
    context.currentPage("/content/handler/sample/en/conference");
    PageTitle underTest = context.request().adaptTo(PageTitle.class);
    assertEquals("Conference - Handler Sample 2014", underTest.getRecursivePageTitle());
  }

  @Test
  public void testGetRecursivePageTitle_Conference_CallForPapers() throws Exception {
    context.currentPage("/content/handler/sample/en/conference/call-for-papers");
    PageTitle underTest = context.request().adaptTo(PageTitle.class);
    assertEquals("Call for Papers - Conference - Handler Sample 2014", underTest.getRecursivePageTitle());
  }

  @Test
  public void testGetRecursivePageTitle_Tools_Imprint() throws Exception {
    context.currentPage("/content/handler/sample/en/tools/navigation/imprint");
    PageTitle underTest = context.request().adaptTo(PageTitle.class);
    assertEquals("Imprint - Handler Sample 2014", underTest.getRecursivePageTitle());
  }

  @Test
  public void testGetSiteRootPageTitle() throws Exception {
    PageTitle underTest = context.request().adaptTo(PageTitle.class);
    assertEquals("Handler Sample 2014", underTest.getSiteRootPageTitle());
  }

}
