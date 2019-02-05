package com.digitro.edittext.Teste;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.digitro.edittext.model.Documento;
import com.digitro.edittext.service.DocumentoService;

public class DocumentoServiceTeste {

	private DocumentoService documentoService;
	// testes unitários

	@Before
	public void before() {
		this.documentoService = new DocumentoService();
		limparDocumentoNoBanco();

	}

	private void limparDocumentoNoBanco() {
		DocumentoService documentoService = new DocumentoService();
		ArrayList<Documento> documentos = documentoService.listar();
		for (Documento documento : documentos) {
			documentoService.exclui(documento.getId());
		}
	}

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
			Assert.assertEquals("Ao salva um documento o ID deve ser nulo", e.getMessage());
		}
		// pronto não mexer
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
		// pronto não mexer
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
		// pronto não mexer
	}

	// Teste integracao

	@Test
	public void deveSalvarUmDocumentoValido() {
		Documento documento = new Documento();
		documento.setTitulo("teste final");
		documento.setCorpo("corpo valido");

		documento = documentoService.salvar(documento);

		Assert.assertNotNull(documento.getId());
		Assert.assertNotNull(documento.getData());
		// pronto não mexer
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

		// pronto não mexer
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
		
		
		
		
		
	}
}