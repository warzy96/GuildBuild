package hr.fer.amigosi.guildbuild.entities;

/**
 * Created by kerma on 13.1.2018..
 */

public class PorukaEntity {

    private String primatelj;
    private String posiljatelj;
    private String tekst;
    private String naslov;
    private int idPoruke;

    public PorukaEntity(){

    }

    public PorukaEntity(String primatelj, String posiljatelj, String tekst, String naslov, int idPoruke) {
        super();
        this.primatelj = primatelj;
        this.posiljatelj = posiljatelj;
        this.tekst = tekst;
        this.naslov = naslov;
        this.idPoruke = idPoruke;
    }

    public String getPrimatelj() {
        return primatelj;
    }

    public void setPrimatelj(String primatelj) {
        this.primatelj = primatelj;
    }

    public String getPosiljatelj() {
        return posiljatelj;
    }

    public void setPosiljatelj(String posiljatelj) {
        this.posiljatelj = posiljatelj;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public int getIdPoruke() {
        return idPoruke;
    }

    public void setIdPoruke(int idPoruke) {
        this.idPoruke = idPoruke;
    }
}
