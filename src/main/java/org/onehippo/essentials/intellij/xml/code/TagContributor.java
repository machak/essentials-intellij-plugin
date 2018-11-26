/*
 * Copyright 2018 Hippo B.V. (http://www.onehippo.com)
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

package org.onehippo.essentials.intellij.xml.code;

import org.jetbrains.annotations.NotNull;
import org.onehippo.essentials.intellij.util.Const;
import org.onehippo.essentials.intellij.util.Util;

import com.google.common.base.Strings;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;

public class TagContributor extends CompletionContributor {

    public TagContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(), new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull final CompletionResultSet resultSet) {
                        final PsiElement position = parameters.getPosition();
                        if (position.getContext() == null) {
                            return;
                        }
                        final PsiElement element = position.getContext().getOriginalElement();
                        final Module module = ModuleUtilCore.findModuleForPsiElement(element);
                        if (module == null) {
                            return;
                        }
                        if (!Util.isEssentialsModule(module)) {
                            return;
                        }
                        final PsiFile psiFile = parameters.getOriginalFile();
                        if (psiFile instanceof XmlFile) {
                            final XmlDocument document = ((XmlFile) psiFile).getDocument();
                            if (document != null) {
                                final XmlTag rootTag = document.getRootTag();
                                final String namespace = rootTag != null ? rootTag.getNamespace() : null;
                                if (!Strings.isNullOrEmpty(namespace) && namespace.equals("http://www.onehippo.org/essentials/instructions")) {
                                    final PsiElement parent = element.getParent();
                                    final boolean isXmlTag = parent instanceof XmlTag;
                                    if (isXmlTag) {
                                        final String name = ((XmlTag) parent).getName();
                                        if (Const.TAG_INSTRUCTION_SET.equals(name)) {
                                            resultSet.addAllElements(Const.TAG_NAMES);
                                        } else if (Const.TAG_FILE.equals(name)
                                                || Const.TAG_XML.equals(name)
                                                || Const.TAG_FREEMARKER.equals(name)
                                                || Const.TAG_DIRECTORY.equals(name)
                                        ) {
                                            resultSet.addAllElements(Const.ATTR_XML_FREEMARKER_FILE);
                                        } else if (Const.TAG_EXECUTE.equals(name)) {
                                            resultSet.addAllElements(Const.ATTR_EXEC);
                                        } else if (Const.TAG_TRANSLATIONS.equals(name)) {
                                            resultSet.addAllElements(Const.ATTR_TRANSLATION);
                                        } else if (Const.TAG_CND.equals(name)) {
                                            resultSet.addAllElements(Const.ATTR_CND);
                                        } else if (Const.TAG_HST_BEAN_CLASSES.equals(name)) {
                                            resultSet.addAllElements(Const.ATTR_BEANS);
                                        } else if (Const.TAG_MAVEN_DEPENDENCY.equals(name)) {
                                            resultSet.addAllElements(Const.ATTR_MAVEN);
                                        }
                                    }
                                }
                            }
                        }


                    }
                }
        );

    }

}
