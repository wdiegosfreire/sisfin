function confirmarExclusao()
{
	if (confirm("Deseja excluir permanentemente este registro?"))
	{
		return true;
	}
	else
	{
		return false;
	}
}