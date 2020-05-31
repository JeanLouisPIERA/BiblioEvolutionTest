/**
 * Ce package permet une gestion globale des exceptions
 */
package biblioWebServiceRest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Cette classe permet de centraliser et généraliser les méthodes @ExceptionHandler 
 * @author jeanl
 *
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	/**
	 * Cette méthode permet de gérer l'exception client 404
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public ExceptionReponse handleCustomException(NotFoundException ex) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setMessageErreur(ex.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		return exceptionReponse;
	}
		
	/**
	 * Cette méthode permet de gérer les exceptions seveur 500
	 * @param ex1
	 * @return
	 */
	@ExceptionHandler(InternalServerErrorException.class)
	@ResponseBody
	public ExceptionReponse handleCustomException(InternalServerErrorException ex1) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setMessageErreur(ex1.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return exceptionReponse;
	}
	

}
