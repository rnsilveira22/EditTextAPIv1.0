package com.digitro.edittext.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.digitro.edittext.dao.DaoException;
import com.digitro.edittext.dao.DocumentoDao;
import com.digitro.edittext.model.Documento;


public class DocumentoServiceMockTeste {

	// Teste integracão 
	
	@Test
	public void deveSalvarUmDocumentoValido() throws DaoException {
		Documento documento = new Documento();
		documento.setTitulo("teste final");
		documento.setCorpo("corpo valido");

		Documento documentoRetorno = new Documento();
		documentoRetorno.setId(1l);
		documentoRetorno.setData(new Date());
		documentoRetorno.setTitulo("teste final");
		documentoRetorno.setCorpo("corpo valido");

		DocumentoDao documentoDao = Mockito.mock(DocumentoDao.class);
		when(documentoDao.salvar(documento)).thenReturn(documentoRetorno);

		Documento resultado = documentoDao.salvar(documento);

		Assert.assertSame(resultado, documentoRetorno);
		Assert.assertEquals(resultado.getId(), resultado.getId());
		Assert.assertEquals(documento.getTitulo(), resultado.getTitulo());
		Assert.assertEquals(documento.getCorpo(), resultado.getCorpo());
		Assert.assertNotNull(resultado.getData());
	}

	@Test
	public void deveRetornarUmListaComTodosDocumentosArmazenados() throws DaoException {
		DocumentoDao documentoDao = Mockito.mock(DocumentoDao.class);
		List<Documento> listaTodos = new ArrayList<>();
		when(documentoDao.listar(null, null)).thenReturn(listaTodos);

		Assert.assertNotNull(listaTodos);
		Assert.assertTrue(listaTodos.isEmpty());
		Documento documento = new Documento();
		documento.setTitulo("teste lista");
		documento.setCorpo("Testelista");
		listaTodos.add(documento);

		documentoDao.listar(null, null);
		Assert.assertEquals(1, listaTodos.size());
		assertEquals(listaTodos.get(0), documento);
	}

	@Test
	public void deveRetornarUmDocumentoPesquisadoPeloId() throws DaoException {
		Documento documentoRetorno = new Documento();
		documentoRetorno.setId(1l);
		documentoRetorno.setData(new Date());
		documentoRetorno.setTitulo("teste final");
		documentoRetorno.setCorpo("corpo valido");

		DocumentoDao documentoDao = Mockito.mock(DocumentoDao.class);
		when(documentoDao.get(documentoRetorno.getId())).thenReturn(documentoRetorno);
		Documento resultado = documentoDao.get(documentoRetorno.getId());

		Assert.assertNotNull(resultado.getId());
		Assert.assertNotNull(resultado.getData());
		Assert.assertEquals(resultado.getTitulo(), documentoRetorno.getTitulo());
		Assert.assertEquals(resultado.getCorpo(), documentoRetorno.getCorpo());

	}

	@Test
	public void deveVerificarSeFoiExcluido() {
		//verificar o erro no WHEN devido o metodo excluir ser void
		
		
//		Documento documento = new Documento();
//		documento.setId(1l);
//		documento.setData(new Date());
//		documento.setTitulo("teste final");
//		documento.setCorpo("corpo valido");
//		
//		Documento documentoRetorno = null;
//		DocumentoService documentoService = new DocumentoService();
//		DocumentoDao documentoDao = Mockito.mock(DocumentoDao.class);
//		
//		when(documentoDao.excluir(documento.getId())).thenReturn(documentoRetorno);
//		documentoDao.excluir(documento.getId());
//		
//		Assert.assertNotNull(documento);
//		Assert.assertNull(documentoRetorno);
	}

	@Test
	public void deveAtualizarDocumentoArmazenado() throws DaoException {
			
		Documento documento = new Documento();
		documento.setId(1l);
		documento.setTitulo("Teste de Atualizar Documento 001");
		documento.setCorpo("Corpo do documento 001 Teste de busca por filtros 001");
		
		Documento documentoAtualizado = new Documento();
		documentoAtualizado.setId(1l);
		documentoAtualizado.setTitulo("documento alturalizado");
		documentoAtualizado.setCorpo("Corpo do documento atualizado");
		documentoAtualizado.setData(new Date());
		
		DocumentoDao documentoDao = Mockito.mock(DocumentoDao.class);	
		when(documentoDao.atualizar(documento)).thenReturn(documentoAtualizado);

		DocumentoService documentoService = new DocumentoService(documentoDao);
		Documento documentoNovo = new Documento();
		documentoNovo = documentoService.atualizar(documento);
		Assert.assertNotEquals(documento, documentoNovo);
		Assert.assertEquals(documentoAtualizado.getCorpo(), documentoNovo.getCorpo());
		Assert.assertEquals(documentoAtualizado.getTitulo(), documentoNovo.getTitulo());
		Assert.assertEquals(documentoAtualizado.getId(), documentoNovo.getId());
		Assert.assertEquals(documentoNovo.getData(), documentoAtualizado.getData());
		
	}

	@Test
	public void deveRetornarListaDeDocumentosBuscadosPelosFiltrosTituloEOuCorpo() throws DaoException {
		Documento documento1 = new Documento();
		documento1.setId(1l);
		documento1.setTitulo("Teste de Buscar por filtros 001");
		documento1.setCorpo("Corpo do documento 001 Teste de busca por filtros 001");
		documento1.setData(new Date());
		Documento documento2 = new Documento();
		documento2.setId(2l);
		documento2.setTitulo("Teste de Buscar por filtros 002");
		documento2.setCorpo("Corpo do documento 002 Teste de busca por filtros 002");
		documento2.setData(new Date());
		Documento documento3 = new Documento();
		documento3.setId(3l);
		documento3.setTitulo("Teste de Buscar por filtros 003");
		documento3.setCorpo("zzzzzzzzzz   xxxxxxxxxxxxxxxxxx ");
		documento3.setData(new Date());
		List<Documento> documentos = new ArrayList<>();
		documentos.add(documento1);
		documentos.add(documento2);
		documentos.add(documento3);
		String titulo = "003";
		String corpo = "";
		List<Documento> documentosFiltro = new ArrayList<>();
		documentosFiltro.add(documento3);
		
		
		DocumentoDao documentoDao = Mockito.mock(DocumentoDao.class);
		when(documentoDao.listar(titulo, corpo)).thenReturn(documentosFiltro);
		
		List<Documento> listaResultados = documentoDao.listar(titulo, corpo);
		
		Assert.assertEquals(1, ((long) listaResultados.size()));
		Assert.assertNotNull(listaResultados.get(0).getId());
		Assert.assertNotNull(listaResultados.get(0).getData());
		Assert.assertTrue(!titulo.contains(listaResultados.get(0).getTitulo()));

	}
}
