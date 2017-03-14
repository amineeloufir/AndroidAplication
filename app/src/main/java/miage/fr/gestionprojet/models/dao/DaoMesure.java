package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import miage.fr.gestionprojet.models.Mesure;
import miage.fr.gestionprojet.models.SaisieCharge;

/**
 * Created by Audrey on 27/02/2017.
 */

public class DaoMesure {

    public Mesure getLastMesureBySaisieCharge(SaisieCharge saisieCharge){
        ArrayList<Mesure> lstMesures =
                new Select()
                .from(Mesure.class)
                .where("action=?", saisieCharge.getId())
                .orderBy("dt_mesure DESC")
                .execute();
        if(lstMesures.size()>0){
            return  lstMesures.get(0);
        }else{
            return new Mesure();
        }
    }
}
