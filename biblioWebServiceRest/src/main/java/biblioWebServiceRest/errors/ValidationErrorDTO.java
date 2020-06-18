/**
 * 
 */
package biblioWebServiceRest.errors;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jeanl
 *
 */
public class ValidationErrorDTO {
	 
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();
 
    public ValidationErrorDTO() {
 
    }
 
    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

	/**
	 * @return the fieldErrors
	 */
	public List<FieldErrorDTO> getFieldErrors() {
		return fieldErrors;
	}

	/**
	 * @param fieldErrors the fieldErrors to set
	 */
	public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
    
    

}
