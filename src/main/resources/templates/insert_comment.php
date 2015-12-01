<?php
	include ('connectDB.php');

	$id = $_GET['id'];
	$nama = $_GET['nama'];
	$komentar = $_GET['komentar'];
	$email = $_GET['email'];

	$query = "INSERT INTO `komentar` (id, nama, email, komentar) VALUES ('{$id}', '{$nama}', '{$email}', '{$komentar}')";
	$result = mysql_query($query) or die(mysql_error());

	$query2 = "SELECT * FROM `komentar` WHERE id=$id";
	$result2 = mysql_query($query2) or die(mysql_error());

	$nums = mysql_fetch_assoc($result2);
	$i = 0;

	while ($i<$nums)
	{
		echo "
		<li class='art-list-item'>
			<div class='art-list-item-title-and-time'>
				<h2 class='art-list-title'><a href='post.html'>".$nama."</a></h2>
				<div class='art-list-time'>2 menit lalu</div>
			</div>
			<p>".$result2['komentar']."</p>
		</li> ";
		$i++;
	}

	mysql_close();
?>