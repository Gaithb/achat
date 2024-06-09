package tn.esprit.achat.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.achat.service.StockServiceImplTest;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;

import java.util.List;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTestRetrieveAllStocksTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImplTest stockService;

    @Test
    public void testRetrieveAllStocks() {
        // Given
        List<Stock> mockStocks = new ArrayList<>();
        mockStocks.add(new Stock());
        mockStocks.add(new Stock());

        when(stockRepository.findAll()).thenReturn(mockStocks);

        // When
        List<Stock> retrievedStocks = stockService.retrieveAllStocks();

        // Then
        assertEquals(mockStocks.size(), retrievedStocks.size());
    }
}
