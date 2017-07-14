/**
 * PrimeUI dynamicform widget
 */
$(function() {

	$.widget("primeui.puidynamicform",
	{
		options: {
			hiddenSufix: "Array",
			allowDelete: "true",
			allowUpdate: "true",
			allowRepeat: "false",
			allowMove: "false",
			cancelUpdate: "true",
			confirmDelete: "true",
			resultData: null,
			formFieldListClone: null
		},

		_create: function()
		{
			var mainDiv = this.element;

			/*
			 * Verificando a existência dos atributos obrigatórios
			 */
			for (var i = 0; i < mainDiv.children().length; i++)
			{
				var formField = mainDiv.children().eq(i);

				// Verificando se o campo de formulário possui o atributo obrigatório "data-label"
				if (formField.attr("data-label") == undefined || formField.attr("data-label") == null || formField.attr("data-label") == "")
				{
					alert("Atributo \"data-label\" não definido para o campo " + formField.attr("name") + ".");
					return false;
				}
			}

			/*
			 * Inicializando as variáveis globais
			 */
			if (mainDiv.attr("data-hiddensufix") != undefined && mainDiv.attr("data-hiddensufix") != "")
				this.options.hiddenSufix = mainDiv.attr("data-hiddensufix");

			if (mainDiv.attr("data-allowdelete") != undefined && mainDiv.attr("data-allowdelete") != "")
				this.options.allowDelete = mainDiv.attr("data-allowdelete");

			if (mainDiv.attr("data-allowupdate") != undefined && mainDiv.attr("data-allowupdate") != "")
				this.options.allowUpdate = mainDiv.attr("data-allowupdate");

			if (mainDiv.attr("data-allowrepeat") != undefined && mainDiv.attr("data-allowrepeat") != "")
				this.options.allowRepeat = mainDiv.attr("data-allowrepeat");

			if (mainDiv.attr("data-allowmove") != undefined && mainDiv.attr("data-allowmove") != "")
				this.options.allowMove = mainDiv.attr("data-allowmove");

			if (mainDiv.attr("data-cancelupdate") != undefined && mainDiv.attr("data-cancelupdate") != "")
				this.options.cancelUpdate = mainDiv.attr("data-cancelupdate");

			if (mainDiv.attr("data-confirmdelete") != undefined && mainDiv.attr("data-confirmdelete") != "")
				this.options.confirmDelete = mainDiv.attr("data-confirmdelete");

			// Criando a lista de resultados, caso exista
			this.options.resultData = mainDiv.find("[data-role='result']").find("li");

			// Clonando a estrutura de campos de formulário antes de qualquer alteração. Será usado para facilitar a montagem dos componentes.
			this.options.formFieldListClone = mainDiv.children(":text, :checkbox, select, textarea").clone();

			this._buildMainForm();

			var $this = this;

			// Criando o evento do botão responsável por adicionar um registro na lista
			mainDiv.parent().find("[name='puiBtnExecuteInsert']").on("click.puidynamicform", function(e) {
				$this.executeInsert();
				e.preventDefault();
			});

			// Criando o evento do botão responsável por confirmar a alteração de um registro na lista
			mainDiv.parent().find("[name='puiBtnExecuteUpdate']").on("click.puidynamicform", function(e) {
				$this.executeUpdate();
				e.preventDefault();
			});

			// Criando o evento do botão responsável por cancelar a edição de um registro na lista
			mainDiv.parent().find("[name='puiBtnCancelUpdate']").on("click.puidynamicform", function(e) {
				$this._cancelUpdate();
				e.preventDefault();
			});

			if (this.options.resultData.length > 0)
				this._buildResultTable();
		},
        
		_destroy: function()
		{
		},

		setResultData: function (liList)
		{
			this.options.resultData = liList;

			if (this.options.resultData.length > 0)
				this._buildResultTable();
		},

		_buildResultTable: function()
		{
			var mainDiv = this.element;

			var liList = this.options.resultData;

			/*
			 * Verificar se a quantidade de itens de cada resultado está compatível com a quantidade de
			 * campos do formulário. Ex.: se o formulário de entrada possuir 4 campos, então a lista de
			 * resultados deve conter a mesma quantidade de elementos, cada um correspondente a um campo
			 * do formulário.
			 */
			for (var i = 0; i < liList.length; i++)
			{
				var li = liList.eq(i);
				if (li.children(":hidden").length != this.options.formFieldListClone.length)
				{
					alert("A quantidade de campos do formulário não está correspondente com a quantidade de itens no resultado.\n\nPuiDynamicForm -> " + mainDiv.attr("title"));
					return false;
				}
			}

			for (var i = 0; i < liList.length; i++)
			{
				var li = liList.eq(i);
				inputList = mainDiv.find(":text, :checkbox, select, textarea");

				var hiddenList = li.children("input");
				for (var j = 0; j < hiddenList.length; j++)
				{
					var hidden = hiddenList.eq(j);

					if (inputList.eq(j).is(":checkbox"))
						inputList.eq(j).prop("checked", (hidden.val() == "true" ? true : false));
					else
						inputList.eq(j).val(hidden.val());
				}

				this.executeInsert();
			}
		},

		_cancelUpdate: function()
		{
			var mainDiv = this.element;
			var formFieldListClone = this.options.formFieldListClone;

			// Fazendo o chaveamento dos botões que devem ser exibidos.
			mainDiv.parent().find("[name='puiBtnExecuteInsert']").show();
			mainDiv.parent().find("[name='puiBtnExecuteUpdate']").hide();
			mainDiv.parent().find("[name='puiBtnCancelUpdate']").hide();

			// Removendo a formatação da linha selecionada para edição
			mainDiv.parent().find("[data-name='primeuiDatatable']").find("tbody").children("tr").css("color", "").css("font-weight", "");

			// Limpando os dados do formulário
			this._resetForm();
		},

		_buildMainForm: function ()
		{
			var mainDiv = this.element;
			var formFieldListClone = this.options.formFieldListClone;

			/*
			 * Montando a estrutura responsiva do formulário principal
			 */
			mainDiv.wrap("<div class=\"puipanel\" title=\"" + mainDiv.attr("title") + "\" style=\"" + mainDiv.attr("style") + "\"></div>");
			mainDiv.addClass("ui-grid ui-grid-responsive fieldContainer");

			// Criando a estrutura responsiva para os campos de formulário exixtentes
			for (var i = 0; i < mainDiv.children().length; i++)
			{
				var formField = mainDiv.children().eq(i);

				if (formField.attr("data-hidden") != undefined && formField.attr("data-hidden") != "" && formField.attr("data-hidden") == "true")
					formField.wrap("<div class=\"ui-grid-row\" style=\"display: none;\"></div>");
				else
					formField.wrap("<div class=\"ui-grid-row\"></div>");

				if (formField.attr("data-required") != undefined && formField.attr("data-required") != "" && formField.attr("data-required") == "true")
					formField.before("<div class=\"ui-grid-col-3\">" + formField.attr("data-label") + "<span class=\"c-red\">*</span></div>");
				else
					formField.before("<div class=\"ui-grid-col-3\">" + formField.attr("data-label") + "</div>");

				formField.wrap("<div class=\"ui-grid-col-9\"></div>");
			}

			var html = "";
			html += "<div class=\"ui-grid ui-grid-responsive\">";
			html += "  <div class=\"ui-grid-row\">";
			html += "    <div class=\"ui-grid-col-12 left\">";
			html += "      <input type=\"button\" name=\"puiBtnExecuteInsert\" value=\"Adicionar\">";
			html += "      <input type=\"button\" name=\"puiBtnExecuteUpdate\" value=\"Alterar\" style=\"display: none;\">";
			html += "      <input type=\"button\" name=\"puiBtnCancelUpdate\" value=\"Cancelar\" style=\"display: none;\">";
			html += "    </div>";
			html += "  </div>";
			html += "</div>";
			html += "<hr />";
			mainDiv.after(html);

			/*
			 * Montando a estrutura da tabela de resultados.
			 */
			html = "";
			html += "<table data-name=\"primeuiDatatable\">";
			html += "  <thead>";
			html += "    <tr>";

			for (var i = 0; i < formFieldListClone.length; i++)
			{
//				alert(formFieldListClone.eq(i).attr("data-hidden"));
				if (formFieldListClone.eq(i).attr("data-hidden") != undefined && formFieldListClone.eq(i).attr("data-hidden") != "" && formFieldListClone.eq(i).attr("data-hidden") == "true")
					html += "  <th style=\"display: none;\">" + formFieldListClone.eq(i).attr("data-label") + "</th>";
				else
					html += "  <th>" + formFieldListClone.eq(i).attr("data-label") + "</th>";
			}

			if (this.options.allowUpdate == "true")
				html += "  <th style=\"width: 50px;\" title=\"Editar\"></th>";

			if (this.options.allowDelete == "true")
				html += "  <th style=\"width: 50px;\" title=\"Excluir\"></th>";

			if (this.options.allowMove == "true")
				html += "  <th style=\"width: 70px;\" title=\"Mover\"></th>";

			html += "    </tr>";
			html += "  </thead>";
			html += "</table>";
			mainDiv.parent().append(html);

			mainDiv.parent().find(":button").puibutton();
		},

		/*
		 * Validando o preenchimento de campos obrigatórios
		 */
		_validateRequiredFields: function()
		{
			var mainDiv = this.element;
			var formFieldListClone = this.options.formFieldListClone;

			for (var i = 0; i < formFieldListClone.length; i++)
			{
				var formField = mainDiv.find("[name='" + formFieldListClone.eq(i).attr("name") + "']");

				// Se o campo estiver marcado como required, então inicia a validação
				if (formField.attr("data-required") == "true")
				{
					var emptyField = false;
					if ((formField.is("select") || formField.is("textarea") || formField.is(":text")) && formField.val() == "")
						emptyField = true;
					else if (formField.is(":checkbox") && formField.prop("checked") == false)
						emptyField = true;

					if (emptyField)
					{
						alert("O campo \"" + formField.attr("data-label") + "\" é de preenchimento obrigatório");
						formField.focus();
						return false;
					}
				}
			}

			return true;
		},

		executeInsert: function ()
		{
			var mainDiv = this.element;
			var formFieldListClone = this.options.formFieldListClone;

			if (!this._validateRequiredFields())
				return false;

			/*
			 * Criando a linha de resultado baseado nos valores inseridos no formulário.
			 */
			var html = "";
			for (var i = 0; i < formFieldListClone.length; i++)
			{
				var formField = mainDiv.find("[name='" + formFieldListClone.eq(i).attr("name") + "']");

				var data = this._getFieldValueByFieldType(formField);

				/*
				 * Verificar se o campo possui a propiedade "data-hidden":
				 *   1) se possuir, ignora qualquer estilo e atribui apenas "display: none";
				 *   2) se não possuir atribui o estilo definido no campo ou vazio, caso nenhum estilo tenha sido definido.
				 */
				var style = "";
				if (formField.attr("data-style") != undefined && formField.attr("data-style") != "")
					style = "style=\"" + formField.attr("data-style") + "\"";
					
				if (formField.attr("data-hidden") != undefined && formField.attr("data-hidden") != "" && formField.attr("data-hidden") == "true")
					html += "<td style=\"display: none;\">" + data.label + "<input type=\"hidden\" name=\"" + formField.attr("name") + "Array\" value=\"" + data.value + "\"></td>";
				else
					html += "<td " + style + ">" + data.label + "<input type=\"hidden\" name=\"" + formField.attr("name") + "Array\" value=\"" + data.value + "\"></td>";
			}

			if (this.options.allowUpdate == "true")
				html += "  <td><a data-name=\"puiBtnPrepareUpdate\" class=\"fa fa-pencil fa-lg\" data-value=\"\" href=\"javascript:void(0)\"></a></td>";

			if (this.options.allowDelete == "true")
				html += "  <td><a data-name=\"puiBtnExecuteRemove\" class=\"fa fa-trash-o fa-lg\" data-value=\"\" href=\"javascript:void(0)\"></a></td>";

			if (this.options.allowMove == "true")
				html += "  <td><a data-name=\"puiBtnMoveUp\" class=\"fa fa-arrow-up fa-lg\" data-value=\"\" href=\"javascript:void(0)\"></a><a data-name=\"puiBtnMoveDown\" class=\"fa fa-arrow-down fa-lg\" data-value=\"\" href=\"javascript:void(0)\"></a></td>";

			mainDiv.parent().find("[data-name='primeuiDatatable']").append("<tr>" + html + "</tr>");

			// Definindo as ações dos botões do componente
			var lastInserted = mainDiv.parent().find("[data-name='primeuiDatatable']").find("tbody").children("tr").last();

			var $this = this;
			lastInserted.find("[data-name='puiBtnExecuteRemove']").on("click.puidynamicform", function(e) {
				$this.executeRemove($(this));
				e.preventDefault();
			});

			lastInserted.find("[data-name='puiBtnPrepareUpdate']").on("click.puidynamicform", function(e) {
				$this.prepareUpdate($(this));
				e.preventDefault();
			});

			lastInserted.find("[data-name='puiBtnMoveUp']").on("click.puidynamicform", function(e) {
				$this.moveUp($(this));
				e.preventDefault();
			});

			lastInserted.find("[data-name='puiBtnMoveDown']").on("click.puidynamicform", function(e) {
				$this.moveDown($(this));
				e.preventDefault();
			});

			jvsEstrutura.prepareHtmlComponents(mainDiv.parent());

			this._resetForm();

			return lastInserted;
		},

		_getFieldValueByFieldType: function (obj)
		{
			var data = {
				label: "",
				value: ""
			}

			if (obj.is("select"))
			{
				if (obj.find(":selected").val() != "")
					data.label = obj.find(":selected").text();

				data.value = obj.find(":selected").val();
			}
			else if (obj.is("textarea"))
			{
				data.label = obj.val();
				data.value = obj.val();
			}
			else if (obj.is("input"))
			{
				if (obj.attr("type") == "text")
				{
					data.label = obj.val();
					data.value = obj.val();
				}
				if (obj.attr("type") == "checkbox")
				{
					if (obj.is(":checked"))
						data.label = "Sim";
					else
						data.label = "Não";

					data.value = obj.is(":checked");
				}
			}

			return data;
		},

		_resetForm: function ()
		{
			var mainDiv = this.element;
			var formFieldListClone = this.options.formFieldListClone;

			for (var i = 0; i < formFieldListClone.length; i++)
			{
				var formField = mainDiv.find("[name='" + formFieldListClone.eq(i).attr("name") + "']");

				if (formField.is("select") || formField.is("textarea") || formField.is(":text"))
					formField.val("");
				else if (formField.is(":checkbox"))
					formField.prop("checked", false);
			}

			// Atribuindo o foco ao primeiro campo do formulário.
			mainDiv.find("[name='" + formFieldListClone.eq(0).attr("name") + "']").focus();

			// Removendo o atributo que identifica uma linha em edição.
			mainDiv.parent().find("[data-name='primeuiDatatable']").children("tbody").children("tr").removeAttr("data-updating");
		},

		executeRemove: function (obj)
		{
			if (this._hasUpdatingData())
				return false;

			if (this.options.confirmDelete == "true")
				if (!confirm("Deseja remover o registro selecionado da lista?"))
					return false;

			obj.closest("tr").hide("slow", function() {
				$(this).remove();
			});

			this._resetForm();

			return true;
        },

        moveUp: function (obj)
        {
			if (this._hasUpdatingData())
				return false;

        	var mainDiv = this.element;
        	var row = obj.closest("tr");
        	var table = mainDiv.parent().find("[data-name='primeuiDatatable']");

        	if (row.index() == 0)
        	{
        		alert("Este é o primeiro registro da tabela e não pode ser movido para cima.");
        		return false;
        	}

        	row.hide("slow", function() {
        		$(this).prev().before($(this));
        		$(this).show("slow");
        	});
        },

        moveDown: function (obj)
        {
			if (this._hasUpdatingData())
				return false;

        	var mainDiv = this.element;
        	var row = obj.closest("tr");
        	var table = mainDiv.parent().find("[data-name='primeuiDatatable']");

        	if (row.index() == table.children("tbody").children("tr").length - 1)
        	{
        		alert("Este é o último registro da tabela e não pode ser movido para baixo.");
        		return false;
        	}

        	row.hide("slow", function() {
        		$(this).next().after($(this));
        		$(this).show("slow");
        	});
        },

        prepareUpdate: function (obj)
		{
			if (this._hasUpdatingData())
				return false;

			var mainDiv = this.element;
			var formFieldListClone = this.options.formFieldListClone;

			// Fazendo o chaveamento dos botões que devem ser exibidos.
			mainDiv.parent().find("[name='puiBtnExecuteInsert']").hide();
			mainDiv.parent().find("[name='puiBtnExecuteUpdate']").show();
			mainDiv.parent().find("[name='puiBtnCancelUpdate']").show();

			// Aplicando uma cor de destaque para o registro a ser editado.
			obj.parent().parent().css("color", "red").css("font-weight", "bold");
			obj.parent().parent().attr("data-updating", "true");

			// Recuperando os valores dos campos hidden e atribuindo aso respectivos campos do formulário
			for (var i = 0; i < formFieldListClone.length; i++)
			{
				var formField = mainDiv.find("[name='" + formFieldListClone.eq(i).attr("name") + "']");
				var hiddenField = obj.closest("tr").find("[name='" + formField.attr("name") + "Array']");

				if (formField.is("select") || formField.is("textarea") || formField.is(":text"))
					formField.val(hiddenField.val());
				else if (formField.is(":checkbox"))
					formField.prop("checked", (hiddenField.val() == "true" ? true : false));

				formField.val(obj.closest("tr").find("[name='" + formField.attr("name") + "Array']").val());
			}
		},

		_hasUpdatingData: function ()
		{
			var mainDiv = this.element;

			// Verificando se há algum registro já preparado para update. Se houver, alerta o usuário e cancela a operação
			var tableBody = mainDiv.parent().find("[data-name='primeuiDatatable']").children("tbody");
			if (tableBody.children("[data-updating='true']").length > 0)
			{
				alert("Existe um registro sendo editado no momento. Confirme ou cancele a edição atual para executar uma nova operação.");
				return true;
			}

			return false;
		},

		executeUpdate: function ()
		{
			var mainDiv = this.element;
			var formFieldListClone = this.options.formFieldListClone;

			if (!this._validateRequiredFields())
				return false;

			var tableBody = mainDiv.parent().find("[data-name='primeuiDatatable']").children("tbody");
			var updatingRow = tableBody.children("[data-updating='true']");
			var newRow = this.executeInsert();

			updatingRow.after(newRow);
			updatingRow.remove();

			// Fazendo o chaveamento dos botões que devem ser exibidos.
			mainDiv.parent().find("[name='puiBtnExecuteInsert']").show();
			mainDiv.parent().find("[name='puiBtnExecuteUpdate']").hide();
			mainDiv.parent().find("[name='puiBtnCancelUpdate']").hide();
		}
    });
});