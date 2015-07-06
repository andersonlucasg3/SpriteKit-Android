package br.com.insanitech.spritekit.logger;

import java.util.logging.Level;

/**
 * Created by anderson on 6/29/15.
 */
public class Logger {
    public static boolean DEBUG_MODE = true;

    public static void log(String key, String content) {
        if (DEBUG_MODE) {
            getLogger().log(Level.INFO, key + " - " + content);
        }
    }

    public static void log(String content) {
        if (DEBUG_MODE) {
            getLogger().log(Level.INFO, content);
        }
    }

    private static java.util.logging.Logger getLogger() {
        final Throwable t = new Throwable();
        final StackTraceElement methodCaller = t.getStackTrace()[1];
        final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(methodCaller.getClassName());
        logger.setLevel(Level.ALL);
        return logger;
    }
}
