package com.big12.log_parser;

import com.big12.log_parser.query.*;

import java.io.*;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery
{
    /**
     * TODO Задание 1
     */
    private Path logDir;

    public LogParser(Path logDir)
    {
        this.logDir = logDir;
    }

    private ArrayList<String> getLogsFromDirectory(Path logDir)
    {
        ArrayList<String> logs = new ArrayList<>();

        ArrayList<File> logFiles = new ArrayList<>();
        try
        {
            //Извлекаем все файлы, содержащие логи
            for (File file : logDir.toFile().listFiles())
                if (file.getName().endsWith(".log"))
                    logFiles.add(file);

            //Извлекаем логи из файлов и помещаем их в лист
            for (File file : logFiles)
            {
                try (BufferedReader reader = new BufferedReader(new FileReader(file)))
                {
                    while (reader.ready())
                        logs.add(reader.readLine());
                }
            }
        }
        catch (NullPointerException | IOException e)
        {
            e.printStackTrace();
        }

        return logs;
    }

    private Date getDateLog(String log)
    {
        String dateString = log.split("\t")[2];
        return transformToDate(dateString);
    }

    private Date transformToDate(String dateString)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try
        {
            date = dateFormat.parse(dateString);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return date;
    }

    private String getIp(String log)
    {
        return log.split("\t")[0];
    }

    private String getUser(String log)
    {
        return log.split("\t")[1];
    }

    private String getEventString(String log)
    {
        String eventString = log.split("\t")[3];
        return eventString.split(" ")[0];
    }

    private int getTask(String log)
    {
        String eventString = log.split("\t")[3];
        return Integer.parseInt(eventString.split(" ")[1]);
    }

    private String getStatusString(String log)
    {
        return log.split("\t")[4];
    }

    private void inspectAndChooseIp(Date after, Date before,
                                    Set<String> uniqueIps, String log)
    {
        if (isAvailable(after, before, log))
            uniqueIps.add(getIp(log));
    }

    private boolean isAvailable(Date after, Date before, String log)
    {
        return ((after == null && before == null)
                || (after == null && getDateLog(log).compareTo(before) <= 0)
                || (before == null && getDateLog(log).compareTo(after) >= 0)
                || (after != null && before != null && getDateLog(log).compareTo(after) >= 0 && getDateLog(log).compareTo(before) <= 0));
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before)
    {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before)
    {
        Set<String> uniqueIps = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            inspectAndChooseIp(after, before, uniqueIps, log);
        }

        return uniqueIps;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before)
    {
        Set<String> uniqueIps = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (getUser(log).equals(user))
            {
                inspectAndChooseIp(after, before, uniqueIps, log);
            }
        }

        return uniqueIps;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before)
    {
        Set<String> uniqueIps = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (event.toString().equals(getEventString(log)))
            {
                inspectAndChooseIp(after, before, uniqueIps, log);
            }
        }

        return uniqueIps;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before)
    {
        Set<String> uniqueIps = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (status.toString().equals(getStatusString(log)))
            {
                inspectAndChooseIp(after, before, uniqueIps, log);
            }
        }

        return uniqueIps;
    }


    /**
     * TODO Задание 2
     */
    private void inspectAndChooseUser(Date after, Date before,
                                      Set<String> userNames, String log)
    {
        if (isAvailable(after, before, log))
            userNames.add(getUser(log));
    }

    private void inspectAndChooseEventString(Date after, Date before,
                                             Set<String> events, String log)
    {
        if (isAvailable(after, before, log))
            events.add(getEventString(log));
    }

    @Override
    public Set<String> getAllUsers()
    {
        Set<String> allUsers = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            inspectAndChooseUser(null, null, allUsers, log);
        }

        return allUsers;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before)
    {
        Set<String> allUsers = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            inspectAndChooseUser(after, before, allUsers, log);
        }

        return allUsers.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before)
    {
        Set<String> userEvents = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (getUser(log).equals(user))
            {
                inspectAndChooseEventString(after, before, userEvents, log);
            }
        }

        return userEvents.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before)
    {
        Set<String> usersWithThisIp = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (getIp(log).equals(ip))
            {
                inspectAndChooseUser(after, before, usersWithThisIp, log);
            }
        }

        return usersWithThisIp;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before)
    {
        Set<String> loggedInUsers = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (getEventString(log).equals(Event.LOGIN.toString()))
            {
                inspectAndChooseUser(after, before, loggedInUsers, log);
            }
        }

        return loggedInUsers;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before)
    {
        Set<String> pluginDownloadedUsers = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (getEventString(log).equals(Event.DOWNLOAD_PLUGIN.toString()))
            {
                inspectAndChooseUser(after, before, pluginDownloadedUsers, log);
            }
        }

        return pluginDownloadedUsers;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before)
    {
        Set<String> wroteMessageUsers = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (getEventString(log).equals(Event.WRITE_MESSAGE.toString()))
            {
                inspectAndChooseUser(after, before, wroteMessageUsers, log);
            }
        }

        return wroteMessageUsers;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before)
    {
        Set<String> solvedTaskUsers = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (getEventString(log).equals(Event.SOLVE_TASK.toString()))
            {
                inspectAndChooseUser(after, before, solvedTaskUsers, log);
            }
        }

        return solvedTaskUsers;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task)
    {
        Set<String> solvedTaskUsers = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (getEventString(log).equals(Event.SOLVE_TASK.toString()) && task == getTask(log))
            {
                inspectAndChooseUser(after, before, solvedTaskUsers, log);
            }
        }

        return solvedTaskUsers;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before)
    {
        Set<String> doneTaskUsers = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (getEventString(log).equals(Event.DONE_TASK.toString()))
            {
                inspectAndChooseUser(after, before, doneTaskUsers, log);
            }
        }

        return doneTaskUsers;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task)
    {
        Set<String> doneTaskUsers = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (getEventString(log).equals(Event.DONE_TASK.toString()) && task == getTask(log))
            {
                inspectAndChooseUser(after, before, doneTaskUsers, log);
            }
        }

        return doneTaskUsers;
    }


    /**
     * TODO Задание 3
     */
    private void inspectAndChooseDate(Date after, Date before,
                                      Set<Date> dates, String log)
    {
        if (isAvailable(after, before, log))
            dates.add(getDateLog(log));
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before)
    {
        Set<Date> dates = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (user.equals(getUser(log)) && event.toString().equals(getEventString(log)))
            {
                inspectAndChooseDate(after, before, dates, log);
            }
        }

        return dates;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before)
    {
        Set<Date> dates = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (Status.FAILED.toString().equals(getStatusString(log)))
            {
                inspectAndChooseDate(after, before, dates, log);
            }
        }

        return dates;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before)
    {
        Set<Date> dates = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (Status.ERROR.toString().equals(getStatusString(log)))
            {
                inspectAndChooseDate(after, before, dates, log);
            }
        }

        return dates;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before)
    {
        Set<Date> dates = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (user.equals(getUser(log)) && Event.LOGIN.toString().equals(getEventString(log)))
            {
                dates.add(getDateLog(log));
            }
        }

        try
        {
            return new TreeSet<>(dates).first();
        }
        catch (NoSuchElementException e)
        {
            return null;
        }
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before)
    {
        Set<Date> dates = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (user.equals(getUser(log))
                    && Event.SOLVE_TASK.toString().equals(getEventString(log))
                    && task == getTask(log))
            {
                dates.add(getDateLog(log));
            }
        }

        try
        {
            return new TreeSet<>(dates).first();
        }
        catch (NoSuchElementException e)
        {
            return null;
        }
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before)
    {
        for (String log : getLogsFromDirectory(logDir))
        {
            if (user.equals(getUser(log))
                    && Event.DONE_TASK.toString().equals(getEventString(log))
                    && task == getTask(log))
            {
                return getDateLog(log);
            }
        }

        return null;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before)
    {
        return getDatesForUserAndEvent(user, Event.WRITE_MESSAGE, after, before);
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before)
    {
        return getDatesForUserAndEvent(user, Event.DOWNLOAD_PLUGIN, after, before);
    }


    /**
     * TODO Задание 4
     */
    private Event getEvent(String log)
    {
        switch (getEventString(log))
        {
            case "LOGIN":
                return Event.LOGIN;
            case "DOWNLOAD_PLUGIN":
                return Event.DOWNLOAD_PLUGIN;
            case "WRITE_MESSAGE":
                return Event.WRITE_MESSAGE;
            case "SOLVE_TASK":
                return Event.SOLVE_TASK;
            case "DONE_TASK":
                return Event.DONE_TASK;
            default:
                return null;
        }
    }

    private void inspectAndChooseEvent(Date after, Date before,
                                       Set<Event> events, String log)
    {
        if (isAvailable(after, before, log))
            events.add(getEvent(log));
    }

    private Map<Integer, Integer> allChosenTasksAttemptCountMap(Event event)
    {
        Map<Integer, Integer> map = new HashMap<>();
        int task;
        int attempts;

        for (String log : getLogsFromDirectory(logDir))
        {
            if (event.equals(getEvent(log)))
            {
                task = getTask(log);
                if (map.containsKey(task))
                {
                    attempts = map.get(task) + 1;
                    map.put(task, attempts);
                } else
                {
                    map.put(task, 1);
                }
            }
        }

        return map;
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before)
    {
        return getAllEvents(after, before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before)
    {
        Set<Event> events = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            inspectAndChooseEvent(after, before, events, log);
        }

        return events;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before)
    {
        Set<Event> events = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (ip.equals(getIp(log)))
            {
                inspectAndChooseEvent(after, before, events, log);
            }
        }

        return events;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before)
    {
        Set<Event> events = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (user.equals(getUser(log)))
            {
                inspectAndChooseEvent(after, before, events, log);
            }
        }

        return events;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before)
    {
        Set<Event> events = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (Status.FAILED.toString().equals(getStatusString(log)))
            {
                inspectAndChooseEvent(after, before, events, log);
            }
        }

        return events;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before)
    {
        Set<Event> events = new HashSet<>();

        for (String log : getLogsFromDirectory(logDir))
        {
            if (Status.ERROR.toString().equals(getStatusString(log)))
            {
                inspectAndChooseEvent(after, before, events, log);
            }
        }

        return events;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before)
    {
        int count = 0;

        for (String log : getLogsFromDirectory(logDir))
        {
            if (Event.SOLVE_TASK.equals(getEvent(log))
                    && task == getTask(log))
            {
                count++;
            }
        }

        return count;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before)
    {
        int count = 0;

        for (String log : getLogsFromDirectory(logDir))
        {
            if (Event.SOLVE_TASK.equals(getEvent(log))
                    && task == getTask(log)
                    && Status.OK.toString().equals(getStatusString(log)))
            {
                count++;
            }
        }

        return count;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before)
    {
        return allChosenTasksAttemptCountMap(Event.SOLVE_TASK);
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before)
    {
        return allChosenTasksAttemptCountMap(Event.DONE_TASK);
    }


    /**
     * TODO Задание 5
     */
    private Status getStatus(String log)
    {
        switch (getStatusString(log))
        {
            case "OK":
                return Status.OK;
            case "FAILED":
                return Status.FAILED;
            case "ERROR":
                return Status.ERROR;
            default:
                return null;
        }
    }

    private void inspectAndChooseStatus(Date after, Date before,
                                        Set<Status> statuses, String log)
    {
        if (isAvailable(after, before, log))
            statuses.add(getStatus(log));
    }

    @Override
    public Set<Object> execute(String query)
    {
        Set<Object> result = new HashSet<>();

        if (!query.contains("="))
        {
            String[] queryElements = query.split(" ");

            switch (queryElements[1])
            {
                case "ip":
                {
                    for (String ip : getUniqueIPs(null, null))
                        result.add(ip);
                    break;
                }
                case "user":
                {
                    for (String user : getAllUsers())
                        result.add(user);
                    break;
                }
                case "date":
                {
                    Set<Date> dates = new HashSet<>();
                    for (String log : getLogsFromDirectory(logDir))
                        inspectAndChooseDate(null, null, dates, log);

                    for (Date date : dates)
                        result.add(date);
                    break;
                }
                case "event":
                {
                    for (Event event : getAllEvents(null, null))
                        result.add(event);
                    break;
                }
                case "status":
                {
                    Set<Status> statuses = new HashSet<>();
                    for (String log : getLogsFromDirectory(logDir))
                        inspectAndChooseStatus(null, null, statuses, log);

                    for (Status status : statuses)
                        result.add(status);
                    break;
                }
            }
        } else
        {
            //TODO Задание 6
            String[] queryElements = query.split(" = ");

            //Здесь мы узнаем, что запращивают
            String[] fields = queryElements[0].split(" ");
            String getField = fields[1];
            String forField = fields[3];

            //Параметр 1 - ключевое слово поиска
            String paramValue = queryElements[1].replace("\"", "");
            //Параметр 2 - в какой промежуток времени
            Date after = null;
            Date before = null;


            //TODO Задание 7
            //Если есть даты, то задаем их и определяем paramValue
            if (queryElements[1].contains(" and date between "))
            {
                String[] parameters = queryElements[1].replace("\"", "").split(" and date between ");
                String[] dates = parameters[1].split(" and ");

                //Ключевое слово поиска
                paramValue = parameters[0];

                //Задаем даты
                after = transformToDate(dates[0]);
                before = transformToDate(dates[1]);
            }

            //алгоритм поиска
            for (String log : getLogsFromDirectory(logDir))
            {
                //Это от задания 7
                if (isAvailable(after, before, log))
                {
                    //Все остальное - от задания 6
                    switch (forField)
                    {
                        case "ip":
                        {
                            String ip = getIp(log);
                            if (paramValue.equals(ip))
                            {
                                switch (getField)
                                {
                                    case "user":
                                        result.add(getUser(log));
                                        break;
                                    case "date":
                                        result.add(getDateLog(log));
                                        break;
                                    case "event":
                                        result.add(getEvent(log));
                                        break;
                                    case "status":
                                        result.add(getStatus(log));
                                        break;
                                }
                            }
                            break;
                        }
                        case "user":
                        {
                            String user = getUser(log);
                            if (paramValue.equals(user))
                            {
                                switch (getField)
                                {
                                    case "ip":
                                        result.add(getIp(log));
                                        break;
                                    case "date":
                                        result.add(getDateLog(log));
                                        break;
                                    case "event":
                                        result.add(getEvent(log));
                                        break;
                                    case "status":
                                        result.add(getStatus(log));
                                        break;
                                }
                            }
                            break;
                        }
                        case "date":
                        {
                            if (paramValue.equals(log.split("\t")[2]))
                            {
                                switch (getField)
                                {
                                    case "ip":
                                        result.add(getIp(log));
                                        break;
                                    case "user":
                                        result.add(getUser(log));
                                        break;
                                    case "event":
                                        result.add(getEvent(log));
                                        break;
                                    case "status":
                                        result.add(getStatus(log));
                                        break;
                                }
                            }
                            break;
                        }
                        case "event":
                        {
                            Event event = getEvent(log);
                            if (paramValue.equals(event.toString()))
                            {
                                switch (getField)
                                {
                                    case "ip":
                                        result.add(getIp(log));
                                        break;
                                    case "user":
                                        result.add(getUser(log));
                                        break;
                                    case "date":
                                        result.add(getDateLog(log));
                                        break;
                                    case "status":
                                        result.add(getStatus(log));
                                        break;
                                }
                            }
                            break;
                        }
                        case "status":
                        {
                            Status status = getStatus(log);
                            if (paramValue.equals(status.toString()))
                            {
                                switch (getField)
                                {
                                    case "ip":
                                        result.add(getIp(log));
                                        break;
                                    case "user":
                                        result.add(getUser(log));
                                        break;
                                    case "date":
                                        result.add(getDateLog(log));
                                        break;
                                    case "event":
                                        result.add(getEvent(log));
                                        break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }
}
