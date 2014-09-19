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

package org.onehippo.essentials.intellij.factory;


import org.jetbrains.annotations.NotNull;
import org.onehippo.essentials.intellij.project.HippoEssentialsProject;

import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.platform.ProjectTemplate;
import com.intellij.platform.ProjectTemplatesFactory;

/**
 * @version "$Id$"
 */
public class HippoProjectFactory extends ProjectTemplatesFactory {


    public static final String[] HIPPO_GROUP = new String[]{"Hippo"};

    @NotNull
    @Override
    public String[] getGroups() {
        return HIPPO_GROUP;
    }

    @NotNull
    @Override
    public ProjectTemplate[] createTemplates(final String s, final WizardContext wizardContext) {
        final ProjectTemplate[] projectTemplates = new ProjectTemplate[1];
        projectTemplates[0] = new HippoEssentialsProject();

        return projectTemplates;
    }
}
