package com.presentacion;

import com.presentacion.vistas.*;
import com.sc.EMFactory;
import com.sc.Fabrica;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.excepciones.precargaExcepcion;
import com.sc.interfaces.IActividadController;
import com.sc.interfaces.IDepartamentoController;
import com.sc.interfaces.ISalidaController;
import com.sc.interfaces.IUsuarioController;
import com.sc.manejadores.ActividadManejador;
import com.sc.manejadores.DepartamentoManejador;
import com.sc.manejadores.UsuarioManejador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDeepOceanIJTheme;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;

public class dashB extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> temas;

	public static void main(String[] args) {

		FlatDarkLaf.setup();
		EventQueue.invokeLater(() -> {
			try {
//				precarga();
				EMFactory.getEntityManagerFactory();
//				IAC.crearActividad("Artigas", "fashionSpot", "Test", "aaa", 1, 1, "aaa", LocalDate.now());
//				ISalidaController ISC = Fabrica.getInstance().getISalidaController();
//				ISC.crearSalida("SalidaPrueba", 10, LocalDate.now(), LocalDate.of(2003, 1, 1), "UTEC", "Test");
				
				dashB frame = new dashB();

				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	public dashB() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(dashB.class.getResource("/com/presentacion/res/mundo.png")));

		// Trae la instancia
		Fabrica fabrica = Fabrica.getInstance();

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel header = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawShadow(g);
			}
		};
		header.setBounds(0, 0, 1084, 82);
		contentPane.add(header);
		header.setLayout(null);

		JLabel nombre = new JLabel("TurismoUY");
		nombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		nombre.setBounds(39, 0, 180, 60);
		header.add(nombre);
		nombre.putClientProperty("FlatLaf.styleClass", "h1");

		JLabel titulo = new JLabel("Sistema de gestion - Administrador");
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		titulo.setBounds(277, 0, 531, 60);
		header.add(titulo);

		String[] themes = { "Light", "Cyan Light", "Dark", "Material Deep Ocean", "Github Dark", "AtomOne Dark" };
		temas = new JComboBox<>(themes);
		temas.putClientProperty("JComponent.roundRect", true);
		temas.setSelectedItem("Light");
		temas.setBounds(978, 19, 96, 22);
		header.add(temas);

		JPanel menu = new JPanel();
		menu.setBounds(20, 93, 192, 418);
		contentPane.add(menu);
		menu.setLayout(new GridLayout(6, 1, 0, 20));

		JPanel contenido = new JPanel();
		contenido.setBounds(210, 93, 874, 588);
		contentPane.add(contenido);
		contenido.setLayout(null);
		titulo.putClientProperty("FlatLaf.styleClass", "h1");
		temas.addActionListener(this::onThemeSelected);

		principalVista inicioFrame = new principalVista((String) temas.getSelectedItem());

		contenido.setLayout(new BorderLayout());
		contenido.add(inicioFrame, BorderLayout.CENTER);

		JButton inicio = new JButton("Inicio");
		inicio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		inicio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contenido.removeAll();

				principalVista v = new principalVista((String) temas.getSelectedItem());
				contenido.setLayout(new BorderLayout());
				contenido.add(v, BorderLayout.CENTER);

				contenido.revalidate();
				contenido.repaint();
			}
		});
		inicio.putClientProperty("JButton.buttonType", "roundRect");
		inicio.putClientProperty("FlatLaf.styleClass", "h3");
		menu.add(inicio);

		JButton usuarios = new JButton("Usuarios");
		usuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		usuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contenido.removeAll();

				usuariosVista v = new usuariosVista((String) temas.getSelectedItem(), fabrica);
				contenido.setLayout(new BorderLayout());
				contenido.add(v, BorderLayout.CENTER);

				contenido.revalidate();
				contenido.repaint();
			}
		});
		usuarios.putClientProperty("JButton.buttonType", "roundRect");
		usuarios.putClientProperty("FlatLaf.styleClass", "h3");
		menu.add(usuarios);

		JButton salidas = new JButton("Salidas");
		salidas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		salidas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contenido.removeAll();

				salidasVista v = new salidasVista((String) temas.getSelectedItem());
				contenido.setLayout(new BorderLayout());
				contenido.add(v, BorderLayout.CENTER);

				contenido.revalidate();
				contenido.repaint();
			}
		});
		salidas.putClientProperty("JButton.buttonType", "roundRect");
		salidas.putClientProperty("FlatLaf.styleClass", "h3");
		menu.add(salidas);

		JButton actividades = new JButton("Actividades");
		actividades.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		actividades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contenido.removeAll();

				actividadesVista v = new actividadesVista((String) temas.getSelectedItem());
				contenido.setLayout(new BorderLayout());
				contenido.add(v, BorderLayout.CENTER);

				contenido.revalidate();
				contenido.repaint();
			}
		});
		actividades.putClientProperty("JButton.buttonType", "roundRect");
		actividades.putClientProperty("FlatLaf.styleClass", "h3");
		menu.add(actividades);

		JButton paquetes = new JButton("Paquetes");
		paquetes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		paquetes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contenido.removeAll();

				paquetesVista v = new paquetesVista((String) temas.getSelectedItem());
				contenido.setLayout(new BorderLayout());
				contenido.add(v, BorderLayout.CENTER);

				contenido.revalidate();
				contenido.repaint();
			}
		});
		paquetes.putClientProperty("JButton.buttonType", "roundRect");
		paquetes.putClientProperty("FlatLaf.styleClass", "h3");
		menu.add(paquetes);

		JButton departamentos = new JButton("Departamentos");
		departamentos.setLocation(18, 352);
		departamentos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		departamentos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contenido.removeAll();

				departamentosVista v = new departamentosVista((String) temas.getSelectedItem());
				contenido.setLayout(new BorderLayout());
				contenido.add(v, BorderLayout.CENTER);

				contenido.revalidate();
				contenido.repaint();
			}
		});
		departamentos.putClientProperty("JButton.buttonType", "roundRect");
		departamentos.putClientProperty("FlatLaf.styleClass", "h3");
		menu.add(departamentos);


		JButton btnPrecarga = new JButton();
		btnPrecarga.setText("Precargar Datos");
		btnPrecarga.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					precarga();
					JOptionPane.showMessageDialog(header, "Los datos se han cargado", "Precarga",
							JOptionPane.INFORMATION_MESSAGE);
					btnPrecarga.setEnabled(false);
					btnPrecarga.setVisible(false);
					return;
				} catch (precargaExcepcion e2) {
					JOptionPane.showMessageDialog(header, "Los datos ya se habian precargado previamente", "Precarga",
							JOptionPane.ERROR_MESSAGE);
					btnPrecarga.setEnabled(false);
					btnPrecarga.setVisible(false);

					return;
				} catch (ParametrosInvalidosExcepcion e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPrecarga.putClientProperty("JButton.buttonType", "roundRect");
		btnPrecarga.setIcon(new ImageIcon(dashB.class.getResource("/com/presentacion/res/loading.png")));
		btnPrecarga.setBounds(31, 633, 154, 37);
		contentPane.add(btnPrecarga);
		
		JButton btnConexion = new JButton();
		btnConexion.setVisible(false);
		btnConexion.setEnabled(false);
		btnConexion.setIcon(new ImageIcon(dashB.class.getResource("/com/presentacion/res/señal.png")));
		btnConexion.putClientProperty("JButton.buttonType", "roundRect");
		btnConexion.setText("Conf. Conexion");
		btnConexion.setBounds(31, 633, 154, 37);
		contentPane.add(btnConexion);
		
		btnConexion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				conexionConfig();
			}
		});

		applyLightTheme();
	}

	private void onThemeSelected(ActionEvent e) {
		String selectedTheme = (String) temas.getSelectedItem();
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

	private void drawShadow(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int shadowSize = 10;
		int width = getWidth();
		int height = getHeight();

		g2d.setColor(new Color(0, 0, 0, 50));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int offsetX = 0;
		int offsetY = 80;

		Area shadowArea = new Area(
				new RoundRectangle2D.Double(offsetX, offsetY, width - shadowSize, height - shadowSize, 20, 20));
		shadowArea.subtract(new Area(new RoundRectangle2D.Double(shadowSize + offsetX, shadowSize + offsetY,
				width - 2 * shadowSize, height - 2 * shadowSize, 20, 20)));
		g2d.fill(shadowArea);
	}

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

	private void applyDeepOceanTheme() {
		try {
			UIManager.setLookAndFeel(new FlatMaterialDeepOceanIJTheme());
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
	
	private void conexionConfig() {
		JDialog popupDialog = new JDialog();
		popupDialog.setResizable(false);
		popupDialog.setTitle("Configurarcion de Conexion");
		popupDialog.setSize(506, 229);
		popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popupDialog.setLocationRelativeTo(null);
		popupDialog.getContentPane().setLayout(null);

		JPanel popupPanel = new JPanel();
		popupPanel.setBounds(0, 0, 490, 285);
		popupDialog.getContentPane().add(popupPanel);
		popupPanel.setLayout(null);

		JTextField nombre = new JTextField();
		nombre.setBounds(149, 11, 325, 20);
		popupPanel.add(nombre);
		nombre.setColumns(10);

		JLabel nom = new JLabel("Nombre:");
		nom.setBounds(10, 11, 136, 20);
		popupPanel.add(nom);

		JLabel ipp = new JLabel("IP:");
		ipp.setBounds(10, 39, 136, 20);
		popupPanel.add(ipp);

		JTextField ip = new JTextField();
		ip.setColumns(10);
		ip.setBounds(149, 39, 325, 20);
		popupPanel.add(ip);

		JLabel us = new JLabel("Usuario:");
		us.setBounds(10, 70, 136, 20);
		popupPanel.add(us);

		JLabel cont = new JLabel("Contraseña:");
		cont.setBounds(10, 101, 136, 20);
		popupPanel.add(cont);
		
		JTextField user = new JTextField();
		user.setText((String) null);
		user.setColumns(10);
		user.setBounds(149, 70, 325, 20);
		popupPanel.add(user);
		
		JTextField pass = new JTextField();
		pass.setText((String) null);
		pass.setColumns(10);
		pass.setBounds(149, 101, 325, 20);
		popupPanel.add(pass);

		JSeparator separator = new JSeparator();
		separator.setBounds(96, 132, 297, 2);
		popupPanel.add(separator);

		JButton cancelar = new JButton("Cancelar");
		cancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelar.setBounds(256, 145, 224, 38);
		cancelar.putClientProperty("JButton.buttonType", "roundRect");
		popupPanel.add(cancelar);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.putClientProperty("JButton.buttonType", "roundRect");
		aceptar.setBounds(10, 145, 224, 38);
		popupPanel.add(aceptar);

		cancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
			}
		});
		
		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupDialog.dispose();
			}
		});

		popupDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		popupDialog.setVisible(true);
	}

	private static void precarga() throws ParametrosInvalidosExcepcion, precargaExcepcion {
		try {
			Fabrica fabrica = Fabrica.getInstance();
			IUsuarioController IUC = fabrica.getIUsuarioController();
			IActividadController IAC = fabrica.getIActividadController();
			IDepartamentoController IDC = fabrica.getIDepartamentoController();

			UsuarioManejador um = UsuarioManejador.getinstance();
			DepartamentoManejador dm = DepartamentoManejador.getinstance();
			ActividadManejador am = ActividadManejador.getinstance();

			IUC.modificarUsuario("Dargon227", "Jorge", "Blandini", LocalDate.of(2000, 05, 15));
			IUC.registrarTurista("Nico", "Nicolas", "Blandin", "exzeblan@gmail.com", "Uruguayan",
					LocalDate.of(2000, 05, 12));
			IUC.registrarTurista("jorge123", "Jorge", "Rodriguez", "jorge123@gmail.com", "Uruguay",
					LocalDate.of(2000, 05, 20));
			IUC.registrarTurista("alice02", "Alice", "Johnson", "aaalice01@gmail.com", "American",
					LocalDate.of(1995, 11, 8));
			IUC.registrarTurista("michael88", "Michael", "Smith", "michael88@gmail.com", "British",
					LocalDate.of(1988, 3, 15));
			IUC.registrarTurista("laura789", "Laura", "Garcia", "laura789@gmail.com", "Spanish",
					LocalDate.of(1992, 7, 23));
			IUC.registrarTurista("emily22", "Emily", "Anderson", "emily22@gmail.com", "Canadian",
					LocalDate.of(1997, 9, 4));
			IUC.registrarTurista("carlos456", "Carlos", "Martinez", "carlos456@gmail.com", "Mexican",
					LocalDate.of(1990, 12, 30));
			IUC.registrarTurista("anna007", "Anna", "Williams", "anna007@gmail.com", "Australian",
					LocalDate.of(1993, 2, 18));
			IUC.registrarTurista("david777", "David", "Lee", "david777@gmail.com", "Korean", LocalDate.of(1998, 6, 10));
			IUC.registrarTurista("maria555", "Maria", "Lopez", "maria555@gmail.com", "Argentinian",
					LocalDate.of(1991, 10, 25));
			IUC.registrarTurista("robert222", "Robert", "Brown", "robert222@gmail.com", "American",
					LocalDate.of(1985, 4, 5));
			IUC.registrarTurista("linda444", "Linda", "Davis", "linda444@gmail.com", "Canadian",
					LocalDate.of(1987, 8, 14));
			IUC.registrarTurista("pablo666", "Pablo", "Gonzalez", "pablo666@gmail.com", "Mexican",
					LocalDate.of(1994, 1, 3));
			IUC.registrarTurista("emily999", "Emily", "Harris", "emily999@gmail.com", "British",
					LocalDate.of(1989, 7, 28));
			IUC.registrarTurista("alex333", "Alex", "Moore", "alex333@gmail.com", "Australian",
					LocalDate.of(1996, 12, 9));
			IUC.registrarTurista("juan111", "Juan", "Perez", "juan111@gmail.com", "Spanish", LocalDate.of(1999, 5, 17));
			IUC.registrarTurista("lucas888", "Lucas", "Taylor", "lucas888@gmail.com", "Australian",
					LocalDate.of(1986, 2, 22));
			IUC.registrarTurista("mariana777", "Mariana", "Rodriguez", "mariana777@gmail.com", "Mexican",
					LocalDate.of(1992, 11, 6));
			IUC.registrarTurista("olivia555", "Olivia", "Wilson", "olivia555@gmail.com", "American",
					LocalDate.of(1984, 9, 1));
			IUC.registrarTurista("carolina444", "Carolina", "Garcia", "carolina444@gmail.com", "Spanish",
					LocalDate.of(1987, 4, 19));
			IUC.registrarTurista("andres222", "Andres", "Hernandez", "andres222@gmail.com", "Colombian",
					LocalDate.of(1993, 8, 12));
			IUC.registrarTurista("sofia999", "Sofia", "Lopez", "sofia999@gmail.com", "Argentinian",
					LocalDate.of(1998, 3, 30));
			IUC.registrarTurista("gabriel666", "Gabriel", "Silva", "gabriel666@gmail.com", "Brazilian",
					LocalDate.of(1997, 6, 27));
			IUC.registrarTurista("ana111", "Ana", "Fernandez", "ana111@gmail.com", "Spanish",
					LocalDate.of(1995, 11, 14));
			IUC.registrarTurista("kevin888", "Kevin", "Miller", "kevin888@gmail.com", "Canadian",
					LocalDate.of(1990, 7, 5));
			IUC.registrarTurista("luis777", "Luis", "Perez", "luis777@gmail.com", "Mexican", LocalDate.of(1994, 12, 9));
			IUC.registrarTurista("clara555", "Clara", "Jones", "clara555@gmail.com", "American",
					LocalDate.of(1988, 1, 22));
			IUC.registrarTurista("daniel444", "Daniel", "Brown", "daniel444@gmail.com", "British",
					LocalDate.of(1983, 5, 3));
			IUC.registrarTurista("valentina222", "Valentina", "Garcia", "valentina222@gmail.com", "Colombian",
					LocalDate.of(1996, 9, 16));
			IUC.registrarTurista("martin999", "Martin", "Williams", "martin999@gmail.com", "Australian",
					LocalDate.of(1991, 3, 8));
			IUC.registrarTurista("juan777", "Juan", "Lopez", "juan777@gmail.com", "Spanish", LocalDate.of(1982, 4, 18));
			IUC.registrarTurista("sara666", "Sara", "Smith", "sara666@gmail.com", "Canadian",
					LocalDate.of(1987, 8, 27));
			IUC.registrarProveedor("nickname1", "Juan", "Gomez", "juan@gmail.com",
					"Proveedor de productos electrónicos", "www.juangomez.com", LocalDate.of(1985, 8, 17));
			IUC.registrarProveedor("sarasola", "Ana Sara", "Rodriguez", "ana@gmail.com",
					"Distribuidora de ropa de moda", "www.anasara.com", LocalDate.of(1990, 3, 25));
			IUC.registrarProveedor("acostaMuebles", "María", "Acosta", "maria.acosta@muebles.com",
					"Fabricante de muebles artesanales", "www.acostamuebles.com", LocalDate.of(1975, 12, 5));
			IUC.registrarProveedor("tecnoMax", "Luis", "Martínez", "luis@tecnomax.com",
					"Especialistas en electrónica y tecnología", "www.tecnomax.com", LocalDate.of(2002, 6, 10));
			IUC.registrarProveedor("gourmetDelight", "Elena", "Pérez", "elena@gourmetdelight.com",
					"Importadora de productos gourmet internacionales", "www.gourmetdelight.com",
					LocalDate.of(1998, 9, 21));
			IUC.registrarProveedor("decorArte", "Roberto", "Fernández", "roberto@decorarte.com",
					"Tienda de decoración y arte para el hogar", "www.decorarte.com", LocalDate.of(1982, 2, 8));
			IUC.registrarProveedor("ropaSport", "Lucía", "Hernández", "lucia.ropa@sport.com",
					"Especialistas en ropa deportiva y accesorios", "www.ropasport.com", LocalDate.of(1995, 7, 14));
			IUC.registrarProveedor("beautyZone", "Carolina", "Vargas", "caro@beautyzone.com",
					"Productos de belleza y cuidado personal", "www.beautyzone.com", LocalDate.of(2005, 4, 30));
			IUC.registrarProveedor("naturalHealth", "Diego", "López", "diego@naturalhealth.com",
					"Productos naturales y suplementos alimenticios", "www.naturalhealth.com",
					LocalDate.of(1993, 1, 12));
			IUC.registrarProveedor("luxuryWatches", "Julia", "Pérez", "julia@luxurywatches.com",
					"Relojes de lujo de marcas internacionales", "www.luxurywatches.com", LocalDate.of(1978, 11, 7));
			IUC.registrarProveedor("urbanStyle", "Carlos", "Montes", "carlos@urbanstyle.com",
					"Ropa urbana y moderna para jóvenes", "www.urbanstyle.com", LocalDate.of(2000, 9, 18));
			IUC.registrarProveedor("greenPlanet", "María", "Fuentes", "maria@greenplanet.com",
					"Productos ecológicos y sustentables", "www.greenplanet.com", LocalDate.of(1997, 5, 3));
			IUC.registrarProveedor("techHub", "Andrés", "Hernández", "andres@techhub.com",
					"Centro de innovación y tecnología", "www.techhub.com", LocalDate.of(1988, 12, 15));
			IUC.registrarProveedor("coffeeWorld", "Laura", "Martínez", "laura@coffeeworld.com",
					"Café de calidad y accesorios para amantes del café", "www.coffeeworld.com",
					LocalDate.of(1991, 10, 9));
			IUC.registrarProveedor("bookHaven", "Pedro", "Gómez", "pedro@bookhaven.com",
					"Librería con una amplia selección de libros", "www.bookhaven.com", LocalDate.of(1980, 4, 26));
			IUC.registrarProveedor("fashionista", "Isabela", "Silva", "isabela@fashionista.com",
					"Blog de moda y estilo personal", "www.fashionista.com", LocalDate.of(1994, 11, 2));
			IUC.registrarProveedor("petParadise", "Fernando", "Chávez", "fernando@petparadise.com",
					"Productos y servicios para mascotas", "www.petparadise.com", LocalDate.of(2001, 7, 22));
			IUC.registrarProveedor("organicEats", "Sofía", "Ortega", "sofia@organiceats.com",
					"Restaurante con enfoque en alimentos orgánicos", "www.organiceats.com", LocalDate.of(1999, 3, 7));
			IUC.registrarProveedor("fitnessFusion", "Alejandro", "Luna", "alejandro@fitnessfusion.com",
					"Gimnasio y centro de acondicionamiento físico", "www.fitnessfusion.com",
					LocalDate.of(1992, 6, 28));
			IUC.registrarProveedor("travelDreams", "Valeria", "Ramírez", "valeria@traveldreams.com",
					"Agencia de viajes para destinos exóticos", "www.traveldreams.com", LocalDate.of(1987, 1, 11));
			IUC.registrarProveedor("artGallery", "Gustavo", "Díaz", "gustavo@artgallery.com",
					"Galería de arte contemporáneo", "www.artgallery.com", LocalDate.of(1984, 9, 9));
			IUC.registrarProveedor("homeSolutions", "Mariana", "Perez", "mariana@homesolutions.com",
					"Soluciones para el hogar y decoración", "www.homesolutions.com", LocalDate.of(2004, 12, 3));
			IUC.registrarProveedor("techGadgets", "Manuel", "Ríos", "manuel@techgadgets.com",
					"Dispositivos tecnológicos y accesorios", "www.techgadgets.com", LocalDate.of(1996, 5, 15));
			IUC.registrarProveedor("urbanExplore", "Renata", "Martínez", "renata@urbanexplore.com",
					"Explora la ciudad con nuestras rutas turísticas", "www.urbanexplore.com",
					LocalDate.of(1992, 2, 24));
			IUC.registrarProveedor("designCraft", "Lucas", "Torres", "lucas@designcraft.com",
					"Artesanía y diseño en productos únicos", "www.designcraft.com", LocalDate.of(1989, 8, 2));
			IUC.registrarProveedor("musicMania", "María", "Ruiz", "maria@musicmania.com",
					"Tienda de instrumentos musicales y accesorios", "www.musicmania.com", LocalDate.of(1997, 6, 6));
			IUC.registrarProveedor("petCompanion", "Camila", "García", "camila@petcompanion.com",
					"Cuidado y servicios para mascotas", "www.petcompanion.com", LocalDate.of(2003, 10, 20));
			IUC.registrarProveedor("ecoLiving", "Mateo", "Morales", "mateo@ecoliving.com",
					"Productos ecológicos y estilo de vida sostenible", "www.ecoliving.com", LocalDate.of(1998, 4, 14));
			IUC.registrarProveedor("fashionSpot", "Valentina", "Vega", "valentina@fashionspot.com",
					"Plataforma para compartir tendencias de moda", "www.fashionspot.com", LocalDate.of(1995, 9, 28));
			IUC.registrarProveedor("deliciousBakery", "Javier", "López", "javier@deliciousbakery.com",
					"Panadería y repostería con productos artesanales", "www.deliciousbakery.com",
					LocalDate.of(2000, 1, 3));

			IDC.crearDepartamento("Artigas", "Departamento del norte de Uruguay", "www.artigas.com.uy");
			IDC.crearDepartamento("Canelones", "Departamento ubicado en el sur de Uruguay", "www.canelones.com.uy");
			IDC.crearDepartamento("Cerro Largo", "Departamento en el este de Uruguay", "www.cerrolargo.com.uy");
			IDC.crearDepartamento("Colonia", "Departamento costero de Uruguay", "www.colonia.com.uy");
			IDC.crearDepartamento("Durazno", "Departamento en el centro de Uruguay", "www.durazno.com.uy");
			IDC.crearDepartamento("Flores", "Departamento en el suroeste de Uruguay", "www.flores.com.uy");
			IDC.crearDepartamento("Florida", "Departamento en el sur de Uruguay", "www.florida.com.uy");
			IDC.crearDepartamento("Lavalleja", "Departamento en el sureste de Uruguay", "www.lavalleja.com.uy");
			IDC.crearDepartamento("Maldonado", "Departamento en la costa este de Uruguay", "www.maldonado.com.uy");
			IDC.crearDepartamento("Montevideo", "La capital de Uruguay", "www.montevideo.com.uy");
			IDC.crearDepartamento("Paysandú", "Departamento en el noroeste de Uruguay", "www.paysandu.com.uy");
			IDC.crearDepartamento("Río Negro", "Departamento en el oeste de Uruguay", "www.rionegro.com.uy");
			IDC.crearDepartamento("Rivera", "Departamento en el norte de Uruguay", "www.rivera.com.uy");
			IDC.crearDepartamento("Rocha", "Departamento en el este de Uruguay", "www.rocha.com.uy");
			IDC.crearDepartamento("Salto", "Departamento en el noroeste de Uruguay", "www.salto.com.uy");
			IDC.crearDepartamento("San José", "Departamento en el suroeste de Uruguay", "www.sanjose.com.uy");
			IDC.crearDepartamento("Soriano", "Departamento en el suroeste de Uruguay", "www.soriano.com.uy");
			IDC.crearDepartamento("Tacuarembó", "Departamento en el norte de Uruguay", "www.tacuarembo.com.uy");
			IDC.crearDepartamento("Treinta y Tres", "Departamento en el este de Uruguay", "www.treintaytres.com.uy");

		} catch (UsuarioYaExisteExcepcion a) {
			a.printStackTrace();
			throw new precargaExcepcion("Ocurrió un error personalizado");
		}
	}
}