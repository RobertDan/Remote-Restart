package com.company;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kickass on 10/10/2016.
 */
public class ClientPacketHandler {
    public static ServerResponseManager rmS;
    public static ClientResponseManager rmC;
    public int port;

    public ClientPacketHandler()
    {
        rmC = new ClientResponseManager();
    }

    public static void startClient(final int port)
    {
        //TODO have user enter UserInfo / read from a config file
        //UserInfo up = new UserInfo("Bobby", "pass");
        UserInfo up = new UserInfo("Bobby", "pass", "cmd.exe", "s");
        rmC.setUserInfo(up);


        new Thread() {
            @Override
            public void run() {
                //Socket s = null;
                BufferedReader in = null;
                PrintWriter out = null;

                //try (Socket s = new Socket("localhost", port)){
                try (Socket s = new Socket("192.168.1.78", port)){
                    try {
                        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        out = new PrintWriter(s.getOutputStream(), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Read failed.");
                        System.exit(-1);
                    }

                    try {
                        String line = "Are you ready?";
                        while (!line.equals("Okay")) {
                            line = in.readLine();
                            System.out.println("[Client heard]" + line);
                            System.out.println("[Client response]" + rmC.getResponse(line));
                            out.println(rmC.getResponse(line));
                            TimeUnit.SECONDS.sleep(1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Could not establish connection on port " + port);
                    System.exit(-1);
                }

                System.out.println("[[Client ending connection successfully.]]");
            }
        }.start();
    }
}
