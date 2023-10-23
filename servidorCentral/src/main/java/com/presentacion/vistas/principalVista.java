package com.presentacion.vistas;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.ImageIcon;

public class principalVista extends JPanel {

	private String selectedTheme;
	private JTextField textField;

	public principalVista(String tema) {
		setAlignmentX(Component.LEFT_ALIGNMENT);

		UIManager.put("Label.font", UIManager.getFont("Label.font").deriveFont(16f));

		this.selectedTheme = selectedTheme;
		this.setSize(874, 577);

		setupTheme();
		setLayout(null);

		JSeparator separator = new JSeparator();
		separator.setBounds(139, 422, 586, 15);
		add(separator);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBounds(52, 52, 612, 324);
		add(panel);

		JLabel lblNewLabel = new JLabel(
				"En el vibrante panorama del turismo, surge \"Turismo UY\", una empresa que personifica");
		panel.add(lblNewLabel);

		JLabel lblATravsDe = new JLabel(
				"la pasión por explorar, descubrir y conectar con destinos excepcionales. Desde sus");
		panel.add(lblATravsDe);

		JLabel lblNewLabel_1_1 = new JLabel(
				"humildes raíces en Uruguay, ha evolucionado en líder del sector, ofreciendo ");
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel(
				"A través de la plataforma los proveedores turísticos pueden gestionar y promocionar");
		panel.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel(
				"actividades turísticas, así como salidas asociadas. Los potenciales usuarios podrán");
		panel.add(lblNewLabel_1_1_1_1);

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel(
				"comprar acceso tanto a salidas puntuales en días y \r\nhorarios definidos, ");
		panel.add(lblNewLabel_1_1_1_1_1);

		JLabel lblNewLabel_1 = new JLabel("como a paquetes que incluyen varias actividades.");
		lblNewLabel_1.setToolTipText("como a paquetes que incluyen varias actividades.");
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel(
				"                                                                                                                                            \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t                  \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t          ");
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_1_1_2 = new JLabel(
				"Experiencias auténticas y responsables. Su compromiso con la innovación y la");
		panel.add(lblNewLabel_1_1_2);

		JLabel lblNewLabel_1_1_2_1 = new JLabel(
				"sostenibilidad redefine el viajar, mientras su historia inspira a aventureros a");
		panel.add(lblNewLabel_1_1_2_1);

		JLabel lblNewLabel_1_1_2_1_1 = new JLabel("explorar el mundo con nuevos ojos.");
		panel.add(lblNewLabel_1_1_2_1_1);
		Font boldFont = lblNewLabel_1_1_2_1_1.getFont().deriveFont(Font.BOLD);
		lblNewLabel_1_1_2_1_1.setFont(boldFont);

		JLabel lblNewLabel_3 = new JLabel("\r\n");
		lblNewLabel_3.setIcon(new ImageIcon(principalVista.class.getResource("/com/presentacion/res/kombi2.png")));
		lblNewLabel_3.setBounds(618, 140, 236, 220);
		add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(principalVista.class.getResource("/com/presentacion/res/wave.png")));
		lblNewLabel_4.setBounds(274, 435, 600, 344);
		add(lblNewLabel_4);

	}

	private void setupTheme() {
		if ("Light".equals(selectedTheme)) {
			applyLightTheme();
		} else if ("Dark".equals(selectedTheme)) {
			applyDarkTheme();
		} else if ("Cyan Light".equals(selectedTheme)) {
			applyCyanLightTheme();
		} else if ("Material Deep Ocean".equals(selectedTheme)) {
			applyDeepOceanTheme();
		} else if ("Github Dark".equals(selectedTheme)) {
			applyGithubDarkTheme();
		} else if ("AtomOne Dark".equals(selectedTheme)) {
			applyAODTheme();
		}
	}

	private void applyLightTheme() {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}

	private void applyDarkTheme() {
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}

	private void applyCyanLightTheme() {
		try {
			UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}

	private void applyDeepOceanTheme() {
		try {
			UIManager.setLookAndFeel(new FlatMaterialDeepOceanIJTheme());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}

	private void applyGithubDarkTheme() {
		try {
			UIManager.setLookAndFeel(new FlatGitHubDarkIJTheme());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}

	private void applyAODTheme() {
		try {
			UIManager.setLookAndFeel(new FlatAtomOneDarkIJTheme());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}
}
