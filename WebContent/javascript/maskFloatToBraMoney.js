function maskFloatToBraMoney(value)
{
	value = replaceAll(value, '.', ',');

	var decimal = '00';
	var arrayNumero = value.split(',');
	var inteiro = arrayNumero[0];

	if (arrayNumero.length == 2)
	{
		decimal = arrayNumero[1];
	}

	var contador = 0;
	var aux = '';

	for (var i = (inteiro.length - 1); i >= 0; i--)
	{
		aux = inteiro.charAt(i) + aux;
		contador++;

		if (contador == 3 && (i - 1) >= 0)
		{
			aux = '.' + aux;
			contador = 0;
		}
	}

	if (decimal.length == 1)
	{
		decimal += '0';
	}

	value = aux + ',' + decimal;

	return value;
}

function replaceAll(string, token, newtoken)
{
	while (string.indexOf(token) != -1)
	{
		string = string.replace(token, newtoken);
	}

	return string;
}