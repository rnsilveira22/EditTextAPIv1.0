package com.digitro.edittext.Teste;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.digitro.edittext.dao.DocumentoDao;
import com.digitro.edittext.model.Documento;
import com.digitro.edittext.service.DocumentoService;

public class DocumentoServiceDaoMock {
	
	private DocumentoService documentoService;
	public DocumentoDao documentoDao = Mockito.mock(DocumentoDao.class); 
	

	@Before
	public void before() {
		public DocumentoDao documentoDao = Mockito.mock(DocumentoDao.class); 	
	}

	

	// Testes unitários
	
	@Test
	public void deveLancarErroQuandoSalvaDocumentoComId() {
		Documento documento = new Documento();
		documento.setId(1l);
		documento.setTitulo("xx");
		documento.setCorpo("Corpo texto pequeno");

		try {
			documentoService.salvar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("Ao salvar um documento o ID deve ser nulo.", e.getMessage());
		}

	}

	@Test
	public void deveLancaErroQuantoTituloInvalido() {

		Documento documento = new Documento();
		documento.setTitulo("");
		documento.setCorpo("Corpo texto pequeno");

		Assert.assertNotNull(documento);

		DocumentoService documentoService = new DocumentoService();

		try {
			documentoService.salvar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O titulo é inválido", e.getMessage());
		}
		
	}

	@Test
	public void deveLancaErroQuantoCorpoInvalido() {

		Documento documento = new Documento();
		documento.setTitulo("54543123123");
		documento.setCorpo("");

		Assert.assertNotNull(documento);

		try {
			documentoService.salvar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O corpo é inválido", e.getMessage());
		}
		
	}

	// Teste integracão

	@Test
	public void deveSalvarUmDocumentoValido() {
		Documento documento = new Documento();
		documento.setTitulo("teste final");
		documento.setCorpo("corpo valido");

		documento = documentoService.salvar(documento);

		Assert.assertNotNull(documento.getId());
		Assert.assertNotNull(documento.getData());
		
	}

	@Test
	public void deveRetornarUmListaComTodosDocumentosArmazenados() {
		
		ArrayList<Documento> listarTodos = documentoService.listar();
		Assert.assertNotNull(listarTodos);
		Assert.assertTrue(listarTodos.isEmpty());

		Documento documento = new Documento();
		documento.setTitulo("teste lista");
		documento.setCorpo("Testelista");
		documentoService.salvar(documento);
		listarTodos = documentoService.listar();
		Assert.assertEquals(1, listarTodos.size());

		
	}

	@Test
	public void deveRetornarUmDocumentoPesquisadoPeloId() {
		Documento documento = new Documento();
		documento.setTitulo("Teste de buscar por ID");
		documento.setCorpo("Corpo do documento");
		documentoService.salvar(documento);
		Documento documentoFinal = new Documento();

		documentoFinal = documentoService.listar(documento.getId());

		Assert.assertEquals(documento.getId(), documentoFinal.getId());
		Assert.assertNotNull(documentoFinal);
		Assert.assertNotNull(documentoFinal.getData());
	}

	@Test
	public void deveVerificarSeFoiExcluido() {
		Documento documento = new Documento();
		documento.setTitulo("Teste de deletar Documento");
		documento.setCorpo("Corpo do documento");
		documentoService.salvar(documento);
		ArrayList<Documento> documentos = documentoService.listar();
		Assert.assertFalse(documentos.isEmpty());
		Assert.assertNotNull(documentos);
		
		Long id = documentos.get(0).getId();
		documentoService.exclui(id);
		documentos = documentoService.listar();
		Assert.assertTrue(documentos.isEmpty());
	}

	@Test
	public void deveRetornarListaDeDocumentosBuscadosPelosFiltrosTituloEOuCorpo() {
		Documento documento1 = new Documento();
		documento1.setTitulo("Teste de Buscar por filtros 001");
		documento1.setCorpo("Corpo do documento 001 Teste de busca por filtros 001");
		Documento documento2 = new Documento();
		documento2.setTitulo("Teste de Buscar por filtros 002");
		documento2.setCorpo("Corpo do documento 002 Teste de busca por filtros 002");
		Documento documento3 = new Documento();
		documento3.setTitulo("Teste de Buscar por filtros 003");
		documento3.setCorpo("zzzzzzzzzz   xxxxxxxxxxxxxxxxxx ");	
		documentoService.salvar(documento1);
		documentoService.salvar(documento2);
		documentoService.salvar(documento3);
		String titulo = "003";
		String corpo = "";
		ArrayList<Documento> listaResultados = documentoService.listar(titulo, corpo);
		
		
		Assert.assertEquals(1,((long)listaResultados.size()));
		Assert.assertNotNull(listaResultados.get(0).getId());
		Assert.assertNotNull(listaResultados.get(0).getData());
		Assert.assertTrue(!titulo.contains(listaResultados.get(0).getTitulo()));
		
		
		
		
	}

	@Test
	public void deveAtulizarDocumentoArmazenado() {
		
		Documento documento1 = new Documento();
		documento1.setTitulo("Teste de Atualizar Documento 001");
		documento1.setCorpo("Corpo do documento 001 Teste de busca por filtros 001");
			
		documento1 = documentoService.salvar(documento1);
		Documento documentoParaAtulizar = new Documento();
		
		
		documentoParaAtulizar.setId(documento1.getId());
		documentoParaAtulizar.setTitulo("003");
		documentoParaAtulizar.setCorpo("Corpo do documento 003");
		
		documentoService.atulizar(documentoParaAtulizar);
		
		Assert.assertNotEquals(documento1.getTitulo(), documentoParaAtulizar.getTitulo());
		Assert.assertEquals(documento1.getId(), documentoParaAtulizar.getId());
		
	}
}

}
