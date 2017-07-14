<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<!-- Lista de bandas cadastradas -->
<table border="1" style="width: 100%; border-spacing: 0px;">
	<tr>
		<td colspan="9"><bean:message key="label.attribute.movDscMovimento.up" /> - <bean:message key="label.application.consulta.up" /></td>
	</tr>

	<logic:iterate id="orcamentoList" name="orcamentoList">
		<tr class="fontePadraoNegrito">
			<td style="width: 1px" onclick="jvsShowHideElement('movimentoGrid<bean:write name="orcamentoList" property="orcCodOrcamento" />', this, 'adicionar.gif', 'minus.gif');">
				<img src="images/adicionar.gif" border="0" />
			</td>
			<td colspan="5">Competência: <bean:write name="orcamentoList" property="orcDatCompetencia" format="MM/yyyy" /></td>
		</tr>
		<tr style="display: none;" id="movimentoGrid<bean:write name="orcamentoList" property="orcCodOrcamento" />">
			<td>&nbsp;</td>
			<td>
				<div style="overflow: auto; width: 100%; height: 200px;">
					<table style="width: 100%;">
						<tr class="fontePadraoNegrito">
							<td>Data</td>
							<td>Descrição</td>
							<td>Tipo</td>
							<td>Estabelecimento</td>
							<td>Categoria</td>
							<td>ITM</td>
							<td>PAR</td>
						</tr>
						<logic:iterate id="movimentoList" name="orcamentoList" property="movimentoList">
							<tr class="fontePadrao">
								<td><bean:write name="movimentoList" property="movDatMovimento" format="dd/MM/yyyy" /></td>
								<td><bean:write name="movimentoList" property="movDscMovimento" /></td>
								<td><bean:write name="movimentoList" property="tipoMovimentoBean.timNomTipoMovimento" /></td>
								<td><bean:write name="movimentoList" property="estabelecimentoBean.estNomEstabelecimento" /></td>
								<td><bean:write name="movimentoList" property="categoriaBean.catNomCategoria" /></td>
								<td>I</td>
								<td>P</td>
							</tr>
						</logic:iterate>
					</table>
				</div>
			</td>
		</tr>
	</logic:iterate>
</table>