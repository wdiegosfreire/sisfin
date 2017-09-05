<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<script type="text/javascript" src="pages/formaPagamento/formaPagamento-main.js"></script>

<html:form action="formaPagamento.do?cmd=actExecInsert" focus="btpFormaPagamento.fopNomFormaPagamento">

	<div class="puiPanel" title="Cadastro de Formas de Pagamento">

		<!-- Formulário de cadastro de formas de pagamento -->
		<table style="width: 100%;">
			<tr>
				<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.fopNomFormaPagamento" /> :</td>
				<td>
					<html:hidden property="cmd" />
					<html:hidden property="btpFormaPagamento.fopCodFormaPagamento" />
					<html:text property="btpFormaPagamento.fopNomFormaPagamento" size="30" maxlength="30"/>
				</td>
			</tr>
		</table>

		<!-- Botões de opção do formulário principal -->
		<hr size="1">
		<table style="width: 100%;">
			<tr>
				<td align="left" nowrap>
					<logic:notEqual name="FrmFormaPagamento" property="cmd" value="actShowEditForm">
						<input type="submit" name="btnConfirmar" value="<bean:message key='label.button.confirm' />" />
						<input type="button" name="btnVoltar" value="<bean:message key='label.button.back' />" onClick="history.go(-1)">
						<input type="button" name="btnFiltrar" value="<bean:message key='label.button.filter' />" onClick="document.forms[0].action='formaPagamento.do?cmd=actShowMainPage&sqlOrdem=1'; document.forms[0].submit();">
					</logic:notEqual>
					<logic:equal name="FrmFormaPagamento" property="cmd" value="actShowEditForm">
						<script type="text/javascript">document.forms[0].action = "formaPagamento.do?cmd=actExecUpdate";</script>
						<input type="submit" name="btnAlterar" value="<bean:message key='label.button.modify' />" />
						<input type="button" name="btnCancelar" value="<bean:message key='label.button.cancel' />" onClick="history.go(-1)">
					</logic:equal>
				</td>
			</tr>
		</table>

	</div>

</html:form>