package miage.fr.gestionprojet.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;

/**
 * Created by Audrey on 25/02/2017.
 */
@Table(name="SaisieCharge")
public class SaisieCharge extends Action {

    @Column(name="nb_unites_cibles")
    private int nbUnitesCibles;

    @Column(name="heure_par_unite")
    private float heureParUnite;
    private float chargeTotaleEstimeeEnHeure;
    private int nbSemaines;
    private float chargeEstimeeParSemaine;
    private float chargeRestanteEstimeeEnHeure;
    private int nbSemainePassee;
    private int nbSemainesRestantes;
    private float chargeRestanteParSemaine;
    private float tempsPasseParSemaine;
    private float prctChargeFaiteParSemaineParChargeEstimee;
    private ArrayList<Mesure> lstMesures;

    public SaisieCharge() {
        super();
    }

    public int getNbUnitesCibles() {
        return nbUnitesCibles;
    }

    public void setNbUnitesCibles(int nbUnitesCibles) {
        this.nbUnitesCibles = nbUnitesCibles;
    }

    public float getHeureParUnite() {
        return heureParUnite;
    }

    public void setHeureParUnite(float heureParUnite) {
        this.heureParUnite = heureParUnite;
    }

    public float getChargeTotaleEstimeeEnHeure() {
        return chargeTotaleEstimeeEnHeure;
    }

    public void setChargeTotaleEstimeeEnHeure(float chargeTotaleEstimeeEnHeure) {
        this.chargeTotaleEstimeeEnHeure = chargeTotaleEstimeeEnHeure;
    }

    public int getNbSemaines() {
        return nbSemaines;
    }

    public void setNbSemaines(int nbSemaines) {
        this.nbSemaines = nbSemaines;
    }

    public float getChargeEstimeeParSemaine() {
        return chargeEstimeeParSemaine;
    }

    public void setChargeEstimeeParSemaine(float chargeEstimeeParSemaine) {
        this.chargeEstimeeParSemaine = chargeEstimeeParSemaine;
    }

    public float getChargeRestanteEstimeeEnHeure() {
        return chargeRestanteEstimeeEnHeure;
    }

    public void setChargeRestanteEstimeeEnHeure(float chargeRestanteEstimeeEnHeure) {
        this.chargeRestanteEstimeeEnHeure = chargeRestanteEstimeeEnHeure;
    }

    public int getNbSemainePassee() {
        return nbSemainePassee;
    }

    public void setNbSemainePassee(int nbSemainePassee) {
        this.nbSemainePassee = nbSemainePassee;
    }

    public int getNbSemainesRestantes() {
        return nbSemainesRestantes;
    }

    public void setNbSemainesRestantes(int nbSemainesRestantes) {
        this.nbSemainesRestantes = nbSemainesRestantes;
    }

    public float getChargeRestanteParSemaine() {
        return chargeRestanteParSemaine;
    }

    public void setChargeRestanteParSemaine(float chargeRestanteParSemaine) {
        this.chargeRestanteParSemaine = chargeRestanteParSemaine;
    }

    public float getTempsPasseParSemaine() {
        return tempsPasseParSemaine;
    }

    public void setTempsPasseParSemaine(float tempsPasseParSemaine) {
        this.tempsPasseParSemaine = tempsPasseParSemaine;
    }

    public float getPrctChargeFaiteParSemaineParChargeEstimee() {
        return prctChargeFaiteParSemaineParChargeEstimee;
    }

    public void setPrctChargeFaiteParSemaineParChargeEstimee(float prctChargeFaiteParSemaineParChargeEstimee) {
        this.prctChargeFaiteParSemaineParChargeEstimee = prctChargeFaiteParSemaineParChargeEstimee;
    }

    public ArrayList<Mesure> getLstMesures() {
        this.lstMesures = getMany(Mesure.class, "action");
        return this.lstMesures;
    }
}
