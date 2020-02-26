package Pruebas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


import Conexion.Conexion;

public class TestConexion {

	public static int opcion;

	public static void main(String[] args) throws SQLException {
		boolean sesion = false;
		String usuario = "";
		while (!sesion) {
			switch (menuInicial()) {
			case 1:
				
				sesion = true;
				break;
			case 2:
				
				sesion = true;
				break;
			case 3:
				crearUsuario();
				break;
			case 4:
				crearEmpresa();
				break;
			default:
				System.out.println("Error");
			}
		}

	}

	public static String getQuery() {
		String query = "";

		return query;
	}

	public static int menuInicial() {
		Scanner in = new Scanner(System.in);
		System.out.println("*** NEAR EAT ***");
		System.out.println("1) Inicio sesion como usuario.");
		System.out.println("2) Inicio sesion como empresa.");
		System.out.println("3) Crear cuenta de usuario");
		System.out.println("4) Crear cuenta como empresa");
		System.out.println("5) Salir");
		System.out.println("Seleccione una opcion");
		return opcion = in.nextInt();
	}




	

	public static void crearUsuario() throws SQLException {
		Scanner in = new Scanner(System.in);
		Conexion conexion = new Conexion();
		Connection cn = conexion.conectar();
		Statement stm = cn.createStatement();
		ResultSet rs = null;
		boolean creado = false;
		while (!creado) {
			System.out.println("Introduzca un correo para crear la cuenta: ");
			String correo = in.next();
			rs = stm.executeQuery("Select * from usuario where USR='" + correo + "'");
			if (!rs.next()) {
				System.out.println("Introduzca una contraseña de 8 a 20 caracteres: ");
				String pass = in.next();
				if (pass.length() < 21 && pass.length() > 7) {
					in.nextLine();
					System.out.println("Introduzca su nombre completo: ");
					String nombre = in.nextLine();
					System.out.println("Introduzca su fecha de nacimiento (aaaa-mm-dd):");
					String fecha_nac = in.next();
					System.out.println("Introduzca su numero de telefono (solo numeros):");
					long tlfno = in.nextLong();
					System.out.println("Introduzca su sexo (1.Hombre - 2.Mujer): ");
					int sexo = in.nextInt() - 1;
					try {
						String query = "Insert into usuario (USR, PWD, Nombre, fecha_nac, num_tfno, sexo) values('"
								+ correo + "','" + pass + "','" + nombre + "','" + fecha_nac + "'," + tlfno + "," + sexo
								+ ")";
						stm.executeUpdate(query);
						System.out.println("Cuenta creada con exito. \n\n\n");
					} catch (SQLException e) {
						System.out.println("Error al crear");
					}
					creado = true;
				} else
					System.out.println("Contraseña con formato no permitido");
			} else
				System.out.println("Correo ya registrado.");
		}
	}

	public static void crearEmpresa() throws SQLException {
		Scanner in = new Scanner(System.in);
		Conexion conexion = new Conexion();
		Connection cn = conexion.conectar();
		Statement stm = cn.createStatement();
		ResultSet rs = null;
		boolean creado = false;
		while (!creado) {
			System.out.println("Introduzca un correo para crear la cuenta de empresa: ");
			String correo = in.next();
			rs = stm.executeQuery("Select * from empresa where USR='" + correo + "'");
			if (!rs.next()) {
				System.out.println("Introduzca una contraseña de 8 a 20 caracteres: ");
				String pass = in.next();
				if (pass.length() < 21 && pass.length() > 7) {
					in.nextLine();
					System.out.println("Introduzca su CIF de empresa: ");
					String cif = in.next();
					in.nextLine();
					System.out.println("Introduzca su nombre de empresa:");
					String nom_emp = in.nextLine();
					System.out.println("Introduzca su numero de telefono (solo numeros):");
					long tlfno = in.nextLong();
					in.nextLine();
					System.out.println("Introduzca su nombre completo: ");
					String nombre = in.nextLine();
					try {
						String query = "Insert into empresa (USR, PWD, CIF, Nombre_empresa, tlfno, nom_titular) values('"
								+ correo + "','" + pass + "','" + cif + "','" + nom_emp + "'," + tlfno + ",'" + nombre
								+ "')";
						stm.executeUpdate(query);
						System.out.println("Cuenta creada con exito. \n\n\n");
					} catch (SQLException e) {
						System.out.println("Error al crear");
					}
					creado = true;
				} else
					System.out.println("Contraseña con formato no permitido");
			} else
				System.out.println("Correo ya registrado.");
		}
	}
}