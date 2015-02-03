/*
 * Copyright 2014 Hippo B.V. (http://www.onehippo.com)
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

package org.onehippo.essentials.intellij.xml;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableSet;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.lang.Language;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.smartPointers.SelfElementInfo;
import com.intellij.psi.xml.XmlTag;

/**
 * @version "$Id$"
 */
public class SourceLineMarker implements LineMarkerProvider {


    public static final String NS = "http://www.onehippo.org/essentials/instructions";
    private static final Logger log = Logger.getInstance("#org.onehippo.essentials.intellij.xml.SourceLineMarker");

    private static final Icon REFERENCE_OK = IconLoader.getIcon("/essentials_ok_reference.png");
    private static final Icon REFERENCE_ERROR = IconLoader.getIcon("/essentials_reference_error.png");

    public static final Set<String> OUR_TAGS =  new ImmutableSet.Builder<String>()
            .add("file")
            .add("xml")
            .add("folder")
            .add("freemarker")
            .build();

    public static final String[] TOOLTIP_OPEN_FILE = new String[]{"Open file"};
    public static final String[] TOOLTIP_NOT_FOUND = new String[]{"File not found"};

    @Override
    public LineMarkerInfo<PsiElement> getLineMarkerInfo(@NotNull PsiElement element) {

        if (!(element instanceof XmlTag)) {
            return null;
        }

        final XmlTag xmlTag = (XmlTag) element;
        final String tagName = xmlTag.getLocalName();
        if (!OUR_TAGS.contains(tagName)) {
            return null;
        }


        final String namespace = xmlTag.getNamespace();
        if (!NS.equals(namespace)) {
            return null;
        }


        final String source = tagName.equals("folder") ? xmlTag.getAttributeValue("template"):xmlTag.getAttributeValue("source");
        if (source == null) {
            return null;
        }


        final Module module = ModuleUtilCore.findModuleForPsiElement(element);
        if (module == null) {
            return null;
        }

        final PsiFile psiFile = element.getContainingFile();
        final VirtualFile[] sourceRoots = ModuleRootManager.getInstance(module).getSourceRoots();
        for (VirtualFile sourceRoot : sourceRoots) {
            final VirtualFile targetFile = sourceRoot.findFileByRelativePath(source);
            if (targetFile != null) {
                PsiFile target = SelfElementInfo.restoreFileFromVirtual(targetFile, module.getProject(), Language.ANY);
                return MarkerNavigator.create(element, new PsiElement[]{target}, REFERENCE_OK, TOOLTIP_OPEN_FILE);
            }

        }
        return MarkerNavigator.create(element, new PsiElement[]{psiFile}, REFERENCE_ERROR, TOOLTIP_NOT_FOUND);


    }

    @Override
    public void collectSlowLineMarkers(List<PsiElement> elements, Collection<LineMarkerInfo> result) {

    }

}
