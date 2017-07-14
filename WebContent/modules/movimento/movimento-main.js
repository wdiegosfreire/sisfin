$(document).ready(function()
{
	$("[name='btpCompetencia.comDatMes']").focus();

	// Criando os eventos dos componentes da página (botões, caixas de seleção etc.).
	$("[name='btpCompetencia.comDatMes']").change(function() {
		jvsMovimentoNewMain.cbxComDatMesChange();
	});

	$("[name='btpCompetencia.comDatAno']").change(function() {
		jvsMovimentoNewMain.txtComDatAnoChange();
	});

	$("[name='btpCompetencia.comDatAno']").blur(function() {
		jvsMovimentoNewMain.txtComDatAnoBlur();
	});

	$("[name='conCodConta']").change(function() {
		jvsMovimentoNewMain.cbxConCodContaChange();
	});

	$("[name='btnConsultar']").click(function() {
		jvsMovimentoNewMain.btnConsultarClick();
	});

	$("[name='btnLimparConsulta']").click(function() {
		jvsMovimentoNewMain.btnLimparConsultaClick();
	});

	$("[name='cbxEscopoConsulta']").change(function() {
		jvsMovimentoNewMain.cbxEscopoConsultaChange();
	});

	$("#periodContainer").swipe({
		swipe: function(event, direction, distance, duration, fingerCount) {
			jvsMovimentoNewMain.periodContainerSwipe(direction);
		},
		threshold: 5
	});

	jvsMovimentoNewMain.pageLoad();
});

var jvsMovimentoNewMain = new JvsMovimentoNewMain();
function JvsMovimentoNewMain()
{
	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */
	this.pageLoad = function()
	{
		carregarDados();
	};

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

	this.cbxComDatMesChange = function()
	{
		carregarDados();
	};

	this.txtComDatAnoChange = function()
	{
		carregarDados();
	};

	this.txtComDatAnoBlur = function()
	{
		carregarDados();
	};

	this.cbxConCodContaChange = function()
	{
		carregarDados();
	};

	this.btnConsultarClick = function()
	{
		carregarDados();
	};

	this.btnLimparConsultaClick = function()
	{
		var frmFiltro = $("[data-name='frmFiltro']");
		frmFiltro.find(":text").val("");
		frmFiltro.find("[type='date']").val("");
		frmFiltro.find("select").val("mes");

		$("[name='btpCompetencia.comDatMes']").prop("disabled", false).removeClass("ui-state-disabled");
		$("[name='btpCompetencia.comDatAno']").prop("disabled", false).removeClass("ui-state-disabled");

		carregarDados();
	};

	this.cbxEscopoConsultaChange = function()
	{
		if ($("[name='cbxEscopoConsulta']").val() == "tudo")
		{
			$("[name='btpCompetencia.comDatMes']").prop("disabled", true).addClass("ui-state-disabled");
			$("[name='btpCompetencia.comDatAno']").prop("disabled", true).addClass("ui-state-disabled");
		}
		else if ($("[name='cbxEscopoConsulta']").val() == "ano")
		{
			$("[name='btpCompetencia.comDatMes']").prop("disabled", true).addClass("ui-state-disabled");
			$("[name='btpCompetencia.comDatAno']").prop("disabled", false).removeClass("ui-state-disabled");
		}
		else
		{
			$("[name='btpCompetencia.comDatMes']").prop("disabled", false).removeClass("ui-state-disabled");
			$("[name='btpCompetencia.comDatAno']").prop("disabled", false).removeClass("ui-state-disabled");
		}
	};

	this.periodContainerSwipe = function(direction)
	{
		var mes = parseInt($("[name='btpCompetencia.comDatMes']").val());
		var ano = parseInt($("[name='btpCompetencia.comDatAno']").val());

		if (direction == "left")
		{
			if (mes == 12)
			{
				mes = 1;
				ano++;
			}
			else
			{
				mes++;
			}
		}
		else if (direction == "right")
		{
			if (mes == 1)
			{
				mes = 12;
				ano--;
			}
			else
			{
				mes--;
			}
		}

		if (mes < 10)
			mes = "0" + mes;

		$("[name='btpCompetencia.comDatMes']").val(mes);
		$("[name='btpCompetencia.comDatAno']").val(ano);

		carregarDados();
	};

	this.btnEditarClick = function(obj)
	{
		movimentoFormPopup.show(obj.attr("data-value"));
	};

	this.btnExcluirClick = function(obj)
	{
		var msgConfirm = "";
		msgConfirm += "Deseja excluir permanentemente o registro selecionado?\n\n";
		msgConfirm += "Aviso: ao excluir este registro serão excluídos também todos os movimentos cadastrados.";

		if (!confirm(msgConfirm))
			return false;

		jvsEstrutura.showLoading();

		btpObjetivo = {
			objCodObjetivo: obj.attr("data-value")
		};

		DwrObjetivo.execute(btpObjetivo, "actExecDelete",
		{
			callback:function(dwrReturn)
			{
				carregarDados();
			}
		});
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
	function carregarDados()
	{
		jvsEstrutura.showLoading();

		if ($("[name='btpCompetencia.comDatMes']").val() == "" || $("[name='btpCompetencia.comDatAno']").val() == "")
			return false;

		var map = {
			movDatVencimentoMin: $("[name='movDatVencimentoMin']").val(),
			movDatVencimentoMax: $("[name='movDatVencimentoMax']").val(),
			movDatPagamentoMin: $("[name='movDatPagamentoMin']").val(),
			movDatPagamentoMax: $("[name='movDatPagamentoMax']").val(),
			objTxtDescricao: $("[name='objTxtDescricao']").val(),
			movVlrMovimentoMin: stringMoneyToNumberObject($("[name='movVlrMovimentoMin']").val()),
			movVlrMovimentoMax: stringMoneyToNumberObject($("[name='movVlrMovimentoMax']").val()),
			conCodContaOrigem: $("[name='conCodContaOrigem']").val(),
			conCodContaDestino: $("[name='conCodContaDestino']").val(),
			cbxEscopoConsulta: $("[name='cbxEscopoConsulta']").val(),
			estCodEstabelecimento: $("[name='estCodEstabelecimento']").val(),
			fopCodFormaPagamento: $("[name='fopCodFormaPagamento']").val()
		};

		var btpCompetencia = {
			comDatMes: $("[name='btpCompetencia.comDatMes']").val(),
			comDatAno: $("[name='btpCompetencia.comDatAno']").val(),
			map: map
		};

		if ($("[name='cbxEscopoConsulta']").val() == "tudo")
		{
			btpCompetencia.comDatMes = null;
			btpCompetencia.comDatAno = null;
		}
		else if ($("[name='cbxEscopoConsulta']").val() == "ano")
		{
			btpCompetencia.comDatMes = null;
		}

		DwrMovimentoNew.execute(btpCompetencia, "actShowMainPage",
		{
			callback:function(dwrReturn)
			{
				carregarTabelaResumoMensal(dwrReturn);

				jvsEstrutura.prepareHtmlComponents($(".resultContainer"));
				jvsEstrutura.hideLoading();
			}
		});
	};

	function carregarTabelaResumoMensal(dwrReturn)
	{
		var html = "";
		var movQtdMovimento = 0;
		for (var i = 0; i < dwrReturn.mapResult.btpObjetivoList.length; i++)
		{
			var btpObjetivo = dwrReturn.mapResult.btpObjetivoList[i];

			for (var j = 0; j < btpObjetivo.btpMovimentoList.length; j++)
			{
				var btpMovimento = btpObjetivo.btpMovimentoList[j];
				movQtdMovimento += btpObjetivo.btpMovimentoList.length;

				html += "<tr>";
				html += "  <td class=\"center\">" + btpMovimento.movDatVencimento.toFormat(datePatternEnum.DIA_MES_ANO) + "</td>";
				html += "  <td class=\"center\">" + (btpMovimento.movDatPagamento == null ? "" : btpMovimento.movDatPagamento.toFormat(datePatternEnum.DIA_MES_ANO)) + "</td>";
				html += "  <td class=\"left\" title=\"" + btpMovimento.btpContaOrigem.btpContaPai.btpContaPai.conTxtDescricao + " > " + btpMovimento.btpContaOrigem.btpContaPai.conTxtDescricao + " > " + btpMovimento.btpContaOrigem.conTxtDescricao + "\">" + btpMovimento.btpContaOrigem.conTxtDescricao + "</td>";
				html += "  <td class=\"left\" title=\"" + btpMovimento.btpContaDestino.btpContaPai.btpContaPai.conTxtDescricao + " > " + btpMovimento.btpContaDestino.btpContaPai.conTxtDescricao + " > " + btpMovimento.btpContaDestino.conTxtDescricao + "\">" + btpMovimento.btpContaDestino.conTxtDescricao + "</td>";
				html += "  <td class=\"left\">" + (btpObjetivo.btpEstabelecimento.estNomEstabelecimento != null ? btpObjetivo.btpEstabelecimento.estNomEstabelecimento : "") + "</td>";
				html += "  <td class=\"left\">" + (btpMovimento.btpFormaPagamento.fopNomFormaPagamento != null ? btpMovimento.btpFormaPagamento.fopNomFormaPagamento : "") + "</td>";
				html += "  <td class=\"left\">" + btpObjetivo.objTxtDescricao + "</td>";
				html += "  <td class=\"center\">" + btpMovimento.movNumParcela + "/" + btpMovimento.map.auxQtdParcela + "</td>";
				html += "  <td class=\"right\">" + numberObjectToStringMoney(btpMovimento.movVlrMovimentado) + "</td>";
				html += "  <td class=\"center\"><a data-name=\"btnEditar\" data-value=\"" + btpObjetivo.objCodObjetivo + "\" onclick=\"jvsMovimentoNewMain.btnEditarClick($(this));\"></a></td>";
				html += "  <td class=\"center\"><a data-name=\"btnExcluir\" data-value=\"" + btpObjetivo.objCodObjetivo + "\" onclick=\"jvsMovimentoNewMain.btnExcluirClick($(this));\"></a></td>";
				html += "</tr>";
			}
		}

		$("[name='FrmMovimentoNew']").find("[data-name='primeuiDatatable']").children("tbody").html(html);
		$("#qtdTotalMovimento").html("Quantidade de Movimentos: " + movQtdMovimento)
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
}