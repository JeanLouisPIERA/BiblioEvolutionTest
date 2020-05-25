/**
 * 
 */
package biblioWebServiceRest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.entities.Categorie;

/**
 * @author jeanl
 *
 */
@Mapper(componentModel="spring")
public interface CategorieMapper {
	
	CategorieMapper INSTANCE = Mappers.getMapper(CategorieMapper.class);
	CategorieDTO categorieToCategorieDTO(Categorie entity);
	Categorie categorieDTOToCategorie(CategorieDTO dto);

}
