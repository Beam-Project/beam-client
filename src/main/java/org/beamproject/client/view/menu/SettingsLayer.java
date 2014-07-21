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
import org.beamproject.client.Event;
import static org.beamproject.client.Event.ENCRYPTED_CONFIG_UNLOCKED;
import org.beamproject.client.model.MenuModel;
import static org.beamproject.client.model.MainModel.AcceptedSender;
import static org.beamproject.client.model.MainModel.AcceptedSender.*;
import static org.beamproject.client.util.ConfigKey.ACCEPTED_MESSAGE_SENDER;

@Singleton
public class SettingsLayer extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private final MenuModel model;
    private final EventBus bus;

    @Inject
    public SettingsLayer(MenuModel model, EventBus bus) {
        this.model = model;
        this.bus = bus;
        this.bus.register(this);

        initComponents();
    }

    @Subscribe
    public void loadSettings(Event event) {
        if (event == ENCRYPTED_CONFIG_UNLOCKED) {
            restoreAcceptMessagesRadioBoxes();
            restorePasswordRememberCheckBox();
        }
    }

    private void restoreAcceptMessagesRadioBoxes() {
        if (!model.getEncryptedConfig().contains(ACCEPTED_MESSAGE_SENDER)) {
            model.getEncryptedConfig().set(ACCEPTED_MESSAGE_SENDER, CONTACTS.toString());
            return;
        }

        AcceptedSender sender = restoreConfiguredSender();
        selectAcceptedSender(sender);
    }

    private AcceptedSender restoreConfiguredSender() {
        try {
            return AcceptedSender.valueOf(model.getEncryptedConfig().getAsString(ACCEPTED_MESSAGE_SENDER));
        } catch (IllegalArgumentException ex) {
            return CONTACTS;
        }
    }

    private void selectAcceptedSender(AcceptedSender sender) {
        switch (sender) {
            case CONTACTS:
                contactsRadioBox.setSelected(true);
                break;
            case EVERYONE:
                everyoneRadioBox.setSelected(true);
                break;
            default:
                contactsRadioBox.setSelected(true);
                model.getEncryptedConfig().set(ACCEPTED_MESSAGE_SENDER, CONTACTS.toString());
        }
    }

    private void restorePasswordRememberCheckBox() {
        rememberCheckBox.setSelected(model.isPasswordRemembered());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        acceptMessagesGroup = new javax.swing.ButtonGroup();
        acceptLabel = new javax.swing.JLabel();
        contactsRadioBox = new javax.swing.JRadioButton();
        everyoneRadioBox = new javax.swing.JRadioButton();
        passwordLabel = new javax.swing.JLabel();
        rememberCheckBox = new javax.swing.JCheckBox();
        changePasswordButton = new javax.swing.JButton();
        dangerZoneLabel = new javax.swing.JLabel();
        changeServerButton = new javax.swing.JButton();
        generateIdentityButton = new javax.swing.JButton();

        setOpaque(false);

        acceptLabel.setFont(acceptLabel.getFont().deriveFont(acceptLabel.getFont().getStyle() | java.awt.Font.BOLD));
        acceptLabel.setText("Accept Messages");

        acceptMessagesGroup.add(contactsRadioBox);
        contactsRadioBox.setSelected(true);
        contactsRadioBox.setText("Contacts");
        contactsRadioBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                contactsRadioBoxItemStateChanged(evt);
            }
        });

        acceptMessagesGroup.add(everyoneRadioBox);
        everyoneRadioBox.setText("Everyone");
        everyoneRadioBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                everyoneRadioBoxItemStateChanged(evt);
            }
        });

        passwordLabel.setFont(passwordLabel.getFont().deriveFont(passwordLabel.getFont().getStyle() | java.awt.Font.BOLD));
        passwordLabel.setText("Password");

        rememberCheckBox.setText("Remember");
        rememberCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rememberCheckBoxItemStateChanged(evt);
            }
        });

        changePasswordButton.setText("Change...");
        changePasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasswordButtonActionPerformed(evt);
            }
        });

        dangerZoneLabel.setFont(dangerZoneLabel.getFont().deriveFont(dangerZoneLabel.getFont().getStyle() | java.awt.Font.BOLD));
        dangerZoneLabel.setText("Danger Zone");

        changeServerButton.setText("Change Server...");
        changeServerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeServerButtonActionPerformed(evt);
            }
        });

        generateIdentityButton.setText("Generate new identity");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rememberCheckBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(changePasswordButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(generateIdentityButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(contactsRadioBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(everyoneRadioBox))
                                    .addComponent(passwordLabel)
                                    .addComponent(acceptLabel)
                                    .addComponent(dangerZoneLabel))
                                .addGap(0, 213, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(changeServerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(acceptLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactsRadioBox)
                    .addComponent(everyoneRadioBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rememberCheckBox)
                    .addComponent(changePasswordButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dangerZoneLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changeServerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generateIdentityButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rememberCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rememberCheckBoxItemStateChanged
        model.rememberPassword(rememberCheckBox.isSelected());
    }//GEN-LAST:event_rememberCheckBoxItemStateChanged

    private void changePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordButtonActionPerformed
        model.showPasswordChangeLayer();
    }//GEN-LAST:event_changePasswordButtonActionPerformed

    private void contactsRadioBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_contactsRadioBoxItemStateChanged
        updateAcceptedMessageSenders();
    }//GEN-LAST:event_contactsRadioBoxItemStateChanged

    private void everyoneRadioBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_everyoneRadioBoxItemStateChanged
        updateAcceptedMessageSenders();
    }//GEN-LAST:event_everyoneRadioBoxItemStateChanged

    private void changeServerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeServerButtonActionPerformed
        model.showServerChangeLayer();
    }//GEN-LAST:event_changeServerButtonActionPerformed

    private void updateAcceptedMessageSenders() {
        AcceptedSender sender;

        if (everyoneRadioBox.isSelected()) {
            sender = EVERYONE;
        } else {
            sender = CONTACTS;
        }

        model.setAcceptedMessageSenders(sender);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel acceptLabel;
    private javax.swing.ButtonGroup acceptMessagesGroup;
    private javax.swing.JButton changePasswordButton;
    private javax.swing.JButton changeServerButton;
    private javax.swing.JRadioButton contactsRadioBox;
    private javax.swing.JLabel dangerZoneLabel;
    private javax.swing.JRadioButton everyoneRadioBox;
    private javax.swing.JButton generateIdentityButton;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JCheckBox rememberCheckBox;
    // End of variables declaration//GEN-END:variables
}
