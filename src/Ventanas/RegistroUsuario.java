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
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class RegistroUsuario extends JFrame {

	protected static RegistroUsuario frame;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static JTextField textField;
	private static JTextField textField_2;
	private static JTextField textField_3;
	private static JTextField textField_4;
	private static JPasswordField passwordField;
	private static JRadioButton rdbtnHombre;
	private static JRadioButton rdbtnMujer;
	private JButton btnVolver;

	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new RegistroUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea el frame de registro de usuario.
	 */
	public RegistroUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNearEat = new JLabel("Near Eat");
		lblNearEat.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblNearEat.setBounds(150, 11, 168, 40);
		contentPane.add(lblNearEat);

		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setBounds(10, 59, 74, 14);
		contentPane.add(lblCorreo);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(10, 84, 97, 14);
		contentPane.add(lblContrasea);

		JLabel lblNombreCompleto = new JLabel("Nombre Completo:");
		lblNombreCompleto.setBounds(10, 112, 132, 14);
		contentPane.add(lblNombreCompleto);

		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento (aaaa-mm-dd):");
		lblFechaNacimiento.setBounds(10, 143, 201, 14);
		contentPane.add(lblFechaNacimiento);

		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(10, 168, 74, 14);
		contentPane.add(lblTelefono);

		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setBounds(10, 210, 37, 14);
		contentPane.add(lblSexo);

		rdbtnHombre = new JRadioButton("Hombre");
		buttonGroup.add(rdbtnHombre);
		rdbtnHombre.setBounds(41, 206, 65, 23);
		contentPane.add(rdbtnHombre);

		rdbtnMujer = new JRadioButton("Mujer");
		buttonGroup.add(rdbtnMujer);
		rdbtnMujer.setBounds(108, 206, 60, 23);
		contentPane.add(rdbtnMujer);

		textField = new JTextField();
		textField.setBounds(69, 56, 282, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(141, 112, 207, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(206, 140, 144, 20);
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
					if (crearUsuario()) {
						InicioUsuario.main(null);
						frame.setVisible(false);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCrear.setBounds(280, 206, 89, 23);
		contentPane.add(btnCrear);

		passwordField = new JPasswordField();
		passwordField.setBounds(99, 80, 252, 23);
		contentPane.add(passwordField);

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
	 * Metodo para crear usuario mediante un insert a la base de datos. <br>
	 * Comprueba que no existe un usuario con el correo(USR) introducido y procede a la creacion del usuario.
	 * 
	 * @return true: Si crea la cuenta. <br>False: Si no consigue crear la cuenta.
	 * @throws SQLException
	 */

	public static boolean crearUsuario() throws SQLException {
		Conexion conexion = new Conexion();
		Connection cn = conexion.conectar();
		Statement stm = cn.createStatement();
		ResultSet rs = null;

		String correo = textField.getText();
		rs = stm.executeQuery("Select * from users where USR='" + correo + "'");
		if (!rs.next()) {
			String pass = passwordField.getText();
			if (pass.length() < 21 && pass.length() > 7) {
				String nombre = textField_2.getText();
				String fecha_nac = textField_3.getText();
				long tlfno = Integer.parseInt(textField_4.getText());
				int sexo = 0;
				if (rdbtnHombre.isSelected())
					sexo = 0;
				if (rdbtnMujer.isSelected())
					sexo = 1;
				try {
					String query = "Insert into users (USR, PWD, Nombre, fecha_nac, num_tfno, sexo) values('"
							+ correo + "','" + pass + "','" + nombre + "','" + fecha_nac + "'," + tlfno + "," + sexo
							+ ")";
					stm.executeUpdate(query);
					JOptionPane.showMessageDialog(null, "Cuenta creada con exito.");
					return true;
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error al crear cuenta.");
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Contraseña con formato no permitido. Introduzca una contraseña entre 7 y 21 caracteres.");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Correo ya registrado.");
			return false;
		}

	}
}
