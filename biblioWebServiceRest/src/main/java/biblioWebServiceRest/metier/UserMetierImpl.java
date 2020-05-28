package biblioWebServiceRest.metier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.entities.User;

@Service
@Transactional
public class UserMetierImpl implements IUserMetier{
	
	@Autowired
	IUserRepository userRepository;

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}
	
}
