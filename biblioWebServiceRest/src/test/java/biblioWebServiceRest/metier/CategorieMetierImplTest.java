package biblioWebServiceRest.metier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import biblioWebServiceRest.dao.ICategorieRepository;
import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Reservation;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotFoundException;
import biblioWebServiceRest.mapper.CategorieMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategorieMetierImplTest {

	@Mock
	private ICategorieRepository categorieRepository;
	
	@Mock
	private CategorieMapper categorieMapper;
	
	@InjectMocks
	private CategorieMetierImpl categorieMetierImpl;
	
	@Before 
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
	}
	
	
	@Test 
	public void createCategorieTest_ReturnEntityAlreadyExistsException() {
		
		CategorieDTO newCategorieDTO = new CategorieDTO();
		newCategorieDTO.setNomCategorie("categorie1");
		
		Categorie categorie1 = new Categorie();
		categorie1.setNomCategorie("categorie1");
		
		when(categorieMapper.categorieDTOToCategorie(newCategorieDTO)).thenReturn(categorie1);
		when(categorieRepository.findByNomCategorie("categorie1")).thenReturn(Optional.of(categorie1));
		
		try {
			Categorie categorieTest = categorieMetierImpl.createCategorie(newCategorieDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
						 .hasMessage("La categorie que vous souhaitez creer existe deja");
		}
		
		
		
	}
	
	
	
	public CategorieMetierImplTest() {
		// TODO Auto-generated constructor stub
	}

}
