package biblioWebServiceRest.metier;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.dao.IUserRepository;
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
			livre = livreRepository.findByTitre(titre);
			System.out.println(livreRepository.findByTitre(titre));
			titre = livre.getTitre();
			System.out.println(titre);
			if(livre.getNbExemplairesDisponibles()==0) throw new RuntimeException("Il n'y a plus d'exemplaire disponible");
			pret.setLivre(livre);
			livre.setNbExemplairesDisponibles(livre.getNbExemplairesDisponibles()-1);
		} catch (Exception e1) {
			if(titre.isEmpty()) {throw new RuntimeException("Il faut saisir le titre du livre à emprunter");
			} else {
			throw new RuntimeException("Le titre saisi n'existe pas");
			}
		}
		User user;
		try {
			user = userRepository.findByUsername(username);
			username = user.getUsername();
			pret.setUser(user);
		} catch (Exception e2) {
			if(!Optional.ofNullable(username).isPresent()) throw new RuntimeException("Il n'existe aucun abonné de ce nom");
			e2.printStackTrace();
		}
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
	@Override
	public Pret readPret(long numPret) {
		// TODO Auto-generated method stub
		return null;
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
