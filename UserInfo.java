package com.company;

/**
 * Created by Kickass on 10/11/2016.
 */
public class UserInfo {
    private String username;
    private String password;
    private String program;
    private String action;

    //constructors
    public UserInfo() {username = null; password = null; program = null; action = null;}
    public UserInfo(String user, String pass){username = user; password = pass; program = null; action = null;}
    public UserInfo(String user, String pass, String programString, String actionString){username = user; password = pass; program = programString; action = actionString;}

    //mutator methods
    public void setUsername(String user){username = user;}
    public void setPassword(String pass) {password = pass;}
    public void setProgram(String programString) {program = programString;}
    public void setAction(String actionString) {action = actionString;}

    //accessor methods
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getProgram(){return program;}
    public String getAction(){return action;}

    public boolean equals(UserInfo input)
    {
        if(input.getUsername().equals(username) && input.getPassword().equals(password) && input.getProgram().equals(program) && input.getAction().equals(action))
            return true;
        else
            return false;
    }

    public String toString(String delim)
    {
        return username + delim + password + delim + program + delim + action;
    }
}
