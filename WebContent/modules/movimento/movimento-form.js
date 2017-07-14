var movimentoFormPopup = new MovimentoFormPopup();
function MovimentoFormPopup()
{
	/* +----------------------------------------------------------------------+
	 * | Variáveis Privadas Obrigatórias                                      |
	 * +----------------------------------------------------------------------+
	 */
	var jvsPopupParent = new JvsPopupParent();
	var popupId = "#movimentoFormPopup";
	var messageArray = new Array();

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */

	/**
	 * Tem as finalidades listada abaixo:
	 * 1) montar a estrutura HTML completa do popup, inclusive com seções que ficaraão escondidas
	 *    e que serão mostradas apenas em determinadas situações e ainda sem dados;
	 * 2) acrescentar a estrutura HTML ao "body" da página;
	 * 3) consultar via Ajax os dados necessários para a montagem dos campos HTML como, por exemplo, comboboxes (opcional);
	 * 4) preparar os componentes HTML com o PrimeUI.
	 */
	function buildPuidialog()
	{
		if ($(popupId).length > 0)
			return false;

		var html = "";
		html += "<div id=\"movimentoFormPopup\" title=\"Movimentos: Formulário de Cadastro/Edição\">";
		html += "  <input type=\"hidden\" name=\"popup.btpObjetivo.objCodObjetivo\" />";
		html += "  <div style=\"padding-bottom: 10px;\">";
		html += "    <input ";
		html += "      name=\"btnFormType\" ";
		html += "      value=\"Formulário Simples\" ";
		html += "      type=\"button\" ";
		html += "      style=\"width: 100% !important;\" ";
		html += "      onclick=\"movimentoFormPopup.btnFormTypeClick();\" ";
		html += "    />";
		html += "  </div>";

		html += "  <div style=\"overflow-y: auto; height: 409px;\">";
		html += "    <div class=\"ui-grid ui-grid-responsive fieldContainer\">";
		html += "      <div class=\"ui-grid-row\">";
		html += "        <div class=\"ui-grid-col-3 bold required\">Objetivo</div>";
		html += "        <div class=\"ui-grid-col-9\"><input type=\"text\" name=\"popup.btpObjetivo.objTxtDescricao\" class=\"width100pc\" /></div>";
		html += "      </div>";
		html += "      <div class=\"ui-grid-row\">";
		html += "        <div class=\"ui-grid-col-3 bold\">Estabelecimento</div>";
		html += "        <div class=\"ui-grid-col-9\">";
		html += "          <select name=\"popup.btpEstabelecimento.estCodEstabelecimento\" class=\"puicomboboxfilter\"></select>";
		html += "        </div>";
		html += "      </div>";
		html += "    </div>";

		html += "    <div data-name=\"divFormSimple\">";
		html += "      <div class=\"ui-grid ui-grid-responsive fieldContainer\">";
		html += "        <div class=\"ui-grid-row\">";
		html += "          <div class=\"ui-grid-col-3 bold required\">Valor</div>";
		html += "          <input type=\"text\" name=\"popup.btpObjetivoItem.obiVlrUnidade\" class=\"fieldMoney width150px\">";
		html += "        </div>";
		html += "        <div class=\"ui-grid-row\">";
		html += "          <div class=\"ui-grid-col-3 bold required\">Data</div>";
		html += "          <input type=\"text\" name=\"popup.btpMovimento.movDatVencimento\" class=\"formFieldDate width150px\">";
		html += "        </div>";
		html += "        <div class=\"ui-grid-row\">";
		html += "          <div class=\"ui-grid-col-3 bold required\">Origem</div>";
		html += "          <select name=\"popup.btpContaOrigem.conCodConta\" class=\"width100pc puicomboboxfilter\"><option value=\"\">Selecione...</option></select>";
		html += "        </div>";
		html += "        <div class=\"ui-grid-row\">";
		html += "          <div class=\"ui-grid-col-3 bold required\">Destino</div>";
		html += "          <select name=\"popup.btpContaDestino.conCodConta\" class=\"width100pc puicomboboxfilter\"><option value=\"\">Selecione...</option></select>";
		html += "        </div>";
		html += "        <div class=\"ui-grid-row\">";
		html += "          <div class=\"ui-grid-col-3 bold required\">Forma</div>";
		html += "          <select name=\"popup.btpFormaPagamento.fopCodFormaPagamento\" class=\"width100pc puicombobox\"><option value=\"\">Selecione...</option></select>";
		html += "        </div>";
		html += "      </div>";
		html += "    </div>";

		html += "    <div data-name=\"divFormFull\" style=\"display: none;\">";
		html += "      <div class=\"puidynamicform\" id=\"btpObjetivoItemForm\" data-allowmove=\"true\" title=\"Cadastro de Itens\">";
		html += "        <input type=\"text\" name=\"popup.btpObjetivoItem.obiCodObjetivoItem\" data-label=\"Cod.\" data-hidden=\"true\">";
		html += "        <input type=\"text\" name=\"popup.btpObjetivoItem.obiNumItem\" data-label=\"No.\" data-required=\"true\" class=\"fieldOnlyNumber\">";
		html += "        <input type=\"text\" name=\"popup.btpObjetivoItem.obiTxtDescricao\" data-label=\"Descrição\" data-required=\"true\" class=\"width100pc\">";
		html += "        <input type=\"text\" name=\"popup.btpObjetivoItem.obiNumQuantidade\" data-label=\"Quantidade\" data-required=\"true\" class=\"fieldQuantity width150px\" onblur=\"movimentoFormPopup.obiNumQuantidadeBlur();\">";
		html += "        <input type=\"text\" name=\"popup.btpObjetivoItem.obiVlrUnidade\" data-label=\"Valor\" data-required=\"true\" class=\"fieldMoney width150px\" onblur=\"movimentoFormPopup.obiVlrUnidadeBlur();\">";
		html += "        <input type=\"text\" name=\"popup.btpObjetivoItem.auxVlrTotal\" data-label=\"Total\" data-required=\"true\" class=\"width150px right\" readonly=\"readonly\">";
		html += "      </div><br />";
		html += "      <div class=\"puidynamicform\" id=\"btpMovimentoForm\" data-allowmove=\"true\" title=\"Cadastro de Parcelas\">";
		html += "        <input type=\"text\" name=\"popup.btpMovimento.movCodMovimento\" data-label=\"Cod.\" data-hidden=\"true\">";
		html += "        <input type=\"text\" name=\"popup.btpMovimento.movNumParcela\" data-label=\"No.\" data-required=\"true\" class=\"fieldOnlyNumber\">";
		html += "        <input type=\"text\" name=\"popup.btpMovimento.movVlrMovimentado\" data-label=\"Valor\" data-required=\"true\" class=\"fieldMoney width150px\">";
		html += "        <input type=\"text\" name=\"popup.btpMovimento.movDatVencimento\" data-label=\"Vencimento\" data-required=\"true\" class=\"formFieldDate width150px\">";
		html += "        <input type=\"text\" name=\"popup.btpMovimento.movDatPagamento\" data-label=\"Pagamento\" class=\"formFieldDate width150px\">";
		html += "        <select name=\"popup.btpContaOrigem.conCodConta\" data-label=\"Origem\" data-required=\"true\" class=\"width100pc puicombobox\"><option value=\"\">Selecione...</option></select>";
		html += "        <select name=\"popup.btpContaDestino.conCodConta\" data-label=\"Destino\" data-required=\"true\" class=\"width100pc puicombobox\"><option value=\"\">Selecione...</option></select>";
		html += "        <select name=\"popup.btpFormaPagamento.fopCodFormaPagamento\" data-label=\"Forma\" data-required=\"true\" class=\"width100pc puicombobox\"><option value=\"\">Selecione...</option></select>";
		html += "      </div>";
		html += "    </div>";
		html += "  </div>";

		html += "  <div class=\"ui-grid ui-grid-responsive\" style=\"font-size: small;\">";
		html += "    <div class=\"ui-grid-row\">";
		html += "      <div class=\"ui-grid-col-6\">Total dos Itens</div>";
		html += "      <div class=\"ui-grid-col-6\">Total das Parcelas</div>";
		html += "    </div>";
		html += "  </div>";
		html += "  <div class=\"ui-grid ui-grid-responsive buttonContainer\">";
		html += "    <div class=\"ui-grid-row\">";
		html += "      <div class=\"ui-grid-col-6\"><input type=\"button\" name=\"btnConfirmar\" value=\"Confirmar\" onclick=\"movimentoFormPopup.btnConfirmarClick();\"></div>";
		html += "      <div class=\"ui-grid-col-6\"><input type=\"button\" name=\"btnCancelar\" value=\"Cancelar\" onclick=\"movimentoFormPopup.btnCancelarClick();\"></div>";
		html += "    </div>";
		html += "  </div>";
		html += "</div>";

		// Chamando a função da classe "pai" responsável por verificar se a aplicação está rodando em um dispositivo mobile ou não e por montar o formulário apropriado
		jvsPopupParent.buildPuidialog(html);

		// Chamando as funções necessárias para o carregamento de comboboxes e outras validações de tela
		loadCbxEstabelecimento();
		loadCbxContaOrigem();
		loadCbxContaDestino();
		loadCbxFormaPagamento();

		// Chamando a função responsável por formatar os componentes html de acordo com o Prime UI e outras formatações personalizadas
		jvsEstrutura.prepareHtmlComponents($(popupId));

		return true;
	};

	/**
	 * Tem a finalidade de executar uma chamada Ajax e popular os campos de formulário com
	 * as informações retornadas, recebendo como parâmetro apenas o número da solicitação.
	 * 
	 * 1) Resetar os dados do formulário;
	 * 2) Executar uma chamada Ajax para recuperar as informações da solicitação tendo como chave de consulta o número;
	 * 3) Popular os campos de formulário com as informações retornadas da chamada Ajax.
	 */
	function setPopupValues(objCodObjetivo)
	{
		resetPopupValues();

		if (objCodObjetivo != undefined && objCodObjetivo != null && objCodObjetivo != "")
		{
			/* Regra:
			 * Montar a entidade BtpCompetencia, o qual será passada via Ajax como parâmetro 
			 */
			var btpObjetivo = {
				objCodObjetivo: objCodObjetivo
			};

			var btpMovimento = {
				btpObjetivo: btpObjetivo
			};

			var btpMovimentoList = new Array();
			btpMovimentoList.push(btpMovimento);

			var btpCompetencia = {
				btpMovimentoList: btpMovimentoList
			};
			
			DwrMovimentoNew.execute(btpCompetencia, "actShowEditForm",
			{
				callback:function(dwrReturn)
				{
					movimentoFormPopup.btnFormTypeClick("divFormFull");
					var btpObjetivo = dwrReturn.mapResult.btpObjetivoList[0];

					$(popupId).find("[name='popup.btpObjetivo.objCodObjetivo']").val(btpObjetivo.objCodObjetivo);
					$(popupId).find("[name='popup.btpObjetivo.objTxtDescricao']").val(btpObjetivo.objTxtDescricao);
					$(popupId).find("[name='popup.btpEstabelecimento.estCodEstabelecimento']").val(btpObjetivo.btpEstabelecimento.estCodEstabelecimento);

					// Criando uma área temporária para a montagem dos resultados das entidades BtpObjetivoItem e BtpMovimento
					$("html").append("<ul id=\"testResult\" style=\"display: none;\"></ul>");

					var html = "";

					// Montando a tabela de resultados para a entidade BtpObjetivoItem
					for (var i = 0; i < btpObjetivo.btpObjetivoItemList.length; i++)
					{
						var btpObjetivoItem = btpObjetivo.btpObjetivoItemList[i];
						var obiVlrTotal = btpObjetivoItem.obiNumQuantidade * btpObjetivoItem.obiVlrUnidade;
						html += "<li>";
						html += "  <input type=\"hidden\" value=\"" + btpObjetivoItem.obiCodObjetivoItem + "\" />";
						html += "  <input type=\"hidden\" value=\"" + btpObjetivoItem.obiNumItem + "\" />";
						html += "  <input type=\"hidden\" value=\"" + btpObjetivoItem.obiTxtDescricao + "\" />";
						html += "  <input type=\"hidden\" value=\"" + numberObjectToStringMoney(btpObjetivoItem.obiNumQuantidade, 3) + "\" />";
						html += "  <input type=\"hidden\" value=\"" + numberObjectToStringMoney(btpObjetivoItem.obiVlrUnidade) + "\" />";
						html += "  <input type=\"hidden\" value=\"" + numberObjectToStringMoney(obiVlrTotal) + "\" />";
						html += "</li>";
					}

					$("#testResult").html(html);
					$("#btpObjetivoItemForm").puidynamicform("setResultData", $("#testResult").find("li"));

					// Montando a tabela de resultados para a entidade BtpMovimento
					html = "";
					for (var i = 0; i < btpObjetivo.btpMovimentoList.length; i++)
					{
						var btpMovimento = btpObjetivo.btpMovimentoList[i];
						var obiVlrTotal = btpObjetivoItem.obiNumQuantidade * btpObjetivoItem.obiVlrUnidade;
						html += "<li>";
						html += "  <input type=\"hidden\" value=\"" + btpMovimento.movCodMovimento + "\" />";
						html += "  <input type=\"hidden\" value=\"" + btpMovimento.movNumParcela + "\" />";
						html += "  <input type=\"hidden\" value=\"" + numberObjectToStringMoney(btpMovimento.movVlrMovimentado) + "\" />";
						html += "  <input type=\"hidden\" value=\"" + btpMovimento.movDatVencimento.toFormat(datePatternEnum.DIA_MES_ANO) + "\" />";
						html += "  <input type=\"hidden\" value=\"" + btpMovimento.movDatPagamento.toFormat(datePatternEnum.DIA_MES_ANO) + "\" />";
						html += "  <input type=\"hidden\" value=\"" + btpMovimento.btpContaOrigem.conCodConta + "\" />";
						html += "  <input type=\"hidden\" value=\"" + btpMovimento.btpContaDestino.conCodConta + "\" />";
						html += "  <input type=\"hidden\" value=\"" + btpMovimento.btpFormaPagamento.fopCodFormaPagamento + "\" />";
						html += "</li>";
					}
					
					$("#testResult").html(html);
					$("#btpMovimentoForm").puidynamicform("setResultData", $("#testResult").find("li"));
				},
				async: true
			});
		}

		return true;
	};

	/**
	 * Tem a finalidade de resetar os dados dos campos de formulário e de visualização para os valores padrão,
	 * permitindo assim que o popup seja chamado novamente sem que seja exibido informações de outras solicitações.
	 */
	function resetPopupValues()
	{
		$(popupId).find("[name='popup.btpObjetivo.objCodObjetivo']").val("");
		$(popupId).find(":text").val("");
		$(popupId).find("select").val("");
		$(popupId).find("[data-name='primeuiDatatable']").children("tbody").children("tr").remove();
	};

	/**
	 * Tem a finalidade de validar as informações do formulário como, por exemplo, campos obrigatórios, validações de data, etc.
	 */
	function validateFormData()
	{
		var divFormSimple = $(popupId).find("[data-name='divFormSimple']");
		var divFormFull = $(popupId).find("[data-name='divFormFull']");

		if ($(popupId).find("[name='popup.btpObjetivo.objTxtDescricao']").val() == "")
			message.addError("Campo Obrigatório", "Objetivo");

		if (divFormSimple.css("display") != "none")
		{
			if (divFormSimple.find("[name='popup.btpObjetivoItem.obiVlrUnidade']").val() == "")
				message.addError("Campo Obrigatório", "Valor");
			if (divFormSimple.find("[name='popup.btpMovimento.movDatVencimento']").val() == "")
				message.addError("Campo Obrigatório", "Data");
			if (divFormSimple.find("[name='popup.btpContaOrigem.conCodConta']").val() == "")
				message.addError("Campo Obrigatório", "Origem");
			if (divFormSimple.find("[name='popup.btpContaDestino.conCodConta']").val() == "")
				message.addError("Campo Obrigatório", "Destino");
			if (divFormSimple.find("[name='popup.btpFormaPagamento.fopCodFormaPagamento']").val() == "")
				message.addError("Campo Obrigatório", "Forma");
		}
		else
		{
			if (divFormFull.find("[name='popup.btpObjetivoItem.obiNumItemArray']").length == 0)
				message.addError("Campo Obrigatório", "Itens (pelo menos um)");
			if (divFormFull.find("[name='popup.btpMovimento.movNumParcelaArray']").length == 0)
				message.addError("Campo Obrigatório", "Parcelas (pelo menos uma)");

			// Validando o valor total dos itens
			var auxVlrTotalArray = $("[name='popup.btpObjetivoItem.auxVlrTotalArray']")
			var auxTotalItem = 0.0;
			for (var i = 0; i < auxVlrTotalArray.length; i++)
				auxTotalItem += parseFloat(stringMoneyToNumberObject(auxVlrTotalArray.eq(i).val()));

			// Validando o valor total das parcelas
			var movVlrMovimentadoArray = $("[name='popup.btpMovimento.movVlrMovimentadoArray']")
			var auxTotalParcela = 0;
			for (var i = 0; i < movVlrMovimentadoArray.length; i++)
				auxTotalParcela += parseFloat(stringMoneyToNumberObject(movVlrMovimentadoArray.eq(i).val()));

			if (auxTotalItem.toFixed(2) != auxTotalParcela.toFixed(2))
				message.addError("Valores Inválidos", "O valor total dos itens deve ser igual ao valor total das parcelas.");
		}

		if (message.size() > 0)
		{
			message.show();
			return false;
		}

		return true;
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */

	/**
	 * Tem as finalidades listadas abaixo:
	 * 
	 * 1) Executa a função responsável por montar a estrutura HTML;
	 * 2) Executa a função responsável pelo preenchimento dos dados do popup;
	 * 3) Exibe o popup para o usuário.
	 */
	this.show = function(objCodObjetivo)
	{
		buildPuidialog();
		if (setPopupValues(objCodObjetivo))
			jvsPopupParent.show();
	};

	/**
	 * Tem as finalidades listadas abaixo:
	 * 
	 * 1) Apaga as informações preenchidas no popup;
	 * 2) Esconde o popup para o usuário.
	 */
	this.hide = function()
	{
		resetPopupValues();
		jvsPopupParent.hide();
	};

	/**
	 * Tem as finalidades listadas abaixo:
	 * 
	 * 1) Valida as informações do popup;
	 * 2) Executa a função Ajax responsável pela regra de negócio;
	 * 3) Opcional: Chama a função responsável por esconder o popup para o usuário. Deve
	 *    ser acrescentada quando necessário pois existem popup's que permanecem na tela após
	 *    a execução da regra de negócio, permitindo assim a inclusão de um novo registro.
	 */
	this.btnConfirmarClick = function()
	{
		if (!validateFormData())
			return false;

		var divFormSimple = $(popupId).find("[data-name='divFormSimple']");
		var divFormFull = $(popupId).find("[data-name='divFormFull']");

		var btpObjetivo = null;
		if (divFormSimple.css("display") != "none")
			btpObjetivo = insertFormSimple(divFormSimple);
		else
			btpObjetivo = insertFormFull(divFormFull);

		var btpEstabelecimento = {
			estCodEstabelecimento: $(popupId).find("[name='popup.btpEstabelecimento.estCodEstabelecimento']").val()
		};

		btpObjetivo.objCodObjetivo = $(popupId).find("[name='popup.btpObjetivo.objCodObjetivo']").val();
		btpObjetivo.objTxtDescricao = $(popupId).find("[name='popup.btpObjetivo.objTxtDescricao']").val();
		btpObjetivo.btpEstabelecimento = btpEstabelecimento;

		var command = "actExecInsert";
		if ($(popupId).find("[name='popup.btpObjetivo.objCodObjetivo']").val() != "")
			command = "actExecUpdate";

		DwrObjetivo.execute(btpObjetivo, command,
		{
			callback:function(dwrReturn)
			{
				resetPopupValues();
				jvsMovimentoNewMain.pageLoad();
				message.addInfo("Operação Concluída", "Cadastro realizado com sucesso.");
				message.show();
			}
		});
	};

	this.btnFormTypeClick = function(formType)
	{
		var btnFormType = $(popupId).find("[name='btnFormType']");
		var divFormSimple = $(popupId).find("[data-name='divFormSimple']");
		var divFormFull = $(popupId).find("[data-name='divFormFull']");

		if (formType == "divFormFull")
			divFormSimple.css("display", "");

		if (divFormSimple.css("display") != "none")
		{
			btnFormType.val("Formulário Completo");
			btnFormType.attr("title", "Clique para exibir o formulário simples...");
			divFormSimple.css("display", "none");
			divFormFull.css("display", "block");
		}
		else
		{
			btnFormType.val("Formulário Simples");
			btnFormType.attr("title", "Clique para exibir o formulário completo...");
			divFormSimple.css("display", "");
			divFormFull.css("display", "none");
		}

		resetPopupValues();
	};

	this.obiNumQuantidadeBlur = function()
	{
		calculateTotalItem();
	};

	this.obiVlrUnidadeBlur = function()
	{
		calculateTotalItem();
	};

	/**
	 * Tem as finalidades listadas abaixo:
	 * 
	 * 1) Cancela o preenchimento das informações chamando a função responsável por esconder o popup.
	 */
	this.btnCancelarClick = function()
	{
		movimentoFormPopup.hide();
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
	function getBtpObjetivoItemList()
	{
		var btpObjetivoItemList = new Array();
		for ( var i = 0; i < $(popupId).find("[name='popup.btpObjetivoItem.obiNumItemArray']").length; i++)
		{
			var btpObjetivoItem = {
				obiCodObjetivoItem: $(popupId).find("[name='popup.btpObjetivoItem.obiCodObjetivoItemArray']").eq(i).val(),
				obiNumItem: $(popupId).find("[name='popup.btpObjetivoItem.obiNumItemArray']").eq(i).val(),
				obiTxtDescricao: $(popupId).find("[name='popup.btpObjetivoItem.obiTxtDescricaoArray']").eq(i).val(),
				obiNumQuantidade: stringMoneyToNumberObject($(popupId).find("[name='popup.btpObjetivoItem.obiNumQuantidadeArray']").eq(i).val()),
				obiVlrUnidade: stringMoneyToNumberObject($(popupId).find("[name='popup.btpObjetivoItem.obiVlrUnidadeArray']").eq(i).val())
			};

			btpObjetivoItemList.push(btpObjetivoItem);
		}

		return btpObjetivoItemList;
	}

	function getBtpMovimentoList()
	{
		var btpMovimentoList = new Array();
		for ( var i = 0; i < $(popupId).find("[name='popup.btpMovimento.movNumParcelaArray']").length; i++)
		{
			var btpContaOrigem = {
				conCodConta: $(popupId).find("[name='popup.btpContaOrigem.conCodContaArray']").eq(i).val()
			};

			var btpContaDestino = {
				conCodConta: $(popupId).find("[name='popup.btpContaDestino.conCodContaArray']").eq(i).val()
			};

			var btpFormaPagamento = {
				fopCodFormaPagamento: $(popupId).find("[name='popup.btpFormaPagamento.fopCodFormaPagamentoArray']").eq(i).val()
			};

			var movDatPagamento = $(popupId).find("[name='popup.btpMovimento.movDatPagamentoArray']").eq(i).val()
			var btpMovimento = {
				movCodMovimento: $(popupId).find("[name='popup.btpMovimento.movCodMovimentoArray']").eq(i).val(),
				movNumParcela: $(popupId).find("[name='popup.btpMovimento.movNumParcelaArray']").eq(i).val(),
				movDatVencimento: $(popupId).find("[name='popup.btpMovimento.movDatVencimentoArray']").eq(i).val().toDate(datePatternEnum.DIA_MES_ANO),
				movDatPagamento: (movDatPagamento == "" ? null : movDatPagamento.toDate(datePatternEnum.DIA_MES_ANO)),
				movVlrMovimentado: stringMoneyToNumberObject($(popupId).find("[name='popup.btpMovimento.movVlrMovimentadoArray']").eq(i).val()),
				btpContaOrigem: btpContaOrigem,
				btpContaDestino: btpContaDestino,
				btpFormaPagamento: btpFormaPagamento
			};

			btpMovimentoList.push(btpMovimento);
		}

		return btpMovimentoList;
	}

	function calculateTotalItem()
	{
		var divFormFull = $(popupId).find("[data-name='divFormFull']");

		var amount = stringMoneyToNumberObject(divFormFull.find("[name='popup.btpObjetivoItem.obiNumQuantidade']").val());
		var value = stringMoneyToNumberObject(divFormFull.find("[name='popup.btpObjetivoItem.obiVlrUnidade']").val());
		var total = amount * value;

		divFormFull.find("[name='popup.btpObjetivoItem.auxVlrTotal']").val(numberObjectToStringMoney(total))
	};

	function loadCbxEstabelecimento()
	{
		var html = "";

		DwrMovimentoNew.loadEstabelecimentoCombo(null,
		{
			callback:function(dwrReturn)
			{
				html += "<option value=\"\">Selecione...</option>";
				for (var i = 0; i < dwrReturn.btpResultList.length; i++)
					html += "<option value=\"" + dwrReturn.btpResultList[i].estCodEstabelecimento + "\">" + dwrReturn.btpResultList[i].estNomEstabelecimento + "</option>";

				$(popupId).find("[name='popup.btpEstabelecimento.estCodEstabelecimento']").html(html);
			}
		});
	};

	function loadCbxContaOrigem()
	{
		var html = "";
		
		DwrMovimentoNew.loadContaCombo(null,
		{
			callback:function(dwrReturn)
			{
				html += "<option value=\"\">Selecione...</option>";
				for (var i = 0; i < dwrReturn.btpResultList.length; i++)
				{
					var btpConta = dwrReturn.btpResultList[i];

					if (btpConta.conNumNivel.substring(0, 2) != "03")
					{
						var nivel = btpConta.conNumNivel.split(".").length;

						if (nivel == 2 || nivel == 3)
							html += "<optgroup label=\"" + btpConta.conNumNivel + " " + btpConta.conTxtDescricao + "\"></optgroup>";
						else
							html += "<option value=\"" + btpConta.conCodConta + "\">" + btpConta.conNumNivel + " " + btpConta.conTxtDescricao + "</option>";
					}
				}

				$(popupId).find("[name='popup.btpContaOrigem.conCodConta']").html(html);
			},
			async: false
		});
	};

	function loadCbxContaDestino()
	{
		var html = "";

		DwrMovimentoNew.loadContaCombo(null,
		{
			callback:function(dwrReturn)
			{
				html += "<option value=\"\">Selecione...</option>";
				for (var i = 0; i < dwrReturn.btpResultList.length; i++)
				{
					var btpConta = dwrReturn.btpResultList[i];

					if (btpConta.conNumNivel.substring(0, 2) != "02")
					{
						var nivel = btpConta.conNumNivel.split(".").length;

						if (nivel == 2 || nivel == 3)
							html += "<optgroup label=\"" + btpConta.conNumNivel + " " + btpConta.conTxtDescricao + "\"></optgroup>";
						else
							html += "<option value=\"" + btpConta.conCodConta + "\">" + btpConta.conNumNivel + " " + btpConta.conTxtDescricao + "</option>";
					}
				}

				$(popupId).find("[name='popup.btpContaDestino.conCodConta']").html(html);
			},
			async: false
		});
	};

	function loadCbxFormaPagamento()
	{
		var html = "";

		DwrMovimentoNew.loadFormaPagamentoCombo(null,
		{
			callback:function(dwrReturn)
			{
				html += "<option value=\"\">Selecione...</option>";
				for (var i = 0; i < dwrReturn.btpResultList.length; i++)
					html += "<option value=\"" + dwrReturn.btpResultList[i].fopCodFormaPagamento + "\">" + dwrReturn.btpResultList[i].fopNomFormaPagamento + "</option>";

				$(popupId).find("[name='popup.btpFormaPagamento.fopCodFormaPagamento']").html(html);
			},
			async: false
		});
	};

	function stringMoneyToNumberObject(money)
	{
		money = money.replace(".", "");
		money = money.replace(",", ".");

		return money;
	};

	/**
	 * Converte um valor numérico para um objeto String e no formato de moeda brasileira
	 * @param valor valor a ser convertido
	 * @param precision configuração de casas decimaus que deve ser considerada.
	 * @returns número convertido em String e no formato de moeda brasileira
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

	function validateTotalItemTotalParcela()
	{
		var auxVlrTotalArray = $(popupId).find("[name='popup.btpObjetivoItem.auxVlrTotalArray']");

		var totalItem = null;
		for (var i = 0; i < auxVlrTotalArray.length; i++)
			

		return false;
	};

	function insertFormSimple(divFormSimple)
	{
		var btpObjetivoItem = {
			obiCodObjetivoItem: null,
			obiNumItem: 1,
			obiTxtDescricao: $(popupId).find("[name='popup.btpObjetivo.objTxtDescricao']").val(),
			obiNumQuantidade: 1,
			obiVlrUnidade: stringMoneyToNumberObject(divFormSimple.find("[name='popup.btpObjetivoItem.obiVlrUnidade']").val())
		};

		var btpObjetivoItemList = new Array();
		btpObjetivoItemList.push(btpObjetivoItem);

		var btpContaOrigem = {
			conCodConta: divFormSimple.find("[name='popup.btpContaOrigem.conCodConta']").val()
		};

		var btpContaDestino = {
			conCodConta: divFormSimple.find("[name='popup.btpContaDestino.conCodConta']").val()
		};

		var btpFormaPagamento = {
			fopCodFormaPagamento: divFormSimple.find("[name='popup.btpFormaPagamento.fopCodFormaPagamento']").val()
		};

		var movDatVencimento = divFormSimple.find("[name='popup.btpMovimento.movDatVencimento']").val()
		var btpMovimento = {
			movCodMovimento: null,
			movNumParcela: 1,
			movDatVencimento: movDatVencimento.toDate(datePatternEnum.DIA_MES_ANO),
			movDatPagamento: movDatVencimento.toDate(datePatternEnum.DIA_MES_ANO),
			movVlrMovimentado: stringMoneyToNumberObject(divFormSimple.find("[name='popup.btpObjetivoItem.obiVlrUnidade']").val()),
			btpContaOrigem: btpContaOrigem,
			btpContaDestino: btpContaDestino,
			btpFormaPagamento: btpFormaPagamento
		};

		var btpMovimentoList = new Array();
		btpMovimentoList.push(btpMovimento);

		var btpObjetivo = {
			btpObjetivoItemList: btpObjetivoItemList,
			btpMovimentoList: btpMovimentoList
		};

		return btpObjetivo;
	};

	function insertFormFull(divFormFull)
	{
		var btpObjetivo = {
			btpObjetivoItemList: getBtpObjetivoItemList(),
			btpMovimentoList: getBtpMovimentoList()
		};

		return btpObjetivo;
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
}