package Class;


public class Item
{
    private String location;
    private int count;
    private int price;

    public Item(String location, String count, String price)
    {
        this.location = location;
        this.count = Integer.parseInt(count);
        this.price = Integer.parseInt(price);
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

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }
    
    
}
