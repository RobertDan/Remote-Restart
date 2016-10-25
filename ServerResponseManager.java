package com.company;

import java.util.ArrayList;

/**
 * Created by Kickass on 10/11/2016.
 */
public class ServerResponseManager {
    //server variables
    private UserInfo lastSentUser;
    private ArrayList<String> users;
    private ArrayList<String> passes;
    private ArrayList<UserInfo> validUserInfo;


    public ServerResponseManager() {
        users = new ArrayList<String>();
        passes = new ArrayList<String>();
        validUserInfo = new ArrayList<UserInfo>();
    }

    //Stores UserInfo objects from config file. These are the valid credentials that the SRM will check against
    //when the client sends over info.
    public void addUserInfo(UserInfo ui) {
        validUserInfo.add(ui);
    }

    //Check if a Username that was submitted to the SRM is in the list of valid Usernames
    //No similar method exists for passwords- correct passwords w/o usernames should never be validated
    public boolean doesUserNameExist(String user) {
        for(int i=0; i<validUserInfo.size(); i++)
        {
            if(validUserInfo.get(i).getUsername().equals(user))
                return true;
        }
        return false;
    }

    //Check if a password that was submitted to the SRM is in the list of valid passwords.
    //Requires a username lookup as well, so requires a UserInfo object to be sent.
    public boolean authenticatePassword(UserInfo ui) {
        for(int i=0; i<validUserInfo.size(); i++)
            if(validUserInfo.get(i).getUsername().equals(ui.getUsername()) && validUserInfo.get(i).getPassword().equals(ui.getPassword()))
                return true;
        return false;
    }

    public void printError(int step) {
        switch (step) {
            case 1:
                System.out.println("Unknown Error! Aborting connection.");
                break;
            case 2:
                System.out.println("Error: Invalid Password. Aborting connection.");
                break;
            case 3:
                System.out.println("Error: Username not found. Aborting connection.");
                break;
            default:
                break;
        }
    }

    public String getResponse(String arg) {
        //server's logic for responding
        switch (arg) {
            case "Yes":
                return "Username?";
            case "End":
                return "End";
            default:
                if (arg == null || arg.substring(arg.length()-4).equals("null")) {
                    System.out.println("Server was sent null string. Aborting.");
                    return "Error: Sent null value";
                    //System.exit(-1);
                } else {
                    //check for username sent to server
                    if (arg.length() > 8 && arg.substring(0, 9).equals("Username:")) {
                        //check for valid username
                        if (doesUserNameExist(arg.substring(9))) {
                            //create new UserInfo object. Since the username is always sent first, the other
                            //checks will not have to create new objects, and will build on the existing one instead.
                            lastSentUser = new UserInfo();
                            lastSentUser.setUsername(arg.substring(9));
                            return "Password?";
                        } else {
                            //System.out.println("doesUserNameExist returned false");
                            return "Error: User not found.";
                        }

                    }
                    //check for password sent to server
                    else if (arg.length() > 8 && arg.substring(0, 9).equals("Password:")) {
                        //check for valid password
                        lastSentUser.setPassword(arg.substring(9));
                        if (authenticatePassword(lastSentUser)) {
                            return "Program?";
                        } else {
                            return "Error: Invalid Password";
                        }

                    }

                    //check for program sent to server
                    else if (arg.length() > 7 && arg.substring(0, 8).equals("Program:")) {
                        lastSentUser.setProgram(arg.substring(8));
                        return "Action?";
                    }

                    //check for action sent to server
                    else if (arg.length() > 6 && arg.substring(0, 7).equals("Action:")) {
                        lastSentUser.setAction(arg.substring(7));
                        return "Okay";
                    }
                }
        }
        //
        return "End";
    }
}
