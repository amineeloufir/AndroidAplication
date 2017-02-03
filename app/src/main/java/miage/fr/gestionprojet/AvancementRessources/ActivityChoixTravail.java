package miage.fr.gestionprojet.AvancementRessources;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.Model;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.ActivityDetailsProjet;
import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.AdapterTravail;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Projet;
import miage.fr.gestionprojet.models.Travail;

public class ActivityChoixTravail extends AppCompatActivity {

    private Projet proj;
    private List<Travail> lstTravaux;
    private ListView liste;
    public static final String TRAVAIL = "travail select";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_travail);
        Intent intent = getIntent();
        //on récupère le projet sélectionné
        long id =  intent.getLongExtra(ActivityDetailsProjet.PROJET,0);
        liste = (ListView) findViewById(R.id.listViewTravail);
        if (id > 0 ) {
            // on récupère les données associées à ce projet
            proj = Model.load(Projet.class, id);
            // on récupère la liste des travaux à afficher
            lstTravaux = new ArrayList<Travail>();
            List<Domaine> lstDomaines = proj.getLstDomaines();
            for(Domaine d : lstDomaines){
                lstTravaux.addAll(d.getLstTravail());
            }

            //on affiche cette liste
            final ArrayAdapter<Travail> adapter = new AdapterTravail(this, R.layout.list_view_layout_travail, lstTravaux);
            liste.setAdapter(adapter);
            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ActivityChoixTravail.this, ActivityApercuDetails.class);
                    intent.putExtra(TRAVAIL, lstTravaux.get(position).getId());
                    startActivity(intent);
                }
            });
        }else{
            // si pas de travail en cours
            ArrayList<String> list = new ArrayList<>(1);
            list.add("Aucun Travail en cours");
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            liste.setAdapter(adapter);
        }
    }

    //ajout du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_choix_travail, menu);
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
