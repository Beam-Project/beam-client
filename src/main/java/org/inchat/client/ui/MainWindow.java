/*
 * Copyright (C) 2013, 2014 inchat.org
 *
 * This file is part of inchat-client.
 *
 * inchat-client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * inchat-client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.inchat.client.ui;

import org.inchat.client.App;
import org.inchat.common.Config;
import org.inchat.common.Contact;
import org.inchat.common.util.Exceptions;

/**
 *
 * @author René Bernhardsgrütter <rene.bernhardsgruetter@posteo.ch>
 */
public class MainWindow extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private final int DOUBLE_CLICK_NUMBER = 2;
    private final int MINIMAL_WINDOW_WIDTH_IN_PX = 260;
    private final int MINIMAL_WINDOW_HEIGHT_IN_PX = 400;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        setProfileSpecificValues();
        setPosition();
        setSize();
        initalizeContactList();
    }

    private void setPosition() {
        String xValue = Config.getProperty(Config.Key.windowPositionX);
        String yValue = Config.getProperty(Config.Key.windowPositionY);

        if (xValue != null && yValue != null) {
            int xPosition = Integer.valueOf(xValue);
            int yPosition = Integer.valueOf(yValue);
            setLocation(xPosition, yPosition);
        } else {
            setLocationRelativeTo(null);
        }
    }

    private void setSize() {
        String width = Config.getProperty(Config.Key.windowWidth);
        String height = Config.getProperty(Config.Key.windowHeight);

        if (width != null && height != null) {
            int windowWidth = Integer.valueOf(width);
            int windowHeight = Integer.valueOf(height);

            if (windowWidth >= MINIMAL_WINDOW_WIDTH_IN_PX
                    && windowHeight >= MINIMAL_WINDOW_HEIGHT_IN_PX) {
                setSize(windowWidth, windowHeight);
                revalidate();
            }
        }
    }

    private void setProfileSpecificValues() {
        String name = Config.getProperty(Config.Key.participantName);
        nameButton.setText(name != null ? name : "");
    }

    public void setUsername(String name) {
        Exceptions.verifyArgumentNotEmpty(name);
        name = name.trim();
        Exceptions.verifyArgumentNotEmpty(name);

        nameButton.setText(name);
    }

    @SuppressWarnings("unchecked")
    private void initalizeContactList() {
        contactList.setModel(App.getModel().getContactList());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topPanel = new javax.swing.JPanel();
        avatarButton = new javax.swing.JButton();
        nameButton = new javax.swing.JButton();
        statusButton = new javax.swing.JButton();
        infoButton = new javax.swing.JButton();
        addUserButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        contactListScrollPane = new javax.swing.JScrollPane();
        contactList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("inchat");
        setMinimumSize(new java.awt.Dimension(260, 400));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        avatarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/inchat/client/ui/avatar.png"))); // NOI18N
        avatarButton.setBorderPainted(false);
        avatarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatarButtonActionPerformed(evt);
            }
        });

        nameButton.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        nameButton.setText("Name");
        nameButton.setBorderPainted(false);
        nameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameButtonActionPerformed(evt);
            }
        });

        statusButton.setFont(new java.awt.Font("Ubuntu", 2, 12)); // NOI18N
        statusButton.setText("Offline");
        statusButton.setBorderPainted(false);

        infoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/inchat/client/ui/infoButton.png"))); // NOI18N
        infoButton.setBorderPainted(false);
        infoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoButtonActionPerformed(evt);
            }
        });

        addUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/inchat/client/ui/addContact.png"))); // NOI18N
        addUserButton.setBorderPainted(false);
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/inchat/client/ui/menu.png"))); // NOI18N
        settingsButton.setBorderPainted(false);
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addComponent(avatarButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(infoButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addUserButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(settingsButton))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addUserButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(settingsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(avatarButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        contactList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        contactList.setMaximumSize(new java.awt.Dimension(999999999, 99999999));
        contactList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactListMouseClicked(evt);
            }
        });
        contactListScrollPane.setViewportView(contactList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contactListScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contactListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        App.getController().showSettingsWindow();
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void avatarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatarButtonActionPerformed

    }//GEN-LAST:event_avatarButtonActionPerformed

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        int xPosition = evt.getComponent().getX();
        int yPosition = evt.getComponent().getY();

        Config.setProperty(Config.Key.windowPositionX, "" + xPosition);
        Config.setProperty(Config.Key.windowPositionY, "" + yPosition);
    }//GEN-LAST:event_formComponentMoved

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        AddContactDialog dialog = new AddContactDialog();
        Frames.setIcons(dialog);
        dialog.setVisible(true);
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void contactListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactListMouseClicked
        if (evt.getClickCount() == DOUBLE_CLICK_NUMBER) {
            Contact selectedContect = (Contact) contactList.getSelectedValue();
            App.getController().openConversationWindow(selectedContect);
        }
    }//GEN-LAST:event_contactListMouseClicked

    private void nameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameButtonActionPerformed
        App.getController().showNameInSettingsWindow();
    }//GEN-LAST:event_nameButtonActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        int width = evt.getComponent().getWidth();
        int height = evt.getComponent().getHeight();

        Config.setProperty(Config.Key.windowWidth, "" + width);
        Config.setProperty(Config.Key.windowHeight, "" + height);
    }//GEN-LAST:event_formComponentResized

    private void infoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoButtonActionPerformed
        App.getController().showInfoWindow();
    }//GEN-LAST:event_infoButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUserButton;
    private javax.swing.JButton avatarButton;
    javax.swing.JList contactList;
    private javax.swing.JScrollPane contactListScrollPane;
    javax.swing.JButton infoButton;
    javax.swing.JButton nameButton;
    javax.swing.JButton settingsButton;
    javax.swing.JButton statusButton;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
