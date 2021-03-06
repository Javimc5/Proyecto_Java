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
	private static ModificarUsuario frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ModificarUsuario();
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		lblNombre.setBounds(67, 62, 144, 24);
		contentPane.add(lblNombre);

		lblPassword = new JLabel("");
		lblPassword.setBounds(88, 97, 143, 23);
		contentPane.add(lblPassword);

		lblTfno = new JLabel("");
		lblTfno.setBounds(67, 143, 153, 24);
		contentPane.add(lblTfno);

		lblFecha = new JLabel("");
		lblFecha.setBounds(116, 178, 153, 24);
		contentPane.add(lblFecha);
		
		lblNombre_1 = new JLabel("Nombre:");
		lblNombre_1.setBounds(10, 62, 74, 24);
		contentPane.add(lblNombre_1);
		
		lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(10, 97, 74, 24);
		contentPane.add(lblContrasea);
		
		lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
		lblFechaNacimiento.setBounds(10, 178, 116, 24);
		contentPane.add(lblFechaNacimiento);
		
		lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(10, 142, 74, 24);
		contentPane.add(lblTelefono);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(290, 62, 153, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setColumns(10);
		textField_1.setBounds(290, 94, 153, 24);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setColumns(10);
		textField_2.setBounds(290, 143, 153, 24);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setColumns(10);
		textField_3.setBounds(290, 178, 153, 24);
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
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					eliminar();
					frame.setVisible(false);
					MenuBusqueda.frame.setVisible(false);
					InicioUsuario.main(null);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEliminar.setBounds(188, 220, 114, 33);
		contentPane.add(btnEliminar);
	}
	
	/**
	 * Metodo que permite la eliminacion del usuario<br> 
	 * que se hallaba con la sesion iniciada <br>
	 * 
	 * @throws SQLException
	 */
	public void eliminar() throws SQLException {
		Conexion conexion = new Conexion();
		Connection cn = conexion.conectar();
		Statement stm = cn.createStatement();
		//Delete en users del usuariop con el que iniciaste sesion para borrar el usuario
		String query="delete from users where USR="+InicioUsuario.correo;
		stm.executeUpdate(query);
		
	}
	
	
	/**
	 * Metodo que permite la modificación de distintos campos de tu usuario <br> 
	 * Entre ellos permite la modificacion de: <br>
	 * -nombre <br>
	 * -Contraseña <br>
	 * -Telefono <br>
	 * -fecha de nacimiento
	 * 
	 * @throws SQLException
	 */
	
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
		
		//Update en la tabla users para actualizar los datos que desees modificar
		String query="update users set PWD='"+password+"', Nombre='"+nombre+"', fecha_nac='"+fecha+"', num_tfno="+tfno+" where USR="+InicioUsuario.correo;
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
	
	
	/**
	 * Metodo que sirve para comprobar los campos asocidos a tu usuario <br>
	 * e imprimirlos en JLable
	 * .Nombre <br>
	 * .Contraseña <br>
	 * .Telefono <br>
	 * .Fecha de nacimiento
	 * @throws SQLException
	 */
	public void rellenarUsuario() throws SQLException {
		Conexion conexion = new Conexion();
		Connection cn = conexion.conectar();
		Statement stm = cn.createStatement();
		ResultSet rs = null;
		rs = stm.executeQuery("Select * from users where USR=" + InicioUsuario.correo);
		while (rs.next()) {
			lblNombre.setText(rs.getString(4));
			lblPassword.setText(rs.getString(3));
			lblTfno.setText(rs.getInt(6) + "");
			lblFecha.setText(rs.getDate(5) + "");
		}
	}
}
