<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<script type="text/javascript" src="dwr/interface/DwrResumo.js"></script>

<script type="text/javascript" src="modules/resumo/resumo-main.js"></script>

<html:form action="resumo.do?cmd=actShowMainPage">

	<html:hidden property="cmd" />

	<div class="puipanel" title="Resumo">
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

		<div class="ui-grid ui-grid-responsive resultContainer">
			<div class="ui-grid-row">
				<div class="ui-grid-col-12">
					<table data-name="primeuiDatatable" title="Resumo Mensal por Conta">
						<thead><tr>
							<th class="left">Conta</th>
							<th class="right">Saldo Inicial</th>
							<th class="right">Vlr. Entrada</th>
							<th class="right">Vlr. Saída</th>
							<th class="right">Economia</th>
							<th class="right">Saldo Final</th>
						</tr></thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div><br /> 

		<div class="ui-grid ui-grid-responsive">
			<div class="ui-grid-row">
				<div class="ui-grid-col-6">
					<div id="divGraficoReceitaVsDespesa"></div>
				</div><br />
				<div class="ui-grid-col-6">
					<ul class="puicarousel" data-headertext="Saída por Categoria" data-numvisible="1">
						<li id="divGraficoDespesaPorCategoria"></li>
					</ul>
				</div>
			</div><br />
			<div class="ui-grid-row">
				<div class="ui-grid-col-12">
					<div class="puipanel" data-toggleable="true" title="Histórico por Categoria">
				  	<select name="conCodConta" class="puicombobox width100pc">
				  		<option value="">Selecione...</option>
					  	<logic:iterate name="btpContaListCombo" id="btpContalv1">
					  		<option disabled="disabled" value="<bean:write name="btpContalv1" property="conCodConta" />">
					  			<bean:write name="btpContalv1" property="conNumNivel" /> - <bean:write name="btpContalv1" property="conTxtDescricao" />
					  		</option>
						  	<logic:iterate name="btpContalv1" property="btpContaList" id="btpContalv2">
						  		<option style="padding-left: 20px;" value="<bean:write name="btpContalv2" property="conCodConta" />">
						  			<bean:write name="btpContalv2" property="conNumNivel" /> - <bean:write name="btpContalv2" property="conTxtDescricao" />
						  		</option>
							  	<logic:iterate name="btpContalv2" property="btpContaList" id="btpContalv3">
							  		<option disabled="disabled" style="padding-left: 40px;" value="<bean:write name="btpContalv3" property="conCodConta" />"><bean:write name="btpContalv3" property="conNumNivel" /> - <bean:write name="btpContalv3" property="conTxtDescricao" /></option>
							  	</logic:iterate>
						  	</logic:iterate>
					  	</logic:iterate>
					  </select>
						<div id="divGraficoLinhaDoTempoDeCategoria"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

</html:form>