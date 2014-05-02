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

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.beamproject.common.Contact;
import org.beamproject.common.util.Exceptions;

public class ConversationWindow extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private final String AND_YOU = "and you";
    Contact contact;
    DefaultListModel<String> messagesModel;

    public ConversationWindow() {
        initComponents();
        initMessages();
        setLocationRelativeTo(null);
        messageTextArea.requestFocus();
    }

    @SuppressWarnings({"unchecked"})
    private void initMessages() {
        messagesModel = new DefaultListModel<>();
        messages.setModel(messagesModel);
        messages.setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(
                    final JList list,
                    final Object value,
                    final int index,
                    final boolean isSelected,
                    final boolean hasFocus) {
                final String text = (String) value;
                final JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());

                final JPanel iconPanel = new JPanel(new BorderLayout());
                final JLabel label = new JLabel(""); //<-- this will be an icon instead of a text
                iconPanel.add(label, BorderLayout.NORTH);
                panel.add(iconPanel, BorderLayout.WEST);

                final JTextArea textArea = new JTextArea();
                textArea.setText(text);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setRows(textArea.getLineCount());
                textArea.validate();
                panel.add(textArea, BorderLayout.CENTER);

                return panel;
            }
        });
    }

    public void setContact(Contact contact) {
        Exceptions.verifyArgumentsNotNull(contact);

        this.contact = contact;
        updateNames();
    }

    void updateNames() {
        namesLabel.setText(contact.getName() + " " + AND_YOU);
    }

    public Contact getContact() {
        return contact;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        namesLabel = new javax.swing.JLabel();
        messageScrollPane = new javax.swing.JScrollPane();
        messageTextArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        messagesScrollPane = new javax.swing.JScrollPane();
        messages = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Conversation");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(400, 400));

        namesLabel.setFont(namesLabel.getFont().deriveFont(namesLabel.getFont().getStyle() | java.awt.Font.BOLD));
        namesLabel.setText("Names");

        messageTextArea.setColumns(20);
        messageTextArea.setLineWrap(true);
        messageTextArea.setRows(4);
        messageTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageTextAreaKeyPressed(evt);
            }
        });
        messageScrollPane.setViewportView(messageTextArea);

        sendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/beamproject/client/ui/sendButton.png"))); // NOI18N
        sendButton.setBorderPainted(false);
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        messages.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        messagesScrollPane.setViewportView(messages);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(messageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(namesLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(messagesScrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(namesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messagesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(messageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        String message = messageTextArea.getText().trim();

        if (message.length() > 0) {
            updateWindowWithMessage(message);
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    void updateWindowWithMessage(String message) {
        messagesModel.addElement(message);
        messages.ensureIndexIsVisible(messagesModel.getSize() - 1);
        messageTextArea.setText("");
    }

    private void messageTextAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageTextAreaKeyPressed
        switch (evt.getKeyCode()) {
            case Components.TAB_KEY_CODE:
                sendButton.requestFocus();
                evt.consume();
                break;
            case Components.ENTER_KEY_CODE:
                sendButton.doClick();
                evt.consume();
                break;
        }
    }//GEN-LAST:event_messageTextAreaKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane messageScrollPane;
    javax.swing.JTextArea messageTextArea;
    javax.swing.JList messages;
    private javax.swing.JScrollPane messagesScrollPane;
    javax.swing.JLabel namesLabel;
    javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
