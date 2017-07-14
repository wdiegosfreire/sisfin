<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<script type="text/javascript" src="dwr/interface/DwrPainelMovimento.js"></script>
<script type="text/javascript" src="javascript/pages/jvsPainelMovimento.js"></script>

<html:form action="painelMovimento.do?cmd=actShowMainPage">
	<div style="width: 100%;">
		<div class="puipanel" title="Competência" style="float: left; width: 99%; margin: 0px 0px 5px 0px;">
			<html:select property="btpObjetivo.map.numMes" onchange="jvsPainelMovimento.loadMainPage();" styleClass="puicomboboxfilter">
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
			<html:text
				property="btpObjetivo.map.numAno"
				maxlength="4"
				size="4"
				styleClass="puiInputText"
				onchange="jvsPainelMovimento.loadMainPage();"
			/>

			<input type="button" value="Adicionar" onclick="jvsPainelMovimento.btnIncluirClick();" />
		</div>

		<div class="puipanel" title="Movimentação do Mês" style="float: left; width: 99%; margin: 0px 5px 5px 0px;">
			<div style="font-size: 12px;" id="tblMovimentoMes"></div>
		</div>
	</div>

	<script type="text/javascript">
		jvsPainelMovimento.loadMainPage();
	</script>

</html:form>