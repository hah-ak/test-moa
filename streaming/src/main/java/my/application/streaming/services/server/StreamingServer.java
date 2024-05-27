package my.application.streaming.services.server;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
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
//        nonBlockingSTOMPMessage();
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

    private static ConcurrentHashMap<String, List<SocketChannel>> roomMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<SocketChannel, List<String>> clientMap = new ConcurrentHashMap<>();

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
//                            accept.write(ByteBuffer.wrap("what's your room number?".getBytes(StandardCharsets.UTF_8)));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else if (selectionKey.isReadable()) {
                        try {
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel(); // accept를 해서 만들어진 객체이다.
//                            String s = clientMap.get(socketChannel);
                            ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                            int read = socketChannel.read(byteBuffer);
                            ByteBuffer flip = byteBuffer.flip();

                            byte[] data = new byte[byteBuffer.remaining()];
                            byteBuffer.get(data);
                            String message = new String(data);
                            System.out.println("Received: " + message);

                            // Example parsing logic: split headers and body
                            String[] parts = message.split("\n\n", 2);
                            String headers = parts[0];
                            String body = parts.length > 1 ? parts[1] : "";

                            System.out.println("Headers: " + headers);
                            System.out.println("Body: " + body);

                            // Echo the message back to the client
                            byteBuffer.rewind();
                            socketChannel.write(ByteBuffer.wrap(data));


                            if (read > 0) {
                                socketChannel.write(flip);
//                                List<SocketChannel> socketChannels = roomMap.get(s);
//                                socketChannels.forEach(socketChannel1 ->{
//                                    if (socketChannel1 != socketChannel) {
//                                        try {
//                                            socketChannel1.write(flip);
//                                        } catch (IOException ignore) {}
//                                    }
//                                });
                            } else {
                                socketChannel.close();
//                                roomMap.get(s).remove(socketChannel);
//                                clientMap.remove(socketChannel);
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

    public static void nonBlockingSTOMPMessage() {
        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        ){

            serverSocketChannel.bind(new InetSocketAddress(9401));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    ByteBuffer buffer = ByteBuffer.allocate(256); //512kb
                    Map<String, String> packetMap = parsePacket(buffer);

                    if (selectionKey.isAcceptable()) {
                        try {
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_WRITE);
                            accept.register(selector, SelectionKey.OP_READ);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else if (selectionKey.isReadable()) {
                        try {
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            int read = channel.read(buffer);

                            if (read < 0 ) { // fin message
                                List<String> rooms = clientMap.get(channel);
                                rooms.forEach(s -> {roomMap.get(s).remove(channel);});
                                clientMap.remove(channel);
                                channel.close();
                            } else {
                                updateClientMap(channel, packetMap);
                                updateRoomMap(channel, packetMap);

                                buffer.rewind();
                                ByteBuffer body = ByteBuffer.wrap(packetMap.get("body").getBytes(StandardCharsets.UTF_8));
                                List<SocketChannel> socketChannels = roomMap.get(packetMap.get("destination"));
                                for (SocketChannel socketChannel : socketChannels) {
                                    if (socketChannel != channel) {
                                        socketChannel.write(body);
                                    }
                                }
                            }
                            buffer.clear();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                iterator.remove();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateClientMap(SocketChannel socketChannel, Map<String, String> parsedSocket) {
        String destination = parsedSocket.get("destination");
        List<String> strings = clientMap.computeIfAbsent(socketChannel, s -> new ArrayList<>());
        boolean hasDestination = strings.stream().noneMatch(s -> s.contentEquals(destination));
        if (!hasDestination) {
            strings.add(destination);
        }
    }

    public static void updateRoomMap(SocketChannel socketChannel, Map<String, String> parsedSocket) {
        String destination = parsedSocket.get("destination");
        List<SocketChannel> socketChannels = roomMap.computeIfAbsent(destination, s -> new ArrayList<>()) ;
        boolean hasSocketChannel = socketChannels.stream().noneMatch(channel -> channel.equals(socketChannel));
        if (!hasSocketChannel) {
            socketChannels.add(socketChannel);
        }
    }

    public static Map<String,String> parsePacket(ByteBuffer buffer) {
        ByteBuffer flip = buffer.flip(); //읽기모드
        byte[] data = new byte[flip.remaining()];
        flip.get(data); // 해당 어레이에 읽어서 결과값 덮어씀
        String packet = new String(data);

        HashMap<String, String> packetMap = new HashMap<>();

        int headerEOL = packet.indexOf("\n\n");
        if (headerEOL == -1) {
            return packetMap;
        }

        packetMap.put("body", packet.substring(headerEOL).trim());

        String[] headers = packet.substring(0, headerEOL).split("\n");
        return Arrays.stream(headers).reduce(
                packetMap,

                (result, current)->{
                    String[] split = current.split(":");
                    result.put(split[0], split[1]);
                    return result;
                },

                (total1,total2)->{
                    total1.putAll(total2);
                    return total1;
                });
    }
}
