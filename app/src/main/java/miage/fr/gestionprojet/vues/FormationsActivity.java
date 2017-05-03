package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.FormationsAdapter;
import miage.fr.gestionprojet.models.Formation;
import miage.fr.gestionprojet.models.dao.DaoFormation;
import miage.fr.gestionprojet.outils.DividerItemDecoration;

public class FormationsActivity extends AppCompatActivity {

    protected ListView formationsList;
    protected List<Formation> formationsData;
    public static final String FORMATION_SELECTED = "formation-selected";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formations);

        formationsList = (ListView) findViewById(R.id.formationsList);
        formationsData = DaoFormation.getFormations();

        fillFormationsList();
        setFormationItemClickListener();
    }

    protected void fillFormationsList() {
        FormationsAdapter formationsAdapter = new FormationsAdapter(this, R.layout.item_formation, formationsData);
        formationsList.setAdapter(formationsAdapter);
        formationsAdapter.notifyDataSetChanged();
        System.out.println("remplissage de la liste");
    }

    protected void setFormationItemClickListener() {
        formationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), FormationActivity.class);
                intent.putExtra(FORMATION_SELECTED, (formationsData.get(i).getId()));
                startActivity(intent);
            }
        });
    }

}
