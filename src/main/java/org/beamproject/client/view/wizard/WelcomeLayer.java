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
import com.google.inject.Inject;
import org.beamproject.client.model.WizardModel;
import org.beamproject.client.util.Components;
import org.beamproject.client.util.validators.ServerAddressValidator;
import org.beamproject.client.util.validators.RegexValidator;

public class WelcomeLayer extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private final WizardModel model;
    private final EventBus bus;
    private final RegexValidator usernameValidator;
    private final ServerAddressValidator activationCodeValidator;

    @Inject
    public WelcomeLayer(WizardModel model, EventBus bus) {
        this.model = model;
        this.bus = bus;
        this.bus.register(this);

        initComponents();
        usernameValidator = new RegexValidator(usernameTextField, ".{3,}");
        activationCodeValidator = new ServerAddressValidator(activationCodeTextField);
    }

    @Override
    public void setVisible(boolean visible) {
        Components.layoutHtmlLabels(introductionLabel, usernameLabel, activationCodeLabel, activationCodeExampleLabel);
        super.setVisible(visible);
        usernameTextField.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        welcomeLabel = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        introductionLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        activationCodeLabel = new javax.swing.JLabel();
        activationCodeTextField = new javax.swing.JTextField();
        activationCodeExampleLabel = new javax.swing.JLabel();
        nextPanel = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        nextButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));

        setOpaque(false);

        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(welcomeLabel.getFont().getStyle() | java.awt.Font.BOLD, welcomeLabel.getFont().getSize()+3));
        welcomeLabel.setForeground(new java.awt.Color(29, 147, 241));
        welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLabel.setText("Welcome to Beam!");

        introductionLabel.setText("<html>Before you can use Beam, we need to create your profile.</html>");
        introductionLabel.setDoubleBuffered(true);

        usernameLabel.setText("<html>Please enter your name:</html>");

        usernameTextField.setText("rené");
        usernameTextField.setNextFocusableComponent(activationCodeTextField);
        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextFieldActionPerformed(evt);
            }
        });

        activationCodeLabel.setText("<html>Enter the <b>Activation Code</b> of your server:</html>");

        activationCodeTextField.setText("beam:z7dBYZ1SNpTr8URsQVxzFg1hWvG33coDFBpxztn575Fadkatsp1wWRJS51s6ggctnXQymxkGNj7Hzo9rUVQwjvAoAfLv6ooC4WULsYWkduyiLAscmJikbDLZUN2rQNN3XQoFTUQdtcA7kxskitimpJYxv5dfJSCKPkBHKQ6cLwansBsxM6bW7CbQR4JrH1HVkQrqJxR4qQdsKiJ7gxJ6dqF48hRwrVxH28LSVFKL");
        activationCodeTextField.setNextFocusableComponent(nextButton);
        activationCodeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activationCodeTextFieldActionPerformed(evt);
            }
        });

        activationCodeExampleLabel.setFont(activationCodeExampleLabel.getFont().deriveFont((activationCodeExampleLabel.getFont().getStyle() | java.awt.Font.ITALIC)));
        activationCodeExampleLabel.setText("Example: beam:z7dBYZ1S9xs...");

        nextPanel.setOpaque(false);
        nextPanel.setLayout(new javax.swing.BoxLayout(nextPanel, javax.swing.BoxLayout.LINE_AXIS));
        nextPanel.add(filler1);

        nextButton.setText("Next");
        nextButton.setNextFocusableComponent(usernameTextField);
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        nextPanel.add(nextButton);
        nextPanel.add(filler2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separator)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(welcomeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nextPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(introductionLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(activationCodeLabel, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(activationCodeTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(activationCodeExampleLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(welcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(introductionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activationCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activationCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activationCodeExampleLabel)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(nextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        usernameValidator.validate();
        activationCodeValidator.validate();

        if (usernameValidator.isValid() && activationCodeValidator.isValid()) {
            model.processWelcomeLayer(usernameTextField.getText(), activationCodeTextField.getText());
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void usernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextFieldActionPerformed
        nextButton.doClick();
    }//GEN-LAST:event_usernameTextFieldActionPerformed

    private void activationCodeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activationCodeTextFieldActionPerformed
        nextButton.doClick();
    }//GEN-LAST:event_activationCodeTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activationCodeExampleLabel;
    private javax.swing.JLabel activationCodeLabel;
    private javax.swing.JTextField activationCodeTextField;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel introductionLabel;
    private javax.swing.JButton nextButton;
    private javax.swing.JPanel nextPanel;
    private javax.swing.JSeparator separator;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
