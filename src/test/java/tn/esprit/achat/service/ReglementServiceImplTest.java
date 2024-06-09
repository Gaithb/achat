package tn.esprit.achat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.ReglementRepository;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReglementServiceImplTest {

	@InjectMocks
	private ReglementServiceImpl reglementService;

	@Mock
	private FactureRepository factureRepository;

	@Mock
	private ReglementRepository reglementRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Order(5)
	public void testRetrieveAllReglements() {
		// Prepare
		List<Reglement> reglements = new ArrayList<>();
		reglements.add(new Reglement());
		when(reglementRepository.findAll()).thenReturn(reglements);

		// Test
		List<Reglement> result = reglementService.retrieveAllReglements();

		// Verify
		assertEquals(1, result.size());
	}

	@Test
	@Order(1)
	public void testAddReglement() {
		// Prepare
		Reglement reglement = new Reglement();

		// Test
		Reglement result = reglementService.addReglement(reglement);

		// Verify
		verify(reglementRepository, times(1)).save(reglement);
		assertEquals(reglement, result);
	}

	@Test
	@Order(2)
	public void testRetrieveReglement() {
		// Prepare
		Long id = 1L;
		Reglement reglement = new Reglement();
		when(reglementRepository.findById(id)).thenReturn(Optional.of(reglement));

		// Test
		Reglement result = reglementService.retrieveReglement(id);

		// Verify
		assertEquals(reglement, result);
	}

	@Test
	@Order(4)
	public void testRetrieveReglementByFacture() {
		// Prepare
		Long idFacture = 1L;
		List<Reglement> reglements = new ArrayList<>();
		reglements.add(new Reglement());

		// Test
		List<Reglement> result = reglementService.retrieveReglementByFacture(idFacture);

		// Verify
		assertEquals(1, result.size());
	}

	@Test
	@Order(3)
	public void testGetChiffreAffaireEntreDeuxDate() {
		// Prepare
		Date startDate = new Date();
		Date endDate = new Date();
		float expectedChiffreAffaire = 1000.0f;

		// Test
		float result = reglementService.getChiffreAffaireEntreDeuxDate(startDate, endDate);

		// Verify
		assertEquals(expectedChiffreAffaire, result);
	}
}
