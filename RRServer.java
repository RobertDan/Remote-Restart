package com.company;

/**
 * Created by Kickass on 10/22/2016.
 */
public class RRServer {
    public static int port;

    public RRServer()
    {

    }

    public static void main(String[] args)
    {
        port = 20050;
        ServerPacketHandler ph = new ServerPacketHandler();
        ph.startServer(port);
    }
}
