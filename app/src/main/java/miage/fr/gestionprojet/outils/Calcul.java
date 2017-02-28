package miage.fr.gestionprojet.outils;

/**
 * Created by Audrey on 27/02/2017.
 */

public class Calcul {

    public static int calculerPourcentage(double valeurReleve, double valeurCible){
        int result = (int) ((valeurReleve/valeurCible)*100);
        return result;
    }
}
