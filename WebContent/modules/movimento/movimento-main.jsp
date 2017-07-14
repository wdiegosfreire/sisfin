<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<script type="text/javascript" src="dwr/interface/DwrMovimentoNew.js"></script>
<script type="text/javascript" src="dwr/interface/DwrObjetivo.js"></script>

<script type="text/javascript" src="modules/movimento/movimento-main.js"></script>
<script type="text/javascript" src="modules/movimento/movimento-form.js"></script>

<html:form action="movimentoNew.do?cmd=actShowMainPage">

	<html:hidden property="cmd" />

	<div class="puipanel" title="Movimentos">
		<table id="periodContainer" class="width100pc" style="padding-bottom: 8px;">
			<tr>
				<td>Mês</td>
				<td>
					<html:select property="btpCompetencia.comDatMes" styleClass="puicombobox">
						<html:option value="01">Janeiro</html:option>
						<html:option value="02">Fevereiro</html:option>
						<html:option value="03">Março</html:option>
						<html:option value="04">Abril</html:option>
						<html:option value="05">Maio</html:option>
						<html:option value="06">Junho</html:option>
						<html:option value="07">Julho</html:option>
						<html:option value="08">Agosto</html:option>
						<html:option value="09">Setembro</html:option>
						<html:option value="10">Outubro</html:option>
						<html:option value="11">Novembro</html:option>
						<html:option value="12">Dezembro</html:option>
					</html:select>
				</td>
				<td>Ano</td>
				<td><html:text property="btpCompetencia.comDatAno" styleClass="fieldNumber width125px" /></td>
			</tr>
		</table>

		<div class="puipanel" title="Filtros" data-toggleable="true" data-collapsed="true" data-name="frmFiltro">
			<div class="ui-grid ui-grid-responsive fieldContainer">
				<div class="ui-grid-row">
					<div class="ui-grid-col-3">Escopo</div>
					<div class="ui-grid-col-9">
						<select name="cbxEscopoConsulta" class="puicombobox">
							<option value="mes">Mês</option>
							<option value="ano">Ano</option>
							<option value="tudo">Tudo</option>
						</select>
					</div>
				</div>

				<div class="ui-grid-row">
					<div class="ui-grid-col-3">Vencimento</div>
					<div class="ui-grid-col-9">
						<div>
							<input type="text" name="movDatVencimentoMin" class="formFieldDate" style="max-width: 49%; box-sizing: border-box;" />
							<input type="text" name="movDatVencimentoMax" class="formFieldDate" style="max-width: 49%; box-sizing: border-box;" />
						</div>
					</div>
				</div>

				<div class="ui-grid-row">
					<div class="ui-grid-col-3">Pagamento</div>
					<div class="ui-grid-col-9">
						<div>
							<input type="text" name="movDatPagamentoMin" class="formFieldDate" style="max-width: 49%; box-sizing: border-box;" />
							<input type="text" name="movDatPagamentoMax" class="formFieldDate" style="max-width: 49%; box-sizing: border-box;" />
						</div>
					</div>
				</div>
				<div class="ui-grid-row">
					<div class="ui-grid-col-3">Descrição</div>
					<div class="ui-grid-col-9"><input type="text" name="objTxtDescricao" style="width: 99% !important;" /></div>
				</div>
				<div class="ui-grid-row">
					<div class="ui-grid-col-3">Valor</div>
					<div class="ui-grid-col-9">
						<div>
							<input type="text" name="movVlrMovimentoMin" class="fieldMoney" style="max-width: 49%; box-sizing: border-box;" />
							<input type="text" name="movVlrMovimentoMax" class="fieldMoney" style="max-width: 49%; box-sizing: border-box;" />
						</div>
					</div>
				</div>
				<div class="ui-grid-row">
					<div class="ui-grid-col-3">Origem</div>
					<div class="ui-grid-col-9">
						<select name="conCodContaOrigem" class="puicomboboxfilter">
				  		<option value="">Selecione...</option>
					  	<logic:iterate name="btpContaListCombo" id="btpContalv1">
					  		<option value="<bean:write name="btpContalv1" property="conCodConta" />">
					  			<bean:write name="btpContalv1" property="conNumNivel" /> - <bean:write name="btpContalv1" property="conTxtDescricao" />
					  		</option>
						  	<logic:iterate name="btpContalv1" property="btpContaList" id="btpContalv2">
						  		<option style="padding-left: 20px;" value="<bean:write name="btpContalv2" property="conCodConta" />">
						  			<bean:write name="btpContalv2" property="conNumNivel" /> - <bean:write name="btpContalv2" property="conTxtDescricao" />
						  		</option>
							  	<logic:iterate name="btpContalv2" property="btpContaList" id="btpContalv3">
							  		<option style="padding-left: 40px;" value="<bean:write name="btpContalv3" property="conCodConta" />"><bean:write name="btpContalv3" property="conNumNivel" /> - <bean:write name="btpContalv3" property="conTxtDescricao" /></option>
							  	</logic:iterate>
						  	</logic:iterate>
					  	</logic:iterate>
					  </select>
					</div>
				</div>
				<div class="ui-grid-row">
					<div class="ui-grid-col-3">Destino</div>
					<div class="ui-grid-col-9">
						<select name="conCodContaDestino" class="puicomboboxfilter">
				  		<option value="">Selecione...</option>
					  	<logic:iterate name="btpContaListCombo" id="btpContalv1">
					  		<option value="<bean:write name="btpContalv1" property="conCodConta" />">
					  			<bean:write name="btpContalv1" property="conNumNivel" /> - <bean:write name="btpContalv1" property="conTxtDescricao" />
					  		</option>
						  	<logic:iterate name="btpContalv1" property="btpContaList" id="btpContalv2">
						  		<option value="<bean:write name="btpContalv2" property="conCodConta" />">
						  			<bean:write name="btpContalv2" property="conNumNivel" /> - <bean:write name="btpContalv2" property="conTxtDescricao" />
						  		</option>
							  	<logic:iterate name="btpContalv2" property="btpContaList" id="btpContalv3">
							  		<option value="<bean:write name="btpContalv3" property="conCodConta" />"><bean:write name="btpContalv3" property="conNumNivel" /> - <bean:write name="btpContalv3" property="conTxtDescricao" /></option>
							  	</logic:iterate>
						  	</logic:iterate>
					  	</logic:iterate>
					  </select>
					</div>
				</div>

				<div class="ui-grid-row">
					<div class="ui-grid-col-3">Estabelecimento</div>
					<div class="ui-grid-col-9">
						<select name="estCodEstabelecimento" class="puicomboboxfilter">
				  		<option value="">Selecione...</option>
				  		<logic:iterate name="btpEstabelecimentoListCombo" id="btpEstabelecimento">
				  			<option value="<bean:write name="btpEstabelecimento" property="estCodEstabelecimento" />"><bean:write name="btpEstabelecimento" property="estNomEstabelecimento" /></option>
				  		</logic:iterate>
						</select>
					</div>
				</div>

				<div class="ui-grid-row">
					<div class="ui-grid-col-3">Forma de Pagamento</div>
					<div class="ui-grid-col-9">
						<select name="fopCodFormaPagamento" class="puicombobox">
				  		<option value="">Selecione...</option>
				  		<logic:iterate name="btpFormaPagamentoListCombo" id="btpFormaPagamento">
				  			<option value="<bean:write name="btpFormaPagamento" property="fopCodFormaPagamento" />"><bean:write name="btpFormaPagamento" property="fopNomFormaPagamento" /></option>
				  		</logic:iterate>
						</select>
					</div>
				</div>

			</div>

			<!-- Botões de opção do formulário pesquisa -->
			<div class="ui-grid ui-grid-responsive buttonContainer">
				<div class="ui-grid-row buttonContainer">
					<div class="ui-grid-col-6"><input type="button" name="btnConsultar" /></div>
					<div class="ui-grid-col-6"><input type="button" name="btnLimparConsulta"></div>
				</div>
			</div>
		</div><br />

		<div class="ui-grid ui-grid-responsive resultContainer">
			<div class="ui-grid-row">
				<div class="ui-grid-col-12">
					<table data-name="primeuiDatatable" title="Movimentos do Período">
						<thead><tr>
							<th class="width120px">Vencimento</th>
							<th class="width120px">Pagamento</th>
							<th class="width150px left">Origem</th>
							<th class="width150px left">Destino</th>
							<th class="width150px left">Estabelecimento</th>
							<th class="width150px left">Pagamento</th>
							<th class="width150px left">Descrição</th>
							<th class="width100px">Parcela</th>
							<th class="right width100px">Valor</th>
							<th class="width40px" title="Editar"></th>
							<th class="width40px" title="Excluir"></th>
						</tr></thead>
						<tbody>
						</tbody>
					</table>
					<div id="qtdTotalMovimento"></div>
				</div>
			</div>
		</div> 
	</div>

</html:form>