package miage.fr.gestionprojet.vues;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import miage.fr.gestionprojet.R;

public class ActivityBudget extends AppCompatActivity {
    private Spinner spinChoixAffichage;
    private ArrayList<String> lstChoixAffichage;

    private final static String DOMAINE = "Domaine";
    private final static String TYPE = "Type";
    private final static String UTILISATEUR = "Utilisateur";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        lstChoixAffichage = new ArrayList<>();
        lstChoixAffichage.add(DOMAINE);
        lstChoixAffichage.add(TYPE);
        lstChoixAffichage.add(UTILISATEUR);

        spinChoixAffichage = (Spinner) findViewById(R.id.spinnerChoixAffichage);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstChoixAffichage);
        spinChoixAffichage.setAdapter(adapter);

        spinChoixAffichage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String valeurSelectionne = lstChoixAffichage.get(i);
                switch(valeurSelectionne){
                    case DOMAINE:
                        AffichageDomaine();
                        break;
                    case TYPE:
                        break;
                    case UTILISATEUR:
                        break;
                }
            }
        });

    }

    private void AffichageDomaine(){
    }
}
