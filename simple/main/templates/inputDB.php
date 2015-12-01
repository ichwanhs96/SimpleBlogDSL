<?php
    include ('connectDB.php');

    // Ambil dari form
    $judul = $_POST['Judul'];
    $tanggal = $_POST['Tanggal'];
    $konten = $_POST['Konten'];
    
    //simpan data ke database
    $query = mysql_query("insert into post (judul, konten, tanggal) values('$judul', '$konten', '$tanggal')") or die(mysql_error());

    if($query)
    {
        header('location:index.php');
    }

    // close DB
    mysql_close();
?>