package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.models.SaisieCharge;

/**
 * Created by Audrey on 07/04/2017.
 */

public class DaoSaisieCharge {

    public static ArrayList<SaisieCharge> loadSaisieChargesByDomaine(int idDomaine){
        ArrayList<SaisieCharge> lst = new ArrayList<>();
        ArrayList<Action> results = new Select()
                .from(Action.class)
                .where("domaine=?",idDomaine)
                .execute();
        for(Action a : results) {
            SaisieCharge result  = (SaisieCharge) new Select()
                    .from(SaisieCharge.class)
                    .where("domaine=?", a.getId())
                    .execute().get(0);
            lst.add(result);

        }
        return lst;
    }

    public static ArrayList<SaisieCharge> loadSaisieChargeByUtilisateur(int idUser){


        ArrayList<SaisieCharge> lst = new ArrayList<>();
        ArrayList<Action> results = new Select()
                .from(Action.class)
                .where("resp_ouv=? or resp_oeu=?",idUser,idUser)
                .execute();
        for(Action a : results) {
            SaisieCharge result  = (SaisieCharge) new Select()
                    .from(SaisieCharge.class)
                    .where("domaine=?", a.getId())
                    .execute().get(0);
            lst.add(result);

        }
        return lst;
    }

    public static SaisieCharge loadSaisieChargeByAction(long idAction){
        ArrayList<SaisieCharge> lst = new Select()
                .from(SaisieCharge.class)
                .where("action = ?", idAction)
                .execute();
        if(lst.size()>0) {
            return lst.get(0);
        }else{
            return null;
        }
    }
}
