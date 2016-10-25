package com.company;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kickass on 9/28/2016.
 */
public class ServerConfigReader {
    /*
    * setAuthenticationPassword(String pass)
    * authenticatePassword(String pass)
    *
    *
    *
    *
    *
    *
    *
     */
    //TODO: remember to make variables private
    public String password;
    public String configContents;
    public int timesConfigReloaded;
    public String debugFile;
    public String killProgram;
    public String startProgram;
    public TokenManip tm;
    public ArrayList<String> loadedContents;
    private boolean debugOn;

    public ServerConfigReader() throws IOException
    {
        debugOn = true;
        loadedContents = new ArrayList<String>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        debugFile = "Logs/" + dateFormat.format(new Date()) + " - Debug Log.txt";
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(debugFile), "utf-8"))) {
            writer.write("DEBUG: ===New ServerConfigReader created.===\n");
        }
        debug("Constructor called.");
        timesConfigReloaded = 0;
        configContents = null;

        debug("==Next task: load config file.");
        loadConfig();
        tm = new TokenManip(configContents,",");
        debug("=Task complete: load config file.");

        debug("==Next task: sorting config tokens.");
        loadConfigTokens();
        debug("=Task complete: sorting config tokens.");
    }

    //appends the passed String to the debug log
    public void debug(String debug) throws IOException
    {
        if(debugOn)
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(debugFile, true), "utf-8"))) {
                writer.write("DEBUG: " + debug + "\n");
            }
    }

    //reloads the config file in event of config file being modified during execution
    //works
    public void reloadConfig() throws IOException
    {
        debug("==Next task: Reload config file.");
        timesConfigReloaded++;
        loadConfig();
        debug("=Task complete: Reloading config file.");
    }

    //attempts to read and load the contents of the config file into configContents String
    //works
    public void loadConfig() throws IOException
    {
        debug("Attempting to load config file...");
        try(BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while(line!=null)
            {
                sb.append(line);
                sb.append(",");
                line = br.readLine();
            }

            configContents = sb.toString();
            debug("config file contents:" + configContents);
        }

        if(configContents==null)
            debug("ERROR: Problem reading config file, or config file blank!");
        else
            debug("Finished loading config file.");
    }

    //attempts to read the tokens from the config contents
    //works
    public void loadConfigTokens() throws IOException
    {
        debug("Attempting to load tokens...");
        TokenManip tm = new TokenManip(configContents, ",");
        try {

            //
            //load server ip
            //
            debug("Attempting to load Server IP...");
            debug("[" + tm.get(0).substring(0,9) + "] , [" + tm.get(0).substring(10) +"]");
            if(tm.get(0).substring(0,10).equals("Server IP:"))
            {
                loadedContents.add(tm.get(0).substring(10));
            }
            else
                throw new IllegalStateException();
            debug("Server IP loaded.");
            debug("Removing Server from TokenManip object.");
            tm.delete(tm.findSubstring("Server IP:"));

            //loop here to ensure that each username has a password, program, and action associated with it
            while(tm.findSubstring("Username")>0) {
                //
                //load username
                //
                String temp = new String();
                debug("Attempting to load username...");
                //throw exception if username doesnt exist
                if (tm.findSubstring("Username:") == -1)
                    throw new IllegalStateException();
                temp = tm.get(tm.findSubstring("Username:"));

                debug("[" + temp.substring(0, 8) + "] , [" + temp.substring(9) + "]");
                if (temp.substring(0, 9).equals("Username:")) {
                    loadedContents.add(temp.substring(9));
                } else
                    throw new IllegalStateException();
                debug("Username loaded.");
                debug("Removing username from TokenManip object.");
                tm.delete(tm.findSubstring("Username:"));

                //
                //load password
                //
                debug("Attempting to load password...");
                //throw exception if username doesnt exist
                if (tm.findSubstring("Password:") == -1)
                    throw new IllegalStateException();
                temp = tm.get(tm.findSubstring("Password:"));

                debug("[" + temp.substring(0, 8) + "] , [" + temp.substring(9) + "]");
                if (temp.substring(0, 9).equals("Password:")) {
                    loadedContents.add(temp.substring(9));
                } else
                    throw new IllegalStateException();
                debug("Password loaded.");
                debug("Removing password from TokenManip object.");
                tm.delete(tm.findSubstring("Password:"));

                //
                //load program
                //
                debug("Attempting to load program...");
                //throw exception if program name doesnt exist
                if (tm.findSubstring("Program:") == -1)
                    throw new IllegalStateException();
                temp = tm.get(tm.findSubstring("Program:"));

                debug("[" + temp.substring(0, 7) + "] , [" + temp.substring(8) + "]");
                if (temp.substring(0, 8).equals("Program:")) {
                    loadedContents.add(temp.substring(8));
                } else
                    throw new IllegalStateException();
                debug("Program loaded.");
                debug("Removing program from TokenManip object.");
                tm.delete(tm.findSubstring("Program:"));

                //
                //load action
                //
                debug("Attempting to load action...");
                //throw exception if program name doesnt exist
                if (tm.findSubstring("Action:") == -1)
                    throw new IllegalStateException();
                temp = tm.get(tm.findSubstring("Action:"));

                debug("[" + temp.substring(0, 6) + "] , [" + temp.substring(7) + "]");
                if (temp.substring(0, 7).equals("Action:")) {
                    loadedContents.add(temp.substring(7));
                } else
                    throw new IllegalStateException();
                debug("Action loaded.");
                debug("Removing action from TokenManip object.");
                tm.delete(tm.findSubstring("Action:"));
            }



            debug("Finished loading tokens.");
        }
        catch (IllegalStateException e)
        {
            debug("Error: Malformed config file. Check debug log for details. Aborting.");
            System.exit(-1);
        }
    }

    //works. may want to add encryption support later on.
    //**Currently moved authentication to the SCRM object.
    /*
    public boolean authenticatePassword(String pass) throws IOException
    {
        debug("==New Task: Authenticate password.");
        debug("Stored password: " + password);
        debug("Given password: " + pass);
        if(password.equals(pass))
            debug("Password match. Authentication succeeded.");
        else
            debug("Password mismatch. Authentication failed.");
        debug("=Task complete: Authenticate password.");
        return password.equals(pass);
    }
    */

    public ArrayList<String> exportContents()
    {
        return loadedContents;
    }
}
