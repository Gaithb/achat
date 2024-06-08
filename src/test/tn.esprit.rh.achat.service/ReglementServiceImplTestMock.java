package tn.esprit.rh.achat.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.ReglementRepository;

@RunWith(MockitoJUnitRunner.class)
public class ReglementServiceImplTestMock {

	@Mock
	private FactureRepository factureRepository;

	@Mock
	private ReglementRepository reglementRepository;

	@InjectMocks
	private ReglementServiceImpl reglementService;

	@Before
	public void setUp() {
		// Initialisation des comportements simulés (si nécessaire)
	}

	@Test
	public void testRetrieveAllReglements() {
		// Création d'une liste de paiements simulée
		List<Reglement> reglements = new ArrayList<>();
		reglements.add(new Reglement());
		reglements.add(new Reglement());

		// Simulation du comportement du repository pour retourner la liste simulée
		when(reglementRepository.findAll()).thenReturn(reglements);

		// Appel de la méthode à tester
		List<Reglement> result = reglementService.retrieveAllReglements();

		// Vérification du résultat
		assertEquals(2, result.size());
	}

	// D'autres méthodes de test peuvent être ajoutées ici

}
