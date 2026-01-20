package java_concurrency;

import java.util.Timer;
import java.util.TimerTask;

public class TimerClass {
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();

        /* Bad task */
        TimerTask badTask = new TimerTask() {
            @Override
            public void run() {
                while(true);
            }
        };

        /* Good Task */
        TimerTask goodTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("This is good task");
            }
        };

        timer.schedule(badTask, 100);
        timer.schedule(goodTask, 500);


        // By three seconds, both tasks are expected to have launched
        Thread.sleep(3000);

    }
}
