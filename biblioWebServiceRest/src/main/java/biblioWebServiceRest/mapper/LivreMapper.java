 /**
 * Interface pour la gestion du mapping MapStruct entre l'entité Livre et son DTO
 */

package biblioWebServiceRest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;

/**
 * @author jeanl
 *
 */

@Mapper(componentModel="spring")
public interface LivreMapper {
	
	
	LivreDTO livreToLivreDTO(Livre entity);
	
	Livre livreDTOToLivre(LivreDTO dto);
	
	//CategorieDTO categorieToCategorieDTO(Categorie entity);
	//Categorie categorieDTOToCategorie(CategorieDTO dto);
	
	List<LivreDTO> livresToLivresDTOs(List<Livre> livres);
	List<Livre> livreDTOsToLivres(List<LivreDTO> livreDTOs);
	

}
