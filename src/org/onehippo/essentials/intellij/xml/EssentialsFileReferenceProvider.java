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
import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.impl.UrlPsiReference;
import com.intellij.psi.impl.smartPointers.SelfElementInfo;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.IdRefReference;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ProcessingContext;

/**
 * @version "$Id$"
 */
public class EssentialsFileReferenceProvider extends PsiReferenceProvider {

    private static final Logger log = Logger.getInstance("#org.onehippo.essentials.intellij.xml.EssentialsFileReferenceProvider");

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull final PsiElement element, @NotNull final ProcessingContext processingContext) {
        // disable for time being
        if (true) {
            return PsiReference.EMPTY_ARRAY;
        }
        final Module module = ModuleUtilCore.findModuleForPsiElement(element);
        if (module == null) {
            return PsiReference.EMPTY_ARRAY;
        }
        final GlobalSearchScope moduleScope = module.getModuleContentScope();
        final Project project = module.getProject();
        final Collection<VirtualFile> filesByName = FilenameIndex.getAllFilesByExt(project, "ftl", moduleScope);
        if (filesByName.isEmpty()) {
            return PsiReference.EMPTY_ARRAY;
        }
        final Set<PsiReference> refs = new HashSet<>();
        // TODO
        return refs.toArray(new PsiReference[refs.size()]);
    }
}
