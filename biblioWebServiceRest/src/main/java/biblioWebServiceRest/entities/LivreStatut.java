package biblioWebServiceRest.entities;

public enum LivreStatut {
	
	DIS ("DIS", "Disponible"),
	NDIS ("NDIS", "Non Disponible");
	
	
	private String code;
	private String text;
	
	private LivreStatut(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static LivreStatut getLivreStatutByCode(String code) {
		for (LivreStatut livreStatut : LivreStatut.values()) {
			if(livreStatut.code.equals(code)){
				return livreStatut;
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
		if(this==DIS) {
			return"Disponible";
		}else if (this==NDIS) {
			return"Non Disponible";
		}
		return super.toString();
		
		}
	

}
