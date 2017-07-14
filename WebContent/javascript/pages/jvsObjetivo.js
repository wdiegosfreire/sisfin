$(document).ready(function()
{
	$(".puidynamicform").puidynamicform();
});

var jvsObjetivo = new JvsObjetivo();
function JvsObjetivo()
{
	/* +----------------------------------------------------------------------+
	 * | Funções Públicas                                                     |
	 * +----------------------------------------------------------------------+
	 */
	this.resetForm = function()
	{
		var obj = null;

		setObjetivoItemValues($("#frmObjetivoItem").find("tbody").find("tr"), null)
		setMovimentoValues($("#frmMovimento").find("tbody").find("tr"), null)

		// Resetando as informa��es do objetivo
		$("[name='btpObjetivo.objTxtDescricao']").focus();

		if ($("[name='btpTemplate.temCodTemplate']").val() == "")
		{
			$("[name='btpObjetivo.objTxtDescricao']").val("");
			$("[name='btpObjetivo.btpEstabelecimento.estCodEstabelecimento']").val("");
		}
	};

	this.calculateTotalValue = function()
	{
		var obiNumQuantidadeForm = stringMoneyToNumberObject($("[name='obiNumQuantidadeForm']").val());
		var obiVlrUnidadeForm = stringMoneyToNumberObject($("[name='obiVlrUnidadeForm']").val());

		var auxVlrTotalForm = 0;
		if (obiNumQuantidadeForm != "" && obiNumQuantidadeForm != "0,00" && obiVlrUnidadeForm != "" && obiVlrUnidadeForm != "0,00")
			auxVlrTotalForm = parseFloat(obiNumQuantidadeForm) * parseFloat(obiVlrUnidadeForm);

		$("[name='auxVlrTotalForm']").val(numberObjectToStringMoney(auxVlrTotalForm));
	};

	this.btnDeleteObjetivoClick = function(row)
	{
		var msgConfirm = "";
		msgConfirm += "Deseja excluir permanentemente este registro?\n\n";
		msgConfirm += "Aviso: ao excluir este registro ser�o exclu�dos tamb�m ";
		msgConfirm += "todos os movimentos cadastrados.";

		if (!confirm(msgConfirm))
			return;

		btpObjetivo = {
			objCodObjetivo: row.find("[name='btpObjetivo.objCodObjetivo']").val()
		}

		DwrObjetivo.execute(btpObjetivo, "actExecDelete",
		{
			callback:function(dwrReturn)
			{
				var map = {
					numMes: $("[name='btpObjetivo.map.numMes']").val(),
					numAno: $("[name='btpObjetivo.map.numAno']").val()
				};

				var btpObjetivo = {
					map: map
				};

				jvsObjetivo.resetForm();

				execSelectObjetivo(btpObjetivo);

				var message = "";
				for (var i = 0; i < dwrReturn.msgContainer.length; i++)
				{
					message += dwrReturn.msgContainer[i];
				}

				alert(message);
			}
		});
	};

	this.buildChartBar = function()
	{
		var map = {
			numMes: $("[name='btpObjetivo.map.numMes']").val(),
			numAno: $("[name='btpObjetivo.map.numAno']").val()
		};

		if (map.numMes == "" || map.numAno == "")
			return false;

		var btpObjetivo = {
			map: map
		};
	};

	/**
	 * Dado um item especificado em <code>auxNumItem</code>, tem a finalidade de multiplicar
	 * a quantidade informada do item por seu valor unit�rio. O resultado � inserido no campo
	 * "Vlr. Total". 
	 * @param auxNumItem
	 */
	this.calculateObiVlrTotal = function(obj)
	{
		var tr = obj.closest("tr");
		var qtd = stringMoneyToNumberObject(tr.find("[name='obiNumQuantidade']").val());
		var vlr = stringMoneyToNumberObject(tr.find("[name='obiVlrUnidade']").val());

		var tot = 0;
		if (qtd != "" && qtd != "0,00" && vlr != "" && vlr != "0,00")
			tot = parseFloat(qtd) * parseFloat(vlr);

		tr.find("[name='auxVlrTotal']").val(numberObjectToStringMoney(tot));

		calculateAuxVlrTotal("frmObjetivoItem");
	};

	this.copyVencimentoToPagamento = function()
	{
		$("[name='movDatPagamentoForm']").val($("[name='movDatVencimentoForm']").val())
	};

	this.movVlrMovimentoBlur = function()
	{
		calculateAuxVlrTotal("frmMovimento")
	};

	this.calculateObiNumItem = function()
	{
		var txt = $("[name=obiNumItem]");

		for (i = 0; i < txt.length; i++)
			txt[i].value = i + 1;

		auxNumItem = txt.length;
	};

	this.calculateMovNumParcela = function()
	{
		var txt = $("[name=movNumParcela]");

		for (i = 0; i < txt.length; i++)
			txt[i].value = i + 1;

		auxNumParcela = txt.length;
	};

	this.execInsertObjetivo = function()
	{
		if (validateRequiredFields() && validateSomaParcela() && validateContaOrigemDestino())
		{
			var btpObjetivo = {
				objTxtDescricao: $("[name='btpObjetivo.objTxtDescricao']").val(),
				btpEstabelecimento: getBtpEstabelecimento(), 
				btpObjetivoItemList: getBtpObjetivoItemList(), 
				btpMovimentoList: getBtpMovimentoList()
			};

			DwrObjetivo.actExecInsertDeprecated(btpObjetivo, 
			{
				callback:function()
				{
					var map = {
						numMes: $("[name='btpObjetivo.map.numMes']").val(),
						numAno: $("[name='btpObjetivo.map.numAno']").val()
					};

					var btpObjetivo = {
						map: map
					};

					jvsObjetivo.resetForm();

					execSelectObjetivo(btpObjetivo);

					jvsObjetivo.resetForm();

					alert("Cadastro realizado com sucesso!");
				}
			});
		}
	}

	this.execUpdateObjetivo = function()
	{
		if (validateRequiredFields() && validateSomaParcela() && validateContaOrigemDestino())
		{
			var btpObjetivo = {
				objCodObjetivo: $("[name='btpObjetivo.objCodObjetivo']").val(),
				objTxtDescricao: $("[name='btpObjetivo.objTxtDescricao']").val(),
				btpEstabelecimento: getBtpEstabelecimento(), 
				btpObjetivoItemList: getBtpObjetivoItemList(), 
				btpMovimentoList: getBtpMovimentoList()
			};

			DwrObjetivo.execute(btpObjetivo, "actExecUpdate",
			{
				callback:function(dwrReturn)
				{
					var map = {
						numMes: $("[name='btpObjetivo.map.numMes']").val(),
						numAno: $("[name='btpObjetivo.map.numAno']").val()
					};

					var btpObjetivo = {
						map: map
					};

					jvsObjetivo.resetForm();

					execSelectObjetivo(btpObjetivo);

					$("[name='btnAlterar']").hide();
					$("[name='btnCancelar']").hide();
					$("[name='btnConfirmar']").show();
					$("[name='btnFiltrar']").show();

					var message = "";
					for (var i = 0; i < dwrReturn.msgContainer.length; i++)
					{
						message += dwrReturn.msgContainer[i];
					}

					message.addInfo("Teste", message);
					message.show();
				}
			});
		}
	};

	this.adjustContaCombo = function(obj)
	{
		if (obj == null)
			obj = $("#frmMovimento").find("tbody").find("tr").last();

		/*
		 * Removendo as contas do grupo 03, "Sa�da", do combo "Conta de Origem".
		 */
		obj.find("[name='conCodContaOrigem']").find("option").each(function()
		{
			if ($(this).text().trim().substr(0, 2) == "03")
				$(this).remove();
		});

		/*
		 * Removendo as contas do grupo 02, "Entrada", do combo "Conta de Destino".
		 */
		obj.find("[name='conCodContaDestino']").find("option").each(function()
		{
			if ($(this).text().trim().substr(0, 2) == "02")
				$(this).remove();
		});

		/*
		 * Bloqueando as contas dos n�veis 1 e 2
		 */
		obj.find("[name='conCodContaOrigem'], [name='conCodContaDestino']").find("option").each(function()
		{
			var aux = $(this).text().split(".");

			if (aux.length == 2)
			{
				$(this).css("font-weight", "bold");
				$(this).css("background", "LightGoldenRodYellow");
			}

			if (aux.length != 4)
				$(this).attr("disabled", "true");
		});
	};

	// TODO Verificar se este método ainda é utilizado. Caso não seja, apagar
	this.execPrepareEddition = function(row)
	{
		btpObjetivo = {
			objCodObjetivo: row.find("[name='btpObjetivo.objCodObjetivo']").val()
		}

		DwrObjetivo.showEditForm(btpObjetivo,
		{
			callback:function(btpObjetivoList)
			{
				btpObjetivo = btpObjetivoList[0];

				var frm = $("[name='FrmObjetivo']");
				frm.find("[name='btpObjetivo.objCodObjetivo']").val(btpObjetivo.objCodObjetivo);
				frm.find("[name='btpObjetivo.objTxtDescricao']").val(btpObjetivo.objTxtDescricao);
				frm.find("[name='btpObjetivo.btpEstabelecimento.estCodEstabelecimento']").val(btpObjetivo.btpEstabelecimento.estCodEstabelecimento);

				auxNumItem = 0;
				auxNumParcela = 0;

				for (var i = 0; i < btpObjetivo.btpObjetivoItemList.length; i++)
				{
					var tr = null;

					setObjetivoItemValues(tr, btpObjetivo.btpObjetivoItemList[i])
				}

				for (var i = 0; i < btpObjetivo.btpMovimentoList.length; i++)
				{
					var tr = null;

					setMovimentoValues(tr, btpObjetivo.btpMovimentoList[i]);
				}

				jvsObjetivo.movVlrMovimentoBlur();

				$("[name='btnAlterar']").show();
				$("[name='btnCancelar']").show();
				$("[name='btnConfirmar']").hide();
				$("[name='btnFiltrar']").hide();
			}
		});
	};

	/* +----------------------------------------------------------------------+
	 * | Fun��es Privadas                                                     |
	 * +----------------------------------------------------------------------+
	 */
	function remObjetivoItemFrm(obiItem)
	{
		obiItem.remove();
		jvsObjetivo.calculateObiNumItem();
	};

	function remMovimentoFrm(movItem)
	{
		movItem.remove();
		jvsObjetivo.calculateMovNumParcela();
	};

	function calculateAuxVlrTotal(formName)
	{
		var source = "";
		var target = "";

		if (formName == "frmObjetivoItem")
		{
			formName = "#frmObjetivoItem";
			source = "[name=auxVlrTotal]";
			target = "[name=auxVlrSomaItem]";
		}
		else
		{
			formName = "#frmMovimento";
			source = "[name=movVlrMovimentado]";
			target = "[name=auxVlrSomaParcela]";
		}

		var frmObjetivoItem = $(formName);

		var obj = frmObjetivoItem.find(source);

		var total = 0;
		for (var i = 0; i < obj.length; i++)
		{
			if (obj.eq(i).val() != "")
				total += parseFloat(stringMoneyToNumberObject(obj.eq(i).val()));
		}

		$(target).val(numberObjectToStringMoney(total));
	};

	function validateRequiredFields()
	{
		var messageError = "";

		if ($("[name='btpObjetivo.objTxtDescricao']").val() == "")
			messageError += "\n- Objetivo n�o informado";

		// Validando o formul�rio de cadastro de itens
		var matched = false;
		$("#frmObjetivoItem").find("tbody").find("tr").find(":text").each(function()
		{
			if ($(this).val() == "" && !matched)
			{
				messageError += "\n- Campos vazios no cadastro de itens";
				matched = true;
			}
		});

		// Validando o formul�rio de cadasatro de parcelas
		matched = false;
		$("#frmMovimento").find("tbody").find("tr").find(":text, select").each(function()
		{
			if ($(this).attr("name") != "movDatPagamento" && $(this).val() == "" && !matched)
			{
				messageError += "\n- Campos vazios no cadastro de parcelas";
				matched = true;
			}
		});

		if (messageError != "")
		{
			alert("Foram encontrados os seguintes erros no preenchimento do formul�rio:\n" + messageError);

			return false;
		}

		return true;
	};

	function validateSomaParcela()
	{
		if ($("[name='auxVlrSomaItem']").val() != $("[name='auxVlrSomaParcela']").val())
		{
			alert("O valor total dos itens deve ser igual ao valor total das parcelas.")
			return false;
		}

		return true;
	};

	function validateContaOrigemDestino()
	{
		var txtNumParcela = $("#frmMovimento").find("[name='movNumParcela']");

		var txtContaOri = $("#frmMovimento").find("[name='conCodContaOrigem']");
		var txtContaDes = $("#frmMovimento").find("[name='conCodContaDestino']");

		var numNivelOri = "";
		var numNivelDes = "";
		for (i = 0; i < txtNumParcela.length; i++)
		{
			numNivelOri = txtContaOri.eq(i).find("option:selected").text();
			numNivelDes = txtContaDes.eq(i).find("option:selected").text();

			// Verifica e impede movimentos entre contas iguais.
			if (numNivelOri == numNivelDes)
			{
				if (!confirm("Contas origem e destino iguais na parcela " + txtNumParcela.eq(i).val() + ". Deseja continuar?"))
					return false;
			}

			/*
			 * Verifica e impede movimentos dos tipos:
			 * Saldo -> Entrada (1 para 2)
			 * Entrada -> Entrada (2 para 2)
			 * Entrada -> Sa�da (2 para 3)
			 * Sa�da -> Saldo (3 para 1)
			 * Sa�da -> Entrada (3 para 2)
			 * Sa�da -> Sa�da (3 para 3)
			 */
			if (numNivelOri.split(".")[0] == 1 && numNivelDes.split(".")[0] == 2)
			{
				alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nSaldo -> Entrada");
				return false;
			}
			else if (numNivelOri.split(".")[0] == 2 && numNivelDes.split(".")[0] == 2)
			{
				alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nEntrada -> Entrada");
				return false;
			}
			else if (numNivelOri.split(".")[0] == 2 && numNivelDes.split(".")[0] == 3)
			{
				alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nEntrada -> Sa�da");
				return false;
			}
			else if (numNivelOri.split(".")[0] == 3 && numNivelDes.split(".")[0] == 1)
			{
				alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nSa�da -> Saldo");
				return false;
			}
			else if (numNivelOri.split(".")[0] == 3 && numNivelDes.split(".")[0] == 2)
			{
				alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nSa�da -> Entrada");
				return false;
			}
			else if (numNivelOri.split(".")[0] == 3 && numNivelDes.split(".")[0] == 3)
			{
				alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nSa�da -> Sa�da");
				return false;
			}

			/*
			 * Se o movimento for uma transfer�ncia, ou seja, entre contas de saldo, ent�o impede movimentos dos tipos:
			 * Movimento -> Virtual (M para V)
			 * Virtual -> Movimento (V para M)
			 */
			if (numNivelOri.indexOf("(M)") != -1 && numNivelDes.indexOf("(V)") != -1)
			{
				alert("Transfer�ncia n�o permitida na parcela " + txtNumParcela.eq(i).val() + ":\n\nMovimento -> Virtual");
				return false;
			}
			else if (numNivelOri.indexOf("(V)") != -1 && numNivelDes.indexOf("(M)") != -1)
			{
				alert("Transfer�ncia n�o permitida na parcela " + txtNumParcela.eq(i).val() + ":\n\nVirtual -> Movimento");
				return false;
			}
		}

		return true;
	};

	function getBtpEstabelecimento()
	{
		var btpEstabelecimento = {
			estCodEstabelecimento: $("[name='btpObjetivo.btpEstabelecimento.estCodEstabelecimento']").val()
		};

		return btpEstabelecimento;
	};

	function setObjetivoItemValues(tr, btpObjetivoItem)
	{
		if (btpObjetivoItem == null)
		{
			btpObjetivoItem = {
				obiNumItem: 1,
				obiTxtDescricao: "",
				obiNumQuantidade: 0,
				obiVlrUnidade: 0
			};
		}

		tr.find("[name='obiNumItem']").val(btpObjetivoItem.obiNumItem);
		tr.find("[name='obiTxtDescricao']").val(btpObjetivoItem.obiTxtDescricao);
		tr.find("[name='obiNumQuantidade']").val(numberObjectToStringMoney(btpObjetivoItem.obiNumQuantidade, 3));
		tr.find("[name='obiVlrUnidade']").val(numberObjectToStringMoney(btpObjetivoItem.obiVlrUnidade, 2));

		jvsObjetivo.calculateObiVlrTotal(tr.find("[name='obiVlrUnidade']"));
	};

	/**
	 * Preenche os valores em uma determinada linha do formul�rio
	 * de movimentos com os valores do bean passado por par�metro.
	 * 
	 * @param tr objeto html que contem os campos de formul�rio.
	 * @param btpMovimento bean com os valores da entidade.
	 */
	function setMovimentoValues(tr, btpMovimento)
	{
		if (btpMovimento == null)
		{
			btpMovimento = {
				movNumParcela: 1,
				movVlrMovimentado: 0,
				movDatVencimento: null,
				movDatPagamento: null,
				btpContaOrigem: {conCodConta: ""},
				btpContaDestino: {conCodConta: ""},
				btpFormaPagamento: {fopCodFormaPagamento: ""}
			};
		}

		tr.find("[name='movNumParcela']").val(btpMovimento.movNumParcela);
		tr.find("[name='movVlrMovimentado']").val(numberObjectToStringMoney(btpMovimento.movVlrMovimentado, 2));
		tr.find("[name='movDatVencimento']").val((btpMovimento.movDatVencimento == null ? "" : btpMovimento.movDatVencimento.toFormat(datePatternEnum.DIA_MES_ANO)));
		tr.find("[name='movDatPagamento']").val((btpMovimento.movDatPagamento == null ? "" : btpMovimento.movDatPagamento.toFormat(datePatternEnum.DIA_MES_ANO)));
		tr.find("[name='conCodContaOrigem']").val(btpMovimento.btpContaOrigem.conCodConta);
		tr.find("[name='conCodContaDestino']").val(btpMovimento.btpContaDestino.conCodConta);
		tr.find("[name='fopCodFormaPagamento']").val(btpMovimento.btpFormaPagamento.fopCodFormaPagamento);
	};

	function getBtpObjetivoItemList()
	{
		var obiCodObjetivoItem = $("#frmObjetivoItem").find("[name='obiCodObjetivoItem']");
		var obiNumItem = $("#frmObjetivoItem").find("[name='obiNumItem']");
		var obiTxtDescricao = $("#frmObjetivoItem").find("[name='obiTxtDescricao']");
		var obiNumQuantidade = $("#frmObjetivoItem").find("[name='obiNumQuantidade']");
		var obiVlrUnidade = $("#frmObjetivoItem").find("[name='obiVlrUnidade']");

		var btpObjetivoItemList = new Array();

		for (i = 0; i < obiNumItem.length; i++)
		{
			var btpObjetivoItem = {
				obiCodObjetivoItem: obiCodObjetivoItem.eq(i).val(),
				obiNumItem: obiNumItem.eq(i).val(),
				obiTxtDescricao: obiTxtDescricao.eq(i).val(),
				obiNumQuantidade: stringMoneyToNumberObject(obiNumQuantidade.eq(i).val()),
				obiVlrUnidade: stringMoneyToNumberObject(obiVlrUnidade.eq(i).val())
			};

			btpObjetivoItemList.push(btpObjetivoItem);
		}

		return btpObjetivoItemList;
	};

	function getBtpMovimentoList()
	{
		var btpMovimentoList = new Array();

		var movCodMovimento = $("#frmMovimento").find("[name='movCodMovimento']");
		var movNumParcela = $("#frmMovimento").find("[name='movNumParcela']");
		for (i = 0; i < movNumParcela.length; i++)
		{
			var btpContaOrigem = {
				conCodConta: $("#frmMovimento").find("[name='conCodContaOrigem']").eq(i).val()
			};

			var btpContaDestino = {
				conCodConta: $("#frmMovimento").find("[name='conCodContaDestino']").eq(i).val()
			};

			var btpFormaPagamento = {
				fopCodFormaPagamento: $("#frmMovimento").find("[name='fopCodFormaPagamento']").eq(i).val()
			};

			var btpMovimento = {
				movCodMovimento: movCodMovimento.eq(i).val(),
				movNumParcela: movNumParcela.eq(i).val(),
				movVlrMovimentado: stringMoneyToNumberObject($("#frmMovimento").find("[name='movVlrMovimentado']").eq(i).val()),
				movDatVencimento: stringDateToObjectDate($("#frmMovimento").find("[name='movDatVencimento']").eq(i).val()),
				movDatPagamento: stringDateToObjectDate($("#frmMovimento").find("[name='movDatPagamento']").eq(i).val()),
				btpContaOrigem: btpContaOrigem,
				btpContaDestino: btpContaDestino,
				btpFormaPagamento: btpFormaPagamento
			};

			btpMovimentoList.push(btpMovimento);
		}

		return btpMovimentoList;
	};
}