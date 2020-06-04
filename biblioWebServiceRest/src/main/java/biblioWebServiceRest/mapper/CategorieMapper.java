/**
 * Interface pour la gestion du mapping MapStruct entre l'entité Categorie et son DTO
 */
package biblioWebServiceRest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.entities.Categorie;

/**
 * @author jeanl
 *
 */

@Mapper(componentModel="spring")
public interface CategorieMapper {
	
	
	CategorieDTO categorieToCategorieDTO(Categorie entity);
	Categorie categorieDTOToCategorie(CategorieDTO dto);
	
	
	List<CategorieDTO> categoriesToCategorieDTOs(List<Categorie> categories);
	List<Categorie> categorieDTOsToCategories(List<CategorieDTO> categorieDTOs);
	

}
