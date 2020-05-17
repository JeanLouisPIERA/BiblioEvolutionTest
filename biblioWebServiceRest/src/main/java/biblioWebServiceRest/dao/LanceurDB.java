package biblioWebServiceRest.dao;


import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import biblioWebServiceRest.dao.specs.LivreSpecification;
import biblioWebServiceRest.dao.specs.SearchCriteria;
import biblioWebServiceRest.dao.specs.SearchOperation;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.LivreStatut;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.PretStatut;
import biblioWebServiceRest.entities.Role;
import biblioWebServiceRest.entities.RoleEnum;
import biblioWebServiceRest.entities.User;


/*
 * Cette classe permet de créer et de persister un jeu de données démo au lancement de l'application
 */

@Component
public class LanceurDB implements CommandLineRunner {

	@Autowired
	private ILivreRepository livreRepository;
	@Autowired
	private IPretRepository pretRepository;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	private ICategorieRepository categorieRepository;
	

	public LanceurDB(ILivreRepository livreRepository, IPretRepository pretRepository, IUserRepository userRepository,
			IRoleRepository roleRepository, ICategorieRepository categorieRepository) {
		super();
		this.livreRepository = livreRepository;
		this.pretRepository = pretRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.categorieRepository = categorieRepository;
	}


	@Override
	public void run(String... args) throws Exception {
		
		Role admin = new Role(RoleEnum.ADMIN);
		Role user = new Role(RoleEnum.USER);
		roleRepository.save(admin);
		roleRepository.save(user);
		
		User user1 = new User("Jean-Charles", "password", "jeannot@yahoo.fr", user);
		User user2 = new User("Charlemagne", "password", "charlot@gmail.com", user);
		User user3 = new User("Alexandre", "password", "alex@hotmail.com", user);
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		
		Categorie categorie1 = new Categorie("Roman");
		Categorie categorie2 = new Categorie("Essai scientifique");
		Categorie categorie3 = new Categorie("Manuel scolaire");
		categorieRepository.save(categorie1);
		categorieRepository.save(categorie2);
		categorieRepository.save(categorie3);
		
		Livre livre1 = new Livre("Le Pere Goriot", "Honore de Balzac", 1,1, LivreStatut.DIS,categorie1);
		Livre livre2 = new Livre("Comment je vois le monde", "Albert Einstein", 2,0, LivreStatut.NDIS, categorie2);
		Livre livre3 = new Livre("Mathématiques au collège", "Collectif", 3, 0,LivreStatut.NDIS, categorie3);
		livreRepository.save(livre1);
		livreRepository.save(livre2);
		livreRepository.save(livre3);
		
		pretRepository.save(new Pret(LocalDate.of(2020, Month.FEBRUARY, 5), LocalDate.of(2020, Month.MARCH, 5), PretStatut.ECHU, user1, livre1));
		pretRepository.save(new Pret(LocalDate.of(2020, Month.MARCH, 20), LocalDate.of(2010, Month.MAY, 20), PretStatut.PROLONGE, user2, livre2));
		pretRepository.save(new Pret(LocalDate.of(2020,  Month.APRIL, 20), LocalDate.of(2020,  Month.MAY, 20), PretStatut.ENCOURS, user3, livre3)); 
		
	}
	
	@Bean
    public CommandLineRunner specificationsDemo(ILivreRepository livreRepository, ICategorieRepository categorieRepository) {
        return args -> {

            // creation de nouveaux livres
        	
        	Categorie categorie4 = new Categorie("Theatre");
        	Categorie categorie5 = new Categorie("Memoire");
        	Categorie categorie6 = new Categorie("Poesie");
        	Categorie categorie7 = new Categorie("Heroic Fantasy");
        	Categorie categorie8 = new Categorie("Bande Dessinee");
        	Categorie categorie9 = new Categorie("Policier");
        	categorieRepository.save(categorie4);
    		categorieRepository.save(categorie5);
    		categorieRepository.save(categorie6);
    		categorieRepository.save(categorie7);
    		categorieRepository.save(categorie8);
    		categorieRepository.save(categorie9);
            livreRepository.saveAll(Arrays.asList(
                    new Livre("La guerre de Troie n'aura pas lieu", "Jean Anouilh", 1, 1,LivreStatut.DIS, categorie4),
                    new Livre("Memoire d'un clown", "Achille Zavatta", 2, 0, LivreStatut.NDIS, categorie5),
                    new Livre("La Legende des Siecles", "Victor Hugo", 3, 2, LivreStatut.DIS, categorie6),
                    new Livre("Le Seigneur de l'Anneau", "JRR Tolkien", 5, 5, LivreStatut.NDIS,categorie7),
                    new Livre("Asterix Le Gaulois", "Uderzo et Goscinny", 10, 10,LivreStatut.NDIS, categorie8),
                    new Livre("Metamorphose des cloportes", "Alphonse Boudard", 1, 1, LivreStatut.DIS, categorie9),
                    new Livre("San Antonio a de la memoire", "Frédéric Dard", 3, 1,LivreStatut.DIS, categorie9),
                    new Livre("Le Gorille joue au clown", "Antoine Dominique", 2, 0,LivreStatut.NDIS, categorie9),
                    new Livre("Tintin et le mystèrez de l'oreille cassée", "Hergé", 6,0, LivreStatut.NDIS, categorie8),
                    new Livre("Alcools", "Apollinaire", 1, 1,LivreStatut.DIS, categorie6)
                    
            ));
            
        
            // search livres by `categorie`
            LivreSpecification lsCategorie = new LivreSpecification();
            lsCategorie.add(new SearchCriteria("categorie", categorie9, SearchOperation.EQUAL));
            List<Livre> lsCategorieList = livreRepository.findAll(lsCategorie);
            System.out.println("***Livres de la catégorie " + categorie9.getNomCategorie()+"***");
            for(Livre livre : lsCategorieList) {
            	System.out.println(livre.getTitre());
            }
          

            // search livres by `titre` and `nbExemplaires` >
            LivreSpecification lsTitreNbExemplaires = new LivreSpecification();
            lsTitreNbExemplaires.add(new SearchCriteria("titre", "le", SearchOperation.MATCH));
            lsTitreNbExemplaires.add(new SearchCriteria("nbExemplaires", 2, SearchOperation.GREATER_THAN));
            List<Livre> lsTitreNbExemplairesList = livreRepository.findAll(lsTitreNbExemplaires);
            System.out.println("***Livres dont le titre contient le mot le et dont le nb d'exemplaire est supérieur à 2***");
            for(Livre livre : lsTitreNbExemplairesList) {
            	System.out.println(livre.getTitre());
            }
           
            
         // search livres by `titre` and `nbExemplairesDisponibles` >
            LivreSpecification lsTitreNbExemplairesDisponibles = new LivreSpecification();
            lsTitreNbExemplairesDisponibles.add(new SearchCriteria("titre", "clown", SearchOperation.MATCH));
            lsTitreNbExemplairesDisponibles.add(new SearchCriteria("nbExemplairesDisponibles", 2, SearchOperation.GREATER_THAN));
            List<Livre> lsTitreNbExemplairesDisponiblesList = livreRepository.findAll(lsTitreNbExemplairesDisponibles);
            System.out.println("***Livres dont le titre est clown et dont le nb d'exemplaires disponibles est supérieur à 2***");
            for(Livre livre : lsTitreNbExemplairesDisponiblesList) {
            	System.out.println(livre.getTitre());
            }
           
            // search livres by nbExemplaires >= 3 and sort by `titre`
            LivreSpecification lsNbExemplairesSup3 = new LivreSpecification();
            lsNbExemplairesSup3.add(new SearchCriteria("nbExemplaires", 3, SearchOperation.GREATER_THAN_EQUAL));
            List<Livre> lsNbExemplairesSup3List = livreRepository.findAll(lsNbExemplairesSup3, Sort.by("titre"));
            System.out.println("***Livres dont le nb d'exemplaires est supérieur ou égal à 3 triés par titre***");
            for (Livre livre : lsNbExemplairesSup3List) {
            	System.out.println(livre.getTitre());
            }
            

            // search livres by `titre` <> 'cloportes' and paginate results
            LivreSpecification lsTitreNonCloportes = new LivreSpecification();
            lsTitreNonCloportes.add(new SearchCriteria("titre", "cloportes", SearchOperation.NOT_EQUAL));
            Pageable pageable = PageRequest.of(0, 6, Sort.by("nbExemplaires").descending());
            Page<Livre> lsTitreNonCloportesList = livreRepository.findAll(lsTitreNonCloportes, pageable);
            System.out.println("***Livres dont le titre ne contient pas le mot cloportes affichés par page de 6***");
            for (Livre livre : lsTitreNonCloportesList) {
            	System.out.println(livre.getTitre());
            }
           
        };
    }
}
