package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Projet;

public class ActivityDetailsProjet extends AppCompatActivity {

    private final static String RESSOURCES = "Avancement des saisies";
    private final static String FORMATIONS = "Avancement des formations";
    private final static String PLANNING = "Planning détaillé";
    private final static String BUDGET = "Suivi du budget";
    public final static String PROJET = "projet visu";
    private ListView liste = null;
    public final static String EXTRA_INITIAL = "initial";
    private ArrayList <String> lstActions;
    private Projet proj;
    public String initialUtilisateur =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_projet);

        Intent intent = getIntent();
        long id = intent.getLongExtra(MainActivity.EXTRA_PROJET,0);
        initialUtilisateur = intent.getStringExtra(MainActivity.EXTRA_INITIAL);

        // s'il n'y pas d'erreur, un projet est sélectionné
        if (id > 0) {
            // on récupère toutes les données de ce projet
            proj = Model.load(Projet.class, id);

            // on récupère les différents élements de la vue
            TextView txtNomProj = (TextView) findViewById(R.id.textViewNomProjet);
            TextView txtDatesProjet = (TextView) findViewById(R.id.textViewDtProjet);

            // on alimente ces différents éléments
            txtNomProj.setText(proj.getNom());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            txtDatesProjet.setText(df.format(proj.getDateDebut()) + "-" + df.format(proj.getDateFinInitiale()));

            // on constitue une liste d'action
            liste = (ListView) findViewById(R.id.listViewAction);
            lstActions = new ArrayList<String>();
            lstActions.add(RESSOURCES);
            lstActions.add(FORMATIONS);
            lstActions.add(PLANNING);
            lstActions.add(BUDGET);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstActions);
            liste.setAdapter(adapter);


            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent;
                    switch (position) {
                        case 0:
                            intent = new Intent(ActivityDetailsProjet.this, ActivityIndicateursSaisieCharge.class);
                            intent.putExtra(EXTRA_INITIAL,initialUtilisateur);
                            intent.putExtra(PROJET, proj.getId());
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(ActivityDetailsProjet.this, FormationsActivity.class);
                            intent.putExtra(EXTRA_INITIAL,initialUtilisateur);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(ActivityDetailsProjet.this, ActionsActivity.class);
                            intent.putExtra(EXTRA_INITIAL, initialUtilisateur);
                            startActivity(intent);
                            break;
                        default:
                            System.out.println("Non instancié pour le moment");
                            break;

                    }

                    //intent.putExtra(PROJET, proj.getId());
                    //startActivity(intent);
                }
            });

            final Button button = (Button) findViewById(R.id.btnActions);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityDetailsProjet.this, ActivityIndicateursSaisieCharge.class);
                    intent.putExtra(EXTRA_INITIAL,initialUtilisateur);
                    intent.putExtra(PROJET, proj.getId());
                    startActivity(intent);
                }
            });

        }

    }

    //ajout du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.initial_utilisateur, menu);
        menu.findItem(R.id.initial_utilisateur).setTitle(initialUtilisateur);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.initial_utilisateur) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void definirCouleurBouton(){

    }
}
