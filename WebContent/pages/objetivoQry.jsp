<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<!-- Lista de bandas cadastradas -->
<div class="puiPanel" title="Listagem de Movimentações do Mês">

	<table border="1" id="tblObjetivo" style="border-collapse: collapse; width: 100%;">
		<tr class="fontePadraoNegrito">
			<td>Venc.</td>
			<td>Pag.</td>
			<td>Objetivo</td>
			<td>Origem</td>
			<td>Destino</td>
			<td>Estabelec.</td>
			<td>Parc.</td>
			<td align="right">Valor</td>
			<td>Forma de Pagamento</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>

</div>