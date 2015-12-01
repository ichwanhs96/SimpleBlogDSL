<?php
    include ('connectDB.php');

    // Ambil dari form
    $id_post = $_GET['id'];
    $judul = $_POST['Judul'];
    $tanggal = $_POST['Tanggal'];
    $konten = $_POST['Konten'];
    
    //simpan data ke database
    $query1 = "UPDATE post SET judul = '".$judul."',tanggal = '".$tanggal."',konten = '".$konten."' where id = ".$id_post;
    $query = mysql_query($query1) or die(mysql_error());

    if($query)
    {
        header('location:index.php');
    }

    // close DB
    mysql_close();
?>