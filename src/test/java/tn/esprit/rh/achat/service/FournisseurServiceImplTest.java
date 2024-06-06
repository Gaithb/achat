package tn.esprit.rh.achat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.services.FournisseurServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FournisseurServiceImplTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private DetailFournisseurRepository detailFournisseurRepository;

    @Mock
    private SecteurActiviteRepository secteurActiviteRepository;

    @InjectMocks
    private FournisseurServiceImpl fournisseurService;

    private Fournisseur fournisseur;
    private DetailFournisseur detailFournisseur;

    @BeforeEach
    public void setUp() {
        detailFournisseur = new DetailFournisseur();
        detailFournisseur.setDateDebutCollaboration(new Date());

        fournisseur = new Fournisseur();
        fournisseur.setIdFournisseur(1L);
        fournisseur.setCode("1234");
        fournisseur.setLibelle("Test Fournisseur");
        fournisseur.setCategorieFournisseur(null);
        fournisseur.setDetailFournisseur(detailFournisseur);
    }

    @Test
    public void testRetrieveAllFournisseurs() {
        when(fournisseurRepository.findAll()).thenReturn(Arrays.asList(fournisseur));

        List<Fournisseur> fournisseurs = fournisseurService.retrieveAllFournisseurs();
        assertNotNull(fournisseurs);
        assertEquals(1, fournisseurs.size());
        assertEquals(fournisseur.getIdFournisseur(), fournisseurs.get(0).getIdFournisseur());

        verify(fournisseurRepository, times(1)).findAll();
    }

    @Test
    public void testAddFournisseur() {
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);

        Fournisseur savedFournisseur = fournisseurService.addFournisseur(fournisseur);
        assertNotNull(savedFournisseur);
        assertEquals(fournisseur.getIdFournisseur(), savedFournisseur.getIdFournisseur());

        verify(fournisseurRepository, times(1)).save(fournisseur);
    }


    @Test
    public void testUpdateFournisseur() {
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(fournisseur);
        when(detailFournisseurRepository.save(any(DetailFournisseur.class))).thenReturn(detailFournisseur);

        Fournisseur updatedFournisseur = fournisseurService.updateFournisseur(fournisseur);
        assertNotNull(updatedFournisseur);
        assertEquals(fournisseur.getIdFournisseur(), updatedFournisseur.getIdFournisseur());

        verify(fournisseurRepository, times(1)).save(fournisseur);
        verify(detailFournisseurRepository, times(1)).save(detailFournisseur);
    }

    @Test
    public void testDeleteFournisseur() {
        doNothing().when(fournisseurRepository).deleteById(1L);

        fournisseurService.deleteFournisseur(1L);
        verify(fournisseurRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRetrieveFournisseur() {
        when(fournisseurRepository.findById(1L)).thenReturn(Optional.of(fournisseur));

        Fournisseur retrievedFournisseur = fournisseurService.retrieveFournisseur(1L);
        assertNotNull(retrievedFournisseur);
        assertEquals(fournisseur.getIdFournisseur(), retrievedFournisseur.getIdFournisseur());

        verify(fournisseurRepository, times(1)).findById(1L);
    }

}
