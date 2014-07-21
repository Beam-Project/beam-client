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
package org.beamproject.client.view.wizard;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import java.util.Observable;
import java.util.Observer;
import org.beamproject.client.Event;
import static org.beamproject.client.Event.SHOW_MAIN_WINDOW;
import org.beamproject.client.model.WizardModel;
import org.beamproject.client.util.Components;
import org.beamproject.client.view.PasswordFormPanel;

public class PasswordLayer extends javax.swing.JPanel implements Observer {

    private static final long serialVersionUID = 1L;
    private final WizardModel model;
    private final EventBus bus;
    private final PasswordFormPanel passwordForm;

    @Inject
    public PasswordLayer(WizardModel model, EventBus bus) {
        this.model = model;
        this.bus = bus;
        this.bus.register(this);
        this.passwordForm = new PasswordFormPanel(this);

        initComponents();

        rememberWarningLabel.setVisible(false);
        passwordPanel.add(passwordForm);
    }

    @Override
    public void setVisible(boolean visible) {
        Components.layoutHtmlLabels(strongPasswordLabel, passwordLabel, rememberWarningLabel);
        super.setVisible(visible);
    }

    @Override
    public void update(Observable o, Object arg) {
        boolean validPasswordEntered = (boolean) arg;
        nextButton.setEnabled(validPasswordEntered);
    }

    @Subscribe
    public void clearPasswords(Event event) {
        if (event == SHOW_MAIN_WINDOW) {
            passwordForm.resetPasswordFields();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLable = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        strongPasswordLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordPanel = new javax.swing.JPanel();
        rememberCheckBox = new javax.swing.JCheckBox();
        rememberWarningLabel = new javax.swing.JLabel();
        nextButtonPanel = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        nextButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));

        setOpaque(false);

        titleLable.setFont(titleLable.getFont().deriveFont(titleLable.getFont().getStyle() | java.awt.Font.BOLD, titleLable.getFont().getSize()+3));
        titleLable.setForeground(new java.awt.Color(29, 147, 241));
        titleLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLable.setText("Password Protection");

        strongPasswordLabel.setText("<html>We need a strong password to protect your private key and your messages.</html>");

        passwordLabel.setText("<html>Please enter your password twice:</html>");

        passwordPanel.setOpaque(false);
        passwordPanel.setLayout(new javax.swing.BoxLayout(passwordPanel, javax.swing.BoxLayout.PAGE_AXIS));

        rememberCheckBox.setText("Remember password");
        rememberCheckBox.setNextFocusableComponent(nextButtonPanel);
        rememberCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rememberCheckBoxItemStateChanged(evt);
            }
        });

        rememberWarningLabel.setText("<html>Note: This enables anyone with access to this device to do mischief with your messages and identity!</html>");

        nextButtonPanel.setOpaque(false);
        nextButtonPanel.setLayout(new javax.swing.BoxLayout(nextButtonPanel, javax.swing.BoxLayout.LINE_AXIS));
        nextButtonPanel.add(filler1);

        nextButton.setText("Finish");
        nextButton.setEnabled(false);
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        nextButtonPanel.add(nextButton);
        nextButtonPanel.add(filler2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separator, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(strongPasswordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(nextButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rememberWarningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rememberCheckBox))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(passwordPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(strongPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rememberCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rememberWarningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(nextButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rememberCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rememberCheckBoxItemStateChanged
        rememberWarningLabel.setVisible(rememberCheckBox.isSelected());
    }//GEN-LAST:event_rememberCheckBoxItemStateChanged

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        model.processPasswordLayer(passwordForm.getPassword(), rememberCheckBox.isSelected());
    }//GEN-LAST:event_nextButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton nextButton;
    private javax.swing.JPanel nextButtonPanel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPanel passwordPanel;
    private javax.swing.JCheckBox rememberCheckBox;
    private javax.swing.JLabel rememberWarningLabel;
    private javax.swing.JSeparator separator;
    private javax.swing.JLabel strongPasswordLabel;
    private javax.swing.JLabel titleLable;
    // End of variables declaration//GEN-END:variables

}
