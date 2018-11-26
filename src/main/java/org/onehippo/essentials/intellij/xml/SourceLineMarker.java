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

package org.onehippo.essentials.intellij.xml;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.lang.Language;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.smartPointers.SelfElementInfo;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;

public class SourceLineMarker implements LineMarkerProvider {


    public static final String NS = "http://www.onehippo.org/essentials/instructions";
    private static final Logger log = Logger.getInstance("#org.onehippo.essentials.intellij.xml.SourceLineMarker");

    private static final Icon REFERENCE_OK = IconLoader.getIcon("/icons/essentials_ok_reference.png");
    private static final Icon REFERENCE_ERROR = IconLoader.getIcon("/icons/essentials_reference_error.png");

    public static final Set<String> OUR_TAGS = new ImmutableSet.Builder<String>()
            .add("file")
            .add("xml")
            .add("folder")
            .add("freemarker")
            .build();

    public static final String[] TOOLTIP_OPEN_FILE = new String[]{"Open file"};
    public static final String[] TOOLTIP_NOT_FOUND = new String[]{"File not found"};


    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement element) {

        if (!(element instanceof XmlAttribute)) {
            return null;
        }

        final XmlAttribute xmlAttribute = (XmlAttribute) element;
        final PsiElement markerTarget = xmlAttribute.getPrevSibling();
        final String attributeName = xmlAttribute.getLocalName();
        if (!"source".equals(attributeName)) {
            return null;
        }

        final String sourcePath = xmlAttribute.getValue();
        if (Strings.isNullOrEmpty(sourcePath)) {
            return null;
        }

        final String namespace = xmlAttribute.getParent().getNamespace();
        if (!NS.equals(namespace)) {
            return null;
        }

        final Module module = ModuleUtilCore.findModuleForPsiElement(element);
        if (module == null) {
            return null;
        }

        final PsiFile psiFile = element.getContainingFile();
        final VirtualFile[] sourceRoots = ModuleRootManager.getInstance(module).getSourceRoots();
        final Project project = module.getProject();
        for (VirtualFile sourceRoot : sourceRoots) {
            final VirtualFile targetFile = sourceRoot.findFileByRelativePath(sourcePath);
            if (targetFile != null) {
                final PsiFile xml = SelfElementInfo.restoreFileFromVirtual(targetFile, project, Language.ANY);
                if (element instanceof XmlTag) {
                    return null;
                }
                return MarkerNavigator.create(markerTarget, new PsiElement[]{xml}, REFERENCE_OK, TOOLTIP_OPEN_FILE);
            }

        }
        return MarkerNavigator.create(markerTarget, new PsiElement[]{psiFile}, REFERENCE_ERROR, TOOLTIP_NOT_FOUND);


    }

    @Override
    public void collectSlowLineMarkers(List<PsiElement> elements, Collection<LineMarkerInfo> result) {

    }

}
