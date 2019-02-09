package com.digitro.edittext.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.digitro.edittext.dao.DaoException;
import com.digitro.edittext.model.Documento;

public class DocumentoServiceTeste {

	private DocumentoService documentoService;

	@Before
	public void before() throws DaoException {
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
	public void deveLancarErroQuandoSalvarDocumentoComTituloInvalido() {
		Documento documento = new Documento();
		documento.setTitulo("");
		documento.setCorpo("Corpo texto pequeno");
		Assert.assertNotNull(documento);
		DocumentoService documentoService = new DocumentoService();
		try {
			documentoService.salvar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O titulo é inválido: titulo deve conter de 1 a 50 caracteres", e.getMessage());
		}
		
		documento.setTitulo(null);
		try {
			documentoService.salvar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O titulo é inválido: titulo deve conter de 1 a 50 caracteres", e.getMessage());
		}
		
		String tituloMaisDe50Caracteres = "123456789012345678901234567890123456789012345678901";
		documento.setTitulo(tituloMaisDe50Caracteres);
		try {
			documentoService.salvar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O titulo é inválido: titulo deve conter de 1 a 50 caracteres", e.getMessage());
		}
	}

	@Test
	public void deveLancaErroQuandoSalvarDocumentoComCorpoInvalido() {
		Documento documento = new Documento();
		documento.setTitulo("54543123123");
		Assert.assertNotNull(documento);

		documento.setCorpo("");
		try {
			documentoService.salvar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O corpo é inválido: corpo deve conter de 1 a 500 caracteres", e.getMessage());
		}
		
		documento.setCorpo(null);
		try {
			documentoService.salvar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O corpo é inválido: corpo deve conter de 1 a 500 caracteres", e.getMessage());
		}
		
		String corpoMaisDe500Caracteres = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
				+ "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
				+ "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
				+ "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
				+ "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012234";
		
		documento.setCorpo(corpoMaisDe500Caracteres);
		try {
			documentoService.salvar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O corpo é inválido: corpo deve conter de 1 a 500 caracteres", e.getMessage());
		}

	}
	
	@Test
	public void deveLancarErroQuantoAtualizarDocumentoSemId() throws DaoException {
	
		Documento documento = new Documento();
		documento.setTitulo("Titulo do documento");
		documento.setCorpo("Corpo texto pequeno");

		try {
			documentoService.atualizar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("Ao atualizar um documento o ID não deve ser nulo.", e.getMessage());
		}
	}
	@Test
	public void deveLancarErroQuandoAtualizarDocumentoComTituloInvalido() throws DaoException {
		Documento documento = new Documento();
		documento.setId(1l);
		
		documento.setCorpo("Corpo texto pequeno");
		Assert.assertNotNull(documento);
		DocumentoService documentoService = new DocumentoService();
		documento.setTitulo("");
		try {
			documentoService.atualizar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O titulo é inválido: titulo deve conter de 1 a 50 caracteres", e.getMessage());
		}
		
		documento.setTitulo(null);
		try {
			documentoService.atualizar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O titulo é inválido: titulo deve conter de 1 a 50 caracteres", e.getMessage());
		}
		
		String tituloMaisDe50Caracteres = "123456789012345678901234567890123456789012345678901";
		documento.setTitulo(tituloMaisDe50Caracteres);
		try {
			documentoService.atualizar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O titulo é inválido: titulo deve conter de 1 a 50 caracteres", e.getMessage());
		}
	}
	@Test
	public void deveLancarErroQuandoAtualizarDocumentoComCorpoInvalido() throws DaoException {
		Documento documento = new Documento();
		documento.setId(1l);
		documento.setTitulo("Titulo do documento");
		documento.setCorpo("");
		Assert.assertNotNull(documento);
		DocumentoService documentoService = new DocumentoService();
		documento.setCorpo("");
		try {
			documentoService.atualizar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O corpo é inválido: corpo deve conter de 1 a 500 caracteres", e.getMessage());
		}
		documento.setCorpo(null);
		try {
			documentoService.atualizar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O corpo é inválido: corpo deve conter de 1 a 500 caracteres", e.getMessage());
		}
		String corpoMaisDe500Caracteres = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
				+ "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
				+ "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
				+ "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
				+ "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012234";
		
		documento.setCorpo(corpoMaisDe500Caracteres);
		try {
			documentoService.atualizar(documento);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("O corpo é inválido: corpo deve conter de 1 a 500 caracteres", e.getMessage());
		}
	}
	@Test
	public void deveLancarErroQuandoExcluirDocumentoSemId() throws DaoException {
		DocumentoService documentoService = new DocumentoService();
		Long id = null;

		try {
			documentoService.excluir(id);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("Ao excluir um documento o ID não deve ser nulo.", e.getMessage());
		}
	}
	// Teste integracão

	@Test
	public void deveSalvarUmDocumentoValido() throws DaoException {
		Documento documento = new Documento();
		documento.setTitulo("teste final");
		documento.setCorpo("corpo valido");
		documento = documentoService.salvar(documento);
		Assert.assertNotNull(documento.getId());
		Assert.assertNotNull(documento.getData());

		Documento documentoNovo = documentoService.get(documento.getId());
		Assert.assertEquals(documento.getId(), documentoNovo.getId());
		Assert.assertEquals(documento.getTitulo(), documentoNovo.getTitulo());
		Assert.assertEquals(documento.getCorpo(), documentoNovo.getCorpo());
		Assert.assertEquals(documento.getData(), documentoNovo.getData());
	}

	@Test
	public void deveRetornarUmListaComTodosDocumentosArmazenados() throws DaoException {
		List<Documento> listarTodos = documentoService.listar(null, null);
		Assert.assertNotNull(listarTodos);
		Assert.assertTrue(listarTodos.isEmpty());
		Documento documento = new Documento();
		documento.setTitulo("teste lista");
		documento.setCorpo("Testelista");
		documentoService.salvar(documento);
		listarTodos = documentoService.listar(null, null);
		Assert.assertEquals(1, listarTodos.size());
	}

	@Test
	public void deveRetornarUmDocumentoPesquisadoPeloId() throws DaoException {
		Documento documento = new Documento();
		documento.setTitulo("Teste de buscar por ID");
		documento.setCorpo("Corpo do documento");
		Documento documentoNovo = null;
		documento = documentoService.salvar(documento);
		documentoNovo = documentoService.get(documento.getId());
		Assert.assertNotNull(documentoNovo.getId());
		Assert.assertNotNull(documentoNovo.getData());
		Assert.assertEquals(documento.getTitulo(), documentoNovo.getTitulo());
		Assert.assertEquals(documento.getCorpo(), documentoNovo.getCorpo());

	}
	@Test
	public void deveErroQuandoBuscarUmDocumentoPesquisadoPeloIdComIdInvalidao() throws DaoException {
		Long id = null;
		try {
			documentoService.get(id);
			fail();
		} catch (RuntimeException e) {
			Assert.assertEquals("Ao buscar um documento o ID não deve ser nulo.", e.getMessage());
		}
	}
	@Test
	public void deveVerificarSeFoiExcluido() throws DaoException {
		Documento documento = new Documento();
		documento.setTitulo("Teste de deletar Documento");
		documento.setCorpo("Corpo do documento");

		documento = documentoService.salvar(documento);
		Assert.assertNotNull(documento);
		Assert.assertNotNull(documento.getId());
		Assert.assertNotNull(documento.getData());

		documentoService.excluir(documento.getId());
		Documento resultado = null;
		resultado = documentoService.get(documento.getId());
		Assert.assertNotNull(resultado);
	}

	@Test
	public void deveAtulizarDocumentoArmazenado() throws DaoException {
		Documento documento1 = new Documento();
		documento1.setTitulo("Teste de Atualizar Documento 001");
		documento1.setCorpo("Corpo do documento 001 Teste de busca por filtros 001");
		documento1 = documentoService.salvar(documento1);
		Documento documentoParaAtulizar = new Documento();
		documentoParaAtulizar.setId(documento1.getId());
		documentoParaAtulizar.setTitulo("003");
		documentoParaAtulizar.setCorpo("Corpo do documento 003");
		Documento documentoNovo = new Documento();
		documentoNovo = documentoService.atualizar(documentoParaAtulizar);
		Assert.assertNotEquals(documento1.getCorpo(), documentoNovo.getCorpo());
		Assert.assertNotEquals(documento1.getTitulo(), documentoNovo.getTitulo());
		Assert.assertEquals(documento1.getId(), documentoNovo.getId());
	}

	@Test
	public void deveRetornarListaDeDocumentosBuscadosPelosFiltrosTituloEOuCorpo() throws DaoException {
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
		String corpo = "zz";
		List<Documento> listaResultados = documentoService.listar(titulo, corpo);
		Assert.assertEquals(1, ((long) listaResultados.size()));
		Assert.assertNotNull(listaResultados.get(0).getId());
		Assert.assertNotNull(listaResultados.get(0).getData());
		Assert.assertTrue(!titulo.contains(listaResultados.get(0).getTitulo()));

	}

	private void limparDocumentoNoBanco() throws DaoException {
		String titulo = null;
		String corpo = null;
		List<Documento> documentos = documentoService.listar(titulo, corpo);

		for (Documento documento : documentos) {
			documentoService.excluir(documento.getId());
		}

	}

}
