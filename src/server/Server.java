//package server;
//
//import common.Request;
//import common.Response;
//import server.IO.FileManager;
//import server.commands.Command;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Server {
//    private static Server server;
//    private static final int PORT = 5001;
//
//    private CollectionManager collectionManager;
//    private ConsoleCaller consoleCaller;
//    private FileManager fileManager;
//    private CommandManager commandManager;
//
//    private Server() {}
//
//    public static Server getServer() {
//        if (server == null) {
//            server = new Server();
//        }
//        return server;
//    }
//
//    public void setCollectionManager(CollectionManager collectionManager) {
//        this.collectionManager = collectionManager;
//    }
//
//    public void setConsoleCaller(ConsoleCaller consoleCaller) {
//        this.consoleCaller = consoleCaller;
//    }
//
//    public void setFileManager(FileManager fileManager) {
//        this.fileManager = fileManager;
//    }
//
//    public void setCommandManager(CommandManager commandManager) {
//        this.commandManager = commandManager;
//    }
//
//    public CollectionManager getCollectionManager() {
//        return collectionManager;
//    }
//
//    public ConsoleCaller getConsoleCaller() {
//        return consoleCaller;
//    }
//
//    public FileManager getFileManager() {
//        return fileManager;
//    }
//
//    public CommandManager getCommandManager() {
//        return commandManager;
//    }
//
//    public void start() {
//
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            fileManager.fileWriter();
//            System.out.println("Сервер остановлен. Коллекция сохранена в файл.");
//        }));
//
//        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//            System.out.println("Сервер запущен на порту " + PORT);
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("Клиент подключен: " + clientSocket.getInetAddress());
//
//
//                new Thread(() -> handleClient(clientSocket)).start();
//            }
//
//        } catch (IOException e) {
//            System.err.println("Ошибка сервера: " + e.getMessage());
//        }
//    }
//
//    private void handleClient(Socket socket) {
//        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
//
//            while (true) {
//                Request request = (Request) in.readObject();
//
//                System.out.println("Получена команда: " + request.getCommandName());
//
//                Command command = commandManager.getCommandByKey(request.getCommandName());
//                Response response;
//
//                if (command != null) {
//
//                    response = command.execute(request.getCommandStrArg(), request.getCommandObjArg());
//                } else {
//
//                    response = new Response("Неизвестная команда: " + request.getCommandName());
//                }
//
//
//                out.writeObject(response);
//                out.flush();
//            }
//
//        } catch (EOFException | ClassNotFoundException e) {
//            System.out.println("Клиент отключился.");
//        } catch (IOException e) {
//            System.err.println("Ошибка соединения с клиентом: " + e.getMessage());
//        }
//    }
//
//}
package server;

import common.Request;
import common.Response;
import server.IO.FileManager;
import server.commands.Command;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class Server {
    private static final int PORT = 5001;
    private static final int BUFFER_SIZE = 8192;

    private CollectionManager collectionManager;
    private ConsoleCaller consoleCaller;
    private FileManager fileManager;
    private CommandManager commandManager;
    private static Server server;

    private Server() {}

    public static Server getServer() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }


    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            fileManager.fileWriter();
            System.out.println("Сервер остановлен. Коллекция сохранена в файл.");
        }));
        new Thread(consoleCaller).start();

        try (Selector selector = Selector.open();
             ServerSocketChannel serverChannel = ServerSocketChannel.open()) {

            serverChannel.bind(new InetSocketAddress(PORT));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Сервер запущен на порту " + PORT);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (true) {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isAcceptable()) {
                        handleAccept(key, selector);
                    } else if (key.isReadable()) {
                        handleRead(key, buffer);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }

    private void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();

        if (clientChannel != null) {
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("Клиент подключен: " + clientChannel.getRemoteAddress());
        }
    }

    private void handleRead(SelectionKey key, ByteBuffer buffer) {
        SocketChannel clientChannel = (SocketChannel) key.channel();

        try {
            buffer.clear();
            int bytesRead = clientChannel.read(buffer);
            if (bytesRead == -1) {
                clientChannel.close();
                System.out.println("Клиент отключился.");
                return;
            }

            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);


            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Request request = (Request) ois.readObject();

            System.out.println("Получена команда: " + request.getCommandName());

            Command command = commandManager.getCommandByKey(request.getCommandName());
            Response response;
            if (command != null) {
                response = command.execute(request.getCommandStrArg(), request.getCommandObjArg());
            } else {
                response = new Response("Неизвестная команда: " + request.getCommandName());
            }



            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(response);
            oos.flush();

            byte[] responseBytes = baos.toByteArray();
            ByteBuffer responseBuffer = ByteBuffer.wrap(responseBytes);
            clientChannel.write(responseBuffer);

        } catch (IOException | ClassNotFoundException e) {
            try {
                System.out.println("Ошибка при чтении/обработке клиента");
                clientChannel.close();
            } catch (IOException ex) {
                System.out.println("Не удалось закрыть соединение" );
            }
        }
    }
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setConsoleCaller(ConsoleCaller consoleCaller) {
        this.consoleCaller = consoleCaller;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ConsoleCaller getConsoleCaller() {
        return consoleCaller;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}

