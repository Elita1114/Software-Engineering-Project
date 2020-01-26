package common;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.Calendar;

public class MonthlyTimer { 
    // What to do
    private final Runnable whatToDo;

    // when 
    private final int dayOfMonth;
    private final int hourOfDay;

    // The current timer
    private Timer current = new Timer();//to avoid NPE

    public void cancelCurrent() { 
        current.cancel();// cancel this execution;
        current.purge(); // removes the timertask so it can be gc'ed
    }

    // create a new instance
    public static MonthlyTimer schedule( Runnable runnable, int dayOfMonth, int hourOfDay ) { 
        return new MonthlyTimer( runnable, dayOfMonth, hourOfDay );
    }

    private MonthlyTimer(Runnable runnable, int day, int hour ) { 
        this.whatToDo = runnable;
        this.dayOfMonth = day;
        this.hourOfDay = hour;
        schedule();
    }
    // Schedules the task for execution on next month. 
    private void schedule() { 
        // Do you mean like this?
        // cancelCurrent();
        current = new Timer(); // assigning a new instance
        // will allow the previous Timer to be gc'ed
        System.out.println("in schedule ");
        current.schedule( new TimerTask() { 
            public void run() { 
                try { 
                    whatToDo.run();
                } finally { 
                    schedule();// schedule for the next month
                }
            }
        } , nextDate() );           
    }
    // Do the next date stuff
    private Date nextDate() { 
    	System.out.println("getting next date");
        Calendar runDate = Calendar.getInstance();
        runDate.set(Calendar.DAY_OF_MONTH, this.dayOfMonth);
        runDate.set(Calendar.HOUR_OF_DAY, this.hourOfDay);
        runDate.set(Calendar.MINUTE, 0);
        runDate.set(Calendar.SECOND, 0);
        runDate.add(runDate.MONTH, 1);//set to next month

        System.out.println("The next run date is "+ runDate.getTime());

        return runDate.getTime();
    }
}