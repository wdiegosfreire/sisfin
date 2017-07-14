var tableRow = null
function showPopupConfirmPaymentDate(row) 
{
	tableRow = row;

	var popup = null;

	popup = new PopupWindow("popupWindowContainer");
	popup.popupBuilder("asdf");
	popup.setSize(350, "");
	popup.setTitle("Confirmar data de pagamento");

	var html = "";

	html += "<div style=\"width: 100%;\">";
	html += "  <span>Data de pagamento:</span>";
	html += "  <input type=\"text\" name=\"btpMovimento.movDatPagamento\" class=\"fieldDate\" />";
	html += "</div>";
	html += "<div style=\"height: 40px;\">";
	html += "  <input type=\"button\" name=\"btnConfirmarDataPagamento\" onclick=\"execConfirmPaymentDate();\" value=\"Confirmar\" style=\"margin-top: 15px;\" />";
	html += "</div>";

	popup.setBodyContent(html);
	popup.show();

	jQuery(function($)
	{
		$("[name='btpMovimento.movDatPagamento']").mask("99/99/9999");
		$("[name='btpMovimento.movDatPagamento']").datepicker({
			changeMonth: true,
			changeYear: true,
			buttonText: 'Calendário',
			showOn: "button",
			buttonImage: "images/calendario.jpg",
			buttonImageOnly: true
		});
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