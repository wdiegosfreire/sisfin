<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<ul id="puimenu">
	<li><h3>Movimentação (novo)</h3></li>
	<li><a href="resumo.do?cmd=actShowMainPage">Resumo</a></li>
	<li><a href="movimentoNew.do?cmd=actShowMainPage">Movimento</a></li>
	<li><a href="extrato.do?cmd=actShowMainPage&sqlOrder=1">Extrato</a></li>

	<li><h3>Cadastros</h3></li>
	<li><a href="conta.do?cmd=actShowMainPage&sqlOrder=1">Contas</a></li>
	<li><a href="estabelecimento.do?cmd=actShowMainPage&sqlOrder=1">Estabelecimentos</a></li>
	<li><a href="formaPagamento.do?cmd=actShowMainPage&sqlOrder=1">Formas de Pagamento</a></li>

	<li><h3>Em Breve</h3></li>
	<li><a href="#">Desejos</a></li>
	<li><a href="#">Automóvel</a></li>
	<li><a href="#">Supermercado</a></li>

	<li><hr></li>
	<li><a href="logoff.do?cmd=actExecLogoff">Sair</a></li>
</ul>