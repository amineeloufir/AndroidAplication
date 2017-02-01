package miage.fr.gestionprojet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.Date;
import java.util.List;

import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Projet;
import miage.fr.gestionprojet.models.Releve;
import miage.fr.gestionprojet.models.Travail;
import miage.fr.gestionprojet.models.dao.DaoProjet;

public class MainActivity  extends Activity {

    private ListView liste = null;
    private List<Projet> lstProjets = null;
    public final static String EXTRA_PROJET = "projetChoisi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        try {
            insererDesProjets();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.activity_main);

        DaoProjet daoProjet = new DaoProjet();
        lstProjets = daoProjet.getProjetEnCours(new Date());
        liste = (ListView) findViewById(R.id.listViewProjet);
        if(lstProjets.size()>1) {
            final ArrayAdapter<Projet> adapter = new ArrayAdapter<Projet>(this, android.R.layout.simple_list_item_1, lstProjets);
            liste.setAdapter(adapter);

            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(MainActivity.this, ActivityDetailsProjet.class);
                    intent.putExtra(EXTRA_PROJET, (lstProjets.get(position).getId()));
                    startActivity(intent);
                }
            });
        }else{
            if(lstProjets.size()==1) {
                Intent intent = new Intent(MainActivity.this, ActivityDetailsProjet.class);
                intent.putExtra(EXTRA_PROJET, (lstProjets.get(0).getId()));
                startActivity(intent);
            }else{
                ArrayList<String> list = new ArrayList<>(1);
                list.add("Aucun projet en cours");
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
                liste.setAdapter(adapter);
            }
        }

    }




    private void insererDesProjets() throws ParseException {
        new Delete().from(Projet.class).execute();
        new Delete().from(Domaine.class).execute();
        new Delete().from(Travail.class).execute();
        new Delete().from(Releve.class).execute();
        Projet projet = new Projet();
        projet.setNom("test");
        projet.setDescription("Ceci est un projet de test");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            projet.setDateDebut(df.parse("12/12/2016"));
            projet.setDateFinInitiale(df.parse("05/03/2017"));
            projet.setDateFinReelle(df.parse("05/03/2017"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        projet.save();
        Domaine dom = new Domaine();
        dom.setNom("testDom");
        dom.setDescription("testDescDom");
        dom.setProjet(projet);
        dom.save();

        Travail travail = new Travail();
        travail.setMesure("travailTest1");
        travail.setNbUnitesCibles(92);
        travail.setTempsEstimeParUnite(10);
        travail.setDateDebut(df.parse("12/02/2017"));
        travail.setDateFinInitiale(df.parse("12/02/2017"));
        travail.setDateFinModifiee(df.parse("00/00/0000"));
        travail.setTempsTotalEstime(92*10);
        travail.setNbSemaine(20);
        travail.setChargeParSemaine(10);
        travail.setDomaine(dom);
        travail.save();

        Releve rel = new Releve();
        rel.setDate(new Date());
        rel.setNbUnitesProduites(10);
        rel.setNbSemainesPassees(2);
        rel.setTravail(travail);

        travail = new Travail();
        travail.setMesure("travailTest2");
        travail.setNbUnitesCibles(92);
        travail.setTempsEstimeParUnite(10);
        travail.setDateDebut(df.parse("12/02/2017"));
        travail.setDateFinInitiale(df.parse("12/02/2017"));
        travail.setDateFinModifiee(df.parse("00/00/0000"));
        travail.setTempsTotalEstime(92*10);
        travail.setNbSemaine(20);
        travail.setChargeParSemaine(10);
        travail.setDomaine(dom);
        travail.save();

        rel = new Releve();
        rel.setDate(new Date());
        rel.setNbUnitesProduites(10);
        rel.setNbSemainesPassees(2);
        rel.setTravail(travail);
        rel.save();



        dom = new Domaine();
        dom.setNom("testDom2");
        dom.setDescription("testDescDom");
        dom.setProjet(projet);
        dom.save();

        travail = new Travail();
        travail.setMesure("travailTest3");
        travail.setNbUnitesCibles(92);
        travail.setTempsEstimeParUnite(10);
        travail.setDateDebut(df.parse("12/02/2017"));
        travail.setDateFinInitiale(df.parse("12/02/2017"));
        travail.setDateFinModifiee(df.parse("00/00/0000"));
        travail.setTempsTotalEstime(92*10);
        travail.setNbSemaine(20);
        travail.setChargeParSemaine(10);
        travail.setDomaine(dom);
        travail.save();

        rel = new Releve();
        rel.setDate(new Date());
        rel.setNbUnitesProduites(10);
        rel.setNbSemainesPassees(2);
        rel.setTravail(travail);
        rel.save();

        travail = new Travail();
        travail.setChargeParSemaine(10);
        travail.setDomaine(dom);
        travail.setNbUnitesCibles(25);
        travail.setNbSemaine(2);
        travail.setMesure("testTravail4");
        travail.save();

        rel = new Releve();
        rel.setDate(new Date());
        rel.setNbUnitesProduites(10);
        rel.setNbSemainesPassees(2);
        rel.setTravail(travail);
        rel.save();

        travail = new Travail();
        travail.setMesure("travailTest5");
        travail.setNbUnitesCibles(92);
        travail.setTempsEstimeParUnite(10);
        travail.setDateDebut(df.parse("12/02/2017"));
        travail.setDateFinInitiale(df.parse("12/02/2017"));
        travail.setDateFinModifiee(df.parse("00/00/0000"));
        travail.setTempsTotalEstime(92*10);
        travail.setNbSemaine(20);
        travail.setChargeParSemaine(10);
        travail.setDomaine(dom);
        travail.save();


        projet = new Projet();
        projet.setNom("test2");
        projet.setDescription("test2");
        try {
            projet.setDateDebut(df.parse("12/11/2016"));
            projet.setDateFinInitiale(df.parse("06/03/2017"));
            projet.setDateFinReelle(df.parse("06/03/2017"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        projet.save();

    }
}
