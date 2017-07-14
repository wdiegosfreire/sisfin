<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<!-- Tabela de Resultados da consulta da entidade BtpExtrato -->
<table style="width: 100%;">
	<tr class="fontePadraoNegrito">
		<td width="100" align="center">Código</td>
		<td>Banco</td>
		<td>Competência</td>
		<td>Tipo</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<logic:present name="btpExtratoList">
		<logic:iterate id="btpExtrato" name="btpExtratoList">
			<tr class="fontePadrao" onmouseover="lightOn(this)" onmouseout="lightOff(this)">
				<td align="center"><bean:write name="btpExtrato" property="extCodExtrato" /></td>
				<td><bean:write name="btpExtrato" property="btpBanco.banTxtNome" /></td>
				<td><bean:write name="btpExtrato" property="btpTipoExtrato.tieTxtNome" /></td>
				<td><bean:write name="btpExtrato" property="extDatAno" />/<bean:write name="btpExtrato" property="extDatMes" format="00" /></td>
				<td width="16"><img src="images/editar.gif" alt="<bean:message key='label.altText.modify'/>" onClick="window.location='extrato.do?cmd=actShowImpoForm&btpExtrato.extCodExtrato=<bean:write name='btpExtrato' property='extCodExtrato' />'"></td>
				<td width="16"><img src="images/excluir.gif" alt="<bean:message key='label.altText.exclude'/>" onClick="confirmDelete(<bean:write name='btpExtrato' property='extCodExtrato' />)"></td>
			</tr>
			<tr>
				<td colspan="6">
					<%@include file="../itemExtrato/resultTable.jsp"%>  
				</td>
			</tr>
		</logic:iterate>
	</logic:present>
</table>