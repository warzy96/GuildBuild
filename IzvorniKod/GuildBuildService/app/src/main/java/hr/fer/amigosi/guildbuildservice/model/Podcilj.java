package hr.fer.amigosi.guildbuildservice.model;

/**
 * Created by ivan_varga on 21/12/2017.
 */

public class Podcilj {
    private int sifraPodcilja;
    private String nazivPodcilja;
    private boolean ispunjenost;

    public Podcilj(int sifraPodcilja, String nazivPodcilja) {
        this.sifraPodcilja = sifraPodcilja;
        this.nazivPodcilja = nazivPodcilja;
    }

    public String getNazivPodcilja() {
        return nazivPodcilja;
    }

    public void setNazivPodcilja(String nazivPodcilja) {
        this.nazivPodcilja = nazivPodcilja;
    }

    public boolean isIspunjenost() {
        return ispunjenost;
    }

    public void setIspunjenost(boolean ispunjenost) {
        this.ispunjenost = ispunjenost;
    }

    public int getSifraPodcilja() {
        return sifraPodcilja;
    }
}
