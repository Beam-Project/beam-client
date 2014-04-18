/*
 * Copyright (C) 2013, 2014 beamproject.org
 *
 * This file is part of beam-client.
 *
 * beam-client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * beam-client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beamproject.client.ui.settings;

import java.awt.BorderLayout;
import javax.swing.event.ListSelectionEvent;
import org.beamproject.client.App;

/**
 * This is the settings window. The default selection is always the 'General'
 * tab.
 */
public class SettingsWindow extends javax.swing.JFrame {

    public final static int GENERAL_MENU_INDEX = 0;
    public final static int IDENTITY_MENU_INDEX = 1;
    public final static int SECURITY_MENU_INDEX = 2;
    public final static int NETWORK_MENU_INDEX = 3;
    private static final long serialVersionUID = 1L;
    GeneralPanel generalPanel;
    IdentityPanel identityPanel;
    SecurityPanel securityPanel;
    NetworkPanel networkPanel;

    public SettingsWindow() {
        initComponents();
        setLocationRelativeTo(null);
        addIconsToMenuList();
        openGeneralMenu();
    }

    @SuppressWarnings("unchecked")
    private void addIconsToMenuList() {
        menuList.setCellRenderer(new MenuCellRenderer());
    }

    private void openGeneralMenu() {
        menuListValueChanged(null);
    }

    public void openIdentityMenuWithFocusedUsername() {
        menuList.setSelectedIndex(IDENTITY_MENU_INDEX);

        ListSelectionEvent clickOnIdentity = new ListSelectionEvent(menuList, IDENTITY_MENU_INDEX, IDENTITY_MENU_INDEX, false);
        menuListValueChanged(clickOnIdentity);

        getIdentityPanel().usernameTextField.requestFocus();
        getIdentityPanel().usernameTextField.selectAll();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuSelectionList = new javax.swing.JScrollPane();
        menuList = new javax.swing.JList();
        contentPanel = new javax.swing.JPanel();
        placeholderLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Settings");
        setMinimumSize(new java.awt.Dimension(580, 400));

        menuSelectionList.setBorder(null);

        menuList.setBorder(null);
        menuList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "General", "Identity", "Security", "Network" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        menuList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        menuList.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        menuList.setMaximumSize(new java.awt.Dimension(260, 64));
        menuList.setMinimumSize(new java.awt.Dimension(260, 62));
        menuList.setSelectedIndex(0);
        menuList.setVisibleRowCount(1);
        menuList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                menuListValueChanged(evt);
            }
        });
        menuSelectionList.setViewportView(menuList);

        contentPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        contentPanel.setLayout(new java.awt.BorderLayout());

        placeholderLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        placeholderLabel.setText("contentPanel");
        placeholderLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        contentPanel.add(placeholderLabel, java.awt.BorderLayout.CENTER);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuSelectionList, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(closeButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menuSelectionList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        App.getController().closeSettingsWindow();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void menuListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_menuListValueChanged
        if (evt != null && evt.getValueIsAdjusting()) {
            return;
        }

        contentPanel.removeAll();

        switch (menuList.getSelectedIndex()) {
            case IDENTITY_MENU_INDEX:
                contentPanel.add(getIdentityPanel(), BorderLayout.CENTER);
                break;
            case SECURITY_MENU_INDEX:
                contentPanel.add(getSecurityPanel(), BorderLayout.CENTER);
                break;
            case NETWORK_MENU_INDEX:
                contentPanel.add(getNetworkPanel(), BorderLayout.CENTER);
                break;
            default:
                contentPanel.add(getGeneralPanel(), BorderLayout.CENTER);
        }

        contentPanel.repaint();
        contentPanel.validate();
    }//GEN-LAST:event_menuListValueChanged

    GeneralPanel getGeneralPanel() {
        if (generalPanel == null) {
            generalPanel = new GeneralPanel();
        }

        return generalPanel;
    }

    IdentityPanel getIdentityPanel() {
        if (identityPanel == null) {
            identityPanel = new IdentityPanel();
        }

        return identityPanel;
    }

    SecurityPanel getSecurityPanel() {
        if (securityPanel == null) {
            securityPanel = new SecurityPanel();
        }

        return securityPanel;
    }

    NetworkPanel getNetworkPanel() {
        if (networkPanel == null) {
            networkPanel = new NetworkPanel();
        }

        return networkPanel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton closeButton;
    javax.swing.JPanel contentPanel;
    javax.swing.JList menuList;
    private javax.swing.JScrollPane menuSelectionList;
    private javax.swing.JLabel placeholderLabel;
    // End of variables declaration//GEN-END:variables
}
