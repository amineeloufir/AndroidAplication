package miage.fr.gestionprojet.models.dao;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.models.Ressource;

/**
 * Created by gamouzou on 01/03/2017.
 */

public class DaoRessource {


    public List<Ressource> getAllRessource(){
        return new Select()
                .from(Ressource.class)
                .execute();
    }


    public List<String> getAllRessourceInitials(){
        List<Ressource> listeRessource=new Select().from(Ressource.class).execute();
        List<String> listeInitials=new ArrayList<>();

        for ( int i=0; i < listeRessource.size();i++){
            listeInitials.add(listeRessource.get(i).getInitiales());
        }
        return listeInitials;
    }
}
