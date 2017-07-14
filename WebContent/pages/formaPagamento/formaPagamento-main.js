$(document).ready(function()
{
	$("[name='btpFormaPagamento.fopNomFormaPagamento']").focus();

	$("[name='btnConsultar']").click(function() {
		jvsFormaPagamento.btnConsultarClick();
	});

	$("[name='btnLimparConsulta']").click(function() {
		jvsFormaPagamento.btnLimparConsultaClick();
	});

	$("[name='btnConfirmar']").click(function() {
		jvsFormaPagamento.btnConfirmarClick();
	});

	$("[data-name='btnEditar']").click(function() {
		jvsFormaPagamento.btnEditarClick($(this));
	});

	$("[data-name='btnExcluir']").click(function() {
		jvsFormaPagamento.btnExcluirClick($(this));
	});

	$("[name='btnAlterar']").click(function() {
		jvsFormaPagamento.btnAlterarClick();
	});

	$("[name='btnCancelar']").click(function() {
		jvsFormaPagamento.btnCancelarClick();
	});
});

var jvsFormaPagamento = new JvsFormaPagamento();
function JvsFormaPagamento()
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
		if ($("[name='btpFormaPagamento.fopNomFormaPagamento']").val() == "")
			message.addError("Campo Obrigat�rio", "Forma de Pagamento");

		if (message.size() > 0)
		{
			message.show();
			return false;
		}

		return true;
	};

	this.btnConsultarClick = function()
	{
		$("[name='FrmFormaPagamento']").attr("action", "formaPagamento.do?cmd=actShowMainPage&sqlOrdem=1");
		$("[name='FrmFormaPagamento']").submit();
	};

	this.btnLimparConsultaClick = function()
	{
		$("[name='btpFormaPagamento.fopCodFormaPagamento']").val("");
		$("[name='btpFormaPagamento.fopNomFormaPagamento']").val("");
		jvsFormaPagamento.btnConsultarClick();
	};

	this.btnConfirmarClick = function()
	{
		if (!jvsFormaPagamento.validateFormData())
			return false;

		$("[name='FrmFormaPagamento']").attr("action", "formaPagamento.do?cmd=actExecInsert");
		$("[name='FrmFormaPagamento']").submit();
	};

	this.btnEditarClick = function(obj)
	{
		$("[name='FrmFormaPagamento']").attr("action", "formaPagamento.do?cmd=actShowEditForm&btpFormaPagamento.fopCodFormaPagamento=" + obj.attr("data-value"));
		$("[name='FrmFormaPagamento']").submit();
	};

	this.btnAlterarClick = function()
	{
		$("[name='FrmFormaPagamento']").attr("action", "formaPagamento.do?cmd=actExecUpdate");
		$("[name='FrmFormaPagamento']").submit();
	};

	this.btnCancelarClick = function()
	{
		jvsFormaPagamento.btnLimparConsultaClick();
	};

	this.btnExcluirClick = function(obj)
	{
		var msgConfirm = "";
		msgConfirm += "Deseja excluir permanentemente o registro selecionado?\n\n";

		if (!confirm(msgConfirm))
			return false;

		$("[name='FrmFormaPagamento']").attr("action", "formaPagamento.do?cmd=actExecDelete&btpFormaPagamento.fopCodFormaPagamento=" + obj.attr("data-value"));
		$("[name='FrmFormaPagamento']").submit();
	};

	/* +----------------------------------------------------------------------+
	 * | Fun��es Privadas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */

	/* +----------------------------------------------------------------------+
	 * | Fun��es P�blicas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
}