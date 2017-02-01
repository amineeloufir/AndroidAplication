package miage.fr.gestionprojet.AvancementRessources;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Projet;
import miage.fr.gestionprojet.models.Travail;

public class ActivityChoixTravail extends AppCompatActivity {

    private Projet proj;
    private ListView liste = null;
    private List<Travail> lstTravaux;
    public static final String TRAVAIL = "travail select";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_travail);
        Intent intent = getIntent();
        long id =  intent.getLongExtra(ActivityDetailsProjet.PROJET,0);
        if (id > 0 ) {
            proj = Model.load(Projet.class, id);
            TextView textProjet = (TextView) findViewById(R.id.textViewNomProjetTrav);
            textProjet.setText(proj.getNom());
            liste = (ListView) findViewById(R.id.listViewTravail);
            lstTravaux = new ArrayList<Travail>();
            List<Domaine> lstDomaines = proj.getLstDomaines();
            for(Domaine d : lstDomaines){
                lstTravaux.addAll(d.getLstTravail());
            }

            final ArrayAdapter<Travail> adapter = new ArrayAdapter<Travail>(this, android.R.layout.simple_list_item_1, lstTravaux);
            liste.setAdapter(adapter);

            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(ActivityChoixTravail.this, ActivityApercuDetails.class);
                    intent.putExtra(TRAVAIL, lstTravaux.get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }
}
