/**
 * Classe d'implémentation des méthodes Métier pour l'entité Pret
 */
package biblioWebServiceRest.metier;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.configurations.ApplicationPropertiesConfiguration;
import biblioWebServiceRest.criteria.PretCriteria;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IRoleRepository;
import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.dao.specs.PretSpecification;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.RoleEnum;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.BookNotAvailableException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.mapper.LivreMapper;
import biblioWebServiceRest.mapper.PretMapper;


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
	private IRoleRepository roleRepository;
	@Autowired
	ApplicationPropertiesConfiguration appProperties;
	@Autowired
	PretMapper pretMapper;
	@Autowired
	LivreMapper livreMapper; 
	
	
	
	/**
	 * CRUD : CREATE Créer le prêt de l'exemplaire disponible d'un livre
	 * Exception levée si aucun titre ou aucun nom utilisateur correspondant ou si nombre d'exemplaire nul
	 * @param titre
	 * @param username
	 * @return
	 * @throws BadRequestException 
	 * @throws BookNotAvailableException 
	 * @throws EntityNotFoundException 
	 * @throws MethodNotAllowedException 
	 */
	@Override
	public Pret createPret(PretDTO pretDTO) throws EntityNotFoundException, BookNotAvailableException {
	
	Optional<Livre> livreToRent = livreRepository.findById(pretDTO.getNumLivre());
	if(!livreToRent.isPresent()) 
		throw new EntityNotFoundException ("Aucun enregistrement de livre ne correspond à votre demande");
	if(livreToRent.get().getNbExemplairesDisponibles() <=0) 
		throw new BookNotAvailableException ("Il n'y a plus d'exemplaire disponible de ce livre");
		
	Optional<User> emprunteur = userRepository.findById(pretDTO.getIdUser());
	if(!emprunteur.isPresent()) 
		throw new EntityNotFoundException ("Aucun utilisateur ne correspond à votre identification de l'emprunteur ");
	
		
	Pret newPret = new Pret();
	
	newPret.setLivre(livreToRent.get());
	newPret.getLivre().setNbExemplairesDisponibles(newPret.getLivre().getNbExemplairesDisponibles()-1);
	newPret.setUser(emprunteur.get());
	
	LocalDate datePret = LocalDate.now();
	newPret.setDatePret(datePret);
	LocalDate dateRetourPrevue = datePret.plusDays(appProperties.getDureePret());
	newPret.setDateRetourPrevue(dateRetourPrevue);
	newPret.setPretStatut(PretStatut.ENCOURS);
	
	return pretRepository.save(newPret);
		
	}
	
	/**
	 * CRUD : UPDATE prolonger la durée d'un prêt encours ou échu 
	 * Cette méthode permet de prolonger la durée d'un pret et de mettre à jour son statut
	 * La durée de prolongation est une constante déclarée dans application.properties et gérée dans le package Configurations 
	 * Mise à jour des prets ENCOURS au statut ECHU selon la date de la demande
	 * Exceptions gérées si le statut du prêt n'est pas ENCOURS 
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws BookNotAvailableException 
	 */
	@Override
	public Pret prolongerPret(Long numPret) throws EntityNotFoundException, BookNotAvailableException {
		Optional<Pret> pretAProlonger = pretRepository.findById(numPret);
		if(!pretAProlonger.isPresent()) 
			throw new EntityNotFoundException ("Aucun prêt enregistré ne correspond à votre demande");
		/*
		 * TICKET 2 : empêche de prolonger un prêt dont le statut n'est pas encours ou dont le statut est encours et la date de retour
		 * est inférieure à aujourd'hui
		*/
		if(!pretAProlonger.get().getPretStatut().equals(PretStatut.ENCOURS)) 
			throw new BookNotAvailableException ("PROLONGATION IMPOSSIBLE = Le statut de ce pret de livre ne permet pas sa prolongation");
		
		if(pretAProlonger.get().getPretStatut().equals(PretStatut.ENCOURS)
				&& pretAProlonger.get().getDateRetourPrevue().isBefore(LocalDate.now())) 
			throw new BookNotAvailableException ("PROLONGATION IMPOSSIBLE = La date de retour prévue de ce prêt ne permet pas sa prolongation");
		
		/*
		 * La date de départ d'un prêt prolonge n'est pas la date de la saisie de sa prolongation mais la date d'échéance
		 * de la 1ère période de prêt (que la date de prolongation soit antérieure ou postérieure à cette date de 1ère échéance)
		 * La date initiale du Prêt reste inchangée
		 */
		LocalDate datePretProlonge = pretAProlonger.get().getDateRetourPrevue();
		LocalDate dateRetourPrevuePretProlonge = datePretProlonge.plusDays(appProperties.getDureeProlongation());
		pretAProlonger.get().setDateRetourPrevue(dateRetourPrevuePretProlonge);
		
		pretAProlonger.get().setPretStatut(PretStatut.PROLONGE);
		
		return pretRepository.save(pretAProlonger.get());
		
		
	}
	
	/**
	 * CRUD : UPDATE clôturer un prêt à la date de transaction
	 * Le pret passe en statut CLOTURE mais n'est pas supprimé en base de données
	 * @param numPret
	 * @return
	 * @throws EntityNotFoundException 
	 * @throws Exception
	 */
	@Override
	public Pret cloturerPret(Long numPret) throws EntityNotFoundException {
		Optional<Pret> pretACloturer = pretRepository.findById(numPret);
		if(!pretACloturer.isPresent()) 
			throw new EntityNotFoundException ("Aucun prêt enregistré ne correspond à votre demande");
		
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
	public Page<Pret> searchByCriteria(PretCriteria pretCriteria, Pageable pageable) {
		Specification<Pret> pretSpecification = new PretSpecification(pretCriteria);
		Page<Pret> prets = pretRepository.findAll(pretSpecification, pageable);
		return prets;
	}

	/**
	 * @param pageable
	 * @return
	 */
	@Override
	public List<Pret> searchAndUpdatePretsEchus() {
		List<Pret> pretsEchusListe = new ArrayList<Pret>();
		Optional<List<Pret>> pretsEchus = pretRepository.findAllByDateRetourPrevueBeforeAndNotPretStatut(LocalDate.now(), PretStatut.CLOTURE);
		if(pretsEchus.isPresent()) {
			for (Pret pret : pretsEchus.get()) {
				pret.setPretStatut(PretStatut.ECHU);
				pretsEchusListe.add(pret);
				pretRepository.save(pret);
				}
			}
		return pretsEchusListe; 
	}

	@Override
	public Pret readPret(Long numPret) throws EntityNotFoundException {
		Optional<Pret> searchedPret = pretRepository.findById(numPret);
		if(!searchedPret.isPresent())
			throw new EntityNotFoundException ("Aucun prêt enregistré ne correspond à votre demande");
		return searchedPret.get();
	}
	
	
	
	
}
