/**
 * 
 */
package biblioWebServiceRest.metier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biblioWebServiceRest.dao.IUserRepository;
import biblioWebServiceRest.entities.Role;
import biblioWebServiceRest.entities.User;


/**
 * @author jeanl
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    /**
     * Cette méthode implémente l'interface UserDetailsService de Spring Security et permet de la customiser
     * le processus de retouver un utilisateur persisté en base de données.
     * Elle est utilisée par le processus d'Authentication pour charger les détails sur l'utilisateur pendant 
     * l'authentification 
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found"); 
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        
        Role role = user.get().getRole();
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().toString()));
        
        
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), grantedAuthorities);
        
    }
}
