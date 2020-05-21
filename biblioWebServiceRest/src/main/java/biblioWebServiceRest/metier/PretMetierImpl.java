package biblioWebServiceRest.metier;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.dao.specs.PretSpecification;
import biblioWebServiceRest.dao.specs.SearchCriteria;
import biblioWebServiceRest.dao.specs.SearchOperation;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;

@Service
@Transactional
public class PretMetierImpl implements IPretMetier {
	
	@Autowired
	private IPretRepository pretRepository;
	@Autowired
	private ILivreRepository livreRepository;
	@Autowired
	private IUserRepository userRepository;

	/**
	 * CRUD : CREATE Créer le prêt de l'exemplaire disponible d'un livre
	 * Messages d'erreurs en cas de titre ou d'emprunteur inexistants ou d'exemplaire non disponible
	 * La date de prêt est la date système
	 * La date de fin de prêt est calculé automatiquement à partir des paramètres saisis dans les properties 
	 * @param pret
	 * @return
	 */
	 @Override
	public Pret createPret(String titre, String username) {
		Pret pret = new Pret();
		LocalDate datePret = LocalDate.now();
		pret.setDatePret(datePret);
		Livre livre;
		try {
			livre = livreRepository.findByTitre(titre).get();
			System.out.println(livreRepository.findByTitre(titre));
			titre = livre.getTitre();
			System.out.println(titre);
		}catch (Exception e1) {
			if(titre.isEmpty()) {throw new RuntimeException("Il faut saisir le titre du livre à emprunter");
			} else {
			throw new RuntimeException("Le titre saisi n'existe pas");
			}
		}
		System.out.println(livre.getNbExemplairesDisponibles());
		if(livre.getNbExemplairesDisponibles() == 0) throw new RuntimeException ("Il n'y a plus d'exemplaire disponible");
		livre.setNbExemplairesDisponibles(livre.getNbExemplairesDisponibles()-1);	
		pret.setLivre(livre);
		//User user;
		if(!userRepository.findByUsername(username).isPresent()) throw new RuntimeException("Cet abonné n'est pas enregistré");
		User user = userRepository.findByUsername(username).get();
		username = user.getUsername();
		pret.setUser(user);
		
		/**
		try {
			user = userRepository.findByUsername(username).get();
			username = user.getUsername();
			pret.setUser(user);
		} catch (Exception e2) {
			if(!Optional.ofNullable(username).isPresent()) throw new RuntimeException("Il n'existe aucun abonné de ce nom");
		}**/
		final Properties prop = new Properties();
		InputStream input = null;
		try {
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            prop.load(input);
            int dureePret = Integer.parseInt(prop.getProperty("dureePretByDefault"));    
            LocalDate pretDateRetourPrevue = pret.getDatePret().plusDays(dureePret);
            pret.setDateRetourPrevue(pretDateRetourPrevue);
        } catch (final IOException ex) { 
            if (input != null) {
                try {
                    input.close();
                } catch (final IOException e) {
                }
            }
        }
		pret.setPretStatut(PretStatut.ENCOURS);
		pretRepository.save(pret);
		return pret;
	}

	 

	/**
	 * @param numPret
	 * @return
	 */
	 /**
	@Override
	public Pret readPret(long numPret) {
		// TODO Auto-generated method stub
		return null;
	}
	**/

	 	/**
	 	 * CRUD : READ consulter un prêt 
		 * @param titre
		 * @param username
		 * @param datePret
		 * @return
		 */
		@Override
		public Pret readPret(String titre, String username, String datePret) {
			Pret readPret = new Pret();
			Livre livre = new Livre();
			User user = new User();
			
			if(!livreRepository.findByTitre(titre).isPresent()) throw new RuntimeException("Ce titre n'existe pas");
			livre = livreRepository.findByTitre(titre).get();
			System.out.println(livreRepository.findByTitre(titre));
			titre = livre.getTitre();
			PretSpecification psTitre = new PretSpecification();
			psTitre.add(new SearchCriteria("livre", livre, SearchOperation.EQUAL));
			
			if(!userRepository.findByUsername(username).isPresent()) throw new RuntimeException("Cet abonné n'est pas enregistré");
			user = userRepository.findByUsername(username).get();
			username = user.getUsername();
			PretSpecification psUsername= new PretSpecification();
			psUsername.add(new SearchCriteria("user", user, SearchOperation.EQUAL));
			
			LocalDate localDate = null;
	        DateTimeFormatter formatter = null;
			formatter = DateTimeFormatter.BASIC_ISO_DATE;
	        localDate = LocalDate.parse(datePret, formatter);
	        System.out.println("Input Date?= "+ datePret);
	        System.out.println("Converted Date?= " + localDate);
			PretSpecification psDatePret= new PretSpecification();
			psDatePret.add(new SearchCriteria("datePret", localDate, SearchOperation.EQUAL));
			
			Specification<Pret> psTitreUsernameDatePret = Specification.where(psTitre).and(psUsername).and(psDatePret);
			
			if (pretRepository.findOne(psTitreUsernameDatePret).isPresent()) {
				readPret = pretRepository.findOne(psTitreUsernameDatePret).get();		
			}else {
				
				throw new RuntimeException ("Il n'existe aucun prêt avec les caractéristiques" + titre + username + datePret);
			}
			
			return readPret;
		}
	 
	/**
	 * @param pret
	 * @return
	 */
	@Override
	public Pret prolongerPret(Pret pret) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param pret
	 * @return
	 */
	@Override
	public Pret cloturerPret(Pret pret) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	@Override
	public List<Pret> searchAllPrets() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<Pret> searchAllPrets(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param dateRetour
	 * @param pretStatut
	 * @param user
	 * @return
	 */
	@Override
	public List<Pret> searchPretsByStatutEncoursAndByUser(LocalDate dateRetour, PretStatut pretStatut, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param dateRetour
	 * @param pretStatut
	 * @param user
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<Pret> displayPretsByDateRetourAndStatutAndByUser(LocalDate dateRetour, PretStatut pretStatut, User user,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
}
