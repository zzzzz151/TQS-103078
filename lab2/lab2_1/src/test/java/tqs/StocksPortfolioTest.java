package tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StocksPortfolioTest {
    @Mock
    IStockmarketService mockMarket;
    @InjectMocks
    StocksPortfolio portfolio;

    @Test
    void getTotalValueTest() {
        portfolio.addStock(new Stock("Abc", 0));
        portfolio.addStock(new Stock("Def", 1));
        portfolio.addStock(new Stock("ZZZ", 4));
        portfolio.addStock(new Stock("Google", 7));

        Mockito.when(mockMarket.lookUpPrice("Abc")).thenReturn(2.5);
        Mockito.when(mockMarket.lookUpPrice("Def")).thenReturn(15.0);
        Mockito.when(mockMarket.lookUpPrice("ZZZ")).thenReturn(0.5);
        Mockito.when(mockMarket.lookUpPrice("Google")).thenReturn(1.0);

        double correctTotal = 0 * 2.5 + 1 * 15 + 4 * 0.5 + 7 * 1;

        // assertEquals(portfolio.getTotalValue(),correctTotal);
        assertThat(portfolio.getTotalValue(), is(correctTotal));

        // Mock function called 4 times?
        Mockito.verify(mockMarket, Mockito.times(4)).lookUpPrice(Mockito.anyString());
    }
}