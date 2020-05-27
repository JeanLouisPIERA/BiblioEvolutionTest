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
	 * @param idUser
	 * @param numLivre
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pret createPret(String titre, String username) throws Exception {
		Pret pret = new Pret();
		
		Optional<Livre> livre = livreRepository.findByTitre(titre);
		if(!livre.isPresent()) throw new Exception ("Le livre demandé n'existe pas");
		if(livre.get().getNbExemplairesDisponibles() ==0) throw new Exception ("Il n'y a plus d'exmplaires disponibles de ce livre");
		pret.setLivre(livre.get());
		pret.getLivre().setNbExemplairesDisponibles(pret.getLivre().getNbExemplairesDisponibles()-1);
		
		Optional<User> user = userRepository.findByUsername(username);
		if(!user.isPresent()) throw new Exception ("L'emprunteur n'existe pas");
		pret.setUser(user.get());
		
		LocalDate datePret = LocalDate.now();
		pret.setDatePret(datePret);
		LocalDate dateRetourPrevue = datePret.plusDays(appProperties.getDureePret());
		pret.setDateRetourPrevue(dateRetourPrevue);
		pret.setPretStatut(PretStatut.ENCOURS);
		
		return pretRepository.save(pret);
	
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
	 * @param pretCriteria
	 * @return
	 */
	@Override
	public List<Pret> searchByCriteria(PretCriteria pretCriteria) {
		Specification<Pret> pretSpecification = new PretSpecification(pretCriteria);
		return pretRepository.findAll(pretSpecification);
	}
	
	


	



	
	
}
