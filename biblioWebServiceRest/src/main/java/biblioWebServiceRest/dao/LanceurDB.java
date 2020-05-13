package biblioWebServiceRest.dao;


import java.time.LocalDate;
import java.time.Month;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.LivreStatut;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.User;

/*
 * Cette classe permet de créer et de persister un jeu de données démo au lancement de l'application
 */

@Component
public class LanceurDB implements CommandLineRunner {

	private ILivreRepository livreRepository;
	private IPretRepository pretRepository;
	private IUserRepository userRepository;
	
	
	public LanceurDB(ILivreRepository livreRepository, IPretRepository pretRepository, IUserRepository userRepository) {
		super();
		this.livreRepository = livreRepository;
		this.pretRepository = pretRepository;
		this.userRepository = userRepository;
	}


	@Override
	public void run(String... args) throws Exception {
		User u1 = new User("Jean-Charles", "password", "jeannot@yahoo.fr");
		User u2 = new User("Charlemagne", "password", "charlot@gmail.com");
		User u3 = new User("Alexandre", "password", "alex@hotmail.com");
		userRepository.save(u1);
		userRepository.save(u2);
		userRepository.save(u3);
		
		Livre l1 = new Livre("La Légende des Siècles", "Victor Hugo", LivreStatut.DIS);
		Livre l2 = new Livre("Guerre et Paix", "Léon Tolstoi", LivreStatut.NDIS, u1);
		Livre l3 = new Livre("L'appel de la forêt", "Jack London", LivreStatut.NDIS, u2);
		livreRepository.save(l1);
		livreRepository.save(l2);
		livreRepository.save(l3);
		
		pretRepository.save(new Pret(LocalDate.of(2020, Month.FEBRUARY, 5), LocalDate.of(2020, Month.MARCH, 5), PretStatut.ECHU, u1, l1));
		pretRepository.save(new Pret(LocalDate.of(2020, Month.MARCH, 20), LocalDate.of(2010, Month.MAY, 20), PretStatut.PROLONGE, u2, l2));
		pretRepository.save(new Pret(LocalDate.of(2020,  Month.APRIL, 20), LocalDate.of(2020,  Month.MAY, 20), PretStatut.ENCOURS, u3, l3)); 
		
	}

}
