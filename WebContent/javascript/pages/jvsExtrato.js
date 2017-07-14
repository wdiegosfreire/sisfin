var jvsExtrato = new JvsExtrato();

function JvsExtrato()
{
	/* +----------------------------------------------------------------------+
	 * | Funções Públicas                                                     |
	 * +----------------------------------------------------------------------+
	 */
	this.resetForm = function()
	{
		alert("TESTE");
	};

	this.btnImportarClick = function()
	{
		// Validando os campos obrigatórios para a importação.
		if ($("[name='btpExtrato.formFile']").val() == "")
			message.addRequired("Arquivo");

		if (message.size() > 0)
		{
			message.show();
			return false;
		}

		document.forms[0].action = "extrato.do?cmd=actExecInsert";
		document.forms[0].submit();
	};

	this.btnCancelarClick = function()
	{
		alert("A função \"Cancelar\" será desenvolvida em breve!")
	};

	this.btnFiltrarClick = function()
	{
		$("[name='FrmExtrato']").attr("action", "extrato.do?cmd=actExecSearch");
		$("[name='FrmExtrato']").submit();
	};

	this.btnVoltarClick = function()
	{
		history.go(-1);
	};

	this.execInsertObjetivoImported = function()
	{
		var tableList = $("[name='chxExport']:checked").closest("table");

		if (tableList.length == 0)
		{
			message.addWarn("Alerta!", "Para executar a importação é necessário selecionar pelo menos um item de extrato.");
			message.show();
			return null;
		}

		// Validando os campos obrigatórios para a exportação de dados
		for (var i = 0; i < tableList.length; i++)
		{
			if (tableList.eq(i).find("[name='btpItemExtrato.iteTxtDescricao']").val() == "")
				message.addError("Campo Obrigatório", "Descrição");
			if (tableList.eq(i).find("[name='fopCodFormaPagamento']").val() == "")
				message.addError("Campo Obrigatório", "Forma de Pagamento");
			if (tableList.eq(i).find("[name='conCodContaOrigem']").val() == "")
				message.addError("Campo Obrigatório", "Conta Origem");
			if (tableList.eq(i).find("[name='conCodContaDestino']").val() == "")
				message.addError("Campo Obrigatório", "Conta Destino");

			if (message.size() > 0)
			{
				message.addError("Campo Obrigatório", "Para exportar as informações é necessário que os campos listados abaixo de cada item selecionado esteja preenchido:");
				message.show();

				return false;
			}
		}

		var btpObjetivoList = new Array();
		for (var i = 0; i < tableList.length; i++)
		{
			var table = tableList.eq(i);

			// Montando a entidade BtpEstabelecimento
			var btpEstabelecimento = {
				estCodEstabelecimento: table.find("[name='estCodEstabelecimento']").val()
			}

			// Montando a entidade BtpObjetivoItem
			var btpObjetivoItem = {
				obiNumItem: 1,
				obiTxtDescricao: table.find("[name='btpItemExtrato.iteTxtDescricao']").val(),
				obiNumQuantidade: 1,
				obiVlrUnidade: table.find("[name='btpItemExtrato.iteVlrMovimento']").val()
			};

			var btpObjetivoItemList = new Array();
			btpObjetivoItemList.push(btpObjetivoItem);

			// Montando a entidade BtpMovimento
			var btpContaOrigem = {
				conCodConta: table.find("[name='conCodContaOrigem']").val()
			};

			var btpContaDestino = {
				conCodConta: table.find("[name='conCodContaDestino']").val()
			};

			var btpFormaPagamento = {
				fopCodFormaPagamento: table.find("[name='fopCodFormaPagamento']").val()
			};

			var btpMovimento = {
				movNumParcela: 1,
				movVlrMovimentado: table.find("[name='btpItemExtrato.iteVlrMovimento']").val(),
				movDatVencimento: stringDateToObjectDate(table.find("[name='btpItemExtrato.iteDatMovimento']").val()),
				movDatPagamento: stringDateToObjectDate(table.find("[name='btpItemExtrato.iteDatMovimento']").val()),
				btpContaOrigem: btpContaOrigem,
				btpContaDestino: btpContaDestino,
				btpFormaPagamento: btpFormaPagamento
			};

			var btpMovimentoList = new Array();
			btpMovimentoList.push(btpMovimento);

			var map = {
				iteCodItemExtrato: table.find("[name='btpItemExtrato.iteCodItemExtrato']").val()
			}

			// Montando a entidade BtpObjetivo a partir das entidades criadas acima
			var btpObjetivo = {
				objTxtDescricao: table.find("[name='btpItemExtrato.iteTxtDescricao']").val(),
				btpEstabelecimento: btpEstabelecimento,
				btpObjetivoItemList: btpObjetivoItemList,
				btpMovimentoList: btpMovimentoList,
				map: map
			};

			btpObjetivoList.push(btpObjetivo);
		}

		var btpExtrato = {
			btpObjetivoList: btpObjetivoList
		};

		DwrExtrato.execute(btpExtrato, "actExecInsert",
		{
			callback:function(dwrReturn)
			{
				for (var i = 0; i < dwrReturn.msgContainer.length; i++)
					message.addWarn("Mensagem", dwrReturn.msgContainer[i]);

				message.show();
				location.assign(location.href);
			}
		});
	};

	this.execUpdateItemExtratoImported = function(iteCodItemExtrato)
	{
		if (!confirm("Deseja marcar este item como exportado?"))
			return false;

		var btpItemExtrato = {
			iteCodItemExtrato: iteCodItemExtrato,
			iteFlgExportado: true
		};

		DwrItemExtrato.execute(btpItemExtrato, "actExecUpdate",
		{
			callback:function(dwrReturn)
			{
				for (var i = 0; i < dwrReturn.msgContainer.length; i++)
					message.addWarn("Mensagem", dwrReturn.msgContainer[i]);

				message.show();
				location.assign(location.href);
			}
		});
	};

	this.adjustContaCombo = function()
	{
		/*
		 * Removendo as contas do grupo 03, "Saída", do combo "Conta de Origem".
		 */
		$("[name='conCodContaOrigem']").find("option").each(function()
		{
			if ($(this).text().trim().substr(0, 2) == "03")
				$(this).remove();
		});

		/*
		 * Removendo as contas do grupo 02, "Entrada", do combo "Conta de Destino".
		 */
		$("[name='conCodContaDestino']").find("option").each(function()
		{
			if ($(this).text().trim().substr(0, 2) == "02")
				$(this).remove();
		});

		/*
		 * Bloqueando as contas dos níveis 1 e 2
		 */
		$("[name='conCodContaOrigem'], [name='conCodContaDestino']").find("option").each(function()
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

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas                                                     |
	 * +----------------------------------------------------------------------+
	 */
	function stringDateToObjectDate(date)
	{
		if (date != "")
			return new Date(date.split("/")[2] + "/" + date.split("/")[1] + "/" + date.split("/")[0]);

		return null;
	};
}