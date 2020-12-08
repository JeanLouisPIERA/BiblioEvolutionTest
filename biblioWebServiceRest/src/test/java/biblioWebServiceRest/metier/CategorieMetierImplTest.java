package biblioWebServiceRest.metier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import biblioWebServiceRest.criteria.CategorieCriteria;
import biblioWebServiceRest.dao.ICategorieRepository;
import biblioWebServiceRest.dao.specs.CategorieSpecification;
import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.entities.Categorie;
import biblioWebServiceRest.entities.Livre;
import biblioWebServiceRest.exceptions.EntityAlreadyExistsException;
import biblioWebServiceRest.exceptions.EntityNotDeletableException;
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
	private CategorieMetierImpl categorieMetier;
	
	@Before 
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		/*
	     * Jeu de données pour les use cases READ
	     */
		Categorie categorie1 = new Categorie();
		Categorie categorie2 = new Categorie();
		
		List<Categorie> categorieList1 = Arrays.asList(categorie1, categorie2);
		
		Page<Categorie> categoriePage1 = new PageImpl<Categorie>(categorieList1);
	    Mockito.when(categorieRepository.findAll(any(CategorieSpecification.class), any(Pageable.class))).thenReturn(categoriePage1);
	
	    Mockito.when(categorieRepository.findById((long)1)).thenReturn(Optional.of(categorie1));
		
		
	}
	
	//**********************************************TEST CREATE ***********************************************************
	
	@Test 
	public void testCreateCategorie_whenEntityAlreadyExistsException() {
		
		CategorieDTO newCategorieDTO = new CategorieDTO();
		newCategorieDTO.setNomCategorie("categorie1");
		
		Categorie categorie1 = new Categorie();
		categorie1.setNomCategorie("categorie1");
		
		when(categorieMapper.categorieDTOToCategorie(newCategorieDTO)).thenReturn(categorie1);
		when(categorieRepository.findByNomCategorie("categorie1")).thenReturn(Optional.of(categorie1));
		
		try {
			Categorie categorieTest = categorieMetier.createCategorie(newCategorieDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
						 .hasMessage("La categorie que vous souhaitez creer existe deja");
		}	
	}
	
	@Test
	public void testCreateCategorie_withoutException() {
		
		CategorieDTO newCategorieDTO = new CategorieDTO();
		newCategorieDTO.setNomCategorie("categorie1");
		
		Categorie categorie1 = new Categorie("categorie1");
	
		when(categorieMapper.categorieDTOToCategorie(newCategorieDTO)).thenReturn(categorie1);
		when(categorieRepository.save(any(Categorie.class))).thenReturn(categorie1);
		
		try {
			Categorie categorieTest = categorieMetier.createCategorie(newCategorieDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
						 .hasMessage("La categorie que vous souhaitez creer existe deja");
		}	
	}

	
	//******************************************** TEST DELETE ***************************************************************
	
	@Test
	public void testDeleteCategorie_whenEntityNotFoundException() {
		
		try {
			categorieMetier.deleteCategorie((long)0);
		} catch (EntityNotFoundException | EntityNotDeletableException e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("La categorie que vous voulez supprimer n'existe pas");
		}
	}
	
	@Test
	public void testDeleteCategorie_whenEntityNotDeletableException() {
		
		Categorie categorie1 = new Categorie("categorie1");
		Livre livre = new Livre();
		List<Livre> livreList = Arrays.asList(livre);
		categorie1.setLivres(livreList);
		
		when(categorieRepository.findById((long)1)).thenReturn(Optional.of(categorie1));
			
		try {
			categorieMetier.deleteCategorie((long)1);
		} catch (EntityNotFoundException | EntityNotDeletableException e) {
			assertThat(e).isInstanceOf(EntityNotDeletableException.class)
			 .hasMessage("Vous ne pouvez pas supprimer cette categorie qui contient des livres");
		}
	}
	
	@Test
	public void testDeleteCategorie_withoutException() {
		
		Categorie categorie1 = new Categorie("categorie1");
		List<Livre> livreList = Arrays.asList();
		categorie1.setLivres(livreList);
		
		when(categorieRepository.findById((long)1)).thenReturn(Optional.of(categorie1));	
		doNothing().when(categorieRepository).deleteById((long)1);
		
		try {
			categorieMetier.deleteCategorie((long)1);
		} catch (EntityNotFoundException e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
			 .hasMessage("La categorie que vous voulez supprimer n'existe pas");
		} catch (EntityNotDeletableException e) {
			assertThat(e).isInstanceOf(EntityNotDeletableException.class)
			 .hasMessage("Vous ne pouvez pas supprimer cette categorie qui contient des livres");
		}
	}
	
	//********************************* TESTS SEARCH By CRITERIA *******************************************************
	
	@Test
	public void testSearchByCriteria_withoutExceptionAndAllAttributesNull() {
		
		CategorieCriteria categorieCriteria = new CategorieCriteria();
		Pageable pageable = PageRequest.of(0,6);
		
		Page<Categorie> categoriePageTest = categorieMetier.searchByCriteria(categorieCriteria, pageable);
		Assertions.assertTrue(categoriePageTest.getContent().size()==2);
	}
	
	
}

