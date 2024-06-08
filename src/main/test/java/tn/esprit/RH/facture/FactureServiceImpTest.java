package tn.esprit.rh.achat.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.DetailFacture;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.repositories.DetailFactureRepository;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;

@ExtendWith(MockitoExtension.class)
public class FactureServiceImplTest {

    @Mock
    private FactureRepository factureRepository;

    @Mock
    private OperateurRepository operateurRepository;

    @Mock
    private DetailFactureRepository detailFactureRepository;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private ReglementServiceImpl reglementService;

    @InjectMocks
    private FactureServiceImpl factureService;

    private Facture facture;
    private Fournisseur fournisseur;
    private DetailFacture detailFacture;

    @BeforeEach
    public void setup() {
        fournisseur = new Fournisseur();
        fournisseur.setIdFournisseur(1L);
        fournisseur.setFactures(new HashSet<>());

        detailFacture = new DetailFacture();
        detailFacture.setQteCommandee(5);
        detailFacture.setPourcentageRemise(10);

        facture = new Facture();
        facture.setIdFacture(1L);
        facture.setMontantFacture(100);
        facture.setMontantRemise(10);
        facture.setArchivee(false);
        facture.setDetailsFacture(new HashSet<>(Arrays.asList(detailFacture)));
        facture.setFournisseur(fournisseur);

        fournisseur.getFactures().add(facture);
    }

    @Test
    public void testRetrieveAllFactures() {
        when(factureRepository.findAll()).thenReturn(Arrays.asList(facture));

        List<Facture> factures = factureService.retrieveAllFactures();
        assert(factures.size() == 1);
        verify(factureRepository, times(1)).findAll();
    }

    @Test
    public void testAddFacture() {
        when(factureRepository.save(any(Facture.class))).thenReturn(facture);

        Facture savedFacture = factureService.addFacture(facture);
        assert(savedFacture.getIdFacture().equals(facture.getIdFacture()));
        verify(factureRepository, times(1)).save(facture);
    }

    @Test
    public void testCancelFacture() {
        when(factureRepository.findById(anyLong())).thenReturn(Optional.of(facture));

        factureService.cancelFacture(facture.getIdFacture());
        assert(facture.getArchivee());
        verify(factureRepository, times(1)).save(facture);
        verify(factureRepository, times(1)).updateFacture(facture.getIdFacture());
    }

    @Test
    public void testRetrieveFacture() {
        when(factureRepository.findById(anyLong())).thenReturn(Optional.of(facture));

        Facture retrievedFacture = factureService.retrieveFacture(facture.getIdFacture());
        assert(retrievedFacture != null);
        assert(retrievedFacture.getIdFacture().equals(facture.getIdFacture()));
        verify(factureRepository, times(1)).findById(facture.getIdFacture());
    }

    @Test
    public void testGetFacturesByFournisseur() {
        when(fournisseurRepository.findById(anyLong())).thenReturn(Optional.of(fournisseur));

        List<Facture> factures = factureService.getFacturesByFournisseur(fournisseur.getIdFournisseur());
        assert(factures.size() == 1);
        verify(fournisseurRepository, times(1)).findById(fournisseur.getIdFournisseur());
    }

    @Test
    public void testAssignOperateurToFacture() {
        // Setup required mocks and data for this test
        // This will vary based on the actual implementation of Operateur and its repository
    }

    @Test
    public void testPourcentageRecouvrement() {
        Date startDate = new Date();
        Date endDate = new Date();
        when(factureRepository.getTotalFacturesEntreDeuxDates(any(Date.class), any(Date.class))).thenReturn(1000f);
        when(reglementService.getChiffreAffaireEntreDeuxDate(any(Date.class), any(Date.class))).thenReturn(800f);

        float pourcentage = factureService.pourcentageRecouvrement(startDate, endDate);
        assert(pourcentage == 80.0f);
        verify(factureRepository, times(1)).getTotalFacturesEntreDeuxDates(startDate, endDate);
        verify(reglementService, times(1)).getChiffreAffaireEntreDeuxDate(startDate, endDate);
    }
}
