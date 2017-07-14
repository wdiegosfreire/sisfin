// Imports area


//Functions area

/**
 * Copia o valor informado no campo "obiTxtDescricao" para o primeiro campo "btpObjetivo.objTxtDescricao".
 */
function copyTemplateToObjetivo()
{
	$("[name='btpObjetivo.objTxtDescricao']").val($("[name='btpTemplate.temCodTemplate'] option:selected").text());
}

/**
 * Copia o valor informado no campo "obiTxtDescricao" para o primeiro campo "btpObjetivo.objTxtDescricao".
 */
function copyObjetivoToItem()
{
	if ($("[name='FrmObjetivo']").find("[name='btpObjetivo.objTxtDescricao']").val() != "")
		$("[name='obiTxtDescricao']").eq(0).val($("[name='FrmObjetivo']").find("[name='btpObjetivo.objTxtDescricao']").val());
}

/**
 * Copia o valor informado no campo "Data de Vencimento" para o campo "Data de Pagamento".
 */
function copyVencimentoToPagamento(obj)
{
	obj.closest("tr").find("[name='movDatPagamento']").val(obj.val());
}

function setDefaultEstabelecimento(value)
{
	$("[name='btpObjetivo.btpEstabelecimento.estCodEstabelecimento']").val(value);
	$("[name='btpObjetivo.btpEstabelecimento.estCodEstabelecimento']").attr("tabindex", 1000);
}

function setDefaultContaOrigem(value)
{
	conContaOrigemDefaultValue = value;
}

function setDefaultContaDestino(value)
{
	conContaDestinoDefaultValue = value;
}

function setDefaultFormaPagamento(value)
{
	fopCodFormaPagamentoDefaultValue = value;
}

function resetTemplate()
{
	$("[name='btpObjetivo.objTxtDescricao']").val("");
	$("[name='FrmObjetivo']").find("[name='btpObjetivo.objTxtDescricao']").val("")
	$("[name='btpObjetivo.btpEstabelecimento.estCodEstabelecimento']").val("");

	$("[name='btpObjetivo.btpEstabelecimento.estCodEstabelecimento']").removeAttr("tabindex");

	conContaOrigemDefaultValue = null;
	conContaDestinoDefaultValue = null;
	fopCodFormaPagamentoDefaultValue = null;

	$("#divMovimentoFrm").find("[name='conContaOrigem']").removeAttr("tabindex");
	$("#divMovimentoFrm").find("[name='conContaDestino']").removeAttr("tabindex");
	$("#divMovimentoFrm").find("[name='fopCodFormaPagamento']").removeAttr("tabindex");

	jvsObjetivo.resetForm();
}