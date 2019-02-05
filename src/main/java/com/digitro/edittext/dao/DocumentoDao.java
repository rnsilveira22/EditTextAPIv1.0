package com.digitro.edittext.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.digitro.edittext.conectBD.ConectaPostgres;
import com.digitro.edittext.model.Documento;

public class DocumentoDao {

	private Connection con = null;
	private PreparedStatement pstmt = null;

	public Documento salva(Documento documento) {

		ResultSet rs = null;
		try {

			con = ConectaPostgres.conectaPostgres();
			pstmt = con.prepareStatement("INSERT INTO documento(titulo,corpo) VALUES(?,?);",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, documento.getTitulo());
			pstmt.setString(2, documento.getCorpo());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();

			while (rs.next()) {
				documento.setId(rs.getLong("id"));
				documento.setData(rs.getDate("data").toString());

			}

			pstmt.close();
			return documento;

		} catch (Exception e) {
			System.err.println("ERRO: ao adicionar novo documento \n" + e);
			e.printStackTrace();
		} finally {
			ConectaPostgres.desconectaPostgres(con);

		}
		return null;
	}

	public ArrayList<Documento> listar() {
		con = ConectaPostgres.conectaPostgres();
		ArrayList<Documento> lista = new ArrayList<Documento>();
		ResultSet rs = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {
			pstmt = con.prepareStatement("SELECT * FROM documento ORDER BY id;");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Documento documento = new Documento();
				documento.setId(rs.getLong("id"));
				documento.setTitulo(rs.getString("titulo"));
				documento.setCorpo(rs.getString("corpo"));
				String dataString = dateFormat.format(rs.getTimestamp("Data"));
				documento.setData(dataString);
				lista.add(documento);

			}
			pstmt.close();

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		} finally {
			ConectaPostgres.desconectaPostgres(con);

		}
		return lista;
	}

	public ArrayList<Documento> listar(String titulo, String corpo) {
		ArrayList<Documento> lista = new ArrayList<Documento>();
		ResultSet rs = null;
		con = ConectaPostgres.conectaPostgres();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			System.out.println("entrou no try");
			String script = "Select * FROM documento WHERE titulo LIKE '%"+titulo+"%'AND corpo LIKE '%"+corpo+"%';";
			pstmt = con.prepareStatement(script);
			Documento documento = new Documento();
			rs = pstmt.executeQuery();
			while (rs.next()) {

				documento.setId(rs.getLong("id"));
				documento.setTitulo(rs.getString("titulo"));
				documento.setCorpo(rs.getString("corpo"));
				String dataString = dateFormat.format(rs.getTimestamp("Data"));
				documento.setData(dataString);
				lista.add(documento);
			}
			return lista;

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		} finally {
			ConectaPostgres.desconectaPostgres(con);

		}
		return null;
	}

	public Documento listar(Long id) {
		ResultSet rs = null;
		con = ConectaPostgres.conectaPostgres();
		Documento documento = new Documento();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			pstmt = con.prepareStatement("SELECT * FROM documento WHERE id=?;");
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				documento.setId(rs.getLong("id"));
				documento.setTitulo(rs.getString("titulo"));
				documento.setCorpo(rs.getString("corpo"));
				String dataString = dateFormat.format(rs.getTimestamp("Data"));
				documento.setData(dataString);
			}
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();

		}
		return documento;
	}

	public Documento atualiza(Documento documento) {

		ResultSet rs = null;
		con = ConectaPostgres.conectaPostgres();

		try {

			pstmt = con.prepareStatement(
					"UPDATE documento SET titulo= ?,corpo= ? WHERE id= ? RETURNING id,titulo,corpo,data ;");
			pstmt.setString(1, documento.getTitulo());
			pstmt.setString(2, documento.getCorpo());
			pstmt.setLong(3, documento.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				documento.setId(rs.getLong("id"));
				documento.setTitulo(rs.getString("titulo"));
				documento.setCorpo(rs.getString("corpo"));
				documento.setData(rs.getString("data").toString());
			}

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}

		return documento;
	}

	public void excluir(Long id) {
		con = ConectaPostgres.conectaPostgres();

		try {
			pstmt = con.prepareStatement("DELETE FROM documento WHERE id= ?");
			pstmt.setLong(1, id);
			pstmt.execute();

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

}