package hr.fer.amigosi.Entities;

/**
 * Created by ivan_varga on 21/12/2017.
 */
public class IgraEntity {
    private int sifraIgre;
    private String nazivIgre;

    public IgraEntity() {
	}
    
    
    
    public IgraEntity(int sifraIgre, String nazivIgre) {
		super();
		this.sifraIgre = sifraIgre;
		this.nazivIgre = nazivIgre;
	}



	public int getSifraIgre() {
        return sifraIgre;
    }

    public void setSifraIgre(int sifraIgre) {
        this.sifraIgre = sifraIgre;
    }

    public String getNazivIgre() {
        return nazivIgre;
    }

    public void setNazivIgre(String nazivIgre) {
        this.nazivIgre = nazivIgre;
    }
}
