<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<!-- Lista de Formas de Pagamento cadastradas -->
<div class="puiPanel" title="Listagem de Formas de Pagamento Cadastradas">

	<table style="width: 100%;">
		<tr class="fontePadraoNegrito">
			<td width="100" align="center"><bean:message key="label.application.codigo.up" /></td>
			<td><bean:message key="label.attribute.fopNomFormaPagamento.up" /></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<logic:iterate id="btpFormaPagamentoList" name="btpFormaPagamentoList">
			<tr class="fontePadrao" onmouseover="lightOn(this)" onmouseout="lightOff(this)">
				<td align="center"><bean:write name="btpFormaPagamentoList" property="fopCodFormaPagamento" /></td>
				<td><bean:write name="btpFormaPagamentoList" property="fopNomFormaPagamento" /></td>
				<td width="16"><img src="images/editar.gif" alt="<bean:message key='label.altText.modify'/>" onClick="window.location='formaPagamento.do?cmd=actShowEditForm&btpFormaPagamento.fopCodFormaPagamento=<bean:write name='btpFormaPagamentoList' property='fopCodFormaPagamento' />'"></td>
				<td width="16"><img src="images/excluir.gif" alt="<bean:message key='label.altText.exclude'/>" onClick="confirmDelete(<bean:write name='btpFormaPagamentoList' property='fopCodFormaPagamento' />)"></td>
			</tr>
		</logic:iterate>
	</table>

</div>