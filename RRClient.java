package com.company;

/**
 * Created by Kickass on 10/22/2016.
 */
public class RRClient {
    public static int port;

    public RRClient(){}

    public static void main(String[] args)
    {
        port = 20050;
        ClientPacketHandler ph = new ClientPacketHandler();
        ph.startClient(port);
    }
}
