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

import org.beamproject.client.App;
import org.beamproject.client.util.ClipboardAccess;

/**
 * This window shows information about the user.
 */
public class InfoWindow extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    public InfoWindow() {
        initComponents();
        setLocationRelativeTo(null);
        loadUsername();
        loadUserUrl();
    }

    private void loadUsername() {
        usernameTextField.setText(App.getConfig().username());
    }

    private void loadUserUrl() {
        urlTextField.setText(App.getModel().getUserUrl());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        myIdentityLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        copyUsernameButton = new javax.swing.JButton();
        urlLabel = new javax.swing.JLabel();
        urlTextField = new javax.swing.JTextField();
        copyUrlButton = new javax.swing.JButton();
        statisticsLabel = new javax.swing.JLabel();
        storedContactsLabel = new javax.swing.JLabel();
        storedContactsValueLabel = new javax.swing.JLabel();
        sentMessagesLabel = new javax.swing.JLabel();
        sentMessagesValueLabel = new javax.swing.JLabel();
        receivedMessagesLabel = new javax.swing.JLabel();
        receivedMessagesValueLabel = new javax.swing.JLabel();
        aboutButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Info");
        setLocationByPlatform(true);
        setResizable(false);

        myIdentityLabel.setFont(myIdentityLabel.getFont().deriveFont(myIdentityLabel.getFont().getStyle() | java.awt.Font.BOLD));
        myIdentityLabel.setText("My Identity");

        usernameLabel.setText("Name:");

        usernameTextField.setEditable(false);

        copyUsernameButton.setText("Copy");
        copyUsernameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyUsernameButtonActionPerformed(evt);
            }
        });

        urlLabel.setText("URL:");

        urlTextField.setEditable(false);

        copyUrlButton.setText("Copy");
        copyUrlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyUrlButtonActionPerformed(evt);
            }
        });

        statisticsLabel.setFont(statisticsLabel.getFont().deriveFont(statisticsLabel.getFont().getStyle() | java.awt.Font.BOLD));
        statisticsLabel.setText("Statistics");

        storedContactsLabel.setText("Stored Contacts:");

        storedContactsValueLabel.setText("n/a");

        sentMessagesLabel.setText("Sent Messages:");

        sentMessagesValueLabel.setText("n/a");

        receivedMessagesLabel.setText("Received Messages:");

        receivedMessagesValueLabel.setText("n/a");

        aboutButton.setText("About");
        aboutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutButtonActionPerformed(evt);
            }
        });

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(usernameLabel)
                                    .addComponent(urlLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(urlTextField)
                                    .addComponent(usernameTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(copyUsernameButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(copyUrlButton, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sentMessagesLabel)
                                .addGap(254, 254, 254))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(storedContactsLabel)
                                        .addGap(31, 31, 31)
                                        .addComponent(storedContactsValueLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(receivedMessagesLabel)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(receivedMessagesValueLabel))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sentMessagesValueLabel)))))
                                .addGap(0, 210, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(myIdentityLabel)
                            .addComponent(statisticsLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(aboutButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myIdentityLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(copyUsernameButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(urlLabel)
                    .addComponent(urlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(copyUrlButton))
                .addGap(18, 18, 18)
                .addComponent(statisticsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(storedContactsLabel)
                    .addComponent(storedContactsValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sentMessagesLabel)
                    .addComponent(sentMessagesValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(receivedMessagesLabel)
                    .addComponent(receivedMessagesValueLabel))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aboutButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        App.getController().closeInfoWindow();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void copyUsernameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyUsernameButtonActionPerformed
        ClipboardAccess access = new ClipboardAccess();
        access.copyTextToClipboard(usernameTextField.getText());
    }//GEN-LAST:event_copyUsernameButtonActionPerformed

    private void copyUrlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyUrlButtonActionPerformed
        ClipboardAccess access = new ClipboardAccess();
        access.copyTextToClipboard(urlTextField.getText());
    }//GEN-LAST:event_copyUrlButtonActionPerformed

    private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutButtonActionPerformed
        AboutWindow window = new AboutWindow();
        Frames.setIcons(window);
        window.setVisible(true);
    }//GEN-LAST:event_aboutButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aboutButton;
    javax.swing.JButton closeButton;
    private javax.swing.JButton copyUrlButton;
    private javax.swing.JButton copyUsernameButton;
    private javax.swing.JLabel myIdentityLabel;
    private javax.swing.JLabel receivedMessagesLabel;
    private javax.swing.JLabel receivedMessagesValueLabel;
    private javax.swing.JLabel sentMessagesLabel;
    private javax.swing.JLabel sentMessagesValueLabel;
    private javax.swing.JLabel statisticsLabel;
    private javax.swing.JLabel storedContactsLabel;
    private javax.swing.JLabel storedContactsValueLabel;
    private javax.swing.JLabel urlLabel;
    javax.swing.JTextField urlTextField;
    private javax.swing.JLabel usernameLabel;
    javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
