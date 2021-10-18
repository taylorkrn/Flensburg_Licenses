package FlensburgData;

public class FlensburgPerson {

    private String vorname;

    private String nachname;

    private Integer personalausweisNummer;

    private String kfzKennzeichnen;

    private int strafPunkte;

    /**
     * Constructor
     * @param vorname
     * @param nachname
     * @param personalausweisNummer
     * @param kfzKennzeichnen
     * @param strafPunkte
     *
     */
    public FlensburgPerson(String vorname, String nachname, Integer personalausweisNummer, String kfzKennzeichnen, int strafPunkte){
        this.vorname = vorname;
        this.nachname = nachname;
        this.personalausweisNummer = personalausweisNummer;
        this.kfzKennzeichnen = kfzKennzeichnen;
        this.strafPunkte = strafPunkte;
    }
    public String getNachname(){
        return this.nachname;
    }

    public String getVorname(){
        return this.vorname;
    }

    public int getStrafe(){
        return this.strafPunkte;
    }

    public String getKfz(){
        return this.kfzKennzeichnen;
    }

    public Integer getAusweis() {return this.personalausweisNummer;}

    public int ausweisHashCode(){
        return this.getAusweis().hashCode();
    }

    public boolean ausweisEquals(Integer ausweis){
        if (ausweis.hashCode() == this.ausweisHashCode()){
            return true;
        }
        return false;
    }

    public int kfzHashCode(){
        return this.getKfz().hashCode();
    }

    public boolean kfzEquals(String kfz){
        if (kfz.hashCode() == this.kfzHashCode()){
            return true;
        }
        return false;
    }

    public boolean equals(FlensburgPerson test) {
        return (nachname.equals(test.getNachname()) && vorname.equals(test.getVorname()) && personalausweisNummer.equals(test.getAusweis()) && kfzKennzeichnen.equals(test.getKfz()) && strafPunkte == test.getStrafe());
    }
}
