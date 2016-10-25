package com.company;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kickass on 10/10/2016.
 */
public class ServerPacketHandler {
    public static ServerResponseManager rmS;
    private int port;

    public ServerPacketHandler()
    {
        rmS = new ServerResponseManager();
    }

    public static void startServer(final int port)
    //public static void startServer()
    {
        //TODO Have server load config file to get USERPASSPAIRS here
        ServerConfigReader scr;
        try {
             scr = new ServerConfigReader();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating ConfigReader. Aborting program.");
            System.exit(-1);
        }

        //extract the config file contents, create the UserInfo objects out of this info.


        UserInfo ui = new UserInfo("Bobby", "pass", "cmd.exe", "s");
        rmS.addUserInfo(ui);

        new Thread() {
            @Override
            public void run() {
                //ServerSocket server = null;
                while(true) {
                    //declare objects so they can be used outside of the scope they are assigned in
                    Socket s = null;
                    BufferedReader in = null;
                    PrintWriter out = null;
                    try (ServerSocket server = new ServerSocket(port)) {

                        //attempt to create socket connection
                        try {
                            s = server.accept();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Could not accept connection on port " + port);
                            System.exit(-1);
                        }

                        //setup read and write streams
                        try {
                            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                            out = new PrintWriter(s.getOutputStream(), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Read failed.");
                            System.exit(-1);
                        }

                        //begin conversation
                        try {
                            String line = "Are you ready?";
                            System.out.println("[Server response]" + line);
                            out.println(line);

                            //if client responds "End", then terminate the conversation. The try-with-resources
                            //will close the read/write and socket connections with the client.
                            //the rmS (Response Manager - Server) will determine how to properly respond to the client.

                            while (!line.equals("End")) {
                                line = in.readLine();
                                System.out.println("[Server heard]" + line);
                                //if client says end of conversation, then end the connection
                                if(line.equals("End"))
                                    break;
                                System.out.println("[Server response]" + rmS.getResponse(line));
                                out.println(rmS.getResponse(line));
                                TimeUnit.SECONDS.sleep(1);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Could not listen to port " + port);
                        System.exit(-1);
                    }

                    System.out.println("[[Server ending connection successfully.]]");
                }
            }
        }.start();
    }
}