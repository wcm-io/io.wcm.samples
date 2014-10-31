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

import io.wcm.sling.models.annotations.AemObject;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.wcm.api.WCMMode;

/**
 * Sets "HTTP 403 Forbidden" header if WCM mode is disabled.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class DenyWcmDisabled {

  @SlingObject
  private SlingHttpServletResponse response;
  @AemObject
  private WCMMode wcmMode;

  @PostConstruct
  protected void activate() throws IOException {
    if (wcmMode == WCMMode.DISABLED) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
  }

}
