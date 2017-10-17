package br.com.insanitech.spritekit.logger

import java.util.logging.Level

/**
 * Created by anderson on 6/29/15.
 */
object Logger {
    var DEBUG_MODE = true

    fun log(key: String, content: String) {
        if (DEBUG_MODE) {
            logger.log(Level.INFO, key + " - " + content)
        }
    }

    fun log(content: String) {
        if (DEBUG_MODE) {
            logger.log(Level.INFO, content)
        }
    }

    private val logger: java.util.logging.Logger
        get() {
            val t = Throwable()
            val methodCaller = t.stackTrace[1]
            val logger = java.util.logging.Logger.getLogger(methodCaller.className)
            logger.level = Level.ALL
            return logger
        }
}
