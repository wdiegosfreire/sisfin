<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<script type="text/javascript" src="javascript/pages/jvsConta.js"></script>

<html:form action="conta.do?cmd=actExecInsert" focus="btpConta.btpContaPai.conCodConta">

	<div class="puiPanel" title="Cadastro de Contas">

		<!-- Formulário de cadastro de contas -->
		<table class="width100pc">
			<tr>
				<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.conTxtDescricao" /> Pai :</td>
				<td>
					<html:hidden property="cmd" />
					<html:hidden property="btpConta.conCodConta" />
					<select name="btpConta.btpContaPai.conCodConta" onchange="conCodContaPaiChange();">
						<option value="">Selecione...</option>
						<logic:iterate id="btpContaListCombo" name="btpContaListCombo">
							<option
								class="ui-widget-header"
								data-level="1"
								value="<bean:write name='btpContaListCombo' property='conCodConta' />">

								<bean:write name='btpContaListCombo' property='conTxtDescricao' />
							</option>

							<logic:iterate name="btpContaListCombo" property="btpContaList" id="btpContaListLevel2">
								<option
									value="<bean:write name='btpContaListLevel2' property='conCodConta' />"
									data-level="2"
									style="padding-left: 20px; font-weight: bold; color: black;">

									<bean:write name='btpContaListLevel2' property='conTxtDescricao' />
								</option>

								<logic:iterate name="btpContaListLevel2" property="btpContaList" id="btpContaListLevel3">
									<option
										value="<bean:write name='btpContaListLevel3' property='conCodConta' />"
										data-level="3"
										style="padding-left: 40px;">

										<bean:write name='btpContaListLevel3' property='conTxtDescricao' />
									</option>
								</logic:iterate>

							</logic:iterate>

						</logic:iterate>
					</select>
				</td>
			</tr>
			<tr>
				<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.conTxtDescricao" /> :</td>
				<td><html:text property="btpConta.conTxtDescricao" size="30" maxlength="30"/></td>
			</tr>
			<tr>
				<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.conNumNivel" /> :</td>
				<td><html:text property="btpConta.conNumNivel" size="20" maxlength="20"/></td>
			</tr>
	
			<tr>
				<td width="130" class="fontePadraoNegrito">Atributos :</td>
				<td>
					<div id="divAtributo"></div>
				</td>
			</tr>
		</table>

		<!-- Botões de opção do formulário principal -->
		<hr size="1">
		<table style="width: 100%; border: none; font-size: 12px;">
			<tr> 
				<td align="left" nowrap>
					<logic:notEqual name="FrmConta" property="cmd" value="actShowEditForm">
						<input type="submit" name="btnConfirmar" value="<bean:message key='label.button.confirm' />" />
						<input type="button" name="btnVoltar" value="<bean:message key='label.button.back' />" onClick="history.go(-1)">
						<input type="button" name="btnFiltrar" value="<bean:message key='label.button.filter' />" onClick="document.forms[0].action='conta.do?cmd=actShowMainPage&sqlOrdem=1'; document.forms[0].submit();">
					</logic:notEqual>
					<logic:equal name="FrmConta" property="cmd" value="actShowEditForm">
						<script type="text/javascript">document.forms[0].action = "conta.do?cmd=actExecUpdate";</script>
						<input type="submit" name="btnAlterar" value="<bean:message key='label.button.modify' />" />
						<input type="button" name="btnCancelar" value="<bean:message key='label.button.cancel' />" onClick="history.go(-1)">
					</logic:equal>
	
					<script type="text/javascript">
						$("[name='btpConta.btpContaPai.conCodConta']").val('<bean:write name="FrmConta" property="btpConta.btpContaPai.conCodConta" />');
						conCodContaPaiChange();
					</script>
				</td>
			</tr>
		</table>

	</div>

</html:form>