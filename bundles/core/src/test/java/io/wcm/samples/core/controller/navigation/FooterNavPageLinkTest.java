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
package io.wcm.samples.core.controller.navigation;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.samples.core.business.navigation.NavigationManager;
import io.wcm.samples.core.business.navigation.NavigationPageItem;

@ExtendWith(MockitoExtension.class)
class FooterNavPageLinkTest {

  @Mock
  private NavigationManager navigationManager;
  private NavigationPageItem navigationPageItem = new NavigationPageItem("dummy");

  private FooterNavPageLink underTest;

  @BeforeEach
  void setUp() {
    when(navigationManager.getFooterNavigation()).thenReturn(navigationPageItem);
    underTest = new FooterNavPageLink(navigationManager);
  }

  @Test
  void testGetRoot() {
    assertSame(navigationPageItem, underTest.getRoot());
  }

}
