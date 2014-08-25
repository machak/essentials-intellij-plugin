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

package org.onehippo.essentials.intellij.xml.intentions;

import org.jetbrains.annotations.NotNull;
import org.onehippo.essentials.intellij.xml.SourceLineMarker;

import com.google.common.base.Strings;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.IncorrectOperationException;

/**
 * @version "$Id$"
 */
public class CreateFileIntention extends PsiElementBaseIntentionAction {

    private static final Logger log = Logger.getInstance("#org.onehippo.essentials.intellij.xml.intentions.CreateFileIntention");

    @Override
    public void invoke(@NotNull final Project project, final Editor editor, @NotNull final PsiElement element) throws IncorrectOperationException {

        log.error("project {}" + project);


    }

    @Override
    public boolean isAvailable(@NotNull final Project project, final Editor editor, @NotNull final PsiElement element) {

        if (!(element instanceof XmlToken)) {
            return false;
        }

        final PsiElement parent = element.getParent();
        if (!(parent instanceof XmlAttribute)) {
            return false;
        }
        final XmlAttribute attribute = (XmlAttribute) parent;
        final XmlTag ourParent = attribute.getParent();
        if(ourParent==null){
            return false;
        }
        final String tagName = ourParent.getLocalName();
        if (!SourceLineMarker.OUR_TAGS.contains(tagName)) {
            return false;
        }
        final String source = attribute.getValue();
        if (Strings.isNullOrEmpty(source)) {
            return false;
        }
        return true;
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "XML";
    }
}
