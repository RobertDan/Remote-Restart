package com.company;

/**
 * Created by Kickass on 10/22/2016.
 */
public class RRBoth {

    public static void main(String[] args)
    {
        int port = 20050;
        ServerPacketHandler sph = new ServerPacketHandler();
        sph.startServer(port);
        ClientPacketHandler cph = new ClientPacketHandler();
        cph.startClient(port);
    }
}
