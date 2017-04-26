package miage.fr.gestionprojet.models.dao;

import android.database.Cursor;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import miage.fr.gestionprojet.models.Action;

/**
 * Created by Audrey on 07/04/2017.
 */

public class DaoAction {

    public static List<Action> loadActionsByType(String type) {
        List<Action> actions = new Select().from(Action.class)
                .where("typeTravail = ?", type)
                .execute();
        return actions;
    }

    public static List<Action> loadActionsByPhaseAndDate(String phase,Date d) {
        List<Action> actions = new Select().from(Action.class)
                .where("phase = ? and dt_fin_prevue>=? and dt_debut<=?", phase,d.getTime(),d.getTime())
                .execute();
        return actions;
    }

    public static List<Action> loadActionsByDate(Date d) {
        //getAll
        List<Action> actions = new Select()
                .from(Action.class)
                .where("dt_fin_prevue>=? and dt_debut<=?",d.getTime(),d.getTime())
                .execute();
        return actions;
    }

    public static List<Action> loadAll(){
        List<Action> actions = new Select().from(Action.class).execute();
        return actions;
    }

    public static List<Action> loadActionsOrderByNomAndDate(Date d){
        List<Action> actions = new Select()
                .from(Action.class)
                .where("dt_fin_prevue>=? and dt_debut<=?",d.getTime(),d.getTime())
                .orderBy("code ASC")
                .execute();
        return actions;
    }

    public static ArrayList<Action> loadActionsByDomaineAndDate(int idDomaine,Date d){
        ArrayList<Action> result = new Select()
                .from(Action.class)
                .where("domaine=? and dt_fin_prevue>=? and dt_debut<=?",idDomaine,d.getTime(),d.getTime())
                .execute();
        return result;
    }
    public static ArrayList<Action> getActionbyCode(String id) {
        return new Select()
                .from(Action.class)
                .where("code = ?", id)
                .execute();
    }

    public static HashMap<String,Integer> getNbActionRealiseeGroupByDomaine(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT COUNT(*) as total, domaine FROM " + new Action().getTableName() + " WHERE reste_a_faire=0 GROUP BY domaine", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
               lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }

    public static HashMap<String,Integer> getNbActionTotalGroupByDomaine(){
        Cursor c = ActiveAndroid
                .getDatabase()
                .rawQuery("SELECT COUNT(*) as total, domaine FROM " + new Action().getTableName() + " GROUP BY domaine", null);
        HashMap<String,Integer> lstResult = new HashMap<>();

        try {
            while (c.moveToNext()) {
                lstResult.put(c.getString(1),c.getInt(0));
            }
        } finally {
            c.close();
        }

        return lstResult;
    }
}
