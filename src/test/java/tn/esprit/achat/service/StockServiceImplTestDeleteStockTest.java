package tn.esprit.achat.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.achat.service.StockServiceImplTest;
import tn.esprit.rh.achat.repositories.StockRepository;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTestDeleteStockTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImplTest stockService;

    @Test
    public void testDeleteStock() {
        // Given
        Long stockIdToDelete = 1L;

        // When
        stockService.deleteStock(stockIdToDelete);

        // Then
        verify(stockRepository, times(1)).deleteById(stockIdToDelete);
    }
}
