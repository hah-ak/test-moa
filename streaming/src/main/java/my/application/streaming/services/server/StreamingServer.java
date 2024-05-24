package my.application.streaming.services.server;

import org.apache.tomcat.util.buf.ByteBufferUtils;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.core.codec.ByteBufferEncoder;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * 지속적연결이 필요한 곳들이다보니 auto close를 함부로 사용하지 않는다.
 * */
public class StreamingServer {
    private static ThreadPoolExecutor threadPoolExecutor;
    private static ExecutorService pool = Executors.newFixedThreadPool(2);
    static {
        threadPoolExecutor = new ThreadPoolExecutor(2, 4, 10000, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20));
    }

    public static void main(String[] args) throws IOException {
        nonBlocking();
    }

    public static void blocking() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(9402)) {
            while (true) {
                    Socket clientSocket = serverSocket.accept();
                    pool.execute(()->{
                        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));) {
                            String inputLine;
                            while ((inputLine = in.readLine()) != null) {
                                Thread.sleep(1000);
                                System.out.println("Time : " + LocalDateTime.now().toLocalTime() + " Thread : " + Thread.currentThread());
                                out.write("보냄 : " + inputLine);
                                out.flush();
                            }
                            clientSocket.close();
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    System.out.println("done time : " + LocalDateTime.now().toLocalTime());
                }
        }
    }

    private static ConcurrentHashMap<String, List<SocketChannel>> socketMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<SocketChannel, String> channelRoomMap = new ConcurrentHashMap<>();

    public static void nonBlocking() {
        try (
                Selector selector = Selector.open();
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ){
            // 최초 연결 요청을 받아들이는 부분
            serverSocketChannel.bind(new InetSocketAddress(9401));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        try {
                            // SelectableChannel channel = selectionKey.channel(); // serversocketchannel 객체 이다.
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_WRITE);
                            accept.register(selector, SelectionKey.OP_READ);
                            accept.write(ByteBuffer.wrap("what's your room number?".getBytes(StandardCharsets.UTF_8)));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else if (selectionKey.isReadable()) {
                        try {
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel(); // accept를 해서 만들어진 객체이다.
                            String s = channelRoomMap.get(socketChannel);

                            ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                            int read = socketChannel.read(byteBuffer);
                            ByteBuffer flip = byteBuffer.flip();

                            if (read > 0) {
                                List<SocketChannel> socketChannels = socketMap.get(s);
                                socketChannels.forEach(socketChannel1 ->{
                                    if (socketChannel1 != socketChannel) {
                                        try {
                                            socketChannel1.write(flip);
                                        } catch (IOException ignore) {}
                                    }
                                });
                            } else {
                                socketChannel.close();
                                socketMap.get(s).remove(socketChannel);
                                channelRoomMap.remove(socketChannel);
                            }
                        } catch (IOException e) {
                            selectionKey.channel().close();
                            throw new RuntimeException(e);
                        }
                    }
//                    else if (selectionKey.isWritable()) {
//                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel(); // accept를 해서 만들어진 객체이다.
//                        ByteBuffer allocate = ByteBuffer.allocate(256);
//                        int read = socketChannel.read(allocate);
//                        if (read > 0) {
//                            allocate.flip();
//                            String s = new String(allocate.array(), StandardCharsets.UTF_8);
//                            System.out.println(socketChannel.hashCode());
//                            channelRoomMap.put(socketChannel,s);
//                            socketMap.computeIfAbsent(s, k -> new ArrayList<>()).add(socketChannel);
//                        }
//                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
