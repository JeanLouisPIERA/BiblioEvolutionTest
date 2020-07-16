/**
 * L'entité Role permet d'identifier les profils d'autorisation des utilisateurs
 */
package biblioWebAppli.objets;


public class Role  {
    
    private Long id;
    private RoleEnum name;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public RoleEnum getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(RoleEnum name) {
		this.name = name;
	}
    
}