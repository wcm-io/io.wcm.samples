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
package io.wcm.samples.core.controller.resource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.samples.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class ResourceRichTextTest {

  private final AemContext context = AppAemContext.newAemContext();

  @Test
  void testRichText() {
    context.currentResource("/content/wcm-io-samples/en/jcr:content/content/contentrichtext");
    ResourceRichText underTest = context.request().adaptTo(ResourceRichText.class);
    assertTrue(underTest.isValid());
    assertTrue(StringUtils.isNotBlank(underTest.getMarkup()));
  }

  @Test
  void testInvalidRichText() {
    context.currentResource("/content/wcm-io-samples/en/jcr:content/content/contentheadline");
    ResourceRichText underTest = context.request().adaptTo(ResourceRichText.class);
    assertFalse(underTest.isValid());
    assertNull(underTest.getMarkup());
  }

}
