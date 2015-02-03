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

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.impl.UrlPsiReference;
import com.intellij.util.ProcessingContext;

/**
 * @version "$Id$"
 */
public class EssentialsFileReferenceProvider extends PsiReferenceProvider {

    private static final Logger log = Logger.getInstance("#org.onehippo.essentials.intellij.xml.EssentialsFileReferenceProvider");



    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull final PsiElement psiElement, @NotNull final ProcessingContext processingContext) {
        final PsiReference[] psiReferences = new PsiReference[1];
        psiReferences[0] =  new UrlPsiReference(psiElement);

        return psiReferences;
    }
}
