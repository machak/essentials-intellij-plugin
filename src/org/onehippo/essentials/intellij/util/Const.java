/*
 * Copyright 2016 Hippo B.V. (http://www.onehippo.com)
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

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;

public final class Const {

    public static final String PLACEHOLDER_NAMESPACE = "{{namespace}}";
    public static final String PLACEHOLDER_TRANSLATION_ID = "{{translationId}}";
    public static final String PLACEHOLDER_PROJECT_ROOT = "{{projectRoot}}";
    public static final String PLACEHOLDER_DATE_REPO_YYYY_MM = "{{dateRepoYearMonth}}";
    public static final String PLACEHOLDER_DATE_FILE_YYYY_MM = "{{dateFileYearMonth}}";
    public static final String PLACEHOLDER_DATE_REPO_YYYY_MM_NEXT_MONTH = "{{dateRepoYearMonthNextMonth}}";
    public static final String PLACEHOLDER_DATE_FILE_YYYY_MM_NEXT_MONTH = "{{dateFileYearMonthNextMonth}}";
    public static final String PLACEHOLDER_DATE_REPO_YYYY_MM_NEXT_YEAR = "{{dateRepoYearMonthNextYear}}";
    public static final String PLACEHOLDER_DATE_FILE_YYYY_MM_NEXT_YEAR = "{{dateFileYearMonthNextYear}}";
    public static final String PLACEHOLDER_SITE_ROOT = "{{siteRoot}}";
    public static final String PLACEHOLDER_SITE_WEB_ROOT = "{{siteWebRoot}}";
    public static final String PLACEHOLDER_ESSENTIALS_ROOT = "{{essentialsRoot}}";
    public static final String PLACEHOLDER_SITE_WEB_INF_ROOT = "{{siteWebInfRoot}}";
    public static final String PLACEHOLDER_CMS_WEB_INF_ROOT = "{{cmsWebInfRoot}}";
    public static final String PLACEHOLDER_SITE_RESOURCES = "{{siteResources}}";
    public static final String PLACEHOLDER_WEBFILES_RESOURCES = "{{webfilesResources}}";
    public static final String PLACEHOLDER_WEBFILES_ROOT = "{{webfilesRoot}}";
    public static final String PLACEHOLDER_WEBFILES_FREEMARKER_ROOT = "{{freemarkerRoot}}";
    public static final String PLACEHOLDER_WEBFILES_CSS_ROOT = "{{webfilesCssRoot}}";
    public static final String PLACEHOLDER_WEBFILES_JS_ROOT = "{{webfilesJsRoot}}";
    public static final String PLACEHOLDER_WEBFILES_IMAGES_ROOT = "{{webfilesImagesRoot}}";
    public static final String PLACEHOLDER_WEBFILES_PREFIX = "{{webfilesPrefix}}";
    public static final String PLACEHOLDER_JSP_ROOT = "{{jspRoot}}";
    public static final String PLACEHOLDER_JAVASCRIPT_ROOT = "{{javascriptRoot}}";
    public static final String PLACEHOLDER_IMAGES_ROOT = "{{imagesRoot}}";
    public static final String PLACEHOLDER_CSS_ROOT = "{{cssRoot}}";
    public static final String PLACEHOLDER_CMS_ROOT = "{{cmsRoot}}";
    public static final String PLACEHOLDER_CMS_RESOURCES = "{{cmsResources}}";
    public static final String PLACEHOLDER_CMS_WEB_ROOT = "{{cmsWebRoot}}";
    public static final String PLACEHOLDER_SOURCE = "{{source}}";
    public static final String PLACEHOLDER_TARGET = "{{target}}";
    public static final String PLACEHOLDER_BEANS_PACKAGE = "{{beansPackage}}";
    public static final String PLACEHOLDER_COMPONENTS_PACKAGE = "{{componentsPackage}}";
    public static final String PLACEHOLDER_REST_PACKAGE = "{{restPackage}}";
    public static final String PLACEHOLDER_BEANS_FOLDER = "{{beansFolder}}";
    public static final String PLACEHOLDER_COMPONENTS_FOLDER = "{{componentsFolder}}";
    public static final String PLACEHOLDER_REST_FOLDER = "{{restFolder}}";
    public static final String PLACEHOLDER_TMP_FOLDER = "{{tmpFolder}}";
    public static final String PLACEHOLDER_SITE_OVERRIDE_FOLDER = "{{siteOverrideFolder}}";
    public static final String PLACEHOLDER_JCR_TODAY_DATE = "{{jcrDate}}";
    public static final String PLACEHOLDER_JCR_DATE_NEXT_MONTH = "{{jcrDateNextMonth}}";
    public static final String PLACEHOLDER_JCR_DATE_NEXT_YEAR = "{{jcrDateNextYear}}";
    public static final String PLACEHOLDER_CURRENT_YEAR = "{{currentYear}}";
    public static final String PLACEHOLDER_CURRENT_MONTH = "{{currentMonth}}";

    public static final Set<LookupElement> PLACEHOLDER_SET = new ImmutableSet.Builder<LookupElement>()

            .add(LookupElementBuilder.create(PLACEHOLDER_NAMESPACE))
            .add(LookupElementBuilder.create(PLACEHOLDER_TRANSLATION_ID))
            .add(LookupElementBuilder.create(PLACEHOLDER_PROJECT_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_DATE_REPO_YYYY_MM))
            .add(LookupElementBuilder.create(PLACEHOLDER_DATE_FILE_YYYY_MM))
            .add(LookupElementBuilder.create(PLACEHOLDER_DATE_REPO_YYYY_MM_NEXT_MONTH))
            .add(LookupElementBuilder.create(PLACEHOLDER_DATE_FILE_YYYY_MM_NEXT_MONTH))
            .add(LookupElementBuilder.create(PLACEHOLDER_DATE_REPO_YYYY_MM_NEXT_YEAR))
            .add(LookupElementBuilder.create(PLACEHOLDER_DATE_FILE_YYYY_MM_NEXT_YEAR))
            .add(LookupElementBuilder.create(PLACEHOLDER_SITE_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_SITE_WEB_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_ESSENTIALS_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_SITE_WEB_INF_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_CMS_WEB_INF_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_SITE_RESOURCES))
            .add(LookupElementBuilder.create(PLACEHOLDER_WEBFILES_RESOURCES))
            .add(LookupElementBuilder.create(PLACEHOLDER_WEBFILES_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_WEBFILES_FREEMARKER_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_WEBFILES_CSS_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_WEBFILES_JS_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_WEBFILES_IMAGES_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_WEBFILES_PREFIX))
            .add(LookupElementBuilder.create(PLACEHOLDER_JSP_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_JAVASCRIPT_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_IMAGES_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_CSS_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_CMS_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_CMS_RESOURCES))
            .add(LookupElementBuilder.create(PLACEHOLDER_CMS_WEB_ROOT))
            .add(LookupElementBuilder.create(PLACEHOLDER_SOURCE))
            .add(LookupElementBuilder.create(PLACEHOLDER_TARGET))
            .add(LookupElementBuilder.create(PLACEHOLDER_BEANS_PACKAGE))
            .add(LookupElementBuilder.create(PLACEHOLDER_COMPONENTS_PACKAGE))
            .add(LookupElementBuilder.create(PLACEHOLDER_REST_PACKAGE))
            .add(LookupElementBuilder.create(PLACEHOLDER_BEANS_FOLDER))
            .add(LookupElementBuilder.create(PLACEHOLDER_COMPONENTS_FOLDER))
            .add(LookupElementBuilder.create(PLACEHOLDER_REST_FOLDER))
            .add(LookupElementBuilder.create(PLACEHOLDER_TMP_FOLDER))
            .add(LookupElementBuilder.create(PLACEHOLDER_SITE_OVERRIDE_FOLDER))
            .add(LookupElementBuilder.create(PLACEHOLDER_JCR_TODAY_DATE))
            .add(LookupElementBuilder.create(PLACEHOLDER_JCR_DATE_NEXT_MONTH))
            .add(LookupElementBuilder.create(PLACEHOLDER_JCR_DATE_NEXT_YEAR))
            .add(LookupElementBuilder.create(PLACEHOLDER_CURRENT_YEAR))
            .add(LookupElementBuilder.create(PLACEHOLDER_CURRENT_MONTH))

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
