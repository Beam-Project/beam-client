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

import org.beamproject.client.App;
import org.beamproject.common.Contact;
import org.beamproject.common.network.UrlAssembler;

public class AddContactDialog extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private boolean isErrorInUrlTextArea;
    private boolean isErrorInNameTextField;
    Contact contact;
    String name;

    public AddContactDialog() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        introductionLabel = new javax.swing.JLabel();
        urlScrollPane = new javax.swing.JScrollPane();
        urlTextArea = new javax.swing.JTextArea();
        addButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add a Contact");
        setResizable(false);

        introductionLabel.setText("Copy & Paste the Beam URL of the contact you want to add:");

        urlTextArea.setColumns(20);
        urlTextArea.setLineWrap(true);
        urlTextArea.setRows(5);
        urlTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                urlTextAreaKeyPressed(evt);
            }
        });
        urlScrollPane.setViewportView(urlTextArea);

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
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
                    .addComponent(urlScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(introductionLabel)
                        .addGap(0, 109, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(introductionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(urlScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        trimUrl();
        verifyUrlTextArea();

        if (!isErrorInUrlTextArea && !isErrorInNameTextField) {
            App.getController().addContact(contact);
            dispose();
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void trimUrl() {
        urlTextArea.setText(urlTextArea.getText().trim());
    }

    private void urlTextAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_urlTextAreaKeyPressed
        switch (evt.getKeyCode()) {
            case Components.TAB_KEY_CODE:
                addButton.requestFocus();
                evt.consume();
                break;
            case Components.ENTER_KEY_CODE:
                addButton.doClick();
                evt.consume();
                break;
        }
    }//GEN-LAST:event_urlTextAreaKeyPressed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    void verifyUrlTextArea() {
        try {
            contact = UrlAssembler.toContactByServerAndUserUrl(urlTextArea.getText());
            Components.setDefalutBackground(urlTextArea);
            isErrorInUrlTextArea = false;
        } catch (IllegalArgumentException ex) {
            Components.setErrorBackground(urlTextArea);
            isErrorInUrlTextArea = true;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton addButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel introductionLabel;
    private javax.swing.JScrollPane urlScrollPane;
    javax.swing.JTextArea urlTextArea;
    // End of variables declaration//GEN-END:variables
}
