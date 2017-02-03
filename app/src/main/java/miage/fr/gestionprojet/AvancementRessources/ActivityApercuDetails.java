package miage.fr.gestionprojet.AvancementRessources;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.Model;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.Calcul;
import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Releve;
import miage.fr.gestionprojet.models.Travail;
import miage.fr.gestionprojet.models.dao.DaoReleve;

public class ActivityApercuDetails extends AppCompatActivity {

    private Travail travail = null;
    private TextView txtTravail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apercu_details);

        Intent intent = getIntent();
        long id = intent.getLongExtra(ActivityChoixTravail.TRAVAIL,0);
        if(id > 0){
            travail = Model.load(Travail.class, id);
            DaoReleve dao = new DaoReleve();
            Releve rel = dao.getDernierReleveByTravail(travail);
            txtTravail = (TextView) findViewById(R.id.textViewTravail);
            txtTravail.setText(travail.getDomaine().getNom());

            int progression = Calcul.calculerPourcentage(rel.getNbUnitesProduites(),travail.getNbUnitesCibles());
            CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progressBarAvancement);
            circularProgressBar.setProgress(progression);

            TextView txtPrct = (TextView) findViewById(R.id.textViewPrct);
            txtPrct.setText(progression+"%");

            ListView lstViewIndicateur = (ListView) findViewById(R.id.ListViewDetailsTravail);
            List<String> indicateurs = new ArrayList<>();
            indicateurs.add("Nombre d'unités produites"+rel.getNbUnitesProduites()+"/"+travail.getNbUnitesCibles());
            indicateurs.add("Charge réalisée:"+(travail.getTempsTotalEstime()-rel.getChargeRestante())+"/"+travail.getTempsTotalEstime());
            indicateurs.add("Semaines écoulées"+(travail.getNbSemaine()-rel.getNbSemainesRestantes())+"/"+travail.getNbSemaine());
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, indicateurs);
            lstViewIndicateur.setAdapter(adapter);

        }


    }


}
