package com.presentacion.vistas;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;

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
import com.sc.datatypes.dataSalida;
import com.sc.datatypes.dataUsuario;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.interfaces.IActividadController;
import com.sc.interfaces.IDepartamentoController;
import com.sc.interfaces.IUsuarioController;
import com.sc.utilidades.log;

import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class actividadesVista extends JPanel {

	private String selectedTheme;
	private PlaceholderTextField textBuscador;
	private String selectedPaquete;
	private String selectedSalida;

	public actividadesVista(String tema) {

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

		JLabel titulo = new JLabel("Actividades");
		titulo.setBounds(0, 0, 671, 40);
		titulo.putClientProperty("FlatLaf.styleClass", "h1");
		center.add(titulo);

		JLabel descripcion = new JLabel("Lista y crea las Actividades que se manejen en el sistema");
		descripcion.setBounds(0, 39, 671, 40);
		center.add(descripcion);

		PlaceholderTextField textBuscador = new PlaceholderTextField("Ingrese el nombre de la salida a buscar");
		textBuscador.setBounds(0, 90, 530, 29);
		center.add(textBuscador);
		textBuscador.setColumns(10);

		JButton btnBuscador = new JButton("Buscar");
		btnBuscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarVentanaEmergente(textBuscador.getText());
			}
		});
		btnBuscador.setBounds(540, 90, 131, 29);
		btnBuscador.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		center.add(btnBuscador);

		JButton btnNewActividad = new JButton("Crear nueva Actividad Turistica");
		btnNewActividad.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewActividad.setBounds(0, 480, 671, 47);
		btnNewActividad.putClientProperty("JButton.buttonType", "roundRect");
		btnNewActividad.putClientProperty("FlatLaf.styleClass", "h3");
		center.add(btnNewActividad);

		IActividadController IAC = Fabrica.getInstance().getIActividadController();
		List<dataActividad> actividades = IAC.getAllActividades();

		String[] columnNames = { "Nombre", "Proveedor", "Departamento", "Precio Unitario" };
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

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Object selectedValue = table.getValueAt(selectedRow, 0);
				if (selectedValue != null) {
					textBuscador.setText(selectedValue.toString());
				}
			}
		});

		if (actividades != null) {
			for (dataActividad actividad : actividades) {
				String dept = actividad.getDepartamento().getNombre();
				String prov = actividad.getProveedor().getNickname();
				String nomb = actividad.getNombre();
				int preciouni = actividad.getCostoUni();
				model.addRow(new Object[] { nomb, prov, dept, preciouni });
			}
		}

		JLabel wave = new JLabel("");
		wave.setIcon(new ImageIcon(principalVista.class.getResource("/com/presentacion/res/wave.png")));
		wave.setBounds(274, 435, 600, 344);
		add(wave);

		btnNewActividad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarCrearActicidad();

				model.setRowCount(0);

				List<dataActividad> actividades = IAC.getAllActividades();

				if (actividades != null) {
					for (dataActividad actividad : actividades) {
						String dept = actividad.getDepartamento().getNombre();
						String nomb = actividad.getNombre();
						int dur = actividad.getDuracion();
						int preciouni = actividad.getCostoUni();
						model.addRow(new Object[] { nomb, dept, dur, preciouni });
					}
				}
			}
		});

		setupTheme();
	}

	public void mostrarVentanaEmergente(String actividad) {
		JDialog popupPanel = new JDialog();
		popupPanel.setResizable(false);
		popupPanel.setTitle("Actividad");
		popupPanel.setSize(1000, 360);
		popupPanel.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupPanel.setLocationRelativeTo(null);
		popupPanel.getContentPane().setLayout(null);

		JTextField nombre = new JTextField();
		nombre.setEditable(false);
		nombre.setBounds(131, 11, 343, 20);
		popupPanel.getContentPane().add(nombre);
		nombre.setColumns(10);

		JLabel nom = new JLabel("Nombre:");
		nom.setBounds(10, 11, 111, 20);
		popupPanel.getContentPane().add(nom);

		JLabel desc = new JLabel("Descripcion:");
		desc.setBounds(10, 39, 111, 20);
		popupPanel.getContentPane().add(desc);

		JTextField descripcion = new JTextField();
		descripcion.setEditable(false);
		descripcion.setColumns(10);
		descripcion.setBounds(131, 39, 343, 20);
		popupPanel.getContentPane().add(descripcion);

		JLabel dur = new JLabel("Duracion:");
		dur.setBounds(10, 70, 111, 20);
		popupPanel.getContentPane().add(dur);

		JTextField ciudadd = new JTextField();
		ciudadd.setEditable(false);
		ciudadd.setColumns(10);
		ciudadd.setBounds(131, 132, 343, 20);
		popupPanel.getContentPane().add(ciudadd);

		JLabel costuni = new JLabel("Costo Unico:");
		costuni.setBounds(10, 101, 111, 20);
		popupPanel.getContentPane().add(costuni);

		JLabel fecha = new JLabel("Fecha de Creacion:");
		fecha.setBounds(10, 163, 111, 20);
		popupPanel.getContentPane().add(fecha);

		JSpinner costounico = new JSpinner();
		costounico.setEnabled(false);;
		costounico.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		costounico.setBounds(131, 101, 343, 20);
		popupPanel.getContentPane().add(costounico);

		JSpinner duracion = new JSpinner();
		duracion.setEnabled(false);
		duracion.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		duracion.setBounds(131, 70, 343, 20);
		popupPanel.getContentPane().add(duracion);

		IActividadController IAC = Fabrica.getInstance().getIActividadController();

		dataActividad dataAct = null;
		try {
			dataAct = IAC.mostrarDatos(actividad);
		} catch (UsuarioNoExisteExcepcion e) {
			JOptionPane.showMessageDialog(popupPanel, "No existe la actividad " + actividad, "Campos incompletos",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (dataAct == null) {
			JOptionPane.showMessageDialog(popupPanel, "No existe la actividad " + actividad, "Campos incompletos",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		LocalDate fechaAlta = dataAct.getFechaCrea();

		Date date = java.sql.Date.valueOf(fechaAlta);

		SpinnerDateModel dateModel = new SpinnerDateModel(date, new Date(-2208975309000L), new Date(32503690800000L),
				Calendar.YEAR);
		JSpinner fechaalta = new JSpinner(dateModel);
		fechaalta.setEnabled(false);
		fechaalta.setBounds(131, 163, 343, 20);
		popupPanel.getContentPane().add(fechaalta);

		JSpinner.DateEditor de_fechaalta = new JSpinner.DateEditor(fechaalta, "dd/MM/yyyy");
		fechaalta.setEditor(de_fechaalta);

		nombre.setText(dataAct.getNombre());
		descripcion.setText(dataAct.getDesc());
		duracion.setValue(dataAct.getDuracion());
		costounico.setValue(dataAct.getCostoUni());
		ciudadd.setText(dataAct.getCiudad());

		JButton aceptar = new JButton("Aceptar");
		aceptar.setVisible(false);
		aceptar.setEnabled(false);
		aceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aceptar.setBounds(19, 272, 224, 38);
		aceptar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.getContentPane().add(aceptar);

		dateModel.addChangeListener(e -> {
			aceptar.setEnabled(true);
		});

		JButton cancelar = new JButton("Cerrar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(19, 272, 458, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.getContentPane().add(cancelar);

		JLabel ciud = new JLabel("Ciudad:");
		ciud.setBounds(10, 132, 111, 20);
		popupPanel.getContentPane().add(ciud);

		JSeparator separator = new JSeparator();
		separator.setBounds(105, 258, 286, 2);
		popupPanel.getContentPane().add(separator);

		JPanel panel = new JPanel();
		panel.setBounds(486, 39, 488, 221);
		popupPanel.getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 2, 20, 0));

		String[] columnNames = { "Nombre", "Descuento" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		JTable table = new JTable(model);

		List<dataPaquete> listPaqute = dataAct.getDtPaquetes();
		
		if (listPaqute != null) {
			for (dataPaquete paq : listPaqute) {
				String nombrepaq = paq.getNombre();
				int descpaq = paq.getDescuento();
				model.addRow(new Object[] {nombrepaq, descpaq});
			}
		}

		String[] columnNames2 = { "Nombre", "Capacidad" };
		DefaultTableModel model2 = new DefaultTableModel(columnNames2, 0) {
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		JTable table2 = new JTable(model2);

		List<dataSalida> listSalida = dataAct.getDtSalidas();
		
		if (listSalida != null) {
			for (dataSalida sal : listSalida) {
				String nombresal = sal.getNombre();
				int capsal = sal.getCapacidad();
				model2.addRow(new Object[] {nombresal, capsal});
			}
		}

		JScrollPane actividadesPane = new JScrollPane(table);
		actividadesPane.setBounds(0, 0, 240, 321);
		panel.add(actividadesPane);

		JScrollPane paquetesPane = new JScrollPane(table2);
		paquetesPane.setBounds(0, 40, 240, 427);
		panel.add(paquetesPane);
		
		JPanel textTables = new JPanel();
		textTables.setBounds(484, 11, 490, 20);
		popupPanel.getContentPane().add(textTables);
		textTables.setLayout(new GridLayout(1, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Paquetes");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textTables.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Salidas");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		textTables.add(lblNewLabel_1);
		
		JPanel btnContainer = new JPanel();
		btnContainer.setBounds(487, 272, 487, 38);
		popupPanel.getContentPane().add(btnContainer);
		btnContainer.setLayout(new GridLayout(1, 2, 20, 0));
		

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Object selectedValue = table.getValueAt(selectedRow, 0);
				if (selectedValue != null) {
					this.selectedPaquete  = selectedValue.toString();
				}
			}
		});
		

		table2.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table2.getSelectedRow();
			if (selectedRow != -1) {
				Object selectedValue = table2.getValueAt(selectedRow, 0);
				if (selectedValue != null) {
					this.selectedSalida  = selectedValue.toString();
				}
			}
		});
		
		JButton btnpaquetes = new JButton("Consultar Paquetes");
		btnpaquetes.putClientProperty("JButton.buttonType", "roundRect");
		btnpaquetes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectedPaquete == null || selectedPaquete == "") {
					JOptionPane.showMessageDialog(popupPanel, "Seleccione un paquete de la tabla para poder continuar",
							"Parametros invalidos", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				paquetesVista pvtmp = new paquetesVista(selectedTheme);
				pvtmp.mostrarVentana(selectedPaquete);
				
				selectedPaquete = null;
			}
		});
		btnContainer.add(btnpaquetes);
		
		JButton btnsalidas = new JButton("Consultar Salidas");
		btnsalidas.putClientProperty("JButton.buttonType", "roundRect");
		btnsalidas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectedSalida == null || selectedSalida == "") {
					JOptionPane.showMessageDialog(popupPanel, "Seleccione una salida de la tabla para poder continuar",
							"Parametros invalidos", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				salidasVista svtmp = new salidasVista(selectedTheme);
				svtmp.mostrarDatosSalida(selectedSalida);
				
				selectedSalida = null;
			}
		});
		btnContainer.add(btnsalidas);

		descripcion.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				aceptar.setEnabled(true);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				aceptar.setEnabled(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		ciudadd.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				aceptar.setEnabled(true);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				aceptar.setEnabled(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		duracion.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				aceptar.setEnabled(true);
			}
		});

		costounico.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				aceptar.setEnabled(true);
			}
		});

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupPanel.dispose();
			}
		});

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupPanel.dispose();
			}
		});

		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (nombre.getText().isEmpty() || descripcion.getText().isEmpty() || ciudadd.getText().isEmpty()) {
					JOptionPane.showMessageDialog(popupPanel, "Todos los campos deben estar llenos",
							"Campos incompletos", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Date selectedDate = (Date) fechaalta.getValue();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(selectedDate);

				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DAY_OF_MONTH);

				LocalDate nuevaFecha = LocalDate.of(year, month, day);

				Object duracionObj = duracion.getValue();
				int duracionInt = (int) duracionObj;
				Object precioObj = costounico.getValue();
				int precioInt = (int) precioObj;

				try {
					IAC.modificarActividad(nombre.getText(), descripcion.getText(), duracionInt, precioInt,
							ciudadd.getText(), nuevaFecha);

					model.setRowCount(0);

					List<dataActividad> actividades = IAC.getAllActividades();

					if (actividades != null) {
						for (dataActividad actividad : actividades) {
							String dept = actividad.getDepartamento().getNombre();
							String nomb = actividad.getNombre();
							int dur = actividad.getDuracion();
							int preciouni = actividad.getCostoUni();
							model.addRow(new Object[] { nomb, dept, dur, preciouni });
						}
					}

					System.out.println("Se ha modificado la actividad");
					JOptionPane.showMessageDialog(popupPanel, "Actividad modificada con EXITO", "Registro Completado",
							JOptionPane.INFORMATION_MESSAGE);
					popupPanel.dispose();

				} catch (ParametrosInvalidosExcepcion e2) {
					JOptionPane.showMessageDialog(popupPanel, "Campos invalidos", "Campos invalidos",
							JOptionPane.ERROR_MESSAGE);
					return;
				} catch (UsuarioYaExisteExcepcion e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				} catch (UsuarioNoExisteExcepcion e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return;
				}
			}
		});

		popupPanel.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		popupPanel.setVisible(true);
	}

	private void mostrarCrearActicidad() {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Actividad");
		popupDialog.setSize(505, 397);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);
		popupDialog.getContentPane().setLayout(null);

		JPanel popupPanel = new JPanel();
		popupPanel.setBounds(0, 0, 490, 364);
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JLabel nom = new JLabel("Nombre: ");
		nom.setBounds(7, 69, 128, 20);
		popupPanel.add(nom);

		JTextField nombre = new JTextField();
		nombre.setColumns(10);
		nombre.setBounds(145, 69, 326, 20);
		popupPanel.add(nombre);

		JLabel des = new JLabel("Descripcion:");
		des.setBounds(7, 100, 128, 20);
		popupPanel.add(des);

		JTextField descripcion = new JTextField();
		descripcion.setColumns(10);
		descripcion.setBounds(145, 100, 326, 20);
		popupPanel.add(descripcion);

		JLabel prov = new JLabel("Proveedor:");
		prov.setBounds(7, 224, 128, 20);
		popupPanel.add(prov);

		JComboBox<String> proveedor = new JComboBox<>();
		DefaultComboBoxModel<String> proveedoresModel = new DefaultComboBoxModel<>();
		proveedor.setBounds(145, 224, 326, 20);

		IUsuarioController IUC = Fabrica.getInstance().getIUsuarioController();
		List<dataUsuario> provedoreslist = IUC.listarUsuarios();

		if (provedoreslist == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen proveedores para la actividad en el sistema",
					"Error", JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for (dataUsuario proveedornick : provedoreslist) {
				if (proveedornick.getisProveedor()) {
					proveedoresModel.addElement(proveedornick.getNickname());
				}
			}
			proveedor.setModel(proveedoresModel);
			popupPanel.add(proveedor);
		}

		JSeparator separator = new JSeparator();
		separator.setBounds(83, 287, 297, 2);
		popupPanel.add(separator);

		JLabel dep = new JLabel("Departamento:");
		dep.setBounds(7, 38, 128, 20);
		popupPanel.add(dep);

		JComboBox<String> departamento = new JComboBox<>();
		DefaultComboBoxModel<String> departamentoModel = new DefaultComboBoxModel<>();
		departamento.setBounds(145, 37, 326, 22);

		IDepartamentoController IDC = Fabrica.getInstance().getIDepartamentoController();
		List<dataDepartamento> departamentos = IDC.listarDepartamentos();

		if (departamentos == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen departamentos en los cuales crear las actividades",
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

		JLabel cosuni = new JLabel("Costo Unico:");
		cosuni.setBounds(7, 131, 128, 20);
		popupPanel.add(cosuni);

		JSpinner costoUnico = new JSpinner();
		costoUnico.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		costoUnico.setBounds(145, 131, 86, 20);
		popupPanel.add(costoUnico);

		JLabel cit = new JLabel("Ciudad:");
		cit.setBounds(7, 193, 128, 20);
		popupPanel.add(cit);

		JTextField ciudad = new JTextField();
		ciudad.setColumns(10);
		ciudad.setBounds(145, 193, 326, 20);
		popupPanel.add(ciudad);

		JLabel dur = new JLabel("Duracion:");
		dur.setBounds(7, 162, 128, 20);
		popupPanel.add(dur);

		JSpinner duracion = new JSpinner();
		duracion.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		duracion.setBounds(145, 162, 86, 20);
		popupPanel.add(duracion);

		JButton aceptar = new JButton("Aceptar");
		aceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aceptar.setBounds(4, 314, 224, 38);
		aceptar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(aceptar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(247, 314, 224, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);

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
				IActividadController IAC = Fabrica.getInstance().getIActividadController();
				try {
					Object duracionObj = duracion.getValue();
					int duracionInt = (int) duracionObj;
					Object precioObj = costoUnico.getValue();
					int precioInt = (int) precioObj;
					IAC.crearActividad(departamento.getSelectedItem().toString(),
							proveedor.getSelectedItem().toString(), nombre.getText(), descripcion.getText(),
							duracionInt, precioInt, ciudad.getText(), LocalDate.now());
					JOptionPane.showMessageDialog(popupDialog, "Actividad creada con EXITO", "Registro Completado",
							JOptionPane.INFORMATION_MESSAGE);
					popupDialog.dispose();
				} catch (UsuarioYaExisteExcepcion a) {
					JOptionPane.showMessageDialog(popupDialog,"Ya existe la actividad " + nombre.getText(), a.getMessage() , JOptionPane.ERROR_MESSAGE);
					return;
				} catch (ParametrosInvalidosExcepcion a) {
					JOptionPane.showMessageDialog(popupDialog,  "Parametros invalidos", a.getMessage(), JOptionPane.ERROR_MESSAGE);
					return;
				} catch (UsuarioNoExisteExcepcion a) {
					JOptionPane.showMessageDialog(popupDialog, "No existe la actividad " + nombre.getText(), a.getMessage(), JOptionPane.ERROR_MESSAGE);
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
