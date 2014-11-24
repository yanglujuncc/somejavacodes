package testThreadPool;

import java.util.Random;


/**
 * @see http://sjsky.iteye.com
 * @author michael sjsky007@gmail.com
 */
public class SimplePrintJob implements Runnable {

    private String jobName;

    /**
     * @param jobName
     */
    public SimplePrintJob(String jobName) {
        this.jobName = jobName;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {
        System.out.println("[ " + jobName + " ] start...");
        int random = 0;
        try {
            Random r = new Random();
            random = r.nextInt(10);
            Thread.sleep(random * 1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[ " + jobName + " ] end with sleep:" + random);
    }
}
