package com.presentacion.vistas;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;
import com.presentacion.utilidades.PlaceholderTextField;
import com.sc.Fabrica;
import com.sc.datatypes.dataActividad;
import com.sc.datatypes.dataDepartamento;
import com.sc.datatypes.dataPaquete;
import com.sc.excepciones.NoExisteExcepcion;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.excepciones.YaExisteExcepcion;
import com.sc.interfaces.IActividadController;
import com.sc.interfaces.IDepartamentoController;
import com.sc.interfaces.IPaqueteController;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dialog;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class paquetesVista extends JPanel {

	private String selectedTheme;
	private PlaceholderTextField textBuscador;
	private String selectedActividad;
	public paquetesVista(String tema) {
		this.selectedTheme = selectedTheme;
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

		JLabel titulo = new JLabel("Paquetes");
		titulo.setBounds(0, 0, 671, 40);
		titulo.putClientProperty("FlatLaf.styleClass", "h1");
		center.add(titulo);

		JLabel descripcion = new JLabel("Lista y crea los paquetes que se manejen en el sistema");
		descripcion.setBounds(0, 39, 671, 40);
		center.add(descripcion);

		textBuscador = new PlaceholderTextField("Ingrese el nombre del paquete a buscar");
		textBuscador.setBounds(0, 90, 530, 29);
		center.add(textBuscador);
		textBuscador.setColumns(10);

		JButton btnBuscador = new JButton("Buscar");
		btnBuscador.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscador.setBounds(540, 90, 131, 29);
		center.add(btnBuscador);

		String[] columnNames = { "Nombre", "Descripcion", "Cant. Actividades", "Descuento (%)" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			boolean[] columnEditables = new boolean[] { false, false, false, false };
		};
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

		JTable table = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 130, 671, 337);
		center.add(scrollPane);

		table.setRowSorter(sorter);
		IPaqueteController IPC = (IPaqueteController) Fabrica.getInstance().getIPaqueteController();
		List<dataPaquete> paquetes = IPC.listarPaquetes();

		if (paquetes != null) {
			for (dataPaquete paquete : paquetes) {
				String nombre = paquete.getNombre();
				String descripcionstring = paquete.getDescripcion();
				// int valid = paquete.getPeriodoValidez();
				int actcant = paquete.getActividades().size();
				int descount = paquete.getDescuento();
				model.addRow(new Object[] { nombre, descripcionstring, actcant, descount });
			}
		}

		center.add(scrollPane);

		JPanel btnPanel = new JPanel();
		btnPanel.setOpaque(false);
		btnPanel.setBounds(0, 478, 671, 49);
		center.add(btnPanel);
		btnPanel.setLayout(new GridLayout(1, 2, 20, 0));

		JButton btnNewPaquete = new JButton("Crear Paquete");
		btnNewPaquete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewPaquete.putClientProperty("JButton.buttonType", "roundRect");
		btnNewPaquete.putClientProperty("FlatLaf.styleClass", "h3");
		btnPanel.add(btnNewPaquete);

		JButton btnAddActividad = new JButton("Agregar Actividad a Paquete");
		btnAddActividad.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddActividad.putClientProperty("JButton.buttonType", "roundRect");
		btnAddActividad.putClientProperty("FlatLaf.styleClass", "h3");
		btnPanel.add(btnAddActividad);

		JLabel wave = new JLabel("");
		wave.setIcon(new ImageIcon(principalVista.class.getResource("/com/presentacion/res/wave.png")));
		wave.setBounds(274, 435, 600, 344);
		add(wave);

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Object selectedValue = table.getValueAt(selectedRow, 0);
				if (selectedValue != null) {
					textBuscador.setText(selectedValue.toString());
				}
			}
		});
		
		btnBuscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarVentana(textBuscador.getText().toString());
			}
		});

		btnNewPaquete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarCrearPaquete();

				model.setRowCount(0);
				List<dataPaquete> paquetes = IPC.listarPaquetes();
				if (paquetes != null) {
					for (dataPaquete paquete : paquetes) {
						String nombre = paquete.getNombre();
						String descripcionstring = paquete.getDescripcion();
						int valid = paquete.getValidez();
						int descount = paquete.getDescuento();
						model.addRow(new Object[] { nombre, descripcionstring, valid, descount });
					}
				}
			}
		});

		btnAddActividad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarAddActividad1();
			}
		});

		setupTheme();
	}

	public void mostrarVentana(String paqueteParameter) {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Paquete");
		popupDialog.setSize(906, 324);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);
		popupDialog.getContentPane().setLayout(null);

		JPanel popupPanel = new JPanel();
		popupPanel.setBounds(0, 0, 490, 285);
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JTextField nombre = new JTextField();
		nombre.setEditable(false);
		nombre.setBounds(149, 11, 325, 20);
		popupPanel.add(nombre);
		nombre.setColumns(10);

		JLabel nom = new JLabel("Nombre:");
		nom.setBounds(10, 11, 136, 20);
		popupPanel.add(nom);

		JLabel desc = new JLabel("Descripcion:");
		desc.setBounds(10, 39, 136, 20);
		popupPanel.add(desc);

		JTextField descripcion = new JTextField();
		descripcion.setEditable(false);
		descripcion.setColumns(10);
		descripcion.setBounds(149, 39, 325, 20);
		popupPanel.add(descripcion);

		JLabel valid = new JLabel("Periodo Validez:");
		valid.setBounds(10, 70, 136, 20);
		popupPanel.add(valid);

		JLabel descue = new JLabel("Descuento:");
		descue.setBounds(10, 101, 136, 20);
		popupPanel.add(descue);

		JSpinner validez = new JSpinner();
		validez.setEnabled(false);
		validez.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		validez.setBounds(149, 70, 325, 20);
		popupPanel.add(validez);

		JSpinner descuento = new JSpinner();
		descuento.setEnabled(false);
		descuento.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		descuento.setBounds(149, 101, 325, 20);
		popupPanel.add(descuento);

		IPaqueteController IPC = Fabrica.getInstance().getIPaqueteController();
		dataPaquete datos = null;

		try {
			datos = IPC.mostrarInfo(paqueteParameter);
		} catch (UsuarioNoExisteExcepcion e) {
			JOptionPane.showMessageDialog(popupDialog, "No existe el paquete " + textBuscador.getText(), "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (datos == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existe el paquete " + textBuscador.getText(), "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		nombre.setText(datos.getNombre());
		descripcion.setText(datos.getDescripcion());
		// validez.setValue(datos.getPeriodoValidez());
		validez.setValue(datos.getValidez());
		descuento.setValue(datos.getDescuento());

		JSeparator separator = new JSeparator();
		separator.setBounds(94, 224, 297, 2);
		popupPanel.add(separator);

		JButton cancelar = new JButton("Cerrar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(9, 236, 465, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);

		JPanel tableConteiner = new JPanel();
		tableConteiner.setBounds(500, 45, 380, 180);
		popupDialog.getContentPane().add(tableConteiner);

		String[] columnNames = { "Nombre", "Ciudad" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		JTable table = new JTable(model);

		List<dataActividad> actList = datos.getActividades();
		
		if (actList != null) {
			for (dataActividad actItem : datos.getActividades()) {
				String nombreact = actItem.getNombre();
				String ciudadact = actItem.getCiudad();
				model.addRow(new Object[] {nombreact, ciudadact});
			}
		}

		JScrollPane actividadesPane = new JScrollPane(table);

		tableConteiner.setLayout(new BorderLayout());
		tableConteiner.add(actividadesPane, BorderLayout.CENTER);

		tableConteiner.revalidate();
		tableConteiner.repaint();

		tableConteiner.add(actividadesPane);
		
		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Object selectedValue = table.getValueAt(selectedRow, 0);
				if (selectedValue != null) {
					this.selectedActividad  = selectedValue.toString();
				}
			}
		});
		
		JButton btnactividades = new JButton("Consultar Actividades");
		btnactividades.putClientProperty("JButton.buttonType", "roundRect");
		btnactividades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnactividades.setBounds(500, 236, 380, 38);
		popupDialog.getContentPane().add(btnactividades);
		
		JLabel lblNewLabel = new JLabel("Actividades");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(500, 11, 380, 23);
		popupDialog.getContentPane().add(lblNewLabel);
		
		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
			}
		});
		
		btnactividades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectedActividad == null || selectedActividad == "") {
					JOptionPane.showMessageDialog(popupDialog, "Seleccione una actividad de la tabla para poder continuar",
							"Parametros invalidos", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				actividadesVista avtmp = new actividadesVista(selectedTheme);
				avtmp.mostrarVentanaEmergente(selectedActividad);
				
				selectedActividad = null;
			}
		});

		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		popupDialog.setVisible(true);
	}

	private void mostrarAddActividad1() {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Paquete");
		popupDialog.setSize(505, 250);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);
		popupDialog.getContentPane().setLayout(null);

		JPanel popupPanel = new JPanel();
		popupPanel.setBounds(0, 0, 490, 209);
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JButton aceptar = new JButton("Aceptar");
		aceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aceptar.setBounds(12, 160, 224, 38);
		aceptar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(aceptar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(252, 160, 224, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);

		JSeparator separator = new JSeparator();
		separator.setBounds(95, 143, 297, 2);
		popupPanel.add(separator);

		JLabel paq = new JLabel("Paquete:");
		paq.setBounds(12, 11, 142, 19);
		popupPanel.add(paq);

		JLabel dep = new JLabel("Departamento:");
		dep.setBounds(12, 57, 142, 19);
		popupPanel.add(dep);

		JComboBox<String> paquete = new JComboBox<>();
		DefaultComboBoxModel<String> paqueteModel = new DefaultComboBoxModel<>();
		paquete.setBounds(164, 9, 312, 22);

		IPaqueteController IPC = Fabrica.getInstance().getIPaqueteController();
		List<dataPaquete> paqs = IPC.listarPaquetes();

		if (paqs == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen paquetes a los cuales asociar las actividades",
					"Error", JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for (dataPaquete p : paqs) {
				paqueteModel.addElement(p.getNombre());
				paquete.setModel(paqueteModel);
				popupPanel.add(paquete);
			}
		}

		JComboBox<String> departamento = new JComboBox<>();
		DefaultComboBoxModel<String> departamentoModel = new DefaultComboBoxModel<>();
		departamento.setBounds(164, 55, 312, 22);

		IDepartamentoController IDC = Fabrica.getInstance().getIDepartamentoController();
		List<dataDepartamento> departamentos = IDC.listarDepartamentos();

		if (departamentos == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen departamentos por los cuales buscar actividades",
					"Error", JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for (dataDepartamento dept : departamentos) {
				departamentoModel.addElement(dept.getNombre());
				departamento.setModel(departamentoModel);
				popupPanel.add(departamento);
			}
		}

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
			}
		});

		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarAddActividad2(paquete.getSelectedItem().toString(), departamento.getSelectedItem().toString());
				popupDialog.dispose();
				return;
			}
		});
		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		popupDialog.setVisible(true);
	}

	private void mostrarAddActividad2( String paqueteParameter, String departamentoParameter) {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Paquete");
		popupDialog.setSize(505, 250);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);
		popupDialog.getContentPane().setLayout(null);

		JPanel popupPanel = new JPanel();
		popupPanel.setBounds(0, 0, 490, 209);
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JButton aceptar = new JButton("Aceptar");
		aceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aceptar.setBounds(12, 160, 224, 38);
		aceptar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(aceptar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(252, 160, 224, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);

		JSeparator separator = new JSeparator();
		separator.setBounds(95, 143, 297, 2);
		popupPanel.add(separator);

		JLabel act = new JLabel("Actividad:");
		act.setBounds(12, 11, 142, 19);
		popupPanel.add(act);

		JComboBox<String> actividad = new JComboBox<>();
		DefaultComboBoxModel<String> actividadModel = new DefaultComboBoxModel<>();
		actividad.setBounds(164, 9, 312, 22);

		IActividadController IAC = Fabrica.getInstance().getIActividadController();
		List<dataActividad> activ = IAC.getAllActividadesDepartamento(departamentoParameter);

		if (activ == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen actividades las cuales asociar",
					"Error", JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for (dataActividad a : activ) {
				actividadModel.addElement(a.getNombre());
				actividad.setModel(actividadModel);
				popupPanel.add(actividad);
			}
		}

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
				return;
			}
		});

		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				IPaqueteController IPC = Fabrica.getInstance().getIPaqueteController();
				System.out.println("click");
				try {
					IPC.agregarActividadPaquete(paqueteParameter, actividad.getSelectedItem().toString());
					JOptionPane.showMessageDialog(popupDialog, "Actividad agregada con EXITO",
							"Exito", JOptionPane.INFORMATION_MESSAGE);
					popupDialog.dispose();
					return;
				} catch (NoExisteExcepcion e1) {
					JOptionPane.showMessageDialog(popupDialog, "NO existe la actividad " + actividad.getSelectedItem().toString() + " en el paquete " + paqueteParameter,  "Error" , JOptionPane.ERROR_MESSAGE);
					return;
				} catch (YaExisteExcepcion e1) {
					JOptionPane.showMessageDialog(popupDialog, "Ya existe la actividad " + actividad.getSelectedItem().toString() + " en el paquete " + paqueteParameter, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		popupDialog.setVisible(true);
	}

	private void mostrarCrearPaquete() {

		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Paquete");
		popupDialog.setSize(505, 250);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);
		popupDialog.getContentPane().setLayout(null);

		JPanel popupPanel = new JPanel();
		popupPanel.setBounds(0, 0, 490, 209);
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JTextField nombre = new JTextField();
		nombre.setBounds(149, 11, 325, 20);
		popupPanel.add(nombre);
		nombre.setColumns(10);

		JLabel nom = new JLabel("Nombre:");
		nom.setBounds(10, 11, 136, 20);
		popupPanel.add(nom);

		JLabel desc = new JLabel("Descripcion:");
		desc.setBounds(10, 39, 136, 20);
		popupPanel.add(desc);

		JTextField descripcion = new JTextField();
		descripcion.setColumns(10);
		descripcion.setBounds(149, 39, 325, 20);
		popupPanel.add(descripcion);

		JLabel valid = new JLabel("Periodo Validez:");
		valid.setBounds(10, 70, 136, 20);
		popupPanel.add(valid);

		JLabel descue = new JLabel("Descuento:");
		descue.setBounds(10, 101, 136, 20);
		popupPanel.add(descue);

		JSeparator separator = new JSeparator();
		separator.setBounds(95, 143, 297, 2);
		popupPanel.add(separator);

		JButton aceptar = new JButton("Aceptar");
		aceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aceptar.setBounds(12, 160, 224, 38);
		aceptar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(aceptar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(252, 160, 224, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);

		JSpinner validez = new JSpinner();
		validez.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		validez.setBounds(149, 70, 325, 20);
		popupPanel.add(validez);

		JSpinner descuento = new JSpinner();
		descuento.setModel(new SpinnerNumberModel(1, 0, 100, 1));
		descuento.setBounds(149, 101, 325, 20);
		popupPanel.add(descuento);

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
			}
		});

		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (nombre.getText().isEmpty() || descripcion.getText().isEmpty()) {
					JOptionPane.showMessageDialog(popupDialog, "Todos los campos deben estar llenos",
							"Campos incompletos", JOptionPane.ERROR_MESSAGE);
					return;
				}
				IPaqueteController IPC = (IPaqueteController) Fabrica.getInstance().getIPaqueteController();
				Object validezObj = validez.getValue();
				int velidezInt = (int) validezObj;
				Object descObj = descuento.getValue();
				int descInt = (int) descObj;

				try {
					IPC.crearPaquete(nombre.getText(), descripcion.getText(), velidezInt, descInt, LocalDate.now());
					System.out.println("Se ha creado el paquete");
					JOptionPane.showMessageDialog(popupDialog, "Paquete creado con EXITO", "Registro Completado",
							JOptionPane.INFORMATION_MESSAGE);
					popupDialog.dispose();
				} catch (UsuarioYaExisteExcepcion e1) {
					JOptionPane.showMessageDialog(popupDialog, e1.getMessage(), "Ya existe el paquete " + nombre.getText(), JOptionPane.ERROR_MESSAGE);
					return;
				} catch (ParametrosInvalidosExcepcion e1) {
					JOptionPane.showMessageDialog(popupDialog, e1.getMessage(), "Parametros invalidos", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});

		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		popupDialog.setVisible(true);
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
