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
import org.beamproject.client.model.MenuModel;
import org.beamproject.client.util.validators.ServerAddressValidator;

public class ServerChangeLayer extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private final MenuModel model;
    private final EventBus bus;
    private final ServerAddressValidator addressValidator;

    @Inject
    public ServerChangeLayer(MenuModel model, EventBus bus) {
        this.model = model;
        this.bus = bus;
        this.bus.register(this);

        initComponents();

        addressValidator = new ServerAddressValidator(changedServerTextField);
    }

    private void showCurrentServer() {
        currentServerTextField.setText(model.getServer().getAddress());
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        showCurrentServer();
        changedServerTextField.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        warningLabel = new javax.swing.JLabel();
        currentServerLabel = new javax.swing.JLabel();
        currentServerTextField = new javax.swing.JTextField();
        changedServerLabel = new javax.swing.JLabel();
        changedServerTextField = new javax.swing.JTextField();
        confirmCheckbox = new javax.swing.JCheckBox();
        abortButton = new javax.swing.JButton();
        restartButton = new javax.swing.JButton();

        setOpaque(false);

        titleLabel.setFont(titleLabel.getFont().deriveFont(titleLabel.getFont().getStyle() | java.awt.Font.BOLD, titleLabel.getFont().getSize()+3));
        titleLabel.setForeground(new java.awt.Color(29, 147, 241));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Change Server");

        warningLabel.setText("<html><b>Changes of these settings result in a new Beam address, therefore you have to share the new address with your contacts!</b></html>");

        currentServerLabel.setText("Current Server Address:");

        currentServerTextField.setEditable(false);

        changedServerLabel.setText("Changed Server Address:");

        changedServerTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changedServerTextFieldActionPerformed(evt);
            }
        });

        confirmCheckbox.setText("Yes, I know what I am doing");
        confirmCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                confirmCheckboxItemStateChanged(evt);
            }
        });

        abortButton.setText("Abort");
        abortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abortButtonActionPerformed(evt);
            }
        });

        restartButton.setText("Restart Beam");
        restartButton.setEnabled(false);
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartButtonActionPerformed(evt);
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
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(warningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(currentServerTextField)
                    .addComponent(currentServerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(changedServerTextField)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(abortButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(restartButton))
                    .addComponent(confirmCheckbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(changedServerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(warningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(currentServerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(currentServerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changedServerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changedServerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmCheckbox)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abortButton)
                    .addComponent(restartButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void changedServerTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changedServerTextFieldActionPerformed
        restartButton.doClick();
    }//GEN-LAST:event_changedServerTextFieldActionPerformed

    private void restartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartButtonActionPerformed
        addressValidator.validate();

        if (addressValidator.isValid()) {
            model.processServerChange(addressValidator.getValidatedText());
        }
    }//GEN-LAST:event_restartButtonActionPerformed

    private void abortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abortButtonActionPerformed
        model.abortServerChange();
    }//GEN-LAST:event_abortButtonActionPerformed

    private void confirmCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_confirmCheckboxItemStateChanged
        restartButton.setEnabled(confirmCheckbox.isSelected());
    }//GEN-LAST:event_confirmCheckboxItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abortButton;
    private javax.swing.JLabel changedServerLabel;
    private javax.swing.JTextField changedServerTextField;
    private javax.swing.JCheckBox confirmCheckbox;
    private javax.swing.JLabel currentServerLabel;
    private javax.swing.JTextField currentServerTextField;
    private javax.swing.JButton restartButton;
    private javax.swing.JSeparator separator;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables
}
