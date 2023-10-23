package com.presentacion.vistas;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;

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
import com.sc.datatypes.dataSalida;
import com.sc.datatypes.dataUsuario;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.interfaces.IActividadController;
import com.sc.interfaces.IDepartamentoController;
import com.sc.interfaces.IInscripcionController;
import com.sc.interfaces.ISalidaController;
import com.sc.interfaces.IUsuarioController;

import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class salidasVista extends JPanel {
	
	private String selectedTheme;
	private PlaceholderTextField textBuscador;
	
	public salidasVista(String tema) {

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

        JLabel titulo = new JLabel("Salidas");
        titulo.setBounds(0, 0, 671, 40);
        titulo.putClientProperty("FlatLaf.styleClass", "h1");
        center.add(titulo);

        JLabel descripcion = new JLabel("Lista y crea las salidas que se manejen en el sistema");
        descripcion.setBounds(0, 39, 671, 40);
        center.add(descripcion);

		JButton btnBuscador = new JButton("Buscar Salida Turistica");
		btnBuscador.setBounds(0, 79, 671, 40);
		btnBuscador.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		center.add(btnBuscador);
		
		JPanel btnpanel = new JPanel();
		btnpanel.setBounds(0, 478, 671, 49);
		btnpanel.setOpaque(false);
		center.add(btnpanel);
		btnpanel.setLayout(new GridLayout(0, 2, 20, 0));

		JButton btnNewSalida = new JButton("Crear nueva Salida Turistica");
		btnpanel.add(btnNewSalida);
		btnNewSalida.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JButton btnRegistrarTurista = new JButton("Registrar turista a Salida");
		

		btnpanel.add(btnRegistrarTurista);
		btnNewSalida.putClientProperty("JButton.buttonType", "roundRect");
		btnNewSalida.putClientProperty("FlatLaf.styleClass", "h3");
		btnRegistrarTurista.putClientProperty("JButton.buttonType", "roundRect");
		btnRegistrarTurista.putClientProperty("FlatLaf.styleClass", "h3");

		
		ISalidaController ISC = Fabrica.getInstance().getISalidaController();
		List<dataSalida> salidas = ISC.getAllSalidas();
        
        String[] columnNames = {"Nombre", "Lugar de Salida", "Fecha de Salida", "Lugar Disponibles"};
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
		scrollPane.setBounds(0, 130, 671, 337);
		center.add(scrollPane);

        table.setRowSorter(sorter);

		if (salidas != null) {
			for (dataSalida salida : salidas) {
				String nomb = salida.getNombre();
				String ls = salida.getLugarSalida();
				String fs = salida.getFechaSalida().toString();
				String ld = salida.getCapacidad() + "";
				model.addRow(new Object[] {nomb, ls, fs, ld});
			}
		}

        JLabel wave = new JLabel("");
		wave.setIcon(new ImageIcon(principalVista.class.getResource("/com/presentacion/res/wave.png")));
		wave.setBounds(274, 435, 600, 344);
		add(wave);
		
		btnBuscador.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarSalida1();
			}
		});

		btnNewSalida.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarAltaSalida();
				
				model.setRowCount(0);
				
				List<dataSalida> salidas = ISC.getAllSalidas();

				if (salidas != null) {
					for (dataSalida salida : salidas) {
						String nomb = salida.getNombre();
						String ls = salida.getLugarSalida();
						String fs = salida.getFechaSalida().toString();
						model.addRow(new Object[] {nomb, ls, fs});
					}
				}
			}
		});
		
		btnRegistrarTurista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarRegistrar1();
				return;
			}
		});
		
		setupTheme();
	}
	
	private void mostrarRegistrar1() {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Salida");
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

		JLabel dep = new JLabel("Departamento:");
		dep.setBounds(12, 11, 142, 19);
		popupPanel.add(dep);

		JComboBox<String> departamento = new JComboBox<>();
		DefaultComboBoxModel<String> departamentoModel = new DefaultComboBoxModel<>();
		departamento.setBounds(164, 9, 312, 22);

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
				mostrarRegistrar2(departamento.getSelectedItem().toString());
				popupDialog.dispose();
				return;
			}
		});
		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		popupDialog.setVisible(true);
	}

	private void mostrarRegistrar2(String departamentoParameter) {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Salida");
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

		JLabel ac = new JLabel("Actividad:");
		ac.setBounds(12, 11, 142, 19);
		popupPanel.add(ac);

		JComboBox<String> actividad = new JComboBox<>();
		DefaultComboBoxModel<String> actividadModel = new DefaultComboBoxModel<>();
		actividad.setBounds(164, 9, 312, 22);

		IActividadController IAC = Fabrica.getInstance().getIActividadController();
		List<dataActividad> actividades = IAC.getAllActividadesDepartamento(departamentoParameter);

		if (actividades == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen departamentos por los cuales buscar actividades",
					"Error", JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for (dataActividad activ : actividades) {
				actividadModel.addElement(activ.getNombre());
				actividad.setModel(actividadModel);
				popupPanel.add(actividad);
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
				mostrarRegistrar3(actividad.getSelectedItem().toString());
				popupDialog.dispose();
				return;
			}
		});
		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		popupDialog.setVisible(true);
	}

	private void mostrarRegistrar3(String ActividadParameter) {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Salida");
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

		JLabel ac = new JLabel("Salida:");
		ac.setBounds(12, 11, 142, 19);
		popupPanel.add(ac);
		
		JLabel tur = new JLabel("Turista:");
		tur.setBounds(12, 57, 142, 19);
		popupPanel.add(tur);
		
		JLabel count = new JLabel("Cant. Inscriptos:");
		count.setBounds(13, 103, 142, 19);
		popupPanel.add(count);
		
		JSpinner cantidad = new JSpinner();
		cantidad.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		cantidad.setBounds(164, 101, 312, 22);
		popupPanel.add(cantidad);

		JComboBox<String> salida = new JComboBox<>();
		DefaultComboBoxModel<String> salidaModel = new DefaultComboBoxModel<>();
		salida.setBounds(164, 9, 312, 22);

		ISalidaController ISC = Fabrica.getInstance().getISalidaController();
		List<dataSalida> salidas = ISC.obtenerSalidasVigentesPorActividad(ActividadParameter);

		if (salidas == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen salidas a las cuales registrar turistas", "Error",
					JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for (dataSalida salid : salidas) {
				salidaModel.addElement(salid.getNombre());
				salida.setModel(salidaModel);
				popupPanel.add(salida);
			}
		}
		
		JComboBox<String> turista = new JComboBox<>();
		DefaultComboBoxModel<String> turistaModel = new DefaultComboBoxModel<>();
		turista.setBounds(164, 55, 312, 22);

		IUsuarioController IUC = Fabrica.getInstance().getIUsuarioController();
		List<dataUsuario> turistas = IUC.listarUsuarios();

		if (turistas == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen departamentos por los cuales buscar actividades",
					"Error", JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for (dataUsuario turist : turistas) {
				if (!turist.getisProveedor()) {
					turistaModel.addElement(turist.getNickname());
					turista.setModel(turistaModel);
					popupPanel.add(turista);
				}
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
				IInscripcionController IIC = Fabrica.getInstance().getIInscripcionController();
				Object cantobj = cantidad.getValue();
				int cantint = (int) cantobj;
				
				try {
					IIC.crearInscripcion(LocalDate.now(), cantint, turista.getSelectedItem().toString(), salida.getSelectedItem().toString(), ActividadParameter);
					JOptionPane.showMessageDialog(popupDialog, "Usuario registrado con EXITO",
							"Registro Completado", JOptionPane.INFORMATION_MESSAGE);
					popupDialog.dispose();
				} catch (UsuarioYaExisteExcepcion e2) {
					JOptionPane.showMessageDialog(popupDialog, "Usuario ya registrado en esta salida, volver a intentar",
							"Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				} catch (ParametrosInvalidosExcepcion e1) {
					dataSalida tmp = ISC.mostrarDatosSalida(salida.getSelectedItem().toString());
					JOptionPane.showMessageDialog(popupDialog, "Parametros invalidos, Tenga cuidado con la cantidad de inscripciones no sea mayor a las disponibles" + " (Capacidad actual: " + tmp.getCapacidad() + ")",
							"Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				} catch (UsuarioNoExisteExcepcion e1) {
					JOptionPane.showMessageDialog(popupDialog, "Usuario no existe",
							"Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		});
		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		popupDialog.setVisible(true);
		
		
	}
	
	private void mostrarSalida1() {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Salida");
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

		JLabel dep = new JLabel("Departamento:");
		dep.setBounds(12, 11, 142, 19);
		popupPanel.add(dep);

		JComboBox<String> departamento = new JComboBox<>();
		DefaultComboBoxModel<String> departamentoModel = new DefaultComboBoxModel<>();
		departamento.setBounds(164, 9, 312, 22);

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
				mostrarSalida2(departamento.getSelectedItem().toString());
				popupDialog.dispose();
				return;
			}
		});
		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		popupDialog.setVisible(true);
	}
	
	private void mostrarSalida2(String departamentoParameter) {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Salida");
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

		JLabel ac = new JLabel("Actividad:");
		ac.setBounds(12, 11, 142, 19);
		popupPanel.add(ac);

		JComboBox<String> actividad = new JComboBox<>();
		DefaultComboBoxModel<String> actividadModel = new DefaultComboBoxModel<>();
		actividad.setBounds(164, 9, 312, 22);

		IActividadController IAC = Fabrica.getInstance().getIActividadController();
		List<dataActividad> actividades = IAC.getAllActividadesDepartamento(departamentoParameter);

		if (actividades == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen departamentos por los cuales buscar actividades",
					"Error", JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for (dataActividad activ : actividades) {
				actividadModel.addElement(activ.getNombre());
				actividad.setModel(actividadModel);
				popupPanel.add(actividad);
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
				mostrarSalida3(actividad.getSelectedItem().toString());
				popupDialog.dispose();
				return;
			}
		});
		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		popupDialog.setVisible(true);
	}
	
	private void mostrarSalida3(String ActividadParameter) {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Salida");
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

		JLabel ac = new JLabel("Salida:");
		ac.setBounds(12, 11, 142, 19);
		popupPanel.add(ac);

		JComboBox<String> salida = new JComboBox<>();
		DefaultComboBoxModel<String> salidaModel = new DefaultComboBoxModel<>();
		salida.setBounds(164, 9, 312, 22);

		ISalidaController ISC = Fabrica.getInstance().getISalidaController();
		List<dataSalida> salidas = ISC.obtenerSalidasVigentesPorActividad(ActividadParameter);

		if (salidas == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen salidas para esta actividad",
					"Error", JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for (dataSalida salid : salidas) {
				salidaModel.addElement(salid.getNombre());
				salida.setModel(salidaModel);
				popupPanel.add(salida);
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
				mostrarDatosSalida(salida.getSelectedItem().toString());
				popupDialog.dispose();
				return;
			}
		});
		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		popupDialog.setVisible(true);
	}

	public void mostrarDatosSalida(String salidaParameter) {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Salida");
		popupDialog.setSize(506, 324);
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

		JLabel lugS = new JLabel("Lugar de Salida:");
		lugS.setBounds(10, 39, 136, 20);
		popupPanel.add(lugS);

		JTextField lugarsalida = new JTextField();
		lugarsalida.setEditable(false);
		lugarsalida.setColumns(10);
		lugarsalida.setBounds(149, 39, 325, 20);
		popupPanel.add(lugarsalida);

		JLabel cap = new JLabel("Capacidad:");
		cap.setBounds(10, 70, 136, 20);
		popupPanel.add(cap);

		JLabel fechaa = new JLabel("Fecha de Alta:");
		fechaa.setBounds(10, 101, 136, 20);
		popupPanel.add(fechaa);
		
		JTextField capacidad = new JTextField();
		capacidad.setText((String) null);
		capacidad.setEditable(false);
		capacidad.setColumns(10);
		capacidad.setBounds(149, 70, 325, 20);
		popupPanel.add(capacidad);
		
		JTextField fechaalta = new JTextField();
		fechaalta.setText((String) null);
		fechaalta.setEditable(false);
		fechaalta.setColumns(10);
		fechaalta.setBounds(149, 101, 325, 20);
		popupPanel.add(fechaalta);
		
		JTextField fechasalida = new JTextField();
		fechasalida.setText((String) null);
		fechasalida.setEditable(false);
		fechasalida.setColumns(10);
		fechasalida.setBounds(149, 132, 325, 20);
		popupPanel.add(fechasalida);

		ISalidaController ISC = Fabrica.getInstance().getISalidaController();
		dataSalida datos = null;
		datos = ISC.mostrarDatosSalida(salidaParameter);
		if (datos == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existe la salidass",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int capacidadInt = datos.getCapacidad();
		String capacidadString = Integer.toString(capacidadInt);
		
		nombre.setText(datos.getNombre());
		lugarsalida.setText(datos.getLugarSalida());
		capacidad.setText(capacidadString);
		fechasalida.setText(datos.getFechaSalida().toString());
		fechaalta.setText(datos.getFechaAlta().toString());

		JSeparator separator = new JSeparator();
		separator.setBounds(96, 223, 297, 2);
		popupPanel.add(separator);

		JButton cancelar = new JButton("Salir");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(133, 236, 224, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);
		
		
		
		JLabel fechas = new JLabel("Fecha de Salida:");
		fechas.setBounds(10, 132, 136, 20);
		popupPanel.add(fechas);

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
			}
		});

		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		popupDialog.setVisible(true);
	}
	
	private void mostrarCrearSalida(String departamento) {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Salida");
		popupDialog.setSize(505, 276);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);
		popupDialog.getContentPane().setLayout(null);

		JPanel popupPanel = new JPanel();
		popupPanel.setBounds(0, 0, 490, 243);
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JLabel nom = new JLabel("Nombre: ");
		nom.setBounds(10, 39, 128, 20);
		popupPanel.add(nom);

		JTextField nombre = new JTextField();
		nombre.setColumns(10);
		nombre.setBounds(148, 39, 326, 20);
		popupPanel.add(nombre);

		JLabel cap = new JLabel("Capacidad:");
		cap.setBounds(10, 132, 128, 20);
		popupPanel.add(cap);

		JLabel fechan = new JLabel("Fecha de Salida:");
		fechan.setBounds(10, 101, 128, 20);
		popupPanel.add(fechan);

		JSpinner fechaSalida = new JSpinner();
		fechaSalida.setModel(new SpinnerDateModel(new Date(946695600000L), new Date(-2208975309000L),
				new Date(32503690800000L), Calendar.YEAR));
		fechaSalida.setBounds(148, 101, 150, 20);
		popupPanel.add(fechaSalida);

		SpinnerDateModel dateModel = (SpinnerDateModel) fechaSalida.getModel();
		Date date = (Date) dateModel.getValue();

		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechaSalida, "dd/MM/yyyy");
		fechaSalida.setEditor(dateEditor);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		JLabel salida = new JLabel("Lugar de salida:");
		salida.setBounds(10, 70, 128, 20);
		popupPanel.add(salida);
		
		JTextComponent lugarSalida = new JTextField();
		lugarSalida.setBounds(148, 70, 326, 20);
		popupPanel.add(lugarSalida);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(86, 163, 297, 2);
		popupPanel.add(separator);

		JButton aceptar = new JButton("Aceptar");
		aceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		aceptar.setBounds(7, 190, 224, 38);
		aceptar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(aceptar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(250, 190, 224, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);
		
		JSpinner capacidad = new JSpinner();
		capacidad.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		capacidad.setBounds(148, 132, 150, 20);
		popupPanel.add(capacidad);
		
		JLabel act = new JLabel("Actividad:");
		act.setBounds(10, 11, 128, 20);
		popupPanel.add(act);
		
		JComboBox<String> actividades = new JComboBox<>();
		DefaultComboBoxModel<String> actividadesModel = new DefaultComboBoxModel<>();
		actividades.setBounds(148, 10, 326, 22);
		
		IActividadController IAC = Fabrica.getInstance().getIActividadController();
		List<dataActividad> listAct = IAC.getAllActividadesDepartamento(departamento.toString());
		
		if (listAct == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen departamentos por los cuales buscar actividades para la salida",
					"Error", JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for(dataActividad a : listAct) {
				actividadesModel.addElement(a.getNombre());
				actividades.setModel(actividadesModel);
				popupPanel.add(actividades);
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
				if (nombre.getText().isEmpty() || lugarSalida.getText().isEmpty()) {
					JOptionPane.showMessageDialog(popupDialog, "Todos los campos deben estar llenos",
							"Campos incompletos", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Date selectedDate = (Date) fechaSalida.getValue();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(selectedDate);

				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				
				Object capacidadObj = capacidad.getValue();
				int capacidadInt = (int) capacidadObj;
				
				ISalidaController ISC = Fabrica.getInstance().getISalidaController();
				try {
					System.out.println(nombre.getText());
					ISC.crearSalida(nombre.getText(), capacidadInt, LocalDate.now(), LocalDate.of(year, month, day), lugarSalida.getText(), actividades.getSelectedItem().toString());
					System.out.println("Se ha creado la salida " + nombre.getText());
					JOptionPane.showMessageDialog(popupDialog, "Salida creada con EXITO",
							"Registro Completado", JOptionPane.INFORMATION_MESSAGE);
					popupDialog.dispose();
				} catch (UsuarioYaExisteExcepcion a) {
					JOptionPane.showMessageDialog(popupDialog, a.getMessage(), "Ya existe la salida " + nombre.getText(), JOptionPane.ERROR_MESSAGE);
					return;
				} catch (ParametrosInvalidosExcepcion e1) {
					JOptionPane.showMessageDialog(popupDialog, e1.getMessage(), "Parametros invalidos", JOptionPane.ERROR_MESSAGE);
					return;
				} catch (UsuarioNoExisteExcepcion e1) {
					JOptionPane.showMessageDialog(popupDialog, e1.getMessage(), "No existe la salida " + nombre.getText(), JOptionPane.ERROR_MESSAGE);
					return;
				} 
			}
		});

		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		popupDialog.setVisible(true);
	}
	
	private void mostrarAltaSalida() {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Salida");
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
		
		JLabel paq = new JLabel("Departamento:");
		paq.setBounds(12, 11, 142, 19);
		popupPanel.add(paq);

		JComboBox<String> departamento = new JComboBox<>();
		DefaultComboBoxModel<String> departamentoModel = new DefaultComboBoxModel<>();
		departamento.setBounds(164, 9, 312, 22);
		
		IDepartamentoController IDC = Fabrica.getInstance().getIDepartamentoController();
		List<dataDepartamento> departamentos = IDC.listarDepartamentos();
		
		if (departamentos == null) {
			JOptionPane.showMessageDialog(popupDialog, "No existen departamentos por los cuales buscar actividades para la salida",
					"Error", JOptionPane.ERROR_MESSAGE);
			popupDialog.dispose();
			return;
		} else {
			for(dataDepartamento dept : departamentos) {
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
				mostrarCrearSalida(departamento.getSelectedItem().toString());
				popupDialog.dispose();
				return;
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
