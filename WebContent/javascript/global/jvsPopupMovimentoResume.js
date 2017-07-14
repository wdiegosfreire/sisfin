var tableRow = null
function showPopupMovimentoResume(row) 
{
	tableRow = row;

	var map = {
		sqlOrder: 2
	};

	btpObjetivo = {
		objCodObjetivo: tableRow.find("[name='btpObjetivo.objCodObjetivo']").val(),
		map: map
	};

	DwrObjetivo.actExecSelect(btpObjetivo, 
	{
		callback:function(btpObjetivoList)
		{
			var popup = null;

			popup = new PopupWindow("popupWindowContainer");
			popup.popupBuilder("asdf");
			popup.setSize(300, "");
			popup.setTitle("Resumo do Objetivo");

			var html = "";

			html += "<div style=\"width: 100%;\" class=\"fontePadraoNegrito\">";

			html += "  <span>Objetivo:</span>";

			html += "  <table width=\"100%\" border=\"0\">";
			html += "    <tr class=\"fontePadraoNegrito\">";
			html += "      <td>Parcela</td>";
			html += "      <td>Vencimento</td>";
			html += "      <td>Pagamento</td>";
			html += "      <td align=\"right\" width=\"1\">Valor</td>";
			html += "      <td align=\"right\" width=\"1\">&nbsp;</td>";
			html += "    </tr>";

			var vlrTotal = 0.0;
			var vlrTotalPago = 0.0;
			for (var i = 0; i < btpObjetivoList.length; i++)
			{
				for (var j = 0; j < btpObjetivoList[i].btpMovimentoList.length; j++)
				{
					vlrTotal += btpObjetivoList[i].btpMovimentoList[j].movVlrMovimentado;

					html += " <tr class=\"fontePadrao\" onmouseover=\"lightOn(this)\" onmouseout=\"lightOff(this)\">";
					html += "   <td>" + btpObjetivoList[i].btpMovimentoList[j].movNumParcela + "</td>";
					html += "   <td>" + btpObjetivoList[i].btpMovimentoList[j].movDatVencimento.toFormat(datePatternEnum.DIA_MES_ANO) + "</td>";

					if (btpObjetivoList[i].btpMovimentoList[j].movDatPagamento != null)
						html += "   <td>" + btpObjetivoList[i].btpMovimentoList[j].movDatPagamento.toFormat(datePatternEnum.DIA_MES_ANO) + "</td>";
					else 
						html += "   <td></td>";
					
					html += "   <td align=\"right\">" + numberObjectToStringMoney(btpObjetivoList[i].btpMovimentoList[j].movVlrMovimentado) + "</td>";

					if (btpObjetivoList[i].btpMovimentoList[j].movDatPagamento != null)
					{
						vlrTotalPago += btpObjetivoList[i].btpMovimentoList[j].movVlrMovimentado;
						html += "<td><img src=\"images/icon-016x016-checked.png\"></td>";
					}

					html += " </tr>";
				}
			}

			html += "    <tr class=\"fontePadraoNegrito\">";
			html += "      <td align=\"right\" colspan=\"3\">Total:<br />Pago:<br />Restante:</td>";
			html += "      <td align=\"right\" style=\"border-top: solid 1px\">";
			html += "        <span>" + numberObjectToStringMoney(vlrTotal) + "</span>";
			html += "        <span>" + numberObjectToStringMoney(vlrTotalPago) + "</span>";
			html += "        <span>" + numberObjectToStringMoney(vlrTotal - vlrTotalPago) + "</span>";
			html += "      </td>";
			html += "      <td align=\"right\">&nbsp;</td>";
			html += "    </tr>";

			html += "  </table>";

			html += "</div>";

			popup.setBodyContent(html);

			popup.show();
		}
	});
}

function execConfirmPaymentDate()
{
	var btpMovimento = {
		movCodMovimento: tableRow.find("[name='btpMovimento.movCodMovimento']").val(), 
		movDatPagamento: stringDateToObjectDate($("[name='btpMovimento.movDatPagamento']").val())
	}

	DwrMovimento.actExecConfirmPaymentDate(btpMovimento,
	{
		callback:function(btpFormaPagamentoList)
		{
			popupHide();

			var map = {
				numMes: $("[name='btpObjetivo.map.numMes']").val(),
				numAno: $("[name='btpObjetivo.map.numAno']").val()
			};

			var btpObjetivo = {
				map: map
			};

			execSelectObjetivo(btpObjetivo);
		}
	});
}