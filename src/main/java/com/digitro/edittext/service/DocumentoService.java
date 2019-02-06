package com.digitro.edittext.service;


import java.util.ArrayList;
import java.util.List;

import com.digitro.edittext.dao.DocumentoDao;
import com.digitro.edittext.model.Documento;

public class DocumentoService {
	
	DocumentoDao dao = new DocumentoDao();
	
	
	public Documento salvar(Documento documento) {
		if (!verificaId(documento)) {
			throw new RuntimeException("Ao salvar um documento o ID deve ser nulo.");
		}
		validaTitulo(documento.getTitulo());
		validaCorpo(documento.getCorpo());
		documento = dao.salva(documento);

		return documento;

	}

	public Documento atulizar(Documento documento) {
		if (verificaId(documento)) {
			throw new RuntimeException("Ao atualizar um documento o ID não deve ser nulo.");
		}
		validaTitulo(documento.getTitulo());
		validaCorpo(documento.getCorpo());

		
		documento = dao.atualiza(documento);

		return documento;
	}

	public void exclui(Long id) {
		DocumentoDao documentoDao = new DocumentoDao();
		documentoDao.excluir(id);

	}
	public List<Documento> listar(String titulo,String corpo) {
		List<Documento> documentos = new ArrayList<>();
		documentos = dao.listar(titulo, corpo);
		return documentos;
	}

	public Documento get(Long id) {
		Documento documento = new Documento();
		
		documento = dao.get(id);
		return documento;
	}

	private void validaTitulo(String titulo) {
		if (titulo == null || titulo.isEmpty() || titulo.length() > 50){
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
	
}
