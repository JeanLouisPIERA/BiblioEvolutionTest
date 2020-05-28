/**
 * 
 */
package biblioWebServiceRest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;

import biblioWebServiceRest.criteria.PretCriteria;
import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.dto.PretCriteriaDTO;
import biblioWebServiceRest.dto.UserDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.entities.User;



/**
 * @author jeanl
 *
 */

@Mapper(componentModel="spring")

public interface PretCriteriaMapper {
	
	
    @ValueMapping(source = "PretStatut.ENCOURS", target = "PretStatut.ENCOURS")
    @ValueMapping(source = "PretStatut.ECHU", target = "PretStatut.ECHU")
    @ValueMapping(source = "PretStatut.PROLONGE", target = "PretStatut.PROLONGE")
    @ValueMapping(source = "PretStatut.CLOTURE", target = "PretStatut.CLOTURE")
    @Mapping(source="numPret", target="refPret")
	PretCriteriaDTO pretCriteriaToPretCriteriaDTO(PretCriteria entity);
    @Mapping(source="refPret", target="numPret")
	PretCriteria pretCriteriaDTOToPretCriteria(PretCriteriaDTO dto);
	
	LivreDTO livreToLivreDTO(Livre entity);
	Livre livreDTOToLivre(LivreDTO dto);
	
	CategorieDTO categorieToCategorieDTO(Categorie entity);
	Categorie categorieDTOToCategorie(CategorieDTO dto);

	UserDTO userToUserDTO(User entity);
	User userDTOToUser(UserDTO dto);

			
	}
