package miage.fr.gestionprojet.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;

/**
 * Created by Audrey on 25/02/2017.
 */

@Table(name = "Formation")
public class Formation extends Model {

    private Action action;



    private float avancementTotal;
    private float avancementPreRequis;
    private float avancementObjectif;
    private float avancementPostFormation;

    private ArrayList<EtapeFormation> lstEtapesFormation;

    public Formation() {
        super();
    }

    public ArrayList<EtapeFormation> getLstEtapesFormations() {
        this.lstEtapesFormation = getMany(EtapeFormation.class, "formation");
        return this.lstEtapesFormation;
    }

    public float getAvancementTotal() {
        return avancementTotal;
    }

    public void setAvancementTotal(float avancementTotal) {
        this.avancementTotal = avancementTotal;
    }

    public float getAvancementPreRequis() {
        return avancementPreRequis;
    }

    public void setAvancementPreRequis(float avancementPreRequis) {
        this.avancementPreRequis = avancementPreRequis;
    }

    public float getAvancementObjectif() {
        return avancementObjectif;
    }

    public void setAvancementObjectif(float avancementObjectif) {
        this.avancementObjectif = avancementObjectif;
    }

    public float getAvancementPostFormation() {
        return avancementPostFormation;
    }

    public void setAvancementPostFormation(float avancementPostFormation) {
        this.avancementPostFormation = avancementPostFormation;
    }

    public ArrayList<EtapeFormation> getLstEtapesFormation() {
        return lstEtapesFormation;
    }

    public void setLstEtapesFormation(ArrayList<EtapeFormation> lstEtapesFormation) {
        this.lstEtapesFormation = lstEtapesFormation;
    }
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
