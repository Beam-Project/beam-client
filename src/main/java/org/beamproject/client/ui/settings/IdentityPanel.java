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

import static org.beamproject.client.App.getConfig;
import static org.beamproject.client.App.getController;
import static org.beamproject.client.App.getModel;
import org.beamproject.client.ui.Validators;
import org.beamproject.common.User;

public class IdentityPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    public static final String SERVER_ADDRESS_PLACEHOLDER = "Not configured yet...";

    public IdentityPanel() {
        initComponents();
        loadUsername();
        loadServerAddress();
    }

    private void loadUsername() {
        usernameTextField.setText(getConfig().username());
    }

    private void loadServerAddress() {
        User user = getModel().getUser();

        serverAddressValueLabel.setText(user.isServerSet()
                ? user.getAddress()
                : SERVER_ADDRESS_PLACEHOLDER);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        serverLabel = new javax.swing.JLabel();
        serverAddressLabel = new javax.swing.JLabel();
        changeServerAddressButton = new javax.swing.JButton();
        serverAddressValueLabel = new javax.swing.JLabel();

        usernameLabel.setText("Your Name:");

        usernameTextField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                usernameTextFieldPropertyChange(evt);
            }
        });

        serverLabel.setFont(serverLabel.getFont().deriveFont(serverLabel.getFont().getStyle() | java.awt.Font.BOLD));
        serverLabel.setText("Server");

        serverAddressLabel.setText("Address:");

        changeServerAddressButton.setText("Change...");
        changeServerAddressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeServerAddressButtonActionPerformed(evt);
            }
        });

        serverAddressValueLabel.setText("Not configured yet...");
        serverAddressValueLabel.setMaximumSize(new java.awt.Dimension(10000, 15));
        serverAddressValueLabel.setMinimumSize(new java.awt.Dimension(15, 15));
        serverAddressValueLabel.setPreferredSize(new java.awt.Dimension(500, 15));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(serverAddressLabel))
                    .addComponent(serverLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(serverAddressValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(changeServerAddressButton))
                    .addComponent(usernameTextField)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(serverLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverAddressLabel)
                    .addComponent(changeServerAddressButton)
                    .addComponent(serverAddressValueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTextFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_usernameTextFieldPropertyChange
        String name = usernameTextField.getText().trim();

        if (getConfig().username().equals(name)) {
            return;
        }

        if (Validators.isUsernameValid(name)) {
            getController().setUsername(name);
        }
    }//GEN-LAST:event_usernameTextFieldPropertyChange

    private void changeServerAddressButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeServerAddressButtonActionPerformed
        System.out.println("change button clicked");
    }//GEN-LAST:event_changeServerAddressButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changeServerAddressButton;
    private javax.swing.JLabel serverAddressLabel;
    javax.swing.JLabel serverAddressValueLabel;
    private javax.swing.JLabel serverLabel;
    private javax.swing.JLabel usernameLabel;
    javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
