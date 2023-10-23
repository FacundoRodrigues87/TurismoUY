package com.presentacion.vistas;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;

import com.sc.Fabrica;
import com.sc.datatypes.*;
import com.sc.interfaces.IDepartamentoController;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class departamentosVista extends JPanel {

	private String selectedTheme;

	public departamentosVista(String tema) {
    	
    	UIManager.put("Label.font", UIManager.getFont("Label.font").deriveFont(16f));

        this.selectedTheme = tema;
        this.setSize(874, 577);
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds(0, 0, 874, 577);
        add(panel);
        panel.setLayout(null);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setBounds(101, 11, 671, 527);
        panel.add(center);
        center.setLayout(null);

        String[] columnNames = {"Nombre", "Descripcion", "Url", "Cant. Actividades"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            boolean[] columnEditables = new boolean[]{false, false, false, false};
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

        
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 90, 671, 437);
        center.add(scrollPane);

        table.setRowSorter(sorter);

        IDepartamentoController IDC = (IDepartamentoController) Fabrica.getInstance().getIDepartamentoController();

		List<dataDepartamento> deptos = IDC.listarDepartamentos();

		if (deptos != null) {
			for (dataDepartamento depto : deptos) {
				String nombre = depto.getNombre();
				String descripcion = depto.getDescripcion();
				String url = depto.getUrl();
				int cantidad = 0;
				if (depto.getActividades() != null) {
					cantidad = depto.getActividades().size();
				}
				model.addRow(new Object[] { nombre, descripcion, url, cantidad });
			}
		}
		
        center.add(scrollPane);
        
        JLabel titulo = new JLabel("Departamentos");
        titulo.setBounds(0, 0, 671, 40);
        titulo.putClientProperty("FlatLaf.styleClass", "h1");
        center.add(titulo);
        
        JLabel descripcion = new JLabel("Lista y crea los departamentos que se manejen en el sistema");
        descripcion.setBounds(0, 39, 671, 40);
        center.add(descripcion);
        
        JButton btnNewDepartamento = new JButton("Crear un nuevo Departamento");
        btnNewDepartamento.putClientProperty( "JButton.buttonType", "roundRect" );
        btnNewDepartamento.setBounds(0, 481, 671, 46);
        center.add(btnNewDepartamento);
        
        JLabel wave = new JLabel("");
		wave.setIcon(new ImageIcon(principalVista.class.getResource("/com/presentacion/res/wave.png")));
		wave.setBounds(274, 435, 600, 344);
		add(wave);

        setupTheme();
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
