/*
 * Copyright 2016-2018 Hippo B.V. (http://www.onehippo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onehippo.essentials.intellij.util;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;

public final class Const {


    public static final String NAMESPACE = "namespace";

    public static final String BEANS_PACKAGE = "beansPackage";
    public static final String PROJECT_PACKAGE = "projectPackage";
    public static final String COMPONENTS_PACKAGE = "componentsPackage";
    public static final String REST_PACKAGE = "restPackage";

    public static final String TRANSLATION_ID = "translationId";

    public static final String DATE_CURRENT_YEAR = "currentYear";
    public static final String DATE_CURRENT_MONTH = "currentMonth";
    public static final String DATE_CURRENT_YYYY = "dateCurrentYear";
    public static final String DATE_CURRENT_MM = "dateCurrentMonth";
    public static final String DATE_NEXT_YYYY = "dateNextYear";
    public static final String DATE_NEXT_MM = "dateNextMonth";
    public static final String DATE_JCR_CURRENT = "jcrDate";
    public static final String DATE_JCR_NEXT_MONTH = "jcrDateNextMonth";
    public static final String DATE_JCR_NEXT_YEAR = "jcrDateNextYear";

    public static final String PROJECT_ROOT = "projectRoot";
    public static final String SITE_ROOT = "siteRoot";
    public static final String SITE_WEB_ROOT = "siteWebRoot";
    public static final String SITE_WEB_INF_ROOT = "siteWebInfRoot";
    public static final String CMS_WEB_INF_ROOT = "cmsWebInfRoot";
    public static final String SITE_RESOURCES = "siteResources";
    public static final String JSP_ROOT = "jspRoot";
    public static final String JAVASCRIPT_ROOT = "javascriptRoot";
    public static final String IMAGES_ROOT = "imagesRoot";
    public static final String BEANS_FOLDER = "beansFolder";
    public static final String COMPONENTS_FOLDER = "componentsFolder";
    public static final String REST_FOLDER = "restFolder";
    public static final String SITE_OVERRIDE_FOLDER = "siteOverrideFolder";
    public static final String CSS_ROOT = "cssRoot";
    public static final String CMS_ROOT = "cmsRoot";
    public static final String CMS_RESOURCES = "cmsResources";
    public static final String CMS_WEB_ROOT = "cmsWebRoot";
    public static final String WEBFILES_RESOURCES = "webfilesResources";
    public static final String WEBFILES_ROOT = "webfilesRoot";
    public static final String WEBFILES_FREEMARKER_ROOT = "freemarkerRoot";
    public static final String WEBFILES_CSS_ROOT = "webfilesCssRoot";
    public static final String WEBFILES_JS_ROOT = "webfilesJsRoot";
    public static final String WEBFILES_IMAGES_ROOT = "webfilesImagesRoot";
    public static final String WEBFILES_PREFIX = "webfilesPrefix";
    public static final String ESSENTIALS_ROOT = "essentialsRoot";

    public static final Set<LookupElement> PLACEHOLDER_SET = new ImmutableSet.Builder<LookupElement>()

            .add(LookupElementBuilder.create(NAMESPACE))
            .add(LookupElementBuilder.create(BEANS_PACKAGE))
            .add(LookupElementBuilder.create(PROJECT_PACKAGE))
            .add(LookupElementBuilder.create(COMPONENTS_PACKAGE))
            .add(LookupElementBuilder.create(REST_PACKAGE))
            .add(LookupElementBuilder.create(TRANSLATION_ID))
            .add(LookupElementBuilder.create(DATE_CURRENT_YEAR))
            .add(LookupElementBuilder.create(DATE_CURRENT_MONTH))
            .add(LookupElementBuilder.create(DATE_CURRENT_YYYY))
            .add(LookupElementBuilder.create(DATE_CURRENT_MM))
            .add(LookupElementBuilder.create(DATE_NEXT_YYYY))
            .add(LookupElementBuilder.create(DATE_NEXT_MM))
            .add(LookupElementBuilder.create(DATE_JCR_CURRENT))
            .add(LookupElementBuilder.create(DATE_JCR_NEXT_MONTH))
            .add(LookupElementBuilder.create(DATE_JCR_NEXT_YEAR))
            .add(LookupElementBuilder.create(PROJECT_ROOT))
            .add(LookupElementBuilder.create(SITE_ROOT))
            .add(LookupElementBuilder.create(SITE_WEB_ROOT))
            .add(LookupElementBuilder.create(SITE_WEB_INF_ROOT))
            .add(LookupElementBuilder.create(CMS_WEB_INF_ROOT))
            .add(LookupElementBuilder.create(SITE_RESOURCES))
            .add(LookupElementBuilder.create(JSP_ROOT))
            .add(LookupElementBuilder.create(JAVASCRIPT_ROOT))
            .add(LookupElementBuilder.create(IMAGES_ROOT))
            .add(LookupElementBuilder.create(BEANS_FOLDER))
            .add(LookupElementBuilder.create(COMPONENTS_FOLDER))
            .add(LookupElementBuilder.create(REST_FOLDER))
            .add(LookupElementBuilder.create(SITE_OVERRIDE_FOLDER))
            .add(LookupElementBuilder.create(CSS_ROOT))
            .add(LookupElementBuilder.create(CMS_ROOT))
            .add(LookupElementBuilder.create(CMS_RESOURCES))
            .add(LookupElementBuilder.create(CMS_WEB_ROOT))
            .add(LookupElementBuilder.create(WEBFILES_RESOURCES))
            .add(LookupElementBuilder.create(WEBFILES_ROOT))
            .add(LookupElementBuilder.create(WEBFILES_FREEMARKER_ROOT))
            .add(LookupElementBuilder.create(WEBFILES_CSS_ROOT))
            .add(LookupElementBuilder.create(WEBFILES_JS_ROOT))
            .add(LookupElementBuilder.create(WEBFILES_IMAGES_ROOT))
            .add(LookupElementBuilder.create(WEBFILES_PREFIX))
            .add(LookupElementBuilder.create(ESSENTIALS_ROOT))

            .build();

    public static final Set<LookupElement> PLACEHOLDER_SET_ALL = new ImmutableSet.Builder<LookupElement>()

            .addAll(PLACEHOLDER_SET)
            .add(LookupElementBuilder.create("/hst:hst/hst:configurations/hst:default/hst:catalog/essentials-catalog"))
            .add(LookupElementBuilder.create("/hippo:configuration/hippo:queries/hippo:templates"))
            .add(LookupElementBuilder.create("/hippo:namespaces/"))
            .add(LookupElementBuilder.create("/content/gallery/"))
            .add(LookupElementBuilder.create("/hst:hst/hst:configurations/{{namespace}}/hst:workspace/hst:containers"))
            .add(LookupElementBuilder.create("/hst:hst/hst:configurations/{{namespace}}/hst:templates"))
            .add(LookupElementBuilder.create("/hst:hst/hst:configurations/hst:default/hst:templates"))
            .add(LookupElementBuilder.create("/hst:hst/hst:configurations/{{namespace}}/hst:pages"))
            .add(LookupElementBuilder.create("/content/documents/{{namespace}}/"))
            .add(LookupElementBuilder.create("{{freemarkerRoot}}/hstdefault/"))
            .add(LookupElementBuilder.create("{{freemarkerRoot}}/{{namespace}}/"))

            .build();




    private Const() {
    }
}
