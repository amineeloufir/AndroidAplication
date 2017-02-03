package miage.fr.gestionprojet.models.dao;

import com.activeandroid.Model;

import miage.fr.gestionprojet.models.Domaine;

/**
 * Created by Audrey on 23/01/2017.
 */

public class DaoDomaine  {

    public void insert(Domaine domaine) {
        domaine.save();

    }
    
    public void delete(Domaine domaine) {
        domaine.delete();

    }
    
    public void update(Domaine domaine) {
        domaine.save();
    }
}
