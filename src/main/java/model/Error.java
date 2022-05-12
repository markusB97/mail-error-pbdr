package model;

public class Error {

    private String anwender;
    private String bestellnummer;
    private String positionsnummer;
    private String datum;
    private String werk;
    private String mail;
    private String id;

    public String getAnwender() {
        return anwender;
    }

    public void setAnwender(String anwender) {
        this.anwender = anwender;
    }

    public String getBestellnummer() {
        return bestellnummer;
    }

    public void setBestellnummer(String bestellnummer) {
        this.bestellnummer = bestellnummer;
    }

    public String getPositionsnummer() {
        return positionsnummer;
    }

    public void setPositionsnummer(String positionsnummer) {
        this.positionsnummer = positionsnummer;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getWerk() {
        return werk;
    }

    public void setWerk(String werk) {
        this.werk = werk;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
