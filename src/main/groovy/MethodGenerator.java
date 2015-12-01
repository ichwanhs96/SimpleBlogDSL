import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.List;

/**
 * Created by Ichwan Haryo Sembodo on 27/11/2015.
 */
public class MethodGenerator {

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    private final String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://";
    private String sql_url;
    private String db_url;
    private String username;
    private String password;
    private String database;

    private String author_name;
    private String author_desc;
    private String avatar_url;

    private static String path = null;
    private static String homefolder = null;
    private Post p;

    //constructor
    public MethodGenerator(){
        if(path == null){
            try {
                File f = new File(SimpleBlogDSL.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                path = f.getParent();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void createSQLDatabase(){
        try{
            // create java mysql database connection
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();

            // create mysql database query
            String query = ("CREATE DATABASE "+database);

            // execute preparedStatement
            statement.executeUpdate(query);

            //set db url biar bisa diakses
            db_url = url+database;

        } catch (SQLException se){
            se.printStackTrace();
        } catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void createSQLTable(){
        try{
            // create java mysql database connection
            Class.forName(driver);
            connection = DriverManager.getConnection(db_url, username, password);

            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();

            // buat table post di database
            String query = ("CREATE TABLE post " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    "judul VARCHAR (255)," +
                    "konten VARCHAR (255), " +
                    "tanggal DATE, " +
                    "PRIMARY KEY (id))");

            // execute
            statement.executeUpdate(query);

            //buat tabel komentar di database
            query = ("CREATE TABLE komentar" +
                    "(id INTEGER NOT NULL AUTO_INCREMENT," +
                    "nama VARCHAR (255)," +
                    "email VARCHAR (255)," +
                    "tanggal DATE," +
                    "komentar VARCHAR (255)," +
                    "FOREIGN KEY (id) REFERENCES post(id))");
            //execute
            statement.executeUpdate(query);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void closeConnection(){
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    //setter getter section
    public void setUsername(String username){ this.username = username; }

    public void setUrl(String url){
        this.url += url + '/';
    }
    public void setSql_url(String sql_url){
        this.sql_url = sql_url;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setAuthor_name(String author_name) { this.author_name = author_name; }
    public void setAuthor_desc(String author_desc) { this.author_desc = author_desc; }
    public void setAvatar_url(String avatar_url) { this.avatar_url = avatar_url; }

    public void setHomeFolder(String p){this.homefolder = p;}

    public void setPath(String path){this.path = path;}

    public String getPath(){ return this.path; }

    public String getHomeFolder(){return homefolder;}

    public void setPost(Post p){ this.p=p; }

    /////////////////////////////////

    public void copyDir(String srcPath, String destPath) {
        File source = new File(srcPath);
        File dest = new File(destPath);
        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFile(String srcPath, String destPath){
        File source = new File(srcPath);
        File dest = new File(destPath);
        try {
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //change placeholder in file for SQL config
    public void generateTemplatesSQL(){
        try {
            String templateLocation = homefolder + "templates";
            String outputLocation = homefolder + "output/";

            //ubah string %20 jadi spasi biasa
            if(templateLocation.contains("%20")){
                templateLocation = templateLocation.replace("%20", " ");
            } if (outputLocation.contains("%20")){
                outputLocation = outputLocation.replace("%20", " ");
            }

            File actual = new File(templateLocation);
            //Collection<File> files = new ArrayList<>();
            for( File f : actual.listFiles()){
                BufferedReader br = null;
                BufferedWriter bw = null;
                try {
                    File of = new File(outputLocation+f.getName());
                    if(!of.exists()){
                        of.createNewFile();
                    }
                    br = new BufferedReader(new FileReader(f));
                    bw = new BufferedWriter(new FileWriter(of));
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.contains("PLACEHOLDER_URL")){
                            line = line.replace("PLACEHOLDER_URL",sql_url);
                        }
                        if (line.contains("PLACEHOLDER_USERNAME")){
                            line = line.replace("PLACEHOLDER_USERNAME",username);
                        }
                        if (line.contains("PLACEHOLDER_PASSWORD")){
                            line = line.replace("PLACEHOLDER_PASSWORD",password);
                        }
                        if (line.contains("PLACEHOLDER_DATABASE")){
                            line = line.replace("PLACEHOLDER_DATABASE",database);
                        }
                        bw.write(line+"\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if(br != null)
                            br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if(bw != null)
                            bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //change placeholder in file for aboutme config
    public void generateTemplatesAboutme(){
        try {
            String templateLocation = homefolder + "templates_aboutme";
            String outputLocation = homefolder + "output/";

            //ubah string %20 jadi spasi biasa
            if(templateLocation.contains("%20")){
                templateLocation = templateLocation.replace("%20", " ");
            } if (outputLocation.contains("%20")){
                outputLocation = outputLocation.replace("%20", " ");
            }

            File actual = new File(templateLocation);
            //Collection<File> files = new ArrayList<>();
            for( File f : actual.listFiles()){
                BufferedReader br = null;
                BufferedWriter bw = null;
                try {
                    File of = new File(outputLocation+f.getName());
                    if(!of.exists()){
                        of.createNewFile();
                    }
                    br = new BufferedReader(new FileReader(f));
                    bw = new BufferedWriter(new FileWriter(of));
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.contains("PLACEHOLDER_AUTHOR_NAME")){
                            line = line.replace("PLACEHOLDER_AUTHOR_NAME", author_name);
                        }
                        if (line.contains("PLACEHOLDER_AUTHOR_DESC")){
                            line = line.replace("PLACEHOLDER_AUTHOR_DESC", author_desc);
                        }
                        if (line.contains("PLACEHOLDER_AVATAR_URL")){
                            line = line.replace("PLACEHOLDER_AVATAR_URL", new File(avatar_url).getName());
                        }
                        bw.write(line+"\n");
                    }
                    File imageOutput = new File(outputLocation + new File(avatar_url).getName());
                    copyFile(avatar_url, imageOutput.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if(br != null)
                            br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if(bw != null)
                            bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generatorAssets(){
        try {
            File path = new File(SimpleBlogDSL.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            String srcPath = path.getParent() + "/assets";
            String destPath = homefolder + "/output/assets";
            copyDir(srcPath,destPath);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void create(){
        File dir = new File(path);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if(child.isFile()) copyFile(child.getAbsolutePath(),homefolder+child.getName());
                else if(child.isDirectory())copyDir(child.getAbsolutePath(),homefolder+child.getName());
                else System.out.println(child.getName()+" Not a file nor a directory");
            }
            System.out.println("project "+homefolder+" created!!!!");
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
            System.out.println("Something went wrong!!!");
        }
    }

    public void settingUpPost(){
        //Set up update
        try{
            File input;
            Document doc;
            Element element;
            String pathTemplates = homefolder+"output/";
            input=new File(pathTemplates+"index.php");
            doc = Jsoup.parse(input, "UTF-8");
            BufferedWriter htmlWriter;

            //Set up update
            File editPost=new File(pathTemplates+"edit_post.php");
            if(p.getUpdate().equals("true")){
                element=doc.getElementById("edit_delete");
                if(element.getElementById("editlink")==null)
                    element.prepend("<a id=\"editlink\" style=\"margin-right:20px\" id=\"editlink\" href=\"edit_post.php?id=<?php echo $f0 ?>\">Edit</a>");
                if(!editPost.exists())copyFile(path+"/templates/edit_post.php",pathTemplates+"edit_post.php");
            }else if(p.getUpdate().equals("false")){
                element=doc.getElementById("editlink");
                if(element!=null)
                    element.remove();
                if(editPost.exists()) editPost.delete();
            }else System.out.println("Value update invalid");


            //Set up delete
            if(p.getDelete().equals("true")){
                element=doc.getElementById("edit_delete");
                if(element.getElementById("deletelink")==null)
                    element.append("<a id=\"deletelink\" href=\"javascript:DeletePost(<?php echo $f0 ?>)\">Hapus</a> </p> </li>");
            }else if(p.getDelete().equals("false")){
                element=doc.getElementById("deletelink");
                if(element!=null) element.remove();
            }else{
                System.out.println("Value delete invalid");
            }

            //Set up sort_by
            element=doc.getElementById("query");
            if(p.getSort_by().equals("date")){
                String temp=element.html();
                int idx=temp.indexOf("$query=");
                if(idx!=-1){
                    temp=temp.replace("`judul` ASC","`tanggal` DESC");
                }else{
                    System.out.println("query= not found");
                }
                element.html(temp);
            }else if(p.getSort_by().equals("name")){
                String temp=element.html();
                int idx=temp.indexOf("$query=");
                if(idx!=-1){
                    temp=temp.replace("`tanggal` DESC","`judul` ASC");

                }else{
                    System.out.println("query= not found");
                }
                element.html(temp);
            }else{
                System.out.println("Value sort_by invalid");
            }

            String document=doc.html();
            document=document.replace("<!--?php","<?php");
            document=document.replace("?-->","?>");

            htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(input), "UTF-8"));
            if(htmlWriter!=null){
                htmlWriter.write(document);
                htmlWriter.close();
            }

            //Set up comment
            input=new File(pathTemplates+"post.php");
            doc= Jsoup.parse(input, "UTF-8");
            element=doc.getElementById("comment");
            if(p.getComment().equals("true")){
                if(!element.hasText()){
                    element.append("                <h2>Komentar</h2>\n" +
                            "\n" +
                            "                <div id=\"contact-area\">\n" +
                            "                    <form method=\"POST\" id=\"FormKomentar\" action=\"#\" onsubmit=\"return false\">\n" +
                            "                        <label for=\"Nama\">Nama:</label>\n" +
                            "                        <input type=\"text\" name=\"Nama\" id=\"Nama\">\n" +
                            "            \n" +
                            "                        <label for=\"Email\">Email:</label>\n" +
                            "                        <input type=\"text\" name=\"Email\" id=\"Email\">\n" +
                            "                        \n" +
                            "                        <label for=\"Komentar\">Komentar:</label><br>\n" +
                            "                        <textarea name=\"Komentar\" rows=\"20\" cols=\"20\" id=\"Komentar\"></textarea>\n" +
                            "\n" +
                            "                        <input type=\"submit\" name=\"submit\" value=\"Kirim\" class=\"submit-button\" onclick=\"Comment(<?php echo $id_post ?>)\">\n" +
                            "                    </form>\n" +
                            "                    <span id=\"errormsg\"></span>\n" +
                            "                </div>\n" +
                            "\n" +
                            "                <ul class=\"art-list-body\" id=\"ListKomentar\">\n" +
                            "                </ul>");
                }
            }else if(p.getComment().equals("false")){
                if(element!=null){
                    List<Element> list=element.getAllElements();
                    for(Element e:list) e.remove();
                }
            }else System.out.println("Value comment invalid");

            document=doc.html();
            document=document.replace("<!--?php", "<?php");
            document=document.replace("?-->", "?>");

            htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(input), "UTF-8"));
            if(htmlWriter!=null){
                htmlWriter.write(document);
                htmlWriter.close();
            }

        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

}
