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
package org.beamproject.client.ui;

import static org.beamproject.client.App.getController;
import org.beamproject.common.Server;

/**
 *
 * @author René Bernhardsgrütter <rene.bernhardsgruetter@posteo.ch>
 */
public class SetUpDialog extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private boolean isUsernameDone;
    private boolean isServerUrlDone;

    public SetUpDialog() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        textLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        serverAddressLabel = new javax.swing.JLabel();
        serverAddressTextField = new javax.swing.JTextField();
        skipForNowCheckBox = new javax.swing.JCheckBox();
        letsBeamButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hi There!");
        setAlwaysOnTop(true);
        setLocationByPlatform(true);
        setResizable(false);

        titleLabel.setFont(titleLabel.getFont().deriveFont(titleLabel.getFont().getStyle() | java.awt.Font.BOLD, titleLabel.getFont().getSize()+5));
        titleLabel.setText("Welcome to Beam!");

        textLabel.setText("Fill in the following fields to set up your account.");

        nameLabel.setText("Your Name:");

        serverAddressLabel.setText("Server Address:");

        skipForNowCheckBox.setText("Skip for now");
        skipForNowCheckBox.setFocusTraversalPolicyProvider(true);
        skipForNowCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                skipForNowCheckBoxItemStateChanged(evt);
            }
        });

        letsBeamButton.setText("Let's Beam!");
        letsBeamButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letsBeamButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                    .addComponent(letsBeamButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(serverAddressLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameTextField)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(serverAddressTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(skipForNowCheckBox)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(skipForNowCheckBox)
                    .addComponent(serverAddressLabel)
                    .addComponent(serverAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(letsBeamButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void letsBeamButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_letsBeamButtonActionPerformed
        validateAndSaveUsername();
        validateAndSaveServerAddress();

        if (isUsernameDone && isServerUrlDone) {
            dispose();
            getController().getMainWindow().requestFocus();
        }
    }//GEN-LAST:event_letsBeamButtonActionPerformed

    private void validateAndSaveUsername() {
        String username = usernameTextField.getText().trim();

        if (Validators.isUsernameValid(username)) {
            Components.setDefalutBackground(usernameTextField);
            getController().setUsername(username);
            isUsernameDone = true;
        } else {
            Components.setErrorBackground(usernameTextField);
            isUsernameDone = false;
        }
    }

    private void validateAndSaveServerAddress() {
        String serverAddress = serverAddressTextField.getText().trim();

        if (skipForNowCheckBox.isSelected()) {
            isServerUrlDone = true;
        } else {
            try {
                getController().setServer(new Server(serverAddress));
                Components.setDefalutBackground(serverAddressTextField);
                isServerUrlDone = true;
            } catch (IllegalArgumentException ex) {
                Components.setErrorBackground(serverAddressTextField);
                isServerUrlDone = false;
            }
        }
    }

    private void skipForNowCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_skipForNowCheckBoxItemStateChanged
        serverAddressTextField.setEditable(!skipForNowCheckBox.isSelected());
        Components.setDefalutBackground(serverAddressTextField);
        serverAddressTextField.setText("");
    }//GEN-LAST:event_skipForNowCheckBoxItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton letsBeamButton;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel serverAddressLabel;
    javax.swing.JTextField serverAddressTextField;
    javax.swing.JCheckBox skipForNowCheckBox;
    private javax.swing.JLabel textLabel;
    private javax.swing.JLabel titleLabel;
    javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
