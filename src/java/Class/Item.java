package Class;

import java.util.Date;


public class Item
{
    private String location;
    private int count;
    private int min;
    private int avg;
    private int max;
    private String time;

    public Item(String location, String count, String min, String avg, String max, String time)
    {
        this.location = location;
        this.count = Integer.parseInt(count);
        this.min = Integer.parseInt(min);
        this.avg = Integer.parseInt(avg);
        this.max = Integer.parseInt(max);
        this.time = time;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getMin()
    {
        return min;
    }

    public void setMin(int min)
    {
        this.min = min;
    }

    public int getAvg()
    {
        return avg;
    }

    public void setAvg(int avg)
    {
        this.avg = avg;
    }

    public int getMax()
    {
        return max;
    }

    public void setMax(int max)
    {
        this.max = max;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    
}
