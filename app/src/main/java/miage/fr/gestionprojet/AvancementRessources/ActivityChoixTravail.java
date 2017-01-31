package miage.fr.gestionprojet.AvancementRessources;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.Model;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Travail;

public class ActivityChoixTravail extends AppCompatActivity {

    private Domaine dom;
    private ListView liste = null;
    public static final String TRAVAIL = "travail select";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_travail);
        Intent intent = getIntent();
        long id =  intent.getLongExtra(ActivityChoixDomaine.DOMAINE,0);
        if (id > 0 ) {
            dom = Model.load(Domaine.class, id);
            liste = (ListView) findViewById(R.id.listViewTravail);
            final ArrayAdapter<Travail> adapter = new ArrayAdapter<Travail>(this, android.R.layout.simple_list_item_1, dom.getLstTravail());
            liste.setAdapter(adapter);

            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(ActivityChoixTravail.this, ActivityApercuDetails.class);
                    intent.putExtra(TRAVAIL, dom.getLstTravail().get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }
}
