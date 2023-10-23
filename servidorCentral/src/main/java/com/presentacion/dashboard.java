package com.presentacion;

import com.sc.entidades.departamento;
import java.awt.EventQueue;
// import com.sc.entidades.persona;
import java.util.List;


import java.awt.BorderLayout;
// import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
// import java.awt.CardLayout;
// import java.awt.FlowLayout;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
// import javax.swing.BoxLayout;
import java.awt.GridLayout;
// import java.awt.GridBagLayout;
// import java.awt.GridBagConstraints;
// import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Cursor;
// import javax.swing.UIManager;
// import java.awt.Rectangle;
import java.awt.ComponentOrientation;
// import javax.swing.ImageIcon;
// import java.awt.Component;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class dashboard extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dashboard frame = new dashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public dashboard(){

		System.out.println("Conectando a la BD...");

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PA2023");
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		// departamento depto2 = new departamento("Ventas2");
		
		try {
            tx.begin();
            // Realiza operaciones con la entidad

			// em.persist(depto2);
			// em.persist(depto3);

			Query query = em.createQuery("SELECT nombre FROM departamento d");
			@SuppressWarnings("unchecked")
			List <departamento> result = query.getResultList();

			System.out.println(result.size());

			for (int i = 0; i < result.size(); i++) {
				departamento e = (departamento) result.get(i);
				System.out.println(e.getNombre());
			}
			
			// for (departamento e : result) {
			// 	System.out.println("x");
			// }
			// for (departamento elemento: result) {
	
			// 	System.out.println(elemento.getNombre());
			// }


            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
		factory.close();

		setTitle("TurismoUY");
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelPricipal = new JPanel();
		panelPricipal.setEnabled(false);
		panelPricipal.setBackground(new Color(204, 208, 207));
		panelPricipal.setBounds(0, 0, 984, 661);
		contentPane.add(panelPricipal);
		panelPricipal.setLayout(null);
		
		JPanel menu = new JPanel();
		menu.setBorder(null);
		menu.setBackground(new Color(6, 20, 27));
		menu.setBounds(0, 0, 213, 661);
		panelPricipal.add(menu);
		menu.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel title = new JLabel("TURISMO UY");
		title.setVerticalTextPosition(SwingConstants.TOP);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(new Color(255, 255, 255));
		title.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
		menu.add(title);
		
		JButton inicio = new JButton("Inicio");
		
		inicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		inicio.setBackground(new Color(17, 33, 45));
		inicio.setForeground(new Color(255, 255, 255));
		inicio.setFont(new Font("Segoe UI", Font.BOLD, 16));
		inicio.setFocusPainted(false);
		inicio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		inicio.setBorder(null);
		menu.add(inicio);
		
		JButton usuarios = new JButton("Usuarios");

		usuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		usuarios.setForeground(new Color(255, 255, 255));
		usuarios.setFont(new Font("Segoe UI", Font.BOLD, 16));
		usuarios.setFocusPainted(false);
		usuarios.setBorderPainted(false);
		usuarios.setBackground(new Color(17, 33, 45));
		menu.add(usuarios);
		
		JButton salidas = new JButton("Salidas");
		
		salidas.setBackground(new Color(17, 33, 45));
		salidas.setFont(new Font("Segoe UI", Font.BOLD, 16));
		salidas.setForeground(new Color(255, 255, 255));
		salidas.setFocusPainted(false);
		salidas.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		salidas.setBorder(null);
		menu.add(salidas);
		
		JButton actividades = new JButton("Actividades");
		actividades.setBorder(null);
		actividades.setBackground(new Color(17, 33, 45));
		actividades.setForeground(new Color(255, 255, 255));
		actividades.setFont(new Font("Segoe UI", Font.BOLD, 16));
		actividades.setFocusPainted(false);
		actividades.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		menu.add(actividades);
		
		JButton paquetes = new JButton("Paquetes");
		paquetes.setForeground(new Color(255, 255, 255));
		paquetes.setFont(new Font("Segoe UI", Font.BOLD, 16));
		paquetes.setFocusPainted(false);
		paquetes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		paquetes.setBorderPainted(false);
		paquetes.setBorder(null);
		paquetes.setBackground(new Color(17, 33, 45));
		menu.add(paquetes);
		
		JButton departamentos = new JButton("Departamentos");
		departamentos.setBorderPainted(false);
		departamentos.setBorder(null);
		departamentos.setBackground(new Color(17, 33, 45));
		departamentos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		departamentos.setForeground(new Color(255, 255, 255));
		departamentos.setFont(new Font("Segoe UI", Font.BOLD, 16));
		departamentos.setFocusPainted(false);
		menu.add(departamentos);
		
		final JPanel content = new JPanel();
		content.setBounds(212, 94, 772, 567);
		panelPricipal.add(content);

		content.setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBackground(new Color(6, 20, 27));
		panel.setBounds(212, 0, 772, 94);
		panelPricipal.add(panel);
		panel.setLayout(new GridLayout(1, 1, 0, 0));

		JLabel screenTitle = new JLabel("Sistema de gestion - Administrador");
		screenTitle.setHorizontalAlignment(SwingConstants.CENTER);
		screenTitle.setForeground(new Color(255, 255, 255));
		screenTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
		panel.add(screenTitle);
		
		
	}
	
}
