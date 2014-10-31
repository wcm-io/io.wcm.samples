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
import io.wcm.samples.handler.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit.AemContext;

import javax.servlet.http.HttpServletResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.day.cq.wcm.api.WCMMode;

@RunWith(MockitoJUnitRunner.class)
public class DenyWcmDisabledTest {

  @Rule
  public final AemContext context = AppAemContext.newAemContext();

  @Test
  public void testWcmModeDisabled() {
    WCMMode.DISABLED.toRequest(context.request());
    context.request().adaptTo(DenyWcmDisabled.class);
    assertEquals(HttpServletResponse.SC_FORBIDDEN, context.response().getStatus());
  }

  @Test
  public void testWcmModeEdit() {
    WCMMode.EDIT.toRequest(context.request());
    context.request().adaptTo(DenyWcmDisabled.class);
    assertEquals(HttpServletResponse.SC_OK, context.response().getStatus());
  }

}
