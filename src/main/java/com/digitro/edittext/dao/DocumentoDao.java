package com.digitro.edittext.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.digitro.edittext.conectBD.ConectaPostgres;
import com.digitro.edittext.model.Documento;

public class DocumentoDao {

	

	public Documento salvar(Documento documento) throws DaoException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try(Connection con = ConectaPostgres.conectaPostgres()) {
			pstmt = con.prepareStatement("INSERT INTO documento(titulo,corpo) VALUES(?,?);",
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, documento.getTitulo());
			pstmt.setString(2, documento.getCorpo());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();

			while (rs.next()) {
				documento.setId(rs.getLong("id"));
				documento.setData(rs.getDate("data"));
			}

			pstmt.close();
			return documento;

		} catch (Exception e) {
			throw new DaoException("", e); //TODO: arrumar
		} 
		
	}

	public List<Documento> listar(String titulo, String corpo) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Documento> lista = new ArrayList<>();
		
		
		try(Connection con = ConectaPostgres.conectaPostgres();) {

			String script = "select * FROM documento WHERE 1 = 1";

			if (titulo != null && !titulo.isEmpty()) {
				script += " and titulo LIKE '%" + titulo + "%' ";
			}
			if (corpo != null && !corpo.isEmpty()) {
				script += " and corpo LIKE '%" + corpo + "%' ";
			}
			pstmt = con.prepareStatement(script);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Documento documento = new Documento();
				documento.setId(rs.getLong("id"));
				documento.setTitulo(rs.getString("titulo"));
				documento.setCorpo(rs.getString("corpo"));
				documento.setData(rs.getDate("Data"));
				lista.add(documento);
			}
			return lista;
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		} 
		return null;
	}

	public Documento get(Long id) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Documento documento = new Documento();
		try(Connection con = ConectaPostgres.conectaPostgres();) {
			pstmt = con.prepareStatement("SELECT * FROM documento WHERE id=?;");
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				documento.setId(rs.getLong("id"));
				documento.setTitulo(rs.getString("titulo"));
				documento.setCorpo(rs.getString("corpo"));
				documento.setData(rs.getDate("Data"));
			}
			return documento;
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();

		} 
		return documento;
	}

	public Documento atualizar(Documento documento) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try(Connection con = ConectaPostgres.conectaPostgres();) {

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
				documento.setData(rs.getDate("data"));
			}
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return documento;
	}

	public void excluir(Long id) {
		
		PreparedStatement pstmt = null;
		
		try(Connection con = ConectaPostgres.conectaPostgres();) {
			pstmt = con.prepareStatement("DELETE FROM documento WHERE id= ?");
			pstmt.setLong(1, id);
			pstmt.execute();

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		} 
	}

}