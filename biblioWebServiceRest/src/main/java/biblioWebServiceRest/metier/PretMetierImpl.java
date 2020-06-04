package biblioWebServiceRest.metier;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.configurations.ApplicationPropertiesConfiguration;
import biblioWebServiceRest.criteria.PretCriteria;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IUserRepository;

import biblioWebServiceRest.dao.specs.PretSpecification;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.InternalServerErrorException;
import biblioWebServiceRest.exceptions.NotFoundException;


@Service
@Transactional
public class PretMetierImpl implements IPretMetier {
	
	@Autowired
	private IPretRepository pretRepository;
	@Autowired
	private ILivreRepository livreRepository;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	ApplicationPropertiesConfiguration appProperties;
	
	
	/**
	 * CRUD : CREATE Créer le prêt de l'exemplaire disponible d'un livre
	 * Exception levée si aucun titre ou aucun nom utilisateur correspondant ou si nombre d'exemplaire nul
	 * @param titre
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pret createPret(String titre, String username) throws Exception {
		Pret pret = new Pret();
		
		Optional<Livre> livre = livreRepository.findByTitre(titre);
		if(!livre.isPresent()) 
			throw new NotFoundException ("Aucun titre de livre ne correspond à votre demande");
		if(livre.get().getNbExemplairesDisponibles() ==0) throw new InternalServerErrorException ("Il n'y a plus d'exemplaire disponible de ce livre");
		pret.setLivre(livre.get());
		pret.getLivre().setNbExemplairesDisponibles(pret.getLivre().getNbExemplairesDisponibles()-1);
		
		Optional<User> user = userRepository.findByUsername(username);
		if(!user.isPresent()) 
			throw new NotFoundException ("Aucun nom d'emprunteur ne correspond à votre demande ");
		pret.setUser(user.get());
		
		LocalDate datePret = LocalDate.now();
		pret.setDatePret(datePret);
		LocalDate dateRetourPrevue = datePret.plusDays(appProperties.getDureePret());
		pret.setDateRetourPrevue(dateRetourPrevue);
		pret.setPretStatut(PretStatut.ENCOURS);
		
		return pretRepository.save(pret);
	
	}
	
	/**
	 * CRUD : UPDATE prolonger la durée d'un prêt encours ou échu 
	 * Cette méthode permet de prolonger la durée d'un pret et de mettre à jour son statut
	 * La durée de prolongation est une constante déclarée dans application.properties et gérée dans le package Configurations 
	 * Mise à jour des prets ENCOURS au statut ECHU selon la date de la demande
	 * Exceptions gérées si le statut du prêt n'est pas ENCOURS 
	 * @param numPret
	 * @return
	 */
	@Override
	public Pret prolongerPret(Long numPret) throws Exception {
		Optional<Pret> pret = pretRepository.findById(numPret);
		if(!pret.isPresent()) 
			throw new NotFoundException ("Aucun prêt enregistré ne correspond à votre demande");
		
		LocalDate dateDemandeProlongation = LocalDate.now();
		if(!pret.get().getPretStatut().equals(PretStatut.ENCOURS)) 
			throw new InternalServerErrorException ("Le statut de ce pret de livre ne permet pas sa prolongation");
		
		LocalDate datePretProlonge = pret.get().getDateRetourPrevue();
		pret.get().setDatePret(datePretProlonge);
		LocalDate dateRetourPrevuePretProlonge = datePretProlonge.plusDays(appProperties.getDureeProlongation());
		pret.get().setDateRetourPrevue(dateRetourPrevuePretProlonge);
		
		pret.get().setPretStatut(PretStatut.PROLONGE);
		
		return pretRepository.save(pret.get());
	}
	
	/**
	 * CRUD : UPDATE clôturer un prêt à la date de transaction
	 * Le pret passe en statut CLOTURE mais n'est pas supprimé en base de données
	 * @param numPret
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pret cloturerPret(Long numPret) throws Exception {
		Optional<Pret> pretACloturer = pretRepository.findById(numPret);
		if(!pretACloturer.isPresent()) 
			throw new NotFoundException ("Aucun prêt enregistré ne correspond à votre demande");
		
		pretACloturer.get().setDateRetourEffectif(LocalDate.now());
		pretACloturer.get().setPretStatut(PretStatut.CLOTURE);
		
		Integer nbExemplairesDisponiblesAvantTransaction = pretACloturer.get().getLivre().getNbExemplairesDisponibles();
		pretACloturer.get().getLivre().setNbExemplairesDisponibles(nbExemplairesDisponiblesAvantTransaction + 1);
		
		return pretRepository.save(pretACloturer.get());
		
	}
	
	/**
	 * Recherche multi-critères des prets enregistrés 
	 * @param pretCriteria
	 * @return
	 */
	@Override
	public List<Pret> searchByCriteria(PretCriteria pretCriteria) {
		Specification<Pret> pretSpecification = new PretSpecification(pretCriteria);
		return pretRepository.findAll(pretSpecification);
	}

	

	
}
