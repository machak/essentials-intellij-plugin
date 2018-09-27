/*
 * Copyright 2015-2018 Hippo B.V. (http://www.onehippo.com)
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


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.onehippo.essentials.intellij.util.Const;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.roots.FileIndex;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import com.intellij.vcsUtil.VcsFileUtil;

public class FileReferenceContributor extends CompletionContributor {

    private static final Pattern PATH = Pattern.compile("../src/main/resources/", Pattern.LITERAL);

    public FileReferenceContributor() {
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
                        if (element instanceof XmlAttributeValue) {
                            final XmlAttribute attribute = (XmlAttribute)element.getParent();
                            final String name = attribute.getName();
                            if (!name.equals("source") && !name.equals("template")) {
                                if (name.equals("target")) {
                                    resultSet.addAllElements(Const.PLACEHOLDER_SET_ALL);
                                }
                                return;
                            }

                            final Module module = ModuleUtilCore.findModuleForPsiElement(element);
                            if (module == null) {
                                return;
                            }
                            final ModuleRootManager manager = ModuleRootManager.getInstance(module);
                            final FileIndex idx = manager.getFileIndex();
                            final VirtualFile[] sourceRoots = manager.getSourceRoots();
                            for (VirtualFile sourceRoot : sourceRoots) {
                                if (sourceRoot.getName().equals("resources")) {
                                    final VirtualFile[] children = sourceRoot.getChildren();
                                    for (VirtualFile child : children) {
                                        if (!child.getName().equals("META-INF")) {
                                            idx.iterateContentUnderDirectory(child, file -> {
                                                if (file.isDirectory()) {
                                                    return true;
                                                }
                                                final VirtualFile moduleFile = module.getModuleFile();
                                                if (moduleFile == null) {
                                                    return false;
                                                }
                                                final String path = VcsFileUtil.relativePath(moduleFile, file);
                                                resultSet.addElement(LookupElementBuilder.create(PATH.matcher(path).replaceAll(Matcher.quoteReplacement(""))));
                                                return true;
                                            });
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
