/**
 * L'enumération PretStatut permet de gérer une propriété de l'entité Livre et identifie 4 statuts selon la situation du pret
 */
package biblioWebServiceRest.entities;



public enum ReservationStatut {
	
	ENREGISTREE("ENREGISTREE", "réservation enregistrée"),
	SUPPRIMEE("SUPPRIMEE", "réservation supprimée"), 
	NOTIFIEE("NOTIFIEE", "réservation notifiée"),
	LIVREE("LIVREE", "réservation livrée"),
	ANNULEE("ANNULEE", "réservation annulée"),
	INCONNUE("INCONNUE", "statut réservation inconnu");
	
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
		}else if(this==INCONNUE) {
			return "Statut Reservation Inconnu";	
		}
		return super.toString();
	}
	
	
	public static ReservationStatut fromValueCode(String code) {
		try {
	        return valueOf(code);
	    } catch (IllegalArgumentException e) {
	        return ReservationStatut.INCONNUE;
	    }
	}
	

}
