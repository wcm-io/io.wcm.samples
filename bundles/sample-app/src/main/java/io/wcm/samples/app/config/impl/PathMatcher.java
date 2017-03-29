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
package io.wcm.samples.app.config.impl;

import java.util.regex.Pattern;

import org.apache.sling.api.resource.Resource;

final class PathMatcher {

  private static final Pattern PATH_PATTERN = Pattern.compile("^/content/(dam/)?wcm-io-samples(/.*)?$");

  private PathMatcher() {
    // constants only
  }

  /**
   * @param resource Context resource
   * @return true if path matches
   */
  public static boolean matches(Resource resource) {
    return resource != null && PATH_PATTERN.matcher(resource.getPath()).matches();
  }

}
