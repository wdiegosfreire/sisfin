<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<script type="text/javascript" src="pages/formaPagamento/formaPagamento-main.js"></script>

<html:form action="formaPagamento.do?cmd=actShowMainPage&sqlOrdem=1">

	<html:hidden property="cmd" />
	<html:hidden property="btpFormaPagamento.fopCodFormaPagamento" />

	<div class="puipanel" title="Cadastro de Formas de Pagamento">

		<!-- Formulário de cadastro e consulta de formas de pagamento -->
		<div class="ui-grid ui-grid-responsive fieldContainer">
			<div class="ui-grid-row">
				<div class="ui-grid-col-3"><bean:message key="label.attribute.fopNomFormaPagamento" /></div>
				<div class="ui-grid-col-9"><html:text property="btpFormaPagamento.fopNomFormaPagamento" styleClass="width100pc"/></div>
			</div>
		</div>

		<!-- Botões de opção do formulário principal -->
		<div class="ui-grid ui-grid-responsive buttonContainer">
			<div class="ui-grid-row buttonContainer">
				<logic:notEqual name="FrmFormaPagamento" property="cmd" value="actShowEditForm">
					<div class="ui-grid-col-4"><input type="button" name="btnConsultar"></div>
					<div class="ui-grid-col-4"><input type="button" name="btnLimparConsulta"></div>
					<div class="ui-grid-col-4"><input type="button" name="btnConfirmar" /></div>
				</logic:notEqual>
				<logic:equal name="FrmFormaPagamento" property="cmd" value="actShowEditForm">
					<div class="ui-grid-col-6"><input type="button" name="btnAlterar" /></div>
					<div class="ui-grid-col-6"><input type="button" name="btnCancelar"></div>
				</logic:equal>
			</div>
		</div>

		<hr size="1">

		<div class="ui-grid ui-grid-responsive resultContainer">
			<div class="ui-grid-row">
				<div class="ui-grid-col-12">
					<logic:present name="btpFormaPagamentoList">
						<table data-name="primeuiDatatable" title="Formas de Pagamento Cadastradas">
							<thead><tr>
								<th class="width60px">Cód.</th>
								<th class="left">Nome</th>
								<th class="width40px" title="Editar"></th>
								<th class="width40px" title="Excluir"></th>
							</tr></thead>
							<tbody>
								<logic:iterate name="btpFormaPagamentoList" id="btpFormaPagamento">
									<tr>
										<td class="center"><bean:write name="btpFormaPagamento" property="fopCodFormaPagamento" /></td>
										<td><bean:write name="btpFormaPagamento" property="fopNomFormaPagamento" /></td>
										<td class="center">
											<a
												data-name="btnEditar"
												data-value="<bean:write name='btpFormaPagamento' property='fopCodFormaPagamento' />">
											</a>
										</td>
										<td class="center">
											<a
												data-name="btnExcluir"
												data-value="<bean:write name='btpFormaPagamento' property='fopCodFormaPagamento' />">
											</a>
										</td>
									</tr>
								</logic:iterate>
							</tbody>
						</table>
					</logic:present>
				</div>
			</div>
		</div>

	</div>

</html:form>