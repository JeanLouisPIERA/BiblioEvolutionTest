package biblioWebAppli.objets;



public enum ReservationStatut {
	
	ENREGISTREE("ENREGISTREE", "réservation enregistrée"),
	SUPPRIMEE("SUPPRIMEE", "réservation supprimée"), 
	NOTIFIEE("NOTIFIEE", "réservation notifiée"),
	LIVREE("LIVREE", "réservation livrée"),
	ANNULEE("ANNULEE", "réservation annulée");
	
	 private String code;
	 private String text;
	  
	private ReservationStatut(String code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public static ReservationStatut getReservationStatutByCode(String code) {
		for(ReservationStatut reservationStatut : ReservationStatut.values()) {
			if(reservationStatut.code.equals(code)) {
				return reservationStatut;
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
		if(this==ENREGISTREE) {
			return "Réservation enregistrée";
		}else if(this==SUPPRIMEE) {
			return "Réservation supprimée";
		}else if(this==NOTIFIEE) {
			return "Réservation notifiée";
		}else if(this==LIVREE) {
			return "Réservation livrée";
		}else if(this==ANNULEE) {
			return "Réservation annulée";	
		}
		return super.toString();
	}


}
