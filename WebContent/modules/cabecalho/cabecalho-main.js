/*
 * Init area ------------------------------------------------------------------
 */
$(document).ready(function()
{
	// Criando os eventos dos componentes da p�gina (bot�es, caixas de sele��o etc.).
	$("[data-name='btnNavmenu']").click(function() {
		jvsCabecalhoMain.btnNavmenuClick();
	});

	$("[data-name='btnUsermenu']").click(function() {
		jvsCabecalhoMain.btnUsermenuClick();
	});
});

var jvsCabecalhoMain = new JvsCabecalhoMain();
function JvsCabecalhoMain()
{
	/* +----------------------------------------------------------------------+
	 * | Fun��es Privadas Obrigat�rias                                        |
	 * +----------------------------------------------------------------------+
	 */

	/* +----------------------------------------------------------------------+
	 * | Fun��es P�blicas Obrigat�rias                                        |
	 * +----------------------------------------------------------------------+
	 */
	this.btnNavmenuClick = function()
	{
		$("#puimenubar").toggle("slow");
	};

	this.btnUsermenuClick = function()
	{
		message.addInfo("Informa��o", "Funcionalidade em desenvolvimento");
		message.show();
	};

	/* +----------------------------------------------------------------------+
	 * | Fun��es Privadas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
	function criptografar()
	{
	};

	/* +----------------------------------------------------------------------+
	 * | Fun��es P�blicas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
}