package concurrent.eight;

import org.nutz.lang.Streams;
import org.nutz.lang.Times;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhour on 2017/2/19.
 */
public class Client {
    private static final String host = "localhost";
    private static final int port = 1000;
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner s = new Scanner(System.in);
        String calExpress;
        Socket client = new Socket();
        client.connect(new InetSocketAddress(host,port));
        InputStream in = client.getInputStream();

        TimeUnit.SECONDS.sleep(1);
        System.out.println(new String(Streams.readBytes(in)));
        System.out.println("请输入四则混合运算表达式：");
        calExpress = s.next();
        OutputStream os = client.getOutputStream();
        os.write(calExpress.getBytes());
        byte[] firtByte = new byte[1];
        while ((in.read(firtByte)) != -1){
            System.out.println(calExpress+"="+new String(firtByte)+new String(Streams.readBytes(in)));
            break;
        }
        client.close();
    }
}
