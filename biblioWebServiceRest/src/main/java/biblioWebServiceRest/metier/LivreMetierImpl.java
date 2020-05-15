package biblioWebServiceRest.metier;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.dao.ILivreRepository;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.LivreStatut;

@Service
@Transactional
public class LivreMetierImpl implements ILivreMetier{
	
	@Autowired
	private ILivreRepository livreRepository;

	/*
	 * 
	 */
	@Override
	public Livre readLivre(long numLivre) {
		Livre l = livreRepository.findById(numLivre).get();
		if(l==null) throw new RuntimeException("Enregistrement Livre introuvable");
		return l;
	}
	

	@Override
	public List<Livre> displayAllLivres() {
		List<Livre> l = livreRepository.findAll();
		return l;
	}

	@Override
	public Page<Livre> displayPagesAllLivres(Pageable pageable) {
		Page<Livre> l = livreRepository.findAll(pageable); 
		return l;
	}

	@Override
	public List<Livre> displayByTitre(String titre) {
		List<Livre> l = livreRepository.findByTitre(titre);
		return l;
	}

	@Override
	public Page<Livre> displayByTitre(String titre, Pageable pageable) {
		Page<Livre> l = livreRepository.findByTitre(titre, pageable);
		return l;
	}

	@Override
	public List<Livre> displayByTitreAndLivreStatut(String titre, LivreStatut livreStatut) {
		List<Livre> l = livreRepository.findByTitreAndLivreStatut(titre, livreStatut); 
		return l;
	}

	@Override
	public Page<Livre> displayByTitreAndLivreStatut(String titre, LivreStatut livreStatut, Pageable pageable) {
		Page<Livre> l = livreRepository.findByTitreAndLivreStatut(titre, livreStatut, pageable);
		return l;
	}

	@Override
	public long countByTitre(String titre) {
		long nb = livreRepository.countByTitre(titre);
		return nb;
	}

	@Override
	public long countByTitreAndByLivreStatut(String titre, LivreStatut livreStatut) {
		long nb = livreRepository.countByTitreAndLivreStatut(titre, livreStatut);
		return nb;
	}

	
	

}
