package com.digitro.edittext.conectBD;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConectaPostgres {

	public static Connection conectaPostgres() {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/editor";
			String usuario = "postgres";
			String senha = "Ana2017ana";
			con = DriverManager.getConnection(url,usuario,senha);
		} catch (Exception e) {
			System.err.println("ERRO ao conectar o banco \n" + e);
			e.printStackTrace();
		}
		return con;
	}
	
	public static void desconectaPostgres(Connection con) {
		
		try {
			con.close();
			
		} catch (Exception e) {
			System.err.println("ERRO ao desconectar o banco \n" + e);
			e.printStackTrace();
		}
		
	}
}
