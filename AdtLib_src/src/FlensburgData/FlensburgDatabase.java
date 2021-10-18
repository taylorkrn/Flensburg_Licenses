package FlensburgData;

import hashing.KennzeichnenHashTable;
import hashing.AusweisHashTable;

public class FlensburgDatabase {

        private AusweisHashTable ausweisNummer;

        private KennzeichnenHashTable kfzKennzeichnen;

    /**
     * Constructor
     *
     */
        public FlensburgDatabase(){
            ausweisNummer = new AusweisHashTable(31);
            kfzKennzeichnen = new KennzeichnenHashTable(31);
        }

    /**
     * Adds a person to both HashTables
     * @param person The element to be added to both HashTables
     *
     */
        public synchronized Boolean add(FlensburgPerson person){
            ausweisNummer.add(person);
            kfzKennzeichnen.add(person);
            return true;
        }

    /**
     * Searches for a person using the personalausweisnummer
     * @param ausweis The element to be searched for
     *
     */
        public synchronized FlensburgPerson search(int ausweis){
            return ausweisNummer.search(ausweis);
        }

        /**
     * Searches for a person using the kennzeichnen
     * @param kfz The element to be searched for
     *
     */
        public synchronized FlensburgPerson search(String kfz){
            if(kfzKennzeichnen.isEmpty()){
                return null;
            } else {
                return kfzKennzeichnen.search(kfz);
            }
        }

    /**
     * Removes a person using the kennzeichnen
     * @param kfz The element to be searched for and removed
     *
     */
        public synchronized FlensburgPerson remove(String kfz){
            FlensburgPerson found = kfzKennzeichnen.remove(kfz);
            return ausweisNummer.remove(found.ausweisHashCode());
        }

    /**
     * Removes a person using the personalausweisnummer
     * @param ausweis The element to be searched for and removed
     *
     */
        public synchronized FlensburgPerson remove(int ausweis){
            FlensburgPerson found = ausweisNummer.remove(ausweis);
            return kfzKennzeichnen.remove(found.getKfz());
        }
}
