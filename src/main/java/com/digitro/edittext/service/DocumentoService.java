package com.digitro.edittext.service;


import java.util.List;

import com.digitro.edittext.dao.DaoException;
import com.digitro.edittext.dao.DocumentoDao;
import com.digitro.edittext.model.Documento;


public class DocumentoService {
	
	private DocumentoDao dao;
	
	public DocumentoService() {
		this.dao = new DocumentoDao();
	}
	
	public DocumentoService(DocumentoDao dao) {
		this.dao = dao;
	}
		
	public Documento salvar(Documento documento) {
		if (!verificaId(documento.getId())) {
			throw new RuntimeException("Ao salvar um documento o ID deve ser nulo.");
		}
		validaTitulo(documento.getTitulo());
		validaCorpo(documento.getCorpo());
		try {
			documento = dao.salvar(documento);
		} catch (DaoException e) {
			throw new RuntimeException("Ao salvar um documento. Motivo: " + e.getMessage());
		}
		return documento;
	}

	public Documento atualizar(Documento documento) throws DaoException {
		if (verificaId(documento.getId())) {
			throw new RuntimeException("Ao atualizar um documento o ID não deve ser nulo.");
		}
		validaTitulo(documento.getTitulo());
		validaCorpo(documento.getCorpo());
		documento = dao.atualizar(documento);
		return documento;
	}

	public void excluir(Long id) throws DaoException {
		if (verificaId(id)) {
			throw new RuntimeException("Ao excluir um documento o ID não deve ser nulo.");
		}
		dao.excluir(id);
	}
	public List<Documento> listar(String titulo,String corpo) throws DaoException {
		List<Documento> documentos = dao.listar(titulo, corpo);
		return documentos;
	}

	public Documento get(Long id) throws DaoException {
		if (verificaId(id)) {
			throw new RuntimeException("Ao buscar um documento o ID não deve ser nulo.");
		}
		Documento documento = new Documento();
		
		documento = dao.get(id);
		return documento;
	}

	private void validaTitulo(String titulo) {
		if (titulo == null || titulo.isEmpty() || titulo.length() > 50) {
			throw new RuntimeException("O titulo é inválido: titulo deve conter de 1 a 50 caracteres");
		}
	}

	private void validaCorpo(String corpo) {
		if (corpo == null || corpo.isEmpty() || corpo.length() > 500) {
			throw new RuntimeException("O corpo é inválido: corpo deve conter de 1 a 500 caracteres");
		}

	}

	private boolean verificaId (Long id) {
		return id == null; 
	}
	
}
