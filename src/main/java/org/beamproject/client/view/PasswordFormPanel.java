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

import java.awt.Color;
import java.util.Arrays;
import java.util.Observer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.beamproject.client.util.PasswordStrenghtMeter;

public class PasswordFormPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private static final String[] PASSWORD_STRENGTHS = {"Very weak password", "Weak password", "Good password", "Strong password", "Very strong password"};
    private static final Color[] PASSWORD_STRENGTH_COLORS = {new Color(255, 2, 2), new Color(255, 132, 2), new Color(223, 239, 16), new Color(116, 179, 39), new Color(1, 179, 1)};
    private final Observer observer;
    private boolean isPasswordOkay;

    public PasswordFormPanel(Observer observer) {
        this.observer = observer;

        initComponents();
        registerDocumentListenerForPasswordFields();
        hideLazyLabels();
    }

    private void registerDocumentListenerForPasswordFields() {
        DocumentListener listener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                checkPasswordStrengthAndEquality();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkPasswordStrengthAndEquality();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };
        password1PasswordField.getDocument().addDocumentListener(listener);
        password2PasswordField.getDocument().addDocumentListener(listener);
    }

    private void hideLazyLabels() {
        passwordsMatchLabel.setVisible(false);
        textSeparatorLabel.setVisible(false);
        passwordStrengthLabel.setVisible(false);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        password1PasswordField.requestFocus();
    }

    private void checkPasswordStrengthAndEquality() {
        checkPasswordStrength();
        checkPasswordEquality();
        notifyObservers();
    }

    private void checkPasswordStrength() {
        char[] password1 = password1PasswordField.getPassword();
        boolean showStrengthText = password1.length > 0;
        int score = PasswordStrenghtMeter.checkStrength(password1);

        passwordStrengthLabel.setVisible(showStrengthText);
        passwordStrengthLabel.setText(PASSWORD_STRENGTHS[score]);
        passwordStrengthLabel.setForeground(PASSWORD_STRENGTH_COLORS[score]);

        Arrays.fill(password1, (char) 0);
    }

    private void checkPasswordEquality() {
        char[] password1 = password1PasswordField.getPassword();
        char[] password2 = password2PasswordField.getPassword();

        boolean doPasswordsMatch = Arrays.equals(password1, password2);
        isPasswordOkay = password1.length > 0 && password2.length > 0 && doPasswordsMatch;
        textSeparatorLabel.setVisible(isPasswordOkay);
        passwordsMatchLabel.setVisible(isPasswordOkay);

        Arrays.fill(password1, (char) 0);
        Arrays.fill(password2, (char) 0);
    }

    private void notifyObservers() {
        observer.update(null, isPasswordOkay);
    }

    public char[] getPassword() {
        return password1PasswordField.getPassword();
    }

    public void resetPasswordFields() {
        password1PasswordField.setText("");
        password2PasswordField.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        password1PasswordField = new javax.swing.JPasswordField();
        password2PasswordField = new javax.swing.JPasswordField();
        passwordStrengthLabel = new javax.swing.JLabel();
        textSeparatorLabel = new javax.swing.JLabel();
        passwordsMatchLabel = new javax.swing.JLabel();

        setOpaque(false);

        passwordStrengthLabel.setFont(passwordStrengthLabel.getFont().deriveFont(passwordStrengthLabel.getFont().getStyle() | java.awt.Font.BOLD));
        passwordStrengthLabel.setText("Good password");

        textSeparatorLabel.setFont(textSeparatorLabel.getFont().deriveFont(textSeparatorLabel.getFont().getStyle() | java.awt.Font.BOLD));
        textSeparatorLabel.setText("–");

        passwordsMatchLabel.setFont(passwordsMatchLabel.getFont().deriveFont(passwordsMatchLabel.getFont().getStyle() | java.awt.Font.BOLD));
        passwordsMatchLabel.setText("matching");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(password1PasswordField)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(passwordStrengthLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textSeparatorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordsMatchLabel)
                        .addGap(0, 258, Short.MAX_VALUE))
                    .addComponent(password2PasswordField))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(password1PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password2PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordStrengthLabel)
                    .addComponent(textSeparatorLabel)
                    .addComponent(passwordsMatchLabel))
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField password1PasswordField;
    private javax.swing.JPasswordField password2PasswordField;
    private javax.swing.JLabel passwordStrengthLabel;
    private javax.swing.JLabel passwordsMatchLabel;
    private javax.swing.JLabel textSeparatorLabel;
    // End of variables declaration//GEN-END:variables
}
