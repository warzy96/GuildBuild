package hr.fer.amigosi.guildbuildservice.model;

/**
 * Created by Ivan on 21/12/2017.
 */

public class Lik {
    private int level;
    private int sifraKlase;
    private String craftingSkills;

    public Lik(int level, int sifraKlase, String craftingSkills) {
        this.level = level;
        this.sifraKlase = sifraKlase;
        this.craftingSkills = craftingSkills;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCraftingSkills() {
        return craftingSkills;
    }

    public void setCraftingSkills(String craftingSkills) {
        this.craftingSkills = craftingSkills;
    }

    public int getSifraKlase() {
        return sifraKlase;
    }
}
