package miage.fr.gestionprojet.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * Created by Audrey on 23/01/2017.
 */
@Table(name="Travail")
public class Travail extends Model {

    @Column(name="mesure_travail")
    private String mesure;

    @Column(name="nb_unites_cibles")
    private int nbUnitesCibles;

    @Column(name="temps_estime_par_unite")
    private double tempsEstimeParUnite;

    @Column(name="date_debut")
    private Date dateDebut;

    @Column(name="date_fin_initiale")
    private Date dateFinInitiale;

    @Column(name="date_fin_modifiee")
    private Date dateFinModifiee;

    @Column(name="temps_total_estime")
    private double tempsTotalEstime;

    @Column(name="nb_semaine")
    private int nbSemaine;

    @Column(name="charge_par_semaine")
    private int chargeParSemaine;

    @Column(name="domaine")
    private Domaine domaine;

    public Travail(int nbUnitesCibles, double tempsEstimeParUnite, Date dateDebut, Date dateFinInitiale, Date dateFinModifiee, double tempsTotalEstime, int nbSemaine, int chargeParSemaine, Domaine domaine) {
        super();
        this.nbUnitesCibles = nbUnitesCibles;
        this.tempsEstimeParUnite = tempsEstimeParUnite;
        this.dateDebut = dateDebut;
        this.dateFinInitiale = dateFinInitiale;
        this.dateFinModifiee = dateFinModifiee;
        this.tempsTotalEstime = tempsTotalEstime;
        this.nbSemaine = nbSemaine;
        this.chargeParSemaine = chargeParSemaine;
        this.domaine = domaine;
    }

    public Travail() {
        super();
    }

    public int getNbUnitesCibles() {
        return nbUnitesCibles;
    }

    public void setNbUnitesCibles(int nbUnitesCibles) {
        this.nbUnitesCibles = nbUnitesCibles;
    }

    public double getTempsEstimeParUnite() {
        return tempsEstimeParUnite;
    }

    public void setTempsEstimeParUnite(double tempsEstimeParUnite) {
        this.tempsEstimeParUnite = tempsEstimeParUnite;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFinInitiale() {
        return dateFinInitiale;
    }

    public void setDateFinInitiale(Date dateFinInitiale) {
        this.dateFinInitiale = dateFinInitiale;
    }

    public Date getDateFinModifiee() {
        return dateFinModifiee;
    }

    public void setDateFinModifiee(Date dateFinModifiee) {
        this.dateFinModifiee = dateFinModifiee;
    }

    public double getTempsTotalEstime() {
        return tempsTotalEstime;
    }

    public void setTempsTotalEstime(double tempsTotalEstime) {
        this.tempsTotalEstime = tempsTotalEstime;
    }

    public int getNbSemaine() {
        return nbSemaine;
    }

    public void setNbSemaine(int nbSemaine) {
        this.nbSemaine = nbSemaine;
    }

    public int getChargeParSemaine() {
        return chargeParSemaine;
    }

    public void setChargeParSemaine(int chargeParSemaine) {
        this.chargeParSemaine = chargeParSemaine;
    }

    public String getMesure() {
        return mesure;
    }

    public void setMesure(String mesure) {
        this.mesure = mesure;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public List<Releve> getLstReleve(){
        return getMany(Releve.class,"travail");
    }

    @Override
    public String toString() {
        return this.mesure;
    }
}
