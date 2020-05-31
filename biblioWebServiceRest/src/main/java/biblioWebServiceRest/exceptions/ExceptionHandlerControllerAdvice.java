/**
 * 
 */
package biblioWebServiceRest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author jeanl
 *
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public ExceptionReponse handleCustomException(NotFoundException ex) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setMessageErreur(ex.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		return exceptionReponse;
	}
		
	@ExceptionHandler(InternalServerErrorException.class)
	@ResponseBody
	public ExceptionReponse handleCustomException(InternalServerErrorException ex1) {
		ExceptionReponse exceptionReponse = new ExceptionReponse();
		exceptionReponse.setMessageErreur(ex1.getMessage());
		exceptionReponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return exceptionReponse;
	}
	

}
