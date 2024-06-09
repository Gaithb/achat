package tn.esprit.achat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;
import tn.esprit.rh.achat.services.OperateurServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OperateurServiceImplTest {

	@InjectMocks
	private OperateurServiceImpl operateurService;

	@Mock
	private OperateurRepository operateurRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@Order(4)
	public void testRetrieveAllOperateurs() {
		List<Operateur> operateurs = new ArrayList<>();
		operateurs.add(new Operateur());
		when(operateurRepository.findAll()).thenReturn(operateurs);
		assertEquals(1, operateurService.retrieveAllOperateurs().size());
	}

	@Test
	@Order(1)
	public void testAddOperateur() {
		Operateur operateur = new Operateur();
		when(operateurRepository.save(operateur)).thenReturn(operateur);
		assertEquals(operateur, operateurService.addOperateur(operateur));
	}

	@Test
	@Order(5)
	public void testDeleteOperateur() {
		Long id = 1L;
		operateurService.deleteOperateur(id);
		verify(operateurRepository, times(1)).deleteById(id);
	}

	@Test
	@Order(3)
	public void testUpdateOperateur() {
		Operateur operateur = new Operateur();
		when(operateurRepository.save(operateur)).thenReturn(operateur);
		assertEquals(operateur, operateurService.updateOperateur(operateur));
	}

	@Test
	@Order(2)
	public void testRetrieveOperateur() {
		Long id = 1L;
		Operateur operateur = new Operateur();
		when(operateurRepository.findById(id)).thenReturn(java.util.Optional.of(operateur));
		assertEquals(operateur, operateurService.retrieveOperateur(id));
	}
}
