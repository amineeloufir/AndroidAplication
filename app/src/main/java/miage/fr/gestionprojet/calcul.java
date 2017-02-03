package miage.fr.gestionprojet;

/**
 * Created by Audrey on 01/02/2017.
 */

public class Calcul {

    public static int calculerPourcentage(double valeurReleve, double valeurCible){
        int result = (int) ((valeurReleve/valeurCible)*100);
        return result;
    }
}
