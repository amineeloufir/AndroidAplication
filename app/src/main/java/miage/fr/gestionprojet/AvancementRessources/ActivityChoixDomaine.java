package miage.fr.gestionprojet.AvancementRessources;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.activeandroid.Model;

import java.util.ArrayList;

import miage.fr.gestionprojet.ActivityDetailsProjet;
import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Projet;

public class ActivityChoixDomaine extends AppCompatActivity {

    private Projet proj;
    public final static String DOMAINE = "domaine select";
    private GridView grid = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avancement_ressources);
        Intent intent = getIntent();
        long id =  intent.getLongExtra(ActivityDetailsProjet.PROJET,0);
        if (id > 0 ) {
            proj = Model.load(Projet.class, id);
            grid = (GridView) findViewById(R.id.gridViewDomaine);
            final ArrayAdapter<Domaine> adapter = new ArrayAdapter<Domaine>(this, android.R.layout.simple_list_item_1, proj.getLstDomaines());
            grid.setAdapter(adapter);

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(ActivityChoixDomaine.this, ActivityChoixTravail.class);
                    intent.putExtra(DOMAINE, proj.getLstDomaines().get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }
}
