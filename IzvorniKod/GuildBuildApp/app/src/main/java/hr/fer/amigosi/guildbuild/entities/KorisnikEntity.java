package hr.fer.amigosi.guildbuild.entities;

/**
 * Created by Ivan on 08/01/2018.
 */

public class KorisnikEntity {

    private String email;
    private String nadimak;
    private String lozinka;
    private boolean statusRegistracije;
    private String rang;
    private String sifraCeha;
    private boolean statusPrijave;
    private String opisKorisnika;
    private boolean isAdmin;

    public KorisnikEntity() {
    }



    public KorisnikEntity(String email, String nadimak, String lozinka, boolean statusRegistracije, String rang,
                          String sifraCeha, boolean statusPrijave, String opisKorisnika, boolean isAdmin) {
        super();
        this.email = email;
        this.nadimak = nadimak;
        this.lozinka = lozinka;
        this.statusRegistracije = statusRegistracije;
        this.rang = rang;
        this.sifraCeha = sifraCeha;
        this.statusPrijave = statusPrijave;
        this.opisKorisnika = opisKorisnika;
        this.isAdmin = isAdmin;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNadimak() {
        return nadimak;
    }

    public void setNadimak(String nadimak) {
        this.nadimak = nadimak;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public boolean isStatusRegistracije() {
        return statusRegistracije;
    }

    public void setStatusRegistracije(boolean statusRegistracije) {
        this.statusRegistracije = statusRegistracije;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public String getSifraCeha() {
        return sifraCeha;
    }

    public void setSifraCeha(String sifraCeha) {
        this.sifraCeha = sifraCeha;
    }

    public boolean isStatusPrijave() {
        return statusPrijave;
    }

    public void setStatusPrijave(boolean statusPrijave) {
        this.statusPrijave = statusPrijave;
    }

    public String getOpisKorisnika() {
        return opisKorisnika;
    }

    public void setOpisKorisnika(String opisKorisnika) {
        this.opisKorisnika = opisKorisnika;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
