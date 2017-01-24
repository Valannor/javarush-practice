package com.big07.messenger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server
{
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    private static class Handler extends Thread
    {
        private Socket socket;

        public Handler(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            Connection connection = null;
            String newUserName = null;
            try
            {
                connection = new Connection(socket);
                SocketAddress socketAddress = connection.getRemoteSocketAddress();
                ConsoleHelper.writeMessage("Установлено новое соединение.");

                newUserName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, newUserName));
                sendListOfUsers(connection, newUserName);

                serverMainLoop(connection, newUserName);
            }
            catch (Exception e)
            {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом.");
                try
                {
                    if (connection != null)
                        connection.close();
                }
                catch (IOException e1)
                {
                    ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом.");
                }

                if (newUserName != null)
                {
                    connectionMap.remove(newUserName);
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED, newUserName));
                }

                ConsoleHelper.writeMessage("Соединение с " + newUserName + " закрыто.");
            }
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException
        {
            while (true)
            {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message message = connection.receive();

                if (message.getType() == MessageType.USER_NAME)
                {
                    String userName = message.getData();
                    if (userName != null && !userName.isEmpty() && !connectionMap.containsKey(userName))
                    {
                        connectionMap.put(userName, connection);
                        connection.send(new Message(MessageType.NAME_ACCEPTED));

                        return userName;
                    }
                }
            }
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException
        {
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet())
            {
                String name = pair.getKey();
                if (!name.equals(userName))
                {
                    connection.send(new Message(MessageType.USER_ADDED, name));
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException
        {
            while (true)
            {
                Message receivedMassage = connection.receive();
                if (receivedMassage.getType() == MessageType.TEXT)
                {
                    Message massageToSend = new Message(MessageType.TEXT, userName + ": " + receivedMassage.getData());
                    sendBroadcastMessage(massageToSend);
                } else
                {
                    ConsoleHelper.writeMessage("Произошла ошибка в главном цикле сервера.");
                }
            }
        }
    }

    public static void sendBroadcastMessage(Message message)
    {
        for (Map.Entry<String, Connection> pair : connectionMap.entrySet())
        {
            String clientName = pair.getKey();
            Connection connection = pair.getValue();
            try
            {
                connection.send(message);
            }
            catch (IOException e)
            {
                ConsoleHelper.writeMessage("Не получилось отправить сообщение пользователю " + clientName + ".");
            }
        }
    }

    public static void main(String[] args)
    {
        ConsoleHelper.writeMessage("Введите номер порта.");
        int port = ConsoleHelper.readInt();

        try (ServerSocket serverSocket = new ServerSocket(port);)
        {
            ConsoleHelper.writeMessage("Сервер запущен...");

            while (true)
            {
                Socket receivingSocket = serverSocket.accept();
                Handler handler = new Handler(receivingSocket);
                handler.start();
            }
        }
        catch (IOException e)
        {
            ConsoleHelper.writeMessage(e.getMessage());
        }
    }
}
