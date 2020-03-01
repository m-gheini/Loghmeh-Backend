package IECA.logic;

public class SaleFood extends Food{
    private int oldPrice;
    private int count;

    public int getOldPrice() {
        return oldPrice;
    }

    public int getCount() {
        return count;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public String toString(){
        return "{ "+"\"count\" : " + getCount() +"\",oldPrice\" : " + getOldPrice() +"\",name\" : " + getName() + ", \"description\" : " + getDescription() + ",\"popularity\" : " + getPopularity() + ", \"price\" : " + getPrice() + ", \"image\" : " + getImage() +" } "  ;
    }
}
