function Comment (id)
{
	var nama = document.getElementById('Nama').value;
	var email = document.getElementById('Email').value;
	var komentar = document.getElementById('Komentar').value;

	if (ValidateComment())  // Jika komentar sudah memenuhi syarat
	{
		var xmlhttp;
		if (window.XMLHttpRequest)
		{
			xmlhttp = new XMLHttpRequest();	// Kode untuk IE7+, Firefox, Chrome, Opera, Safari
		}
		else
		{
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");	// Kode untuk IE5, IE6
		}
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				document.getElementById('ListKomentar').innerHTML = xmlhttp.responseText;
			}
		} 
		xmlhttp.open("GET","insert_comment.php?id="+id+"&nama="+nama+"&komentar="+komentar+"&email="+email,true)
		xmlhttp.send();
		document.getElementById('FormKomentar').reset();
	}
	else
	{
		return false;
	}
}

function ValidateComment()
{
	var nama = document.getElementById("Nama").value;
	var email = document.getElementById("Email").value;
	var komentar = document.getElementById("Komentar").value;
	var error_msg = document.getElementById("errormsg");
	
	// Pengecekkan form komentar
	if ((nama != "") && (email != "") && (komentar != ""))
	{
		if (ValidateEmail(email)){
			error_msg.innerHTML="";
			return true;
		}
		else	// email tidak valid
		{
			error_msg.innerHTML="Email yang Anda masukkan tidak valid";
			return false;
		}
	}
	else
	{
		error_msg.innerHTML="Semua field harus terisi";
		return false;
	}
}

function ValidateEmail(email)
{
	var pattern = /^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]+)$/g;
	var result = pattern.test(email);
	if(result)
	{
		return true;
	}
	else
	{
		return false;
	}
}

function Show_Comment(id)
{
	var xmlhttp;
	if (window.XMLHttpRequest)
	{
		xmlhttp = new XMLHttpRequest();	// Kode untuk IE7+, Firefox, Chrome, Opera, Safari
	}
	else
	{
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");	// Kode untuk IE5, IE6
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById('ListKomentar').innerHTML = xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET","show_comment.php?id="+id,true);
	xmlhttp.send();
}

/*
window.onload=function()
{
	//ValidateForm();
	Comment(id);
}
*/