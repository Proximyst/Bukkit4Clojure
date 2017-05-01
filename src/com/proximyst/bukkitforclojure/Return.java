package com.proximyst.bukkitforclojure;

import java.io.PrintStream;
import java.io.PrintWriter;

public class Return extends Exception {
    @Override
    public void printStackTrace() {
    }

    @Override
    public void printStackTrace(PrintStream s) {
    }

    @Override
    public void printStackTrace(PrintWriter s) {
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getLocalizedMessage() {
        return "Returned out of Clojure function.";
    }

    @Override
    public String getMessage() {
        return "Returned out of Clojure function.";
    }

    @Override
    public synchronized Throwable getCause() {
        return null;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return new StackTraceElement[0];
    }
}
