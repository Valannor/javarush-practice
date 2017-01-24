package com.big07.messenger.client;

import com.big07.messenger.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BotClient extends Client
{
    public class BotSocketThread extends SocketThread
    {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException
        {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message)
        {
            ConsoleHelper.writeMessage(message);
            SimpleDateFormat simpleDateFormat = null;
            Calendar calendar = new GregorianCalendar();

            if (message != null && message.length() > 0)
            {
                String[] strings = message.split(": ");
                if (strings.length >= 2)
                {
                    String userName = strings[0];
                    String text = strings[1];

                    switch (text)
                    {
                        case "дата":
                            simpleDateFormat = new SimpleDateFormat("d.MM.YYYY");
                            break;
                        case "день":
                            simpleDateFormat = new SimpleDateFormat("d");
                            break;
                        case "месяц":
                            simpleDateFormat = new SimpleDateFormat("MMMM");
                            break;
                        case "год":
                            simpleDateFormat = new SimpleDateFormat("YYYY");
                            break;
                        case "время":
                            simpleDateFormat = new SimpleDateFormat("H:mm:ss");
                            break;
                        case "час":
                            simpleDateFormat = new SimpleDateFormat("H");
                            break;
                        case "минуты":
                            simpleDateFormat = new SimpleDateFormat("m");
                            break;
                        case "секунды":
                            simpleDateFormat = new SimpleDateFormat("s");
                            break;
                    }

                    if (simpleDateFormat != null)
                        sendTextMessage(String.format("Информация для %s: %s", userName,
                                simpleDateFormat.format(calendar.getTime())));
                }
            }
        }
    }

    @Override
    protected SocketThread getSocketThread()
    {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSentTextFromConsole()
    {
        return false;
    }

    @Override
    protected String getUserName()
    {
        return "date_bot_" + (int) (Math.random() * 100);
    }

    public static void main(String[] args)
    {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
