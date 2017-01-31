package miage.fr.gestionprojet.models.dao;

import miage.fr.gestionprojet.models.Travail;

/**
 * Created by Audrey on 23/01/2017.
 */

public class DaoTravail {

    public void insert(Travail travail) {
        travail.save();

    }

    public void delete(Travail travail) {
        travail.delete();

    }

    public void update(Travail travail) {
        travail.save();

    }
}
