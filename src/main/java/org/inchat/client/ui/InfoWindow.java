/*
 * Copyright (C) 2013, 2014 inchat.org
 *
 * This file is part of inchat-client.
 *
 * inchat-client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * inchat-client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.inchat.client.ui;

import org.inchat.client.App;
import org.inchat.client.util.ClipboardAccess;
import org.inchat.common.Config;

/**
 * This window shows information about the user.
 */
public class InfoWindow extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    public InfoWindow() {
        initComponents();
        setLocationRelativeTo(null);
        loadName();
        loadUrl();
    }

    private void loadName() {
        nameTextField.setText(Config.getProperty(Config.Key.participantName));
    }

    private void loadUrl() {
        urlTextField.setText("n/a");
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
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        copyNameButton = new javax.swing.JButton();
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
        setResizable(false);

        myIdentityLabel.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
        myIdentityLabel.setText("My Identity");

        nameLabel.setText("Name:");

        nameTextField.setEditable(false);

        copyNameButton.setText("Copy");
        copyNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyNameButtonActionPerformed(evt);
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

        statisticsLabel.setFont(new java.awt.Font("Ubuntu", 1, 13)); // NOI18N
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
                                    .addComponent(nameLabel)
                                    .addComponent(urlLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(urlTextField)
                                    .addComponent(nameTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(copyNameButton, javax.swing.GroupLayout.Alignment.TRAILING)
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
                        .addComponent(aboutButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(nameLabel)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(copyNameButton))
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

    private void copyNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyNameButtonActionPerformed
        ClipboardAccess access = new ClipboardAccess();
        access.copyTextToClipboard(nameTextField.getText());
    }//GEN-LAST:event_copyNameButtonActionPerformed

    private void copyUrlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyUrlButtonActionPerformed
        ClipboardAccess access = new ClipboardAccess();
        access.copyTextToClipboard(urlTextField.getText());
    }//GEN-LAST:event_copyUrlButtonActionPerformed

    private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutButtonActionPerformed
        AboutWindow window = new AboutWindow();
        Frames.setIcons(window);
        window.setVisible(true);

        closeButtonActionPerformed(null);
    }//GEN-LAST:event_aboutButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aboutButton;
    javax.swing.JButton closeButton;
    private javax.swing.JButton copyNameButton;
    private javax.swing.JButton copyUrlButton;
    private javax.swing.JLabel myIdentityLabel;
    private javax.swing.JLabel nameLabel;
    javax.swing.JTextField nameTextField;
    private javax.swing.JLabel receivedMessagesLabel;
    private javax.swing.JLabel receivedMessagesValueLabel;
    private javax.swing.JLabel sentMessagesLabel;
    private javax.swing.JLabel sentMessagesValueLabel;
    private javax.swing.JLabel statisticsLabel;
    private javax.swing.JLabel storedContactsLabel;
    private javax.swing.JLabel storedContactsValueLabel;
    private javax.swing.JLabel urlLabel;
    javax.swing.JTextField urlTextField;
    // End of variables declaration//GEN-END:variables
}
