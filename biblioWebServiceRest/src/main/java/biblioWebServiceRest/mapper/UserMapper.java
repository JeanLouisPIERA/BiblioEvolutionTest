/**
 * 
 */
package biblioWebServiceRest.mapper;

import org.mapstruct.Mapper;

import biblioWebServiceRest.dto.UserDTO;
import biblioWebServiceRest.entities.User;

/**
 * @author jeanl
 *
 */

@Mapper(componentModel="spring")
public interface UserMapper {
	
	
	UserDTO userTouserDTO(User entity);
	User userDTOToUser(UserDTO dto);

}

