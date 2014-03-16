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
import org.beamproject.client.ClientConfigKey;
import org.beamproject.common.Contact;
import org.beamproject.common.util.Exceptions;

/**
 *
 * @author René Bernhardsgrütter <rene.bernhardsgruetter@posteo.ch>
 */
public class MainWindow extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    final String DEFAULT_USERNAME = "Your Name";
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

    void setProfileSpecificValues() {
        String name = App.getConfig().getProperty(ClientConfigKey.participantName);
        nameButton.setText(name != null ? name : DEFAULT_USERNAME);
    }

    private void setPosition() {
        String xValue = App.getConfig().getProperty(ClientConfigKey.windowPositionX);
        String yValue = App.getConfig().getProperty(ClientConfigKey.windowPositionY);

        if (xValue != null && yValue != null) {
            int xPosition = Integer.valueOf(xValue);
            int yPosition = Integer.valueOf(yValue);
            setLocation(xPosition, yPosition);
        } else {
            setLocationRelativeTo(null);
        }
    }

    private void setSize() {
        String preferredWidth = App.getConfig().getProperty(ClientConfigKey.windowWidth);
        String preferredHeight = App.getConfig().getProperty(ClientConfigKey.windowHeight);

        if (preferredWidth != null && preferredHeight != null) {
            int width = Integer.valueOf(preferredWidth);
            int height = Integer.valueOf(preferredHeight);

            if (width >= MINIMAL_WINDOW_WIDTH_IN_PX && height >= MINIMAL_WINDOW_HEIGHT_IN_PX) {
                setSize(width, height);
                revalidate();
                repaint();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initalizeContactList() {
        contactList.setModel(App.getModel().getContactList());
    }

    public void setUsername(String name) {
        Exceptions.verifyArgumentNotEmpty(name);
        name = name.trim();
        Exceptions.verifyArgumentNotEmpty(name);

        nameButton.setText(name);
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
        topPanelHorizontalFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        addUserButton = new javax.swing.JButton();
        infoButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        contactListScrollPane = new javax.swing.JScrollPane();
        contactList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Beam");
        setMinimumSize(new java.awt.Dimension(270, 500));
        setPreferredSize(new java.awt.Dimension(270, 400));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        topPanel.setLayout(new javax.swing.BoxLayout(topPanel, javax.swing.BoxLayout.LINE_AXIS));

        avatarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/beamproject/client/ui/avatar.png"))); // NOI18N
        avatarButton.setBorderPainted(false);
        avatarButton.setFocusPainted(false);
        avatarButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        avatarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatarButtonActionPerformed(evt);
            }
        });
        topPanel.add(avatarButton);

        nameButton.setFont(nameButton.getFont().deriveFont(nameButton.getFont().getStyle() | java.awt.Font.BOLD, nameButton.getFont().getSize()-1));
        nameButton.setText("Your Name");
        nameButton.setBorderPainted(false);
        nameButton.setFocusPainted(false);
        nameButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        nameButton.setMaximumSize(new java.awt.Dimension(200, 36));
        nameButton.setMinimumSize(new java.awt.Dimension(73, 36));
        nameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameButtonActionPerformed(evt);
            }
        });
        topPanel.add(nameButton);

        statusButton.setFont(statusButton.getFont().deriveFont((statusButton.getFont().getStyle() | java.awt.Font.ITALIC), statusButton.getFont().getSize()-1));
        statusButton.setText("Offline");
        statusButton.setBorderPainted(false);
        statusButton.setFocusPainted(false);
        statusButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        statusButton.setMaximumSize(new java.awt.Dimension(48, 36));
        statusButton.setMinimumSize(new java.awt.Dimension(48, 36));
        statusButton.setPreferredSize(new java.awt.Dimension(48, 36));
        topPanel.add(statusButton);
        topPanel.add(topPanelHorizontalFiller);

        addUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/beamproject/client/ui/addContact.png"))); // NOI18N
        addUserButton.setBorderPainted(false);
        addUserButton.setFocusPainted(false);
        addUserButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });
        topPanel.add(addUserButton);

        infoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/beamproject/client/ui/infoButton.png"))); // NOI18N
        infoButton.setBorderPainted(false);
        infoButton.setFocusPainted(false);
        infoButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        infoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoButtonActionPerformed(evt);
            }
        });
        topPanel.add(infoButton);

        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/beamproject/client/ui/menu.png"))); // NOI18N
        settingsButton.setBorderPainted(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });
        topPanel.add(settingsButton);

        getContentPane().add(topPanel);

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

        getContentPane().add(contactListScrollPane);

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

        App.getConfig().setProperty(ClientConfigKey.windowPositionX, "" + xPosition);
        App.getConfig().setProperty(ClientConfigKey.windowPositionY, "" + yPosition);
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

        App.getConfig().setProperty(ClientConfigKey.windowWidth, "" + width);
        App.getConfig().setProperty(ClientConfigKey.windowHeight, "" + height);
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
    private javax.swing.Box.Filler topPanelHorizontalFiller;
    // End of variables declaration//GEN-END:variables
}
