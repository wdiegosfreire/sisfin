<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>

<html>

<head></head>

<body>

<table style="width: 100%; text-align: center; height: 100%; border: none">
	<tr valign="top" height="1"><td><bean:message key='label.erro.paginaErros' /></td></tr>

	<html:errors />

	<tr>
		<td height="1">
			<hr size="1">
			<!-- Botões de opção do formulário principal -->
			<table style="width: 100%; border: none;">
				<tr>
					<td align="left" nowrap>
						<input type="button" name="btnVoltar" value="<bean:message key='label.button.back' />" onClick="history.go(-1);">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>

</html>