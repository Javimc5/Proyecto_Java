package Ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Conexion.Conexion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class RegistroEmpresa extends JFrame {

	private JPanel contentPane;
	private static JTextField textField;
	private static JTextField textField_2;
	private static JTextField textField_3;
	private static JTextField textField_4;
	private static JPasswordField passwordField;
	private static JTextField textField_1;
	static RegistroEmpresa frame;

	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new RegistroEmpresa();
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
	public RegistroEmpresa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNearEat = new JLabel("Near Eat");
		lblNearEat.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblNearEat.setBounds(145, 0, 168, 28);
		contentPane.add(lblNearEat);

		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setBounds(10, 59, 74, 14);
		contentPane.add(lblCorreo);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(10, 84, 97, 14);
		contentPane.add(lblContrasea);

		JLabel lblNombreEmpresa = new JLabel("Nombre Empresa:");
		lblNombreEmpresa.setBounds(10, 143, 132, 14);
		contentPane.add(lblNombreEmpresa);

		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(10, 168, 74, 14);
		contentPane.add(lblTelefono);

		textField = new JTextField();
		textField.setBounds(69, 56, 282, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(52, 112, 225, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(123, 140, 227, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(94, 168, 168, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);

		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (crearEmpresa()) {
						InicioEmpresa.main(null);
						frame.dispose();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCrear.setBounds(315, 227, 89, 23);
		contentPane.add(btnCrear);

		passwordField = new JPasswordField();
		passwordField.setBounds(95, 81, 254, 23);
		contentPane.add(passwordField);

		JLabel lblEmpresa = new JLabel("Empresa");
		lblEmpresa.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblEmpresa.setBounds(161, 26, 101, 23);
		contentPane.add(lblEmpresa);

		JLabel lblCif = new JLabel("CIF:");
		lblCif.setBounds(10, 109, 57, 20);
		contentPane.add(lblCif);

		JLabel lblNombreCompleto = new JLabel("Nombre Completo:");
		lblNombreCompleto.setBounds(10, 193, 119, 20);
		contentPane.add(lblNombreCompleto);

		textField_1 = new JTextField();
		textField_1.setBounds(133, 193, 191, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				InicioEmpresa.main(null);
			}
		});
		btnVolver.setBounds(10, 7, 89, 23);
		contentPane.add(btnVolver);
	}

	public static boolean crearEmpresa() throws SQLException {
		Conexion conexion = new Conexion();
		Connection cn = conexion.conectar();
		Statement stm = cn.createStatement();
		ResultSet rs = null;

		String correo = textField.getText();
		rs = stm.executeQuery("Select * from empresa where correo='" + correo + "'");
		if (!rs.next()) {
			String pass = passwordField.getText();
			if (pass.length() < 21 && pass.length() > 7) {
				String nombre_empresa = textField_3.getText();
				String CIF = textField_2.getText();
				String nom_titular = textField_1.getText();
				long tlfno = Integer.parseInt(textField_4.getText());
				try {
					String query = "Insert into empresa (correo, password, CIF, nombre_empresa, tlfno, nom_titular) values('"
							+ correo + "','" + pass + "','" + CIF + "','" + nombre_empresa + "'," + tlfno + ",'"
							+ nom_titular + "')";
					stm.executeUpdate(query);
					JOptionPane.showMessageDialog(null, "Cuenta de empresa creada con exito.");
					return true;
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error al crear la cuenta de empresa.");
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Contraseña con formato no permitido. Por favor introduzca una contraseña entre 7 y 21 caracteres.");
				return false;
			}

		} else {
			JOptionPane.showMessageDialog(null, "Correo ya registrado.");
			return false;
		}
	}
}
