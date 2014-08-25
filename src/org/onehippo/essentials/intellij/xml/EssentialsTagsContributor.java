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
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;

import static com.intellij.patterns.DomPatterns.domElement;
import static com.intellij.patterns.XmlPatterns.xmlAttributeValue;


/**
 * @version "$Id$"
 */
public class EssentialsTagsContributor extends PsiReferenceContributor {
    private static final Logger log = Logger.getInstance("#org.onehippo.essentials.intellij.xml.EssentialsTagsContributor");


    @Override
    public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
        registerSourceCompletion(registrar);
    }


    private void registerSourceCompletion(final PsiReferenceRegistrar registrar) {

        registrar.registerReferenceProvider(

                xmlAttributeValue().withLocalName("source"),
                //.withParent(withDom(domElement(XmlInstruction.class))),
                new EssentialsFileReferenceProvider());

    }


}
