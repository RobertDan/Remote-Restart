package com.company;
import org.testng.Assert;

import java.io.*;

/**
 * Created by Kickass on 9/28/2016.
 */
public class ServerConfigReaderTest {
    @org.testng.annotations.Test
    //works
    public void testLoadConfig() throws IOException
    {
        ServerConfigReader pr = new ServerConfigReader();
        //Assert.assertEquals(pr.configContents,"Password:pooperscooper,MaxAttempts:1,");
    }

    @org.testng.annotations.Test
    public void testConfigSorter() throws IOException
    {
        ServerConfigReader pr = new ServerConfigReader();
    }

    @org.testng.annotations.Test
    public void testLoadConfigTokens() throws IOException
    {
        ServerConfigReader pr = new ServerConfigReader();
        TokenManip tm = new TokenManip(pr.configContents, ",");

        //Assert.assertEquals(tm.size(),2);
        Assert.assertEquals(tm.get(0),"Password:pooperscooper");
        Assert.assertEquals(tm.get(1),"MaxAttempts:1");
        Assert.assertEquals(tm.get(2),"Program:python.exe");
    }

    @org.testng.annotations.Test
    public void testAuthenticatePassword() throws IOException
    {
        ServerConfigReader pr = new ServerConfigReader();
        TokenManip tm = new TokenManip(pr.configContents, ",");

        Assert.assertEquals(pr.authenticatePassword("pooperscooper"),true);
        Assert.assertEquals(pr.authenticatePassword("pooperscooper2"),false);
    }

    /* works. comemented out to avoid problems with the config file changes
    @org.testng.annotations.Test
    public void testReloadConfig() throws IOException
    {
        ServerConfigReader pr = new ServerConfigReader();
        TokenManip tm = new TokenManip(pr.configContents);
        tm.setDefaultDelimiter(",");

        //change the config file
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("config.txt"), "utf-8"))) {
            writer.write("Password:blah\nMaxAttempts:1");
        }

        pr.reloadConfig();
        TokenManip tm2 = new TokenManip(pr.configContents,",");

        Assert.assertEquals(tm2.get(tm2.findSubstring("Password:")),"Password:blah");
        Assert.assertNotEquals(tm.get(tm.findSubstring("Password:")),tm2.get(tm.findSubstring("Password:")));

        //restore the config file
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("config.txt"), "utf-8"))) {
            writer.write("Password:pooperscooper\nMaxAttempts:1");
        }

    }*/
}
