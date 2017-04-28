package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.FormationsAdapter;
import miage.fr.gestionprojet.models.Formation;
import miage.fr.gestionprojet.models.dao.DaoFormation;
import miage.fr.gestionprojet.outils.DividerItemDecoration;

public class FormationsActivity extends AppCompatActivity implements View.OnClickListener, FormationsAdapter.FormationClickListener {

    protected List<Formation> formationsData;
    public static final String FORMATION_SELECTED = "formation-selected";
    protected RecyclerView formationsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formations);

        formationsData = DaoFormation.getFormations();
        formationsRecyclerView = (RecyclerView) findViewById(R.id.formationsRecyclerView);
        formationsRecyclerView.setHasFixedSize(true);
        formationsRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        fillFormationsList();
        setFormationItemClickListener();
    }

    protected void fillFormationsList() {
        formationsRecyclerView.setVisibility(View.VISIBLE);
        FormationsAdapter formationsAdapter = new FormationsAdapter(formationsData);
        formationsRecyclerView.setAdapter(formationsAdapter);
        formationsAdapter.notifyDataSetChanged();
    }

    protected void setFormationItemClickListener() {

    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Intent intent = new Intent(FormationsActivity.this, FormationActivity.class);
        intent.putExtra(FORMATION_SELECTED, (formationsData.get(position).getId()));
        startActivity(intent);
    }

    @Override
    public void onFormationClick(Formation formation) {
        Intent intent = new Intent(FormationsActivity.this, FormationActivity.class);
        intent.putExtra(FORMATION_SELECTED, formation.getId());
        startActivity(intent);
    }
}
