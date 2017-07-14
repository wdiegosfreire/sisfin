$(document).ready(function()
{
	$("[name='btpCompetencia.comDatMes']").focus();

	// Criando os eventos dos componentes da página (botões, caixas de seleção etc.).
	$("[name='btpCompetencia.comDatMes']").change(function() {
		jvsResumoMain.cbxComDatMesChange();
	});

	$("[name='btpCompetencia.comDatAno']").change(function() {
		jvsResumoMain.txtComDatAnoChange();
	});

	$("[name='btpCompetencia.comDatAno']").blur(function() {
		jvsResumoMain.txtComDatAnoBlur();
	});

	$("[name='conCodConta']").change(function() {
		jvsResumoMain.cbxConCodContaChange();
	});

	$("#periodContainer").swipe({
		swipe: function(event, direction, distance, duration, fingerCount) {
			jvsResumoMain.periodContainerSwipe(direction);
		},
		threshold: 5
	});

	jvsResumoMain.pageLoad();
});

var jvsResumoMain = new JvsResumoMain();
function JvsResumoMain()
{
	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Obrigatórias                                        |
	 * +----------------------------------------------------------------------+
	 */
	this.pageLoad = function()
	{
		carregarDados();
	};

	this.validateFormData = function()
	{
		if ($("[name='btpUsuario.usuTxtEmail']").val() == "")
			message.addError("Campo Obrigatório", "Usuário Error");
		if ($("[name='auxTxtSenha']").val() == "")
			message.addError("Campo Obrigatório", "Senha");

		if (message.size() > 0)
		{
			message.show();
			return false;
		}

		return true;
	};

	this.cbxComDatMesChange = function()
	{
		carregarDados();
	};

	this.txtComDatAnoChange = function()
	{
		carregarDados();
	};

	this.txtComDatAnoBlur = function()
	{
		carregarDados();
	};

	this.cbxConCodContaChange = function()
	{
		carregarDados();
	};

	this.periodContainerSwipe = function(direction)
	{
		var mes = parseInt($("[name='btpCompetencia.comDatMes']").val());
		var ano = parseInt($("[name='btpCompetencia.comDatAno']").val());

		if (direction == "left")
		{
			if (mes == 12)
			{
				mes = 1;
				ano++;
			}
			else
			{
				mes++;
			}
		}
		else if (direction == "right")
		{
			if (mes == 1)
			{
				mes = 12;
				ano--;
			}
			else
			{
				mes--;
			}
		}

		if (mes < 10)
			mes = "0" + mes;

		$("[name='btpCompetencia.comDatMes']").val(mes);
		$("[name='btpCompetencia.comDatAno']").val(ano);

		carregarDados();
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Privadas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
	function carregarDados()
	{
		jvsEstrutura.showLoading();

		if ($("[name='btpCompetencia.comDatMes']").val() == "" || $("[name='btpCompetencia.comDatAno']").val() == "")
			return false;

		var map = {
			conCodConta: $("[name='conCodConta']").val()
		};

		var btpCompetencia = {
			comDatMes: $("[name='btpCompetencia.comDatMes']").val(),
			comDatAno: $("[name='btpCompetencia.comDatAno']").val(),
			map: map
		};

		DwrResumo.execute(btpCompetencia, "actShowMainPage",
		{
			callback:function(dwrReturn)
			{
				carregarTabelaResumoMensal(dwrReturn);

				$("#divGraficoReceitaVsDespesa").html("<ul class=\"puicarousel\" data-headertext=\"Entrada vs. Saída\" data-numvisible=\"1\"></ul>")
				for (var i = 0; i < dwrReturn.mapResult.btpContaList.length; i++)
				{
					var btpConta = dwrReturn.mapResult.btpContaList[i];

					$("#divGraficoReceitaVsDespesa").children("ul").append("<li id=\"tab" + btpConta.conCodConta + "\"></li>");

					carregarGraficoReceitaVsDespesa(btpConta);
				}

				carregarGraficoDespesaPorCategoria(dwrReturn);
				carregarGraficoLinhaDoTempoDeCategoria(dwrReturn);

				jvsEstrutura.prepareHtmlComponents($(".resultContainer"));
				jvsEstrutura.prepareHtmlComponents($("#divGraficoReceitaVsDespesa"));
				jvsEstrutura.hideLoading();
			}
		});
	};

	function carregarTabelaResumoMensal(dwrReturn)
	{
		var html = "";
		var auxVlrAnteriorTotal = parseFloat(0);
		var auxVlrEntradaTotal = 0;
		var auxVlrSaidaTotal = 0;
		var auxVlrEconomiaTotal = 0;
		var auxVlrAcumuladoTotal = 0;
		for (var i = 0; i < dwrReturn.mapResult.btpContaList.length; i++)
		{
			var btpConta = dwrReturn.mapResult.btpContaList[i];

			html += "<tr>";
			html += "  <td class=\"left\">" + btpConta.conTxtDescricao + "</td>";
			html += "  <td class=\"right\">" + numberObjectToStringMoney(btpConta.map.auxVlrAnterior) + "</td>";
			html += "  <td class=\"right\">" + numberObjectToStringMoney(btpConta.map.auxVlrEntrada) + "</td>";
			html += "  <td class=\"right\">" + numberObjectToStringMoney(btpConta.map.auxVlrSaida) + "</td>";
			html += "  <td class=\"right\">" + numberObjectToStringMoney(btpConta.map.auxVlrEconomia) + "</td>";
			html += "  <td class=\"right\">" + numberObjectToStringMoney(btpConta.map.auxVlrAcumulado) + "</td>";
			html += "</tr>";

			auxVlrAnteriorTotal += parseFloat(btpConta.map.auxVlrAnterior);
			auxVlrEntradaTotal += parseFloat(btpConta.map.auxVlrEntrada);
			auxVlrSaidaTotal += parseFloat(btpConta.map.auxVlrSaida);
			auxVlrEconomiaTotal += parseFloat(btpConta.map.auxVlrEconomia);
			auxVlrAcumuladoTotal += parseFloat(btpConta.map.auxVlrAcumulado);
		}

		if (dwrReturn.mapResult.btpContaList.length > 0)
		{
			html += "<tr>";
			html += "  <td class=\"left c-red\">Totais</td>";
			html += "  <td class=\"right c-red\">" + numberObjectToStringMoney(auxVlrAnteriorTotal) + "</td>";
			html += "  <td class=\"right c-red\">" + numberObjectToStringMoney(auxVlrEntradaTotal) + "</td>";
			html += "  <td class=\"right c-red\">" + numberObjectToStringMoney(auxVlrSaidaTotal) + "</td>";
			html += "  <td class=\"right c-red\">" + numberObjectToStringMoney(auxVlrEconomiaTotal) + "</td>";
			html += "  <td class=\"right c-red\">" + numberObjectToStringMoney(auxVlrAcumuladoTotal) + "</td>";
			html += "</tr>";
		}

		$("[data-name='primeuiDatatable']").children("tbody").html(html);
	};

	function carregarGraficoReceitaVsDespesa(btpConta)
	{
		var id = "tab" + btpConta.conCodConta;
		var periodo = $("[name='btpCompetencia.comDatMes']").val() + "/" + $("[name='btpCompetencia.comDatAno']").val();

		Highcharts.chart(id, {
			chart: {
				type: "column",
				height: 300
			},
			title: {
				text: btpConta.conTxtDescricao
			},
			xAxis: {
				categories: [periodo],
				crosshair: true
			},
			yAxis: {
				min: 0,
				title: {text: "Valor (R$)"}
			},
			tooltip: {
				headerFormat: "<span style=\"font-size:10px\">{point.key}</span><table>",
				pointFormat: "<tr><td style=\"color: {series.color}; padding: 0;\">{series.name}: </td><td style=\"padding: 0\"><b>{point.y:.2f} R$</b></td></tr>",
				footerFormat: "</table>",
				shared: true,
				useHTML: true
			},
			series: [{
				name: "Entrada",
				data: [parseFloat(btpConta.map.auxVlrEntrada)],
				color: "green"
			}, {
				name: "Saída",
				data: [parseFloat(btpConta.map.auxVlrSaida)],
				color: "red"
			}]
		});
	};

	function carregarGraficoDespesaPorCategoria(dwrReturn)
	{
		var btpContaList = dwrReturn.mapResult.btpContaListDespesaCategoriaGraph;
		if (btpContaList == undefined || btpContaList.length == 0)
		{
			$("#divGraficoDespesaPorCategoria").html("");
			return false;
		}

		var values = new Array();
		for (var i = 0; i < btpContaList.length; i++)
		{
			var auxVlrTotal = parseFloat(btpContaList[i].map["AUX_VLR_TOTAL"]);
			values.push([btpContaList[i].conTxtDescricao, auxVlrTotal]);
		}
		
		$("#divGraficoDespesaPorCategoria").highcharts({
			title: {text: ""},
			tooltip: {
				pointFormat: "<b>{point.y:2f}</b> ({point.percentage:.2f}%)",
				percentageDecimals: 1
			},
			chart: {
				height: 300
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: "pointer",
					dataLabels: {enabled: false},
					showInLegend: true
				}
			},
			series: [{
				type: "pie",
				name: "Despesa",
				data: values
			}]
		});
	};

	function carregarGraficoLinhaDoTempoDeCategoria(dwrReturn)
	{
		if ($("[name='conCodConta']").val() == "")
		{
			$("#divGraficoLinhaDoTempoDeCategoria").html("");
			return false;
		}

		var categoriaArray = new Array();
		var serieArray = new Array();
		for (var i = 0; i < dwrReturn.mapResult.btpContaListLinhaTempoGraph.length; i++)
		{
			var btpConta = dwrReturn.mapResult.btpContaListLinhaTempoGraph[i];

			if (i == 0)
			{
				var aux = btpConta.map["categoria"].split(";")
				for (var j = 5; j >= 0; j--)
					categoriaArray.push(aux[j]);
			}

			var dataArray = new Array();

			for (var j = 5; j >= 0; j--)
				dataArray.push(parseFloat(btpConta.map[j]));

			var conta = {
				name: btpConta.conTxtDescricao,
				data: dataArray
			}

			serieArray.push(conta);
		}

		$("#divGraficoLinhaDoTempoDeCategoria").highcharts({
			chart: {
				type: "spline",
				height: 300
			},
			title: {
				text: "",
			},
			xAxis: {
				categories: categoriaArray
			},
			yAxis: {
				min: 0,
				title: {text: "Valor (R$)"}
			},
			series: serieArray
	    });
	};

	/* +----------------------------------------------------------------------+
	 * | Funções Públicas Personalizadas                                      |
	 * +----------------------------------------------------------------------+
	 */
}