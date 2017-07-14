<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<script type="text/javascript" src="javascript/pages/jvsObjetivo.js"></script>
<script type="text/javascript" src="javascript/pages/jvsObjetivoOld.js"></script>
<script type="text/javascript" src="javascript/pages/jvsRegra.js"></script>

<html:form action="objetivo.do?cmd=actExecInsert">

	<div class="puiPanel" title="Consulta/Cadastro de Movimentos">

		<!-- Formulário de cadastro de objetivos -->
		<table style="width: 100%; border: none; font-size: 12px;">
			<tr>
				<td class="fontePadraoNegrito">Competência :</td>
				<td>
					<html:select property="btpObjetivo.map.numMes" onchange="btnFiltrarClick();">
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
					<html:text property="btpObjetivo.map.numAno" maxlength="4" size="4" styleClass="puiInputText" onchange="btnFiltrarClick();" />
				</td>
			</tr>

			<tr><td colspan="2"><hr /></td></tr>
	
			<tr>
				<td class="fontePadraoNegrito"><bean:message key="label.attribute.temTxtNome" /> :</td>
				<td>
					<html:select property="btpTemplate.temCodTemplate" onchange="comboTemCodTemplateChange($(this))">
						<html:option value="">Selecione...</html:option>
						<html:options collection="btpTemplateListCombo" property="temCodTemplate" labelProperty="temTxtNome" />
					</html:select>
					<input type="button" value="Novo" onclick="btnNovoTemplateClick();">
				</td>
			</tr>

			<tr>
				<td width="130" class="fontePadraoNegrito"><bean:message key="label.application.objetivo" /> :</td>
				<td>
					<html:hidden property="cmd" />
					<html:hidden property="btpObjetivo.objCodObjetivo" />
					<html:text property="btpObjetivo.objTxtDescricao" size="30" maxlength="30" onblur="copyObjetivoToItem();" />
				</td>
			</tr>

			<tr>
				<td class="fontePadraoNegrito"><bean:message key="label.attribute.estNomEstabelecimento" /> :</td>
				<td>
					<html:select property="btpObjetivo.btpEstabelecimento.estCodEstabelecimento">
						<html:option value="">Selecione...</html:option>
						<html:options collection="btpEstabelecimentoListCombo" property="estCodEstabelecimento" labelProperty="estNomEstabelecimento" />
					</html:select>
					<input type="button" value="+" onclick="showPopupCadastroEstabelecimento();">
				</td>
			</tr>

			<!--
				Formulário de cadastro de itens
			-->
			<tr>
				<td colspan="2">
					<div class="puiPanel" title="Itens">
						<div style="max-height: 140px; overflow-y: scroll;">
							<table id="frmObjetivoItem" style="font-size: 12px;">
								<thead style="text-align: left;">
									<tr>
										<th><label>No.</label></th>
										<th><label>Descrição</label></th>
										<th><label>Quantidade</label></th>
										<th><label>Vlr. Unitário</label></th>
										<th><label>Total</label></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<tr>
									  <td><input type="text" name="obiNumItem" style="width: 40px;" readonly="readonly" tabindex="-1" /></td>
									  <td><input type="text" name="obiTxtDescricao" style="width: 383px;" /></td>
									  <td><input type="text" name="obiNumQuantidade" class="fieldQuantity" onblur="jvsObjetivo.calculateObiVlrTotal($(this));" /></td>
									  <td><input type="text" name="obiVlrUnidade" class="fieldMoney" onblur="jvsObjetivo.calculateObiVlrTotal($(this));" /></td>
									  <td><input type="text" name="auxVlrTotal" class="fieldMoney" readonly="readonly" tabindex="-1" /></td>
									  <td><span class="ui-icon ui-icon-circle-minus" onclick="jvsObjetivo.btnRemoverItemClick($(this));"></span></td>
									</tr>
								</tbody>
							</table>
						</div>

						<hr style="border: solid 1px gray;" >
						<table style="width: 100%; font-size: 12px;">
							<tr>
								<td>
									<input
										type="button"
										name="btnAdicionarItem"
										value="Adicionar Item"
										onclick="jvsObjetivo.btnAdicionarItemClick();"
									/>
								</td>
								<td style="text-align: right;">
									<label>Total do Itens:</label>
									<input
										type="text"
										name="auxVlrSomaItem"
										style="width: 100px; text-align: right; margin-right: 39px;"
										readonly="readonly"
										tabindex="-1"
									/>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>

			<!--
				Formulário de cadastro de movimentos
			-->
			<tr>
				<td colspan="2">
					<div class="puiPanel" title="Parcelas">
						<div style="max-height: 140px; overflow-y: scroll;">
							<table id="frmMovimento" style="font-size: 12px;">
								<thead style="text-align: left;">
									<tr>
										<th><label>No.</label></th>
										<th><label>Valor</label></th>
										<th><label>Vencimento</label></th>
										<th><label>Pagamento</label></th>
										<th><label>Origem</label></th>
										<th><label>Destino</label></th>
										<th><label>Forma</label></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<tr>
									  <td><input type="text" name="movNumParcela" style="width: 40px;" readonly="readonly" tabindex="-1" /></td>
									  <td><input type="text" name="movVlrMovimentado" class="fieldMoney" onblur="jvsObjetivo.movVlrMovimentoBlur();" /></td>
									  <td><input type="text" name="movDatVencimento" class="fieldDate" onblur="copyVencimentoToPagamento($(this));" /></td>
									  <td><input type="text" name="movDatPagamento" class="fieldDate" /></td>
									  <td>
									  	<select name="conCodContaOrigem" style="width: 142px;">
									  		<option value="">Selecione...</option>
										  	<logic:iterate name="btpContaListCombo" id="btpContalv1">
										  		<option value="<bean:write name="btpContalv1" property="conCodConta" />"><bean:write name="btpContalv1" property="conNumNivel" /> - <bean:write name="btpContalv1" property="conTxtDescricao" /></option>
											  	<logic:iterate name="btpContalv1" property="btpContaList" id="btpContalv2">
											  		<option style="padding-left: 20px;" value="<bean:write name="btpContalv2" property="conCodConta" />"><bean:write name="btpContalv2" property="conNumNivel" /> - <bean:write name="btpContalv2" property="conTxtDescricao" /></option>
												  	<logic:iterate name="btpContalv2" property="btpContaList" id="btpContalv3">
												  		<option style="padding-left: 40px;" value="<bean:write name="btpContalv3" property="conCodConta" />"><bean:write name="btpContalv3" property="conNumNivel" /> - <bean:write name="btpContalv3" property="conTxtDescricao" /></option>
												  	</logic:iterate>
											  	</logic:iterate>
										  	</logic:iterate>
										  </select>
									  </td>
									  <td>
									  	<select name="conCodContaDestino" style="width: 142px;">
									  		<option value="">Selecione...</option>
										  	<logic:iterate name="btpContaListCombo" id="btpContalv1">
										  		<option value="<bean:write name="btpContalv1" property="conCodConta" />"><bean:write name="btpContalv1" property="conNumNivel" /> - <bean:write name="btpContalv1" property="conTxtDescricao" /></option>
											  	<logic:iterate name="btpContalv1" property="btpContaList" id="btpContalv2">
											  		<option style="padding-left: 20px;" value="<bean:write name="btpContalv2" property="conCodConta" />"><bean:write name="btpContalv2" property="conNumNivel" /> - <bean:write name="btpContalv2" property="conTxtDescricao" /></option>
												  	<logic:iterate name="btpContalv2" property="btpContaList" id="btpContalv3">
												  		<option style="padding-left: 40px;" value="<bean:write name="btpContalv3" property="conCodConta" />"><bean:write name="btpContalv3" property="conNumNivel" /> - <bean:write name="btpContalv3" property="conTxtDescricao" /></option>
												  	</logic:iterate>
											  	</logic:iterate>
										  	</logic:iterate>
										  </select>
									  </td>
									  <td>
									  	<select name="fopCodFormaPagamento" style="width: 142px;">
									  		<option value="">Selecione...</option>
										  	<logic:iterate id="btpFormaPagamento" name="btpFormaPagamentoListCombo">
										  		<option value="<bean:write name="btpFormaPagamento" property="fopCodFormaPagamento" />"><bean:write name="btpFormaPagamento" property="fopNomFormaPagamento" /></option>
										  	</logic:iterate>
										  </select>
									  </td>
									  <td><span class="ui-icon ui-icon-circle-minus" onclick="jvsObjetivo.btnRemoverParcelaClick($(this));"></span></td>
									</tr>
								</tbody>
							</table>

						</div>

						<hr style="border: solid 1px gray;" >
						<table style="width: 100%; font-size: 12px;">
							<tr>
								<td>
									<input
										type="button"
										name="btnAdicionarParcela"
										value="Adicionar Parcela"
										onclick="jvsObjetivo.btnAdicionarParcelaClick();"
									/>
								</td>
								<td style="text-align: right;">
									<label>Total das Parcelas:</label>
									<input
										type="text"
										name="auxVlrSomaParcela"
										style="width: 100px; text-align: right; margin-right: 39px;"
										readonly="readonly"
										tabindex="-1"
									/>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>

		</table>

		<!-- Botões de opção do formulário principal -->
		<hr size="1">
		<table style="width: 100%; font-size: 12px; border: none;">
			<tr>
				<td align="left" nowrap>
					<input
						type="button"
						name="btnConfirmar"
						value="<bean:message key='label.button.confirm' />"
						onclick="jvsObjetivo.execInsertObjetivo();"
					/>
					<input
						type="button"
						name="btnAlterar"
						value="<bean:message key='label.button.modify' />"
						onclick="jvsObjetivo.execUpdateObjetivo();"
						style="display: none;"
					/>
					<input
						type="button"
						name="btnCancelar"
						value="<bean:message key='label.button.cancel' />"
						onclick="btnCancelarClick();"
						style="display: none;"
					/>
					<input
						type="button"
						name="btnVoltar"
						value="<bean:message key='label.button.back' />"
						onClick="history.go(-1)"
					/>
					<input
						type="button"
						name="btnFiltrar"
						value="<bean:message key='label.button.filter' />"
						onClick="btnFiltrarClick();"
					/>
				</td>
			</tr>
		</table>
	</div>

</html:form>

<script type="text/javascript">
	var map = {
		numMes: $("[name='btpObjetivo.map.numMes']").val(),
		numAno: $("[name='btpObjetivo.map.numAno']").val()
	};

	var btpObjetivo = {
		map: map
	};

	initPage();
	execSelectObjetivo(btpObjetivo);

	jvsObjetivo.calculateMovNumParcela();
	jvsObjetivo.calculateObiNumItem();
	jvsObjetivo.adjustContaCombo(null);
</script>