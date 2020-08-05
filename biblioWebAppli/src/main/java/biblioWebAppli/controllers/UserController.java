package biblioWebAppli.controllers;




import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import biblioWebAppli.metier.IUserMetier;
import biblioWebAppli.objets.User;





@Controller
public class UserController {

@Autowired
private IUserMetier userMetier;
@Value("${application.username}")
private String applicationUsername;
@Value("${application.password}")
private String applicationPassword;


/**
 * Cette méthode permet d'afficher le formulaire de login
 * @param model
 * @param error
 * @param logout
 * @return
 */
@RequestMapping(value = "/login", method = RequestMethod.GET)
public String login(Model model, String error, String logout) {
	
    return "login";
}

/**
 * Cette méthode permet à l'utilisateur de se logger en saisissant son nom et son mot de passe
 * @param model
 * @param username
 * @param password
 * @return
 * @throws IOException 
 * @throws FileNotFoundException 
 */

@RequestMapping(value="/login", method = RequestMethod.POST)
public String autologin(Model model, @RequestParam("username")String username,  @RequestParam("password")String password) throws FileNotFoundException, IOException   {
	
	if(!username.equals(applicationUsername) || !password.equals(applicationPassword))
		{model.addAttribute("error", "Votre nom d'utilisateur et/ou votre mot de passe sont invalides.");
		return "login";}
	
	try {
		
		
		User userInLogged = userMetier.findByUsernameAndPassword(username, password);
		model.addAttribute("user", userInLogged);
		
		
		
		System.out.println("testnom"+userInLogged.getUsername());
		
		//if (error != null)
	        //model.addAttribute("error", "Votre nom d'utilisateur et/ou votre mot de passe sont invalides.");

	    //if (logout != null)
	        //model.addAttribute("message", "Vous avez bien été déconnecté.");
		
		
	} catch (HttpClientErrorException e) {
        model.addAttribute("error", e.getResponseBodyAsString());
        return"/error";
	}
	return "accueil";
	
}

/**
 * Cette méthode gère l'affichage de la page accueil
 * @param model
 * @return
 */
@RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
public String welcome(Model model) {
    return "accueil";
}

}