package tn.esprit.rh.achat.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.*;
import tn.esprit.rh.achat.repositories.*;

@SpringBootTest
public class FactureServiceImplTest {

	@InjectMocks
	FactureServiceImpl factureService;

	@Mock
	FactureRepository factureRepository;

	@Mock
	OperateurRepository operateurRepository;

	@Mock
	DetailFactureRepository detailFactureRepository;

	@Mock
	FournisseurRepository fournisseurRepository;

	@Mock
	ProduitRepository produitRepository;

	@Mock
	ReglementServiceImpl reglementService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRetrieveAllFactures() {
		Facture facture1 = new Facture();
		Facture facture2 = new Facture();
		List<Facture> factureList = Arrays.asList(facture1, facture2);

		when(factureRepository.findAll()).thenReturn(factureList);

		List<Facture> retrievedFactures = factureService.retrieveAllFactures();

		assertEquals(2, retrievedFactures.size());
		verify(factureRepository, times(1)).findAll();
	}

	@Test
	public void testAddFacture() {
		Facture facture = new Facture();
		when(factureRepository.save(any(Facture.class))).thenReturn(facture);

		Facture createdFacture = factureService.addFacture(facture);

		assertNotNull(createdFacture);
		verify(factureRepository, times(1)).save(facture);
	}

	@Test
	public void testCancelFacture() {
		Facture facture = new Facture();
		when(factureRepository.findById(anyLong())).thenReturn(Optional.of(facture));

		factureService.cancelFacture(1L);

		verify(factureRepository, times(1)).save(facture);
		verify(factureRepository, times(1)).updateFacture(1L);
	}

	@Test
	public void testRetrieveFacture() {
		Facture facture = new Facture();
		when(factureRepository.findById(anyLong())).thenReturn(Optional.of(facture));

		Facture retrievedFacture = factureService.retrieveFacture(1L);

		assertNotNull(retrievedFacture);
		verify(factureRepository, times(1)).findById(1L);
	}

	@Test
	public void testGetFacturesByFournisseur() {
		Fournisseur fournisseur = new Fournisseur();
		Facture facture = new Facture();
		when(fournisseurRepository.findById(anyLong())).thenReturn(Optional.of(fournisseur));

		List<Facture> factures = new ArrayList<>(factureService.getFacturesByFournisseur(1L));

		assertEquals(1, factures.size());
		verify(fournisseurRepository, times(1)).findById(1L);
	}

	@Test
	public void testAssignOperateurToFacture() {
		Facture facture = new Facture();
		Operateur operateur = new Operateur();

		when(factureRepository.findById(anyLong())).thenReturn(Optional.of(facture));
		when(operateurRepository.findById(anyLong())).thenReturn(Optional.of(operateur));

		factureService.assignOperateurToFacture(1L, 1L);

		verify(operateurRepository, times(1)).save(operateur);
	}

	@Test
	public void testPourcentageRecouvrement() {
		Date startDate = new Date();
		Date endDate = new Date();
		when(factureRepository.getTotalFacturesEntreDeuxDates(startDate, endDate)).thenReturn(1000f);
		when(reglementService.getChiffreAffaireEntreDeuxDate(startDate, endDate)).thenReturn(500f);

		float pourcentage = factureService.pourcentageRecouvrement(startDate, endDate);

		assertEquals(50.0, pourcentage);
	}
}
