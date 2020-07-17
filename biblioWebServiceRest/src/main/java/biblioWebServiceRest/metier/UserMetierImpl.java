/**
 * Classe d'implémentation des méthodes Métier pour l'entité User
 */
package biblioWebServiceRest.metier;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.dao.IRoleRepository;
import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.entities.Role;
import biblioWebServiceRest.entities.RoleEnum;
import biblioWebServiceRest.entities.User;


@Service
public class UserMetierImpl implements IUserMetier{
	
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Cette méthode permet de persister un utilisateur en base de donnéees 
	 */
	@Override
	public void save(User user) {
	    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    user.setRole(roleRepository.findByName(RoleEnum.USER));
	    userRepository.save(user);
	}

	/**
	 * Cette méthode permet de rechercher par son nom un utilisateur persisté en base de données
	 */
	@Override
	public User findByUsername(String username) {
	    return userRepository.findByUsername(username).get();
	}

	/**
	 * Cette méthode permet de créer un jeu de données Utilisateur dans le Main au moment du lancement de l'appli 
	 */
	@Override
	public User createUser(String username) {
		User u = new User(username);
		Role r = roleRepository.findByName(RoleEnum.USER);
		u.setAdresseMail(username.concat("@hotmail.com"));
		u.setRole(r);
		u.setPassword("$2a$10$8kVCmqZNmEu7ihwunzaNN.KxnFMn1HuDmBcj1O.mOK24gJ15C5b06");
		userRepository.save(u);
		return u;
	}

	/**
	 * Cette méthode permet de créer un administrateur dans le Main au moment du lancement de l'appli
	 */
	@Override
	public User createAdmin(String username) {
		User u = new User(username);
		Role r = roleRepository.findByName(RoleEnum.ADMIN);
		u.setAdresseMail(username.concat("@hotmail.com"));
		u.setRole(r);
		u.setPassword("$2a$10$8kVCmqZNmEu7ihwunzaNN.KxnFMn1HuDmBcj1O.mOK24gJ15C5b06");
		
		userRepository.save(u);
		
		
		return u;
	}

	
}
