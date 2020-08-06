/**
 * Classe d'implémentation des méthodes Métier pour l'entité User
 */
package biblioWebServiceRest.metier;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.dao.IRoleRepository;
import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.dto.UserDTO;

import biblioWebServiceRest.entities.RoleEnum;
import biblioWebServiceRest.entities.User;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.mapper.UserMapper;


@Service
public class UserMetierImpl implements IUserMetier{
	
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private SecurityServiceImpl securityService;
	@Autowired
	private UserMapper userMapper;

	/**
	 * Cette méthode permet de persister un utilisateur en base de donnéees 
	 */
	@Override
	public User registrateUser(UserDTO userDTO) {
		User userToCreate = userMapper.userDTOToUser(userDTO);
	    userToCreate.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
	    userToCreate.setRole(roleRepository.findByName(RoleEnum.USER));
	    return userRepository.save(userToCreate);
	}
	

	/**
	 * Cette méthode permet d'authentifier un utilisateur par son nom et son mot de passe
	 * @param username
	 * @param password
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Override
	public User findByUsernameAndPassword(String username, String password) throws EntityNotFoundException{
		    User userFound = securityService.autologin(username, password);
		    return userFound;
	}

	
}
