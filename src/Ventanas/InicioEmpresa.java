package Ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Conexion.Conexion;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class InicioEmpresa extends JFrame {
	static InicioEmpresa frame;
	private JPanel contentPane;
	static JTextField txtUsuario;
	private JLabel lblUsuario;
	private JLabel lblContrasea;
	private JLabel lblNearEat;
	static JPasswordField passwordField;
	private JButton btnInicio;
	private static int contInicio = 0;
	private JButton btnVolver;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new InicioEmpresa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame for empresa
	 */
	public InicioEmpresa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(137, 132, 180, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(55, 135, 72, 14);
		contentPane.add(lblUsuario);

		lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(55, 166, 89, 14);
		contentPane.add(lblContrasea);

		lblNearEat = new JLabel("Near Eat");
		lblNearEat.setFont(new Font("Comic Sans MS", Font.BOLD, 33));
		lblNearEat.setBounds(137, 11, 201, 56);
		contentPane.add(lblNearEat);

		passwordField = new JPasswordField();
		passwordField.setBounds(137, 163, 180, 20);
		contentPane.add(passwordField);

		JLabel lblNoTienesCuenta = new JLabel("No tienes cuenta?");
		lblNoTienesCuenta.setBounds(10, 236, 129, 14);
		contentPane.add(lblNoTienesCuenta);

		JButton btnRegistrate = new JButton("Registrate");
		btnRegistrate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistroEmpresa regEmp = new RegistroEmpresa();
				regEmp.main(null);
				frame.setVisible(false);
			}
		});
		btnRegistrate.setBounds(137, 232, 123, 23);
		contentPane.add(btnRegistrate);

		btnInicio = new JButton("Inicio");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (sesionEmpresa()) {
						MenuBusqueda inicio = new MenuBusqueda();
						inicio.main(null);
						frame.setVisible(false);
					}
					;
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnInicio.setBounds(185, 186, 89, 23);
		contentPane.add(btnInicio);

		JLabel lblEmpresa = new JLabel("Empresa");
		lblEmpresa.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		lblEmpresa.setBounds(178, 67, 108, 37);
		contentPane.add(lblEmpresa);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				InicioUsuario.main(null);
			}
		});
		btnVolver.setBounds(10, 11, 89, 23);
		contentPane.add(btnVolver);
	}

	/**
	 * Comprueba usuario y contraseña de la empresa
	 * 
	 * @return True: si usuario y contraseña son correctos; False: si usuario o
	 *         contraseña son incorrectos
	 * @throws SQLException
	 */

	public static boolean sesionEmpresa() throws SQLException {
		Conexion conexion = new Conexion();
		Connection cn = conexion.conectar();
		Statement stm = cn.createStatement();
		ResultSet rs = null;

		String correo = "'" + txtUsuario.getText() + "'";
		rs = stm.executeQuery("Select * from empresa where USR=" + correo);
		if (!rs.next()) {
			JOptionPane.showMessageDialog(null, "Correo Invalido");
			contInicio++;
			System.out.println(contInicio);
			if (contInicio >= 3) {
				frame.setVisible(false);
			}
			return false;
		} else {
			String pass = passwordField.getText();
			String contr = rs.getString(3);
			if (pass.equals(contr)) {
				JOptionPane.showMessageDialog(null, "Bienvenido " + rs.getString(5) + "\n\n\n");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
				contInicio++;
				System.out.println(contInicio);
				if (contInicio >= 3) {
					frame.setVisible(false);
				}
			}
			return false;
		}

	}
}
