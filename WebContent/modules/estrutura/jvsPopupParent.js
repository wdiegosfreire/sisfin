function JvsPopupParent()
{
	/* +----------------------------------------------------------------------+
	 * | Variáveis Privadas Obrigatórias                                      |
	 * +----------------------------------------------------------------------+
	 */
	var effect = "fade";
	var speed = "slow";
	var content = "#content";
	var popupArea = "#popupArea";
	var isMobile = app.isMobile();
//	var isMobile = true;

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */

	/**
	 * Tem as finalidades listada abaixo:
	 * 1) montar o container popup de acordo com o tipo de dispositivo: desktop ou mobile.
	 */
	this.buildPuidialog = function(html)
	{
		$(popupArea).html(html);

		if (isMobile)
		{
			$("#popupArea").children("div").puipanel();
		}
		else
		{
			var options = {
				responsive: true,
				resizable: false,
				closable: false,
				location: "top",
				modal: true,
				width: "1000px",
				height: "550px",
				showEffect: effect,
				hideEffect: effect,
				effectSpeed: speed
			};

			$(popupArea).children("div").puidialog(options);
		}
	};

	/**
	 * Tem as finalidades listadas abaixo:
	 * 
	 * 1) Executa a função responsável por montar a estrutura HTML;
	 * 2) Executa a função responsável pelo preenchimento dos dados do popup;
	 * 3) Exibe o popup para o usuário.
	 */
	this.show = function()
	{
		if (isMobile)
		{
			$(content).hide(effect, speed, function() {
				$(popupArea).show(effect, speed);
			});
		}
		else
		{
			$(popupArea).show(function() {
				$(popupArea).children("div").puidialog("show");
			});
		}
	};

	/**
	 * Tem as finalidades listadas abaixo:
	 * 
	 * 1) Apaga as informações preenchidas no popup;
	 * 2) Esconde o popup para o usuário.
	 */
	this.hide = function()
	{
		if (isMobile)
		{
			$(popupArea).hide(effect, speed, function() {
				$(content).show(effect, speed);
			});
		}
		else
		{
			$(popupArea).children("div").puidialog("hide", function() {
				$(popupArea).hide();
			});
		}
	};
}