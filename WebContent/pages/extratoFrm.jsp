<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<script type="text/javascript" src="javascript/pages/jvsExtrato.js"></script>

<html:form action="extrato.do?cmd=actShowMainPage" method="post" enctype="multipart/form-data" focus="btpExtrato.btpBanco.banCodBanco">

	<html:hidden property="cmd" />

	<div class="puipanel" title="Importar Extrato Bancário">

		<!-- Formulário de cadastro e consulta de estabelecimentos -->
		<div class="ui-grid ui-grid-responsive fieldContainer">
			<div class="ui-grid-row">
				<div class="ui-grid-col-3">Banco</div>
				<div class="ui-grid-col-9">
					<html:select property="btpExtrato.btpBanco.banCodBanco" styleClass="puicombobox">
						<html:option value="">Selecione...</html:option>
						<html:options collection="btpBancoListCombo" property="banCodBanco" labelProperty="banTxtNome" />
					</html:select>
				</div>
			</div>
			<div class="ui-grid-row">
				<div class="ui-grid-col-3">Tipo de Extrato</div>
				<div class="ui-grid-col-9">
					<html:select property="btpExtrato.btpTipoExtrato.tieCodTipoExtrato" styleClass="puicombobox">
						<html:option value="">Selecione...</html:option>
						<html:options collection="btpTipoExtratoListCombo" property="tieCodTipoExtrato" labelProperty="tieTxtNome" />
					</html:select>
				</div>
			</div>
			<div class="ui-grid-row">
				<div class="ui-grid-col-3">Mês</div>
				<div class="ui-grid-col-9"><html:text property="btpExtrato.extDatMes" styleClass="fieldMonth" /></div>
			</div>
			<div class="ui-grid-row">
				<div class="ui-grid-col-3">Ano</div>
				<div class="ui-grid-col-9"><html:text property="btpExtrato.extDatAno" styleClass="fieldYear" /></div>
			</div>
			<div class="ui-grid-row">
				<div class="ui-grid-col-3">Arquivo</div>
				<div class="ui-grid-col-9">
					<html:file style="font-size: 0.8em;" property="btpExtrato.formFile" />
					<a
						onclick="jvsExtrato.btnImportarClick();"
						href="javascript:void(0);"
						data-name="btnImportar"
						class="fa fa-gear fa-lg"
						title="Clique para importar o arquivo selecionado">
					</a>
				</div>
			</div>
		</div>

		<!-- Botões de opção do formulário principal -->
		<div class="ui-grid ui-grid-responsive buttonContainer">
			<div class="ui-grid-row buttonContainer">
				<div class="ui-grid-col-6">
					<input
						type="button"
						title="Seleciona os resultados de acordo com os filtros selecionados no formulário de consulta"
						name="btnFiltrar"
						value="<bean:message key='label.button.filter' />"
						onClick="jvsExtrato.btnFiltrarClick();"
					/>
				</div>
				<div class="ui-grid-col-6">
					<input
						type="button"
						name="btnVoltar"
						value="<bean:message key='label.button.back' />"
						onClick="jvsExtrato.btnVoltarClick();"
					/>
				</div>
			</div>
		</div>

		<hr size="1">

		<div class="ui-grid ui-grid-responsive resultContainer">
			<div class="ui-grid-row">
				<div class="ui-grid-col-12">
					<logic:present name="btpExtratoList">
						<table data-name="primeuiDatatable" title="Extratos Bancários Cadastrados">
							<thead><tr>
								<th class="width40px">Cód.</th>
								<th class="width155px">Banco</th>
								<th class="width130px">Tipo</th>
								<th class="width80px">Compet.</th>
								<th class="right width120px">Saldo Inicial</th>
								<th class="right width120px">Saldo Final</th>
								<th class="width120px">Status</th>
								<th class="width40px" title="Editar"></th>
								<th class="width40px" title="Excluir"></th>
							</tr></thead>
							<tbody>
								<logic:iterate name="btpExtratoList" id="btpExtratoList">
									<tr>
										<td class="center"><bean:write name="btpExtratoList" property="extCodExtrato" /></td>
										<td><bean:write name="btpExtratoList" property="btpBanco.banTxtNome" /></td>
										<td><bean:write name="btpExtratoList" property="btpTipoExtrato.tieTxtNome" /></td>
										<td class="center"><bean:write name="btpExtratoList" property="extDatAno" />/<bean:write name="btpExtratoList" property="extDatMes" format="00" /></td>
										<td class="right">R$ <bean:write name="btpExtratoList" property="extVlrSaldoInicial" format="#,##0.00" /></td>
										<td class="right">R$ <bean:write name="btpExtratoList" property="extVlrSaldoFinal"  format="#,##0.00" /></td>
										<td class="center">
											<logic:equal name="btpExtratoList" property="map.extQtdPendente" value="0">Concluído</logic:equal>
											<logic:greaterThan name="btpExtratoList" property="map.extQtdPendente" value="0">
												Pendente (<bean:write name="btpExtratoList" property="map.extQtdPendente" />)
											</logic:greaterThan>
										</td>
										<td class="center">
											<a
												data-name="btnEditar"
												onclick="window.location='extrato.do?cmd=actShowImpoForm&btpExtrato.extCodExtrato=<bean:write name='btpExtratoList' property='extCodExtrato' />'">
											</a>
										</td>
										<td class="center">
											<a
												data-name="btnExcluir"
												onClick="confirmDelete(<bean:write name='btpExtratoList' property='extCodExtrato' />)">
											</a>
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