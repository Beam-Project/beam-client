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
package org.beamproject.client.view.menu;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javax.swing.ImageIcon;
import org.beamproject.client.Event;
import static org.beamproject.client.Event.ENCRYPTED_CONFIG_UNLOCKED;
import org.beamproject.client.model.MenuModel;
import org.beamproject.client.util.Components;
import org.beamproject.client.view.MainWindow;

@Singleton
public class InfoLayer extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private static final int HORIZONTAL_GAP = 24;
    private final MenuModel model;
    private final EventBus bus;
    private final MainWindow mainWindow;

    @Inject
    public InfoLayer(MenuModel model, EventBus bus, MainWindow mainWindow) {
        this.model = model;
        this.bus = bus;
        this.mainWindow = mainWindow;
        this.bus.register(this);

        initComponents();
    }

    @Subscribe
    public void loadAndShowQrCode(Event event) {
        if (event == ENCRYPTED_CONFIG_UNLOCKED) {
            Components.layoutHtmlLabels(shareLabel);
            qrCodeLabel.setText("");
            generateAndShowQrCode();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        shareLabel = new javax.swing.JLabel();
        qrCodeLabel = new javax.swing.JLabel();
        copyButtonPanel = new javax.swing.JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        copyButton = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));

        setOpaque(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        shareLabel.setText("<html>Share your Beam address with your friends to chat with them.</html>");

        qrCodeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        qrCodeLabel.setText("QR Code");

        copyButtonPanel.setOpaque(false);
        copyButtonPanel.setLayout(new javax.swing.BoxLayout(copyButtonPanel, javax.swing.BoxLayout.LINE_AXIS));
        copyButtonPanel.add(filler3);

        copyButton.setText("Copy Beam address");
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });
        copyButtonPanel.add(copyButton);
        copyButtonPanel.add(filler4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(qrCodeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(copyButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(shareLabel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(shareLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(qrCodeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(copyButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
        model.copyAddressToClipboard();
    }//GEN-LAST:event_copyButtonActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        generateAndShowQrCode();
    }//GEN-LAST:event_formComponentResized

    private void generateAndShowQrCode() {
        int qrCodeWidth = mainWindow.getSize().width - HORIZONTAL_GAP;
        ImageIcon qrCode = new ImageIcon(model.getUserAddressQrCode(qrCodeWidth));
        qrCodeLabel.setSize(qrCodeWidth, qrCodeWidth);
        qrCodeLabel.validate();
        qrCodeLabel.setIcon(qrCode);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton copyButton;
    private javax.swing.JPanel copyButtonPanel;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JLabel qrCodeLabel;
    private javax.swing.JLabel shareLabel;
    // End of variables declaration//GEN-END:variables
}
