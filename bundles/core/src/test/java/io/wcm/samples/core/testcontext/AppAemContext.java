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
package io.wcm.samples.core.testcontext;

import static io.wcm.testing.mock.wcmio.caconfig.ContextPlugins.WCMIO_CACONFIG;
import static io.wcm.testing.mock.wcmio.handler.ContextPlugins.WCMIO_HANDLER;
import static io.wcm.testing.mock.wcmio.sling.ContextPlugins.WCMIO_SLING;
import static io.wcm.testing.mock.wcmio.wcm.ContextPlugins.WCMIO_WCM;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;

import java.io.IOException;

import org.apache.sling.api.resource.PersistenceException;

import io.wcm.handler.media.spi.MediaFormatProvider;
import io.wcm.samples.core.config.AppTemplate;
import io.wcm.samples.core.config.impl.LinkHandlerConfigImpl;
import io.wcm.samples.core.config.impl.MediaFormatProviderImpl;
import io.wcm.samples.core.config.impl.MediaHandlerConfigImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextCallback;
import io.wcm.testing.mock.wcmio.caconfig.MockCAConfig;

/**
 * Sets up {@link AemContext} for unit tests in this application.
 */
public final class AppAemContext {

  public static final String CONTENT_ROOT = "/content/wcm-io-samples/en";
  public static final String DAM_ROOT = "/content/dam/wcm-io-samples";

  private AppAemContext() {
    // static methods only
  }

  public static AemContext newAemContext() {
    return new AemContextBuilder()
        .plugin(CACONFIG, WCMIO_SLING, WCMIO_WCM, WCMIO_CACONFIG, WCMIO_HANDLER)
        .afterSetUp(SETUP_CALLBACK)
        .build();
  }

  /**
   * Custom set up rules required in all unit tests.
   */
  private static final AemContextCallback SETUP_CALLBACK = new AemContextCallback() {
    @Override
    public void execute(AemContext context) throws PersistenceException, IOException {

      // context path strategy
      MockCAConfig.contextPathStrategyRootTemplate(context, AppTemplate.EDITORIAL_HOMEPAGE.getTemplatePath());

      // setup handler
      context.registerInjectActivateService(new MediaHandlerConfigImpl());
      context.registerInjectActivateService(new LinkHandlerConfigImpl());
      context.registerService(MediaFormatProvider.class, new MediaFormatProviderImpl());

      // import sample content
      context.load().json("/sample-content.json", CONTENT_ROOT);
      context.load().json("/sample-content-dam.json", DAM_ROOT);

      // set default current page
      context.currentPage("/content/wcm-io-samples/en");
    }
  };

}
