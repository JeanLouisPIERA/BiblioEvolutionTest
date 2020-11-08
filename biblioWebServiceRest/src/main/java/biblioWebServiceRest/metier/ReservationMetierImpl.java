package biblioWebServiceRest.metier;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblioWebServiceRest.configurations.ApplicationPropertiesConfiguration;
import biblioWebServiceRest.criteria.ReservationCriteria;
import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IReservationRepository;
import biblioWebServiceRest.dao.IRoleRepository;
import biblioWebServiceRest.dao.IUserRepository;
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
import biblioWebServiceRest.mapper.LivreMapper;
import biblioWebServiceRest.mapper.PretMapper;
import biblioWebServiceRest.mapper.ReservationMapper;

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
	private IRoleRepository roleRepository;
	@Autowired
	ApplicationPropertiesConfiguration appProperties;
	@Autowired
	ReservationMapper reservationMapper;
	@Autowired
	PretMapper pretMapper;
	@Autowired
	LivreMapper livreMapper; 

	@Override
	public Reservation createReservation(ReservationDTO reservationDTO) throws EntityNotFoundException, BookNotAvailableException, BookAvailableException {
		Optional<Livre> livreToRent = livreRepository.findById(reservationDTO.getNumLivre());
		if(!livreToRent.isPresent()) 
			throw new EntityNotFoundException ("Aucun enregistrement de livre ne correspond à votre demande");
		if(livreToRent.get().getNbExemplaires()==0)
			throw new BookNotAvailableException ("RESERVATION IMPOSSIBLE = Il n'y a aucun exemplaire à emprunter pour cette référence de livre");
		if(livreToRent.get().getNbExemplairesDisponibles() >0) 
			throw new BookAvailableException ("RESERVATION IMPOSSIBLE = Vous pouvez emprunter immédiatement un exemplaire disponible de ce livre");
		if((livreToRent.get().getReservations().size()+1)>=(2*(livreToRent.get().getNbExemplaires())))
			throw new BookNotAvailableException ("RESERVATION IMPOSSIBLE : le nombre de réservations en cours maximum est atteint");
		
		Optional<User> user = userRepository.findById(reservationDTO.getIdUser());
		if(!user.isPresent()) 
			throw new EntityNotFoundException ("Aucun utilisateur ne correspond à votre identification de l'emprunteur ");
				
		User emprunteur = user.get();
		livreToRent.get().getPrets().forEach((pret)->{
			if(pret.getUser().equals(emprunteur)) 
				throw new RuntimeException ("RESERVATION REFUSEE : vous ne pouvez pas réserver un livre que vous avez déjà en cours de prêt");
			});
		
		Reservation newReservation = new Reservation();
		
		newReservation.setLivre(livreToRent.get());
		newReservation.setUser(emprunteur);
		
		LocalDate dateReservation = LocalDate.now();
		newReservation.setDateReservation(dateReservation);
		
		newReservation.setReservationStatut(ReservationStatut.ENREGISTREE);
		
		return reservationRepository.save(newReservation);
		
	}

	@Override
	public Reservation notifierReservation(Long numReservation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation livrerReservation(Long numReservation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation deleteReservation(Long numReservation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Reservation> searchAllReservationsByCriteria(ReservationCriteria reservationCriteria,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Reservation> searchMyReservations(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> searchAndNotifierReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> searchAndUpdateReservationsAnnulées() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
	

}
