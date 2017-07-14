(function($)
{
	/*
	 * Declaração do array que comportará os atributos de configuração do plugin
	 */
	var defaults = {
		indexAlign: "left",
		transitionTime: 0,
		displayedItens: 1,
		buttonFirst: " |< ",
		buttonPrev: " < ",
		buttonNext: " > ",
		buttonLast: " >| ",
		extraHtml: ""
	};

	/*
	 * Declaração do array que comportará os métodos do plugin
	 */
	var methods = {
		init : function()
		{
			return this.each(function() {

				// 1) Verificar se a estrutura html está de acordo com o plugin
				if (this.tagName != "DIV")
				{
					alert("A <div> element must be defined as a main container.");
					return false;
				}

				var div = $(this);
				var list = div.find(":first-child");
				var listItem = list.find("li");

				if (div.find("ul").html() == null && div.find("ol").html() == null)
				{
					alert("Object type is not supported! Only objects type <ol> and <ul> can be used.");
					return false;
				}

				// Para cada item da lista, chama a função hide()
				listItem.each(function() {
					$(this).hide();
				});

				// Seleciona o primeiro elemento da lista e chama a função show()
//				for (var i = 0; i < defaults.displayedItens; i++)
//					list.find("li").eq(i).show();
					

				// Remove a barra de índice de navegação
				div.find("[class='carousel-index']").remove();

				// Recria a barra de índice de navegação
				var count = Math.ceil(list.find("li").length / defaults.displayedItens);

				var tmp = "";
				tmp += "<select tabindex=\"-1\" onchange=\"$(this).makeCarousel('goTo', null, this.value)\">";
				for (var i = 0; i < count; i++)
					tmp += "<option value=\"" + i + "\">" + (i + 1) + "</option>";
				tmp += "</select>";

				var html = "";

				html += "<div class=\"carousel-index\">";

				if (defaults.extraHtml != "")
					html += "<div>" + defaults.extraHtml + "</div>";

				html += "  <span onclick=\"$(this).makeCarousel('first');\">" + defaults.buttonFirst + "</span>";
				html += "  <span onclick=\"$(this).makeCarousel('prev');\">" + defaults.buttonPrev + "</span>";
				html +=    tmp;
				html += "  <span onclick=\"$(this).makeCarousel('next');\">" + defaults.buttonNext + "</span>";
				html += "  <span onclick=\"$(this).makeCarousel('last');\">" + defaults.buttonLast + "</span>";
				html += "  <span>N° de itens: " + list.find("li").length + "</span><br />";
				html += "</div>";

				// Adiciona a barra de navegação ao container DIV
				div.append(html);

				var i = div.find(":first-child").find("li:visible").index();
				div.find("[class='carousel-index']").find("select").find("option").eq(i).attr("selected", "selected");

				/*
				 * Aplicando as configurações defaults à barra de navegação.
				 */
				$(".carousel-index").css("text-align", defaults.indexAlign);
				$(".carousel-index").css("margin-top", "10px");

				$(".carousel-index").find("span").each(function() {
					$(this).css("cursor", "pointer");
					$(this).css("font-weight", "bold");
					$(this).css("font-family", "courier");
					$(this).css("opacity", "0.5");
					$(this).css("vertical-align", "middle");
				});

				$(".carousel-index").find("span").mouseover(function() {
					$(this).css("opacity", "1");
				});

				$(".carousel-index").find("span").mouseout(function() {
					$(this).css("opacity", "0.5");
				});

				for (var i = 0; i < defaults.displayedItens; i++)
				{
					var lastIndex = list.find("li").last().index();
					list.find("li").eq(lastIndex - i).show();
				}
			});
		},
		next : function()
		{ 
			var liTot = this.parent().prev().find("li");
			var liVis = this.parent().prev().find("li:visible");

			var newIndex = (liVis.first().index() + defaults.displayedItens);

			liVis.hide(defaults.transitionTime);

			if (newIndex >= liTot.length)
				newIndex = 0;

			$(".carousel-index").find("select").val(Math.floor(newIndex / defaults.displayedItens));

			for (var i = 0; i < defaults.displayedItens; i++)
			{
				liTot.eq(newIndex).show(defaults.transitionTime);
				newIndex++;
			}
		},
		prev : function()
		{
			var liTot = this.parent().prev().find("li");
			var liVis = this.parent().prev().find("li:visible");

			var newIndex = liVis.first().index() - defaults.displayedItens;

			liVis.hide(defaults.transitionTime);

			if (newIndex < 0)
				newIndex = Math.floor(liTot.last().index() / defaults.displayedItens) * defaults.displayedItens;

			$(".carousel-index").find("select").val(Math.floor(newIndex / defaults.displayedItens));

			for (var i = 0; i < defaults.displayedItens; i++)
			{
				liTot.eq(newIndex).show(defaults.transitionTime);
				newIndex++;
			}
		},
		first : function()
		{
			var liTot = this.parent().prev().find("li");
			var liVis = this.parent().prev().find("li:visible");

			var newIndex = 0;

			liVis.hide(defaults.transitionTime);

			$(".carousel-index").find("select").val(Math.floor(newIndex / defaults.displayedItens));

			for (var i = 0; i < defaults.displayedItens; i++)
			{
				liTot.eq(newIndex).show(defaults.transitionTime);
				newIndex++;
			}
		},
		last : function()
		{
			var liTot = this.parent().prev().find("li");
			var liVis = this.parent().prev().find("li:visible");

			var newIndex = Math.floor(liTot.last().index() / defaults.displayedItens) * defaults.displayedItens;

			liVis.hide(defaults.transitionTime);

			$(".carousel-index").find("select").val(Math.floor(newIndex / defaults.displayedItens));

			for (var i = 0; i < defaults.displayedItens; i++)
			{
				liTot.eq(newIndex).show(defaults.transitionTime);
				newIndex++;
			}

//			var ul = this.parent().prev();
//
//			ul.find("li:visible").hide(defaults.transitionTime);
//			ul.find("li").last().show(defaults.transitionTime);
//
//			var i = this.parent().prev().find("li:visible").index();
//			this.parent().find("select").find("option").eq(i).attr("selected", "selected");
		},
		goTo : function(index)
		{
			var listItem = $(this).parent().prev().find("li");

			listItem.each(function() {
				$(this).hide(defaults.transitionTime);
			});

			index = index * defaults.displayedItens;

			for (var i = 0; i < defaults.displayedItens; i++)
			{
				listItem.eq(index).show(defaults.transitionTime);
				index++;
			}
		}
	};

	/*
	 * Capturando a chamada aos métodos do plugin
	 */
	jQuery.fn.makeCarousel = function(method, options, index)
	{
		options = $.extend(defaults, options);

		/*
		 * Capturando a chamada de métodos
		 */
		if (methods[method])
		{
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 2));
		}
		else if (typeof method === "object" || ! method)
		{
			return methods.init.apply(this, arguments);
		}
		else
		{
			$.error("Method " + method + " does not exist on jQuery.carousel");
		}
	};

})(jQuery);