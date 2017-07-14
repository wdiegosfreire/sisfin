<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<script type="text/javascript" src="dwr/interface/DwrExtrato.js"></script>
<script type="text/javascript" src="dwr/interface/DwrItemExtrato.js"></script>
<script type="text/javascript" src="javascript/pages/jvsExtrato.js"></script>

<!-- Resultado da consulta de Estabelecimentos -->
<div class="puiPanel" title="Formulário de Importação de Extratos">

	<jsp:include page="../extrato/resultTable.jsp"></jsp:include>

	<hr />

	<input type="button" value="Confirmar" onclick="jvsExtrato.execInsertObjetivoImported();">

</div>