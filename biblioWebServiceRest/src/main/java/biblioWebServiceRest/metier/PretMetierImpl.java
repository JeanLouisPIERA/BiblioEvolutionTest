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
import biblioWebServiceRest.exceptions.WrongNumberException;
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
	@Autowired
	private ILivreMetier livreMetier;
	
	
	
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
	
	livreMetier.miseAJourLivres();
	
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
	 * @throws WrongNumberException 
	 */
	@Override
	public Pret prolongerPret(Long numPret) throws EntityNotFoundException, BookNotAvailableException, WrongNumberException {
		Optional<Pret> pretAProlonger = pretRepository.findById(numPret);
		
		if(!pretAProlonger.isPresent()) 
			throw new EntityNotFoundException ("Aucun prêt enregistré ne correspond à votre demande");

		/*
		 * TICKET 2 : empêche de prolonger un prêt dont le statut n'est pas encours ou dont le statut est encours et la date de retour
		 * est inférieure à aujourd'hui
		*/
		
		if(
				!pretAProlonger.get().getPretStatut().equals(PretStatut.ENCOURS) &&
				!pretAProlonger.get().getPretStatut().equals(PretStatut.AECHOIR)	
				) 
			throw new BookNotAvailableException ("Le statut de ce pret de livre ne permet pas sa prolongation");

		
		/*COMMENTAIRE HOTFIX 1.0.1 : Dans la version d'origine, il n'y avait pas de bug 
		 * Un prêt à prolonger ne pouvait pas avoir une date de retour postérieure à la date de traitement
		 * Donc un utilisateur qui recevait un mail pour un pret ECHU ne pouvait pas le prolonger
		 * En revanche la seconde partie du bug existait bien puisqu'il ne recevait un mail qu'après la date de retour
		 */
		if(pretAProlonger.get().getDateRetourPrevue().isBefore(LocalDate.now()))
			throw new WrongNumberException("La date limite pour prolonger votre prêt est dépassée");

		
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
	 * @throws BookNotAvailableException 
	 * @throws Exception
	 */
	@Override
	public Pret cloturerPret(Long numPret) throws EntityNotFoundException, BookNotAvailableException {
		Optional<Pret> pretACloturer = pretRepository.findById(numPret);
		if(!pretACloturer.isPresent()) 
			throw new EntityNotFoundException ("Aucun prêt enregistré ne correspond à votre demande");
		
		if(pretACloturer.get().getPretStatut().equals(PretStatut.CLOTURE)) 
			throw new BookNotAvailableException ("Le statut de ce pret de livre ne permet pas sa clôture");
		
		pretACloturer.get().setDateRetourEffectif(LocalDate.now());
		pretACloturer.get().setPretStatut(PretStatut.CLOTURE);
		
		Integer nbExemplairesDisponiblesAvantTransaction = pretACloturer.get().getLivre().getNbExemplairesDisponibles();
		pretACloturer.get().getLivre().setNbExemplairesDisponibles(nbExemplairesDisponiblesAvantTransaction + 1);
		
		livreMetier.miseAJourLivres();
		
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
		/*
		 * COMMENTAIRE HOTIX 1.0.1 : on maintient cette 1ère méthode pour envoyer un mail aux utilisateurs dont le pret est échu
		 * c'est à dire qu'à la date de traitement la date de retour est dépassée et le livre n'est toujours pas restitué
		 * Seuls les prêts cloturés qui sont dans ce cas sont exclus
		 */
		List<Pret> pretsEchus = new ArrayList<Pret>();
		Optional<List<Pret>> pretsNonCloturesDateEcheanceBeforeToday = pretRepository.findAllByOtherPretStatutAndDateEcheanceBeforeThisDate(
				PretStatut.CLOTURE, 
				LocalDate.now());
		if(pretsNonCloturesDateEcheanceBeforeToday.isPresent())
		{
			for(Pret pretEchu : pretsNonCloturesDateEcheanceBeforeToday.get())
				{
				pretEchu.setPretStatut(PretStatut.ECHU);
				pretsEchus.add(pretEchu);
				pretRepository.save(pretEchu);
				}
		}
		return pretsEchus; 
	}

	

	
	@Override
	public Pret readPret(Long numPret) throws EntityNotFoundException {
		Optional<Pret> searchedPret = pretRepository.findById(numPret);
		if(!searchedPret.isPresent())
			throw new EntityNotFoundException ("Aucun prêt enregistré ne correspond à votre demande");
		return searchedPret.get();
	}

	@Override
	public List<Pret> searchAndUpdatePretsAEchoir() {
		/*
		 * COMMENTAIRE HOTFIX 1.0.1 : on ajoute cette méthode pour identifier les prets qui viennent à échéance 
		 * Un mail sera envoyé en batch aux utilisateurs concernés en leur demandant de :
		 * 1/ restituer l'ouvrage avant la date d'échéance si le prêt est déjà prolongé
		 * 2/ prolonger le pret ou restituer l'ouvrage avant la date d'échéance si le prêt n'a jamais été prolongé. Dans ce cas, 
		 * le statut du pret est passé en pret A ECHOIR
		 * La durée de sélection des prêts qui viennent à échéance est ici de 48 heures et elle peut être modifiée 
		 * dans application.properties
		 */
		List<Pret> pretsAEchoir = new ArrayList<Pret>();
		
		Optional<List<Pret>> pretsAlreadyAEchoir = pretRepository.findAllByPretStatut(PretStatut.AECHOIR);
		if(pretsAlreadyAEchoir.isPresent()) {
		pretsAEchoir.addAll(pretsAlreadyAEchoir.get());
		}
		
		Optional<List<Pret>> pretsProlongesOuEncoursAEchoir = pretRepository.findAllByPretStatutAndDateEcheanceAfterThisDate(PretStatut.PROLONGE, PretStatut.ENCOURS,LocalDate.now());
		if(pretsProlongesOuEncoursAEchoir.isPresent()) {
			for (Pret pretProlongeOuEncoursAEchoir : pretsProlongesOuEncoursAEchoir.get()) {
			LocalDate dateDebutPretProlongeOuEncoursAEchoir = pretProlongeOuEncoursAEchoir.getDateRetourPrevue().minusDays(appProperties.getDureeAEchoir());
				if (LocalDate.now().isAfter(dateDebutPretProlongeOuEncoursAEchoir) && 
						pretProlongeOuEncoursAEchoir.getPretStatut().equals(PretStatut.PROLONGE)) {
					pretsAEchoir.add(pretProlongeOuEncoursAEchoir);
				} else if (LocalDate.now().isAfter(dateDebutPretProlongeOuEncoursAEchoir) && 
						pretProlongeOuEncoursAEchoir.getPretStatut().equals(PretStatut.ENCOURS))
				{
					pretProlongeOuEncoursAEchoir.setPretStatut(PretStatut.AECHOIR);
					pretsAEchoir.add(pretProlongeOuEncoursAEchoir);
					pretRepository.save(pretProlongeOuEncoursAEchoir);
				}
			}
		}
		return pretsAEchoir; 
		}
		
	}	
		
	
	
	
	
	

