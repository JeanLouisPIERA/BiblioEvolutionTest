/**
 * Ce package permet une gestion globale des exceptions
 */
package biblioWebServiceRest.exceptions;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.MethodNotAllowed;

/**
 * Cette classe permet de centraliser et généraliser les méthodes @ExceptionHandler 
 * @author jeanl
 *
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	/**
	 * Cette méthode permet de gérer l'exception métier personnalisée "Entity Not Found"
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionReponse handleCustomException(EntityNotFoundException ex) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		return exceptionReponse;
	}
	
		
	/**
	 * Cette méthode permet de gérer l'exception métier personnalisée " Book Not Available "
	 * Voir LivreMetier
	 * @param ex1
	 * @return
	 */
	@ExceptionHandler(BookNotAvailableException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.LOCKED)
	public ExceptionReponse handleCustomException(BookNotAvailableException ex1) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex1.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.LOCKED.value());
		return exceptionReponse;
	}
	
	
	/**
	 * Cette méthode permet de gérer l'exception métier personnalisée " Book Available "
	 * Voir ReservationMetier
	 * @param ex1
	 * @return
	 */
	@ExceptionHandler(BookAvailableException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
	public ExceptionReponse handleCustomException(BookAvailableException ex1) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex1.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
		return exceptionReponse;
	}
	
	
	/**
	 * Cette méthode permet de gérer l'exception personnalisée " Entity Already Exists "
	 * Voir CategorieMetier et LivreMetier
	 * @param ex2
	 * @return
	 */
	@ExceptionHandler (EntityAlreadyExistsException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ExceptionReponse handleCustomException(EntityAlreadyExistsException ex2) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex2.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.CONFLICT.value());
		return exceptionReponse;
		
	}
	
	
	/**
	 * Cette méthode permet de gérer l'exception personnalisée " Entity Not Deletable Exists "
	 * Voir CategorieMetier et LivreMetier
	 * @param ex2
	 * @return
	 */
	
	
	@ExceptionHandler (EntityNotDeletableException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
	public ExceptionReponse handleCustomException(EntityNotDeletableException ex3) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex3.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.PRECONDITION_FAILED.value());
		return exceptionReponse;
		
	}
	
	
	/**
	 * Cette méthode permet de gérer l'exception personnalisée " Wrong Number"
	 * Utilisée dans la méthode métier livre UPDATE pour la mise à jour du nombre d'exemplaires
	 * @param ex3
	 * @return
	 */
	@ExceptionHandler (WrongNumberException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionReponse handleCustomException(WrongNumberException ex4) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex4.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return exceptionReponse;
		
	}
	
	
	
	
	
	
}
