/*
 * Copyright 2014-2018 Hippo B.V. (http://www.onehippo.com)
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

package org.onehippo.essentials.intellij.xml.intentions;

import java.io.File;

import org.jetbrains.annotations.NotNull;
import org.onehippo.essentials.intellij.xml.SourceLineMarker;

import com.google.common.base.Strings;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.IncorrectOperationException;


public class CreateFileIntention extends PsiElementBaseIntentionAction {

    private static final Logger log = Logger.getInstance("#org.onehippo.essentials.intellij.xml.intentions.CreateFileIntention");

    @NotNull
    @Override
    public String getText() {
        return "Create missing instruction file";
    }


    @Override
    public void invoke(@NotNull final Project project, final Editor editor, @NotNull final PsiElement element) throws IncorrectOperationException {

        final String fileName = getFileName(project, editor, element);
        if (Strings.isNullOrEmpty(fileName)) {
            return;
        }
        final Module module = ModuleUtilCore.findModuleForPsiElement(element);
        if (module == null) {
            return;
        }
        VirtualFile ourResource = null;
        final VirtualFile[] sourceRoots = ModuleRootManager.getInstance(module).getSourceRoots();
        for (VirtualFile sourceRoot : sourceRoots) {
            ourResource = sourceRoot;
            if (sourceRoot.getName().equals("resources")) {
                break;
            }

        }

        if (ourResource == null) {
            return;
        }
        final String filePath = ourResource.getCanonicalPath() + File.separator + fileName;

        final Application application = ApplicationManager.getApplication();
        application.runWriteAction(() -> {

            try {
                FileUtil.createIfDoesntExist(new File(filePath));
            } finally {

                application.invokeLater(() -> {
                    final VirtualFile file = VirtualFileManager.getInstance().refreshAndFindFileByUrl("file://" + filePath);
                    if (file != null) {
                        final OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(project, file);
                        FileEditorManager.getInstance(project).openTextEditor(openFileDescriptor, true);
                    }

                    });
            }

        });
    }

    private String getFileName(@NotNull final Project project, final Editor editor, @NotNull final PsiElement element) {
        if (!(element instanceof XmlToken)) {
            return null;
        }

        final PsiElement parent = element.getParent();
        final boolean isAttribute = parent instanceof XmlAttribute;
        final boolean isValue = parent instanceof XmlAttributeValue;
        if (!isAttribute && !isValue) {
            return null;
        }
        String ourValue = null;
        XmlAttribute attribute;
        if (isValue) {
            ourValue = ((XmlAttributeValue) parent).getValue();
            attribute = (XmlAttribute) parent.getParent();
        } else {
            attribute = (XmlAttribute) parent;
            final XmlAttributeValue valueElement = attribute.getValueElement();
            if (valueElement != null) {
                ourValue = valueElement.getValue();
            }
        }
        return ourValue;
    }

    @Override
    public boolean isAvailable(@NotNull final Project project, final Editor editor, @NotNull final PsiElement element) {

        if (!(element instanceof XmlToken)) {
            return false;
        }

        final PsiElement parent = element.getParent();
        final boolean isAttribute = parent instanceof XmlAttribute;
        final boolean isValue = parent instanceof XmlAttributeValue;
        if (!isAttribute && !isValue) {
            return false;
        }
        String ourValue = "";
        XmlAttribute attribute;
        if (isValue) {
            ourValue = ((XmlAttributeValue) parent).getValue();
            attribute = (XmlAttribute) parent.getParent();
        } else {
            attribute = (XmlAttribute) parent;
            final XmlAttributeValue valueElement = attribute.getValueElement();
            if (valueElement != null) {
                ourValue = valueElement.getValue();
            }
        }
        if (Strings.isNullOrEmpty(ourValue)) {
            return false;
        }

        final XmlTag ourParent = attribute.getParent();
        if (ourParent == null) {
            return false;
        }
        final String tagName = ourParent.getLocalName();
        if (!SourceLineMarker.OUR_ATTRIBUTES.contains(tagName)) {
            return false;
        }
        final String source = attribute.getValue();
        return !Strings.isNullOrEmpty(source);
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "XML";
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }
}
