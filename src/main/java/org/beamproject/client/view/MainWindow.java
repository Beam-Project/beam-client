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

import org.beamproject.client.view.menu.InfoLayer;
import org.beamproject.client.view.menu.StatusLayer;
import org.beamproject.client.view.menu.SettingsLayer;
import org.beamproject.client.view.wizard.AddressLayer;
import org.beamproject.client.view.wizard.WelcomeLayer;
import org.beamproject.client.view.wizard.PasswordLayer;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import org.beamproject.client.Event;
import static org.beamproject.client.Event.*;
import org.beamproject.client.model.ChatModel;
import org.beamproject.client.model.MainModel;
import org.beamproject.client.util.Components;
import org.beamproject.client.util.validators.RegexValidator;

@Singleton
public class MainWindow extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private final MainModel model;
    private final ChatModel chatModel;
    private final EventBus bus;
    private final BootstrapLayer bootstrapLayer;
    private JPanel layer;
    @Inject
    private WelcomeLayer welcomeLayer;
    @Inject
    private AddressLayer addressLayer;
    @Inject
    private PasswordLayer passwordLayer;
    @Inject
    private UnlockLayer unlockLayer;
    @Inject
    private PasswordChangeLayer passwordChangeLayer;
    @Inject
    private ServerChangeLayer serverChangeLayer;
    @Inject
    private AddContactLayer addContactLayer;
    @Inject
    private InfoLayer infoLayer;
    @Inject
    private StatusLayer statusLayer;
    @Inject
    private SettingsLayer settingsLayer;
    private RegexValidator usernameValidator;

    @Inject
    public MainWindow(MainModel model, ChatModel chatModel, EventBus bus, BootstrapLayer bootstrapLayer) {
        this.model = model;
        this.chatModel = chatModel;
        this.bus = bus;
        this.bootstrapLayer = bootstrapLayer;
        this.bus.register(this);

        initComponents();
        showBootstrapLayer();
        configureUsernameTextField();
        Components.setIcons(this);
    }

    private void showBootstrapLayer() {
        layer = new LayerPanel(basePanel, bootstrapLayer, true);
    }

    private void configureUsernameTextField() {
        usernameValidator = new RegexValidator(usernameTextField, ".{3,}");
        usernameTextField.setVisible(false);
    }

    @Subscribe
    public void showStrechedLayers(Event event) {
        JPanel newLayer;

        switch (event) {
            case SHOW_WIZARD_WELCOME_LAYER:
                newLayer = welcomeLayer;
                break;
            case SHOW_WIZARD_ADDRESS_LAYER:
                newLayer = addressLayer;
                break;
            case SHOW_WIZARD_PASSWORD_LAYER:
                newLayer = passwordLayer;
                break;
            case SHOW_UNLOCK_LAYER:
                newLayer = unlockLayer;
                break;
            case SHOW_PASSWORD_CHANGE_LAYER:
                newLayer = passwordChangeLayer;
                break;
            case SHOW_SERVER_CHANGE_LAYER:
                newLayer = serverChangeLayer;
                break;
            case SHOW_ADD_CONTACT_LAYER:
                newLayer = addContactLayer;
                break;
            default:
                return;
        }

        disableLayers();
        layer = new LayerPanel(basePanel, newLayer, true);
    }

    @Subscribe
    public void showMainWindow(Event event) {
        if (event == SHOW_MAIN_WINDOW) {
            disableLayers();
            deselectAllButtons();
        }
    }

    @Subscribe
    public void updateUsername(Event event) {
        if (event == ENCRYPTED_CONFIG_UNLOCKED) {
            usernameLabel.setText(model.getUser().getUsername());
        }
    }

    @Subscribe
    public void dispose(Event event) {
        if (event == DISPOSE) {
            dispose();
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        basePanel = new javax.swing.JLayeredPane();
        avatarLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        statusButton = new javax.swing.JToggleButton();
        infoButton = new javax.swing.JToggleButton();
        settingsButton = new javax.swing.JToggleButton();
        contactScrollPane = new javax.swing.JScrollPane();
        contactList = new javax.swing.JList();
        addContactButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Beam");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(240, 378));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        avatarLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/beamproject/client/view/24x24.png"))); // NOI18N

        usernameLabel.setText("Your Name");
        usernameLabel.setMinimumSize(new java.awt.Dimension(20, 14));
        usernameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usernameLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                usernameLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                usernameLabelMouseExited(evt);
            }
        });

        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextFieldActionPerformed(evt);
            }
        });
        usernameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameTextFieldFocusLost(evt);
            }
        });
        usernameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usernameTextFieldKeyTyped(evt);
            }
        });

        statusButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/beamproject/client/view/status-offline.png"))); // NOI18N
        statusButton.setBorderPainted(false);
        statusButton.setFocusPainted(false);
        statusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusButtonActionPerformed(evt);
            }
        });

        infoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/beamproject/client/view/info.png"))); // NOI18N
        infoButton.setBorderPainted(false);
        infoButton.setFocusPainted(false);
        infoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoButtonActionPerformed(evt);
            }
        });

        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/beamproject/client/view/settings.png"))); // NOI18N
        settingsButton.setBorderPainted(false);
        settingsButton.setFocusPainted(false);
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });

        contactScrollPane.setBorder(null);
        contactScrollPane.setViewportBorder(null);

        contactList.setBorder(null);
        contactList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        contactScrollPane.setViewportView(contactList);

        addContactButton.setText("Add Contact...");
        addContactButton.setBorderPainted(false);
        addContactButton.setFocusPainted(false);
        addContactButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addContactButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout basePanelLayout = new javax.swing.GroupLayout(basePanel);
        basePanel.setLayout(basePanelLayout);
        basePanelLayout.setHorizontalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basePanelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(avatarLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(usernameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(settingsButton)
                .addGap(4, 4, 4))
            .addComponent(contactScrollPane)
            .addGroup(basePanelLayout.createSequentialGroup()
                .addComponent(addContactButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        basePanelLayout.setVerticalGroup(
            basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basePanelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(basePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(settingsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(avatarLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contactScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(addContactButton))
        );
        basePanel.setLayer(avatarLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        basePanel.setLayer(usernameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        basePanel.setLayer(usernameTextField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        basePanel.setLayer(statusButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        basePanel.setLayer(infoButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        basePanel.setLayer(settingsButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        basePanel.setLayer(contactScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        basePanel.setLayer(addContactButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(basePanel, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(basePanel)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        updateLayerVisibility(settingsButton, settingsLayer);
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void infoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoButtonActionPerformed
        updateLayerVisibility(infoButton, infoLayer);
    }//GEN-LAST:event_infoButtonActionPerformed

    private void statusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusButtonActionPerformed
        updateLayerVisibility(statusButton, statusLayer);
    }//GEN-LAST:event_statusButtonActionPerformed

    private void usernameLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usernameLabelMouseEntered
        Font bold = usernameLabel.getFont().deriveFont(Font.BOLD);
        usernameLabel.setFont(bold);
    }//GEN-LAST:event_usernameLabelMouseEntered

    private void usernameLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usernameLabelMouseExited
        Font plain = usernameLabel.getFont().deriveFont(Font.PLAIN);
        usernameLabel.setFont(plain);
    }//GEN-LAST:event_usernameLabelMouseExited

    private void usernameLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usernameLabelMouseClicked
        usernameLabel.setVisible(false);
        usernameTextField.setVisible(true);
        usernameTextField.setText(usernameLabel.getText());
        usernameTextField.requestFocus();
        usernameTextField.selectAll();
    }//GEN-LAST:event_usernameLabelMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        setVisible(false);
        model.shutdown();
    }//GEN-LAST:event_formWindowClosing

    private void usernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextFieldActionPerformed
        usernameValidator.validate();

        if (usernameValidator.isValid()) {
            model.setUsername(usernameValidator.getValidatedText());
            usernameLabel.setText(usernameValidator.getValidatedText());
            showUsernameLabel();
        }
    }//GEN-LAST:event_usernameTextFieldActionPerformed

    private void usernameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameTextFieldFocusLost
        showUsernameLabel();
    }//GEN-LAST:event_usernameTextFieldFocusLost

    private void usernameTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTextFieldKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            showUsernameLabel();
        }
    }//GEN-LAST:event_usernameTextFieldKeyTyped

    private void addContactButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addContactButtonActionPerformed
        chatModel.showAddContactLayer();
    }//GEN-LAST:event_addContactButtonActionPerformed

    private void showUsernameLabel() {
        usernameTextField.setVisible(false);
        usernameLabel.setVisible(true);

        Font plain = usernameTextField.getFont().deriveFont(Font.PLAIN);
        usernameLabel.setFont(plain);
    }

    private void updateLayerVisibility(JToggleButton button, JPanel layerContent) {
        disableLayers();

        if (button.isSelected()) {
            deselectAllButtonsExcept(button);
            layer = new LayerPanel(button, basePanel, layerContent, false);
            layer.setVisible(true);
        }
    }

    private void disableLayers() {
        if (layer != null) {
            layer.setVisible(false);
        }
    }

    private void deselectAllButtons() {
        statusButton.setSelected(false);
        infoButton.setSelected(false);
        settingsButton.setSelected(false);
    }

    private void deselectAllButtonsExcept(JToggleButton button) {
        statusButton.setSelected(button == statusButton);
        infoButton.setSelected(button == infoButton);
        settingsButton.setSelected(button == settingsButton);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addContactButton;
    private javax.swing.JLabel avatarLabel;
    private javax.swing.JLayeredPane basePanel;
    private javax.swing.JList contactList;
    private javax.swing.JScrollPane contactScrollPane;
    private javax.swing.JToggleButton infoButton;
    private javax.swing.JToggleButton settingsButton;
    private javax.swing.JToggleButton statusButton;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables

}
