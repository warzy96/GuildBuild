package hr.fer.amigosi.guildbuildservice.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by ivan_varga on 21/12/2017.
 */
@Entity(primaryKeys = {"sifraKlase", "nadimak"},
        foreignKeys = {
                @ForeignKey(entity = KlasaEntity.class, parentColumns = "sifraKlase", childColumns = "sifraKlase", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = KorisnikEntity.class, parentColumns = "nadimak", childColumns = "nadimak", onDelete = ForeignKey.CASCADE)
        })
public class LikEntity {
    private int level;
    private int sifraKlase;
    private String craftingSkills;
    private String nadimak;

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
