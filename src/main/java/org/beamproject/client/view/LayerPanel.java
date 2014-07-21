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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;

public class LayerPanel extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;
    private JComponent anchor;
    private final JLayeredPane parent;
    private final Component child;
    private boolean stretchLayer = false;

    public LayerPanel(JLayeredPane parent, Component child, boolean stretchLayer) {
        this(new JComponent() {
            private static final long serialVersionUID = 1L;

            @Override
            public Rectangle getBounds() {
                return new Rectangle(0, 0, 0, 0);
            }
        }, parent, child, stretchLayer);
    }

    public LayerPanel(JComponent anchor, JLayeredPane parent, Component child, boolean stretchLayer) {
        this.anchor = anchor;
        this.parent = parent;
        this.child = child;
        this.stretchLayer = stretchLayer;

        initComponents();
        configureParent();
        configureChild();
        updateSize();
    }

    private void configureParent() {
        parent.add(this, JLayeredPane.POPUP_LAYER);

        for (ComponentListener listener : parent.getComponentListeners()) {
            parent.removeComponentListener(listener);
        }

        parent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent evt) {
                // repaint();
            }
        });
    }

    private void configureChild() {
        add(child, BorderLayout.CENTER);
        child.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateSize();
        validate();
    }

    private void updateSize() {
        final int xPosition = parent.getBounds().x;
        final int yPosition = anchor.getBounds().y + anchor.getBounds().height;
        final int width = parent.getBounds().width;
        final int stretchedHeight = parent.getBounds().height;
        final int unstretchedHeight = child.getPreferredSize().height;
        final int height = stretchLayer ? stretchedHeight : unstretchedHeight;

        setBounds(xPosition, yPosition, width, height);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());
        add(jSeparator2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Consume this event, then otherwise one could click through the panel.
     *
     * @param evt A mouse event, performed above this panel.
     */
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        evt.consume();
    }//GEN-LAST:event_formMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
