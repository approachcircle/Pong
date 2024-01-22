package net.approachcircle.game.backend;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static boolean initialised = false;
    private static String path;
    private static StringBuilder logName;
    private static PrintStream outStream = System.out;
    private static PrintStream errStream = System.err;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public static void initialise(String name) {
        if (initialised) return;
        path = String.format("%s\\%s\\logs\\", System.getenv("APPDATA"), name);
        logName = new StringBuilder(dateFormat.format(new Date()));
        initDir();
        initFile();
        initialised = true;
    }

    public static void log(LogLevel level, Object message) {
        if (!initialised) {
            initialise("Application");
            warn("the logger has not been initialised yet, ensure you do so before use");
        }
        String time = timeFormat.format(new Date());
        String log = String.format("%s [%s]: %s%n", time, level, message);
        if (level == LogLevel.Error) {
            errStream.append(log);
        } else {
            outStream.append(log);
        }
    }

    public static void info(Object message) {
        log(LogLevel.Info, message);
    }

    public static void warn(Object message) {
        log(LogLevel.Warn, message);
    }

    public static void error(Object message) {
        log(LogLevel.Error, message);
    }

    private static void initDir() {
        if (!new File(path).exists()) {
            if (!new File(path).mkdirs()) {
                throw new RuntimeException("failed to create directory for logging in " + path);
            }
        }
    }

    private static void initFile() {
        boolean exists;
        int i = 1;
        exists = !createNewFileElseFallback(String.valueOf(logName));
        for (;;) {
            if (exists) {
                if (String.valueOf(logName).split("-").length > 3) {
                    for (int j = 0; j < String.valueOf(i - 1).length(); j++) {
                        logName.deleteCharAt(logName.length() - 1);
                    }
                    logName.deleteCharAt(logName.length() - 1);
                }
                logName.append("-").append(i);
                exists = !createNewFileElseFallback(String.valueOf(logName));
                i++;
                continue;
            }
            // found a filename that doesn't already exist, so we're good to go
            PrintStream ps;
            try {
                ps = new PrintStream(path + logName + ".log");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            outStream = ps;
            errStream = ps;
            break;
        }
    }

    private static boolean createNewFileElseFallback(String logName) {
        try {
            return new File(path + logName + ".log").createNewFile();
        } catch (IOException e) {
            errStream.println("an error occurred creating a new log file, redirecting logs to standard out/err");
        }
        return false;
    }
}

