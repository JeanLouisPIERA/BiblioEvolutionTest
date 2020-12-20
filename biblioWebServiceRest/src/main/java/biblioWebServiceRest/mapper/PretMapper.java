/**
 * Interface pour la gestion du mapping MapStruct entre l'entité Pret et son DTO
 */
package biblioWebServiceRest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;

import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.dto.PretDTO;
import biblioWebServiceRest.dto.UserDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.Pret;
import biblioWebServiceRest.entities.User;



/**
 * @author jeanl
 *
 */

@Mapper(componentModel="spring")

public interface PretMapper {
	
		@ValueMapping(source = "PretStatut.ENCOURS", target = "PretStatut.ENCOURS")
	    @ValueMapping(source = "PretStatut.ECHU", target = "PretStatut.ECHU")
	    @ValueMapping(source = "PretStatut.PROLONGE", target = "PretStatut.PROLONGE")
	    @ValueMapping(source = "PretStatut.CLOTURE", target = "PretStatut.CLOTURE")
		PretDTO pretToPretDTO(Pret entity);
		
		Pret pretDTOToPret(PretDTO dto);
		
		LivreDTO livreToLivreDTO(Livre entity);
		Livre livreDTOToLivre(LivreDTO dto);
	
		UserDTO userToUserDTO(User entity);
		User userDTOToUser(UserDTO dto);
		
		
		List<PretDTO> pretsToPretsDTOs(List<Pret> prets);
		List<Pret> pretDTOsToPret(List<PretDTO> pretDTOs);
		
}
