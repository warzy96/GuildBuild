package hr.fer.amigosi.guildbuild.entities;

/**
 * Created by Ivan on 08/01/2018.
 */

public class PodciljEntity {
    private int sifraCilja;
    private int sifraPodcilja;
    private String nazivPodcilja;
    private boolean ispunjenost;



    public PodciljEntity(int sifraCilja, int sifraPodcilja, String nazivPodcilja, boolean ispunjenost) {
        super();
        this.sifraCilja = sifraCilja;
        this.sifraPodcilja = sifraPodcilja;
        this.nazivPodcilja = nazivPodcilja;
        this.ispunjenost = ispunjenost;
    }

    public PodciljEntity() {
    }

    public int getSifraCilja() {
        return sifraCilja;
    }

    public void setSifraCilja(int sifraCilja) {
        this.sifraCilja = sifraCilja;
    }

    public int getSifraPodcilja() {
        return sifraPodcilja;
    }

    public void setSifraPodcilja(int sifraPodcilja) {
        this.sifraPodcilja = sifraPodcilja;
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
}
