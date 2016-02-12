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

import org.jetbrains.annotations.NotNull;

import com.intellij.lang.Language;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.impl.smartPointers.SelfElementInfo;
import com.intellij.psi.impl.source.resolve.reference.impl.PsiMultiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceHelper;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceOwner;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceUtil;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.URLReference;
import com.intellij.psi.search.scope.packageSet.PackageSetBase;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.refactoring.move.FileReferenceContextUtil;
import com.intellij.util.ProcessingContext;
import com.yourkit.util.Strings;

import static org.onehippo.essentials.intellij.xml.SourceLineMarker.TOOLTIP_NOT_FOUND;
import static org.onehippo.essentials.intellij.xml.SourceLineMarker.TOOLTIP_OPEN_FILE;

public class EssentialsFileReferenceProvider extends PsiReferenceProvider {

    private static final Logger log = Logger.getInstance("#org.onehippo.essentials.intellij.xml.EssentialsFileReferenceProvider");
    private static final PsiReference[] EMPTY_REFS = new PsiReference[0];



    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull final PsiElement original, @NotNull final ProcessingContext processingContext) {
        if (original instanceof XmlAttributeValue) {
            final String value = ((XmlAttributeValue)original).getValue();
            if (Strings.isEmptyOrNull(value)) {
                return EMPTY_REFS;
            }
            final Module module = ModuleUtilCore.findModuleForPsiElement(original);
            if (module == null) {
                return EMPTY_REFS;
            }
            final VirtualFile[] sourceRoots = ModuleRootManager.getInstance(module).getSourceRoots();
            for (VirtualFile sourceRoot : sourceRoots) {
                final VirtualFile targetFile = sourceRoot.findFileByRelativePath(value);
                if (targetFile != null) {
                    final Project project = module.getProject();
//                    final PsiFile file = SelfElementInfo.restoreFileFromVirtual(targetFile, project, Language.ANY);
                    final PsiFile file = PackageSetBase.getPsiFile(targetFile, project);
                    if (file == null) {
                        return EMPTY_REFS;
                    }
                    final PsiReference[] psiReferences = new PsiReference[1];
                    final PsiReference psiReference = FileReferenceUtil.findFileReference(file);
                    /*final PsiFileReference ourReference = FileReference.findFileReference(psiReference);*/
                    psiReferences[0] = psiReference;
                    return psiReferences;
                }
            }
        }
        return EMPTY_REFS;

    }
}
