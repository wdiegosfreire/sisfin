$(document).ready(function()
{
	$(".puidynamicform").puidynamicform();
});

var jvsPainelMovimento = new JvsPainelMovimento();
function JvsPainelMovimento()
{
	/* +----------------------------------------------------------------------+
	 * | FunÃ§Ãµes PÃºblicas                                                     |
	 * +----------------------------------------------------------------------+
	 */
	this.loadMainPage = function()
	{
		var map = {
			numAno: $("[name='btpObjetivo.map.numAno']").val(),
			numMes: $("[name='btpObjetivo.map.numMes']").val()
		};

		var btpObjetivo = {
			map: map
		};

		DwrPainelMovimento.execute(btpObjetivo, "actShowMainPage",
		{
			callback:function(dwrReturn)
			{
				loadMovimentoMesTable(dwrReturn, map.numAno, map.numMes);
			}
		});
	};

	this.cacularValorTotalDoItem = function()
	{
		var painelIte = $("#panelIte");

		var qtd = stringMoneyToNumberObject(painelIte.find("[name='auxObiNumQuantidade']").val());
		var vlr = stringMoneyToNumberObject(painelIte.find("[name='auxObiVlrUnidade']").val());

		var tot = 0;
		if (qtd != "" && qtd != "0,00" && vlr != "" && vlr != "0,00")
			tot = parseFloat(qtd) * parseFloat(vlr);

		painelIte.find("[name='auxObiVlrTotal']").val(numberObjectToStringMoney(tot));
	};

	this.btnEditarClick = function(objCodObjetivo)
	{
		$("#puidialog").remove();
		$("#popupWindowContainer").after("<div id=\"puidialog\"></div>");
		$("#puidialog").attr("title", "Edição de Despesas - Formulário Completo");
		$("#puidialog").append("<iframe style=\"width: 100%; height: 100%; border: none;\" src=\"objetivo.do?cmd=actShowEditForm\"></iframe>");

		var btnConfirmar = {
			text: "Confirmar",
			icon: "ui-icon-check",
			click: function() {
				$("#puidialog").puidialog("hide");
			}
		}

		var btnCancelar = {
			text: "Cancelar",
			icon: "ui-icon-close",
			click: function() {
				$("#puidialog").puidialog("hide");
			}
		}

		$("#puidialog").puidialog({
			width: "1100px",
			height: "500px",
			showEffect: "fade",
			hideEffect: "fade",
			modal: true,
			closeOnEscape: true,
			resizable: false,
			buttons: [btnConfirmar, btnCancelar]
		});

		$("#puidialog").puidialog("show");
	};

	this.btnIncluirClick = function()
	{
		$("#puidialog").remove();
		$("#popupWindowContainer").after("<div id=\"puidialog\"></div>");
		$("#puidialog").attr("title", "Cadastro de Despesas - Formulário Completo");
		$("#puidialog").append("<iframe style=\"width: 100%; height: 100%; border: none;\" src=\"objetivo.do?cmd=actShowInclForm\"></iframe>");
		
		var btnConfirmar = {
			text: "Confirmar",
			icon: "ui-icon-check",
			click: function() {
				$("#puidialog").puidialog("hide");
			}
		}
		
		var btnCancelar = {
			text: "Cancelar",
			icon: "ui-icon-close",
			click: function() {
				$("#puidialog").puidialog("hide");
			}
		}
		
		$("#puidialog").puidialog({
			width: "1100px",
			height: "500px",
			showEffect: "fade",
			hideEffect: "fade",
			modal: true,
			closeOnEscape: true,
			resizable: false,
			buttons: [btnConfirmar, btnCancelar]
		});
		
		$("#puidialog").puidialog("show");
	};

//	this.btnEditarClick = function(objCodObjetivo)
//	{
//		$("#puidialog").remove();
//		$("#popupWindowContainer").after("<div id=\"puidialog\"></div>");
//		
//		btpObjetivo = {
//			objCodObjetivo: objCodObjetivo
//		};
//		
//		DwrPainelMovimento.execute(btpObjetivo, "actShowEditForm",
//			{
//			callback:function(dwrReturn)
//			{
//				btpObjetivo = dwrReturn.mapResult.btpObjetivoList[0];
//				
//				montarFormularioObjetivoCompleto(dwrReturn);
//				
//				var btnConfirmar = {
//					text: "Confirmar",
//					icon: "ui-icon-check",
//					click: function() {
//						$("#puidialog").puidialog("hide");
//					}
//				}
//				
//				var btnCancelar = {
//					text: "Cancelar",
//					icon: "ui-icon-close",
//					click: function() {
//						$("#puidialog").puidialog("hide");
//					}
//				}
//				
//				$("#puidialog").puidialog({
//					width: "1100px",
//					height: "500px",
//					showEffect: "fade",
//					hideEffect: "fade",
//					modal: true,
//					closeOnEscape: true,
//					resizable: false,
//					buttons: [btnConfirmar, btnCancelar]
//				});
//				
//				$("#puidialog").puidialog("show");
//			}
//			});
//	};

	this.btnDetalharClick = function(objCodObjetivo)
	{
		alert("Detalhar " + objCodObjetivo);
	};

	this.btnInserirObjetivoItemClick = function()
	{
		adicionarObjetivoItem();
	};

	this.btnRemoverObjetivoItemClick = function(obj)
	{
		if (confirm("Deseja excluir o item selecionado?"))
		{
			removerObjetivoItem(obj);
			reordenarNumeracaoItens();
		}
	};

	this.btnExcluirClick = function(objCodObjetivo)
	{
		var msgConfirm = "";
		msgConfirm += "Deseja excluir permanentemente o registro selecionado? (" + objCodObjetivo + ")\n\n";
		msgConfirm += "Aviso: ao excluir este registro serão excluídos também ";
		msgConfirm += "todos os movimentos cadastrados.";

		if (!confirm(msgConfirm))
			return;

		btpObjetivo = {
			objCodObjetivo: objCodObjetivo
		}

		DwrObjetivo.execute(btpObjetivo, "actExecDelete",
		{
			callback:function(dwrReturn)
			{
				jvsPainelMovimento.loadMainPage();
				messageManager(dwrReturn);
			}
		});
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas                                                     |
	 * +----------------------------------------------------------------------+
	 */

	/**
	 * Monta a tabela do resumo mensal por conta
	 */
	function loadMovimentoMesTable(dwrReturn, numAno, numMes)
	{
		var datasource = new Array();
		for (var i = 0; i < dwrReturn.mapResult.btpObjetivoList.length; i++)
		{
			var btpObjetivo = dwrReturn.mapResult.btpObjetivoList[i];

			for (var j = 0; j < btpObjetivo.btpMovimentoList.length; j++)
			{
				var btpMovimento = btpObjetivo.btpMovimentoList[j];

				var row = {
					objCodObjetivo: btpObjetivo.objCodObjetivo,
					movDatVencimento: btpMovimento.movDatVencimento.toFormat(datePatternEnum.DIA_MES_ANO),
					movDatPagamento: btpMovimento.movDatPagamento.toFormat(datePatternEnum.DIA_MES_ANO),
					objTxtDescricao: btpObjetivo.objTxtDescricao,
					conCodContaOrigem: btpMovimento.btpContaOrigem.btpContaPai.btpContaPai.conTxtDescricao + ": " + btpMovimento.btpContaOrigem.btpContaPai.conTxtDescricao + ": " + btpMovimento.btpContaOrigem.conTxtDescricao,
					conCodContaDestino: btpMovimento.btpContaDestino.btpContaPai.btpContaPai.conTxtDescricao + ": " + btpMovimento.btpContaDestino.btpContaPai.conTxtDescricao + ": " + btpMovimento.btpContaDestino.conTxtDescricao,
					estNomEstabelecimento: (btpObjetivo.btpEstabelecimento.estNomEstabelecimento == null ? "" : btpObjetivo.btpEstabelecimento.estNomEstabelecimento),
					fopNomFormaPagamento: btpMovimento.btpFormaPagamento.fopNomFormaPagamento,
					movNumParcela: btpMovimento.movNumParcela + "/" + btpMovimento.map.auxQtdParcela,
					movVlrMovimentado: "R$ " + numberObjectToStringMoney(btpMovimento.movVlrMovimentado)
				};
			}

			datasource.push(row);
		}

		var hStyle = "";
		hStyle += "font-size: 12px; padding: 3px 1px;";

		var bStyle = "";
		bStyle += "font-size: 12px; white-space: normal; padding: 1px;";

		$("#tblMovimentoMes").puidatatable({
			columns: [
				{field: "objCodObjetivo", headerText: "Cod.", sortable: false, headerStyle: hStyle + "width: 40px;", bodyStyle: bStyle + "text-align: center;"},
				{field: "movDatVencimento", headerText: "Venc.", sortable: true, headerStyle: hStyle + "width: 70px;", bodyStyle: bStyle + "text-align: center;"},
				{field: "movDatPagamento", headerText: "Pag.", sortable: true, headerStyle: hStyle + "width: 70px;", bodyStyle: bStyle + "text-align: center;"},
				{field: "objTxtDescricao", headerText: "Objetivo", sortable: true, headerStyle: hStyle, bodyStyle: bStyle},
				{field: "conCodContaOrigem", headerText: "Origem", sortable: true, headerStyle: hStyle, bodyStyle: bStyle},
				{field: "conCodContaDestino", headerText: "Destino", sortable: true, headerStyle: hStyle, bodyStyle: bStyle},
				{field: "estNomEstabelecimento", headerText: "Estabelec.", headerStyle: hStyle, bodyStyle: bStyle + "text-align: center;"},
				{field: "fopNomFormaPagamento", headerText: "Forma.", headerStyle: hStyle, bodyStyle: bStyle + "text-align: center;"},
				{field: "movNumParcela", headerText: "Parc.", headerStyle: hStyle + "width: 60px;", bodyStyle: bStyle + "text-align: center;"},
				{field: "movVlrMovimentado", headerText: "Valor", headerStyle: hStyle + "width: 90px; text-align: right;", bodyStyle: bStyle + "text-align: right;"}
			],
			datasource: datasource,
			selectionMode: "single",
			rowSelect: function(event, data)
			{
				$("#puicontextmenu").remove();
				$("#popupWindowContainer").after("<ul id=\"puicontextmenu\"></ul>");

				var html = "";
				html += "<li><a data-icon=\"ui-icon-search\" onclick=\"jvsPainelMovimento.btnDetalharClick(" + data.objCodObjetivo + ")\">Detalhar</a></li>";

				var aux = data.movNumParcela.split("/")[0];
				if (aux == "1")
				{
					html += "<li><a data-icon=\"ui-icon-pencil\" onclick=\"jvsPainelMovimento.btnEditarClick(" + data.objCodObjetivo + ");\">Editar</a></li>";
					html += "<li><a data-icon=\"ui-icon-close\" onclick=\"jvsPainelMovimento.btnExcluirClick(" + data.objCodObjetivo + ");\">Excluir</a></li>";
				}

				$("#puicontextmenu").html(html);
				$("#puicontextmenu").puicontextmenu({
					target: $(this)
				});
			},
			rowUnselect: function(event, data) {
				$("#puicontextmenu").html("");
			}
		});
	};

	function messageManager(dwrReturn)
	{
		for (var i = 0; i < dwrReturn.msgContainer.length; i++)
			message.addWarn("Mensagem", dwrReturn.msgContainer[i]);

		message.show();
	};

	/**
	 * Converte um valor numï¿½rico para um objeto String e no formato de moeda brasileira
	 * @param number valor a ser convertido
	 * @returns valor numï¿½rico convertido em String
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
	};

	function montarFormularioObjetivoCompleto(dwrReturn)
	{
		var html = "";

		/*
		 * Construindo a tabela com as informaï¿½ï¿½es do objetivo
		 */
		html += "<table style=\"border: none; width: 100%; font-size: 12px;\">";
		html += "  <tr>";
		html += "    <td style=\"width: 100px;\">Descriï¿½ï¿½o:</td>";
		html += "    <td><input type=\"text\" name=\"btpObjetivo.objTxtDescricao\" style=\"width: 100%;\" /></td>";
		html += "  </tr>";
		html += "  <tr>";
		html += "    <td style=\"width: 100px;\">Estabelecimento:</td>";
		html += "    <td><select name=\"btpObjetivo.btpEstabelecimento.estCodEstabelecimento\" style=\"width: 500px;\"></select></td>";
		html += "  </tr>";
		html += "</table><br />";

		/*
		 * Construindo o painel de itens do objetivo
		 */
		html += "<div id=\"panelIte\" class=\"puipanel\" title=\"Itens\">";
		html += "  <table style=\"width: 100%; font-size: 12px; border-collapse: collapse;\">";
		html += "    <thead style=\"text-align: left;\">";
		html += "      <tr>";
		html += "        <th style=\"width: 40px;\">No.<th>";
		html += "        <th>Descriï¿½ï¿½o<th>";
		html += "        <th style=\"width: 100px; text-align: right;\">Quantidade<th>";
		html += "        <th style=\"width: 100px; text-align: right;\">Vlr. Unitï¿½rio<th>";
		html += "        <th style=\"width: 100px; text-align: right;\">Total<th>";
		html += "        <th style=\"width: 1px;\">&nbsp;<th>";
		html += "      </tr>";
		html += "      <tr>";
		html += "        <th><input type=\"text\" name=\"auxObiNumItem\" style=\"width: 100%;\" tabindex=\"-1\" readonly=\"readonly\"><th>";
		html += "        <th><input type=\"text\" name=\"auxObiTxtDescricao\" style=\"width: 100%;\"><th>";
		html += "        <th><input type=\"text\" name=\"auxObiNumQuantidade\" style=\"width: 100%;\" onblur=\"jvsPainelMovimento.cacularValorTotalDoItem();\"><th>";
		html += "        <th><input type=\"text\" name=\"auxObiVlrUnidade\" style=\"width: 100%;\" onblur=\"jvsPainelMovimento.cacularValorTotalDoItem();\"><th>";
		html += "        <th><input type=\"text\" name=\"auxObiVlrTotal\" style=\"width: 100%;\" readonly=\"readonly\" tabindex=\"-1\"><th>";
		html += "        <th><span onclick=\"jvsPainelMovimento.btnInserirObjetivoItemClick();\">Add.</span><th>";
		html += "      </tr>";
		html += "    </thead>";
		html += "    <tbody>";
		html += "    </tbody>";
		html += "  </table>";
		html += "</div><br/>";

		/*
		 * Construindo o painél de parcelas (movimentos) do objetivo
		 */ 
		html += "<div id=\"panelMov\" class=\"puipanel\" title=\"Parcelas\">";
		html += "  <table style=\"width: 100%; font-size: 12px;\">";
		html += "    <thead>";
		html += "      <tr>";
		html += "        <td>No.<td>";
		html += "        <td>Valor<td>";
		html += "        <td>Vencimento<td>";
		html += "        <td>Pagamento<td>";
		html += "        <td>Origem<td>";
		html += "        <td>Destino<td>";
		html += "        <td>Forma<td>";
		html += "        <td>&nbsp;<td>";
		html += "      </tr>";
		html += "    </thead>";
		html += "    <tbody>";
		html += "    </tbody>";
		html += "  </table>";
		html += "  <input type=\"button\" name=\"btnAdicionarParcela\" value=\"Adicionar Parcela\">";
		html += "</div>";

		$("#puidialog").append(html);
		$("#puidialog").attr("title", "Formulï¿½rio de Cadastro/Ediï¿½ï¿½o de Objetivos");

		$("#puidialog").find(":text").each(function() {
			$(this).puiinputtext();
		});

		$("#puidialog").find(":button").each(function() {
			$(this).css("width", "130px");
			$(this).puibutton();
		});

		$("#puidialog").find(".puipanel").each(function() {
			$(this).puipanel({toggleable: true});
		});

		/*
		 * Configurando o combobox de estabelecimentos
		 */
		var cbxEstabelecimento = $("[name='btpObjetivo.btpEstabelecimento.estCodEstabelecimento']");

		cbxEstabelecimento.puidropdown({  
			filter: true,
			filterMatchMode: "contains"
		});

		var cbxDataSource = new Array();

		var option = {
			value: "",
			label: "Selecione"
		};

		cbxDataSource.push(option);

		for (var i = 0; i < dwrReturn.mapResult.btpEstabelecimentoList.length; i++)
		{
			var btpEstAux = dwrReturn.mapResult.btpEstabelecimentoList[i];

			var option = {
				value: btpEstAux.estCodEstabelecimento,
				label: btpEstAux.estNomEstabelecimento
			};

			cbxDataSource.push(option);
		}

		cbxEstabelecimento.puidropdown("option", "data", cbxDataSource);

		var txtQtdFormat = $("#puidialog").find("[name='auxObiNumQuantidade']");
		txtQtdFormat.css("width", "100px");
		txtQtdFormat.css("text-align", "right");
		txtQtdFormat.maskMoney({decimal: ",", thousands: ".", allowZero: false, precision: 3});

		var txtVlrFormat = $("#puidialog").find("[name='auxObiVlrUnidade'], [name='auxObiVlrTotal']");
		
		txtVlrFormat.css("width", "100px");
		txtVlrFormat.css("text-align", "right");
		txtVlrFormat.maskMoney({decimal: ",", thousands: ".", allowZero: false, precision: 2, allowNegative: true});

		return html;
	};

	function adicionarObjetivoItem()
	{
		var painelIte = $("#panelIte");
		/*
		 * Validar o preenchimento dos campos obrigatï¿½rios
		 */
		var msg = "";
		if (painelIte.find("[name='auxObiTxtDescricao']").val() == "")
			msg += "\n- Descriï¿½ï¿½o";
		if (painelIte.find("[name='auxObiNumQuantidade']").val() == "")
			msg += "\n- Quantidade";
		if (painelIte.find("[name='auxObiVlrUnidade']").val() == "")
			msg += "\n- Vlr. Unitï¿½rio";
		if (painelIte.find("[name='auxObiVlrTotal']").val() == "")
			msg += "\n- Total";

		if (msg != "")
		{
			alert("Os campos descritos abaixo sï¿½o de preenchimento obrigtï¿½rio:\n" + msg);
			return false;
		}

		var html = "";
		html += "";
		html += "<tr>";
		html += "  <td>";
		html += "    <span>" + painelIte.find("[name='auxObiNumItem']").val() + "</span>";
		html += "    <input type=\"hidden\" name=\"obiNumItem\" />";
		html += "  </td>";
		html += "  <td>&nbsp;</td>";
		html += "  <td>";
		html += "    <span>" + painelIte.find("[name='auxObiTxtDescricao']").val() + "</span>";
		html += "    <input type=\"hidden\" name=\"obiTxtDescricao\" value=\"" + painelIte.find("[name='auxObiTxtDescricao']").val() + "\" />";
		html += "  </td>";
		html += "  <td>&nbsp;</td>";
		html += "  <td style=\"text-align: right;\">";
		html += "    <span>" + painelIte.find("[name='auxObiNumQuantidade']").val() + "</span>";
		html += "    <input type=\"hidden\" name=\"obiNumQuantidade\" value=\"" + painelIte.find("[name='auxObiNumQuantidade']").val() + "\" />";
		html += "  </td>";
		html += "  <td>&nbsp;</td>";
		html += "  <td style=\"text-align: right;\">";
		html += "    <span>" + painelIte.find("[name='auxObiVlrUnidade']").val() + "</span>";
		html += "    <input type=\"hidden\" name=\"obiVlrUnidade\" value=\"" + painelIte.find("[name='auxObiVlrUnidade']").val() + "\" />";
		html += "  </td>";
		html += "  <td>&nbsp;</td>";
		html += "  <td style=\"text-align: right;\">";
		html += "    <span>" + painelIte.find("[name='auxObiVlrTotal']").val() + "</span>";
		html += "    <input ";
		html += "      type=\"hidden\" ";
		html += "      name=\"obiVlrTotal\" ";
		html += "      value=\"" + painelIte.find("[name='auxObiVlrTotal']").val() + "\" ";
		html += "    />";
		html += "  </td>";
		html += "  <td>&nbsp;</td>";
		html += "  <td>";
		html += "    <span ";
		html += "      onclick=\"jvsPainelMovimento.btnRemoverObjetivoItemClick($(this));\" ";
		html += "      title=\"Clique para excluir este item.\" ";
		html += "      class=\"ui-icon ui-icon-minusthick\">";
		html += "    </span>";
		html += "  </td>";
		html += "</tr>";

		painelIte.find("tbody").append(html);
		painelIte.find(":text").val("");

		reordenarNumeracaoItens();
	};

	function removerObjetivoItem(obj)
	{
		obj.closest("tr").remove();
	};

	function stringMoneyToNumberObject(money)
	{
		money = money.replace(".", "");
		money = money.replace(",", ".");

		return money;
	};

	function reordenarNumeracaoItens()
	{
		var auxTrArray = $("#panelIte").find("tbody").find("tr");
		for (var i = 0; i < auxTrArray.length; i++)
		{
			var td = auxTrArray.eq(i).find("td").first();

			td.text(i + 1);
			td.find("text").val(i + 1);
		}
	};
}