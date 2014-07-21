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
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import java.util.Observable;
import java.util.Observer;
import org.beamproject.client.Event;
import static org.beamproject.client.Event.*;
import org.beamproject.client.model.MenuModel;
import org.beamproject.client.util.validators.Validator;

public class PasswordChangeLayer extends javax.swing.JPanel implements Observer {

    private static final long serialVersionUID = 1L;
    private final MenuModel model;
    private final EventBus bus;
    private final PasswordFormPanel passwordForm;

    @Inject
    public PasswordChangeLayer(MenuModel model, EventBus bus) {
        this.model = model;
        this.bus = bus;
        this.bus.register(this);
        this.passwordForm = new PasswordFormPanel(this);

        initComponents();

        passwordPanel.add(passwordForm);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        oldPasswordField.requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
        boolean validNewPasswordEntered = (boolean) arg;
        boolean saveButtonUsable = validNewPasswordEntered
                && oldPasswordField.getDocument().getLength() > 0;

        saveButton.setEnabled(saveButtonUsable);
    }

    @Subscribe
    public void reactToWrongOldPassword(Event event) {
        if (event == PASSWORD_CHANGE_WRONG_PASSWORD) {
            oldPasswordField.setBackground(Validator.ERROR_BACKGROUND);
        }
    }

    @Subscribe
    public void clearPasswords(Event event) {
        if (event == SHOW_MAIN_WINDOW) {
            oldPasswordField.setText("");
            passwordForm.resetPasswordFields();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLable = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        oldPasswordLabel = new javax.swing.JLabel();
        oldPasswordField = new javax.swing.JPasswordField();
        abortButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        enterPasswordLabel = new javax.swing.JLabel();
        passwordPanel = new javax.swing.JPanel();

        setOpaque(false);

        titleLable.setFont(titleLable.getFont().deriveFont(titleLable.getFont().getStyle() | java.awt.Font.BOLD, titleLable.getFont().getSize()+3));
        titleLable.setForeground(new java.awt.Color(29, 147, 241));
        titleLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLable.setText("Change Password");

        oldPasswordLabel.setText("<html>Enter the old password:</html>");

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

        enterPasswordLabel.setText("<html>Enter your new password twice:</html>");

        passwordPanel.setOpaque(false);
        passwordPanel.setLayout(new javax.swing.BoxLayout(passwordPanel, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separator)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titleLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(abortButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton))
                    .addComponent(oldPasswordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(oldPasswordField)
                    .addComponent(enterPasswordLabel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(oldPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(oldPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(enterPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(abortButton)
                    .addComponent(saveButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void abortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abortButtonActionPerformed
        model.abortPasswordChange();
    }//GEN-LAST:event_abortButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        model.processPasswordChange(oldPasswordField.getPassword(), passwordForm.getPassword());
    }//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abortButton;
    private javax.swing.JLabel enterPasswordLabel;
    private javax.swing.JPasswordField oldPasswordField;
    private javax.swing.JLabel oldPasswordLabel;
    private javax.swing.JPanel passwordPanel;
    private javax.swing.JButton saveButton;
    private javax.swing.JSeparator separator;
    private javax.swing.JLabel titleLable;
    // End of variables declaration//GEN-END:variables

}
