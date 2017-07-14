window.onerror = function(msg, url, line, col, error)
{
	if (!url.includes('jquery'))
	{
		var message = "";
		message += "<b>Message:</b> " + msg + "<br />";
		message += "<b>Error:</b> " + error + "<br />";
		message += "<b>Url:</b> " + url + "<br />";
		message += "<b>Line:</b> " + line + "<br />";
		message += "<b>Column:</b> " + col + "<br />";

		message.addError("Javascript Error", message);
		message.show();
	}

	var suppressErrorAlert = true;

	return suppressErrorAlert;
};

/*
 * Init area ------------------------------------------------------------------
 */
$(document).ready(function()
{
	/*
	 * Configurações de inicialização padrão dos componentes PrimeUI
	 */
	jvsEstrutura.prepareHtmlComponents($("html"));
	$("#puigrowl").puigrowl({life: 20000});

	$("#puinotify").puinotify({
		visible: true,
		position: "bottom"
	});
});

var jvsEstrutura = new JvsEstrutura();
function JvsEstrutura()
{
	this.showLoading = function()
	{
		$("#divLoading").show();
	};

	this.hideLoading = function()
	{
		$("#divLoading").hide();
	};

	this.desktopNotification = function(severity, summary, detail)
	{
		if(window.Notification && Notification.permission !== "denied")
		{
			if (severity == "error")
				icon = "imagens/error.png";
			else if (severity == "warn")
				icon = "imagens/warn.png";
			if (severity == "info")
				icon = "imagens/info.png";

			Notification.requestPermission(function(status) {
				var n = new Notification(summary, { 
					body: detail,
					icon: icon
				}); 
			});
		}
	}

	this.prepareHtmlComponents = function(container)
	{
		if (container == null || container == undefined)
			return false;

		container.find(".fa").each(function()
		{
			var obj = $(this);

			obj.css("transition", "color 0.5s");

			obj.mouseover(function() {
				$(this).css("color", "red");
			});

			obj.mouseout(function() {
				$(this).css("color", "");
			});
		})

		var formFieldDate = container.find(".formFieldDate");
		formFieldDate.each(function() {
			$(this).mask("99/99/9999");
			$(this).datepicker({changeMonth: true, changeYear: true});
		});

		container.find(".fieldYear").each(function()
		{
			$(this).prop("min", "1970");
			$(this).prop("max", "2100");
		})

		container.find(".fieldMonth").each(function()
		{
			$(this).prop("min", "1");
			$(this).prop("max", "12");
		})

		// Formatando os componentes de tipo semelhante a "button"
		container.find(":button, :submit, :reset").each(function() {
			$(this).puibutton();
		});

		// Formatando os componentes de tipo semelhante ao "text"
		container.find("[type='text'], [type='password'], [type='date'], [type='datetime'], [type='datetime-local'], [type='month'], [type='search'], [type='tel'], [type='time'], [type='url'], [type='week']").each(function() {
			if ($(this).hasClass("fieldEmail"))
				$(this).attr("type", "email");
			if ($(this).hasClass("fieldNumber") || $(this).hasClass("fieldYear") || $(this).hasClass("fieldMonth"))
				$(this).attr("type", "number");

			$(this).puiinputtext();
		});

		// Formatando os botões identificados como "btnConsultar"
		container.find("[name='btnConsultar']").each(function() {
			$(this).val("Consultar");

			if ($(this).attr("title") == undefined)
				$(this).attr("title", "Clique para executar uma consulta baseada nos filtros selecionados.");
		});

		// Formatando os botões identificados como "btnLimparConsulta"
		container.find("[name='btnLimparConsulta']").each(function() {
			$(this).val("Limpar");

			if ($(this).attr("title") == undefined)
				$(this).attr("title", "Clique para limpar o formulário de consulta.");
		});

		// Formatando os botões identificados como "btnIncluir"
		container.find("[name='btnIncluir']").each(function() {
			$(this).val("Incluir");

			if ($(this).attr("title") == undefined)
				$(this).attr("title", "Clique acessar o formulário de cadastro.");
		});

		// Formatando os botões identificados como "btnConfirmar"
		container.find("[name='btnConfirmar']").each(function() {
			$(this).val("Confirmar");

			if ($(this).attr("title") == undefined)
				$(this).attr("title", "Clique para confirmar o preenchimento das informações.");
		});

		// Formatando os botões identificados como "btnAlterar"
		container.find("[name='btnAlterar']").each(function() {
			$(this).val("Alterar");
			
			if ($(this).attr("title") == undefined)
				$(this).attr("title", "Clique para confirmar a alteração das informações.");
		});

		// Formatando os botões identificados como "btnCancelar"
		container.find("[name='btnCancelar']").each(function() {
			$(this).val("Cancelar");
			
			if ($(this).attr("title") == undefined)
				$(this).attr("title", "Clique para cancelar eta operação.");
		});

		// Formatando os botões identificados como "btnCancelar"
		container.find("[name='btnCancelar']").each(function() {
			if ($(this).attr("title") == undefined)
				$(this).attr("title", "Clique para cancelar o cadastro/ediÃ§Ã£o.");
		});

		// Formatando os botões identificados como "btnEditar"
		container.find("[data-name='btnEditar']").each(function() {
			$(this).addClass("fa fa-edit fa-lg");
			$(this).attr("href", "javascript:void(0)");

			if ($(this).attr("title") == undefined)
				$(this).attr("title", "Clique para editar este registro.");
		});

		// Formatando os botões identificados como "btnExcluir"
		container.find("[data-name='btnExcluir']").each(function() {
			$(this).addClass("fa fa-trash-o fa-lg");

			if ($(this).attr("title") == undefined)
				$(this).attr("title", "Clique para excluir este registro.");
		});

		// Formatando os botões identificados como "btnExcluir"
		container.find("[data-name='btnRelatorioPdf']").each(function() {
			$(this).addClass("fa fa-file-pdf-o fa-lg");

			if ($(this).attr("title") == undefined)
				$(this).attr("title", "Clique para exibir o relatório PDF.");
		});

		container.find(".formFieldAreaCode").mask("99").css('width','30px');
		container.find(".formFieldPhone").mask("99999999?9").css('width','90px');

		container.find(".fieldOnlyNumber").each(function() {
			$(this).mask("9?99999");
		});

		container.find(".formFieldHour").each(function() {
			$(this).css("width", "40px");
			$(this).mask("99:99");
		});

		container.find(".fieldMoney").each(function()
		{
			$(this).maskMoney("destroy");

			$(this).css("text-align", "right");
			$(this).maskMoney({decimal: ",", thousands: ".", allowZero: false, precision: 2, allowNegative: true});
		});

		container.find(".fieldQuantity").each(function()
		{
			$(this).css("text-align", "right");
			$(this).maskMoney({decimal: ",", thousands: ".", allowZero: false, precision: 3});
		})

		container.find(".required").append("<span class=\"c-red\">*</span>");

		container.find(".puidynamicform").each(function() {
			$(this).puidynamicform();
		});

		container.find(".puicomboboxfilter").attr("data-filter", "true");
		container.find(".puicombobox, .puicomboboxfilter").each(function()
		{
			$(this).puicombobox();
		});

		/*
		 * Lista de opções disponíveis: usar data-[option]="value"
		 *   toggleable | Boolean | default: false | Defines if content of panel can be expanded and collapsed.
		 *   collapsed  | Boolean | default: false | Displays the panel as collapsed initially.
		 *   closable   | Boolean | default: false | Defines if content of panel can be hidden using close option.
		 * 
		 * Lista de eventos disponíveis: usar data-[event]="javascript code"
		 *   beforeExpand
		 */
		container.find(".puitogglebutton").each(function() {
			var onLabel = $(this).attr("data-onlabel");
			var offLabel = $(this).attr("data-offlabel");
			var styleClass = $(this).attr("data-styleclass");

			$(this).puitogglebutton({
				// Options
				onLabel: (onLabel != undefined && !onLabel == "" ? onLabel : "Sim"),
				offLabel: (offLabel != undefined && !offLabel == "" ? offLabel : "Não"),
				styleClass: (styleClass != undefined && !styleClass == "" ? styleClass : "")
			});
		});

		/*
		 * Lista de opções disponíveis: usar data-[option]="value"
		 *   toggleable | Boolean | default: false | Defines if content of panel can be expanded and collapsed.
		 *   collapsed  | Boolean | default: false | Displays the panel as collapsed initially.
		 *   closable   | Boolean | default: false | Defines if content of panel can be hidden using close option.
		 * 
		 * Lista de eventos disponíveis: usar data-[event]="javascript code"
		 *   beforeExpand
		 */
		container.find(".puipanel").each(function() {
			$(this).puipanel({
				// Options
				toggleable: ($(this).attr("data-toggleable") != undefined && $(this).attr("data-toggleable") == "true" ? true : ""),
				closable: ($(this).attr("data-closable") != undefined && $(this).attr("data-closable") == "true" ? true : ""),
				collapsed: ($(this).attr("data-collapsed") != undefined && $(this).attr("data-collapsed") == "true" ? true : ""),

				// Events
				beforeExpand: function(event) {eval($(this).attr("data-beforeExpand"));}
			});
		});

		container.find("#puimenu").puimenu({
			popup: true,
			trigger: $(".fa-navicon")
		});

		/*
		 * Lista de opções disponíveis: usar data-[option]="value"
		 *   numvisible     | Integer | default: 3    | Number of visible items per page.
		 *   headertext     | String  | default: null | Text of the header section.
		 *   effectduration | Integer | default: 500  | Duration of the scrolling animation in milliseconds.
		 */
		container.find(".puicarousel").each(function() {
			$(this).puicarousel({
				numVisible: ($(this).attr("data-numvisible") != undefined && $(this).attr("data-numvisible") != "" ? $(this).attr("data-numvisible") : "3"),
				headerText: ($(this).attr("data-headertext") != undefined && $(this).attr("data-headertext") != "" ? $(this).attr("data-headertext") : ""),
				effectDuration: ($(this).attr("data-effectduration") != undefined && $(this).attr("data-effectduration") != "" ? $(this).attr("data-effectduration") : "500"),
				autoplayInterval: ($(this).attr("data-autoplayinterval") != undefined && $(this).attr("data-autoplayinterval") != "" ? $(this).attr("data-autoplayinterval") : null),
				circular: ($(this).attr("data-circular") != undefined && $(this).attr("data-circular") == "true" ? true : "")
			});
		});


		// Formatando os componentes do tipo "textarea"
		container.find("textarea").each(function() {
			if ($(this).attr("data-maxlength") != undefined && $(this).attr("data-maxlength") != "")
				$(this).after("<div style=\"text-align: right;\"></div>");

			$(this).puiinputtextarea({
				counter: ($(this).attr("data-maxlength") != undefined && $(this).attr("data-maxlength") != "" ? $(this).next() : ""),
				maxlength: ($(this).attr("data-maxlength") != undefined && $(this).attr("data-maxlength") != "" ? $(this).attr("data-maxlength") : ""),
				counterTemplate: ($(this).attr("data-maxlength") != undefined && $(this).attr("data-maxlength") != "" ? "{0}/" + $(this).attr("data-maxlength") : "")
			});
		});

		formatTableLikePrimeui(container.find("[data-name='primeuiDatatable']"));
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
	function formatTableLikePrimeui(tableArray)
	{
		tableArray.each(function() {
			var table = $(this);

			// Verifica se a tabela já foi formatada como PrimeUI. Serve para que a estrutura não seja recriada nos casos de atualização de linhas da tabela
			var isPrimeui = false;
			if (table.attr("data-isprimeui") != undefined && table.attr("data-isprimeui") != null && table.attr("data-isprimeui") == "true")
				isPrimeui = true;

			// Código responsável por transformar o resultado da tabela em responsivo quando a largura diminui até um valor pré-definido
			var lastTr = table.children("thead").children("tr").last();
			for (var i = 0; i < lastTr.children("th").length; i++)
			{
				var th = lastTr.children("th").eq(i);

				table.children("tbody").children("tr").each(function() {

					if ($(this).find(".ui-column-title").length < $(this).children("td").length)
					{
						if (th.text() != "")
							$(this).children("td").eq(th.index()).prepend("<span class=\"ui-column-title\">" + th.text() + "</span>");
						else if (th.attr("title") != undefined && th.attr("title") != null && th.attr("title") != "")
							$(this).children("td").eq(th.index()).prepend("<span class=\"ui-column-title\">" + th.attr("title") + "</span>");
					}

				});
			}

			if (!isPrimeui)
			{
				// Criando a estrutura de div's necessárias para a formatação da tabela
				table.wrap("<div class=\"ui-datatable ui-widget ui-datatable-reflow\"></div>"); 
				table.wrap("<div class=\"ui-datatable-tablewrapper\" style=\"overflow: auto;\"></div>");

				// Criando a div que representará o cabeçalho da tabela, pegando o valor do atributo "title" da tabela
				if (table.attr("title") != undefined && table.attr("title") != null && table.attr("title") != "")
				{
					table.before("<div class=\"ui-datatable-header ui-widget-header\">" + table.attr("title") + "</div>");
					table.removeAttr("title");
				}

				// Criando o componente de paginação
				table.after("<div class=\"ui-paginator ui-widget-header\">&nbsp;</div>");
			}

			// Verifica se a tabela possui linhas de resultado. Se não houver, cria uma linha com uma mensagem padrão de resultado vazio
			if (table.children("tbody").children("tr").length == 0)
			{
				table.children("tbody").append("<tr data-role=\"nodata\"><td style=\"text-align: center;\">Nenhum registro encontrado.</td></tr>");
				table.children("tbody").children("tr").children("td").attr("colspan", table.children("thead").children("tr").children("th").length);
			}
			else
			{
				table.children("tbody").children("[data-role='nodata']").remove();
			}

			// Aplicando os estilos CSS do JQueryUI
			table.children("thead").children("tr").addClass("ui-state-default");
			table.children("thead").children("tr").children("th").addClass("ui-state-default");
			table.children("tbody").addClass("ui-datatable-data ui-widget-content");
			table.children("tbody").children("tr").addClass("ui-widget-content ui-datatable-even");

			// Criando os eventos "mouseover", "mouseout" e "click" da tabela
			table.children("tbody").children("tr").mouseover(function() {
				$(this).addClass("ui-state-hover");
			});

			table.children("tbody").children("tr").mouseout(function() {
				$(this).removeClass("ui-state-hover");
			});

			table.attr("data-isprimeui", "true");
		});
	};
}

var message = new MessageManager();
function MessageManager()
{
	/**
	 * Tem a finalidade de recuperar os objetos "li" dentro da div "msgTempContainer" definida na estrutura do sistema
	 * e adicioná-las em um array, que posteriormente será passado para a função puigrowl do PrimeUI.
	 */
	this.show = function()
	{
		var liArray = $("#msgTempContainer").find("li");

		var messageArray = new Array();
		for (var i = 0; i < liArray.length; i++)
		{
			var severity = "info";
			var summary = "Mensagem do Sistema";
			var detail = "Ocorreu um erro desconhecido ao executar esta operação. Para maiores detalhes entre em contato com a unidade gestora do sistema.";

			var li = liArray.eq(i);

			if (li.attr("data-severity") != undefined && li.attr("data-severity") != null && li.attr("data-severity") != "")
				severity = li.attr("data-severity");
			if (li.attr("data-summary") != undefined && li.attr("data-summary") != null && li.attr("data-summary") != "")
				summary = li.attr("data-summary");
			if (li.text() != undefined && li.text() != null && li.text() != "")
				detail = li.text();

			var message = {
				severity: severity,
				summary: summary,
				detail: detail
			};

			messageArray.push(message);
		}

		$("#puigrowl").puigrowl("show", messageArray);

		// Criando o evento swipe para as mensagens do sistema
		$(".ui-growl-item-container").swipe({
			swipe:function(event, direction, distance, duration, fingerCount) {
				if (direction == "left" || direction == "right")
				{
					if (fingerCount == 0 || fingerCount == 1)
						$("#puigrowl").puigrowl("clearOne", $(this));
					else if (fingerCount == 2)
						$("#puigrowl").puigrowl("clear");
				}
			},
			threshold: 50
		});

		// Limpando a estrutura temporária de mensagens #msgTempContainer
		clear();
	};

	this.addError = function(summary, detail)
	{
		add("error", summary, detail);
	};

	this.addWarn = function(summary, detail)
	{
		add("warn", summary, detail);
	};

	this.addInfo = function(summary, detail)
	{
		add("info", summary, detail);
	};

	this.addRequired = function(detail)
	{
		message.addError("Campo Obrigatório", detail);
	};

	this.size = function()
	{
		return $("#msgTempContainer").find("li").length;
	};

	function add(severity, summary, detail)
	{
		$("#msgTempContainer").children("ul").append("<li data-severity=\"" + severity + "\" data-summary=\"" + summary + "\">" + detail + "</li>");
	};

	function clear()
	{
		$("#msgTempContainer").find("li").remove();
	};
}

/*
 * Iniciando os componentes primeui
 */
$(function()
{
	$(":file").puibutton();

	$("#msgBox").puigrowl({life: 5000});
});

function stringMoneyToNumberObject(money)
{
	money = money.replace(".", "");
	money = money.replace(",", ".");

	return money;
}

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
}