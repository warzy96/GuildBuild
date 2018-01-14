package hr.fer.amigosi.guildbuild.entities;

/**
 * Created by Ivan on 08/01/2018.
 */

public class LikEntity {
    private int level;
    private int sifraKlase;
    private String craftingSkills;
    private String nadimak;
    private String imeLika;

    public LikEntity() {
    }



    public LikEntity(int level, int sifraKlase, String craftingSkills, String nadimak, String imeLika) {
        super();
        this.level = level;
        this.sifraKlase = sifraKlase;
        this.craftingSkills = craftingSkills;
        this.nadimak = nadimak;
        this.imeLika = imeLika;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSifraKlase() {
        return sifraKlase;
    }

    public void setSifraKlase(int sifraKlase) {
        this.sifraKlase = sifraKlase;
    }

    public String getCraftingSkills() {
        return craftingSkills;
    }

    public void setCraftingSkills(String craftingSkills) {
        this.craftingSkills = craftingSkills;
    }

    public String getNadimak() {
        return nadimak;
    }

    public void setNadimak(String nadimak) {
        this.nadimak = nadimak;
    }

    public String getImeLika() { return imeLika; }

    public void setImeLika(String imeLika) { this.imeLika = imeLika; }
}
