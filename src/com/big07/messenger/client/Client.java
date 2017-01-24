package com.big07.messenger.client;

import com.big07.messenger.Connection;
import com.big07.messenger.ConsoleHelper;
import com.big07.messenger.Message;
import com.big07.messenger.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client
{
    protected Connection connection;
    private volatile boolean clientConnected = false;

    public class SocketThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                String serverAddress = getServerAddress();
                int serverPort = getServerPort();

                Socket socket = new Socket(serverAddress, serverPort);
                connection = new Connection(socket);

                clientHandshake();
                clientMainLoop();


            }
            catch (IOException e)
            {
                ConsoleHelper.writeMessage("IOException. Ошибка в методе run() класса SocketThread (пакет client).");
                notifyConnectionStatusChanged(false);
            }
            catch (ClassNotFoundException e)
            {
                ConsoleHelper.writeMessage("ClassNotFoundException. Ошибка в методе run() класса SocketThread (пакет client).");
                notifyConnectionStatusChanged(false);
            }
        }

        protected void processIncomingMessage(String message)
        {
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName)
        {
            ConsoleHelper.writeMessage(userName + " присоединился к беседе.");
        }

        protected void informAboutDeletingNewUser(String userName)
        {
            ConsoleHelper.writeMessage(userName + " покинул беседу.");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected)
        {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this)
            {
                Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException
        {
            while (true)
            {
                Message massage = connection.receive();
                if (massage.getType() == MessageType.NAME_REQUEST)
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));
                else if (massage.getType() == MessageType.NAME_ACCEPTED)
                {
                    notifyConnectionStatusChanged(true);
                    return;
                }
                else
                    throw new IOException("Unexpected MessageType");
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException
        {
            while (true)
            {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT)
                    processIncomingMessage(message.getData());
                else if (message.getType() == MessageType.USER_ADDED)
                    informAboutAddingNewUser(message.getData());
                else if (message.getType() == MessageType.USER_REMOVED)
                    informAboutDeletingNewUser(message.getData());
                else
                    throw new IOException("Unexpected MessageType");
            }
        }
    }

    protected String getServerAddress()
    {
        ConsoleHelper.writeMessage("Пожалуйста введите адресс сервера.");
        return ConsoleHelper.readString();
    }

    protected int getServerPort()
    {
        ConsoleHelper.writeMessage("Пожалуйста, введите номер порта.");
        return ConsoleHelper.readInt();
    }

    protected String getUserName()
    {
        ConsoleHelper.writeMessage("Пожалуйста, введите имя пользователя.");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSentTextFromConsole()
    {
        return true;
    }

    protected SocketThread getSocketThread()
    {
        return new SocketThread();
    }

    protected void sendTextMessage(String text)
    {
        try
        {
            connection.send(new Message(MessageType.TEXT, text));
        }
        catch (IOException e)
        {
            ConsoleHelper.writeMessage("Произошла ошибка при отправке сообщения.");
            clientConnected = false;
        }
    }

    public void run()
    {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        try
        {
            synchronized (this)
            {
                this.wait();
            }
        }
        catch (InterruptedException e)
        {
            ConsoleHelper.writeMessage("Ошибка с wait.");
        }

        if (clientConnected)
        {
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
        } else
        {
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
        }

        while (clientConnected)
        {
            String text = ConsoleHelper.readString();
            if (text.equals("exit"))
                break;
            else if (shouldSentTextFromConsole())
                sendTextMessage(text);
        }
    }

    public static void main(String[] args)
    {
        Client client = new Client();
        client.run();
    }
}
