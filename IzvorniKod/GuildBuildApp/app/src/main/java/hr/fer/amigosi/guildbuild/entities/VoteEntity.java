package hr.fer.amigosi.guildbuild.entities;

/**
 * Created by kerma on 15.1.2018..
 */

public class VoteEntity {

    private int sifraCeha;
    private String nadimak;
    private int brojGlasova;
    private boolean isGlasao;

    public VoteEntity() {
    }

    public VoteEntity(int sifraCeha, String nadimak, int brojGlasova, boolean isGlasao) {
        this.sifraCeha = sifraCeha;
        this.nadimak = nadimak;
        this.brojGlasova = brojGlasova;
        this.isGlasao = isGlasao;
    }

    public int getSifraCeha() {
        return sifraCeha;
    }

    public void setSifraCeha(int sifraCeha) {
        this.sifraCeha = sifraCeha;
    }

    public String getNadimak() {
        return nadimak;
    }

    public void setNadimak(String nadimak) {
        this.nadimak = nadimak;
    }

    public int getBrojGlasova() {
        return brojGlasova;
    }

    public void setBrojGlasova(int brojGlasova) {
        this.brojGlasova = brojGlasova;
    }

    public boolean isGlasao() {
        return isGlasao;
    }

    public void setGlasao(boolean glasao) {
        isGlasao = glasao;
    }
}
