package miage.fr.gestionprojet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import miage.fr.gestionprojet.AvancementRessources.ActivityChoixDomaine;
import miage.fr.gestionprojet.models.Projet;

public class ActivityDetailsProjet extends AppCompatActivity {

    private final static String RESSOURCES = "Etat d'avancement des ressources";
    public final static String PROJET = "projet visu";
    private ListView liste = null;
    private ArrayList <String> lstAction;
    private Projet proj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_projet);

        Intent intent = getIntent();
        long id = intent.getLongExtra(MainActivity.EXTRA_PROJET,0);
        if (id > 0) {
            proj = Model.load(Projet.class, id);

            TextView txtNomProj = (TextView) findViewById(R.id.textViewNomProjet);
            TextView txtDescription = (TextView) findViewById(R.id.textViewDescription);
            TextView txtDatesProjet = (TextView) findViewById(R.id.textViewDtProjet);

            txtNomProj.setText(proj.getNom());
            txtDescription.setText(proj.getDescription());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            txtDatesProjet.setText(df.format(proj.getDateDebut()) + "-" + df.format(proj.getDateFinInitiale()));

            liste = (ListView) findViewById(R.id.listViewAction);
            lstAction = new ArrayList<String>();
            lstAction.add(RESSOURCES);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstAction);
            liste.setAdapter(adapter);

            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent();
                    switch (position) {
                        case 0:
                            intent = new Intent(ActivityDetailsProjet.this, ActivityChoixDomaine.class);
                            break;

                    }

                    intent.putExtra(PROJET, proj.getId());
                    startActivity(intent);
                }
            });
        }

    }
}
