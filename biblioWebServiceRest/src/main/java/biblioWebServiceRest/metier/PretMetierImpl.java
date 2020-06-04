/**
 * Classe d'implémentation des méthodes Métier pour l'entité Pret
 */
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
import biblioWebServiceRest.dto.PretCriteriaDTO;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.InternalServerErrorException;
import biblioWebServiceRest.exceptions.NotFoundException;
import biblioWebServiceRest.mapper.PretCriteriaMapper;
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
	ApplicationPropertiesConfiguration appProperties;
	@Autowired
	PretMapper pretMapper;
	@Autowired
	PretCriteriaMapper pretCriteriaMapper;
	
	
	/**
	 * CRUD : CREATE Créer le prêt de l'exemplaire disponible d'un livre
	 * Exception levée si aucun titre ou aucun nom utilisateur correspondant ou si nombre d'exemplaire nul
	 * @param titre
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@Override
	public PretDTO createPret(Long numLivre, Long idUser) throws Exception {
		
		Optional<Livre> livre = livreRepository.findById(numLivre);
		if(!livre.isPresent()) 
			throw new NotFoundException ("Aucun enregistrement de livre ne correspond à votre demande");
		if(livre.get().getNbExemplairesDisponibles() ==0) 
			throw new InternalServerErrorException ("Il n'y a plus d'exemplaire disponible de ce livre");
		
		Optional<User> user = userRepository.findById(idUser);
		if(!user.isPresent()) 
			throw new NotFoundException ("Aucun utlisateur ne correspond à votre demande ");
		
		Pret pret = new Pret();
		
		pret.setLivre(livre.get());
		pret.getLivre().setNbExemplairesDisponibles(pret.getLivre().getNbExemplairesDisponibles()-1);
		pret.setUser(user.get());
		
		LocalDate datePret = LocalDate.now();
		pret.setDatePret(datePret);
		LocalDate dateRetourPrevue = datePret.plusDays(appProperties.getDureePret());
		pret.setDateRetourPrevue(dateRetourPrevue);
		pret.setPretStatut(PretStatut.ENCOURS);
		
		pretRepository.save(pret);
		
		PretDTO pretDTO = pretMapper.pretToPretDTO(pret);
		
		return pretDTO;
		
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
	public PretDTO prolongerPret(Long numPret) throws Exception {
		Optional<Pret> pret = pretRepository.findById(numPret);
		if(!pret.isPresent()) 
			throw new NotFoundException ("Aucun prêt enregistré ne correspond à votre demande");
		
		if(!pret.get().getPretStatut().equals(PretStatut.ENCOURS)) 
			throw new InternalServerErrorException ("Le statut de ce pret de livre ne permet pas sa prolongation");
		
		LocalDate datePretProlonge = pret.get().getDateRetourPrevue();
		pret.get().setDatePret(datePretProlonge);
		LocalDate dateRetourPrevuePretProlonge = datePretProlonge.plusDays(appProperties.getDureeProlongation());
		pret.get().setDateRetourPrevue(dateRetourPrevuePretProlonge);
		
		pret.get().setPretStatut(PretStatut.PROLONGE);
		
		pretRepository.save(pret.get());
		
		PretDTO prolongationPretDTO = pretMapper.pretToPretDTO(pret.get());
		
		return prolongationPretDTO;
		
	}
	
	/**
	 * CRUD : UPDATE clôturer un prêt à la date de transaction
	 * Le pret passe en statut CLOTURE mais n'est pas supprimé en base de données
	 * @param numPret
	 * @return
	 * @throws Exception
	 */
	@Override
	public PretDTO cloturerPret(Long numPret) throws Exception {
		Optional<Pret> pretACloturer = pretRepository.findById(numPret);
		if(!pretACloturer.isPresent()) 
			throw new NotFoundException ("Aucun prêt enregistré ne correspond à votre demande");
		
		pretACloturer.get().setDateRetourEffectif(LocalDate.now());
		pretACloturer.get().setPretStatut(PretStatut.CLOTURE);
		
		Integer nbExemplairesDisponiblesAvantTransaction = pretACloturer.get().getLivre().getNbExemplairesDisponibles();
		pretACloturer.get().getLivre().setNbExemplairesDisponibles(nbExemplairesDisponiblesAvantTransaction + 1);
		
		pretRepository.save(pretACloturer.get());
		
		PretDTO cloturePretDTO = pretMapper.pretToPretDTO(pretACloturer.get());
		
		return cloturePretDTO;
		
	}
	
	/**
	 * Recherche multi-critères des prets enregistrés 
	 * @param pretCriteria
	 * @return
	 */
	@Override
	public List<PretDTO> searchByCriteria(PretCriteriaDTO pretCriteriaDTO) {
		/**
		Specification<Livre> livreSpecification = new LivreSpecification(livreCriteria);
		List<Livre> livres = livreRepository.findAll(livreSpecification);
		List<LivreDTO> livreDTOs = livreMapper.livresToLivresDTOs(livres);
		return livreDTOs;
		**/
		PretCriteria pretCriteria = pretCriteriaMapper.pretCriteriaDTOToPretCriteria(pretCriteriaDTO);
		Specification<Pret> pretSpecification = new PretSpecification(pretCriteria);
		List<Pret> prets = pretRepository.findAll(pretSpecification);
		List<PretDTO> pretDTOs = pretMapper.pretsToPretsDTOs(prets);
		
		return pretDTOs;
	}

	

	
}
