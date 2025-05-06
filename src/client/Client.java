//package client;
//
//import common.Request;
//import common.Response;
//import common.collectionObject.Movie;
//import server.CommandManager;
//import common.MovieFiller;
//import server.Server;
//
//import java.io.*;
//import java.net.*;
//import java.util.NoSuchElementException;
//import java.util.Scanner;
//
//public class Client {
//    private static final String SERVER_HOST = "localhost";
//    private static final int SERVER_PORT = 5001;
//    CommandManager commandManager = Server.getServer().getCommandManager();
//    String[] text;
//    String command;
//    private static Client client;
//    private final InetAddress host;
//    private final int port;
//
//    private Client(InetAddress host, int port) {
//        this.host = host;
//        this.port = port;
//    }
//
//    public static Client getInstance(){
//        if(client == null){
//            try {
//                client = new Client(InetAddress.getByName("localhost"), SERVER_PORT);
//            } catch (UnknownHostException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return client;
//    }
//
//    public void start() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Клиент запущен. Введите команду:");
//
//        Socket socket = null;
//        ObjectOutputStream out = null;
//        ObjectInputStream in = null;
//
//        // Подключение к серверу
//        while (socket == null) {
//            try {
//                socket = new Socket(SERVER_HOST, SERVER_PORT);
//                out = new ObjectOutputStream(socket.getOutputStream());
//                in = new ObjectInputStream(socket.getInputStream());
//                System.out.println("Соединение с сервером установлено.");
//            } catch (IOException e) {
//                System.out.println("Сервер недоступен, пробуем подключиться снова...");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException ignored) {}
//            }
//        }
//
//        // Основной цикл чтения команд
//        while (true) {
//            try {
//                System.out.print("> ");
//                String line = scanner.nextLine().trim();
//                if (line.isEmpty()) continue;
//
//                if (line.equalsIgnoreCase("exit")) {
//                    System.out.println("Завершение клиента...");
//                    break;
//                }
//
//                String[] tokens = line.split(" ", 2);
//                String command = tokens[0];
//                String argument = tokens.length > 1 ? tokens[1] : "";
//
//
//                Request request = createRequest(command, argument);
//
//                // Отправка
//                out.writeObject(request);
//                out.flush();
//
//                // Получение и вывод ответа
//                Response response = (Response) in.readObject();
//                System.out.println(response.getMessage());
//
//            } catch (NoSuchElementException e) {
//                System.out.println("Завершение по Ctrl+D...");
//                break;
//            } catch (IOException e) {
//                System.err.println("Потеря соединения с сервером. Завершение...");
//                break;
//            } catch (ClassNotFoundException e) {
//                System.err.println("Не удалось прочитать ответ сервера.");
//            }
//        }
//
//        // Очистка ресурсов
//        try {
//            if (out != null) out.close();
//            if (in != null) in.close();
//            if (socket != null) socket.close();
//        } catch (IOException e) {
//            System.err.println("Ошибка при закрытии соединения.");
//        }
//    }
//    public Request createRequest(String command, String argument){
//        if (command.equalsIgnoreCase("add") || command.equalsIgnoreCase("update_id") || command.equalsIgnoreCase("add_if_max")){
//            MovieFiller movieFiller = new MovieFiller();
//            Movie objArgument = movieFiller.fill(new Movie());
//            if (objArgument != null) {
//                return new Request(command, argument, objArgument);
//            } else {
//                System.out.println("Ошибка при создании объекта Movie.");
//                return null;
//            }
//        }
//        else {
//            return new Request(command, argument);
//        }
//    }
//}


package client;

import common.MovieFiller;
import common.Request;
import common.Response;
import common.collectionObject.Movie;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private static final String SERVER_HOST = "localhost";
//    private static final String SERVER_HOST = "helios.cs.ifmo.ru";
    private static final int SERVER_PORT = 5001;
    private static final int BUFFER_SIZE = 8192;
    private static Client client;
    private final InetAddress host;
    private final int port;

    private Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public static Client getClient(){
        if(client == null){
            try {
                client = new Client(InetAddress.getByName(SERVER_HOST), SERVER_PORT);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return client;
    }
    public void start() {
        while (true){
        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Подключено к серверу " + SERVER_HOST + ":" + SERVER_PORT);

            while (true) {
                String argument="";
                String commandName="";
                try {
                    System.out.print("Введите команду (или 'exit' для выхода): ");
                    String[] text = scanner.nextLine().trim().split(" ");
                    commandName = text[0];

                    if (text.length>1){
                        argument=text[1];
                    }
                    if (commandName.equalsIgnoreCase("exit")) {
                        System.out.println("Отключение от сервера...");
                        System.exit(0);
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Завершение по Ctrl+D...");
                    System.exit(0);
                }

                Request request = createRequest(commandName,argument);

                // Отправка запроса
                sendRequest(socketChannel, request);

                // Получение ответа
                Response response = receiveResponse(socketChannel);
                if (response != null) {
                    System.out.println(response.getMessage());
                } else {
                    System.out.println("Ошибка получения ответа от сервера.");
                }
            }

            } catch (IOException e) {
                System.out.println("Сервер не доступен. Повторное подключение...");
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Работа клиента прервана");
                break;
            }

        }
    }

    private void sendRequest(SocketChannel socketChannel, Request request) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(request);
        oos.flush();

        byte[] requestBytes = baos.toByteArray();
        ByteBuffer writeBuffer = ByteBuffer.wrap(requestBytes);
        socketChannel.write(writeBuffer);
    }

    private Response receiveResponse(SocketChannel socketChannel) {
        try {
            ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            socketChannel.read(readBuffer);
            readBuffer.flip();

            byte[] responseBytes = new byte[readBuffer.remaining()];
            readBuffer.get(responseBytes);

            ByteArrayInputStream bais = new ByteArrayInputStream(responseBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Response) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка получения ответа: " + e.getMessage());
            return null;
        }
    }

    public Request createRequest(String command, String argument){
        if (command.equalsIgnoreCase("add") || command.equalsIgnoreCase("update_id") || command.equalsIgnoreCase("add_if_max")){
            MovieFiller movieFiller = new MovieFiller();
            Movie objArgument = movieFiller.fill(new Movie());
            if (objArgument != null) {
                return new Request(command, argument, objArgument);
            } else {
                System.out.println("Ошибка при создании объекта Movie.");
                return null;
            }
        }
        else {
            return new Request(command, argument);
        }
    }
}


