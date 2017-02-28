package miage.fr.gestionprojet.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Audrey on 25/02/2017.
 */
@Table(name="SaisieCharge")
public class SaisieCharge extends Model {

    @Column(name="typeTravail")
    private String typeTravail;

    @Column(name="ordre")
    private int ordre;

    @Column(name="tarif")
    private String tarif;

    @Column(name="phase")
    private String phase;

    @Column(name="code")
    private String code;

    @Column(name="domaine")
    private Domaine domaine;

    @Column(name="apparaitr_planing")
    private boolean apparaitrePlanning;

    @Column(name="type_facturation")
    private String typeFacturation;

    @Column(name="nb_jours_prevus")
    private int nbJoursPrevus;

    @Column(name="cout_par_pour")
    private float coutParJour;

    @Column(name="resp_oeu")
    private Ressource RespOeu;

    @Column(name="resp_ouv")
    private Ressource RespOuv;

    @Column(name="utilisateurs_ouv")
    private String lstUtilisateursOuv;

    @Column(name="dt_debut")
    private Date dtDeb;

    @Column(name="dt_fin_prevue")
    private Date dtFinPrevue;

    @Column(name="dt_fin_reelle")
    private Date dtFinReelle;

    @Column(name="nb_unites_cibles")
    private int nbUnitesCibles;

    @Column(name="heure_par_unite")
    private float heureParUnite;

    // Les attributs suivants ne sont pas chargé à partir des fichiers, ils sont calculés et doivent être mis à jour à chaque nouvelle mesure enregistrée

    @Column(name="charge_totale_estime_en_heure")
    private float chargeTotaleEstimeeEnHeure;

    @Column(name="nb_semaines")
    private int nbSemaines;

    @Column(name="charge_estime_par_semaine")
    private float chargeEstimeeParSemaine;

    @Column(name="charge_restante_estime_en_heure")
    private float chargeRestanteEstimeeEnHeure;

    @Column(name="nb_semaine_passee")
    private int nbSemainePassee;

    @Column(name="nb_semaines_restantes")
    private int nbSemainesRestantes;

    @Column(name="charge_restante_par_semaine")
    private float chargeRestanteParSemaine;

    @Column(name="temps_passe_par_semaine")
    private float tempsPasseParSemaine;

    @Column(name="prct_charge_faite_par_semaine_par_cahre_estimee")
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

    public String getTypeTravail() {
        return typeTravail;
    }

    public void setTypeTravail(String typeTravail) {
        this.typeTravail = typeTravail;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public boolean isApparaitrePlanning() {
        return apparaitrePlanning;
    }

    public void setApparaitrePlanning(boolean apparaitrePlanning) {
        this.apparaitrePlanning = apparaitrePlanning;
    }

    public String getTypeFacturation() {
        return typeFacturation;
    }

    public void setTypeFacturation(String typeFacturation) {
        this.typeFacturation = typeFacturation;
    }

    public int getNbJoursPrevus() {
        return nbJoursPrevus;
    }

    public void setNbJoursPrevus(int nbJoursPrevus) {
        this.nbJoursPrevus = nbJoursPrevus;
    }

    public float getCoutParJour() {
        return coutParJour;
    }

    public void setCoutParJour(float coutParJour) {
        this.coutParJour = coutParJour;
    }

    public Ressource getRespOeu() {
        return RespOeu;
    }

    public void setRespOeu(Ressource respOeu) {
        RespOeu = respOeu;
    }

    public Ressource getRespOuv() {
        return RespOuv;
    }

    public void setRespOuv(Ressource respOuv) {
        RespOuv = respOuv;
    }

    public String getLstUtilisateursOuv() {
        return lstUtilisateursOuv;
    }

    public void setLstUtilisateursOuv(String lstUtilisateursOuv) {
        this.lstUtilisateursOuv = lstUtilisateursOuv;
    }

    public Date getDtDeb() {
        return dtDeb;
    }

    public void setDtDeb(Date dtDeb) {
        this.dtDeb = dtDeb;
    }

    public Date getDtFinPrevue() {
        return dtFinPrevue;
    }

    public void setDtFinPrevue(Date dtFinPrevue) {
        this.dtFinPrevue = dtFinPrevue;
    }

    public Date getDtFinReelle() {
        return dtFinReelle;
    }

    public void setDtFinReelle(Date dtFinReelle) {
        this.dtFinReelle = dtFinReelle;
    }

    public void setLstMesures(ArrayList<Mesure> lstMesures) {
        this.lstMesures = lstMesures;
    }
}
