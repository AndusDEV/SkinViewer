package pl.andus.SkinViewer.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public Logger() {

    }

    public void info(String input) {
        String prefix_info = "[INFO] ";
        System.out.println(prefix_info + getTimeAndDate() + ": " + input);
    }

    public void warning(String input) {
        String prefix_warning = "[WARNING] ";
        System.out.println(prefix_warning + getTimeAndDate() + ": " + input);
    }

    public void error(String input) {
        String prefix_error = "[ERROR] ";
        System.out.println(prefix_error + getTimeAndDate() + ": " + input);
    }

    private String getTimeAndDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
