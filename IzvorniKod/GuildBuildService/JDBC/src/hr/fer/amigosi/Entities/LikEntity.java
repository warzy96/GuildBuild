package hr.fer.amigosi.Entities;

/**
 * Created by ivan_varga on 21/12/2017.
 */
public class LikEntity {
    private int level;
    private int sifraKlase;
    private String craftingSkills;
    private String nadimak;

    public LikEntity() {
	}
    
    
    
    public LikEntity(int level, int sifraKlase, String craftingSkills, String nadimak) {
		super();
		this.level = level;
		this.sifraKlase = sifraKlase;
		this.craftingSkills = craftingSkills;
		this.nadimak = nadimak;
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
}
