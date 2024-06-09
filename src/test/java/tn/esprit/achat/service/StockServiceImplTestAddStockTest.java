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

@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTestAddStockTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImplTest stockService;

    @Test
    public void testAddStock() {
        // Given
        Stock newStock = new Stock();
        newStock.setLibelleStock("Test Stock");

        when(stockRepository.save(any(Stock.class))).thenReturn(newStock);

        // When
        Stock addedStock = stockService.addStock(newStock);

        // Then
        assertEquals(newStock.getLibelleStock(), addedStock.getLibelleStock());
    }
}
