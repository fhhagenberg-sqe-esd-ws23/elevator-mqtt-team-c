package at.fhhagenberg.sqelevator.service;

import java.util.Timer;
import java.util.TimerTask;

public class ScheduleService {
    private final Timer timer = new Timer();

    public void start(TimerTask task, int interval) {

        timer.scheduleAtFixedRate(task, 0, interval);
    }

    public void stop() {
        timer.cancel();
    }
}
