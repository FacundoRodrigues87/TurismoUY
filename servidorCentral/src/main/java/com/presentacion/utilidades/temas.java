package com.presentacion.utilidades;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;

public class temas {
	public temas(JComboBox<String> temas) {
		super();
		this.temas = temas;
	}

	private JComboBox<String> temas;
	
	
	
	
	private void applyLightTheme() {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			SwingUtilities.updateComponentTreeUI(temas.getRootPane());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}

	private void applyDarkTheme() {
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
			SwingUtilities.updateComponentTreeUI(temas.getRootPane());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}
	
	private void applyCyanLightTheme() {
		try {
			UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
			SwingUtilities.updateComponentTreeUI(temas.getRootPane());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}
	
	private void applyCobaltTheme() {
		try {
			UIManager.setLookAndFeel(new FlatCobalt2IJTheme());
			SwingUtilities.updateComponentTreeUI(temas.getRootPane());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}
	
	private void applyGithubDarkTheme() {
		try {
			UIManager.setLookAndFeel(new FlatGitHubDarkIJTheme());
			SwingUtilities.updateComponentTreeUI(temas.getRootPane());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}
	
	private void applyAODTheme() {
		try {
			UIManager.setLookAndFeel(new FlatAtomOneDarkIJTheme());
			SwingUtilities.updateComponentTreeUI(temas.getRootPane());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}
}
