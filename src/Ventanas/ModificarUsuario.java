package Ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Conexion.Conexion;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarUsuario extends JFrame {

	private JPanel contentPane;
	private JLabel lblNombre;
	private JLabel lblPassword;
	private JLabel lblTfno;
	private JLabel lblFecha;
	private JLabel lblNombre_1;
	private JLabel lblContrasea;
	private JLabel lblFechaNacimiento;
	private JLabel lblTelefono;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificarUsuario frame = new ModificarUsuario();
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
	public ModificarUsuario() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					rellenarUsuario();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 512, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblUsuario.setBounds(10, 11, 153, 40);
		contentPane.add(lblUsuario);

		lblNombre = new JLabel("");
		lblNombre.setBounds(78, 62, 153, 24);
		contentPane.add(lblNombre);

		lblPassword = new JLabel("");
		lblPassword.setBounds(88, 97, 153, 23);
		contentPane.add(lblPassword);

		lblTfno = new JLabel("");
		lblTfno.setBounds(78, 143, 153, 24);
		contentPane.add(lblTfno);

		lblFecha = new JLabel("");
		lblFecha.setBounds(124, 178, 153, 24);
		contentPane.add(lblFecha);
		
		lblNombre_1 = new JLabel("Nombre:");
		lblNombre_1.setBounds(10, 69, 74, 14);
		contentPane.add(lblNombre_1);
		
		lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(10, 103, 74, 14);
		contentPane.add(lblContrasea);
		
		lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
		lblFechaNacimiento.setBounds(10, 188, 116, 14);
		contentPane.add(lblFechaNacimiento);
		
		lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(10, 143, 74, 14);
		contentPane.add(lblTelefono);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(290, 62, 153, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setColumns(10);
		textField_1.setBounds(290, 100, 153, 24);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setColumns(10);
		textField_2.setBounds(290, 143, 153, 24);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setColumns(10);
		textField_3.setBounds(290, 185, 153, 24);
		contentPane.add(textField_3);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField.isEnabled()) {
					textField.setEnabled(true);
					textField_1.setEnabled(true);
					textField_2.setEnabled(true);
					textField_3.setEnabled(true);
				}
				else {
					try {
						modificar();
						rellenarUsuario();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnModificar.setBounds(312, 220, 116, 33);
		contentPane.add(btnModificar);
	}
	
	public void modificar() throws SQLException {
		Conexion conexion = new Conexion();
		Connection cn = conexion.conectar();
		Statement stm = cn.createStatement();
		String nombre;
		String password;
		long tfno;
		String fecha;
		if(textField.getText().equals("")) nombre=lblNombre.getText();
		else nombre=textField.getText();
		
		if(textField_1.getText().equals("")) password=lblPassword.getText();
		else password=textField_1.getText();
		
		if(textField_2.getText().equals("")) tfno=Integer.parseInt(lblTfno.getText());
		else tfno=Integer.parseInt(textField_2.getText());
		
		if(textField_3.getText().equals("")) fecha=lblFecha.getText();
		else fecha=textField_3.getText();
		
		String query="update users set password='"+password+"', Nombre='"+nombre+"', fecha_nac='"+fecha+"', num_tfno="+tfno+" where correo="+InicioUsuario.correo;
		stm.executeUpdate(query);
		textField.setText("");
		textField.setEnabled(false);
		textField_1.setText("");
		textField_1.setEnabled(false);
		textField_2.setText("");
		textField_2.setEnabled(false);
		textField_3.setText("");
		textField_3.setEnabled(false);
	}
	
	public void rellenarUsuario() throws SQLException {
		Conexion conexion = new Conexion();
		Connection cn = conexion.conectar();
		Statement stm = cn.createStatement();
		ResultSet rs = null;
		rs = stm.executeQuery("Select * from users where correo=" + InicioUsuario.correo);
		while (rs.next()) {
			lblNombre.setText(rs.getString(4));
			lblPassword.setText(rs.getString(3));
			lblTfno.setText(rs.getInt(6) + "");
			lblFecha.setText(rs.getDate(5) + "");
		}
	}
}
