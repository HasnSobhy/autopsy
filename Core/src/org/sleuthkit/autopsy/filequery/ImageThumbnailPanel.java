/*
 * Autopsy
 *
 * Copyright 2019 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.filequery;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.openide.util.NbBundle;

/**
 * Class which displays a thumbnail and information for an image file.
 */
public class ImageThumbnailPanel extends javax.swing.JPanel implements ListCellRenderer<ImageThumbnailWrapper> {

    private static final long serialVersionUID = 1L;
    private static final Color SELECTION_COLOR = new Color(0, 120, 215);
    private static final int MAX_NAME_STRING = 30;

    /**
     * Creates new form ImageThumbnailPanel
     */
    public ImageThumbnailPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel thumbnailPanel = new javax.swing.JPanel();
        thumbnailLabel = new javax.swing.JLabel();
        fileSizeLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        isDeletedLabel = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();

        setToolTipText("");

        thumbnailPanel.setToolTipText("");
        thumbnailPanel.setLayout(new java.awt.GridBagLayout());
        thumbnailPanel.add(thumbnailLabel, new java.awt.GridBagConstraints());

        fileSizeLabel.setToolTipText("");

        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, org.openide.util.NbBundle.getMessage(ImageThumbnailPanel.class, "ImageThumbnailPanel.nameLabel.text")); // NOI18N
        nameLabel.setToolTipText("");
        nameLabel.setMaximumSize(new java.awt.Dimension(159, 12));
        nameLabel.setMinimumSize(new java.awt.Dimension(159, 12));
        nameLabel.setPreferredSize(new java.awt.Dimension(159, 12));

        isDeletedLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/file-icon-deleted.png"))); // NOI18N
        isDeletedLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ImageThumbnailPanel.class, "ImageThumbnailPanel.isDeletedLabel.toolTipText")); // NOI18N
        isDeletedLabel.setMaximumSize(new Dimension(org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize(),org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize()));
        isDeletedLabel.setMinimumSize(new Dimension(org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize(),org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize()));
        isDeletedLabel.setPreferredSize(new Dimension(org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize(),org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize()));

        scoreLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/sleuthkit/autopsy/images/red-circle-exclamation.png"))); // NOI18N
        scoreLabel.setToolTipText("");
        scoreLabel.setMaximumSize(new Dimension(org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize(),org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize()));
        scoreLabel.setMinimumSize(new Dimension(org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize(),org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize()));
        scoreLabel.setPreferredSize(new Dimension(org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize(),org.sleuthkit.autopsy.filequery.DiscoveryUiUtils.getIconSize()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(isDeletedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(thumbnailPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(thumbnailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isDeletedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fileSizeLabel;
    private javax.swing.JLabel isDeletedLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel thumbnailLabel;
    // End of variables declaration//GEN-END:variables

    @NbBundle.Messages({
        "# {0} - otherInstanceCount",
        "ImageThumbnailPanel.nameLabel.more.text= and {0} more",
        "ImageThumbnailPanel.isDeleted.text=All instances of file are deleted."})
    @Override
    public Component getListCellRendererComponent(JList<? extends ImageThumbnailWrapper> list, ImageThumbnailWrapper value, int index, boolean isSelected, boolean cellHasFocus) {
        fileSizeLabel.setText(DiscoveryUiUtils.getFileSizeString(value.getResultFile().getFirstInstance().getSize()));
        String nameText = Paths.get(value.getResultFile().getFirstInstance().getParentPath(), value.getResultFile().getFirstInstance().getName()).toString();
        if (value.getResultFile().getAllInstances().size() > 1) {
            nameText += Bundle.ImageThumbnailPanel_nameLabel_more_text(value.getResultFile().getAllInstances().size() - 1);
        }
        if (nameText.length() > MAX_NAME_STRING) {
            nameText = "..." + nameText.substring(nameText.length() - (MAX_NAME_STRING - 3));
        }
        nameLabel.setText(nameText);
        thumbnailLabel.setIcon(new ImageIcon(value.getThumbnail()));
        DiscoveryUiUtils.setDeletedIcon(value.getResultFile().isDeleted(), isDeletedLabel);
        DiscoveryUiUtils.setScoreIcon(value.getResultFile(), scoreLabel);
        setBackground(isSelected ? SELECTION_COLOR : list.getBackground());

        return this;
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        if (event != null) {
            //gets tooltip of internal panel item mouse is over
            Point point = event.getPoint();
            for (Component comp : getComponents()) {
                if (DiscoveryUiUtils.isPointOnIcon(comp, point)) {
                    String toolTip = ((JComponent) comp).getToolTipText();
                    if (toolTip == null || toolTip.isEmpty()) {
                        return null;
                    } else {
                        return toolTip;
                    }
                }
            }
        }
        return null;
    }

}
