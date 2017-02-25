package miage.fr.gestionprojet.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Audrey on 25/02/2017.
 */

@Table(name="Ressource")
public class Ressource extends Model{

    @Column(name="initiales")
    private String initiales;

    @Column(name="prenom")
    private String prenom;

    @Column(name="nom")
    private String nom;

    @Column(name="entreprise")
    private String entreprise;

    @Column(name="fonction")
    private String fonction;

    @Column(name="email")
    private String email;

    @Column(name="telephone_fixe")
    private String telephoneFixe;

    @Column(name="telephone_mobile")
    private String telephoneMobile;

    @Column(name="informations_diverses")
    private String InformationsDiverses;
}
