package com.big12.log_parser;

import java.nio.file.Paths;

public class Solution
{
    public static void main(String[] args)
    {
        LogParser logParser = new LogParser(Paths.get("src/com/big12/log_parser/logs/"));

        //IP
//        System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));
//        System.out.println(logParser.getIPsForStatus(Status.FAILED, null, null));
//        System.out.println(logParser.getIPsForUser("Amigo", null, null));
//        System.out.println(logParser.getIPsForEvent(Event.LOGIN, null, null));
//        System.out.println(logParser.getUniqueIPs(null, null));


        //USER
//        System.out.println(logParser.getAllUsers());
//        System.out.println(logParser.getNumberOfUsers(null, null));
//        System.out.println(logParser.getNumberOfUserEvents("Eduard Petrovich Morozko", null, null));
//        System.out.println(logParser.getUsersForIP("127.0.0.1", null, null));
//        System.out.println(logParser.getLoggedUsers(null, null));
//        System.out.println(logParser.getDownloadedPluginUsers(null, null));
//        System.out.println(logParser.getWroteMessageUsers(null, null));
//        System.out.println(logParser.getSolvedTaskUsers(null, null));
//        System.out.println(logParser.getDoneTaskUsers(null, null));
//        System.out.println(logParser.getSolvedTaskUsers(null, null, 1));
//        System.out.println(logParser.getDoneTaskUsers(null, null, 48));


        //DATE
//        System.out.println(logParser.getDatesForUserAndEvent("Eduard Petrovich Morozko", Event.WRITE_MESSAGE, null, null));
//        System.out.println(logParser.getDatesWhenSomethingFailed(null, null));
//        System.out.println(logParser.getDatesWhenErrorHappened(null, null));
//        System.out.println(logParser.getDateWhenUserLoggedFirstTime("Vasya Pupkin", null, null));
//        System.out.println(logParser.getDateWhenUserSolvedTask("Vasya Pupkin", 18, null, null));
//        System.out.println(logParser.getDateWhenUserDoneTask("Eduard Petrovich Morozko", 48, null, null));
//        System.out.println(logParser.getDatesWhenUserWroteMessage("Eduard Petrovich Morozko", null, null));
//        System.out.println(logParser.getDatesWhenUserDownloadedPlugin("Eduard Petrovich Morozko", null, null));


        //EVENT
//        System.out.println(logParser.getNumberOfAllEvents(null, null));
//        System.out.println(logParser.getAllEvents(null, null));
//        System.out.println(logParser.getEventsForIP("127.0.0.1", null, null));
//        System.out.println(logParser.getEventsForUser("Amigo", null, null));
//        System.out.println(logParser.getFailedEvents(null, null));
//        System.out.println(logParser.getErrorEvents(null, null));
//        System.out.println(logParser.getNumberOfAttemptToSolveTask(18, null, null));
//        System.out.println(logParser.getNumberOfSuccessfulAttemptToSolveTask(18, null, null));
//        System.out.println(logParser.getAllSolvedTasksAndTheirNumber(null, null));
//        System.out.println(logParser.getAllDoneTasksAndTheirNumber(null, null));


        //МЕТОДЫ EXECUTE
//        System.out.println(logParser.execute("get ip"));
//        System.out.println(logParser.execute("get user"));
//        System.out.println(logParser.execute("get date"));
//        System.out.println(logParser.execute("get event"));
//
//
//        System.out.println(logParser.execute("get ip for user = \"Vasya\""));
//        System.out.println(logParser.execute("get user for event = \"DONE_TASK\""));
//        System.out.println(logParser.execute("get event for date = \"30.01.2014 12:56:22\""));
//        System.out.println(logParser.execute("get date for event = \"LOGIN\""));
//
//
//        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and date between " +
//                "\"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));
    }
}
