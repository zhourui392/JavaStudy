package concurrent.eight;

import org.nutz.lang.Strings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhour on 2017/2/19.
 */
public class AIOServer {
    public static void main(String[] args) throws IOException {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(1000));
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                buffer.clear();
                Future<Integer> writeResult = null;
                try {
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    buffer.flip();
                    String msg = new String(buffer.array());
                    if (Strings.isNotBlank(msg)){
                        try {
                            float resultFloat = CalUtil.opt(msg);
                            writeResult = doWrite(result,Float.toString(resultFloat));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }finally {
                    server.accept(null,this);
                    try {
                        if (writeResult != null){
                            writeResult.get();
                        }
                        result.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }

            private Future doWrite(AsynchronousSocketChannel result, String msg) {
                byte[] msgByte = msg.getBytes();
                ByteBuffer buffer = ByteBuffer.allocate(msgByte.length);
                buffer.put(msgByte);
                buffer.flip();
                return result.write(buffer);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("failed:"+exc);
            }
        });

        // 主线程继续
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
