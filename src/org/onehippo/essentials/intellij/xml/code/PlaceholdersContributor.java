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

package org.onehippo.essentials.intellij.xml.code;


import java.util.regex.Pattern;

import com.google.common.base.Strings;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;

import org.jetbrains.annotations.NotNull;
import org.onehippo.essentials.intellij.util.Const;
import org.onehippo.essentials.intellij.util.Util;

public class PlaceholdersContributor extends CompletionContributor {

    private static final Pattern PATH = Pattern.compile("src/main/resources/", Pattern.LITERAL);

    public PlaceholdersContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(), new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
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
                        // do not complete for instruction file:
                        final PsiFile psiFile = parameters.getOriginalFile();
                        if (psiFile instanceof XmlFile) {
                            final XmlDocument document = ((XmlFile)psiFile).getDocument();
                            if (document != null) {
                                final XmlTag rootTag = document.getRootTag();
                                final String namespace = rootTag != null ? rootTag.getNamespace() : null;
                                if (!Strings.isNullOrEmpty(namespace) && namespace.equals("http://www.onehippo.org/essentials/instructions")) {
                                    return;
                                }
                            }
                        }
                        // only for resource folder:
                        final VirtualFile virtualFile = psiFile.getVirtualFile();
                        if (virtualFile != null) {
                            final String path = virtualFile.getPath();
                            if (path.lastIndexOf("/main/resources/") == -1) {
                                return;
                            }
                        }
                        resultSet.addAllElements(Const.PLACEHOLDER_SET);
                    }
                }
        );

    }
}
