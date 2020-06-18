/**
 * 
 */
package biblioWebAppli.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jeanl
 *
 */
@Controller
public class CategorieController {
	
	@RequestMapping(value="/categories", method = RequestMethod.GET)
	public String viewCategories() {
	   return "categories/categorieListe"; 
	}
	
	@RequestMapping(value="/categories/newCategorie", method = RequestMethod.POST)
	public String addCategorie() {
	   return "categories/categorieCreation";
	}

}
