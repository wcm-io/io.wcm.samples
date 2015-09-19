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
package io.wcm.samples.handler.controller.navigation;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.wcm.samples.handler.business.navigation.NavigationManager;
import io.wcm.samples.handler.business.navigation.NavigationPageItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainNavPageLinkTest {

  @Mock
  private NavigationManager navigationManager;
  private NavigationPageItem navigationPageItem = new NavigationPageItem("dummy");

  private MainNavPageLink underTest;

  @Before
  public void setUp() throws Exception {
    when(navigationManager.getMainNavigation(anyInt())).thenReturn(navigationPageItem);
  }

  @Test
  public void testGetRoot() throws Exception {
    underTest = new MainNavPageLink(navigationManager, 5);
    verify(navigationManager).getMainNavigation(5);
    assertSame(navigationPageItem, underTest.getRoot());
  }

}
