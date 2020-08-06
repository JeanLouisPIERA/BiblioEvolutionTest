/**
 * 
 */
package biblioBatch.scheduledtasks;

import java.util.List;

import biblioBatch.objets.Pret;



/**
 * Cette interface gère les tâches programmées du batch
 * @author jeanl
 *
 */
public interface IScheduledTasks {
	
	/**
	 * Cette méthode permet d'obtenir la liste des prêts échus à la date du batch
	 * @return
	 */
	Pret[] relancePretsEchus();

}
