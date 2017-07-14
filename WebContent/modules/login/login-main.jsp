<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import = "br.com.dfdevforge.sisfin.constants.Constants" %>
<%@ page import = "java.util.Date" %>

<script type="text/javascript" src="modules/login/login-main.js"></script>

<html:form action="login.do?cmd=actExecSearch">

<html:hidden property="btpUsuario.usuTxtSenha" />

<div class="puipanel" title="Acesso ao Sistema">
	<div class="ui-grid ui-grid-responsive fieldContainer">
		<div class="ui-grid-row">
			<div class="ui-grid-col-3">Email</div>
			<div class="ui-grid-col-9"><html:text property="btpUsuario.usuTxtEmail" styleClass="fieldEmail width400px" /></div>
		</div>
		<div class="ui-grid-row">
			<div class="ui-grid-col-3">Senha</div>
			<div class="ui-grid-col-9"><input type="password" name="auxTxtSenha" class="width400px" /></div>
		</div>
	</div>

	<div class="ui-grid ui-grid-responsive buttonContainer">
		<div class="ui-grid-row"><div class="ui-grid-col-12"><input type="button" name="btnConfirmar"></div></div>
	</div>

	<div class="ui-grid ui-grid-responsive center">
		<hr />
		<%out.print(new Date());%>
	</div>
</div>

</html:form>