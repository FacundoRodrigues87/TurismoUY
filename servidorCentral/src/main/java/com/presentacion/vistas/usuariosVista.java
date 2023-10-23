package com.presentacion.vistas;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;
import com.presentacion.utilidades.PlaceholderTextField;
import com.sc.Fabrica;
import com.sc.datatypes.dataActividad;
import com.sc.datatypes.dataSalida;
import com.sc.datatypes.dataUsuario;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.interfaces.IUsuarioController;
import com.sc.utilidades.log;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.Cursor;
import java.awt.Dialog;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.swing.ImageIcon;

public class usuariosVista extends JPanel {

	private String selectedTheme;
	private PlaceholderTextField textBuscador;
	private Fabrica fabrica;
	private JTable table;
	private String buscadorActividad;
	private String buscadorSalida;

	public usuariosVista(String tema, Fabrica fabrica) {

		this.fabrica = fabrica;

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
		
        JLabel titulo = new JLabel("Usuarios");
        titulo.setBounds(0, 0, 671, 40);
        titulo.putClientProperty("FlatLaf.styleClass", "h1");
        center.add(titulo);
        
        JLabel descripcion = new JLabel("Lista y crea los usuarios tanto turistas como proveedores que se manejen en el sistema");
        descripcion.setBounds(0, 39, 671, 40);
        center.add(descripcion);

		textBuscador = new PlaceholderTextField("Ingrese el Nickname del usuario a buscar");
		textBuscador.setBounds(0, 90, 530, 29);
		center.add(textBuscador);
		textBuscador.setColumns(10);

		JButton btnBuscador = new JButton("Buscar");
		btnBuscador.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscador.setBounds(540, 90, 131, 29);
		center.add(btnBuscador);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setOpaque(false);
        btnPanel.setBounds(0, 478, 671, 49);
        center.add(btnPanel);
        btnPanel.setLayout(new GridLayout(1, 2, 20, 0));
		
		JButton btnNewProveedor = new JButton("Crear un usuario Proveedor");
		btnNewProveedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewProveedor.setBounds(421, 480, 250, 47);
		btnPanel.add(btnNewProveedor);
		btnNewProveedor.putClientProperty("JButton.buttonType", "roundRect");
		btnNewProveedor.putClientProperty("FlatLaf.styleClass", "h3");


		JButton btnNewTurista = new JButton("Crear un usuario Turista");

		btnNewTurista.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewTurista.setBounds(0, 480, 250, 47);
		btnNewTurista.putClientProperty("JButton.buttonType", "roundRect");
		btnNewTurista.putClientProperty("FlatLaf.styleClass", "h3");
		btnPanel.add(btnNewTurista);

		IUsuarioController IUC = fabrica.getIUsuarioController();
		List<dataUsuario> usuarios = IUC.listarUsuarios();

		String[] columnNames = { "Nickname", "Clase", "Nombre", "Apellido", "Correo" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		JTable table = new JTable(model);

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
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

		if (usuarios != null) {
			for (dataUsuario usuario : usuarios) {
				String nickname = usuario.getNickname();
				String nombre = usuario.getNombre();
				String apellido = usuario.getApellido();
				String correo = usuario.getEmail();
				String tipo;
				if (!usuario.getisProveedor()) {
					tipo = "Turista";
				} else {
					tipo = "Proveedor";
				}

				model.addRow(new Object[] { nickname, tipo, nombre, apellido, correo });
			}
		}

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 130, 671, 337);
		center.add(scrollPane);

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

		btnNewTurista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarCrearTurista();

				model.setRowCount(0);

				List<dataUsuario> usuarios = IUC.listarUsuarios();

				for (dataUsuario usuario : usuarios) {
					String nickname = usuario.getNickname();
					String nombre = usuario.getNombre();
					String apellido = usuario.getApellido();
					String correo = usuario.getEmail();
					String tipo;
					if (!usuario.getisProveedor()) {
						tipo = "Turista";
					} else {
						tipo = "Proveedor";
					}

					model.addRow(new Object[] { nickname, tipo, nombre, apellido, correo });
				}

			}
		});

		btnNewProveedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarCrearProveedor();

				model.setRowCount(0);

				List<dataUsuario> usuarios = IUC.listarUsuarios();

				for (dataUsuario usuario : usuarios) {
					String nickname = usuario.getNickname();
					String nombre = usuario.getNombre();
					String apellido = usuario.getApellido();
					String correo = usuario.getEmail();
					String tipo;
					if (!usuario.getisProveedor()) {
						tipo = "Turista";
					} else {
						tipo = "Proveedor";
					}

					model.addRow(new Object[] { nickname, tipo, nombre, apellido, correo });
				}
			}
		});

		btnBuscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textBuscador.getText().isEmpty()) {
					JOptionPane.showMessageDialog(center, "No hay usuario a buscar ", "Campos incompletos",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				mostrarVentanaEmergente();

				model.setRowCount(0);

				List<dataUsuario> usuarios = IUC.listarUsuarios();

				for (dataUsuario usuario : usuarios) {
					String nickname = usuario.getNickname();
					String nombre = usuario.getNombre();
					String apellido = usuario.getApellido();
					String correo = usuario.getEmail();
					String tipo;
					if (!usuario.getisProveedor()) {
						tipo = "Turista";
					} else {
						tipo = "Proveedor";
					}

					model.addRow(new Object[] { nickname, tipo, nombre, apellido, correo });
				}
			}
		});

		setupTheme();
	}

	private void mostrarCrearProveedor() {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Turista");
		popupDialog.setSize(505, 360);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);
		popupDialog.getContentPane().setLayout(null);

		JPanel popupPanel = new JPanel();
		popupPanel.setBounds(0, 0, 490, 321);
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JTextField nickname = new JTextField();
		nickname.setBounds(93, 11, 381, 20);
		popupPanel.add(nickname);
		nickname.setColumns(10);

		JLabel nick = new JLabel("Nickname:");
		nick.setBounds(10, 11, 484, 20);
		popupPanel.add(nick);

		JLabel nom = new JLabel("Nombre: ");
		nom.setBounds(10, 39, 69, 20);
		popupPanel.add(nom);

		JTextField nombre = new JTextField();
		nombre.setColumns(10);
		nombre.setBounds(93, 39, 381, 20);
		popupPanel.add(nombre);

		JLabel ape = new JLabel("Apellido:");
		ape.setBounds(10, 70, 69, 20);
		popupPanel.add(ape);

		JTextField apellido = new JTextField();
		apellido.setColumns(10);
		apellido.setBounds(93, 70, 381, 20);
		popupPanel.add(apellido);

		JLabel mail = new JLabel("Email:");
		mail.setBounds(10, 101, 69, 20);
		popupPanel.add(mail);

		JTextField correo = new JTextField();
		correo.setColumns(10);
		correo.setBounds(93, 101, 381, 20);
		popupPanel.add(correo);

		JLabel fechan = new JLabel("Fecha de Nacimiento:");
		fechan.setBounds(10, 134, 169, 20);
		popupPanel.add(fechan);

		JSeparator separator = new JSeparator();
		separator.setBounds(93, 239, 297, 2);
		popupPanel.add(separator);

		JButton aceptar = new JButton("Aceptar");
		aceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aceptar.setBounds(10, 256, 224, 38);
		aceptar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(aceptar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(250, 256, 224, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);

		JSpinner fechanacimiento = new JSpinner();
		fechanacimiento.setModel(new SpinnerDateModel(new Date(946695600000L), new Date(-2208975309000L),
				new Date(32503690800000L), Calendar.YEAR));
		fechanacimiento.setBounds(189, 134, 150, 20);
		popupPanel.add(fechanacimiento);

		SpinnerDateModel dateModel = (SpinnerDateModel) fechanacimiento.getModel();
		Date date = (Date) dateModel.getValue();

		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechanacimiento, "dd/MM/yyyy");
		fechanacimiento.setEditor(dateEditor);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		JTextComponent descripcion = new JTextField();
		descripcion.setBounds(135, 165, 339, 20);
		popupPanel.add(descripcion);

		JLabel desc = new JLabel("Descripcion:");
		desc.setHorizontalTextPosition(SwingConstants.CENTER);
		desc.setBounds(10, 165, 100, 20);
		popupPanel.add(desc);

		JLabel urltext = new JLabel("URL:");
		urltext.setHorizontalTextPosition(SwingConstants.CENTER);
		urltext.setBounds(10, 196, 100, 20);
		popupPanel.add(urltext);

		JTextField url = new JTextField();
		url.setBounds(135, 196, 339, 20);
		popupPanel.add(url);

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
			}
		});

		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (nickname.getText().isEmpty() || nombre.getText().isEmpty() || apellido.getText().isEmpty()
						|| correo.getText().isEmpty() || descripcion.getText().isEmpty()) {
					JOptionPane.showMessageDialog(popupDialog, "Todos los campos deben estar llenos",
							"Campos incompletos", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Date selectedDate = (Date) fechanacimiento.getValue();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(selectedDate);

				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DAY_OF_MONTH);

				IUsuarioController IUC = fabrica.getIUsuarioController();
				try {
					IUC.registrarProveedor(nickname.getText(), nombre.getText(), apellido.getText(), correo.getText(),
							descripcion.getText(), url.getText(), LocalDate.of(year, month, day));
					System.out.println("Se ha creado el usuario");
					JOptionPane.showMessageDialog(popupDialog, "Usuario creado con EXITO",
							"Registro Completado", JOptionPane.INFORMATION_MESSAGE);
					popupDialog.dispose();
				} catch (UsuarioYaExisteExcepcion a) {
					log.error(a.getMessage());
				} catch (ParametrosInvalidosExcepcion a) {
					log.error(a.getMessage());
				}
			}
		});

		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		popupDialog.setVisible(true);
	}

	private void mostrarCrearTurista() {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Turista");
		popupDialog.setSize(505, 360);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);
		popupDialog.getContentPane().setLayout(null);

		JPanel popupPanel = new JPanel();
		popupPanel.setBounds(0, 0, 490, 321);
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JTextField nickname = new JTextField();
		nickname.setBounds(93, 11, 381, 20);
		popupPanel.add(nickname);
		nickname.setColumns(10);

		JLabel nick = new JLabel("Nickname:");
		nick.setBounds(10, 11, 484, 20);
		popupPanel.add(nick);

		JLabel nom = new JLabel("Nombre: ");
		nom.setBounds(10, 39, 69, 20);
		popupPanel.add(nom);

		JTextField nombre = new JTextField();
		nombre.setColumns(10);
		nombre.setBounds(93, 39, 381, 20);
		popupPanel.add(nombre);

		JLabel ape = new JLabel("Apellido:");
		ape.setBounds(10, 70, 69, 20);
		popupPanel.add(ape);

		JTextField apellido = new JTextField();
		apellido.setColumns(10);
		apellido.setBounds(93, 70, 381, 20);
		popupPanel.add(apellido);

		JLabel mail = new JLabel("Email:");
		mail.setBounds(10, 101, 69, 20);
		popupPanel.add(mail);

		JTextField correo = new JTextField();
		correo.setColumns(10);
		correo.setBounds(93, 101, 381, 20);
		popupPanel.add(correo);

		JLabel fechan = new JLabel("Fecha de Nacimiento:");
		fechan.setBounds(10, 134, 169, 20);
		popupPanel.add(fechan);

		JSeparator separator = new JSeparator();
		separator.setBounds(89, 212, 297, 2);
		popupPanel.add(separator);

		JButton aceptar = new JButton("Aceptar");
		aceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aceptar.setBounds(10, 239, 224, 38);
		aceptar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(aceptar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(253, 239, 224, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);

		JSpinner fechanacimiento = new JSpinner();
		fechanacimiento.setModel(new SpinnerDateModel(new Date(946695600000L), new Date(-2208975309000L),
				new Date(32503690800000L), Calendar.YEAR));
		fechanacimiento.setBounds(189, 134, 150, 20);
		popupPanel.add(fechanacimiento);

		SpinnerDateModel dateModel = (SpinnerDateModel) fechanacimiento.getModel();
		Date date = (Date) dateModel.getValue();

		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechanacimiento, "dd/MM/yyyy");
		fechanacimiento.setEditor(dateEditor);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		JTextComponent nacionalidad = new JTextField();
		nacionalidad.setBounds(135, 165, 339, 20);
		popupPanel.add(nacionalidad);

		JLabel nac = new JLabel("Nacionalidad");
		nac.setBounds(10, 165, 100, 20);
		popupPanel.add(nac);

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
			}
		});

		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (nickname.getText().isEmpty() || nombre.getText().isEmpty() || apellido.getText().isEmpty()
						|| correo.getText().isEmpty() || nacionalidad.getText().isEmpty()) {
					JOptionPane.showMessageDialog(popupDialog, "Todos los campos deben estar llenos",
							"Campos incompletos", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Date selectedDate = (Date) fechanacimiento.getValue();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(selectedDate);

				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DAY_OF_MONTH);

				IUsuarioController IUC = fabrica.getIUsuarioController();

				try {
					IUC.registrarTurista(nickname.getText(), nombre.getText(), apellido.getText(), correo.getText(),
							nacionalidad.getText(), LocalDate.of(year, month, day));
					System.out.println("Se ha creado el usuario");
					JOptionPane.showMessageDialog(popupDialog, "Usuario creado con EXITO",
							"Registro Completado", JOptionPane.INFORMATION_MESSAGE);
					popupDialog.dispose();
				} catch (UsuarioYaExisteExcepcion a) {
					JOptionPane.showMessageDialog(popupDialog, a.getMessage(), "Ya existe", JOptionPane.ERROR_MESSAGE);
					return;
				} catch (ParametrosInvalidosExcepcion a) {
					JOptionPane.showMessageDialog(popupDialog, "Los parametros ingresado no son validos",
							"Parametros invalidos", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});

		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		popupDialog.setVisible(true);
	}

	private void mostrarVentanaEmergenteError() {
		JDialog popupDialog = new JDialog();
		popupDialog.setTitle("Error!!!");
		popupDialog.setSize(300, 200);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);

		JPanel popupPanel = new JPanel();
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JLabel label = new JLabel("No se ha encontrado al usuario");
		label.setBounds(32, 70, 220, 20);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		popupPanel.add(label);

		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		popupDialog.setVisible(true);
	}

	private void mostrarVentanaEmergente() {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Usuario");
		popupDialog.setSize(1000, 360);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);
		popupDialog.getContentPane().setLayout(null);

		JPanel popupPanel = new JPanel();
		popupPanel.setBounds(0, 0, 487, 321);
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JTextField nickname = new JTextField();
		nickname.setEditable(false);
		nickname.setBounds(93, 11, 381, 20);
		popupPanel.add(nickname);
		nickname.setColumns(10);

		JLabel nick = new JLabel("Nickname:");
		nick.setBounds(10, 11, 484, 20);
		popupPanel.add(nick);

		JLabel nom = new JLabel("Nombre: ");
		nom.setBounds(10, 39, 69, 20);
		popupPanel.add(nom);

		JTextField nombre = new JTextField();
		nombre.setColumns(10);
		nombre.setBounds(93, 39, 381, 20);
		popupPanel.add(nombre);

		JLabel ape = new JLabel("Apellido:");
		ape.setBounds(10, 70, 69, 20);
		popupPanel.add(ape);

		JTextField apellido = new JTextField();
		apellido.setColumns(10);
		apellido.setBounds(93, 70, 381, 20);
		popupPanel.add(apellido);

		JLabel mail = new JLabel("Email:");
		mail.setBounds(10, 101, 69, 20);
		popupPanel.add(mail);

		JTextField correo = new JTextField();
		correo.setEditable(false);
		correo.setColumns(10);
		correo.setBounds(93, 101, 381, 20);
		popupPanel.add(correo);

		JLabel fechan = new JLabel("FN:");
		fechan.setBounds(10, 134, 69, 20);
		popupPanel.add(fechan);

		IUsuarioController IUC = fabrica.getIUsuarioController();
		dataUsuario datosUsuario = null;

		try {
			datosUsuario = IUC.mostrarInfo(textBuscador.getText());
		} catch (UsuarioNoExisteExcepcion e) {
			JOptionPane.showMessageDialog(popupDialog, "No existe el usuario " + textBuscador.getText(),
					"Error", JOptionPane.ERROR_MESSAGE);
			log.error("Usuario no existe: " + e.getMessage());
			return;
		}

		LocalDate fechaNacimiento = datosUsuario.getNacimiento();

		Date date = java.sql.Date.valueOf(fechaNacimiento);

		SpinnerDateModel dateModel = new SpinnerDateModel(date, new Date(-2208975309000L), new Date(32503690800000L),
				Calendar.YEAR);
		JSpinner fechanacimiento = new JSpinner(dateModel);
		fechanacimiento.setBounds(93, 134, 381, 20);
		popupPanel.add(fechanacimiento);

		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechanacimiento, "dd/MM/yyyy");
		fechanacimiento.setEditor(dateEditor);

		nickname.setText(datosUsuario.getNickname());
		nombre.setText(datosUsuario.getNombre());
		apellido.setText(datosUsuario.getApellido());
		correo.setText(datosUsuario.getEmail());

		JButton aceptar = new JButton("Aceptar");
		aceptar.setEnabled(false);
		aceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aceptar.setBounds(19, 272, 224, 38);
		aceptar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(aceptar);

		dateModel.addChangeListener(e -> {
			aceptar.setEnabled(true);
		});

		JButton cancelar = new JButton("Cancelar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(253, 272, 224, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);

		JTextField mostrarNacionalidad = new JTextField();
		mostrarNacionalidad.setEditable(false);
		mostrarNacionalidad.setVisible(false);
		mostrarNacionalidad.setBounds(132, 165, 342, 20);
		popupPanel.add(mostrarNacionalidad);

		JLabel naclabel = new JLabel("Nacionalidad");
		naclabel.setVisible(false);
		naclabel.setBounds(10, 165, 100, 20);
		popupPanel.add(naclabel);

		JTextField mostrarDesc = new JTextField();
		mostrarDesc.setVisible(false);
		mostrarDesc.setEditable(false);
		mostrarDesc.setBounds(132, 165, 342, 20);
		popupPanel.add(mostrarDesc);

		JLabel desclabel = new JLabel("Descripcion:");
		desclabel.setVisible(false);
		desclabel.setBounds(10, 165, 100, 20);
		popupPanel.add(desclabel);

		JLabel urllabel = new JLabel("URL:");
		urllabel.setVisible(false);
		urllabel.setBounds(10, 196, 69, 20);
		popupPanel.add(urllabel);

		JTextField mostrarurl = new JTextField();
		mostrarurl.setVisible(false);
		mostrarurl.setEditable(false);
		mostrarurl.setBounds(132, 196, 342, 20);
		popupPanel.add(mostrarurl);

		JPanel panel = new JPanel();
		panel.setBounds(486, 50, 488, 216);
		popupDialog.getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 2, 20, 0));

		
		String[] columnSalidas = { "Nombre", "Lugar de Salida" };
		DefaultTableModel modelsalidas = new DefaultTableModel(columnSalidas, 0) {
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		JTable tablesalidas = new JTable(modelsalidas);
		
		List<dataSalida> salList = datosUsuario.getSalidas();
		
		if (salList != null) {
			for (dataSalida salItem : salList) {
				String nombresal = salItem.getNombre();
				String lugaract = salItem.getLugarSalida();
				modelsalidas.addRow(new Object[] {nombresal, lugaract});
			}
		}

		JScrollPane SalidasPane = new JScrollPane(tablesalidas);
		
		tablesalidas.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = tablesalidas.getSelectedRow();
			if (selectedRow != -1) {
				Object selectedValue = tablesalidas.getValueAt(selectedRow, 0);
				if (selectedValue != null) {
					this.buscadorSalida = selectedValue.toString();
				}
			}
		});
		
		if (datosUsuario.getisProveedor()) {
			
			JPanel textTable = new JPanel();
			textTable.setBounds(486, 11, 488, 28);
			popupDialog.getContentPane().add(textTable);
			textTable.setLayout(new GridLayout(1, 2, 0, 0));
			
			JLabel lblNewLabel = new JLabel("Actividades");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			textTable.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("Salidas");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			textTable.add(lblNewLabel_1);
			
			String[] columnNames = { "Nombre", "Ciudad" };
			DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
				boolean[] columnEditables = new boolean[] { false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};

			JTable table = new JTable(model);

			List<dataActividad> actList = datosUsuario.getActividades();
			
			if (actList != null) {
				for (dataActividad actItem : datosUsuario.getActividades()) {
					String nombreact = actItem.getNombre();
					String ciudadact = actItem.getCiudad();
					model.addRow(new Object[] {nombreact, ciudadact});
				}
			}

			JScrollPane actividadesPane = new JScrollPane(table);
			actividadesPane.setBounds(0, 130, 671, 337);
			panel.add(actividadesPane);
			
			JPanel btnpanels = new JPanel();
			btnpanels.setBounds(486, 277, 488, 33);
			popupDialog.getContentPane().add(btnpanels);
			btnpanels.setLayout(new GridLayout(1, 2, 15, 0));
			
			JButton btnActividades = new JButton("Consultar Actividades");
			btnActividades.putClientProperty("JButton.buttonType", "roundRect");
			btnpanels.add(btnActividades);
			
			JButton btnSalidas = new JButton("Consultar Salidas");
			btnSalidas.putClientProperty("JButton.buttonType", "roundRect");
			btnpanels.add(btnSalidas);
			
			table.getSelectionModel().addListSelectionListener(e -> {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					Object selectedValue = table.getValueAt(selectedRow, 0);
					if (selectedValue != null) {
						this.buscadorActividad = selectedValue.toString();
					}
				}
			});
			
			btnActividades.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (buscadorActividad == null || buscadorActividad == "") {
						JOptionPane.showMessageDialog(popupDialog, "Seleccione una actividad de la tabla para poder continuar",
								"Parametros invalidos", JOptionPane.ERROR_MESSAGE);
						return;
					}
					actividadesVista avtmp = new actividadesVista(selectedTheme);
					avtmp.mostrarVentanaEmergente(buscadorActividad);
					buscadorActividad = null;
				}
			});
			
			btnSalidas.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (buscadorSalida == null || buscadorSalida == "") {
						JOptionPane.showMessageDialog(popupDialog, "Seleccione una salida de la tabla para poder continuar",
								"Parametros invalidos", JOptionPane.ERROR_MESSAGE);
						return;
					}
					salidasVista svtmp = new salidasVista(selectedTheme);
					svtmp.mostrarDatosSalida(buscadorSalida);
					buscadorSalida = null;
				}
			});

			mostrarDesc.setVisible(true);
			mostrarDesc.setText(datosUsuario.getDescripcion());
			desclabel.setVisible(true);
			mostrarurl.setVisible(true);
			mostrarurl.setText(datosUsuario.getUrl());
			urllabel.setVisible(true);
			panel.add(actividadesPane);
			panel.add(SalidasPane);
		} else {
			JPanel textTable = new JPanel();
			textTable.setBounds(486, 11, 260, 28);
			popupDialog.getContentPane().add(textTable);
			textTable.setLayout(new GridLayout(1, 1, 0, 0));
			
			JLabel lblNewLabel = new JLabel("Salidas");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			textTable.add(lblNewLabel);
			
			String[] columnNames = { "Nombre", "Lugar de Salida" };
			DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
				boolean[] columnEditables = new boolean[] { false, false };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};

			JTable table = new JTable(model);

			List<dataSalida> salLista = datosUsuario.getSalidas();
			
			if (salLista != null) {
				for (dataSalida salItem : datosUsuario.getSalidas()) {
					String nombreact = salItem.getNombre();
					String lugarsalida = salItem.getLugarSalida();
					model.addRow(new Object[] {nombreact, lugarsalida});
				}
			}
			
			JPanel btnpanels = new JPanel();
			btnpanels.setBounds(486, 277, 238, 33);
			popupDialog.getContentPane().add(btnpanels);
			btnpanels.setLayout(new GridLayout(1, 1, 0, 0));
			
			JButton btnSalidas = new JButton("Consultar Salidas");
			btnSalidas.putClientProperty("JButton.buttonType", "roundRect");
			btnpanels.add(btnSalidas);
			
			btnSalidas.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (buscadorSalida == null || buscadorSalida == "") {
						JOptionPane.showMessageDialog(popupDialog, "Seleccione una salida de la tabla para poder continuar",
								"Parametros invalidos", JOptionPane.ERROR_MESSAGE);
						return;
					}
					salidasVista svtmp = new salidasVista(selectedTheme);
					svtmp.mostrarDatosSalida(buscadorSalida);
					buscadorSalida = null;
				}
			});
			
			mostrarNacionalidad.setVisible(true);
			mostrarNacionalidad.setText(datosUsuario.getNacionalidad());
			naclabel.setVisible(true);
			popupDialog.setSize(750, 360);
			panel.setLayout(new GridLayout(1, 1, 0, 0));
			panel.setBounds(486, 50, 238, 216);
			SalidasPane.setBounds(0, 0, 250, 427);
			panel.add(SalidasPane);
		}

		nombre.getDocument().addDocumentListener(new DocumentListener() {
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

		apellido.getDocument().addDocumentListener(new DocumentListener() {
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

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
			}
		});

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
			}
		});

		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (nickname.getText().isEmpty() || nombre.getText().isEmpty() || apellido.getText().isEmpty()
						|| correo.getText().isEmpty()) {
					JOptionPane.showMessageDialog(popupDialog, "Todos los campos deben estar llenos",
							"Campos incompletos", JOptionPane.ERROR_MESSAGE);
					return;
				}

				Date selectedDate = (Date) fechanacimiento.getValue();
				System.out.println(fechanacimiento.getValue().toString());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(selectedDate);

				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DAY_OF_MONTH);

				System.out.println(year + " " + month + " " + day);

				LocalDate nuevaFecha = LocalDate.of(year, month, day);

				System.out.println(nuevaFecha.toString());

				try {
					IUC.modificarUsuario(nickname.getText(), nombre.getText(), apellido.getText(), nuevaFecha);
					System.out.println("Se ha modificado el usuario");
					JOptionPane.showMessageDialog(popupDialog, "Usuario modificado con EXITO",
							"Registro Completado", JOptionPane.INFORMATION_MESSAGE);
					popupDialog.dispose();
				} catch (ParametrosInvalidosExcepcion a) {
					JOptionPane.showMessageDialog(popupDialog, "Los parametros ingresado no son validos",
							"Parametros invalidos", JOptionPane.ERROR_MESSAGE);
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
