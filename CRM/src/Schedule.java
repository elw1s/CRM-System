package src;
import java.util.Date;
import java.util.Objects;

/**
 * The type Schedule.
 */
public class Schedule implements Comparable<Schedule> {

    private Date date;
    private String process;


    /**
     * Instantiates a new Schedule.
     *
     * @param date    the date
     * @param process the process
     */
    public Schedule(Date date, String process) {
        this.date = date;
        this.process = process;
    }

    @Override
    public int compareTo(Schedule o) {
        if(date.compareTo(o.date) == 0)
            return 0;
        if(date.compareTo(o.date) > 0)
            return 1;
        else
            return -1;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return date.equals(schedule.getDate()) && process.equals(schedule.getProcess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, process);
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets process.
     *
     * @return the process
     */
    public String getProcess() {
        return process;
    }

    /**
     * Sets process.
     *
     * @param process the process
     */
    public void setProcess(String process) {
        this.process = process;
    }


}
