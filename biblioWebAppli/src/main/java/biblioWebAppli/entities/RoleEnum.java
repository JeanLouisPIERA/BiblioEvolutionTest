/**
 * L'énumération RoleEnum permet d'identifier les différents profils d'autorisation donnés à l'entité Role
 */
package biblioWebAppli.entities;

public enum RoleEnum {
	
	USER ("USER", "USER"),
	ADMIN ("ADMIN", "ADMIN");
	
	 private String code;
	  private String text;
	  
	private RoleEnum(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static RoleEnum getRoleEnumByCode(String code) {
		for (RoleEnum roleEnum : RoleEnum.values()) {
			if(roleEnum.code.equals(code)){
				return roleEnum;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		if(this==USER) {
			return"USER";
		}else if (this==ADMIN) {
			return"ADMIN";
		}
		return super.toString();
	}   
}
