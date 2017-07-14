// Imports area


//Functions area
function confirmDelete(key)
{
	if (confirm("Deseja excluir permanentemente este registro?"))
	{
		window.location = "conta.do?cmd=actExecDelete&btpConta.conCodConta=" + key;
	}
}

function conCodContaPaiChange()
{
//	var nivel = $("[name='btpConta.btpContaPai.conCodConta']").find("option:selected").text().split(".");
	var nivel = $("[name='btpConta.btpContaPai.conCodConta']").find("option:selected").attr("data-level");

	if (nivel == 3)
	{
		var html = "";
		html += "<input type=\"radio\" name=\"btpConta.conFlgMovimento\" value=\"1\" />Movimento";
		html += "<input type=\"radio\" name=\"btpConta.conFlgPoupanca\" value=\"1\" />Poupança";
		html += "<input type=\"radio\" name=\"btpConta.conFlgVirtual\" value=\"1\" />Virtual";

		$("#divAtributo").html(html);
	}
	else
	{
		$("#divAtributo").html("");
	}
}