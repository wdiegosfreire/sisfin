/*
 * Init area ------------------------------------------------------------------
 */
$(document).ready(function()
{
	// Criando os eventos dos componentes da página (botões, caixas de seleção etc.).
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
	 * | Funções Privadas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */
	this.btnNavmenuClick = function()
	{
		$("#puimenubar").toggle("slow");
	};

	this.btnUsermenuClick = function()
	{
		message.addInfo("Informação", "Funcionalidade em desenvolvimento");
		message.show();
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
	function criptografar()
	{
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
}