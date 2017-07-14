function jvsShowHideElement(objId, cell, imgAdd, imgRem)
{
	var img = "";

	var obj = document.getElementById(objId);

	if(obj.style.display == "")
	{
		obj.style.display = "none";
		cell.innerHTML = "<img src=\"images/" + imgAdd + "\" border=\"0\">";
	}
	else
	{
		obj.style.display = "";
		cell.innerHTML = "<img src=\"images/" + imgRem + "\" border=\"0\">";
	}
}