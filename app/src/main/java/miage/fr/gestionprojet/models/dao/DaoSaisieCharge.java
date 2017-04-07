package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;

import java.util.ArrayList;

import miage.fr.gestionprojet.models.SaisieCharge;

/**
 * Created by Audrey on 07/04/2017.
 */

public class DaoSaisieCharge {

    public static ArrayList<SaisieCharge> loadSaisieChargesByDomaine(int idDomaine){
        ArrayList<SaisieCharge> result = new Select()
                .from(SaisieCharge.class)
                .where("domaine=?",idDomaine)
                .execute();
        return result;
    }

    public static ArrayList<SaisieCharge> loadSaisieChargeByUtilisateur(int idUser){
        ArrayList<SaisieCharge> result = new Select()
                .from(SaisieCharge.class)
                .where("resp_ouv=? or resp_oeu=?",idUser,idUser)
                .execute();
        return result;
    }
}
