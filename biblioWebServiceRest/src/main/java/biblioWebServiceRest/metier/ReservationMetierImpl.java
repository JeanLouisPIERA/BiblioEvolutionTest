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
import biblioWebServiceRest.criteria.ReservationCriteria;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IReservationRepository;
import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.dao.specs.ReservationSpecification;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.dto.ReservationDTO;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.entities.ReservationStatut;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.BookAvailableException;
import biblioWebServiceRest.exceptions.BookNotAvailableException;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.exceptions.RentAlreadyExistsException;
import biblioWebServiceRest.exceptions.WrongNumberException;
import biblioWebServiceRest.mapper.LivreMapper;
import biblioWebServiceRest.mapper.PretMapper;
import biblioWebServiceRest.mapper.ReservationMapper;

@Service
@Transactional
public class ReservationMetierImpl implements IReservationMetier{
	
	@Autowired
	private IReservationRepository reservationRepository;
	@Autowired
	private IPretRepository pretRepository;
	@Autowired
	private ILivreRepository livreRepository;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	ApplicationPropertiesConfiguration appProperties;
	@Autowired
	ReservationMapper reservationMapper;
	@Autowired
	PretMapper pretMapper;
	@Autowired
	LivreMapper livreMapper; 
	@Autowired
	IPretMetier pretMetier;
	@Autowired
	ILivreMetier livreMetier;
	

	@Override
	public Reservation createReservation(ReservationDTO reservationDTO) throws EntityNotFoundException, BookNotAvailableException, BookAvailableException, EntityAlreadyExistsException, RentAlreadyExistsException {
			
		Optional<User> user = userRepository.findById(reservationDTO.getIdUser());
		if(!user.isPresent()) 
			throw new EntityNotFoundException ("UTILISATEUR INCONNU = Aucun utilisateur ne correspond à votre identification de l'emprunteur ");
		
		Optional<Livre> livreToRent = livreRepository.findById(reservationDTO.getNumLivre());
		if(!livreToRent.isPresent()) 
			throw new EntityNotFoundException ("OUVRAGE INCONNU = Aucun enregistrement de livre ne correspond à votre demande");
	
		if(livreToRent.get().getNbExemplaires()==0)
			throw new BookNotAvailableException ("RESERVATION IMPOSSIBLE = Il n'y a aucun exemplaire à emprunter pour cette référence de livre");
	
		if(livreToRent.get().getNbExemplairesDisponibles() >0) 
			throw new BookAvailableException ("RESERVATION IMPOSSIBLE = Vous pouvez emprunter immédiatement un exemplaire disponible de ce livre");
	
		Optional<Reservation> reservation = reservationRepository.findByUserAndLivreAndStatutReservationOrStatutReservation(user.get(), livreToRent.get(), ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE);
		if(reservation.isPresent()) 
			 throw new EntityAlreadyExistsException ("RESERVATION IMPOSSIBLE : vous ne pouvez pas réserver un livre pour lequel vous avez déjà une réservation en cours");	
		
		Optional<List<Pret>> pretListe = pretRepository.findAllByLivreAndUserAndNotPretStatut(livreToRent.get(), user.get(), PretStatut.CLOTURE);
		if(pretListe.isPresent())
			 throw new RentAlreadyExistsException ("RESERVATION IMPOSSIBLE : vous ne pouvez pas réserver un livre que vous avez déjà en cours de prêt");	
			
		//TICKET 1 FONCTIONNALITE REGLE DE GESTION
		//PERMET DE RESPECTER LA REGLE DE GESTION ET DE GERER LE RANG DANS LA FILE D'ATTENTE
		Optional<List<Reservation>> reservationsByUser = reservationRepository.findAllByLivreGroupByUserAndReservationStatutOrReservationStatut(livreToRent.get(), ReservationStatut.ENREGISTREE, ReservationStatut.NOTIFIEE);
	
		if(reservationsByUser.isPresent()) {  	
			if ((reservationsByUser.get().size())>=(appProperties.getMultiplicateur()*(livreToRent.get().getNbExemplaires()))) 
			throw new BookNotAvailableException ("RESERVATION IMPOSSIBLE : le nombre maximum d'utilisateurs ayant fait une réservation est atteint");
		}
		
		Reservation newReservation = new Reservation();
		
		newReservation.setLivre(livreToRent.get());
	
		newReservation.setUser(user.get());
	
		LocalDate dateReservation = LocalDate.now();
		newReservation.setDateReservation(dateReservation);
		
		//Incrémente le rang de la réservation en cours de création dans la file d'attente du livre à réserver
		if(reservationsByUser.isPresent()) {
				newReservation.setRangReservation(reservationsByUser.get().size()+1);
		} else {
			newReservation.setRangReservation(1);
		}
		
		newReservation.setReservationStatut(ReservationStatut.ENREGISTREE);
		
		livreMetier.miseAJourLivres();
		
		return reservationRepository.save(newReservation);
		
	}

	@Override
	public Reservation notifierReservation(Long numReservation) throws EntityNotFoundException, WrongNumberException {
		Optional<Reservation> searchedReservation = reservationRepository.findById(numReservation);
		if(!searchedReservation.isPresent())
			throw new EntityNotFoundException("RESERVATION INCONNUE : Cette réservation n'existe pas");
		if(!searchedReservation.get().getReservationStatut().equals(ReservationStatut.ENREGISTREE))
			throw new WrongNumberException("NOTIFICATION IMPOSSIBLE = Le statut de cette réservation ne permet pas de la notifier");
		
			searchedReservation.get().setReservationStatut(ReservationStatut.NOTIFIEE);
			searchedReservation.get().getLivre().setNbExemplairesDisponibles(searchedReservation.get().getLivre().getNbExemplairesDisponibles()-1);
			LocalDate dateNotification = LocalDate.now();
			searchedReservation.get().setDateNotification(dateNotification);
			LocalDate dateDeadline = dateNotification.plusDays(appProperties.getDureeNotification());
			searchedReservation.get().setDateDeadline(dateDeadline);
		
		return reservationRepository.save(searchedReservation.get());
	}
	
	@Override
	public void miseAJourRangReservationDansFileAttente(Livre livre) {
		//TICKET 1 FONCTIONNALITE
		//PERMET DE METTRE A JOUR LE RANG DANS LA FILE D'ATTENTE
		Optional<List<Reservation>> reservationList = reservationRepository.findAllByLivreAndReservationStatut(livre, ReservationStatut.ENREGISTREE);
		if(reservationList.isPresent()) {
			for(Reservation reservation : reservationList.get()) {
				reservation.setRangReservation(reservation.getRangReservation()-1);
				reservationRepository.save(reservation);
			}
		}
	}

	@Override
	public Reservation livrerReservationAndCreerPret(Long numReservation) throws EntityNotFoundException, WrongNumberException, BookNotAvailableException {
		Optional<Reservation> searchedReservation = reservationRepository.findById(numReservation);
		if(!searchedReservation.isPresent())
			throw new EntityNotFoundException("RESERVATION INCONNUE = Cette réservation n'existe pas");
		if(!searchedReservation.get().getReservationStatut().equals(ReservationStatut.NOTIFIEE))
			throw new WrongNumberException("LIVRAISON IMPOSSIBLE = Le statut de cette réservation ne permet pas de la notifier");
		if(searchedReservation.get().getDateDeadline().isBefore(LocalDate.now())) {
			searchedReservation.get().setReservationStatut(ReservationStatut.ANNULEE);
			throw new BookNotAvailableException("LIVRAISON ANNULEE = La date limite de votre réservation pour le pret du livre est dépassée");
		}
		
		//TICKET 1 FONCTIONNALITE
		//PERMET DE METTRE A JOUR LE RANG DANS LA FILE D'ATTENTE
		Livre livreSearchedReservation = searchedReservation.get().getLivre();
		this.miseAJourRangReservationDansFileAttente(livreSearchedReservation);
		searchedReservation.get().setReservationStatut(ReservationStatut.LIVREE);
		searchedReservation.get().setRangReservation(null);
		searchedReservation.get().getLivre().setNbExemplairesDisponibles(searchedReservation.get().getLivre().getNbExemplairesDisponibles()+1);
		searchedReservation.get().setDateSuppression(LocalDate.now());
		PretDTO pretDTO = new PretDTO();
		pretDTO.setIdUser(searchedReservation.get().getUser().getIdUser());
		pretDTO.setNumLivre(searchedReservation.get().getLivre().getNumLivre());
		pretMetier.createPret(pretDTO);
		
		livreMetier.miseAJourLivres();
		
		return reservationRepository.save(searchedReservation.get());
	}

	//TiCKET 1 FONCTIONNALITE APPLIWEB N°4 : PERMET DE SUPPRIMER UNE RESERVATION
	@Override
	public Reservation suppressReservation(Long numReservation) throws EntityNotFoundException, WrongNumberException {
		Optional<Reservation> searchedReservation = reservationRepository.findById(numReservation);
		if(!searchedReservation.isPresent())
			throw new EntityNotFoundException("RESERVATION INCONNUE = Cette réservation n'existe pas");
		if(searchedReservation.get().getReservationStatut().equals(ReservationStatut.LIVREE)
				|| searchedReservation.get().getReservationStatut().equals(ReservationStatut.ANNULEE)
				|| searchedReservation.get().getReservationStatut().equals(ReservationStatut.SUPPRIMEE))
			throw new WrongNumberException("SUPPRESSION IMPOSSIBLE = Le statut de cette réservation ne permet pas de la supprimer");
				
		//TICKET 1 FONCTIONNALITE 
		//PERMET DE METTRE A JOUR LE RANG DANS LA FILE D'ATTENTE DES RESERVATIONS DE RANG SUPERIEUR A LA RESERVATION A ANNULER
		this.miseAJourRangReservationDansFileAttente(searchedReservation.get().getLivre());
		
		if(searchedReservation.get().getReservationStatut()==ReservationStatut.ENREGISTREE) {
			searchedReservation.get().setReservationStatut(ReservationStatut.SUPPRIMEE);
		}
		if(searchedReservation.get().getReservationStatut()==ReservationStatut.NOTIFIEE) {
			searchedReservation.get().setReservationStatut(ReservationStatut.ANNULEE);
			
			
		}
		
		LocalDate dateSuppression = LocalDate.now();
		searchedReservation.get().setDateSuppression(dateSuppression);
		searchedReservation.get().setRangReservation(null);
		
		livreMetier.miseAJourLivres();
		
		return reservationRepository.save(searchedReservation.get());
	}
	

	@Override
	public Page<Reservation> searchAllReservationsByCriteria(ReservationCriteria reservationCriteria, Pageable pageable) {
		Specification<Reservation> reservationSpecification = new ReservationSpecification(reservationCriteria);
		Page<Reservation> reservations = reservationRepository.findAll(
				reservationSpecification, 
				pageable);
		return reservations;
	}
	
	@Override
	public List<Reservation> searchAndNotifierReservations() throws EntityNotFoundException, WrongNumberException {
		List<Reservation> reservationsANotifier = new ArrayList<Reservation>();
		
		Optional<List<Reservation>> reservationsAlreadyNotifieesDeadlineValide = reservationRepository.findAllByReservationStatutAndDateDeadlineValide(ReservationStatut.NOTIFIEE, LocalDate.now()); 
		if(reservationsAlreadyNotifieesDeadlineValide.isPresent()) {	
			reservationsANotifier.addAll(reservationsAlreadyNotifieesDeadlineValide.get());
		}
		
		Optional<List<Reservation>> reservationsAlreadyNotifieesDeadlineDechue = reservationRepository.findAllByReservationStatutAndDateDeadlineDechue(ReservationStatut.NOTIFIEE, LocalDate.now()); 
		if(reservationsAlreadyNotifieesDeadlineDechue.isPresent()) {	
			for(Reservation reservationAlreadyNotifieeDeadlineDechue : reservationsAlreadyNotifieesDeadlineDechue.get()) {
				this.suppressReservation(reservationAlreadyNotifieeDeadlineDechue.getNumReservation());
			}

		}	
		
		Optional<List<Reservation>> reservationsEnregistreesSelectionnees = reservationRepository.findAllByReservationStatutAndNbExemplairesDisponiblesAndRangReservation(ReservationStatut.ENREGISTREE, 1,1);
		if(reservationsEnregistreesSelectionnees.isPresent()) {
			for (Reservation reservation : reservationsEnregistreesSelectionnees.get()) {
					Reservation reservationPostNotif = this.notifierReservation(reservation.getNumReservation());
					if(reservationPostNotif.getReservationStatut().equals(ReservationStatut.NOTIFIEE))
					{
						reservationsANotifier.add(reservationPostNotif); 
					}	
				}
		}
		return reservationsANotifier; 
	}

	@Override
	public Reservation readReservation(Long numReservation) throws EntityNotFoundException {
		Optional<Reservation> searchedReservation = reservationRepository.findById(numReservation);
		if(!searchedReservation.isPresent())
			throw new EntityNotFoundException ("Aucune reservation enregistrée ne correspond à votre demande");
		return searchedReservation.get();
	}

} 
