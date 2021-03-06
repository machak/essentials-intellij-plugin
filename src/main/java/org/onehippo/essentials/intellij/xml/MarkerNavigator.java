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

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.ide.util.PsiElementListCellRenderer;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.ui.popup.IPopupChooserBuilder;
import com.intellij.openapi.ui.popup.PopupChooserBuilder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.Function;
import com.intellij.util.PsiNavigateUtil;


public final class MarkerNavigator {
    private MarkerNavigator() {
    }

    public static LineMarkerInfo<PsiElement> create(@NotNull PsiElement element, @NotNull final PsiElement[] targets, Icon icon, @Nullable final String[] tooltips) {
        return new LineMarkerInfo<>(element,
                element.getTextRange(),
                icon,
                Pass.UPDATE_ALL,
                tooltips == null || tooltips.length == 0 ? null : (Function<PsiElement, String>) psiElement -> {
                    // only one tooltip
                    if (tooltips.length == 1) {
                        return StringUtil.escapeXml(tooltips[0]);
                    }
                    // multiple tooltips
                    StringBuilder sb = new StringBuilder();
                    for (String tooltip : tooltips) {
                        if (sb.length() > 0) {
                            sb.append("<hr>");
                        }
                        sb.append("<div>").append(StringUtil.escapeXml(tooltip)).append("</div>");
                    }
                    return sb.toString();
                },
                (e, elt) -> openTargets(e, targets, "Select Target", new DefaultPsiElementCellRenderer()),
                GutterIconRenderer.Alignment.LEFT);
    }

    public static void openTargets(MouseEvent e, PsiElement[] targets, String title, ListCellRenderer listRenderer) {
        if (targets.length == 0) {
            return;
        }
        if (targets.length == 1) {
            PsiNavigateUtil.navigate(targets[0]);
        } else {
            final JList<PsiElement> list = new com.intellij.ui.components.JBList<>(targets);
            //noinspection unchecked
            list.setCellRenderer(listRenderer);

            final IPopupChooserBuilder builder = new PopupChooserBuilder(list);
            if (listRenderer instanceof PsiElementListCellRenderer) {
                //noinspection unchecked
                ((PsiElementListCellRenderer) listRenderer).installSpeedSearch(builder);
            }

            builder.setTitle(title).setMovable(true).
                    setItemChosenCallback((con) -> {
                        int[] ids = list.getSelectedIndices();
                        if (ids == null || ids.length == 0) {
                            return;
                        }
                        final List<PsiElement> psiElements = list.getSelectedValuesList();

                        for (PsiElement element : psiElements) {
                            PsiNavigateUtil.navigate(element);
                        }
                    }).createPopup().
                    show(new RelativePoint(e));
        }
    }

}