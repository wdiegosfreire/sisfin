/**
 * PrimeUI dynamicform widget
 */
$(function() {

	$.widget("primeui.puicombobox",
	{
		options: {
			filter: "false",
			filterMode: "begin"
		},

		variables: {
			resultData: null
		},

		_create: function()
		{
        	
			var combobox = this.element;

			// Criando uma div que servirÃ¡ como container para o componenete
			combobox.wrap("<div id=\"puicombobox-container\"></div>");
			var container = combobox.parent();

			/*
			 * Inicializando as variÃ¡veis globais
			 */
			if (combobox.attr("data-filter") != undefined && combobox.attr("data-filter") != "")
				this.options.filter = combobox.attr("data-filter");
			if (combobox.attr("data-filtermode") != undefined && combobox.attr("data-filtermode") != "")
				this.options.filterMode = combobox.attr("data-filtermode");

			combobox.css("max-width", "100%");

			/*
			 * Verifica se a opção de filtro foi habilitada. Em caso afirmativo,
			 * chama a função que prepara o componente para a opção de filtro.
			 */
			var $this = this;
			if (this.options.filter == "true")
			{
				this._enableFilterOption();

				container = combobox.parent();

				combobox.css("max-width", "90%");

				var icon = container.find("[data-name='icon']");
				var text = container.find("[data-name='text']");

				// Criando o evento do botão responsável por cancelar a edição de um registro na lista
				icon.on("click.puidynamicform", function(e) {
					$this.toggleSearchbox();
					e.preventDefault();
				});

				// Criando o evento de digitação da caixa de busca
				text.on("keyup.puidynamicform", function(e) {
					$this.executeSearch();
					e.preventDefault();
				});

				// Criando o evento de reset da busca quando um valor do combobox for selecionado
				combobox.on("change.puidynamicform", function(e) {
					$this.resetSearch();
					e.preventDefault();
				});
			}

			// adiconando os estilos do JQuery UI e Prime UI
			combobox.addClass("pui-combobox ui-widget ui-state-default ui-corner-all");

			// Verificando se o componente foi definido como desabilitado
			var isDisabled = combobox.prop("disabled");
			if (isDisabled)
				combobox.addClass("ui-state-disabled");
			else
				this._enableMouseEffects();
		},
        
		_destroy: function()
		{
		},

		_enableMouseEffects: function ()
		{
			var combobox = this.element;
			var container = combobox.parent();

			combobox.hover(function ()
			{
				combobox.toggleClass("ui-state-hover");
			});

			combobox.focus(function ()
			{
				combobox.addClass("ui-state-focus");
			})

			combobox.blur(function ()
			{
				combobox.removeClass("ui-state-focus");
			});
		},

		_disableMouseEffects: function ()
		{
			var combobox = this.element;
			combobox.off("mouseenter mouseleave focus blur" );
		},

		_enableFilterOption: function ()
		{
			var combobox = this.element;
			var container = combobox.parent();

			combobox.after("<span data-name=\"span\" style=\"white-space: nowrap;\"></span>")

			container.find("[data-name='span']").css("margin-left", "3px");
			container.find("[data-name='span']").css("margin-top", "1px");
			container.find("[data-name='span']").append("<input type=\"text\" data-name=\"text\" /><a href=\"javascript:void(0)\" data-name=\"icon\" class=\"fa fa-search-plus fa-lg pointer\"></a>");

			container.find("[data-name='text']").puiinputtext();
			container.find("[data-name='text']").css("display", "none");
			container.find("[data-name='text']").css("margin-left", "-3px");
			container.find("[data-name='text']").css("margin-right", "3px");
			container.find("[data-name='text']").css("max-width", "150px");
		},

		toggleSearchbox: function ()
		{
			var combobox = this.element;
			var container = combobox.parent();

			// Criando um atributo auxiliar para a busca pela propriedade text do combobox
			var options = combobox.find("option");
			for (var i = 0; i < options.length; i++)
				options.eq(i).attr("data-text", options.eq(i).text());

			var icon = container.find("[data-name='icon']");
			var text = container.find("[data-name='text']");

			if (text.css("display") == "none")
			{
				text.fadeIn("fast", function() {
					icon.removeClass("fa-search-plus").addClass("fa-search-minus");
					text.focus();
				});
			}
			else
			{
				this.resetSearch();
			}
		},

		executeSearch: function ()
		{
			var combobox = this.element;
			var container = combobox.parent();
			var value = container.find("[data-name='text']").val();

			if (value.length > 0 && value.length < 3)
				return false;

			combobox.focus("down");

			var optionList = combobox.find("option");
			optionList.removeAttr("disabled");
			optionList.css("display", "");

			for (var i = 0; i < optionList.length; i++)
			{
				var option = optionList.eq(i);

				// Verificando se a propriedade "data-text" existe. Caso não exista, interrompe a pesquisa
				if (option.attr("data-text") == undefined || option.attr("data-text") == null || option.attr("data-text") == "")
					return false;

				// Transformando as duas chaves de comparação para maiúscula para que a busca não seja case sensitive
				var optionValue = option.attr("data-text").toUpperCase();
				value = value.toUpperCase();

				if (optionValue.indexOf(value) == -1)
				{
					option.attr("disabled", "disabled");
					option.css("display", "none");
				}
			}

			// Verifica se há resultado para a busca informada. Em caso negativo, exibe uma mensagem para o usuário e limpa a busca.
			var optionAll = combobox.find("option");
			var optionDisabled = combobox.find("option[disabled='disabled']");

			if (optionAll.length == optionDisabled.length)
			{
				alert("Nenhuma correspondÃªncia encontrada");
				this.resetSearch();
			}
		},

		resetSearch: function ()
		{
			var combobox = this.element;
			var container = combobox.parent();

			var icon = container.find("[data-name='icon']");
			var text = container.find("[data-name='text']");

			text.val("");
			text.fadeOut("fast", function() {
				icon.removeClass("fa-search-minus").addClass("fa-search-plus");
			});

			this.executeSearch();
		}
	});
});