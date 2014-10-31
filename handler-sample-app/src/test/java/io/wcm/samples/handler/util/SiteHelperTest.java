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
package io.wcm.samples.handler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import io.wcm.samples.handler.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit.AemContext;

import org.junit.Rule;
import org.junit.Test;

import com.day.cq.wcm.api.WCMMode;

public class SiteHelperTest {

  @Rule
  public final AemContext context = AppAemContext.newAemContext();

  @Test
  public void testGetSiteRootPath() {
    SiteHelper underTest = context.request().adaptTo(SiteHelper.class);
    assertEquals("/content/handler/sample/en", underTest.getSiteRootPath());
  }

  @Test
  public void testGetSiteRootPage() {
    SiteHelper underTest = context.request().adaptTo(SiteHelper.class);
    assertEquals("/content/handler/sample/en", underTest.getSiteRootPage().getPath());
    assertEquals("/content/handler/sample/en",
        underTest.getSiteRootPage("/content/handler/sample/en/conference").getPath());
    assertNull(underTest.getSiteRootPage("/other/path"));
  }

  @Test
  public void testGetRelativePage() {
    SiteHelper underTest = context.request().adaptTo(SiteHelper.class);
    assertEquals("/content/handler/sample/en/conference", underTest.getRelativePage("/conference").getPath());
  }

  @Test
  public void testGetEditPreviewIntegratorCacheSelector() throws Exception {
    SiteHelper underTest = context.request().adaptTo(SiteHelper.class);
    assertEquals("", underTest.getEditPreviewIntegratorCacheSelector());
  }

  @Test
  public void testGetEditPreviewIntegratorCacheSelector_OtherWcmMode() throws Exception {
    WCMMode.EDIT.toRequest(context.request());
    SiteHelper underTest = context.request().adaptTo(SiteHelper.class);
    assertEquals(".edit", underTest.getEditPreviewIntegratorCacheSelector());
  }

  @Test
  public void testIsSiteRootPage() {
    SiteHelper underTest = context.request().adaptTo(SiteHelper.class);
    assertTrue(underTest.isSiteRootPage(context.pageManager().getPage("/content/handler/sample/en")));
    assertFalse(underTest.isSiteRootPage(context.pageManager().getPage("/content/handler/sample/en/conference")));
  }

}
