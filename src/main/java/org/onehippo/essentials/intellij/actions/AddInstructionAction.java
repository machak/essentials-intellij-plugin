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

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.xml.DomManager;

import org.onehippo.essentials.intellij.util.Util;

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
                    final VirtualFile instructionFile = Util.findInstructionFile(module).file;
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


    private void showError(final Project project, final String message) {
        final Notification notification = ERROR_GROUP.createNotification("Hippo Essentials", message, NotificationType.ERROR, null);
        notification.notify(project);
    }

}
