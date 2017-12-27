package hr.fer.amigosi.guildbuildservice.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan_varga on 21/12/2017.
 */

public class Ceh {
    private int sifraCeha;
    private String naziv;
    private int sifraIgre;
    private String opis;
    private List<RegistriraniKorisnik> clanovi;
    private List<Obrazac> obrasci;
    private List<Dogadaj> dogadaji;
    private Map<String, Integer> brojGlasova;

    public Ceh(int sifraCeha, String naziv, int sifraIgre) {
        this.sifraCeha = sifraCeha;
        this.naziv = naziv;
        this.sifraCeha = sifraCeha;
        clanovi = new LinkedList<>();
        obrasci = new LinkedList<>();
        dogadaji = new LinkedList<>();
        brojGlasova = new HashMap<>();
    }
    /*public Ceh(int sifraCeha, String naziv, int sifraIgre, String opis) {
        this(sifraCeha, naziv, sifraIgre);
        this.opis = opis;
    }*/

    //TODO: metode

    public int getSifraCeha() {
        return sifraCeha;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getSifraIgre() {
        return sifraIgre;
    }

    public String getOpis() {
        return opis;
    }

    public List<RegistriraniKorisnik> getClanovi() {
        return clanovi;
    }

    public List<Obrazac> getObrasci() {
        return obrasci;
    }

    public List<Dogadaj> getDogadaji() {
        return dogadaji;
    }

    public Map<String, Integer> getBrojGlasova() {
        return brojGlasova;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
