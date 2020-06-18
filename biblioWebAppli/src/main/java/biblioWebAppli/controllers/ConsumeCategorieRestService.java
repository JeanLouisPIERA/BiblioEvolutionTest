/**
 * 
 */
package biblioWebAppli.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import biblioWebServiceRest.dto.CategorieDTO;
import biblioWebServiceRest.entities.Categorie;



/**
 * @author jeanl
 *
 */
@RestController
public class ConsumeCategorieRestService {
	
	@Autowired
	   RestTemplate restTemplate;

	   @RequestMapping(value = "/template/categories", method = RequestMethod.GET)
	   public String getCategorieListe() {
	      HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange(
	         "http://localhost:8090/categories", HttpMethod.GET, entity, String.class).getBody();
	   }
	   @RequestMapping(value = "/template/categories", method = RequestMethod.POST)
	   public String createCategorie(@RequestBody CategorieDTO categorie) {
	      HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<CategorieDTO> entity = new HttpEntity<CategorieDTO>(categorie,headers);
	      
	      return restTemplate.exchange(
	         "http://localhost:8090/categories", HttpMethod.POST, entity, String.class).getBody();
	   }
	   @RequestMapping(value = "/template/categories/{numCategorie}", method = RequestMethod.DELETE)
	   public String deleteCategorie(@PathVariable("numCategorie") Long numCategorie) {
	      HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<Categorie> entity = new HttpEntity<Categorie>(headers);
	      
	      return restTemplate.exchange(
	         "http://localhost:8090/categories/"+numCategorie, HttpMethod.DELETE, entity, String.class).getBody();
	   }
	}


