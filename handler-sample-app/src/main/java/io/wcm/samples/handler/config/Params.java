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
package io.wcm.samples.handler.config;

import static io.wcm.config.api.ParameterBuilder.create;
import static io.wcm.config.editor.EditorProperties.DESCRIPTION;
import static io.wcm.config.editor.EditorProperties.GROUP;
import static io.wcm.samples.handler.config.ApplicationProviderImpl.APPLICATION_ID;
import io.wcm.config.api.Parameter;
import io.wcm.config.editor.WidgetTypes;

/**
 * Configuration parameters;
 */
public final class Params {

  private Params() {
    // constants only
  }

  private static final String AMIANDO_PARAMETER_GROUP = "Amiando Ticketing";

  /**
   * Fallback Url when JavaScript/Frames are not avialable.
   */
  public static final Parameter AMIANDO_FALLBACK_URL = create("amiandoFallbackUrl", String.class, APPLICATION_ID)
      .property(DESCRIPTION, "Fallback Url when JavaScript/Frames are not avialable.")
      .property(GROUP, AMIANDO_PARAMETER_GROUP)
      .properties(WidgetTypes.TEXTFIELD.getDefaultWidgetConfiguration())
      .build();

  /**
   * Amiando JavaScript Interface URL for current event.
   */
  public static final Parameter AMIANDO_SCRIPT_URL = create("amiandoScriptUrl", String.class, APPLICATION_ID)
      .property(DESCRIPTION, "Amiando JavaScript Interface URL for current event.")
      .property(GROUP, AMIANDO_PARAMETER_GROUP)
      .properties(WidgetTypes.TEXTFIELD.getDefaultWidgetConfiguration())
      .build();

  /**
   * Amiando IFrame URL for current event.
   */
  public static final Parameter AMIANDO_IFRAME_URL = create("amiandoIFrameUrl", String.class, APPLICATION_ID)
      .property(DESCRIPTION, "Amiando IFrame URL for current event.")
      .property(GROUP, AMIANDO_PARAMETER_GROUP)
      .properties(WidgetTypes.TEXTFIELD.getDefaultWidgetConfiguration())
      .build();

  /**
   * Name/ID of IFrame for Aminado Ticketing.
   */
  public static final Parameter AMIANDO_IFRAME_NAME = create("amiandoIFrameName", String.class, APPLICATION_ID)
      .property(DESCRIPTION, "Name/ID of IFrame for Aminado Ticketing.")
      .property(GROUP, AMIANDO_PARAMETER_GROUP)
      .properties(WidgetTypes.TEXTFIELD.getDefaultWidgetConfiguration())
      .build();

}
