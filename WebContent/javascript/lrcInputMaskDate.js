function inputMaskDate(field, event)
{
	//Declarando as variáveis
	var vlrInicial = field.value;
	var key = '';
	var i = 0;
	var len = 0;
	var strCheck = '0123456789';
	var aux = '';
	var whichCode = (window.Event) ? event.which : event.keyCode;

	//Checar se usurio apertou o enter
	if (whichCode == 13) 
	{
		return true;
	}

	// Verifica se o usuário pressionou uma tecla válida
	key = String.fromCharCode(whichCode); 
	if (strCheck.indexOf(key) == -1) 
	{
		return false;  // Not a valid key
	}

	len = field.value.length;
	aux = '';
	for(i = 0; i < len; i++)
	{
		if (strCheck.indexOf(field.value.charAt(i))!=-1) 
		{
			aux += field.value.charAt(i);
		}
	}

	// A string aux contém apenas os números
	aux += key;
	len = aux.length;
	// Reiniciando o valor do campo
	field.value = '';

	//Se o tamanho do campo for maior que 16, não faz nada;
	if (len > 8)
	{
		field.value = vlrInicial;
		return false;
	}
    
	//Nesse laço, percorro os números já formatando em XXXXXX-X
	for(i = 0; i < len; i++)
	{
		if (i == 2) 
		{
			field.value += '/';
		}
	
		if (i == 4) 
		{
			field.value += '/';
		}
	
		field.value += aux.charAt(i);
	}

	return false;
}