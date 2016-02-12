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

import com.google.common.base.Strings;
import com.intellij.formatting.Wrap;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.FileIndex;
import com.intellij.openapi.roots.FileIndexFacade;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.testFramework.LightVirtualFile;

import org.jetbrains.annotations.NotNull;

import static org.jetbrains.jps.TimingLog.LOG;

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
                                final PsiFile psiFile = Util.getPsiFile(module.getProject(), file);
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

    /**
     * Tries to find PSI file for a virtual file and throws assertion error with debug info if it is null.
     */
    @NotNull
    public static PsiFile getPsiFile(@NotNull Project project, @NotNull VirtualFile file) {
        PsiManager psiManager = PsiManager.getInstance(project);
        PsiFile psi = psiManager.findFile(file);
        if (psi != null) return psi;
        FileType fileType = file.getFileType();
        FileViewProvider viewProvider = psiManager.findViewProvider(file);
        Document document = FileDocumentManager.getInstance().getDocument(file);
        boolean ignored = !(file instanceof LightVirtualFile) && FileTypeRegistry.getInstance().isFileIgnored(file);
        VirtualFile vDir = file.getParent();
        PsiDirectory psiDir = vDir == null ? null : PsiManager.getInstance(project).findDirectory(vDir);
        FileIndexFacade indexFacade = FileIndexFacade.getInstance(project);
        StringBuilder sb = new StringBuilder();
        sb.append("valid=").append(file.isValid()).
                append(" isDirectory=").append(file.isDirectory()).
                append(" hasDocument=").append(document != null).
                append(" length=").append(file.getLength());
        sb.append("\nproject=").append(project.getName()).
                append(" default=").append(project.isDefault()).
                append(" open=").append(project.isOpen());
        ;
        sb.append("\nfileType=").append(fileType.getName()).append("/").append(fileType.getClass().getName());
        sb.append("\nisIgnored=").append(ignored);
        sb.append(" underIgnored=").append(indexFacade.isUnderIgnored(file));
        sb.append(" inLibrary=").append(indexFacade.isInLibrarySource(file) || indexFacade.isInLibraryClasses(file));
        sb.append(" parentDir=").append(vDir == null ? "no-vfs" : vDir.isDirectory() ? "has-vfs-dir" : "has-vfs-file").
                append("/").append(psiDir == null ? "no-psi" : "has-psi");
        sb.append("\nviewProvider=").append(viewProvider == null ? "null" : viewProvider.getClass().getName());
        if (viewProvider != null) {
            List<PsiFile> files = viewProvider.getAllFiles();
            sb.append(" language=").append(viewProvider.getBaseLanguage().getID());
            sb.append(" physical=").append(viewProvider.isPhysical());
            sb.append(" rootCount=").append(files.size());
            for (PsiFile o : files) {
                sb.append("\n  root=").append(o.getLanguage().getID()).append("/").append(o.getClass().getName());
            }
        }
        throw new AssertionError();
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
