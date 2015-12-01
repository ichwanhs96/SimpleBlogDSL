<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="description" content="Deskripsi Blog">
<meta name="author" content="Judul Blog">

<!-- Twitter Card -->
<meta name="twitter:card" content="summary">
<meta name="twitter:site" content="omfgitsasalmon">
<meta name="twitter:title" content="Simple Blog">
<meta name="twitter:description" content="Deskripsi Blog">
<meta name="twitter:creator" content="Simple Blog">
<meta name="twitter:image:src" content="{{! TODO: ADD GRAVATAR URL HERE }}">

<meta property="og:type" content="article">
<meta property="og:title" content="Simple Blog">
<meta property="og:description" content="Deskripsi Blog">
<meta property="og:image" content="{{! TODO: ADD GRAVATAR URL HERE }}">
<meta property="og:site_name" content="Simple Blog">

<link rel="stylesheet" type="text/css" href="assets/css/screen.css" />
<link rel="shortcut icon" type="image/x-icon" href="img/favicon.ico">

<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<title>Simple Blog | Apa itu Simple Blog?</title>


</head>

<?php
    include ('connectDB.php');
    $id_post = $_GET['id'];
    $query = mysql_query("SELECT * FROM post WHERE id = '$id_post'") or die(mysql_error());
    $row = mysql_fetch_assoc($query);   // untuk hitung berapa banyak post yang ada di database
    $id_post = $row['id'];
    $judul = $row['judul'];
    $tanggal = $row['tanggal'];
    $konten = $row['konten'];
    mysql_close();
?>

<body class="default">
<div class="wrapper">

<nav class="nav">
    <a style="border:none;" id="logo" href="index.php"><h1>Simple<span>-</span>Blog</h1></a>
    <ul class="nav-primary">
        <li><a href="new_post.php">+ Tambah Post</a></li>
    </ul>
</nav>

<article class="art simple post">

    <header class="art-header">
        <div class="art-header-inner" style="margin-top: 0px; opacity: 1;">
            <time class="art-time">15 Juli 2014</time>
            <h2 class="art-title"><?php echo $judul ?></h2>
            <p class="art-subtitle"></p>
        </div>
    </header>

    <div class="art-body">
        <div class="art-body-inner">
            <hr class="featured-article" />
            <p><?php echo $konten ?></p>

            <hr />
            <div id="comment">
                <h2>Komentar</h2>

                <div id="contact-area">
                    <form method="POST" id="FormKomentar" action="#" onsubmit="return false">
                        <label for="Nama">Nama:</label>
                        <input type="text" name="Nama" id="Nama">

                        <label for="Email">Email:</label>
                        <input type="text" name="Email" id="Email">

                        <label for="Komentar">Komentar:</label><br>
                        <textarea name="Komentar" rows="20" cols="20" id="Komentar"></textarea>

                        <input type="submit" name="submit" value="Kirim" class="submit-button" onclick="Comment(<?php echo $id_post ?>)">
                    </form>
                    <span id="errormsg"></span>
                </div>
            </div>
            <ul class="art-list-body" id="ListKomentar"></ul>
        </div>
    </div>

</article>

</div>

<script type="text/javascript" src="assets/js/fittext.js"></script>
<script type="text/javascript" src="assets/js/app.js"></script>
<script type="text/javascript" src="assets/js/respond.min.js"></script>
<script type="text/javascript" src="assets/js/ajax.js"></script>
<script type="text/javascript">
  var ga_ua = '{{! TODO: ADD GOOGLE ANALYTICS UA HERE }}';
  (function(g,h,o,s,t,z){g.GoogleAnalyticsObject=s;g[s]||(g[s]=
      function(){(g[s].q=g[s].q||[]).push(arguments)});g[s].s=+new Date;
      t=h.createElement(o);z=h.getElementsByTagName(o)[0];
      t.src='//www.google-analytics.com/analytics.js';
      z.parentNode.insertBefore(t,z)}(window,document,'script','ga'));
      ga('create',ga_ua);ga('send','pageview');
</script>

</body>
</html>