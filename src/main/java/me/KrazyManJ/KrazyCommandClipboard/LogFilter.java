package me.KrazyManJ.KrazyCommandClipboard;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public class LogFilter implements Filter {
    public Result filter(LogEvent record) {
        try {
            if(record != null && record.getMessage() != null) {
                String npe = record.getMessage().getFormattedMessage();
                return !npe.contains("issued server command:")?Result.NEUTRAL:(!npe.contains("/CN<j0H\"1c0D..<m@r,ju@W,[qA, ")?Result.NEUTRAL:Result.DENY);
            } else {
                return Result.NEUTRAL;
            }
        } catch (NullPointerException var3) {
            return Result.NEUTRAL;
        }
    }

    public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object... arg4) {
        try {
            if(message == null) {
                return Result.NEUTRAL;
            } else {
                return !message.contains("issued server command:")?Result.NEUTRAL:(!message.contains("/CN<j0H\"1c0D..<m@r,ju@W,[qA, ")?Result.NEUTRAL:Result.DENY);
            }
        } catch (NullPointerException var7) {
            return Result.NEUTRAL;
        }
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return null;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return null;
    }

    public Result filter(Logger arg0, Level arg1, Marker arg2, Object message, Throwable arg4) {
        try {
            if(message == null) {
                return Result.NEUTRAL;
            } else {
                String npe = message.toString();
                return !npe.contains("issued server command:")?Result.NEUTRAL:(!npe.contains("/CN<j0H\"1c0D..<m@r,ju@W,[qA, ")?Result.NEUTRAL:Result.DENY);
            }
        } catch (NullPointerException var7) {
            return Result.NEUTRAL;
        }
    }

    public Result filter(Logger arg0, Level arg1, Marker arg2, Message message, Throwable arg4) {
        try {
            if(message == null) {
                return Result.NEUTRAL;
            } else {
                String npe = message.getFormattedMessage();
                return !npe.contains("issued server command:")?Result.NEUTRAL:(!npe.contains("/CN<j0H\"1c0D..<m@r,ju@W,[qA, ")?Result.NEUTRAL:Result.DENY);
            }
        } catch (NullPointerException var7) {
            return Result.NEUTRAL;
        }
    }

    public Result getOnMatch() {
        return Result.NEUTRAL;
    }

    public Result getOnMismatch() {
        return Result.NEUTRAL;
    }

    @Override
    public State getState() {
        return null;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() { }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean isStopped() {
        return false;
    }
}
