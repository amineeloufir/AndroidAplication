package miage.fr.gestionprojet.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by Audrey on 23/01/2017.
 */

@Table(name="Releve")
public class Releve extends Model {

    @Column(name="travail")
    private Travail travail;

    @Column(name="date_de_releve")
    private Date date;

    @Column(name="nb_unites_produites")
    private int nbUnitesProduites;

    @Column(name="charge_restante")
    private double chargeRestante;

    @Column(name="nb_semaines_passees")
    private int nbSemainesPassees;

    @Column(name="nb_semaines_restantes")
    private int nbSemainesRestantes;

    @Column(name="charge_restante_par_semaine")
    private double chargeRestanteParSemaine;

    @Column(name="temps_passe_par_semaine")
    private double tempsPasseParSemaine;

    @Column(name="taux_passe_par_semaine")
    private double tauxPasseParSemaine;

    public Releve(Travail travail, Date date, int nbUnitesProduites, double chargeRestante, int nbSemainesPassees, int nbSemainesRestantes, double chargeRestanteParSemaine, double tempsPasseParSemaine, double tauxPasseParSemaine) {
        super();
        this.travail = travail;
        this.date = date;
        this.nbUnitesProduites = nbUnitesProduites;
        this.chargeRestante = chargeRestante;
        this.nbSemainesPassees = nbSemainesPassees;
        this.nbSemainesRestantes = nbSemainesRestantes;
        this.chargeRestanteParSemaine = chargeRestanteParSemaine;
        this.tempsPasseParSemaine = tempsPasseParSemaine;
        this.tauxPasseParSemaine = tauxPasseParSemaine;
    }

    public Releve() {
        super();
    }

    public Travail getTravail() {
        return travail;
    }

    public void setTravail(Travail travail) {
        this.travail = travail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNbUnitesProduites() {
        return nbUnitesProduites;
    }

    public void setNbUnitesProduites(int nbUnitesProduites) {
        this.nbUnitesProduites = nbUnitesProduites;
    }

    public double getChargeRestante() {
        return chargeRestante;
    }

    public void setChargeRestante(double chargeRestante) {
        this.chargeRestante = chargeRestante;
    }

    public int getNbSemainesPassees() {
        return nbSemainesPassees;
    }

    public void setNbSemainesPassees(int nbSemainesPassees) {
        this.nbSemainesPassees = nbSemainesPassees;
    }

    public int getNbSemainesRestantes() {
        return nbSemainesRestantes;
    }

    public void setNbSemainesRestantes(int nbSemainesRestantes) {
        this.nbSemainesRestantes = nbSemainesRestantes;
    }

    public double getChargeRestanteParSemaine() {
        return chargeRestanteParSemaine;
    }

    public void setChargeRestanteParSemaine(double chargeRestanteParSemaine) {
        this.chargeRestanteParSemaine = chargeRestanteParSemaine;
    }

    public double getTempsPasseParSemaine() {
        return tempsPasseParSemaine;
    }

    public void setTempsPasseParSemaine(double tempsPasseParSemaine) {
        this.tempsPasseParSemaine = tempsPasseParSemaine;
    }

    public double getTauxPasseParSemaine() {
        return tauxPasseParSemaine;
    }

    public void setTauxPasseParSemaine(double tauxPasseParSemaine) {
        this.tauxPasseParSemaine = tauxPasseParSemaine;
    }
}


