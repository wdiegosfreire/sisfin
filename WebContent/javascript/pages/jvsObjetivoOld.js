document.write("<scr" + "ipt type='text/javascript' src='dwr/interface/DwrMovimento.js'></scr" + "ipt>");
document.write("<scr" + "ipt type='text/javascript' src='dwr/interface/DwrTemplate.js'></scr" + "ipt>");
document.write("<scr" + "ipt type='text/javascript' src='dwr/interface/DwrTemplateRegra.js'></scr" + "ipt>");

document.write("<scr" + "ipt type='text/javascript' src='javascript/global/jvsPopupConfirmPaymentDate.js'></scr" + "ipt>");
document.write("<scr" + "ipt type='text/javascript' src='javascript/global/jvsPopupMovimentoResume.js'></scr" + "ipt>");
document.write("<scr" + "ipt type='text/javascript' src='javascript/global/jvsPopupTemplateRegra.js'></scr" + "ipt>");

document.write("<scr" + "ipt type='text/javascript' src='javascript/global/jvsPopupCadastroEstabelecimento.js'></scr" + "ipt>");

document.write("<scr" + "ipt type='text/javascript' src='plugin/jquery.dfsis.carousel/jquery.dfsis.carousel.js'></scr" + "ipt>");


var auxNumParcela = 0;
var auxNumItem = 0;

var htmlContaComboOptionList = null;
var htmlFormaPagamentoComboOptionList = null;
var htmlContaGridResumeList = null;

var conContaOrigemDefaultValue = null;
var conContaDestinoDefaultValue = null;
var fopCodFormaPagamentoDefaultValue = null;


//Functions area
function confirmDelete(key)
{
	if (confirm("Deseja excluir permanentemente este registro?"))
	{
		window.location = "objetivo.do?cmd=actExecDelete&btpObjetivo.objCodObjetivo=" + key;
	}
}

function addObjetivoItemFrm(btpObjetivoItem)
{
	auxNumItem++;

	var html = "";

	html += "<li id=\"divObi" + auxNumItem + "\">";
	html += "  <input type=\"hidden\" name=\"obiCodObjetivoItem\">";
	html += "  <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" onmouseover=\"lightOn(this);\" onmouseout=\"lightOff(this);\">";
	html += "    <tr class=\"fontePadraoNegrito\">";
	html += "      <td>N�mero:<br><input type=\"text\" obrigatorio=\"true\" readonly=\"readonly\" tabindex=\"-1\" name=\"obiNumItem\" value=\"" + auxNumItem + "\" size=\"5\" maxlength=\"2\" /></td>";
	html += "      <td colspan=\"2\">Descri��o:<br><input type=\"text\" obrigatorio=\"true\" style=\"width: 100%\" name=\"obiTxtDescricao\" /></td>";
	html += "      <td rowspan=\"2\" width=\"20\" align=\"center\"><img src=\"images/excluir.gif\" onclick=\"jvsObjetivo.btnRemoverItemClick($(this));\" title=\"Clique para remover este item.\"></td>";
	html += "    </tr>";
	html += "    <tr class=\"fontePadraoNegrito\">";
	html += "      <td>Quantidade:<br><input type=\"text\" obrigatorio=\"true\" class=\"fieldNumber\" name=\"obiNumQuantidade\" onblur=\"calcAuxVlrTotal(" + auxNumItem + "); sumTotalValueObiObjetivoItem();\" /></td>";
	html += "      <td>Vlr. Unit�rio:<br><input type=\"text\" obrigatorio=\"true\" class=\"fieldNumber\" name=\"obiVlrUnidade\" onblur=\"calcAuxVlrTotal(" + auxNumItem + "); sumTotalValueObiObjetivoItem();\" /></td>";
	html += "      <td>Vlr. Total:<br><input type=\"text\" obrigatorio=\"true\" class=\"fieldNumber\" tabindex=\"-1\" name=\"auxVlrTotal\" size=\"5\" readonly=\"readonly\" /></td>";
	html += "    </tr>";
	html += "  </table>";
	html += "</li>";

	$("#divObjetivoItemFrm").append(html);

	if (btpObjetivoItem != null)
	{
		$("#divObi" + auxNumItem).find("[name='obiCodObjetivoItem']").val(btpObjetivoItem.obiCodObjetivoItem);
		$("#divObi" + auxNumItem).find("[name='obiTxtDescricao']").val(btpObjetivoItem.obiTxtDescricao);
		$("#divObi" + auxNumItem).find("[name='obiNumQuantidade']").val(numberObjectToStringMoney(btpObjetivoItem.obiNumQuantidade, 3));
		$("#divObi" + auxNumItem).find("[name='obiVlrUnidade']").val(numberObjectToStringMoney(btpObjetivoItem.obiVlrUnidade));
		$("#divObi" + auxNumItem).find("[name='auxVlrTotal']").val(numberObjectToStringMoney(btpObjetivoItem.obiNumQuantidade * btpObjetivoItem.obiVlrUnidade));
	}

	$("#divObi" + auxNumItem).find("[name='obiTxtDescricao']").focus();
	$("#divObi" + auxNumItem).find("[name='obiNumQuantidade']").maskMoney({decimal: ",", thousands: ".", allowZero: false, precision: 3});
	$("#divObi" + auxNumItem).find("[name='obiVlrUnidade']").maskMoney({decimal: ",", thousands: ".", allowZero: false});
	$("#divObi" + auxNumItem).find("[name='auxVlrTotal']").maskMoney({decimal: ",", thousands: ".", allowZero: false});

	$(".obiDynamicForm").makeCarousel("init");
}

function execSelectObjetivo(btpObjetivo)
{
	DwrObjetivo.actExecSelect(btpObjetivo, 
	{
		callback:function(btpObjetivoList)
		{
			var html = "";

			/*
			 * Vari�veis totalizadoras:
			 * a) Valor total de rendimentos: contas de destino de n�vel 02
			 * b) Valor total de despesas   : contas de destino de n�vel 03
			 */
			var movVlrTotRend = 0;
			var movVlrTotDesp = 0;

			for (var i = 0; i < btpObjetivoList.length; i++)
			{
				html += "<tr class=\"fontePadrao\" valign=\"top\" onmouseover=\"lightOn(this)\" onmouseout=\"lightOff(this)\">";

				var btpMovimentoList = btpObjetivoList[i].btpMovimentoList;
				for (var j = 0; j < btpMovimentoList.length; j++)
				{
					html += "<td>" + btpMovimentoList[j].movDatVencimento.toFormat(datePatternEnum.DIA_MES_ANO) + "</td>";
					html += "<td>" + (btpMovimentoList[j].movDatPagamento != null ? btpMovimentoList[j].movDatPagamento.toFormat(datePatternEnum.DIA_MES_ANO) : "") + "</td>";

					html += "  <td>";
					html += "    <input type=\"hidden\" name=\"btpObjetivo.objCodObjetivo\" value=\"" + btpObjetivoList[i].objCodObjetivo + "\">";
					html +=      btpObjetivoList[i].objTxtDescricao;
					html += "  </td>";

					html += "<td>" + btpMovimentoList[j].btpContaOrigem.btpContaPai.btpContaPai.conTxtDescricao + " -> " + btpMovimentoList[j].btpContaOrigem.btpContaPai.conTxtDescricao + " -> " + btpMovimentoList[j].btpContaOrigem.conTxtDescricao + "</td>";
					html += "<td>" + btpMovimentoList[j].btpContaDestino.btpContaPai.btpContaPai.conTxtDescricao + " -> " + btpMovimentoList[j].btpContaDestino.btpContaPai.conTxtDescricao + " -> " + btpMovimentoList[j].btpContaDestino.conTxtDescricao + "</td>";

					html += "<td>" + (btpObjetivoList[i].btpEstabelecimento.estNomEstabelecimento != null ? btpObjetivoList[i].btpEstabelecimento.estNomEstabelecimento : "") + "</td>";

					html += "<td>";
					html += "  <input type=\"hidden\" name=\"btpMovimento.movCodMovimento\" value=\"" + btpMovimentoList[j].movCodMovimento + "\">";
					html += "  <span style=\"cursor: pointer;\" onclick=\"showPopupMovimentoResume($(this).closest('tr'));\">";
					html +=      btpMovimentoList[j].movNumParcela + "/" + btpMovimentoList[j].map.auxQtdParcela;
					html += "  </span>";
					html += "</td>";

					html += "<td align=\"right\">" + numberObjectToStringMoney(btpMovimentoList[j].movVlrMovimentado) + "</td>";

					html += "<td>";
					if (btpMovimentoList[j].btpFormaPagamento.fopNomFormaPagamento != null)
						html += btpMovimentoList[j].btpFormaPagamento.fopNomFormaPagamento;
					html += "</td>";

					/*
					 * Montagem do bot�o de edi��o de objetivos
					 */
					html += "<td>";
					if (btpMovimentoList[j].movNumParcela != 1 && btpMovimentoList[j].movDatPagamento == null)
						html += "<img src=\"images/calendario.jpg\" style=\"cursor: pointer;\" onclick=\"showPopupConfirmPaymentDate($(this).closest('tr'));\" />";
					else if (btpMovimentoList[j].movNumParcela == 1)
						html += "<img src=\"images/editar.gif\" title=\"Clique para editar este movimento\" style=\"cursor: pointer;\" onclick=\"jvsObjetivo.execPrepareEddition($(this).closest('tr'));\" />";
					html += "</td>";

					/*
					 * Montagem do bot�o de exclus�o de objetivos
					 */
					html += "<td>";
					if (btpMovimentoList[j].movNumParcela == 1)
						html += "<img src=\"images/excluir.gif\" title=\"Clique para excluir este movimento\" style=\"cursor: pointer;\" onclick=\"jvsObjetivo.btnDeleteObjetivoClick($(this).closest('tr'));\" />";
					html += "</td>";

					var numNivelOri = btpMovimentoList[j].btpContaOrigem.conNumNivel;
					if (numNivelOri != null && numNivelOri.split(".")[0] == "02")
						movVlrTotRend += btpMovimentoList[j].movVlrMovimentado;

					var numNivelDes = btpMovimentoList[j].btpContaDestino.conNumNivel;
					if (numNivelDes != null && numNivelDes.split(".")[0] == "03")
						movVlrTotDesp += btpMovimentoList[j].movVlrMovimentado;
				}

				html += "</tr>";
			}

			html += "<tr><td colspan=\"9\" align=\"right\" class=\"fontePadrao\">Total de movimentos realizados no m�s: " + btpObjetivoList.length + "</td></tr>";

			/*
			 * Populando a tabela de movimentos
			 */
			$("#tblObjetivo").find("[class='fontePadrao']").remove();

			if (btpObjetivoList.length > 0)
				$("#tblObjetivo").append(html);

			/*
			 * Populando a tabela de resumo
			 */
			var map = {
				auxDatCompetencia: $("[name='btpObjetivo.map.numMes']").val() + "/" + $("[name='btpObjetivo.map.numAno']").val()
			};

			var btpMovimento = {
				map: map
			};

			DwrObjetivo.actExecSelectResume(btpMovimento,
			{
				callback:function(btpContaList)
				{
					html = "";

					var totGeral = 0.0;

					$("#graph01Container").html("");

					for (var i = 0; i < btpContaList.length; i++)
					{
						var totRendMes = 0.0;
						var totDespMes = 0.0;
						var totAcumAnt = 0.0;
						var totEconMes = 0.0;

						/*
						 * Cabe�alho da conta
						 */
						html += "<tr class=\"fontePadrao\">";
						html += "  <td align=\"left\" colspan=\"3\"><b>" + btpContaList[i].conTxtDescricao + " (" + btpContaList[i].conNumNivel + ")</b></td>";
						html += "</tr>";

						/*
						 * Cabe�alho de movimentos
						 */
						html += "<tr class=\"fontePadrao\">";
						html += "  <td align=\"left\"><b>Descri��o</b></td>";
						html += "  <td align=\"center\"><b>Sigla</b></td>";
						html += "  <td align=\"right\"><b>Valor</b></td>";
						html += "</tr>";

						var btpMovimentoList = btpContaList[i].btpMovimentoList;

						for (var j = 0; j < btpMovimentoList.length; j++)
						{
							html += "<tr class=\"fontePadrao\">";
							html += "  <td nowrap=\"nowrap\" align=\"left\">" + btpMovimentoList[j].map.auxTxtTipo + "</td>";
							html += "  <td align=\"center\">" + btpMovimentoList[j].map.auxTxtSiglaTipo + "</td>";
							html += "  <td>" + numberObjectToStringMoney(btpMovimentoList[j].movVlrMovimentado) + "</td>";
							html += "</tr>";

							if (btpMovimentoList[j].map.auxTxtSiglaTipo == "ANT")
								totAcumAnt = btpMovimentoList[j].movVlrMovimentado;
							if (btpMovimentoList[j].map.auxTxtSiglaTipo == "REN")
								totRendMes = btpMovimentoList[j].movVlrMovimentado;
							if (btpMovimentoList[j].map.auxTxtSiglaTipo == "DES")
								totDespMes = btpMovimentoList[j].movVlrMovimentado;
						}

						totGeral += totRendMes - totDespMes + totAcumAnt;

						html += "<tr class=\"fontePadrao\">";
						html += "  <td nowrap=\"nowrap\" align=\"left\">Economia do M�s</td>";
						html += "  <td align=\"center\">ECO</td>";
						html += "  <td>" + numberObjectToStringMoney(totRendMes - totDespMes) + "</td>";
						html += "</tr>";

						html += "<tr class=\"fontePadrao\">";
						html += "  <td nowrap=\"nowrap\" align=\"left\">Saldo Acumulado</td>";
						html += "  <td align=\"center\">ACU</td>";
						html += "  <td>" + numberObjectToStringMoney(totRendMes - totDespMes + totAcumAnt) + "</td>";
						html += "</tr>";

						var graph = "";
						graph += "<h3>" + btpContaList[i].conTxtDescricao + "</h3>";
						graph += "<div id=\"conta" + btpContaList[i].conCodConta + "\" style=\"width: 99%;\"></div>";
						$("#graph01Container").append(graph);
					}

					html += "<tr class=\"fontePadrao\">";
					html += "  <td align=\"left\" colspan=\"3\"><b>Saldo</b></td>";
					html += "</tr>";

					html += "<tr class=\"fontePadrao\">";
					html += "  <td nowrap=\"nowrap\" align=\"left\">Saldo Total</td>";
					html += "  <td align=\"center\">TOT</td>";
					html += "  <td>" + numberObjectToStringMoney(totGeral) + "</td>";
					html += "</tr>";

					$("#tblResumo").find("[class='fontePadrao']").remove();
					$("#tblResumo").append(html);

					$("#graph01Container").removeAttr("class");
				}
			});
		}
	});
}

function stringDateToObjectDate(date)
{
	if (date != "")
		return new Date(date.split("/")[2] + "/" + date.split("/")[1] + "/" + date.split("/")[0]);

	return null;
}

function stringMoneyToNumberObject(money)
{
	money = money.replace(".", "");
	money = money.replace(",", ".");

	return money;
}

/**
 * Converte um valor num�rico para um objeto String e no formato de moeda brasileira
 * @param number valor a ser convertido
 * @returns valor num�rico convertido em String
 */
function numberObjectToStringMoney(valor, precision)
{
	if (valor == null)
		valor = 0;

	if (precision == null)
		precision = 2;

	valor = parseFloat(valor).toFixed(precision);

	var valorText = valor + "";
	if (valorText.indexOf(".") < 0)
	{
		valorText += ".";

		for (var i = 0; i < precision; i++)
			valorText += "0";
	}

	var tmp2 = valorText.replace(/[\.,]/g, ""); // "limpa" numero
	tmp2 = tmp2.replace(/^0+/, ""); // remove zeros a esquerda

	var npts = parseInt((tmp2.length - 3) / 3); // quantos pontos de milhar?

	while (tmp2.length < (precision + 1))
		tmp2 = "0" + tmp2;
  
	tmp2 = tmp2.substr(0, tmp2.length - precision) + "," + tmp2.substr(tmp2.length - precision);

	for (var p = 0; p < npts; p++)
	{
		var pos = tmp2.length - 3 * (p + 2) - p;
		tmp2 = tmp2.substr(0, pos) + "." + tmp2.substr(pos);
	}

	return tmp2;
}

//function numberObjectToStringMoney(valor)
//{
//	if (valor == null)
//		valor = 0;
//
//	valor = parseFloat(valor).toFixed(2);
//
//   var valorText = valor + "";
//   if (valorText.indexOf(".") < 0)
//   {
//       valorText += ".00";
//   }
//
//   var tmp2 = valorText.replace(/[\.,]/g, ""); // "limpa" numero
//   tmp2 = tmp2.replace(/^0+/, ""); // remove zeros a esquerda
//
//   var npts = parseInt((tmp2.length - 3) / 3); // quantos pontos de milhar?
//
//   while (tmp2.length < 3)
//       tmp2 = "0" + tmp2;
//  
//   tmp2 = tmp2.substr(0, tmp2.length - 2) + "," + tmp2.substr(tmp2.length - 2);
//
//   for (var p=0; p < npts; p++)
//   {
//       var pos = tmp2.length - 3 * (p + 2) - p;
//       tmp2 = tmp2.substr(0, pos) + "." + tmp2.substr(pos);
//   }
//
//   return tmp2;
//}

//function validateContaOrigemDestino()
//{
//	var txtNumParcela = $("#divMovimentoFrm").find("[name='movNumParcela']");
//
//	var txtContaOri = $("#divMovimentoFrm").find("[name='conContaOrigem']");
//	var txtContaDes = $("#divMovimentoFrm").find("[name='conContaDestino']");
//
//	var numNivelOri = "";
//	var numNivelDes = "";
//	for (i = 0; i < txtNumParcela.length; i++)
//	{
//		numNivelOri = txtContaOri.eq(i).find("option:selected").text();
//		numNivelDes = txtContaDes.eq(i).find("option:selected").text();
//
//		// Verifica e impede movimentos entre contas iguais.
//		if (numNivelOri == numNivelDes)
//		{
//			if (!confirm("Contas origem e destino iguais na parcela " + txtNumParcela.eq(i).val() + ". Deseja continuar?"))
//				return false;
//		}
//
//		/*
//		 * Verifica e impede movimentos dos tipos:
//		 * Saldo -> Entrada (1 para 2)
//		 * Entrada -> Entrada (2 para 2)
//		 * Entrada -> Sa�da (2 para 3)
//		 * Sa�da -> Saldo (3 para 1)
//		 * Sa�da -> Entrada (3 para 2)
//		 * Sa�da -> Sa�da (3 para 3)
//		 */
//		if (numNivelOri.split(".")[0] == 1 && numNivelDes.split(".")[0] == 2)
//		{
//			alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nSaldo -> Entrada");
//			return false;
//		}
//		else if (numNivelOri.split(".")[0] == 2 && numNivelDes.split(".")[0] == 2)
//		{
//			alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nEntrada -> Entrada");
//			return false;
//		}
//		else if (numNivelOri.split(".")[0] == 2 && numNivelDes.split(".")[0] == 3)
//		{
//			alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nEntrada -> Sa�da");
//			return false;
//		}
//		else if (numNivelOri.split(".")[0] == 3 && numNivelDes.split(".")[0] == 1)
//		{
//			alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nSa�da -> Saldo");
//			return false;
//		}
//		else if (numNivelOri.split(".")[0] == 3 && numNivelDes.split(".")[0] == 2)
//		{
//			alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nSa�da -> Entrada");
//			return false;
//		}
//		else if (numNivelOri.split(".")[0] == 3 && numNivelDes.split(".")[0] == 3)
//		{
//			alert("Movimento n�o permitido na parcela " + txtNumParcela.eq(i).val() + ":\n\nSa�da -> Sa�da");
//			return false;
//		}
//
//		/*
//		 * Se o movimento for uma transfer�ncia, ou seja, entre contas de saldo, e for uma transferencia permitida
//		 * ent�o verifica e impede
//		 * movimentos entre contas de saldo diferentes:
//		 * Movimento -> Virtual (M para V)
//		 * Virtual -> Movimento (V para M)
//		 */
////		if ((numNivelOri.indexOf("(M)") == -1) && (numNivelOri.split(".")[0] != numNivelDes.split(".")[0] || numNivelOri.split(".")[1] != numNivelDes.split(".")[1]))
////		{
////			alert("Transfer�ncia n�o permitida na parcela " + txtNumParcela.eq(i).val() + ":\n\nTransfer�ncia entre contas diferentes s� � permitida entre contas de movimento");
////			return false;
////		}
//
//		/*
//		 * Se o movimento for uma transfer�ncia, ou seja, entre contas de saldo, ent�o impede movimentos dos tipos:
//		 * Movimento -> Virtual (M para V)
//		 * Virtual -> Movimento (V para M)
//		 */
//		if (numNivelOri.indexOf("(M)") != -1 && numNivelDes.indexOf("(V)") != -1)
//		{
//			alert("Transfer�ncia n�o permitida na parcela " + txtNumParcela.eq(i).val() + ":\n\nMovimento -> Virtual");
//			return false;
//		}
//		else if (numNivelOri.indexOf("(V)") != -1 && numNivelDes.indexOf("(M)") != -1)
//		{
//			alert("Transfer�ncia n�o permitida na parcela " + txtNumParcela.eq(i).val() + ":\n\nVirtual -> Movimento");
//			return false;
//		}
//	}
//
//	return true;
//}

/**
 * Dado um item especificado em <code>auxNumItem</code>, tem a finalidade de multiplicar
 * a quantidade informada do item por seu valor unit�rio. O resultado � inserido no campo
 * "Vlr. Total". 
 * @param auxNumItem
 */
function calcAuxVlrTotal(auxNumItem)
{
	var qtd = stringMoneyToNumberObject($("#divObi" + auxNumItem).find("[name='obiNumQuantidade']").val());
	var vlr = stringMoneyToNumberObject($("#divObi" + auxNumItem).find("[name='obiVlrUnidade']").val());

	var tot = 0;
	if (qtd != "" && qtd != "0,00" && vlr != "" && vlr != "0,00")
		tot = parseFloat(qtd) * parseFloat(vlr);

	$("#divObi" + auxNumItem).find("[name='auxVlrTotal']").val(numberObjectToStringMoney(tot));
}

/**
 * Respons�vel por calcular o somat�rio de valores totais dos itens do objetivo.
 * O resultado ser� inserido em uma �rea de resumo da se��o de itens.
 */
function sumTotalValueObiObjetivoItem()
{
	var liList = $("#divObjetivoItemFrm").find("li");
	var sumTotal = 0;
	
	for (var i = 0; i < liList.length; i++)
	{
		if (liList.eq(i).find("[name='auxVlrTotal']").val() != "")
		{
			var tmp = stringMoneyToNumberObject(liList.eq(i).find("[name='auxVlrTotal']").val());
			sumTotal += parseFloat(tmp);
		}
	}

	$("#obiTotalValue").html("Valor total dos itens: " + numberObjectToStringMoney(sumTotal));
}

/**
 * Respons�vel por calcular o somat�rio de valores totais das parcelas do objetivo.
 * O resultado ser� inserido em uma �rea de resumo da se��o de parcelas.
 */
function sumTotalValueMovMovimento()
{
	var liList = $("#divMovimentoFrm").find("li");
	var sumTotal = 0;
	
	for (var i = 0; i < liList.length; i++)
	{
		if (liList.eq(i).find("[name='movVlrMovimentado']").val() != "")
		{
			var tmp = stringMoneyToNumberObject(liList.eq(i).find("[name='movVlrMovimentado']").val());
			sumTotal += parseFloat(tmp);
		}
	}

	$("#movTotalValue").html("Valor total das parcelas: " + numberObjectToStringMoney(sumTotal));
}

function maskTextField(auxNumItem)
{
	alert($("#divObi" + auxNumItem).find("[name='obiNumQuantidade']").val());

	$("#divObi" + auxNumItem).find("[name='obiNumQuantidade']").maskMoney()
}

function btnFiltrarClick()
{
	var map = {
		numMes: $("[name='btpObjetivo.map.numMes']").val(), 
		numAno: $("[name='btpObjetivo.map.numAno']").val()
	};

	var btpObjetivo = {
		map: map
	};

	execSelectObjetivo(btpObjetivo);
}

//function execPrepareEddition(row)
//{
//	btpObjetivo = {
//		objCodObjetivo: row.find("[name='btpObjetivo.objCodObjetivo']").val()
//	}
//
//	DwrObjetivo.showEditForm(btpObjetivo,
//	{
//		callback:function(btpObjetivoList)
//		{
//			btpObjetivo = btpObjetivoList[0];
//
//			var frm = $("[name='FrmObjetivo']");
//			frm.find("[name='btpObjetivo.objCodObjetivo']").val(btpObjetivo.objCodObjetivo);
//			frm.find("[name='btpObjetivo.objTxtDescricao']").val(btpObjetivo.objTxtDescricao);
//			frm.find("[name='btpObjetivo.btpEstabelecimento.estCodEstabelecimento']").val(btpObjetivo.btpEstabelecimento.estCodEstabelecimento);
//
//			$("#divObjetivoItemFrm").find("[id^='divObi']").remove();
//			$("#divMovimentoFrm").find("[id^='divMov']").remove();
//			auxNumItem = 0;
//			auxNumParcela = 0;
//
//			for (var i = 0; i < btpObjetivo.btpObjetivoItemList.length; i++)
//				addObjetivoItemFrm(btpObjetivo.btpObjetivoItemList[i]);
//
//			for (var i = 0; i < btpObjetivo.btpMovimentoList.length; i++)
//				addMovimentoFrm(btpObjetivo.btpMovimentoList[i]);
//
//			$("[name='btnAlterar']").show();
//			$("[name='btnCancelar']").show();
//			$("[name='btnConfirmar']").hide();
//			$("[name='btnFiltrar']").hide();
//		}
//	});
//}

function removeCarousel(divClass)
{
	var obiForm = $("div." + divClass);

	obiForm.parent().find("[class^='carousel-control']").remove();

	obiForm.find("[id^='divObi']").removeAttr("style");
	obiForm.find("ul").removeAttr("style");

	var temp = obiForm.find("ul");

	obiForm.attr("class", divClass);
	obiForm.find("[class='carousel-wrap']").remove();
	obiForm.find("[class='center-wrap']").remove();

	obiForm.html(temp);
}

function btnAdicionarItemClick()
{
	addObjetivoItemFrm(null);
}

function btnAdicionarParcelaClick()
{
	addMovimentoFrm(null);
}

function btnCancelarClick()
{
	jvsObjetivo.resetForm();

	$("[name='btnAlterar']").hide();
	$("[name='btnCancelar']").hide();
	$("[name='btnConfirmar']").show();
	$("[name='btnFiltrar']").show();

	alert("Altera��o cancelada com sucesso!");
}

function comboTemCodTemplateChange(obj)
{
	if (obj.val() == null || obj.val() == "")
	{
		resetTemplate()
		return false;
	}

	var btpTemplate = {
		temCodTemplate: obj.val()
	};

	var btpTemplateRegra = {
		btpTemplate: btpTemplate
	};

	DwrTemplateRegra.actExecSelect(btpTemplateRegra,
	{
		callback:function(btpTemplateRegraList)
		{
			for (var i = 0; i < btpTemplateRegraList.length; i++)
			{
				var funcao = btpTemplateRegraList[i].btpRegra.regTxtNomeFuncao;

				if (btpTemplateRegraList[i].btpRegra.regFlgValorAssociado)
					eval(funcao + "(" + btpTemplateRegraList[i].terCodValorAssociado + ");");
				else
					eval(funcao + "();");

				jvsObjetivo.resetForm();
			}
		}
	});
}

function objTxtDescricaoBlur()
{
	if ($("[name='obiTxtDescricao']").length > 0)
		$("[name='obiTxtDescricao']").eq(0).val($("[name='FrmObjetivo']").find("[name='btpObjetivo.objTxtDescricao']").val());
}

function btnNovoTemplateClick()
{
	showPopupTemplateRegra();
}

/*
 * Init area
 */
function initPage()
{
//	getHtmlContaComboOptionList();
//	getHtmlFormaPagamentoOptionList();
//	addObjetivoItemFrm();
}