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

import org.beamproject.client.App;
import org.beamproject.client.ui.Validators;
import org.beamproject.common.Participant;
import org.beamproject.common.crypto.EccKeyPairGenerator;
import org.beamproject.common.util.Base58;

public class IdentityPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;

    public IdentityPanel() {
        initComponents();
        loadUsername();
        loadUserPublicKey();
        loadServerUrl();
        loadServerPublicKey();
    }

    private void loadUsername() {
        usernameTextField.setText(App.getConfig().username());
    }

    private void loadUserPublicKey() {
        Participant participant = App.getModel().getUser();

        if (participant != null) {
            userPublicKeyTextField.setText(participant.getPublicKeyAsBase58());
        }
    }

    private void loadServerPublicKey() {
        Participant server = App.getModel().getServer();

        if (server != null) {
            serverPublicKeyTextField.setText(server.getPublicKeyAsBase58());
        }
    }

    private void loadServerUrl() {
        serverUrlTextField.setText(App.getConfig().serverUrl());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        keyMaterialLabel = new javax.swing.JLabel();
        userPublicKeyLabel = new javax.swing.JLabel();
        userPublicKeyTextField = new javax.swing.JTextField();
        serverLabel = new javax.swing.JLabel();
        serverUrlLabel = new javax.swing.JLabel();
        serverUrlTextField = new javax.swing.JTextField();
        serverPublicKeyLabel = new javax.swing.JLabel();
        serverPublicKeyTextField = new javax.swing.JTextField();

        usernameLabel.setText("Your Name:");

        usernameTextField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                usernameTextFieldPropertyChange(evt);
            }
        });

        keyMaterialLabel.setFont(keyMaterialLabel.getFont().deriveFont(keyMaterialLabel.getFont().getStyle() | java.awt.Font.BOLD));
        keyMaterialLabel.setText("Key Material");

        userPublicKeyLabel.setText("Public Key:");

        userPublicKeyTextField.setEditable(false);

        serverLabel.setFont(serverLabel.getFont().deriveFont(serverLabel.getFont().getStyle() | java.awt.Font.BOLD));
        serverLabel.setText("Server");

        serverUrlLabel.setText("URL:");

        serverUrlTextField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                serverUrlTextFieldPropertyChange(evt);
            }
        });

        serverPublicKeyLabel.setText("Public Key:");

        serverPublicKeyTextField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                serverPublicKeyTextFieldPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(userPublicKeyLabel))
                    .addComponent(usernameLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userPublicKeyTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addComponent(usernameTextField)))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serverPublicKeyLabel)
                    .addComponent(serverUrlLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serverUrlTextField)
                    .addComponent(serverPublicKeyTextField)))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(keyMaterialLabel)
                    .addComponent(serverLabel))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(keyMaterialLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userPublicKeyLabel)
                    .addComponent(userPublicKeyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(serverLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverUrlLabel)
                    .addComponent(serverUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverPublicKeyLabel)
                    .addComponent(serverPublicKeyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(146, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTextFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_usernameTextFieldPropertyChange
        String name = usernameTextField.getText().trim();

        if (App.getConfig().username().equals(name)) {
            return;
        }

        if (Validators.isUsernameValid(name)) {
            App.getController().setUsername(name);
        }
    }//GEN-LAST:event_usernameTextFieldPropertyChange

    private void serverUrlTextFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_serverUrlTextFieldPropertyChange
        String serverUrl = serverUrlTextField.getText().trim();

        if (App.getConfig().serverUrl().equals(serverUrl)) {
            return;
        }
        
        if (Validators.isServerHttpUrlValid(serverUrl)) {
            App.getController().setServerUrl(serverUrl);
        }
    }//GEN-LAST:event_serverUrlTextFieldPropertyChange

    private void serverPublicKeyTextFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_serverPublicKeyTextFieldPropertyChange
        String serverPublicKey = serverPublicKeyTextField.getText().trim();

        if (App.getModel().getServer().getPublicKeyAsBase58().equals(serverPublicKey)) {
            return;
        }

        if (Validators.isServerPublicKeyValid(serverPublicKey)) {
            byte[] publicKeyAsBytes = Base58.decode(serverPublicKey);
            Participant server = new Participant(EccKeyPairGenerator.fromPublicKey(publicKeyAsBytes));
            App.getController().setServer(server);
        }
    }//GEN-LAST:event_serverPublicKeyTextFieldPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel keyMaterialLabel;
    private javax.swing.JLabel serverLabel;
    private javax.swing.JLabel serverPublicKeyLabel;
    javax.swing.JTextField serverPublicKeyTextField;
    private javax.swing.JLabel serverUrlLabel;
    javax.swing.JTextField serverUrlTextField;
    private javax.swing.JLabel userPublicKeyLabel;
    javax.swing.JTextField userPublicKeyTextField;
    private javax.swing.JLabel usernameLabel;
    javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
