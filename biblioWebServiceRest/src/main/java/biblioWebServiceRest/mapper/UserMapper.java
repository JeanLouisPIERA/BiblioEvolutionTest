/**
 * 
 */
package biblioWebServiceRest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import biblioWebServiceRest.dto.UserDTO;
import biblioWebServiceRest.entities.User;

/**
 * @author jeanl
 *
 */

@Mapper(componentModel="spring")
public interface UserMapper {
	
	@Mapping(source="username", target="nomEmprunteur")
	UserDTO userTouserDTO(User entity);
	@Mapping(source="nomEmprunteur", target="username")
	User userDTOToUser(UserDTO dto);

}

