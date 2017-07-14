<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<!-- Resultado da consulta de Contas -->
<div class="puiPanel" title="Listagem de Contas Cadastradas">

	<table style="width: 100%;" cellspacing="0" border="0">
		<tr class="fontePadraoNegrito">
			<td><bean:message key="label.attribute.conTxtDescricao.up" /></td>
			<td align="center">Movimento</td>
			<td align="center">Poupança</td>
			<td align="center">Virtual</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<logic:iterate id="btpContaList" name="btpContaList">
			<tr class="fontePadrao ui-widget-header">
				<td><bean:write name="btpContaList" property="conTxtDescricao" /></td>
				<td align="center"><logic:equal name="btpContaList" property="conFlgMovimento" value="true">SIM</logic:equal></td>
				<td align="center"><logic:equal name="btpContaList" property="conFlgPoupanca" value="true">SIM</logic:equal></td>
				<td align="center"><logic:equal name="btpContaList" property="conFlgVirtual" value="true">SIM</logic:equal></td>
				<td width="16">&nbsp;</td>
				<td width="16">&nbsp;</td>
			</tr>

			<logic:iterate name="btpContaList" property="btpContaList" id="btpContaListLevel2">
				<tr class="fontePadrao" onmouseover="lightOn(this)" onmouseout="lightOff(this)" style="font-weight: bold;">
					<td style="padding-left: 20px;"><bean:write name="btpContaListLevel2" property="conTxtDescricao" /></td>
					<td align="center"><logic:equal name="btpContaListLevel2" property="conFlgMovimento" value="true">SIM</logic:equal></td>
					<td align="center"><logic:equal name="btpContaListLevel2" property="conFlgPoupanca" value="true">SIM</logic:equal></td>
					<td align="center"><logic:equal name="btpContaListLevel2" property="conFlgVirtual" value="true">SIM</logic:equal></td>
					<td width="16"><img src="images/editar.gif" alt="<bean:message key='label.altText.modify'/>" onClick="window.location='conta.do?cmd=actShowEditForm&btpConta.conCodConta=<bean:write name='btpContaListLevel2' property='conCodConta' />'"></td>
					<td width="16"><img src="images/excluir.gif" alt="<bean:message key='label.altText.exclude'/>" onClick="confirmDelete(<bean:write name='btpContaListLevel2' property='conCodConta' />)"></td>
				</tr>

				<logic:iterate name="btpContaListLevel2" property="btpContaList" id="btpContaListLevel3">
					<tr class="fontePadrao" onmouseover="lightOn(this)" onmouseout="lightOff(this)">
						<td style="padding-left: 40px;"><bean:write name="btpContaListLevel3" property="conTxtDescricao" /></td>
						<td align="center"><logic:equal name="btpContaListLevel3" property="conFlgMovimento" value="true">SIM</logic:equal></td>
						<td align="center"><logic:equal name="btpContaListLevel3" property="conFlgPoupanca" value="true">SIM</logic:equal></td>
						<td align="center"><logic:equal name="btpContaListLevel3" property="conFlgVirtual" value="true">SIM</logic:equal></td>
						<td width="16"><img src="images/editar.gif" alt="<bean:message key='label.altText.modify'/>" onClick="window.location='conta.do?cmd=actShowEditForm&btpConta.conCodConta=<bean:write name='btpContaListLevel3' property='conCodConta' />'"></td>
						<td width="16"><img src="images/excluir.gif" alt="<bean:message key='label.altText.exclude'/>" onClick="confirmDelete(<bean:write name='btpContaListLevel3' property='conCodConta' />)"></td>
					</tr>
				</logic:iterate>

			</logic:iterate>
		</logic:iterate>
	</table>

</div>