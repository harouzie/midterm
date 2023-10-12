package tdtu.fit.hrz.midterm.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class RandomDateGenerator {
    private final static Random random = new Random();
    private final static Calendar calendar = Calendar.getInstance();
    private final static Date today = calendar.getTime();
    static Date startOfYear;
    static Date getRandomDate(){
        // Set the start date (beginning of the year)
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        startOfYear = calendar.getTime();

        // Calculate the time difference in milliseconds
        long timeDifference = today.getTime() - startOfYear.getTime();

        // Generate a random time value within the time difference
        long randomTime = (long) (random.nextDouble() * timeDifference);

        return new Date(startOfYear.getTime() + randomTime);
    }
}
