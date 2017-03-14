package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.Model;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.AdapterSaisieCharge;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Projet;
import miage.fr.gestionprojet.models.SaisieCharge;

public class ActivityIndicateursSaisieCharge extends AppCompatActivity {

    private Projet proj;
    private List<SaisieCharge> lstSaisieCharge;
    private ListView liste;
    public static final String SAISIECHARGE = "saisie charge";
    public final static String EXTRA_INITIAL = "initial";
    public String initialUtilisateur =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicateurs_saisie_charge);
        Intent intent = getIntent();
        //on récupère le projet sélectionné
        long id =  intent.getLongExtra(ActivityDetailsProjet.PROJET,0);
        liste = (ListView) findViewById(R.id.listViewSaisieCharge);
        initialUtilisateur = intent.getStringExtra(ActivityDetailsProjet.EXTRA_INITIAL);

        if (id > 0 ) {
            // on récupère les données associées à ce projet
            proj = Model.load(Projet.class, id);
            // on récupère la liste des travaux à afficher
            lstSaisieCharge= new ArrayList<SaisieCharge>();
            List<Domaine> lstDomaines = proj.getLstDomaines();
            for(Domaine d : lstDomaines){
                lstSaisieCharge.addAll(d.getLstSaisieCharge());
            }

            //on affiche cette liste
            final ArrayAdapter<SaisieCharge> adapter = new AdapterSaisieCharge(this, R.layout.list_view_layout_saisie_charge, lstSaisieCharge);
            liste.setAdapter(adapter);
            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ActivityIndicateursSaisieCharge.this, ActivityDetailsIndicateursSaisieCharge.class);
                    intent.putExtra(SAISIECHARGE, lstSaisieCharge.get(position).getId());
                    intent.putExtra(EXTRA_INITIAL,initialUtilisateur);
                    startActivity(intent);
                }
            });
        }else{
            // si pas de saisiecharge en cours
            ArrayList<String> list = new ArrayList<>(1);
            list.add("Aucune saisie en cours");
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            liste.setAdapter(adapter);
        }
    }

    //ajout du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.initial_utilisateur, menu);
        menu.findItem(R.id.initial_utilisateur).setTitle(initialUtilisateur);
        getMenuInflater().inflate(R.menu.activity_indicateurs_saisie_charge, menu);
        return true;
    }

    // action à réaliser pour chaque item du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_trie_criticite:
                // Comportement du bouton "Rafraichir"
                return true;
            case R.id.menu_trie_date:
                // Comportement du bouton "Recherche"
                return true;
            case R.id.menu_trie_domaine:
                // Comportement du bouton "Paramètres"
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
