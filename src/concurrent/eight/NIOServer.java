package concurrent.eight;

import org.nutz.lang.Strings;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhour on 2017/2/19.
 */
public class NIOServer {
    private static final int port = 1000;
    public static void main(String[] args) throws IOException {
        new NIOServer().startServer();
    }

    class HandleMs implements Runnable{
        SelectionKey sk;
        ByteBuffer bb;
        public HandleMs(SelectionKey sk, ByteBuffer bb){
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            String msg = new String(bb.array());
            System.out.println("get:"+msg);
            if (Strings.isNotBlank(msg)){
                try {
                    float result = CalUtil.opt(msg);
                    doWrite(sk,Float.toString(result));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Selector selector;
    private ExecutorService tp = Executors.newCachedThreadPool();

    private void doAcctpt(SelectionKey sk) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        clientChannel = server.accept();
        clientChannel.configureBlocking(false);

        SelectionKey clientKey = clientChannel.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);

        InetAddress clientAddress = clientChannel.socket().getInetAddress();
        System.out.println("Accept connection from "+clientAddress.getHostAddress());

        doWrite(clientKey,"connected");
    }

    public void startServer() throws IOException {
        selector = SelectorProvider.provider().openSelector();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(port));
        ssc.register(selector,SelectionKey.OP_ACCEPT);

        for (;;){
            selector.select();

            Set<SelectionKey> readyKeys = selector.selectedKeys();

            Iterator<SelectionKey> iter = readyKeys.iterator();

            while (iter.hasNext()){
                SelectionKey sk = iter.next();
                iter.remove();

                if (sk.isAcceptable()){
                    doAcctpt(sk);
                }else if (sk.isValid() && sk.isReadable()){
                    doRead(sk);
                }
            }
        }
    }

    private void doWrite(SelectionKey sk,String msg) {
        SocketChannel chnnel = (SocketChannel) sk.channel();
        byte[] msgByte = msg.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(msgByte.length);
        buffer.put(msgByte);
        buffer.flip();
        try {
            if(chnnel.isConnected()){
                chnnel.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect(SelectionKey sc) {
        SocketChannel chnnel = (SocketChannel) sc.channel();
        try {
            chnnel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Disconnect.");
    }

    private void doRead(SelectionKey sk) {
        SocketChannel chnnel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8092);
        int len;
        try {
            len = chnnel.read(bb);
            if (len < 0){
                disconnect(sk);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            disconnect(sk);
            return;
        }

        bb.flip();
        tp.execute(new HandleMs(sk,bb));
    }
}
