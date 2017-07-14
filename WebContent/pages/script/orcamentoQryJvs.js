function lodOrcamentoRendimento(orcCodOrcamento)
{
	OrcamentoRemote.loadGridOrcamentoRendimento(orcCodOrcamento, 
	{
		callback:function(str)
		{
			document.getElementById("gridOrcamentoRendimento").innerHTML = str;
		}
	});
}