$(document).ready(function()
{
	$("[name='btpEstabelecimento.estNomEstabelecimento']").focus();

	$("[name='btnConsultar']").click(function() {
		jvsEstabelecimento.btnConsultarClick();
	});

	$("[name='btnLimparConsulta']").click(function() {
		jvsEstabelecimento.btnLimparConsultaClick();
	});

	$("[name='btnConfirmar']").click(function() {
		jvsEstabelecimento.btnConfirmarClick();
	});

	$("[data-name='btnEditar']").click(function() {
		jvsEstabelecimento.btnEditarClick($(this));
	});

	$("[data-name='btnExcluir']").click(function() {
		jvsEstabelecimento.btnExcluirClick($(this));
	});

	$("[name='btnAlterar']").click(function() {
		jvsEstabelecimento.btnAlterarClick();
	});

	$("[name='btnCancelar']").click(function() {
		jvsEstabelecimento.btnCancelarClick();
	});
});

var jvsEstabelecimento = new JvsEstabelecimento();
function JvsEstabelecimento()
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
		if ($("[name='btpEstabelecimento.estNomEstabelecimento']").val() == "")
			message.addError("Campo Obrigatório", "Estabelecimento");

		if (message.size() > 0)
		{
			message.show();
			return false;
		}

		return true;
	};

	this.btnConsultarClick = function()
	{
		$("[name='FrmEstabelecimento']").attr("action", "estabelecimento.do?cmd=actShowMainPage&sqlOrdem=1");
		$("[name='FrmEstabelecimento']").submit();
	};

	this.btnLimparConsultaClick = function()
	{
		$("[name='btpEstabelecimento.estCodEstabelecimento']").val("");
		$("[name='btpEstabelecimento.estNomEstabelecimento']").val("");
		jvsEstabelecimento.btnConsultarClick();
	};

	this.btnConfirmarClick = function()
	{
		if (!jvsEstabelecimento.validateFormData())
			return false;

		$("[name='FrmEstabelecimento']").attr("action", "estabelecimento.do?cmd=actExecInsert");
		$("[name='FrmEstabelecimento']").submit();
	};

	this.btnEditarClick = function(obj)
	{
		$("[name='FrmEstabelecimento']").attr("action", "estabelecimento.do?cmd=actShowEditForm&btpEstabelecimento.estCodEstabelecimento=" + obj.attr("data-value"));
		$("[name='FrmEstabelecimento']").submit();
	};

	this.btnAlterarClick = function()
	{
		$("[name='FrmEstabelecimento']").attr("action", "estabelecimento.do?cmd=actExecUpdate");
		$("[name='FrmEstabelecimento']").submit();
	};

	this.btnCancelarClick = function()
	{
		jvsEstabelecimento.btnLimparConsultaClick();
	};

	this.btnExcluirClick = function(obj)
	{
		var msgConfirm = "";
		msgConfirm += "Deseja excluir permanentemente o registro selecionado?\n\n";

		if (!confirm(msgConfirm))
			return false;

		$("[name='FrmEstabelecimento']").attr("action", "estabelecimento.do?cmd=actExecDelete&btpEstabelecimento.estCodEstabelecimento=" + obj.attr("data-value"));
		$("[name='FrmEstabelecimento']").submit();
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
}