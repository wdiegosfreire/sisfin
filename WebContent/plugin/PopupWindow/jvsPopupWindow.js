if (navigator.appName == "Microsoft Internet Explorer")
	document.write("<link rel='stylesheet' type='text/css' href='plugin/PopupWindow/cssPopupWindow_IE.css' />");
else
	document.write("<link rel='stylesheet' type='text/css' href='plugin/PopupWindow/cssPopupWindow_FF.css' />");

function PopupWindow(place)
{
	this.popupPlace = document.getElementById(place);

	this.divShadow = null;
	this.divMain = null;

	this.divTitle = null;
	this.divTitleSpan = null;
	this.divTitleH3 = null;
	
	this.divBody = null;

	this.divButton = null;

	// Métodos
	this.getDivShadow = getDivShadow;
	this.getDivMain = getDivMain;
	this.getDivBody = getDivBody;
	this.popupBuilder = popupBuilder;
	this.setTitle = setTitle;
	this.setSize = setSize;
	this.setBodyContent = setBodyContent;
	this.getWindowId = getWindowId;
	this.show = show;

	this.createButton = createButton;
}

function getDivShadow() {return this.divShadow;}
function getDivMain() {return this.divMain;}
function getDivBody() {return this.divBody;}

function createButton(name, value, click)
{
	var button = document.createElement("input");
	button.setAttribute("name", name);
	button.setAttribute("value", value);
	button.setAttribute("type", "button");

	$(button).addClass("botao");
	$(button).click(function (){eval(click);});

	this.divMain.appendChild(button);
}

function popupBuilder(windowId)
{
	// Criando a div shadow
	this.divShadow = document.createElement("div");
	this.divShadow.setAttribute("id", "divShadow");
	
	$(this.divShadow).addClass("divShadow");

	this.divShadow.style.width = document.body.scrollWidth;
	this.divShadow.style.height = document.body.scrollHeight;

	// Criando a div principal
	this.divMain = document.createElement("div");
	this.divMain.setAttribute("id", "divMain");
	
	$(this.divMain).addClass("divMain");
	this.divMain.setAttribute("align", "center");

	// Criando a div título
	this.divTitle = document.createElement("div");
	this.divTitle.setAttribute("id", "idDivTitle");
	$(this.divTitle).addClass("divTitle");

	this.divTitleSpan = document.createElement("span");
	this.divTitleSpan.innerHTML = "&nbsp;";

	this.divTitleH3 = document.createElement("h3");
	this.divTitleH3.innerHTML = "&nbsp;";

	this.divTitle.appendChild(this.divTitleSpan);
	this.divTitle.appendChild(this.divTitleH3);

	// Criando a div body
	this.divBody = document.createElement("div");
	this.divBody.setAttribute("id", "idDivBody");
	this.divBody.setAttribute("align", "center");
	$(this.divBody).addClass("divBody");

	// Adicionando componentes a div principal
	this.divMain.appendChild(this.divTitle);
	this.divMain.appendChild(this.divBody);

	return this.divMain;
}

function setTitle(title)
{
	this.divTitleH3.innerHTML = title;
}

function setBodyContent(html)
{
	$(this.divTitleSpan).click(function (){eval("popupHide();");});

	this.divBody.innerHTML = html;
}

function setSize(width, height)
{
	this.divMain.style.width = width;

	if (height != null && height != "")
		this.divMain.style.height = height;
}

function getWindowId()
{
	return this.divMain.id;
}

function show()
{
	this.popupPlace.appendChild(this.getDivShadow());
	this.popupPlace.appendChild(this.getDivMain());

	$("#divShadow").cornerPosition();
	$("#divShadow").fadeIn("slow");

	$("#divMain").draggable();
	$("#divMain").center();
	$("#divMain").fadeIn("slow");
}

function popupHide()
{
	$("#divMain").fadeOut("slow", function()
	{
		$("#divMain").remove();
		$("#divShadow").hide("");
		$("#divShadow").remove();
	});
}