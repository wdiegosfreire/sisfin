function showPopupTemplateRegra() 
{
	DwrObjetivo.getBtpRegraList(
	{
		callback:function(btpRegraList)
		{
			var html = "";

			html += "<div style=\"width: 90%;\">";

			html += "<table>";
			html += "  <tr>";
			html += "    <td colspan=\"3\">";
			html += "      Informe um nome para o seu template:<br />";
			html += "      <input type=\"text\" style=\"width: 500px;\" name=\"btpTemplate.temTxtNome\"><br /><hr />";
			html += "    </td>";
			html += "  </tr>";

			for (var i = 0; i < btpRegraList.length; i++)
			{
				html += "  <tr>";
				html += "    <td>" + btpRegraList[i].regTxtDescricao + "<input type=\"hidden\" name=\"btpTemplateRegra.btpRegra.regCodRegra\" value=\"" + btpRegraList[i].regCodRegra + "\"></td>";
				html += "    <td><input type=\"checkbox\" name=\"chxUseThis\"></td>";

				if (btpRegraList[i].regCodRegra == 4 || btpRegraList[i].regCodRegra == 5)
				{
					html += "<td>";
					html += "  <select name=\"btpTemplateRegra.terCodValorAssociado\">";
					html += "    <option>Selecione...</option>";
					html +=      htmlContaComboOptionList
					html += "  </select>";
					html += "</td>";
				}

				if (btpRegraList[i].regCodRegra == 6)
				{
					html += "<td>";
					html += "  <select name=\"btpTemplateRegra.terCodValorAssociado\">";
					html += "    <option>Selecione...</option>";
					html +=      htmlFormaPagamentoComboOptionList
					html += "  </select>";
					html += "</td>";
				}

				if (btpRegraList[i].regCodRegra == 3)
				{
					var opts = $("[name='btpObjetivo.btpEstabelecimento.estCodEstabelecimento']").find("option");
					html += "<td>";
					html += "  <select name=\"btpTemplateRegra.terCodValorAssociado\">";
					html += "    <option>Selecione...</option>";

					for (var j = 0; j < opts.length; j++)
						html += "<option value=\"" + opts[j].value + "\">" + opts[j].text + "</option>";

					html += "  </select>";
					html += "</td>";
				}
//				

				html += "  </tr>";
			}

			html += "<tr><td><input type=\"button\" onclick=\"btnConfirmarClick();\" value=\"Confirmar\"></td></tr>"

			html += "</table>";

			html += "</div>";

			var popup = null;

			popup = new PopupWindow("popupWindowContainer");
			popup.popupBuilder("popupTemplateRegra");
			popup.setSize(800, "");
			popup.setTitle("Cadastro de Templates de Despesas");
			popup.setBodyContent(html);
			popup.show();
		}
	});
}

function btnConfirmarClick()
{
	var container = $("#popupWindowContainer");
	
	var btpTemplateRegraList = new Array();

	var tr = container.find("tr");

	for (var i = 0; i < tr.length; i++)
	{
		if (tr.eq(i).find("[name='chxUseThis']").attr("checked") == "checked")
		{
			var btpTemplateRegra = {
				terCodValorAssociado: tr.eq(i).find("[name='btpTemplateRegra.terCodValorAssociado']").val(),
				btpRegra: {regCodRegra: tr.eq(i).find("[name='btpTemplateRegra.btpRegra.regCodRegra']").val()}
			};

			btpTemplateRegraList.push(btpTemplateRegra);
		}
	}

	var btpTemplate = {
		temTxtNome: container.find("[name='btpTemplate.temTxtNome']").val(),
		btpTemplateRegraList: btpTemplateRegraList
	};

	DwrTemplate.actExecInsert(btpTemplate, 
	{
		callback:function(btpContaList)
		{
			
			alert("Template cadastrado com sucesso!");
		}
	});
}