// Imports area
document.write("<scr" + "ipt type='text/javascript' src='javascript/global/jvsCriptografia.js'></scr" + "ipt>");

/*
 * Init area ------------------------------------------------------------------
 */
$(document).ready(function()
{
	$("[name='btpUsuario.usuTxtEmail']").focus();

	// Criando os eventos dos componentes da página (botões, caixas de seleção etc.).
	$("[name='btnConfirmar']").click(function() {
		jvsLoginMain.btnConfirmarClick();
	});
});

var jvsLoginMain = new JvsLoginMain();
function JvsLoginMain()
{
	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */
	this.validateFormData = function()
	{
		if ($("[name='btpUsuario.usuTxtEmail']").val() == "")
			message.addError("Campo Obrigatório", "Usuário Error");
		if ($("[name='auxTxtSenha']").val() == "")
			message.addError("Campo Obrigatório", "Senha");

		if (message.size() > 0)
		{
			message.show();
			return false;
		}

		return true;
	};

	this.btnConfirmarClick = function()
	{
		if (jvsLoginMain.validateFormData())
		{
			criptografar();
			$("[name='FrmLogin']").submit();
		}
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
	function criptografar()
	{
		var hash = hex_md5($("[name='auxTxtSenha']").val());
		$("[name='btpUsuario.usuTxtSenha']").val(hash);
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
}