<?php
	include ('connectDB.php');

	$id = $_GET['id'];

	$query2 = "SELECT * FROM `komentar` WHERE id=$id";
	$result2 = mysql_query($query2) or die(mysql_error());

	$nums = mysql_fetch_assoc($result2);
	$i = 0;

	while ($i<$nums)
	{
		echo "
		<li class='art-list-item'>
			<div class='art-list-item-title-and-time'>
				<h2 class='art-list-title'><a href='post.html'>".$result2['nama']."</a></h2>
				<div class='art-list-time'>2 menit lalu</div>
			</div>
			<p>".$result2['komentar']."</p>
		</li> "
		$i++;
	}

	mysql_close();
?>