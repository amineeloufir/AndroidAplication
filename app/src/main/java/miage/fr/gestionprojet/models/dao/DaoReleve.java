package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;

import java.util.List;

import miage.fr.gestionprojet.models.Releve;
import miage.fr.gestionprojet.models.Travail;

/**
 * Created by Audrey on 23/01/2017.
 */

public class DaoReleve {

    public void insert(Releve releve) {
        releve.save();

    }

    public void delete(Releve releve) {
        releve.delete();
        
    }

    public void update(Releve releve) {
        releve.save();

    }

    public Releve getDernierReleveByTravail (Travail travail){
        List<Releve> lst=  new Select()
                .from(Releve.class)
                .where("travail = ?",travail)
                .orderBy("date_de_releve DESC")
                .execute();

        if(lst.size()>0){
            return lst.get(0);
        }else{
            return new Releve();
        }

    }
}
