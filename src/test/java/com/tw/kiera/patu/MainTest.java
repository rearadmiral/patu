package com.tw.kiera.patu;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.*;

public class MainTest {

    public static final int DEFAULT_PORT = 8080;
    private Main server;

    @Before
    public void startServer() throws Exception {
        server = new Main();
        server.startAsync();
        while(!server.isRunning()) {
            Thread.sleep(100);
        }
    }

    @After
    public void shutdownServer() throws Exception {
        this.server.stop();
    }

    @Test
    public void shouldListenOn8080() throws Exception {
        assertTrue(canConnectTo(DEFAULT_PORT));
    }

    @Test
    public void allowAllowPortToBeSpecifiedWithCmdLineArgs() throws InterruptedException {
        server.stop();
        server = new Main("-p", "7777");
        server.startAsync();
        while(!server.isRunning()) {
            Thread.sleep(100);
        }
        assertEquals(7777, server.getPort());

    }

    private boolean canConnectTo(int port) throws Exception {
        Socket s = new Socket(InetAddress.getLocalHost(), port);
        return s.isConnected();
    }
}
