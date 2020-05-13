package biblioWebServiceRest.entities;

public enum PretStatut {
	
	ENCOURS("ENCOURS", "En cours"),
	ECHU("ECHU", "Echu"),
	PROLONGE("PROLONGE", "PROLONGE");
	
	 private String code;
	  private String text;
	  
	private PretStatut(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static PretStatut getPretStatutByCode(String code) {
		for (PretStatut pretStatut : PretStatut.values()) {
			if(pretStatut.code.equals(code)){
				return pretStatut;
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
		if(this==ENCOURS) {
			return"Pret en-cours";
		}else if (this==ECHU) {
			return"Pret échu";
		}else if (this==PROLONGE) {
			return"Pret prolongé";
		}
		return super.toString();
		
		}
	
	  
	  
	  
	
}
