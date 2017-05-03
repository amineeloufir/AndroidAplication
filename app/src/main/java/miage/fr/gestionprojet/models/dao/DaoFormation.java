package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.models.Formation;

/**
 * Created by Romain on 27/04/2017.
 */
public class DaoFormation {

    public static List<Formation> getFormations() {
        return new Select().from(Formation.class).execute();
    }

    public static Formation getFormation(long id) {
        return new Select().from(Formation.class).where("Id = ?", id).executeSingle();
    }
}
