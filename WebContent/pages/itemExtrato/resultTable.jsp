<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<!-- Tabela de Resultados da consulta da entidade BtpItemExtrato -->
<logic:present name="btpExtrato" property="btpItemExtratoList">
	<logic:iterate name="btpExtrato" property="btpItemExtratoList" id="btpItemExtrato">
		<table
			data-name="extratoContainer"
			style="width: 100%; margin-bottom: 5px; border-collapse: collapse;"
			border="1"
			onmouseover="lightOn(this)"
			onmouseout="lightOff(this)">

			<tr class="fontePadraoNegrito ui-widget-header">
				<td style="width: 25px;">&nbsp</td>
				<td style="width: 60px;">Data</td>
				<td>Descrição</td>
				<td style="width: 120px; text-align: right;">Número</td>
				<td style="width: 60px; text-align: right;">Valor</td>
				<td style="width: 1px;">Tipo</td>
			</tr>
			<tr class="fontePadrao">
				<td rowspan="3" style="text-align: center;">
					<logic:equal name="btpItemExtrato" property="iteFlgExportado" value="false">
						<input type="checkbox" name="chxExport" />
						<input
							type="hidden"
							name="btpItemExtrato.iteCodItemExtrato"
							value="<bean:write name="btpItemExtrato" property="iteCodItemExtrato" />"
						/>
					</logic:equal>
					<logic:equal name="btpItemExtrato" property="iteFlgExportado" value="true">
						<img title="Item de extrato já exportado" src="images/icon-016x016-checked.png">
					</logic:equal>
				</td>
				<td>
					<bean:write name="btpItemExtrato" property="iteDatMovimento" format="dd/MM/yyyy" />
					<input
						type="hidden"
						name="btpItemExtrato.iteDatMovimento"
						value="<bean:write name="btpItemExtrato" property="iteDatMovimento" format="dd/MM/yyyy" />"
					/>
				</td>
				<td>
					<logic:equal name="btpItemExtrato" property="iteFlgExportado" value="false">
						<input
							style="width: 100%;"
							type="text"
							name="btpItemExtrato.iteTxtDescricao"
							value="<bean:write name="btpItemExtrato" property="iteTxtDescricao" />"
						/>
					</logic:equal>
					<logic:equal name="btpItemExtrato" property="iteFlgExportado" value="true">
						<bean:write name="btpItemExtrato" property="iteTxtDescricao" />
					</logic:equal>
				</td>
				<td style="text-align: right;"><bean:write name="btpItemExtrato" property="iteNumDocumento" /></td>
				<td style="text-align: right;">
					<bean:write name="btpItemExtrato" property="iteVlrMovimento" format="#,##0.00" locale="userCurrentLocale" />
					<input
						type="hidden"
						name="btpItemExtrato.iteVlrMovimento"
						value="<bean:write name="btpItemExtrato" property="iteVlrMovimento" />"
					/>
				</td>
				<td style="text-align: center;"><bean:write name="btpItemExtrato" property="iteTxtTipo" /></td>
			</tr>
			<tr class="fontePadrao">
				<td colspan="5">
					<logic:equal name="btpItemExtrato" property="iteFlgExportado" value="false">
						<div style="float: left; width: 49%">
							<span>Estabelecimento</span>
							<select name="estCodEstabelecimento" style="width: 98%;">
								<option value="">Selecione...</option>
								<logic:iterate name="btpEstabelecimentoListCombo" id="btpEstAux">
									<option value="<bean:write name="btpEstAux" property="estCodEstabelecimento" />"><bean:write name="btpEstAux" property="estNomEstabelecimento" /></option>
								</logic:iterate>
							</select>
						</div>

						<div style="float: left; width: 49%">
							<span>Forma Pagamento</span>
					  	<select name="fopCodFormaPagamento" style="width: 98%;">
					  		<option value="">Selecione...</option>
						  	<logic:iterate id="btpFormaPagamento" name="btpFormaPagamentoListCombo">
						  		<option value="<bean:write name="btpFormaPagamento" property="fopCodFormaPagamento" />"><bean:write name="btpFormaPagamento" property="fopNomFormaPagamento" /></option>
						  	</logic:iterate>
						  </select>
						</div>

						<div style="float: left; width: 49%">
							<span>Origem</span>
					  	<select name="conCodContaOrigem" style="width: 98%;">
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
						</div>

						<div style="float: left; width: 49%">
							<span>Destino</span>
					  	<select name="conCodContaDestino" style="width: 98%;">
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
						</div>
					</logic:equal>
				</td>
			</tr>

			<logic:equal name="btpItemExtrato" property="iteFlgExportado" value="false">
				<tr>
					<td colspan="5">
						<a
							href="javascript:void(0);"
							style="color:blue; text-decoration: underline;"
							onclick="jvsExtrato.execUpdateItemExtratoImported(<bean:write name="btpItemExtrato" property="iteCodItemExtrato" />);"
							tabindex="-1">

							Marcar este item como exportado sem gerar um novo movimento.
						</a>
					</td>
				</tr>
				<logic:notEqual name="btpItemExtrato" property="map.auxNumOccurrenceValor" value="0">
					<tr>
						<td colspan="5">
							<div class="fontePadraoNegrito">Observações:</div>
							<div style="color: red;">Movimentos coincidentes para este mesmo valor: <bean:write name="btpItemExtrato" property="map.auxNumOccurrenceValor" /></div>
							<div style="color: red;">Movimentos coincidentes para esta mesma data e mesmo valor: <bean:write name="btpItemExtrato" property="map.auxNumOccurrenceDataValor" /></div>
						</td>
					</tr>
				</logic:notEqual>
			</logic:equal>

		</table>
	</logic:iterate>

	<script type="text/javascript">
		jvsExtrato.adjustContaCombo();
	</script>

</logic:present>