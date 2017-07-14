<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<script type="text/javascript" src="pages/estabelecimento/estabelecimento-main.js"></script>

<html:form action="estabelecimento.do?cmd=actShowMainPage&sqlOrdem=1">

	<html:hidden property="cmd" />
	<html:hidden property="btpEstabelecimento.estCodEstabelecimento" />

	<div class="puipanel" title="Cadastro de Estabelecimentos">

		<!-- Formulário de cadastro e consulta de estabelecimentos -->
		<div class="ui-grid ui-grid-responsive fieldContainer">
			<div class="ui-grid-row">
				<div class="ui-grid-col-3"><bean:message key="label.attribute.estNomEstabelecimento" /></div>
				<div class="ui-grid-col-9"><html:text property="btpEstabelecimento.estNomEstabelecimento" styleClass="width100pc"/></div>
			</div>
		</div>

		<!-- Botões de opção do formulário principal -->
		<div class="ui-grid ui-grid-responsive buttonContainer">
			<div class="ui-grid-row buttonContainer">
				<logic:notEqual name="FrmEstabelecimento" property="cmd" value="actShowEditForm">
					<div class="ui-grid-col-4"><input type="button" name="btnConsultar"></div>
					<div class="ui-grid-col-4"><input type="button" name="btnLimparConsulta"></div>
					<div class="ui-grid-col-4"><input type="button" name="btnConfirmar" /></div>
				</logic:notEqual>
				<logic:equal name="FrmEstabelecimento" property="cmd" value="actShowEditForm">
					<div class="ui-grid-col-6"><input type="button" name="btnAlterar" /></div>
					<div class="ui-grid-col-6"><input type="button" name="btnCancelar"></div>
				</logic:equal>
			</div>
		</div>

		<hr size="1">

		<div class="ui-grid ui-grid-responsive resultContainer">
			<div class="ui-grid-row">
				<div class="ui-grid-col-12">
					<logic:present name="btpEstabelecimentoList">
						<table data-name="primeuiDatatable" title="Estabelecimentos Cadastrados">
							<thead><tr>
								<th class="width60px">Cód.</th>
								<th class="left">Nome</th>
								<th class="width40px" title="Editar"></th>
								<th class="width40px" title="Excluir"></th>
							</tr></thead>
							<tbody>
								<logic:iterate name="btpEstabelecimentoList" id="btpEstabelecimento">
									<tr>
										<td class="center"><bean:write name="btpEstabelecimento" property="estCodEstabelecimento" /></td>
										<td><bean:write name="btpEstabelecimento" property="estNomEstabelecimento" /></td>
										<td class="center">
											<a
												data-name="btnEditar"
												data-value="<bean:write name='btpEstabelecimento' property='estCodEstabelecimento' />">
											</a>
										</td>
										<td class="center">
											<a
												data-name="btnExcluir"
												data-value="<bean:write name='btpEstabelecimento' property='estCodEstabelecimento' />">
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