/**
 * 
 */
package biblioWebServiceRest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import biblioWebServiceRest.dto.LivreDTO;
import biblioWebServiceRest.entities.Livre;




/**
 * @author jeanl
 *
 */
@Mapper(componentModel="spring")
public interface LivreMapper {
	
	LivreMapper INSTANCE = Mappers.getMapper(LivreMapper.class);
	LivreDTO livreToLivreDTO(Livre entity);
	Livre livreDTOToLivre(LivreDTO dto);
	
	
	

}
