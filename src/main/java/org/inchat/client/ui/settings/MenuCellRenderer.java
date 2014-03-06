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
package org.inchat.client.ui.settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

/**
 * This {@link ListCellRenderer} is used to display the menu bar in the
 * {@link SettingsWindow}. It provides only UI relevant functionalities.
 */
public class MenuCellRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = 1L;
    private final Color SELECTION_BACKGROUND_COLOR = UIManager.getColor("List.selectionBackground");
    private final Color BACKGROUND_COLOR = UIManager.getColor("List.background");

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel original = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        JLabel iconLabel = new JLabel(getIconByMenuIndex(index));
        JLabel textLabel = new JLabel(original.getText());
        textLabel.setHorizontalAlignment(CENTER);

        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(getTopFiller(), BorderLayout.NORTH);
        borderPanel.add(getTopFiller(), BorderLayout.SOUTH);
        borderPanel.add(getSideFiller(), BorderLayout.EAST);
        borderPanel.add(getSideFiller(), BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(iconLabel, BorderLayout.NORTH);
        contentPanel.add(textLabel, BorderLayout.SOUTH);
        contentPanel.setOpaque(false);

        borderPanel.add(contentPanel, BorderLayout.CENTER);

        borderPanel.setOpaque(isSelected);
        borderPanel.setBackground(isSelected ? SELECTION_BACKGROUND_COLOR : BACKGROUND_COLOR);

        return borderPanel;
    }

    private ImageIcon getIconByMenuIndex(int index) {
        String path = getImagePathByMenuIndex(index);

        try {
            InputStream stream = SettingsWindow.class.getResourceAsStream(path);
            BufferedImage image = ImageIO.read(stream);
            return new ImageIcon(image);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not load the settings window icons: " + ex.getMessage());
        }
    }

    private String getImagePathByMenuIndex(int menuIndex) {
        String imagePath = "/org/inchat/client/ui/settings/";

        switch (menuIndex) {
            case SettingsWindow.GENERAL_MENU_INDEX:
                imagePath += "general.png";
                break;
            case SettingsWindow.IDENTITY_MENU_INDEX:
                imagePath += "identity.png";
                break;
            case SettingsWindow.SECURITY_MENU_INDEX:
                imagePath += "security.png";
                break;
            case SettingsWindow.NETWORK_MENU_INDEX:
                imagePath += "network.png";
                break;
        }

        return imagePath;
    }

    private Box.Filler getTopFiller() {
        return new Box.Filler(new Dimension(0, 0), new Dimension(0, 4), new Dimension(0, 100));
    }

    private Box.Filler getSideFiller() {
        return new Box.Filler(new Dimension(24, 0), new Dimension(24, 0), new Dimension(24, 0));
    }
}
