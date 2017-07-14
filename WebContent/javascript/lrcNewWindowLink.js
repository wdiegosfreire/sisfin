function newWindowLink(url, altura, largura)
{
	var topo = (screen.width / 2) - (largura / 2);
	var esqd = (screen.height / 2) - (altura / 2);
	var janela = window.open(url, '', 'toolbar=no, location=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=' + largura + ', height=' + altura + ', top=' + topo + ', left=' + esqd);
}