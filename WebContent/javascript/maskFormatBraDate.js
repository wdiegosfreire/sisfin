function maskFormatBraDate(edit)
{
	if(event.keyCode < 48 || event.keyCode > 57)
		event.returnValue = false;

	if(edit.value.length >= 10)
		event.returnValue = false;

	if(edit.value.length == 2)
		edit.value += "/";
	else if(edit.value.length == 5)
		edit.value += "/";
}