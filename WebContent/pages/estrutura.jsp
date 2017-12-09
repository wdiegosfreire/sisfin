<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<html>

<head>
	<link rel="icon" href="images/favicon-32.ico" sizes="32x32">

	<title><bean:message key="label.appName.short"/></title>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<meta http-equiv="Pragma" content="no-cache">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">

	<link href="manifest/manifest.json" rel="manifest">
	<link href="styles/estilos.css" type="text/css" rel="stylesheet">

	<!--
		Importando o Jquery
	-->
	<script src="jquery/jquery/js/jquery-1.11.1.min.js" type="text/javascript"></script>

	<!--
		Importando o JqueryUI
	-->
	<link href="jquery/jquery/css/<bean:message key="env.theme" />/jquery-ui-custom.css" rel="stylesheet">
	<script src="jquery/jquery/js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
	<script src="jquery/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>

	<!--
		Importando Font Awesome
	-->
	<link href="jquery/font-awesome-4.6.3/css/font-awesome.min.css" rel="stylesheet">

	<!--
		Importando o PrimeUI
	-->
	<link href="jquery/primeui-4.1.15/primeui.css" type="text/css" rel="stylesheet">
	<script src="jquery/primeui-4.1.15/primeui.js" type="text/javascript"></script>

	<!--
		Importando o Touch Swipe
	-->
	<script src="jquery/touchswipe-1.6.9/touchswipe.js"></script>

	<!--
		Importando as extensões PrimeUI Dynamic Form e Prime UI Combobox
	-->
	<script src="jquery/primeui-extensions/puidynamicform/puidynamicform-1.0.0.js"></script>

	<link href="jquery/primeui-extensions/puicombobox/puicombobox-0.1.css" rel="stylesheet">
	<script src="jquery/primeui-extensions/puicombobox/puicombobox-0.1.js"></script>

	<!--
		Importando outros plugins baseados em Jquery
	-->
	<script type="text/javascript" src="jquery/maskMoney/jquery.maskMoney-3.0.2.js"></script>
	<script type="text/javascript" src="jquery/maskedinput/jquery.maskedinput-1.3.1.js"></script>

	<!--
		Importando o plugin mobile-detect, responsável por identificar qual é o tipo do navegador, sistema operacional e outros
	-->
	<script type="text/javascript" src="jquery/mobile-detect-1.3.5/mobile-detect.js"></script>

	<!--
		DWR Plugin
	-->
	<script type="text/javascript" src="dwr/engine.js"></script>
	<script type="text/javascript" src="dwr/util.js"></script>

	<!--
		Importando highcharts e seus componentes
	-->
	<script src="jquery/highcharts-5.0.2/highcharts.js"></script>
	<script src="jquery/highcharts-5.0.2/highcharts-3d.js"></script>
	<script src="jquery/highcharts-5.0.2/exporting.js"></script>

	<!--
		Importando o arquivo JavaScript global do sistema
	-->
	<script type="text/javascript" src="javascript/global/prototype.js"></script>
	<script type="text/javascript" src="modules/estrutura/jvsEstrutura.js"></script>
	<script type="text/javascript" src="modules/estrutura/jvsPopupParent.js"></script>

	<!--
		A partir deste ponto, os plugins devem ser analisados para que sejam verificados se há a necessidade de estarem no projeto.
		Devem ser eliminado caso poucas funcionalidades sejam usadas ou que possam ser substituídas por alguma funcionalidade
		presente em algum dos plugins de base do projeto.
	-->

	<%-- Importando javascript da aplicação --%>
	<script type="text/javascript" src="javascript/highlight.js"></script>
</head>

	<body>
		<html:errors />

		<div id="puigrowl" style="margin-top: 60px;"></div>
		<div id="msgTempContainer" style="display: none;"><ul></ul></div>

		<bean:define id="cmd_"><%=request.getParameter("cmd")%></bean:define>
		<input type="hidden" name="cmd" value="<%=request.getParameter("cmd")%>" />

		<!-- A div a seguir é referente ao componente PrimeUI Growl em uma definição antiga e deve ser descontinuado. -->
		<div id="msgBox"></div>

		<!-- Verificar se a div a seguir é necessária -->
		<div id="dialogBox"></div>

		<div id="main">
			<div id="header">
				<tiles:insert attribute="header" />
				<tiles:insert attribute="menu" />
			</div>
			<div id="content">
				<tiles:insert attribute="table01" />
				<tiles:insert attribute="table02" />
			</div>
			<div id="popupArea" style="display: none;"></div>
		</div>
	</body>

</html>