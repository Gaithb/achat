package tn.esprit.rh.achat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.rh.achat.entities.*;
import tn.esprit.rh.achat.repositories.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class FactureServiceImpl implements IFactureService {

	@Autowired
	private FactureRepository factureRepository;
	@Autowired
	private OperateurRepository operateurRepository;
	@Autowired
	private DetailFactureRepository detailFactureRepository;
	@Autowired
	private FournisseurRepository fournisseurRepository;
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private ReglementServiceImpl reglementService;

	@Override
	public List<Facture> retrieveAllFactures() {
		List<Facture> factures = factureRepository.findAll();
		for (Facture facture : factures) {
			log.info(" facture : " + facture);
		}
		return factures;
	}

	@Override
	public Facture addFacture(Facture f) {
		return factureRepository.save(f);
	}

	private Facture addDetailsFacture(Facture f, Set<DetailFacture> detailsFacture) {
		float montantFacture = 0;
		float montantRemise = 0;
		for (DetailFacture detail : detailsFacture) {
			Produit produit = produitRepository.findById(detail.getProduit().getIdProduit()).orElse(null);
			if (produit != null) {
				float prixTotalDetail = detail.getQteCommandee() * produit.getPrix();
				float montantRemiseDetail = (prixTotalDetail * detail.getPourcentageRemise()) / 100;
				float prixTotalDetailRemise = prixTotalDetail - montantRemiseDetail;
				detail.setMontantRemise(montantRemiseDetail);
				detail.setPrixTotalDetail(prixTotalDetailRemise);
				montantFacture += prixTotalDetailRemise;
				montantRemise += montantRemiseDetail;
				detailFactureRepository.save(detail);
			}
		}
		f.setMontantFacture(montantFacture);
		f.setMontantRemise(montantRemise);
		return f;
	}

	@Override
	public void cancelFacture(Long factureId) {
		Facture facture = factureRepository.findById(factureId).orElse(null);
		if (facture != null) {
			facture.setArchivee(true);
			factureRepository.save(facture);
			factureRepository.updateFacture(factureId);
		}
	}

	@Override
	public Facture retrieveFacture(Long factureId) {
		return factureRepository.findById(factureId).orElse(null);
	}

	@Override
	public List<Facture> getFacturesByFournisseur(Long idFournisseur) {
		Fournisseur fournisseur = fournisseurRepository.findById(idFournisseur).orElse(null);
		if (fournisseur != null) {
			return (List<Facture>) fournisseur.getFactures();
		}
		return null;
	}

	@Override
	public void assignOperateurToFacture(Long idOperateur, Long idFacture) {
		Facture facture = factureRepository.findById(idFacture).orElse(null);
		Operateur operateur = operateurRepository.findById(idOperateur).orElse(null);
		if (facture != null && operateur != null) {
			operateur.getFactures().add(facture);
			operateurRepository.save(operateur);
		}
	}

	@Override
	public float pourcentageRecouvrement(Date startDate, Date endDate) {
		float totalFacturesEntreDeuxDates = factureRepository.getTotalFacturesEntreDeuxDates(startDate, endDate);
		float totalRecouvrementEntreDeuxDates = reglementService.getChiffreAffaireEntreDeuxDate(startDate, endDate);
		if (totalFacturesEntreDeuxDates != 0) {
			return (totalRecouvrementEntreDeuxDates / totalFacturesEntreDeuxDates) * 100;
		}
		return 0;
	}
}
