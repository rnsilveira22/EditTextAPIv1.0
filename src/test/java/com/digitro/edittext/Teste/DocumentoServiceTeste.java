package com.digitro.edittext.Teste;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.digitro.edittext.model.Documento;
import com.digitro.edittext.service.DocumentoService;

public class DocumentoServiceTeste {

	private DocumentoService documentoService;
	


	@Before
	public void before() {
		this.documentoService = new DocumentoService();
		limparDocumentoNoBanco();
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
		List<Documento> listarTodos = documentoService.listar(null,null);
		Assert.assertNotNull(listarTodos);
		Assert.assertTrue(listarTodos.isEmpty());
		Documento documento = new Documento();
		documento.setTitulo("teste lista");
		documento.setCorpo("Testelista");
		documentoService.salvar(documento);
		listarTodos = documentoService.listar(null,null);
		Assert.assertEquals(1, listarTodos.size());
	}

	@Test
	public void deveRetornarUmDocumentoPesquisadoPeloId() {
		Documento documento = new Documento();
		documento.setTitulo("Teste de buscar por ID");
		documento.setCorpo("Corpo do documento");
		Documento documentoNovo = null;
		documentoNovo = documentoService.salvar(documento);
		Assert.assertNotNull(documentoNovo.getId());
		Assert.assertNotNull(documentoNovo.getData());
		Assert.assertEquals(documento.getTitulo(), documentoNovo.getTitulo());
		Assert.assertEquals(documento.getCorpo(), documentoNovo.getCorpo());

	}

	@Test
	public void deveVerificarSeFoiExcluido() {
		Documento documento = new Documento();
		documento.setTitulo("Teste de deletar Documento");
		documento.setCorpo("Corpo do documento");

		Documento documentoNovo = documentoService.salvar(documento);
		Assert.assertNotNull(documentoNovo);
		documentoService.exclui(documentoNovo.getId());
		documento = documentoService.get(documentoNovo.getId());
		Assert.assertNull(documento);
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
		Documento documentoNovo = new Documento();
		documentoNovo = documentoService.atulizar(documentoParaAtulizar);
		Assert.assertNotEquals(documento1.getCorpo(), documentoNovo.getCorpo());
		Assert.assertNotEquals(documento1.getTitulo(), documentoNovo.getTitulo());
		Assert.assertEquals(documento1.getId(), documentoNovo.getId());
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
		List<Documento> listaResultados = documentoService.listar(titulo, corpo);
		Assert.assertEquals(1, ((long) listaResultados.size()));
		Assert.assertNotNull(listaResultados.get(0).getId());
		Assert.assertNotNull(listaResultados.get(0).getData());
		Assert.assertTrue(!titulo.contains(listaResultados.get(0).getTitulo()));

	}

//	@After
//	public void after() {
//		this.documentoService = new DocumentoService();
//		limparDocumentoNoBanco();
//	}

	private void limparDocumentoNoBanco() {
		String titulo = null;
		String corpo = null;
		List<Documento> documentos = documentoService.listar(titulo, corpo);
		for (Documento documento: documentos) {
			documentoService.exclui(documento.getId());
		}

	}

}
