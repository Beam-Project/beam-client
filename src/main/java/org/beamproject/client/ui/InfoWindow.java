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

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import org.beamproject.client.App;
import org.beamproject.client.util.ClipboardAccess;
import org.beamproject.common.util.QrCode;

/**
 * This window shows information about the user.
 */
public class InfoWindow extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    static final int QR_CODE_DIMENSION_IN_PX = 250;

    public InfoWindow() {
        initComponents();
        setLocationRelativeTo(null);
        loadUsername();
        loadUserAddress();
        loadQrCode();
    }

    private void loadUsername() {
        usernameLabel.setText(App.getConfig().username());
    }

    private void loadUserAddress() {
        addressLabel.setText(App.getModel().getUserUrl());
    }

    private void loadQrCode() {
        String userUrl = App.getModel().getUserUrl();

        if (userUrl.isEmpty()) {
            qrCodePanel.setVisible(false);
            pack();
        } else {
            qrCodeLabel.setText("");
            BufferedImage qrCode = QrCode.encode(userUrl, QR_CODE_DIMENSION_IN_PX);
            qrCodeLabel.setIcon(new ImageIcon(qrCode));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameLabel = new javax.swing.JLabel();
        addressTitleLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
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
        qrCodePanel = new javax.swing.JPanel();
        qrCodeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Info");
        setLocationByPlatform(true);
        setResizable(false);

        usernameLabel.setFont(usernameLabel.getFont().deriveFont(usernameLabel.getFont().getStyle() | java.awt.Font.BOLD));
        usernameLabel.setText("My Identity");

        addressTitleLabel.setText("Beam address:");

        addressLabel.setText("n/a");

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

        aboutButton.setText("About Beam...");
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

        qrCodeLabel.setFont(qrCodeLabel.getFont().deriveFont((qrCodeLabel.getFont().getStyle() | java.awt.Font.ITALIC), qrCodeLabel.getFont().getSize()-1));
        qrCodeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        qrCodeLabel.setText("QR code not available");
        qrCodeLabel.setMaximumSize(new java.awt.Dimension(250, 250));
        qrCodeLabel.setMinimumSize(new java.awt.Dimension(250, 250));
        qrCodeLabel.setPreferredSize(new java.awt.Dimension(250, 250));
        qrCodePanel.add(qrCodeLabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(qrCodePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(addressTitleLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(copyUrlButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(aboutButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sentMessagesLabel)
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
                                                    .addComponent(sentMessagesValueLabel)))))))
                            .addComponent(statisticsLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(usernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressTitleLabel)
                    .addComponent(copyUrlButton)
                    .addComponent(addressLabel))
                .addGap(18, 18, 18)
                .addComponent(qrCodePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(18, 18, 18)
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

    private void copyUrlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyUrlButtonActionPerformed
        ClipboardAccess access = new ClipboardAccess();
        access.copyTextToClipboard(addressLabel.getText());
    }//GEN-LAST:event_copyUrlButtonActionPerformed

    private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutButtonActionPerformed
        AboutWindow window = new AboutWindow();
        Frames.setIcons(window);
        window.setVisible(true);
    }//GEN-LAST:event_aboutButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aboutButton;
    javax.swing.JLabel addressLabel;
    private javax.swing.JLabel addressTitleLabel;
    javax.swing.JButton closeButton;
    private javax.swing.JButton copyUrlButton;
    javax.swing.JLabel qrCodeLabel;
    javax.swing.JPanel qrCodePanel;
    private javax.swing.JLabel receivedMessagesLabel;
    private javax.swing.JLabel receivedMessagesValueLabel;
    private javax.swing.JLabel sentMessagesLabel;
    private javax.swing.JLabel sentMessagesValueLabel;
    private javax.swing.JLabel statisticsLabel;
    private javax.swing.JLabel storedContactsLabel;
    private javax.swing.JLabel storedContactsValueLabel;
    javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
