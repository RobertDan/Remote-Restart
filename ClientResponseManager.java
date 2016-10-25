package com.company;

/**
 * Created by Kickass on 10/11/2016.
 */
public class ClientResponseManager {
    private UserInfo ui;

    public ClientResponseManager(){}
    public ClientResponseManager(UserInfo userinfo){ui = userinfo;}

    //Pass the four things needed to converse with the server (username, password, program, and action) as a UserInfo object.
    public void setUserInfo(UserInfo userinfo) {ui = userinfo;}

    public void printError(int step)
    {
        switch(step) {
            case 1: System.out.println("Unknown Error! Aborting connection."); break;
            case 2: System.out.println("Error: Invalid Password. Aborting connection."); break;
            case 3: System.out.println("Error: Username not found. Aborting connection."); break;
            case 4: System.out.println("Error: Server was given null value. Aborting connection."); break;
            default: break;
        }
    }

    public String getResponse(String arg)
    {
        //client's logic for responding
        switch(arg) {
            case "Are you ready?": return "Yes";
            case "Username?": return "Username:" + ui.getUsername();
            case "Password?": return "Password:" + ui.getPassword();
            case "Program?": return "Program:" + ui.getProgram();
            case "Action?": return "Action:" + ui.getAction();
            case "Okay": return "End";
            case "Error: Goto 9": printError(1); return "End";
            case "Error: Invalid Password": printError(2); return "End";
            case "Error: User not found.": printError(3); return "End";
            case "Error: Sent null value.": printError(4); return "End";
            case "End": return "End";
            default: System.out.println("[[" + arg + "]]"); printError(1); return "End";
        }
    }
}
