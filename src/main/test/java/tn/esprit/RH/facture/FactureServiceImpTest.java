import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import tn.esprit.rh.achat.entities.DetailFacture;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.repositories.DetailFactureRepository;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.services.FactureServiceImpl;

public class FactureServiceImpTest {
    
    @Mock
    private FactureRepository factureRepository;

    @Mock
    private DetailFactureRepository detailFactureRepository;

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private FactureServiceImpl factureService;

    public FactureServiceImplMockTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllFactures() {
        Facture facture1 = new Facture(/* initialize with necessary fields */);
        Facture facture2 = new Facture(/* initialize with necessary fields */);
        List<Facture> expectedFactures = Arrays.asList(facture1, facture2);
        when(factureRepository.findAll()).thenReturn(expectedFactures);

        List<Facture> actualFactures = factureService.retrieveAllFactures();

        assertEquals(expectedFactures, actualFactures);
        verify(factureRepository, times(1)).findAll();
    }

    @Test
    public void testAddFacture() {
        Facture facture = new Facture(/* initialize with necessary fields */);
        when(factureRepository.save(facture)).thenReturn(facture);

        Facture result = factureService.addFacture(facture);

        assertNotNull(result);
        verify(factureRepository, times(1)).save(facture);
    }

    @Test
    public void testCancelFacture() {
        Long factureId = 1L;
        Facture facture = new Facture(/* initialize with necessary fields */);
        when(factureRepository.findById(factureId)).thenReturn(java.util.Optional.ofNullable(facture));
        doNothing().when(factureRepository).updateFacture(factureId);

        factureService.cancelFacture(factureId);

        verify(factureRepository, times(1)).findById(factureId);
        verify(factureRepository, times(1)).updateFacture(factureId);
    }
}
