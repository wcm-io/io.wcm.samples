<%--
  #%L
  wcm.io
  %%
  Copyright (C) 2021 wcm.io
  %%
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  #L%
  --%>
<%@page import="java.util.List"%>
<%@page import="org.apache.sling.api.resource.Resource"%>
<%@page import="com.adobe.granite.ui.components.Config"%>
<%@page import="com.adobe.granite.ui.components.ComponentHelper.Options"%>
<%@page import="com.adobe.granite.ui.components.Tag"%>
<%@page import="com.day.cq.commons.jcr.JcrConstants"%>
<%@page import="io.wcm.handler.link.LinkNameConstants"%>
<%@page import="io.wcm.sling.commons.resource.ImmutableValueMap"%>
<%@page import="io.wcm.wcm.ui.granite.resource.GraniteUiSyntheticResource"%>
<%@page import="io.wcm.samples.core.config.WikipediaLinkType"%>
<%@include file="/apps/wcm-io/handler/link/components/granite/global/global.jsp" %><%--###

Dialog properties for "wikipedia" link type (example for custom link type).

###--%><%

Tag tag = cmp.consumeTag();
Config cfg = cmp.getConfig();

String namePrefix = cfg.get("namePrefix", "./");
boolean required = cfg.get("required", false);

Resource container = GraniteUiSyntheticResource.wrap(resource);
Resource items = GraniteUiSyntheticResource.child(container, "items", JcrConstants.NT_UNSTRUCTURED);

// wiki page name
ImmutableValueMap.Builder linkWikipediaRefProps = ImmutableValueMap.builder()
    .put("name", namePrefix + WikipediaLinkType.PN_LINK_WIKIPEDIA_REF)
    .put("fieldLabel", "io.wcm.samples.components.global.linktype.wikipedia.linkWikipediaRef.fieldLabel")
    .put("fieldDescription", "io.wcm.samples.components.global.linktype.wikipedia.linkWikipediaRef.fieldDescription")
    .put("required", required);
GraniteUiSyntheticResource.child(items, WikipediaLinkType.PN_LINK_WIKIPEDIA_REF, "granite/ui/components/coral/foundation/form/textfield",
    linkWikipediaRefProps.build());

// wikipedia language
ImmutableValueMap.Builder linkWikipediaLanguageProps = ImmutableValueMap.builder()
    .put("name", namePrefix + WikipediaLinkType.PN_LINK_WIKIPEDIA_LANGUAGE)
    .put("fieldLabel", "io.wcm.samples.components.global.linktype.wikipedia.linkWikipediaLanguage.fieldLabel")
    .put("fieldDescription", "io.wcm.samples.components.global.linktype.wikipedia.linkWikipediaLanguage.fieldDescription")
    .put("required", required);
Resource languageSelect = GraniteUiSyntheticResource.child(items, WikipediaLinkType.PN_LINK_WIKIPEDIA_LANGUAGE, "granite/ui/components/coral/foundation/form/select",
    linkWikipediaLanguageProps.build());
Resource languageItems = GraniteUiSyntheticResource.child(languageSelect, "items", JcrConstants.NT_UNSTRUCTURED);
for (String language : List.of("en", "de")) {
  GraniteUiSyntheticResource.child(languageItems, language, JcrConstants.NT_UNSTRUCTURED,
      ImmutableValueMap.builder()
      .put("value", language)
      .put("text", language)
      .build());
}

cmp.include(container, "granite/ui/components/coral/foundation/container", new Options().tag(tag));

%>