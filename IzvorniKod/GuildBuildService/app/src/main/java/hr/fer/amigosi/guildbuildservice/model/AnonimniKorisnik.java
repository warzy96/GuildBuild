package hr.fer.amigosi.guildbuildservice.model;

/**
 * Created by ivan_varga on 21/12/2017.
 */

public class AnonimniKorisnik {
    private String email;
    private String nadimak;
    private String lozinka;
    private boolean statusRegistracije;

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
}
