package tqs;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StocksPortfolio {
    private IStockmarketService marketService;
    private ArrayList<Stock> stocks = new ArrayList<>();

    public IStockmarketService getMarketService(){
        return this.marketService;
    }

    public void setMarketService(IStockmarketService newMarket){
        this.marketService = newMarket;
    }

    public double getTotalValue(){
        double total = 0;

        for(Stock s : this.stocks)
            total += (marketService.lookUpPrice(s.getLabel()) * s.getQuantity());

        return total;
    }

    public void addStock(Stock newStock){
        this.stocks.add(newStock);
    }


}