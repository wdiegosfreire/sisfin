// Imports area


//Functions area
function confirmDelete(key)
{
	if (confirm("Deseja excluir permanentemente este registro?"))
	{
		window.location = "categoria.do?cmd=actExecDelete&btpCategoria.catCodCategoria=" + key;
	}
}