package miage.fr.gestionprojet.vues;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Mesure;
import miage.fr.gestionprojet.models.Projet;
import miage.fr.gestionprojet.models.Ressource;
import miage.fr.gestionprojet.models.SaisieCharge;
import miage.fr.gestionprojet.models.dao.DaoProjet;

public class MainActivity  extends AppCompatActivity {

    private ListView liste = null;
    private List<Projet> lstProjets = null;
    private String initialUtilisateur = null;
    public final static String EXTRA_PROJET = "projetChoisi";
    public final static String EXTRA_INITIAL = "initial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);

        Intent intentInitial = getIntent();
        initialUtilisateur = intentInitial.getStringExtra(ActivityGestionDesInitials.EXTRA_INITIAL);

        try {
            insererDonneesTests();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.activity_main);

        //on récupère la liste des projet dont la date de fin n'est passé
        DaoProjet daoProjet = new DaoProjet();
        lstProjets = daoProjet.getProjetEnCours(new Date());
        liste = (ListView) findViewById(R.id.listViewProjet);

        // si le nombre de projet en cours est supérieur à 1 on affiche une liste
        if(lstProjets.size()>1) {
            final ArrayAdapter<Projet> adapter = new ArrayAdapter<Projet>(this, android.R.layout.simple_list_item_1, lstProjets);
            liste.setAdapter(adapter);

            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(MainActivity.this, ActivityDetailsProjet.class);
                    intent.putExtra(EXTRA_PROJET, (lstProjets.get(position).getId()));
                    intent.putExtra(EXTRA_INITIAL,initialUtilisateur);

                    startActivity(intent);
                }
            });
        }else{

            // sinon, on affiche directement les détails du projet en cours
            if(lstProjets.size()==1) {
                Intent intent = new Intent(MainActivity.this, ActivityDetailsProjet.class);
                intent.putExtra(EXTRA_PROJET, (lstProjets.get(0).getId()));
                intent.putExtra(EXTRA_INITIAL,initialUtilisateur);
                startActivity(intent);
            }else{

                // sinon on affiche un message indiquand qu'il n'y a aucun projet en cours
                ArrayList<String> list = new ArrayList<>(1);
                list.add("Aucun projet en cours");
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
                liste.setAdapter(adapter);
            }
        }

    }

    public void insererDonneesTests() throws ParseException {

        new Delete().from(Projet.class).execute();
        new Delete().from(Domaine.class).execute();
        new Delete().from(Action.class).execute();
        new Delete().from(SaisieCharge.class).execute();
        new Delete().from(Ressource.class).execute();
        new Delete().from(Mesure.class).execute();

        Projet proj = new Projet();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        proj.setDateDebut(df.parse("12/08/2016"));
        proj.setDateFinInitiale(df.parse("12/08/2017"));
        proj.setDescription("test test test");
        proj.setNom("Test");
        proj.save();

        Domaine dom = new Domaine();
        dom.setNom("PRJ - PROJET");
        dom.setProjet(proj);
        dom.setDescription("Ceci est un test");
        dom.save();

        dom = new Domaine();
        dom.setNom("GC - GESTION COMMERCIALE");
        dom.setProjet(proj);
        dom.setDescription("Ceci est un deuxième test");
        dom.save();

        Ressource res = new Ressource();
        res.setNom("Mercereau");
        res.setPrenom("Nicolas");
        res.setEmail("nm@vif.fr");
        res.setEntreprise("vif");
        res.setInitiales("NM");
        res.setTelephoneFixe("0000000000");
        res.setTelephoneMobile("0000000000");
        res.save();

        SaisieCharge saisie = new SaisieCharge();
        saisie.setDomaine(dom);
        saisie.setCode("SA - Article UL de type PF");
        saisie.setTypeTravail("Saisie");
        saisie.setNbUnitesCibles(92);
        saisie.setHeureParUnite(2);
        saisie.setApparaitrePlanning(true);
        saisie.setOrdre(1);
        saisie.setRespOeu(res);
        saisie.setRespOuv(res);
        saisie.setDtDeb(df.parse("02/02/2017"));
        saisie.setDtFinPrevue(df.parse("15/04/2017"));
        saisie.save();

        Mesure mesure = new Mesure();
        mesure.setAction(saisie);
        mesure.setDtMesure(df.parse("20/02/2017"));
        mesure.setNbUnitesMesures(50);
        mesure.save();

        mesure = new Mesure();
        mesure.setAction(saisie);
        mesure.setDtMesure(df.parse("27/02/2017"));
        mesure.setNbUnitesMesures(65);
        mesure.save();


        saisie = new SaisieCharge();
        saisie.setDomaine(dom);
        saisie.setCode("SA - Clients");
        saisie.setTypeTravail("Saisie");
        saisie.setNbUnitesCibles(50);
        saisie.setHeureParUnite(2);
        saisie.setApparaitrePlanning(true);
        saisie.setOrdre(1);
        saisie.setRespOeu(res);
        saisie.setRespOuv(res);
        saisie.setDtDeb(df.parse("10/02/2017"));
        saisie.setDtFinPrevue(df.parse("24/04/2017"));
        saisie.save();

        Action action = new Action();
        action.setDomaine(dom);
        action.setTypeTravail("Projet");
        action.setCode("Action 1");
        action.setApparaitrePlanning(true);
        action.setCoutParJour(2);
        action.setDtDeb(df.parse("10/05/2017"));
        action.setDtFinPrevue(df.parse("10/07/2017"));
        action.setPhase("2");
        action.setRespOeu(res);
        action.setRespOuv(res);
        ArrayList<Ressource> ls = new ArrayList<>();
        ls.add(res);
        action.setLstUtilisateursOuv(ls);
        action.save();

        Action action2 = new Action();
        action2.setDomaine(dom);
        action2.setTypeTravail("Analyse");
        action2.setCode("Action 2");
        action2.setApparaitrePlanning(true);
        action2.setCoutParJour(2);
        action2.setDtDeb(df.parse("10/05/2017"));
        action2.setDtFinPrevue(df.parse("10/07/2017"));
        action2.setPhase("1");
        action2.setRespOeu(res);
        action2.setRespOuv(res);
        action2.setLstUtilisateursOuv(ls);
        action2.save();
    }


}