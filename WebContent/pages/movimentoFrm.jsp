<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<script type="text/javascript" src="javascript/pages/jvsMovimento.js"></script>

<script type="text/javascript" src="dwr/interface/MovimentoRemote.js"></script>
<script type="text/javascript" src="dwr/engine.js"></script>

<link href="styles/calendario.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="javascript/calendario.js"></script>

<script type="text/javascript" src="javascript/maskFormatBraDate.js"></script>
<script type="text/javascript" src="javascript/maskFormatBraMoney.js"></script>
<script type="text/javascript" src="javascript/maskFloatToBraMoney.js"></script>
<script type="text/javascript" src="pages/script/movimentoJvs.js"></script>

<bean:define id="cmd_"><%=request.getParameter("cmd")%></bean:define>

<html:form action="movimento.do?cmd=add" focus="movimentoBean.orcamentoBean.orcCodOrcamento">

	<!-- Formulário de cadastro de movimentos -->
	<table style="width: 100%;">
		<tr>
			<td colspan="2"><bean:message key="label.application.movimento.up" /> - <bean:message key="label.application.cadastro.up" /></td>
		</tr>
		<tr>
			<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.orcNomOrcamento" /> :</td>
			<td>
				<html:hidden property="movimentoBean.movCodMovimento" />
				<html:select property="movimentoBean.orcamentoBean.orcCodOrcamento">
					<html:option value=""><bean:message key="label.application.selecionar"/></html:option>
					<html:options collection="orcamentoListCombo" property="orcCodOrcamento" labelProperty="orcDatCompetencia" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.renNomRendimento" /> :</td>
			<td>
				<html:select property="movimentoBean.btpRendimento.renCodRendimento">
					<html:option value=""><bean:message key="label.application.selecionar"/></html:option>
					<html:options collection="rendimentoListCombo" property="renCodRendimento" labelProperty="renNomRendimento" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.timNomTipoMovimento" /> :</td>
			<td>
				<html:select property="movimentoBean.tipoMovimentoBean.timCodTipoMovimento">
					<html:option value=""><bean:message key="label.application.selecionar"/></html:option>
					<html:options collection="tipoMovimentoListCombo" property="timCodTipoMovimento" labelProperty="timNomTipoMovimento" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.estNomEstabelecimento" /> :</td>
			<td>
				<html:select property="movimentoBean.estabelecimentoBean.estCodEstabelecimento">
					<html:option value=""><bean:message key="label.application.selecionar"/></html:option>
					<html:options collection="estabelecimentoListCombo" property="estCodEstabelecimento" labelProperty="estNomEstabelecimento" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.catNomCategoria" /> :</td>
			<td>
				<html:select property="movimentoBean.categoriaBean.categoriaBean.catCodCategoria" onchange="lodComboSubCategoria();">
					<html:option value=""><bean:message key="label.application.selecionar" /></html:option>
					<html:options collection="categoriaListCombo" property="catCodCategoria" labelProperty="catNomCategoria" />
				</html:select>

				<div id="comboSubCategoria">
					<html:select property="movimentoBean.categoriaBean.catCodCategoria">
						<html:option value=""><bean:message key="label.application.selecionar" /></html:option>
						<html:options collection="subCategoriaListCombo" property="catCodCategoria" labelProperty="catNomCategoria" />
					</html:select>
				</div>
			</td>
		</tr>
		<tr>
			<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.movDscMovimento" /></td>
			<td><html:text property="movimentoBean.movDscMovimento" size="70" maxlength="150" /></td>
		</tr>
		<tr>
			<td width="130" class="fontePadraoNegrito"><bean:message key="label.attribute.movDatMovimento" /></td>
			<td>
				<html:text property="movDatMovimento" size="10" styleId="movDatMovimento" onkeypress="maskFormatBraDate(this);" />
				<img src="images/calendario.jpg" id="btnDatMovimento">
				<script type="text/javascript">
         	Calendar.setup({
           inputField : "movDatMovimento", // id of the input field
           ifFormat : "%d/%m/%Y", // format of the input field
           button : "btnDatMovimento", // trigger for the calendar (button ID)
           align : "br", // alignment (defaults to "Bl")
           singleClick : true });
				</script>
			</td>
		</tr>
	</table>

	<!-- Botões de opção do formulário principal -->
	<hr size="1">
	<table style="width: 100%;">
		<tr>
			<td align="left" nowrap>
				<logic:notEqual name="cmd_" value="mtn">
					<input type="submit" name="btnConfirmar" value="<bean:message key='label.button.confirm' />" />
					<input type="button" name="btnVoltar" value="<bean:message key='label.button.back' />" onClick="history.go(-1)">
					<input type="button" name="btnFiltrar" value="<bean:message key='label.button.filter' />" onClick="document.forms[0].action='movimento.do?cmd=def&sqlOrdem=1'; document.forms[0].submit();">
				</logic:notEqual>
				<logic:equal name="cmd_" value="mtn">
					<script type="text/javascript">document.forms[0].action = "movimento.do?cmd=upd";</script>
					<input type="submit" name="btnAlterar" value="<bean:message key='label.button.modify' />" />
					<input type="button" name="btnCancelar" value="<bean:message key='label.button.cancel' />" onClick="history.go(-1)">
				</logic:equal><br>
			</td>
		</tr>
	</table>

</html:form>

<logic:equal name="isFormVisible" value="true">

	<html:form action="itemMovimento.do?cmd=add">
	
		<table style="width: 100%;">
			<tr valign="top">
				<td width="20%%" class="fontePadraoNegrito"><bean:message key="label.attribute.itmNomItem" /></td>
				<td class="fontePadraoNegrito"><bean:message key="label.attribute.itmVlrUnitario" /></td>
				<td class="fontePadraoNegrito"><bean:message key="label.attribute.itmQtdItem" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr valign="top">
				<td><html:text property="itemMovimentoBean.itmNomItem" size="50" maxlength="100" /></td>
				<td><html:text property="itemMovimentoBean.itmVlrUnitario" value="0,00" size="15" maxlength="15" onkeypress="return maskFormatBraMoney(this, '.', ',', event);" /></td>
				<td><html:text property="itemMovimentoBean.itmQtdItem" value="0,00" size="15" maxlength="15" onkeypress="return maskFormatBraMoney(this, '.', ',', event);" /></td>
				<td>
					<input type="button" value="Confirmar" onclick="addItemMovimento();">
					<input type="reset" value="Limpar">
				</td>
			</tr>
			<tr>
				<td id="gridItemMovimento" colspan="4" align="center"></td>
			</tr>
		</table>
		
		<script type="text/javascript">
			lodItemMovimento();
		</script>
	
	</html:form>

	<html:form action="parcela.do?cmd=add">
	
		<table style="width: 100%;">
			<tr valign="top">
				<td class="fontePadraoNegrito">
					Compra à Vista <input type="checkbox" name="flgParcelaUnica" onclick="setParcelaUnica();">
				</td>
			</tr>
			<tr valign="top">
				<td width="20%" class="fontePadraoNegrito"><bean:message key="label.attribute.parNumParcela" /></td>
				<td class="fontePadraoNegrito"><bean:message key="label.attribute.parDatVencimento" /></td>
				<td class="fontePadraoNegrito"><bean:message key="label.attribute.parDatPagamento" /></td>
				<td class="fontePadraoNegrito"><bean:message key="label.attribute.parVlrParcela" /></td>
				<td class="fontePadraoNegrito"><bean:message key="label.attribute.fopNomFormaPagamento" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr valign="top">
				<td><html:text property="parcelaBean.parNumParcela" size="2" maxlength="2" /></td>
				<td>
					<html:text property="parcelaBean.parDatVencimento" size="10" styleId="parDatVencimento" onkeypress="maskFormatBraDate(this);" />
					<img src="images/calendario.jpg" id="btnDatVencimento">
					<script type="text/javascript">
	         	Calendar.setup({
	           inputField : "parDatVencimento", // id of the input field
	           ifFormat : "%d/%m/%Y", // format of the input field
	           button : "btnDatVencimento", // trigger for the calendar (button ID)
	           align : "br", // alignment (defaults to "Bl")
	           singleClick : true });
					</script>
				</td>
				<td>
					<html:text property="parcelaBean.parDatPagamento" size="10" styleId="parDatPagamento" onkeypress="maskFormatBraDate(this);" />
					<img src="images/calendario.jpg" id="btnDatPagamento">
					<script type="text/javascript">
	         	Calendar.setup({
	           inputField : "parDatPagamento", // id of the input field
	           ifFormat : "%d/%m/%Y", // format of the input field
	           button : "btnDatPagamento", // trigger for the calendar (button ID)
	           align : "br", // alignment (defaults to "Bl")
	           singleClick : true });
					</script>
				</td>
				<td><html:text property="parcelaBean.parVlrParcela" value="0,00" size="15" maxlength="15" onkeypress="return maskFormatBraMoney(this, '.', ',', event);" /></td>
				<td>
					<html:select property="parcelaBean.formaPagamentoBean.fopCodFormaPagamento">
						<html:option value=""><bean:message key="label.application.selecionar"/></html:option>
						<html:options collection="formaPagamentoListCombo" property="fopCodFormaPagamento" labelProperty="fopNomFormaPagamento" />
					</html:select>
				</td>
				<td>
					<input type="button" value="Confirmar" onclick="addParcela();">
					<input type="reset" value="Limpar">
				</td>
			</tr>
			<tr>
				<td id="gridParcela" colspan="6" align="center"></td>
			</tr>
		</table>
		
		<script type="text/javascript">
			lodParcela();
		</script>
	
	</html:form>

</logic:equal>