function maskFormatter(obj, type, tamanho1, tamanho2, sinal)
{
	// Descrição: Máscaras para edição de campos de formulário.
	// usar sinal = 1 para valores positivos/negativos. tamanho1 e tamanho2 são opcionais e determinam o tamanho máximo de um campo numérico e suas casas decimais.
	// types: cep,cpf,cgc,cnpj10,ddd,ramal,fone,celular,DD/MM/AA,DD/MM/AAAA,MM/AAAA,IE,caracter,numero,valor,percentual,cartao,cc,poupanca,unidade, HH:MM
	// Exemplo: onKeyUp="mascara(this, 'cep');"
	// Exemplo: onKeyUp="mascara(this, 'valor', 13, 2);"
	// Exemplo: onKeyUp="mascara(this, 'valor', 13, 2, 1);"

	if ((event.keyCode == 8) || (event.keyCode == 13) || (event.keyCode == 37) || (event.keyCode == 39) || (event.keyCode == 46) || (event.keyCode == 16) || (event.keyCode == 17))
		return;

	tamanho1 = toFloat(tamanho1);
	tamanho2 = toFloat(tamanho2);
	retorno = '';

	switch (type)
	{
		// 99999-999
		case 'cep':
		{
			obj.maxLength = 9;
			retorno = limpaParaMascara(obj.value, 'numeros');
			retorno = retorno.substr(0, 9);
	
			if (retorno.length >= 8 && (retorno-0 == 0) )
			{
				alerta(retorno.substr(0, 5) + "-" + retorno.substr(5, 7) + "\n" + "CEP inválido.");
				obj.value = "";
				obj.focus();
	
				return;
			}

			if (retorno.length >= 5)
			{
				retorno = retorno.substr(0, 5) + "-" + retorno.substr(5, 7);
			}
	
			obj.value = retorno.substr(0, 9);
	
			break;
		}

			// 999.999.999-99
		case 'cpf':
		{
			obj.maxLength = 14;
			retorno = limpaParaMascara(obj.value, 'numeros');
	
			if (retorno.length >= 3)
			{
				retorno = retorno.substr(0, 3) + "." + retorno.substr(3);
			}
			if (retorno.length >= 7)
			{
				retorno = retorno.substr(0, 7) + "." + retorno.substr(7);
			}
			if (retorno.length >= 11)
			{
				retorno = retorno.substr(0, 11) + "-" + retorno.substr(11);
			}
	
			retorno = retorno.substr(0, 14);
			obj.value = retorno;
	
			if (retorno == '000.000.000-00' && tamanho1 == 1)
				return true;
	
			if (retorno.length >= 14)
			{
				if (!validaCPF(retorno) || retorno == '00000000000000')
				{
					alerta(obj.value + "\n" + "CPF inválido.");
					obj.value = "";
					obj.focus();
	
					return false;
				}
			}
	
			break;
		}
	case 'cgc':
	{  // 99.999.999/9999-99
		obj.maxLength=18;
		retorno = limpaParaMascara(obj.value,'numeros');
		if (retorno.length >= 2)  { retorno = retorno.substr(0,2)+"."+retorno.substr(2); }
		if (retorno.length >= 6)  { retorno = retorno.substr(0,6)+"."+retorno.substr(6); }
		if (retorno.length >= 10) { retorno = retorno.substr(0,10)+"/"+retorno.substr(10); }
		if (retorno.length >= 15) { retorno = retorno.substr(0,15)+"-"+retorno.substr(15); }
		obj.value = retorno.substr(0,18);
		if (retorno.length >= 18) {
			if (!validaCGC(retorno)) {
				alerta(obj.value+"\n"+"CNPJ inválido.");
				obj.value="";
				obj.focus();
				return;
			}
		}
		break;
	}
	case 'ramal': {  // 9999
		obj.maxLength=4;
		retorno = limpaParaMascara(obj.value,'numeros');
		obj.value = retorno.substr(0,4);
		break;  }
	case 'DD/MM/AA': {  // 99/99/99
		obj.maxLength=8;
		retorno = limpaParaMascara(obj.value,'numeros');
		if (retorno.length >= 2) { retorno = retorno.substr(0,2)+"/"+retorno.substr(2); }
		if (retorno.length >= 5) { retorno = retorno.substr(0,5)+"/"+retorno.substr(5); }
		obj.value = retorno.substr(0,8);
		if (retorno.length >= 8) {
			dataEmTeste = retorno.substr(0,6)+'20'+retorno.substr(6,2) ;
			if (!retornaValidaData(dataEmTeste)) {
				obj.value="";
				obj.focus();
				return;
			}
		}
		break;  }
	case 'DD/MM/AAAA': {  // 99/99/9999
		obj.maxLength=10;
		retorno = limpaParaMascara(obj.value,'numeros');
		if (retorno.length >= 2) { retorno = retorno.substr(0,2)+"/"+retorno.substr(2); }
		if (retorno.length >= 5) { retorno = retorno.substr(0,5)+"/"+retorno.substr(5); }
		obj.value = retorno.substr(0,10);
		if (retorno.length >= 10) {
			if (!retornaValidaData(obj.value,tamanho1)) {
				obj.value="";
				obj.focus();
				return;
			}
		}
		break;  }
	case 'MM/AAAA': {  // 99/9999
		obj.maxLength=7;
		retorno = limpaParaMascara(obj.value,'numeros');
		if (retorno.length >= 2) { retorno = retorno.substr(0,2)+"/"+retorno.substr(2); }
		obj.value = retorno.substr(0,7);
		if (retorno.length >= 7) {
			dataEmTeste = '01/'+retorno
			if (!retornaValidaData(dataEmTeste)) {
				obj.value="";
				obj.focus();
				return;
			}
		}
		break;  }
	case 'numero': {
		if(tamanho1 != 0){
			obj.maxLength = tamanho1;
		}
		retorno = limpaParaMascara(obj.value,'numeros');
		obj.value = retorno.substr(0,obj.maxLength);
		break;  }
	case 'inteiro': {
		if(tamanho1 != 0){
			obj.maxLength = tamanho1;
		}
		retorno = limpaZerosAEsquerda(limpaParaMascara(obj.value,'numeros'));
		obj.value = retorno.substr(0,obj.maxLength);
		break;  }
	case 'valor': {
		retorno = obj.value;
		if (tamanho1+tamanho2 >0) {
			obj.maxLength = tamanho1 + 1 + tamanho2 + Math.floor(tamanho1/3);
		}
		var isNeg = false;
		if (retorno.charAt(0) == '-') {
			isNeg = true;
			retorno = retorno.substring(1);
			obj.maxLength++;
		}
		retorno = limpaParaMascara(retorno,'valores');
		var posPrimVirgula = retorno.indexOf(",");
		retorno = limpaParaMascara(retorno,'numeros');
		if (posPrimVirgula > 0) {
			valorInteiro = retorno.substr(0,posPrimVirgula);
			valorCentavo = retorno.substring(posPrimVirgula);
			if (retorno.charAt(0) == '0') {
				retorno = "0,"+valorCentavo.substr(0,tamanho2);
			} else {
				valorInteiro = retornaFormatoMonetarioInteiro(valorInteiro);
				valorCentavo = valorCentavo.substr(0,tamanho2);
				retorno = valorInteiro+","+valorCentavo;
			}
		} else {
			retorno = retorno.substr(0,tamanho1);
			retorno = retornaFormatoMonetarioInteiro(retorno);
		}
		if (retorno == "" && (event.keyCode == 48 || event.keyCode == 96)) { retorno = '0'; }
		if (isNeg) { retorno = "-"+retorno; }
		obj.value = retorno;
		break;  }
	case 'percentual': {  // 999
		obj.maxLength=3;
		retorno = limpaParaMascara(obj.value,'numeros');
		obj.value = retorno.substr(0,3);
		/*          obj.maxLength=6;
            retorno = limpaParaMascara(obj.value,'valores');
            if (retorno.length >= 3)  {
                retorno = retorno.substr(0,3)+","+retorno.substr(3); }
            posicaoPrimeiraVirgula = retorno.indexOf(",");
            retorno = limpaParaMascara(retorno,'numeros');
            if (posicaoPrimeiraVirgula > -1) {
                retorno = retorno.substr(0,posicaoPrimeiraVirgula)+","+retorno.substr(posicaoPrimeiraVirgula,2);
            };
            obj.value = retorno ;
		 */
		break;  }
	case 'cartao': {  // 9999 9999 9999 9999
		obj.maxLength=19;
		retorno = limpaParaMascara(obj.value,'numeros');
		if (retorno.length >= 4) { retorno = retorno.substr(0,4)+" "+retorno.substr(4); }
		if (retorno.length >= 9) { retorno = retorno.substr(0,9)+" "+retorno.substr(9); }
		if (retorno.length >= 14) { retorno = retorno.substr(0,14)+" "+retorno.substr(14); }
		obj.value = retorno.substr(0,19);
		if (obj.value.length == 19) {
			if (!validaCartao(obj.value)) {
				alerta(obj.value+"\nNúmero do Cartão inválido")
				obj.value = "";
				obj.focus();
				return;
			}
		}
		break;  }
	case 'cc': {  //  9999-99999-99
		obj.maxLength=13;
		retorno = limpaParaMascara(obj.value,"numeros");
		if (retorno.length >= 4) { retorno = retorno.substr(0,4) + "-" + retorno.substr(4); }
		if (retorno.length >= 10) { retorno = retorno.substr(0,10) + "-" + retorno.substr(10); }
		obj.value = retorno.substr(0,13);
		if (obj.value.length == 13) {
			if (!isContaCorrente(obj.value)) {
				alerta(obj.value+"\nConta corrente inválida")
				obj.value = "";
				obj.focus();
				return;
			}
		}
		break;  }
	case 'HH:MM': { // 12:00
		obj.maxLength=5;
		retorno = limpaParaMascara(obj.value,"numeros");
		if ( retorno.substr(0,1) > 2 ) { retorno = ''; }
		if ( retorno.substr(0,2) > 23 ) { retorno = retorno.substr(0,1); }
		if ( retorno.substr(2,1) > 5 ) { retorno = retorno.substr(0,2); }
		if (retorno.length >= 2) { retorno = retorno.substr(0,2) + ":" + retorno.substr(2); }
		obj.value = retorno.substr(0,5);
		break; }
	}
}