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
        List<Formation> formationsList = new ArrayList<>();

        for (int i=0; i<10; i++) {
            Action action = new Action();
            action.setCode("Formation "+i);
            action.setPhase(i+"");
            Formation formation = new Formation();
            formation.setAction(action);
            formation.setAvancementTotal(i*10);
            formation.setAvancementObjectif(91);
            formation.setAvancementPostFormation(30);
            formation.setAvancementPreRequis(74);
            formationsList.add(formation);
        }

        return formationsList;

        // TODO replace when data will be loaded
//        return new Select().from(Formation.class).execute();
    }

    public static Formation getFormation(long id) {

        Action action = new Action();
        action.setCode("Formation "+id);
        action.setPhase("1");
        Formation formation = new Formation();
        formation.setAction(action);
        formation.setAvancementObjectif(91);
        formation.setAvancementPostFormation(0);
        formation.setAvancementTotal(55);
        formation.setAvancementPreRequis(0);

        return formation;

        // TODO replace when data will be loaded
//        return new Select().from(Formation.class).where("Id = ?", id).executeSingle();
    }
    public static List<Formation> loadAll() {
        List<Formation> formations= new Select().from(Formation.class).execute();
        return formations;

    }

}
