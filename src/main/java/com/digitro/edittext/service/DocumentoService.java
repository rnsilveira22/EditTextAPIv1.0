package com.digitro.edittext.service;

import java.util.ArrayList;

import com.digitro.edittext.dao.DocumentoDao;
import com.digitro.edittext.model.Documento;

public class DocumentoService {

	private void validaTitulo(String titulo) {
		if (titulo == null || titulo.isEmpty() || titulo.length() > 50) {
			throw new RuntimeException("O titulo é inválido");
		}
	}

	private void validaCorpo(String corpo) {
		if (corpo == null || corpo.isEmpty() || corpo.length() > 500) {
			throw new RuntimeException("O corpo é inválido");
		}

	}

	private boolean verificaId (Documento documento) {
		return documento.getId() == null; 
	}
public Documento salvar(Documento documento) {

		if (!verificaId(documento)) {
			throw new RuntimeException("Ao salvar um documento o ID deve ser nulo.");
		}

		validaTitulo(documento.getTitulo());
		validaCorpo(documento.getCorpo());

		DocumentoDao dao = new DocumentoDao();
		documento = dao.salva(documento);

		return documento;

	}

	public Documento atulizar(Documento documento) {
		if (verificaId(documento)) {
			throw new RuntimeException("Ao atualizar um documento o ID não deve ser nulo.");
		}
		validaTitulo(documento.getTitulo());
		validaCorpo(documento.getCorpo());

		DocumentoDao dao = new DocumentoDao();
		documento = dao.atualiza(documento);

		return documento;
	}

	public void exclui(Long id) {
		DocumentoDao documentoDao = new DocumentoDao();
		documentoDao.excluir(id);

	}

	public ArrayList<Documento> listar() {
		DocumentoDao documentoDao = new DocumentoDao();
		ArrayList<Documento> documentos = new ArrayList<Documento>();
		documentos = documentoDao.listar();
		return documentos;
	}

	public Documento listar(Long id) {
		Documento documento = new Documento();
		DocumentoDao documentoDao = new DocumentoDao();
		documento = documentoDao.listar(id);
		return documento;
	}

	public ArrayList<Documento> listar(String titulo, String corpo){
		ArrayList<Documento> documentos = new ArrayList<Documento>();
		DocumentoDao documentoDao = new DocumentoDao();
		documentos = documentoDao.listar(titulo,corpo);
		
		
		return documentos;
	}
	
}
