import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Ichwan Haryo Sembodo on 27/11/2015.
 */
class SimpleBlogDSL {
    String url;
    String username;
    String password;
    String database;
    String author_name;
    String author_description;
    String avatar_url;

    //rama
    private static String smatcher="([A-Za-z-0-9._]+=\"[/\\sA-Za-z-0-9._=:]+\")|([A-Za-z-0-9._]+=[/A-Za-z-0-9._=]+)|([A-Za-z-0-9._/:]+)";
    private static ArrayList<String> regex;
    private static String homepath;

    def static make(closure){
        SimpleBlogDSL simpleBlogDSL = new SimpleBlogDSL()
        closure.delegate = simpleBlogDSL
        closure()
    }

    def author_name (String author_name) {
        this.author_name = author_name
    }

    def author_description (String author_description) {
        this.author_description = author_description
    }

    def avatar_url (String avatar_url) {
        this.avatar_url = avatar_url
    }

    def url(String url){
        this.url = url
    }

    def username(String username){
        this.username = username
    }

    def password(String password){
        this.password = password
    }

    def database(String database){
        this.database = database
    }

    def createHomeFolder(String path){
        doCreateHomeFolder(path);
    }

    def setProjectDir(String path){
        doSetProject(path);
    }

    //getter

    def getdir(){
        def generator=new MethodGenerator();
        System.out.println(generator.getHomeFolder());
    }

    def getPost(Post p){
        dopost(p)
    }

    def getSQL(){
        doSQL(this)
    }

    def getAboutme() {
        doaboutme(this)
    }

    def methodMissing(String methodName, args){
        println "Method Missing!"
    }

    private static doaboutme(SimpleBlogDSL simpleBlogDSL) {
        def generator = new MethodGenerator();
        generator.setAuthor_name(simpleBlogDSL.author_name)
        generator.setAuthor_desc(simpleBlogDSL.author_description)
        generator.setAvatar_url(simpleBlogDSL.avatar_url)
        generator.generateTemplatesAboutme()
        generator.generatorAssets()
    }

    private static doSQL(SimpleBlogDSL simpleBlogDSL){
        //logic
        //edit file?
        def generator = new MethodGenerator();
        generator.setUrl(simpleBlogDSL.url)
        generator.setSql_url(simpleBlogDSL.url)
        generator.setUsername(simpleBlogDSL.username)
        generator.setPassword(simpleBlogDSL.password)
        generator.setDatabase(simpleBlogDSL.database)
        generator.createSQLDatabase()
        generator.createSQLTable()
        generator.generateTemplatesSQL()
        generator.generatorAssets()
    }

    private static doSetProject(String path){
        System.out.println("Set to "+path);
        def generator=new MethodGenerator();
        generator.setHomeFolder(path);
    }
    private static dopost(Post p){
        def generator=new MethodGenerator();
        generator.setPost(p);
        generator.settingUpPost();
    }

    private static doCreateHomeFolder(String path){
        def generator=new MethodGenerator();
        generator.setHomeFolder(path);
        generator.create();
    }

    public static void main(String[] args){
        /*
        println "choose your grammar (SQL / aboutme) = "
        def grammar = System.in.newReader().readLine()
        if(grammar.equals("SQL")){
            println "input your MySQL url = "
            def input_url = System.in.newReader().readLine()
            println "input your username for MySQL = "
            def input_username = System.in.newReader().readLine()
            println "input your password for MySQL = "
            def input_password = System.in.newReader().readLine()
            println "input your desire database name = "
            def input_database = System.in.newReader().readLine()
            SimpleBlogDSL.make{
                url input_url
                username input_username
                password input_password
                database input_database
                SQL
            }
        } else if(grammar.equals("aboutme")) {
            println "input your name = "
            def input_author_name = System.in.newReader().readLine()
            println "input your link profile photo = "
            def input_avatar_url = System.in.newReader().readLine()
            println "input your description = "
            def input_author_description = System.in.newReader().readLine()
            //logic here
            SimpleBlogDSL.make{
                author_name input_author_name
                avatar_url input_avatar_url
                author_description input_author_description
                aboutme
            }
        }
        */
        regex = new ArrayList<>();
        Pattern pattern = Pattern.compile(smatcher);
        String command;
        Command cmd = new Command();
        while(true){
            System.out.print(">>");
            Scanner scan=new Scanner(System.in);
            command=scan.nextLine();
            Matcher matcher=pattern.matcher(command);
            while (matcher.find()) {
                regex.add(matcher.group());
            }
            if(regex.size()>=2) {
                if (regex.get(0).equals("simpleblog")) {
                    //Find command
                    String temp = regex.get(1);
                    for(int i=2;i<regex.size();i++){
                        String regex=regex.get(i);
                        if(regex.contains("=")){
                            String key=regex.substring(0,regex.indexOf('='));
                            String value=regex.substring(regex.indexOf('=')+1,regex.length()).replace('"','');
                            cmd.getKeyvalue().put(key,value);
                        }
                    }
                    switch (temp){
                        case "create":
                            homepath=regex.get(2);
                            if(homepath.charAt(homepath.length()-1)!='/') homepath+='/';
                            System.out.println(homepath);
                            SimpleBlogDSL.make{
                                createHomeFolder homepath
                            }
                            break;
                        case "switch_project":
                            homepath=regex.get(2);
                            if(homepath.charAt(homepath.length()-1)!='/') homepath+='/';
                            SimpleBlogDSL.make{
                                setProjectDir homepath
                            }
                            break;
                        case "get_project_dir":
                            SimpleBlogDSL.make{
                                dir
                            }
                            break;
                        case "post":
                            Post post=new Post();
                            post.setUpdate(cmd.getKeyvalue().get("update","true"));
                            post.setDelete(cmd.getKeyvalue().get("delete","true"));
                            post.setComment(cmd.getKeyvalue().get("comment","true"));
                            post.setSort_by(cmd.getKeyvalue().get("sort_by","date"));
                            SimpleBlogDSL.make{
                                getPost post
                            }
                            break;
                        case "setdb":
                            String input_url = cmd.getKeyvalue().get("url", "localhost");
                            String input_username = cmd.getKeyvalue().get("username", "root");
                            String input_password = cmd.getKeyvalue().get("password", "");
                            String input_database = cmd.getKeyvalue().get("database", "simpleblog");
                            SimpleBlogDSL.make{
                                url input_url
                                username input_username
                                password input_password
                                database input_database
                                SQL
                            }
                            break;
                        case "about_me":
                            String nama=cmd.getKeyvalue().get("author_name");
                            String gambar_url=cmd.getKeyvalue().get("avatar_url");
                            String desc=cmd.getKeyvalue().get("author_description");
                            SimpleBlogDSL.make{
                                author_name nama
                                avatar_url gambar_url
                                author_description desc
                                aboutme
                            }
                            break;
                        default:
                            System.out.println("command not found");
                            break;
                    }

                } else System.out.println("Command not found!!!");

            }else System.out.println("Command not found!!!");
            regex.clear();
        }
    }
}
