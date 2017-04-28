package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Formation;
import miage.fr.gestionprojet.models.dao.DaoFormation;

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
        ArrayAdapter<Formation> formationsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, formationsData);
        formationsList.setAdapter(formationsAdapter);
    }

    protected void setFormationItemClickListener() {
        formationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FormationsActivity.this, FormationActivity.class);
                intent.putExtra(FORMATION_SELECTED, (formationsData.get(i).getId()));
                startActivity(intent);
            }
        });
    }

}
