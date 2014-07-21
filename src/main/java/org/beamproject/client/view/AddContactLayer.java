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
package org.beamproject.client.view;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.beamproject.client.model.ChatModel;
import org.beamproject.client.util.validators.UserAddressValidator;

public class AddContactLayer extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private final static String NAME_PLACEHOLDER = "-";
    private final ChatModel model;
    private final EventBus bus;
    private final UserAddressValidator addressValidator;

    @Inject
    public AddContactLayer(ChatModel model, EventBus bus) {
        this.model = model;
        this.bus = bus;
        this.bus.register(this);

        initComponents();
        registerDocumentListener();
        addressValidator = new UserAddressValidator(addressTextField);
    }

    private void registerDocumentListener() {
        addressTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkSaveButtonAndName();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkSaveButtonAndName();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void checkSaveButtonAndName() {
        saveButton.setEnabled(addressTextField.getDocument().getLength() > 0);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        addressTextField.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        addressLabel = new javax.swing.JLabel();
        addressTextField = new javax.swing.JTextField();
        orLabel = new javax.swing.JLabel();
        wrapperPanel = new javax.swing.JPanel();
        qrCodePanel = new javax.swing.JPanel();
        qrCodeLabel = new javax.swing.JLabel();
        abortButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();

        setOpaque(false);

        titleLabel.setFont(titleLabel.getFont().deriveFont(titleLabel.getFont().getStyle() | java.awt.Font.BOLD, titleLabel.getFont().getSize()+3));
        titleLabel.setForeground(new java.awt.Color(29, 147, 241));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Add Contact");

        addressLabel.setText("Enter Beam address:");

        addressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressTextFieldActionPerformed(evt);
            }
        });

        orLabel.setFont(orLabel.getFont().deriveFont(orLabel.getFont().getStyle() | java.awt.Font.BOLD, orLabel.getFont().getSize()+2));
        orLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orLabel.setText("or");

        wrapperPanel.setOpaque(false);

        qrCodePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(119, 119, 119)));
        qrCodePanel.setOpaque(false);
        qrCodePanel.setPreferredSize(new java.awt.Dimension(140, 140));

        qrCodeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        qrCodeLabel.setText("<html><center>Drop<br />QR Code<br />here</center></html>");

        javax.swing.GroupLayout qrCodePanelLayout = new javax.swing.GroupLayout(qrCodePanel);
        qrCodePanel.setLayout(qrCodePanelLayout);
        qrCodePanelLayout.setHorizontalGroup(
            qrCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qrCodePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(qrCodeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );
        qrCodePanelLayout.setVerticalGroup(
            qrCodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(qrCodePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(qrCodeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        wrapperPanel.add(qrCodePanel);

        abortButton.setText("Abort");
        abortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abortButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separator)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(wrapperPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addressTextField)
                            .addComponent(addressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(abortButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton))
                            .addComponent(orLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addressLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wrapperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abortButton)
                    .addComponent(saveButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        addressValidator.validate();

        if (addressValidator.isValid()) {
            model.addContact(addressValidator.getValidatedText());
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void abortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abortButtonActionPerformed
        model.abortAddContact();
    }//GEN-LAST:event_abortButtonActionPerformed

    private void addressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressTextFieldActionPerformed
        saveButton.doClick();
    }//GEN-LAST:event_addressTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abortButton;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTextField;
    private javax.swing.JLabel orLabel;
    private javax.swing.JLabel qrCodeLabel;
    private javax.swing.JPanel qrCodePanel;
    private javax.swing.JButton saveButton;
    private javax.swing.JSeparator separator;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel wrapperPanel;
    // End of variables declaration//GEN-END:variables
}
