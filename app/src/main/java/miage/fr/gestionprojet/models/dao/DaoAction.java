package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;

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

    public static List<Action> loadActionsByPhase(String phase) {
        List<Action> actions = new Select().from(Action.class)
                .where("phase = ?", phase)
                .execute();
        return actions;
    }

    public static List<Action> loadActionsByDate(int year, int week) {
        //getAll
        List<Action> actions = new Select().from(Action.class).execute();
        return actions;
    }
}
