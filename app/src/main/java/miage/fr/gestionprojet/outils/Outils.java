package miage.fr.gestionprojet.outils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Audrey on 27/02/2017.
 */

public class Outils {

    public static int calculerPourcentage(double valeurReleve, double valeurCible){
        int result = (int) ((valeurReleve/valeurCible)*100);
        return result;
    }

    public static Date weekOfYearToDate(int week, int year){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.WEEK_OF_YEAR,week);
        c.set(Calendar.YEAR,year);
        return c.getTime();
    }
}
