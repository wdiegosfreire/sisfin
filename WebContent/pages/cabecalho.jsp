<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script type="text/javascript" src="modules/cabecalho/cabecalho-main.js"></script>

<table class="ui-panel ui-panel-titlebar ui-widget-header ui-helper-clearfix width100pc">
	<tr>
		<td class="width40px center">
			<logic:present name="btpUsuario" property="usuTxtNome">
				<a href="javascript:void(0)" accesskey="m" data-name="btnNavmenu" class="fa fa-navicon fa-2x" tabindex="-1"></a>
			</logic:present>
		</td>
		<td class="width40px center">
			<logic:present name="btpUsuario" property="usuTxtNome">
				<logic:present name="showNewButtom">
					<a href="javascript:void(0)" accesskey="n" class="fa fa-plus-square-o fa-2x" style="padding: 2px 0px 0px 1px;" tabindex="-1" onclick="movimentoFormPopup.show(null);"></a>
				</logic:present>
			</logic:present>
		</td>
		<td class="width40px center">
			<span id="divLoading" style="display: none;" class="c-red"><i class="fa fa-spin fa-cog fa-2x"></i></span>
		</td>
		<td class="center">
			<span style="font-size: 33px;"><i class="fa fa-usd"></i><bean:message key="label.appName.short"/></span>
			<span style="font-size: 10px;"><bean:message key="label.application.version"/></span>
		</td>
		<td class="width40px center"></td>
		<td class="width40px center"></td>
		<td class="width40px center">
			<logic:present name="btpUsuario" property="usuTxtNome">
				<a href="javascript:void(0)" accesskey="u" data-name="btnUsermenu" class="fa fa-user fa-2x" tabindex="-1"></a>
			</logic:present>
		</td>
	</tr>
</table>