package com.example.whatsapplite.Users;

public class Users {
    String profilepic ,email,password,userid,username ;

    public Users(String email, String password, String username, String userid, String profilepic) {
        this.profilepic = profilepic;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.username = username;
    }
  public Users(){}


// constructor for signup

    public Users(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }



    public String getUsername() {
        return username;
    }
}
