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

package org.onehippo.essentials.intellij.actions;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.intellij.conversion.CannotConvertException;
import com.intellij.execution.CantRunException;
import com.intellij.javaee.model.xml.web.WebApp;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.FileIndex;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.controlFlow.Instruction;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.indexing.FileContentImpl;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomFileDescription;
import com.intellij.util.xml.DomManager;
import com.intellij.util.xml.ModelMerger;

import org.onehippo.essentials.intellij.xml.model.InstructionSet;
import org.onehippo.essentials.intellij.xml.model.Instructions;

import static com.intellij.icons.AllIcons.Nodes.Servlet;

public class AddInstructionAction extends AnAction {


    public static final NotificationGroup ERROR_GROUP =
            new NotificationGroup("Hippo essentials error messages", NotificationDisplayType.BALLOON, true);

    @Override
    public void actionPerformed(final AnActionEvent event) {
        final Project project = event.getProject();
        if (project != null) {
            final Object data = event.getDataContext().getData(DataKeys.VIRTUAL_FILE.getName());
            if (data instanceof VirtualFile) {
                final VirtualFile file = (VirtualFile)data;
                final Module module = ModuleUtilCore.findModuleForFile(file, project);
                if (module != null) {
                    final VirtualFile instructionFile = findInstructionFile(module).file;
                    if (instructionFile != null) {
                        System.out.println("instructionFile = " + instructionFile);
                        final DomManager manager = DomManager.getDomManager(project);
                        // TODO...insert
                        return;
                    } else {
                        showError(project, "Couldn't find instruction.xml file.");
                    }

                } else {
                    showError(project, "File not inside module");
                }

            } else {
                showError(project, "No file selected");
            }

        }
    }

    private Wrap findInstructionFile(final Module module) {
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




    private void showError(final Project project, final String message) {
        final Notification notification = ERROR_GROUP.createNotification("Hippo Essentials", message, NotificationType.ERROR, null);
        notification.notify(project);
    }

    private static class Wrap {
        public Wrap(final VirtualFile file) {
            this.file = file;
        }

        public VirtualFile file;

        public boolean found() {
            return file != null;
        }
    }
}
