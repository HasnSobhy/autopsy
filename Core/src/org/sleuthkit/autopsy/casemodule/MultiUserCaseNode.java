/*
 * Autopsy Forensic Browser
 *
 * Copyright 2017-2019 Basis Technology Corp.
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
package org.sleuthkit.autopsy.casemodule;

import org.sleuthkit.autopsy.coordinationservice.CaseNodeData;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;
import org.sleuthkit.autopsy.coreutils.Logger;
import org.sleuthkit.autopsy.coreutils.MessageNotifyUtil;
import org.sleuthkit.autopsy.datamodel.NodeProperty;

/**
 * A NetBeans Explorer View node that represents a multi-user case.
 */
final class MultiUserCaseNode extends AbstractNode {

    private static final Logger logger = Logger.getLogger(MultiUserCaseNode.class.getName());
    private final CaseNodeData caseNodeData;

    /**
     * Constructs a NetBeans Explorer View node that represents a multi-user
     * case.
     *
     * @param caseNodeData The coordination service node data for the case.
     */
    MultiUserCaseNode(CaseNodeData caseNodeData) {
        super(Children.LEAF);
        super.setName(caseNodeData.getDisplayName());
        setName(caseNodeData.getDisplayName());
        setDisplayName(caseNodeData.getDisplayName());
        this.caseNodeData = caseNodeData;
    }

    @NbBundle.Messages({
        "MultiUserCaseNode.column.name=Name",
        "MultiUserCaseNode.column.createTime=Create Time",
        "MultiUserCaseNode.column.path=Path"
    })
    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        Sheet.Set sheetSet = sheet.get(Sheet.PROPERTIES);
        if (sheetSet == null) {
            sheetSet = Sheet.createPropertiesSet();
            sheet.put(sheetSet);
        }
        sheetSet.put(new NodeProperty<>(Bundle.MultiUserCaseNode_column_name(),
                Bundle.MultiUserCaseNode_column_name(),
                Bundle.MultiUserCaseNode_column_name(),
                caseNodeData.getDisplayName()));
        sheetSet.put(new NodeProperty<>(Bundle.MultiUserCaseNode_column_createTime(),
                Bundle.MultiUserCaseNode_column_createTime(),
                Bundle.MultiUserCaseNode_column_createTime(),
                caseNodeData.getCreateDate()));
        sheetSet.put(new NodeProperty<>(Bundle.MultiUserCaseNode_column_path(),
                Bundle.MultiUserCaseNode_column_path(),
                Bundle.MultiUserCaseNode_column_path(),
                caseNodeData.getDirectory().toString()));
        return sheet;
    }

    @Override
    public Action[] getActions(boolean context) {
        List<Action> actions = new ArrayList<>();
        actions.add(new OpenMultiUserCaseAction(this.caseNodeData));
        actions.add(new OpenCaseAutoIngestLogAction(this.caseNodeData));
        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public Action getPreferredAction() {
        return new OpenMultiUserCaseAction(this.caseNodeData);
    }

    /**
     * An action that opens the specified case and hides the multi-user case
     * panel.
     */
    @NbBundle.Messages({
        "MultiUserNode.OpenMultiUserCaseAction.menuItemText=Open Case",
        "# {0} - caseErrorMessage", "MultiUserNode.OpenMultiUserCaseAction.caseOpeningErrorErrorMsg=Failed to open case: {0}"
    })
    private static final class OpenMultiUserCaseAction extends AbstractAction {

        private static final long serialVersionUID = 1L;
        private final CaseNodeData caseNodeData;

        OpenMultiUserCaseAction(CaseNodeData caseNodeData) {
            super(Bundle.MultiUserNode_OpenMultiUserCaseAction_menuItemText());
            this.caseNodeData = caseNodeData;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            StartupWindowProvider.getInstance().close();
            OpenMultiUserCaseDialog.getInstance().setVisible(false);
            new Thread(() -> {
                String caseMetadataFilePath = null;
                File caseDirectory = caseNodeData.getDirectory().toFile();
                File[] filesInDirectory = caseDirectory.listFiles();
                if (filesInDirectory != null) {
                    for (File file : filesInDirectory) {
                        if (file.getName().toLowerCase().endsWith(CaseMetadata.getFileExtension()) && file.isFile()) {
                            caseMetadataFilePath = file.getPath();
                        }
                    }
                }
                if (caseMetadataFilePath != null) {
                    try {
                        Case.openAsCurrentCase(caseMetadataFilePath);
                    } catch (CaseActionException ex) {
                        if (null != ex.getCause() && !(ex.getCause() instanceof CaseActionCancelledException)) {
                            logger.log(Level.SEVERE, String.format("Error opening case with metadata file path %s", caseMetadataFilePath), ex); //NON-NLS
                        }
                        SwingUtilities.invokeLater(() -> {
                            MessageNotifyUtil.Message.error(Bundle.MultiUserNode_OpenMultiUserCaseAction_caseOpeningErrorErrorMsg(ex.getLocalizedMessage()));
                            StartupWindowProvider.getInstance().open();
                            OpenMultiUserCaseDialog.getInstance().setVisible(true);
                        });
                    }
                } else {
                    SwingUtilities.invokeLater(() -> {
                        MessageNotifyUtil.Message.error(Bundle.MultiUserNode_OpenMultiUserCaseAction_caseOpeningErrorErrorMsg("Could not locate case metadata file."));
                    });
                }
            }).start();
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            super.clone();
            throw new CloneNotSupportedException();
        }

    }

    /**
     * An action that opens the case auto ingest log for the specified case.
     */
    @NbBundle.Messages({
        "MultiUserNode.OpenCaseAutoIngestLogAction.menuItemText=Open Auto Ingest Log File",
        "MultiUserNode.OpenCaseAutoIngestLogAction.deletedLogErrorMsg=The case auto ingest log has been deleted.",
        "MultiUserNode.OpenCaseAutoIngestLogAction.logOpenFailedErrorMsg=Failed to open case auto ingest log. See application log for details."
    })
    private static final class OpenCaseAutoIngestLogAction extends AbstractAction {

        private static final long serialVersionUID = 1L;
        private static final String CASE_AUTO_INGEST_LOG_FILE_NAME = "auto_ingest_log.txt";
        private final Path caseAutoIngestLogFilePath;

        OpenCaseAutoIngestLogAction(CaseNodeData caseNodeData) {
            super(Bundle.MultiUserNode_OpenCaseAutoIngestLogAction_menuItemText());
            this.caseAutoIngestLogFilePath = Paths.get(caseNodeData.getDirectory().toString(), CASE_AUTO_INGEST_LOG_FILE_NAME);
            this.setEnabled(caseAutoIngestLogFilePath.toFile().exists());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (caseAutoIngestLogFilePath.toFile().exists()) {
                    Desktop.getDesktop().edit(caseAutoIngestLogFilePath.toFile());
                } else {
                    MessageNotifyUtil.Message.error(Bundle.MultiUserNode_OpenCaseAutoIngestLogAction_deletedLogErrorMsg());
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, String.format("Error opening case auto ingest log file at %s", caseAutoIngestLogFilePath), ex); //NON-NLS
                MessageNotifyUtil.Message.error(Bundle.MultiUserNode_OpenCaseAutoIngestLogAction_logOpenFailedErrorMsg());
            }
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            super.clone();
            throw new CloneNotSupportedException();
        }

    }

}
