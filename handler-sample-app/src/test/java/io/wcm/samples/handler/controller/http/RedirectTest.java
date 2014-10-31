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
package io.wcm.samples.handler.controller.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.wcm.samples.handler.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit.AemContext;

import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.junit.Rule;
import org.junit.Test;

import com.day.cq.wcm.api.WCMMode;

public class RedirectTest {

  @Rule
  public final AemContext context = AppAemContext.newAemContext();

  @Test
  public void testRedirectDefault() {
    context.currentPage("/content/handler/sample/en/archive/handlerSample---2012");
    Redirect redirect = context.request().adaptTo(Redirect.class);

    assertEquals(HttpServletResponse.SC_MOVED_TEMPORARILY, context.response().getStatus());
    assertEquals("http://adapt.to/2012/", context.response().getHeader("Location"));
    assertFalse(redirect.isRenderPage());
  }

  @Test
  public void testRedirect301() throws Exception {
    context.currentPage("/content/handler/sample/en/archive/handlerSample---2012");

    Resource currenResource = context.request().getResource();
    ModifiableValueMap props = currenResource.adaptTo(ModifiableValueMap.class);
    props.put("redirectStatus", HttpServletResponse.SC_MOVED_PERMANENTLY);
    context.resourceResolver().commit();

    Redirect redirect = context.request().adaptTo(Redirect.class);

    assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, context.response().getStatus());
    assertEquals("http://adapt.to/2012/", context.response().getHeader("Location"));
    assertFalse(redirect.isRenderPage());
  }

  @Test
  public void testRedirectInvalid() throws Exception {
    context.currentPage("/content/handler/sample/en/archive");

    Redirect redirect = context.request().adaptTo(Redirect.class);

    assertEquals(HttpServletResponse.SC_NOT_FOUND, context.response().getStatus());
    assertFalse(redirect.isRenderPage());
  }

  @Test
  public void testRedirectEditMode() throws Exception {
    WCMMode.EDIT.toRequest(context.request());

    context.currentPage("/content/handler/sample/en/archive/handlerSample---2012");
    Redirect redirect = context.request().adaptTo(Redirect.class);

    assertEquals(HttpServletResponse.SC_OK, context.response().getStatus());
    assertTrue(redirect.isRenderPage());
  }

}
