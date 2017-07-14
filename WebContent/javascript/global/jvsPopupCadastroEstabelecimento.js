function showPopupCadastroEstabelecimento() 
{
	var html = "";

	html += "<span>Testando o PrimeUI Dialog Box!!!</span>";

	$("#dialogBox").html(html);

	$("#dialogBox").puidialog({
		showEffect: "fade",
		hideEffect: "fade",
		modal: true,
		resizable: false
	});

	$("#dialogBox").puidialog("show");
}