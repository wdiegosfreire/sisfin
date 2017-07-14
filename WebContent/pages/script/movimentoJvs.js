function addItemMovimento()
{
	var itmNomItem = document.forms["ItemMovimentoForm"].elements["itemMovimentoBean.itmNomItem"].value;
	var itmVlrUnitario = document.forms["ItemMovimentoForm"].elements["itemMovimentoBean.itmVlrUnitario"].value;
	var itmQtdItem = document.forms["ItemMovimentoForm"].elements["itemMovimentoBean.itmQtdItem"].value;
	var movCodMovimento = document.forms["MovimentoForm"].elements["movimentoBean.movCodMovimento"].value;

	MovimentoRemote.insertItemMovimento(itmNomItem, itmVlrUnitario, itmQtdItem, movCodMovimento, 
	{
		callback:function(str)
		{
			lodItemMovimento();
			clsItemMovimento();
		}
	});
}

function addParcela()
{
	var frm = document.forms["ParcelaForm"];

	var parNumParcela = frm.elements["parcelaBean.parNumParcela"].value;
	var parDatVencimento = frm.elements["parcelaBean.parDatVencimento"].value;
	var parDatPagamento = frm.elements["parcelaBean.parDatPagamento"].value;
	var parVlrParcela = frm.elements["parcelaBean.parVlrParcela"].value;
	var parVlrSomaParcelas = frm.elements["parVlrSomaParcelas"].value;
	var movCodMovimento = document.forms["MovimentoForm"].elements["movimentoBean.movCodMovimento"].value;
	var fopCodFormaPagamento = frm.elements["parcelaBean.formaPagamentoBean.fopCodFormaPagamento"].value;
	var itmVlrSomaItens = document.forms["ItemMovimentoForm"].elements["itmVlrSomaItens"].value;
	MovimentoRemote.insertParcela(parNumParcela, parDatVencimento, parDatPagamento, parVlrParcela, movCodMovimento, fopCodFormaPagamento, itmVlrSomaItens, parVlrSomaParcelas, 
	{
		callback:function(str)
		{
			lodParcela();
			clsParcela();
		}
	});
}

function delItemMovimento(itmCodItemMovimento)
{
	if (confirm("Deseja excluir permanentemente este registro?"))
	{
		MovimentoRemote.deleteItemMovimento(itmCodItemMovimento, 
		{
			callback:function(str)
			{
				lodItemMovimento();
				clsItemMovimento();
			}
		});
	}
}

function delParcela(parCodParcela)
{
	if (confirm("Deseja excluir permanentemente este registro?"))
	{
		MovimentoRemote.deleteParcela(parCodParcela, 
		{
			callback:function(str)
			{
				lodParcela();
				clsParcela();
			}
		});
	}
}

function lodItemMovimento()
{
	var movCodMovimento = document.forms["MovimentoForm"].elements["movimentoBean.movCodMovimento"].value;

	MovimentoRemote.loadGridItemMovimento(movCodMovimento, 
	{
		callback:function(str)
		{
			document.getElementById("gridItemMovimento").innerHTML = str;
		}
	});
}

function lodParcela()
{
	var movCodMovimento = document.forms["MovimentoForm"].elements["movimentoBean.movCodMovimento"].value;

	MovimentoRemote.loadGridParcela(movCodMovimento, 
	{
		callback:function(str)
		{
			document.getElementById("gridParcela").innerHTML = str;
		}
	});
}

function clsItemMovimento()
{
	var frm = document.forms["ItemMovimentoForm"];

	frm.elements["itemMovimentoBean.itmNomItem"].value = "";
	frm.elements["itemMovimentoBean.itmVlrUnitario"].value = "0,00";
	frm.elements["itemMovimentoBean.itmQtdItem"].value = "0,00";
	frm.elements["itemMovimentoBean.itmNomItem"].focus();
}

function clsParcela()
{
	var frm = document.forms["ParcelaForm"];

	frm.elements["parcelaBean.parNumParcela"].value = "0";
	frm.elements["parcelaBean.parDatVencimento"].value = "";
	frm.elements["parcelaBean.parDatPagamento"].value = "";
	frm.elements["parcelaBean.parVlrParcela"].value = "0,00";
	frm.elements["parcelaBean.formaPagamentoBean.fopCodFormaPagamento"].value = "";
	frm.elements["parcelaBean.parNumParcela"].focus();
}

function lodComboSubCategoria()
{
	var frm = document.forms["MovimentoForm"];
	var catCodCategoria = frm.elements["btpMovimento.categoriaBean.categoriaBean.catCodCategoria"].value;

	MovimentoRemote.loadComboSubCategoria(catCodCategoria, 
	{
		callback:function(str)
		{
			document.getElementById("comboSubCategoria").innerHTML = str;
		}
	});
}

function setParcelaUnica()
{
	var checked = document.forms["ParcelaForm"].elements["flgParcelaUnica"].checked;

	var frm = document.forms["ParcelaForm"];

	if (checked == true)
	{
		frm.elements["parcelaBean.parNumParcela"].value = 1;
		frm.elements["parcelaBean.parDatVencimento"].value = document.forms["MovimentoForm"].elements["movDatMovimento"].value;
		frm.elements["parcelaBean.parDatPagamento"].value = document.forms["MovimentoForm"].elements["movDatMovimento"].value;
		frm.elements["parcelaBean.parVlrParcela"].value = maskFloatToBraMoney(document.forms["ItemMovimentoForm"].elements["itmVlrSomaItens"].value);
		frm.elements["parcelaBean.formaPagamentoBean.fopCodFormaPagamento"].focus();
	}
	else
	{
		frm.elements["parcelaBean.parNumParcela"].value = "";
		frm.elements["parcelaBean.parDatVencimento"].value = "";
		frm.elements["parcelaBean.parDatPagamento"].value = "";
		frm.elements["parcelaBean.parVlrParcela"].value = "0,00";
	}
}