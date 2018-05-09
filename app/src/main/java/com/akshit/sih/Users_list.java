package com.akshit.sih;

/**
 * Created by AkshayeJH on 15/12/17.
 */

public class Users_list {

    public String name, status, username;

    public Users_list(){

    }
    public String getUsername()
    {return username;}
    public void setUsername()
    {
        this.username=username;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users_list(String name,  String status,String username) {
        this.name = name;
        this.username=username;
        this.status = status;
    }
}
