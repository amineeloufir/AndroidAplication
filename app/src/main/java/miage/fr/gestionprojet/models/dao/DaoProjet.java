package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

import miage.fr.gestionprojet.models.Projet;

/**
 * Created by Audrey on 23/01/2017.
 */

public class DaoProjet {

    public List<Projet> getProjetEnCours(Date dateDuJour){
        return new Select()
                .from(Projet.class)
                .where("date_fin_initiale>? or date_fin_reelle>?", dateDuJour.getTime(),dateDuJour.getTime())
                .execute();
    }
}