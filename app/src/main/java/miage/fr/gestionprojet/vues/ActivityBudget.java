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
import android.widget.Spinner;

import java.util.ArrayList;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.AdapterBudgetDomaine;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.dao.DaoDomaine;

public class ActivityBudget extends AppCompatActivity {
    private Spinner spinChoixAffichage;
    private ListView liste;
    private ArrayList<String> lstChoixAffichage;
    private String initialUtilisateur;

    private final static String DOMAINE = "Domaine";
    private final static String TYPE = "Type";
    private final static String UTILISATEUR = "Utilisateur";
    public final static String EXTRA_INITIAL = "initial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Intent intentInitial = getIntent();
        initialUtilisateur = intentInitial.getStringExtra(ActivityGestionDesInitials.EXTRA_INITIAL);
        lstChoixAffichage = new ArrayList<>();
        lstChoixAffichage.add(DOMAINE);
        lstChoixAffichage.add(TYPE);
        lstChoixAffichage.add(UTILISATEUR);

        spinChoixAffichage = (Spinner) findViewById(R.id.spinnerChoixAffichage);
        this.liste = (ListView) findViewById(R.id.lstViewBudget);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstChoixAffichage);
        spinChoixAffichage.setAdapter(adapter);

        spinChoixAffichage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String valeurSelectionne = lstChoixAffichage.get(i);
                switch(valeurSelectionne){
                    case DOMAINE:
                        AffichageDomaine();
                        break;
                    case TYPE:
                        AffichageType();
                        break;
                    case UTILISATEUR:
                        AffichageUtilisateur();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void AffichageDomaine(){
        ArrayList<Domaine> lstDomaines = DaoDomaine.loadAll();
        AdapterBudgetDomaine adapter = new AdapterBudgetDomaine(ActivityBudget.this,R.layout.lst_view_budget,lstDomaines);
        this.liste.setAdapter(adapter);
    }

    private void AffichageType(){

    }

    private void AffichageUtilisateur(){

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.initial_utilisateur, menu);
        menu.findItem(R.id.initial_utilisateur).setTitle(initialUtilisateur);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.initial_utilisateur:
                return true;
            case R.id.charger_donnees:
                Intent intent = new Intent(ActivityBudget.this, ChargementDonnees.class);
                intent.putExtra(EXTRA_INITIAL, (initialUtilisateur));
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
