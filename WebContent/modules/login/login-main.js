// Imports area
document.write("<scr" + "ipt type='text/javascript' src='javascript/global/jvsCriptografia.js'></scr" + "ipt>");

/*
 * Init area ------------------------------------------------------------------
 */
$(document).ready(function()
{
	$("[name='btpUsuario.usuTxtEmail']").focus();

	// Criando os eventos dos componentes da p�gina (bot�es, caixas de sele��o etc.).
	$("[name='btnConfirmar']").click(function() {
		jvsLoginMain.btnConfirmarClick();
	});
});

var jvsLoginMain = new JvsLoginMain();
function JvsLoginMain()
{
	/* +----------------------------------------------------------------------+
	 * | Fun��es Privadas Obrigat�rias                                        |
	 * +----------------------------------------------------------------------+
	 */

	/* +----------------------------------------------------------------------+
	 * | Fun��es P�blicas Obrigat�rias                                        |
	 * +----------------------------------------------------------------------+
	 */
	this.validateFormData = function()
	{
		if ($("[name='btpUsuario.usuTxtEmail']").val() == "")
			message.addError("Campo Obrigat�rio", "Usu�rio Error");
		if ($("[name='auxTxtSenha']").val() == "")
			message.addError("Campo Obrigat�rio", "Senha");

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
	 * | Fun��es Privadas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
	function criptografar()
	{
		var hash = hex_md5($("[name='auxTxtSenha']").val());
		$("[name='btpUsuario.usuTxtSenha']").val(hash);
	};

	/* +----------------------------------------------------------------------+
	 * | Fun��es P�blicas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
}