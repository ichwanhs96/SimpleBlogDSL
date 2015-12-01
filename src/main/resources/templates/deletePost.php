<?php
	include ('connectDB.php');
   
	// Mengambil id value
	$id_post = $_GET['id'];

	// Query to display list post
	$query="DELETE from post where id = '$id_post'";
	mysql_query($query) or die(mysql_error());

	if($query)
	{
	   header('location:index.php');
	}
?>