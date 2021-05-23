package pl.andus.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private String prefix_info = "[INFO] ";
    private String prefix_warning = "[WARNING] ";
    private String prefix_error = "[ERROR] ";

    public Logger() {

    }

    public void info(String input) {
        System.out.println(prefix_info + getTimeAndDate() + ": " + input);
    }

    public void warning(String input) {
        System.out.println(prefix_warning + getTimeAndDate() + ": " + input);
    }

    public void error(String input) {
        System.out.println(prefix_error + getTimeAndDate() + ": " + input);
    }

    private String getTimeAndDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
