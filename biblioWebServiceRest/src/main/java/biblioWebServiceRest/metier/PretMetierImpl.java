package biblioWebServiceRest.metier;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.dao.IPretRepository;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;

@Service
@Transactional
public class PretMetierImpl implements IPretMetier {
	
	@Autowired
	private IPretRepository pretRepository;

	@Override
	public Pret createPret(Pret pret) {
		final Properties prop = new Properties();
		InputStream input = null;
		try {
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            prop.load(input);
            int dureePret = Integer.parseInt(prop.getProperty("dureePretByDefault"));    
            LocalDate pretDateRetour = pret.getDatePret().plusDays(dureePret);
            pret.setDateRetour(pretDateRetour);
        } catch (final IOException ex) { 
            if (input != null) {
                try {
                    input.close();
                } catch (final IOException e) {
                }
            }
        }
		pret.setPretStatut(PretStatut.ENCOURS);
		return pretRepository.save(pret);
	}

	@Override
	public Pret readPret(long numPret) {
		Pret p = pretRepository.findById(numPret).get();
		if(p==null) throw new RuntimeException("Enregistrement Pret introuvable");
		return p;
	}

	@Override
	public Pret prolongerPret(Pret pret) {
		final Properties prop = new Properties();
		InputStream input = null;
		try {
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            prop.load(input);
            int dureeProlongation = Integer.parseInt(prop.getProperty("dureeProlongationByDefault"));    
            LocalDate pretNouvelleDateRetour = pret.getDateRetour().plusDays(dureeProlongation);
            pret.setDateRetour(pretNouvelleDateRetour);
        } catch (final IOException ex) { 
            if (input != null) {
                try {
                    input.close();
                } catch (final IOException e) {
                }
            }
        }
		pret.setPretStatut(PretStatut.PROLONGE);
		return pretRepository.save(pret);
		
	}

	@Override
	public Pret cloturerPret(Pret pret) {
		pret.setPretStatut(PretStatut.CLOTURE);
		return pretRepository.save(pret);
	}

	@Override
	public List<Pret> displayAllPrets() {
		List<Pret> l = pretRepository.findAll();
		return l;
	}

	@Override
	public Page<Pret> displayAllPrets(Pageable pageable) {
		Page<Pret> p = pretRepository.findAll(pageable);
		return p;
	}

	@Override
	public List<Pret> displayPretsByStatutEncours(PretStatut pretStatut) {
		List<Pret> l = pretRepository.findByPretStatut(PretStatut.ENCOURS);
		return l;
	}

	@Override
	public Page<Pret> displayPretsByStatutEncours(PretStatut pretStatut, Pageable pageable) {
		Page<Pret> p = pretRepository.findByPretStatut(PretStatut.ENCOURS, pageable); 
		return p;
	}

	@Override
	public List<Pret> displayPretsByStatutEncoursAndByUser(PretStatut pretStatut, User user) {
		List<Pret> l = pretRepository.findByPretStatutAndUser(PretStatut.ENCOURS, user);
		return l;
	}

	@Override
	public Page<Pret> displayPretsByStatutEncoursAndByUser(PretStatut pretStatut, User user, Pageable pageable) {
		Page<Pret> p = pretRepository.findByPretStatutAndUser(PretStatut.ENCOURS, user, pageable);
		return p;
	}

	@Override
	public List<Pret> displayPretsByStatutEncoursAndByTitreLivre(PretStatut pretStatut, Livre livre) {
		List<Pret> l = pretRepository.findByPretStatutAndLivre(PretStatut.ENCOURS, livre);
		return l;
	}

	@Override
	public Page<Pret> displayPretsByStatutEncoursAndByTitreLivre(PretStatut pretStatut, Livre livre,
			Pageable pageable) {
		Page<Pret> p = pretRepository.findByPretStatutAndLivre(PretStatut.ENCOURS, livre, pageable);
		return null;
	}

	
	@Override
	public List<Pret> displayPretsByStatutProlonge(PretStatut pretStatut) {
		List<Pret> l = pretRepository.findByPretStatut(PretStatut.PROLONGE);
		return l;
	}

	@Override
	public Page<Pret> displayPretsByStatutProlonge(PretStatut pretStatut, Pageable pageable) {
		Page<Pret> p = pretRepository.findByPretStatut(PretStatut.PROLONGE, pageable); 
		return p;
	}

	@Override
	public List<Pret> displayPretsByStatutProlongeAndByUser(PretStatut pretStatut, User user) {
		List<Pret> l = pretRepository.findByPretStatutAndUser(PretStatut.PROLONGE, user);
		return l;
	}

	@Override
	public Page<Pret> displayPretsByStatutProlongeAndByUser(PretStatut pretStatut, User user, Pageable pageable) {
		Page<Pret> p = pretRepository.findByPretStatutAndUser(PretStatut.PROLONGE, user, pageable);
		return p;
	}

	@Override
	public List<Pret> displayPretsByStatutProlongeAndByTitreLivre(PretStatut pretStatut, Livre livre) {
		List<Pret> l = pretRepository.findByPretStatutAndLivre(PretStatut.PROLONGE, livre);
		return l;
	}

	@Override
	public Page<Pret> displayPretsByStatutProlongeAndByTitreLivre(PretStatut pretStatut, Livre livre,
			Pageable pageable) {
		Page<Pret> p = pretRepository.findByPretStatutAndLivre(PretStatut.PROLONGE, livre, pageable);
		return null;
	}
	
	

	@Override
	public List<Pret> displayPretsByStatutEchu(PretStatut pretStatut) {
		
		List<Pret> lenc = displayPretsByStatutEncours(pretStatut);
		for(Pret pret : lenc ) {
			if(pret.getDateRetour().isBefore(LocalDate.now())) {
			pret.setPretStatut(PretStatut.ECHU); 
			}
		}
		
		List<Pret> lprol = displayPretsByStatutProlonge(pretStatut);
		for(Pret pret : lprol ) {
			if(pret.getDateRetour().isBefore(LocalDate.now())) {
			pret.setPretStatut(PretStatut.ECHU); 
			}
		}
			
		// code à terminer	
			
		return null;
	}

	@Override
	public Page<Pret> displayPretsByStatutEchu(PretStatut pretStatut, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pret> displayPretsByStatutEchuAndByUser(PretStatut pretStatut, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Pret> displayPretsByStatutEchuAndByUser(PretStatut pretStatut, User user, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pret> displayPretsByStatutEchuAndByTitreLivre(PretStatut pretStatut, Livre livre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Pret> displayPretsByStatutEchuAndByTitreLivre(PretStatut pretStatut, Livre livre, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
