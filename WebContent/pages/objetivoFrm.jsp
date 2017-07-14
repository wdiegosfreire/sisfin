<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<script type="text/javascript" src="javascript/pages/jvsObjetivo.js"></script>

<html:form action="objetivo.do?cmd=actExecInsert">

<html:hidden property="cmd" />
<html:hidden property="btpObjetivo.objCodObjetivo" />

<table style="width: 100%; border: none; font-size: 12px;">
	<tr>
		<td width="130" class="fontePadraoNegrito"><bean:message key="label.application.objetivo" /> :</td>
		<td><html:text property="btpObjetivo.objTxtDescricao" style="width: 60%;" /></td>
	</tr>

	<tr>
		<td class="fontePadraoNegrito"><bean:message key="label.attribute.estNomEstabelecimento" /> :</td>
		<td>
			<html:select property="btpObjetivo.btpEstabelecimento.estCodEstabelecimento">
				<html:option value="">Selecione...</html:option>
				<html:options collection="btpEstabelecimentoListCombo" property="estCodEstabelecimento" labelProperty="estNomEstabelecimento" />
			</html:select>
		</td>
	</tr>
</table>

<div
	class="puidynamicform"
	title="Cadastro de Itens da Despesa"
	style="width: 100%; height: 80px; font-size: 12px;"
	data-allowswap="true"
	data-insertbuttonlabel="Confirmar"
	data-cancelbuttonlabel="Cancelar">

	<div data-required="true" data-hiddenname="obiNumItem" style="width: 40px;">
		<label>No.</label>
		<input type="text" name="obiNumItemForm" style="width: 100%;">
	</div>
	<div data-required="true" data-hiddenname="obiTxtDescricao">
		<label>Descrição</label>
		<input type="text" name="obiTxtDescricaoForm" style="width: 100%;">
	</div>
	<div data-required="true" data-hiddenname="obiNumQuantidade" style="width: 100px;">
		<label>Quantidade</label>
		<input type="text" name="obiNumQuantidadeForm" class="fieldQuantity" onblur="jvsObjetivo.calculateTotalValue();">
	</div>
	<div data-required="true" data-hiddenname="obiVlrUnidade" style="width: 100px;">
		<label>V. Unitário</label>
		<input type="text" name="obiVlrUnidadeForm" style="width: 100%;" class="fieldMoney" onblur="jvsObjetivo.calculateTotalValue();">
	</div>
	<div data-required="true" data-hiddenname="auxVlrTotal" style="width: 100px;">
		<label>V. Total</label>
		<input type="text" name="auxVlrTotalForm" style="width: 100%;" class="fieldMoney" readonly="readonly">
	</div>
</div>

<div
	class="puidynamicform"
	title="Parcelas da Despesa"
	style="width: 100%; height: 80px; font-size: 12px;"
	data-allowswap="true"
	data-insertbuttonlabel="Confirmar"
	data-cancelbuttonlabel="Cancelar">

	<div data-required="true" data-hiddenname="movNumParcela" style="width: 40px;">
		<label>No.</label>
		<input type="text" name="movNumParcelaForm" style="width: 100%;">
	</div>
	<div data-required="true" data-hiddenname="movVlrMovimentado" style="width: 100px;">
		<label>Valor</label>
		<input type="text" name="movVlrMovimentadoForm" class="fieldMoney" style="width: 100%;">
	</div>
	<div data-required="true" data-hiddenname="movDatVencimento" style="width: 75px;">
		<label>Vencimento</label>
		<input type="text" name="movDatVencimentoForm" class="fieldDate" style="width: 100%;" onblur="jvsObjetivo.copyVencimentoToPagamento();">
	</div>
	<div data-required="true" data-hiddenname="movDatPagamento" style="width: 75px;">
		<label>Pagamento</label>
		<input type="text" name="movDatPagamentoForm" class="fieldDate" style="width: 100%;">
	</div>
	<div data-required="true" data-hiddenname="conCodContaOrigem">
		<label>Origem</label>
  	<select name="conCodContaOrigemForm" style="width: 100%;">
			<option value="">Selecione...</option>
	  	<logic:iterate name="btpContaOrigemListCombo" id="btpContalv1">
	  		<option disabled="disabled" value="<bean:write name="btpContalv1" property="conCodConta" />"><bean:write name="btpContalv1" property="conNumNivel" /> - <bean:write name="btpContalv1" property="conTxtDescricao" /></option>
		  	<logic:iterate name="btpContalv1" property="btpContaList" id="btpContalv2">
		  		<option disabled="disabled" style="padding-left: 20px;" value="<bean:write name="btpContalv2" property="conCodConta" />"><bean:write name="btpContalv2" property="conNumNivel" /> - <bean:write name="btpContalv2" property="conTxtDescricao" /></option>
			  	<logic:iterate name="btpContalv2" property="btpContaList" id="btpContalv3">
			  		<option style="padding-left: 40px;" value="<bean:write name="btpContalv3" property="conCodConta" />"><bean:write name="btpContalv3" property="conNumNivel" /> - <bean:write name="btpContalv3" property="conTxtDescricao" /></option>
			  	</logic:iterate>
		  	</logic:iterate>
	  	</logic:iterate>
		</select>
	</div>
	<div data-required="true" data-hiddenname="conCodContaDestino">
		<label>Destino</label>
  	<select name="conCodContaDestinoForm" style="width: 100%;">
			<option value="">Selecione...</option>
	  	<logic:iterate name="btpContaDestinoListCombo" id="btpContalv1">
	  		<option disabled="disabled" value="<bean:write name="btpContalv1" property="conCodConta" />"><bean:write name="btpContalv1" property="conNumNivel" /> - <bean:write name="btpContalv1" property="conTxtDescricao" /></option>
		  	<logic:iterate name="btpContalv1" property="btpContaList" id="btpContalv2">
		  		<option disabled="disabled" style="padding-left: 20px;" value="<bean:write name="btpContalv2" property="conCodConta" />"><bean:write name="btpContalv2" property="conNumNivel" /> - <bean:write name="btpContalv2" property="conTxtDescricao" /></option>
			  	<logic:iterate name="btpContalv2" property="btpContaList" id="btpContalv3">
			  		<option style="padding-left: 40px;" value="<bean:write name="btpContalv3" property="conCodConta" />"><bean:write name="btpContalv3" property="conNumNivel" /> - <bean:write name="btpContalv3" property="conTxtDescricao" /></option>
			  	</logic:iterate>
		  	</logic:iterate>
	  	</logic:iterate>
		</select>
	</div>
	<div data-required="true" data-hiddenname="fopCodFormaPagamento" style="width: 100px;">
		<label>Forma</label>
  	<select name="fopCodFormaPagamentoForm" style="width: 100%;">
  		<option value="">Selecione...</option>
	  	<logic:iterate id="btpFormaPagamento" name="btpFormaPagamentoListCombo">
	  		<option value="<bean:write name="btpFormaPagamento" property="fopCodFormaPagamento" />"><bean:write name="btpFormaPagamento" property="fopNomFormaPagamento" /></option>
	  	</logic:iterate>
	  </select>
	</div>
</div>

</html:form>