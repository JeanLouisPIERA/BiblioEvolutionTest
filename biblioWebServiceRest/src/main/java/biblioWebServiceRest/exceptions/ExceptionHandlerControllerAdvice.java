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
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionReponse handleCustomException(BookNotAvailableException ex1) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex1.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.NOT_FOUND.value());
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
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ExceptionReponse handleCustomException(EntityNotDeletableException ex2) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex2.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.CONFLICT.value());
		return exceptionReponse;
		
	}
	
	
	
	/**
	 * Cette méthode permet de gérer l'exception personnalisée " Bad Request"
	 * @param ex3
	 * @return
	 */
	@ExceptionHandler (BadRequestException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionReponse handleCustomException(BadRequestException ex3) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setLocalDate(LocalDate.now());
		exceptionReponse.setMessageErreur(ex3.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return exceptionReponse;
		
	}
	
	/**
	 * Cette méthode permet de gérer l'exception personnalisée " Wrong Number"
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
