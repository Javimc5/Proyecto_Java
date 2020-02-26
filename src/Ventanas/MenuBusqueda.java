package Ventanas;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.Color;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Conexion.Conexion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuBusqueda extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	static MenuBusqueda frame;

	/**
	 * Launch the application.
	 */
	public void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MenuBusqueda();
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
	public MenuBusqueda() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					modificarTabla("Select * from restaurante");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 852, 492);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNearEat = new JLabel("Near Eat");
		lblNearEat.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
		lblNearEat.setBounds(10, 11, 158, 43);
		contentPane.add(lblNearEat);
		
		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setBounds(10, 65, 99, 23);
		contentPane.add(lblBuscar);
		
		textField = new JTextField();
		textField.setBounds(51, 66, 256, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField.getText().equals(""))
						modificarTabla("Select * from restaurante");
					else {
						String query="Select * from restaurante where Nombre like '%"+textField.getText()+"%'";
						modificarTabla(query);
					}
				}catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(317, 65, 89, 23);
		contentPane.add(btnBuscar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 99, 816, 332);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Direccion", "Ciudad", "Telefono", "Descripcion", "Valoracion"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(138);
		table.getColumnModel().getColumn(4).setPreferredWidth(186);
		scrollPane.setViewportView(table);
		
		JButton btnModificarUsuario = new JButton("Modificar Usuario");
		btnModificarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModificarUsuario.main(null);
			}
		});
		btnModificarUsuario.setBounds(650, 11, 176, 32);
		contentPane.add(btnModificarUsuario);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesion");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				InicioUsuario.main(null);
			}
		});
		btnCerrarSesion.setBounds(650, 54, 176, 32);
		contentPane.add(btnCerrarSesion);
	}
	
	public void modificarTabla(String query) throws SQLException {
		Conexion conexion = new Conexion();
		Connection cn = conexion.conectar();
		Statement stm = cn.createStatement();
		ResultSet rs = stm.executeQuery(query);
		
		DefaultTableModel model=new DefaultTableModel(new Object[][] {
		},
		new String[] {
			"Nombre", "Direccion", "Ciudad", "Telefono", "Descripcion", "Valoracion"
		});
		 while(rs.next()) {
			 model.addRow(new Object[] {rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(7), rs.getInt(8)});
		 }
		 table.setModel(model);
	}
}
