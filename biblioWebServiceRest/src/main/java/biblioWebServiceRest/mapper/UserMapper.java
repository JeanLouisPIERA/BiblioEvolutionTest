/**
 * Interface pour la gestion du mapping MapStruct entre l'entité User et son DTO
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
	
	
	UserDTO userTouserDTO(User entity);
	
	User userDTOToUser(UserDTO dto);

}

