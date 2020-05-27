/**
 * 
 */
package biblioWebServiceRest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

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
	
	CategorieDTO categorieToCategorieDTO(Categorie entity);
	Categorie categorieDTOToCategorie(CategorieDTO dto);
	
	List<LivreDTO> livresToLivresDTOs(List<Livre> livres);

}
