/**
 * Cette classe permet de charger les paramètres de durée initiale et de prolongation du prêt depuis le fichier application.properties
 */
package biblioWebServiceRest.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jeanl
 *
 */

@Component
@ConfigurationProperties("constante-duree")
public class ApplicationPropertiesConfiguration {

    private int dureePret;
    private int dureeProlongation;

    private int dureeAEchoir;

    private int dureeNotification;

	/**
	 * @return the dureePret
	 */
	public int getDureePret() {
		return dureePret;
	}
	/**
	 * @param dureePret the dureePret to set
	 */
	public void setDureePret(int dureePret) {
		this.dureePret = dureePret;
	}
	/**
	 * @return the dureeProlongation
	 */
	public int getDureeProlongation() {
		return dureeProlongation;
	}
	/**
	 * @param dureeProlongation the dureeProlongation to set
	 */
	public void setDureeProlongation(int dureeProlongation) {
		this.dureeProlongation = dureeProlongation;
	}

	public int getDureeAEchoir() {
		return dureeAEchoir;
	}
	public void setDureeAEchoir(int dureeAEchoir) {
		this.dureeAEchoir = dureeAEchoir;
	}
	public int getDureeNotification() {
		return dureeNotification;
	}
	public void setDureeNotification(int dureeNotification) {
		this.dureeNotification = dureeNotification;

	} 
	
	

    
}