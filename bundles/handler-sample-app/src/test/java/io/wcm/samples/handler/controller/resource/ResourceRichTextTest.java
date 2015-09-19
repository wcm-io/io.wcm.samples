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
package io.wcm.samples.handler.controller.resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import io.wcm.samples.handler.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit.AemContext;

import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;

public class ResourceRichTextTest {

  @Rule
  public final AemContext context = AppAemContext.newAemContext();

  @Test
  public void testRichText() {
    context.currentResource("/content/handler/sample/en/jcr:content/content/contentrichtext");
    ResourceRichText underTest = context.request().adaptTo(ResourceRichText.class);
    assertTrue(underTest.isValid());
    assertTrue(StringUtils.isNotBlank(underTest.getMarkup()));
  }

  @Test
  public void testInvalidRichText() {
    context.currentResource("/content/handler/sample/en/jcr:content/content/contentheadline");
    ResourceRichText underTest = context.request().adaptTo(ResourceRichText.class);
    assertFalse(underTest.isValid());
    assertNull(underTest.getMarkup());
  }

}
