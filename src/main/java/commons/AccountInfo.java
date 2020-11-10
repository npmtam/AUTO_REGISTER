package commons;

public class AccountInfo {

    private String userName;
    private String password;
    private String url;
    private String ipAddress;

    public AccountInfo(String userName, String password, String url, String ipAdress){
        super();
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.ipAddress = ipAdress;
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

    public String getIpAddress(){
        return ipAddress;
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

    public void setIpAddress(){
        this.ipAddress = ipAddress;
    }
}
