package com.presentacion.utilidades;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextField extends JTextField implements FocusListener {
    private String placeholder;
    private boolean showingPlaceholder;
    private Font placeholderFont;

    public PlaceholderTextField(String placeholder) {
    	this.placeholder = placeholder;
        this.showingPlaceholder = true;
        this.placeholderFont = new Font("Segoe UI", Font.PLAIN, 11);
        addFocusListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (showingPlaceholder && getText().isEmpty()) {
            g.setColor(Color.GRAY);
            int padding = (getHeight() - getFont().getSize()) / 2;
            g.drawString(placeholder, getInsets().left, getHeight() - padding);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (showingPlaceholder) {
            showingPlaceholder = false;
            repaint();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
            showingPlaceholder = true;
            repaint();
        }
    }
}