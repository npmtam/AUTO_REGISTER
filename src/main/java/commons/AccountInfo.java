package commons;

public class AccountInfo {

    private String userName;
    private String password;
    private String url;

    public AccountInfo(String userName, String password, String url){
        super();
        this.userName = userName;
        this.password = password;
        this.url = url;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public String getUrl(){
        return url;
    }

    public void setUserName(){
        this.userName = userName;
    }

    public void setPassword(){
        this.password = password;
    }

    public void setUrl(){
        this.url = url;
    }
}
