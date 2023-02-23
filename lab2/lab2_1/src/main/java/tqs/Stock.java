package tqs;

public class Stock {
    private String label;
    private int quantity;

    public Stock(String label, int quantity){
        this.label = label;
        this.quantity = quantity;
    }

    public String getLabel(){
        return this.label;
    }

    public void setlabel(String newlabel){
        this.label = newlabel;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int newQuantity){
        this.quantity = newQuantity;
    }

}