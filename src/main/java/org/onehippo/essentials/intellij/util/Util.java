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

import com.google.common.base.Strings;
import com.intellij.formatting.Wrap;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.FileIndex;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;

public final class Util {

    private Util() {

    }

    public static boolean isEssentialsModule(final Module module) {
        return findInstructionFile(module).file != null;
    }

    public static Wrap findInstructionFile(final Module module) {
        final ModuleRootManager manager = ModuleRootManager.getInstance(module);
        final FileIndex idx = manager.getFileIndex();
        final VirtualFile[] sourceRoots = manager.getSourceRoots();
        final Wrap wrap = new Wrap(null);
        for (VirtualFile sourceRoot : sourceRoots) {
            if (wrap.found()) {
                return wrap;
            }
            if (sourceRoot.getName().equals("resources")) {
                final VirtualFile[] children = sourceRoot.getChildren();

                for (VirtualFile child : children) {
                    if (wrap.found()) {
                        return wrap;
                    }
                    idx.iterateContentUnderDirectory(child, new ContentIterator() {
                        @Override
                        public boolean processFile(final VirtualFile file) {
                            if (wrap.found()) {
                                return false;
                            }
                            if (file.isDirectory()) {
                                return true;
                            }
                            if (file.getFileType() == StdFileTypes.XML) {
                                final PsiFile psiFile = PsiUtilCore.getPsiFile(module.getProject(), file);
                                if (psiFile instanceof XmlFile) {
                                    final XmlDocument document = ((XmlFile)psiFile).getDocument();

                                    if (document != null) {
                                        final XmlTag rootTag = document.getRootTag();
                                        final String namespace = rootTag != null ? rootTag.getNamespace() : null;
                                        if (!Strings.isNullOrEmpty(namespace) && namespace.equals("http://www.onehippo.org/essentials/instructions")) {
                                            wrap.file = file;
                                            return false;
                                        }
                                    }
                                }
                            }
                            return true;
                        }
                    });
                }
            }
        }
        return wrap;
    }

    public static class Wrap {
        public Wrap(final VirtualFile file) {
            this.file = file;
        }

        public VirtualFile file;

        public boolean found() {
            return file != null;
        }
    }
}
