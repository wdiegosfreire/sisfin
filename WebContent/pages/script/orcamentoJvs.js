function addOrcamentoRendimento()
{
	var renCodRendimento = document.forms["OrcamentoRendimentoForm"].elements["btpOrcamentoRendimento.btpRendimento.renCodRendimento"].value;
	var orrVlrRendimento = document.forms["OrcamentoRendimentoForm"].elements["btpOrcamentoRendimento.orrVlrRendimento"].value;
	var orcCodOrcamento = document.forms["OrcamentoForm"].elements["orcamentoBean.orcCodOrcamento"].value;

	OrcamentoRemote.insertOrcamentoRendimento(renCodRendimento, orrVlrRendimento, orcCodOrcamento, 
	{
		callback:function(str)
		{
			lodOrcamentoRendimento();
			clsOrcamentoRendimento();
		}
	});
}

function delOrcamentoRendimento(orrCodOrcamentoRendimento)
{
	if (confirm("Deseja excluir permanentemente este registro?"))
	{
		OrcamentoRemote.deleteOrcamentoRendimento(orrCodOrcamentoRendimento, 
		{
			callback:function(str)
			{
				lodOrcamentoRendimento();
				clsOrcamentoRendimento();
			}
		});
	}
}

function lodOrcamentoRendimento()
{
	var orcCodOrcamento = document.forms["OrcamentoForm"].elements["orcamentoBean.orcCodOrcamento"].value;

	OrcamentoRemote.loadGridOrcamentoRendimento(orcCodOrcamento, 
	{
		callback:function(str)
		{
			document.getElementById("gridOrcamentoRendimento").innerHTML = str;
		}
	});
}

function clsOrcamentoRendimento()
{
	document.forms["OrcamentoRendimentoForm"].elements["btpOrcamentoRendimento.btpRendimento.renCodRendimento"].value = "";
	document.forms["OrcamentoRendimentoForm"].elements["btpOrcamentoRendimento.orrVlrRendimento"].value = "";
	document.forms["OrcamentoRendimentoForm"].elements["btpOrcamentoRendimento.btpRendimento.renCodRendimento"].focus();
}