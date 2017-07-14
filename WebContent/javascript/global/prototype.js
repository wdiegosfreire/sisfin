/**
 * Adiciona o método <code>format</code> como uma função nativa da classe Date. Tem a finalidade de formatar
 * o valor de um objeto Date para uma <code>String</code> formatada de acordo com a máscara fornecida.
 * Caso a máscara não seja informada o valor padrão será a data no formato brasileiro </code>dd/MM/yyyy<code>.
 * 
 * @param mask
 * @returns {String}
 */
Date.prototype.toFormat = function(datePattern)
{
	if (datePattern == undefined || datePattern.mask == undefined)
	{
		alert("Formato inválido");
		datePattern.mask;
		return false;
	}

	var day = (this.getDate() < 10 ? "0" + this.getDate() : this.getDate());
	var month = (this.getMonth() + 1 < 10 ? "0" + (this.getMonth() + 1) : this.getMonth() + 1);
	var year = this.getFullYear();
	var hour = (this.getHours() < 10 ? "0" + this.getHours() : this.getHours());
	var minutes = (this.getMinutes() < 10 ? "0" + this.getMinutes() : this.getMinutes());
	var seconds = (this.getSeconds() < 10 ? "0" + this.getSeconds() : this.getSeconds());

	if (datePattern == datePatternEnum.ANO)
		return (year);
	else if (datePattern == datePatternEnum.MES_ANO)
		return (month + "/" + year);
	else if (datePattern == datePatternEnum.DIA_MES_ANO)
		return (day + "/" + month + "/" + year);
	else if (datePattern == datePatternEnum.HOR_MIN)
		return (hour + ":" + minutes);
	else if (datePattern == datePatternEnum.HOR_MIN_SEG)
		return (hour + ":" + minutes + ":" + seconds);
	else if (datePattern == datePatternEnum.DIA_MES_ANO_HOR_MIN)
		return (day + "/" + month + "/" + year + " " + hour + ":" + minutes);
	else if (datePattern == datePatternEnum.DIA_MES_ANO_HOR_MIN_SEG)
		return (day + "/" + month + "/" + year + " " + hour + ":" + minutes + ":" + seconds);
};

this.datePatternEnum = {
 	ANO: {mask: "yyyy"},
	MES_ANO: {mask: "MM/yyyy"},
	DIA_MES_ANO: {mask: "dd/MM/yyyy"},
	HOR_MIN: {mask: "HH:mm"},
	HOR_MIN_SEG: {mask: "HH:mm:ss"},
	DIA_MES_ANO_HOR_MIN :{mask: "dd/MM/yyyy HH:mm"},
	DIA_MES_ANO_HOR_MIN_SEG: {mask: "dd/MM/yyyy HH:mm:ss"}
};

String.prototype.toDate = function(datePattern)
{
	var date = null;

	if (datePattern == undefined || datePattern.mask == undefined)
	{
		alert("Formato inválido");
		return false;
	}
	
	if (datePattern == datePatternEnum.DIA_MES_ANO)
	{
		var splitedDate = this.split("/");
		date = new Date(parseInt(splitedDate[2]), parseInt(splitedDate[1]) - 1, parseInt(splitedDate[0]));
	}

	return date;
};

var app = new App();
function App()
{
	this.isMobile = function()
	{
		var md = new MobileDetect(window.navigator.userAgent);

		return (md.mobile() == null ? false : true);
	};

	this.visibleWidth = function()
	{
		return window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth || 0;
	};

	this.visibleHeight = function()
	{
		return window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight || 0;
	};
};