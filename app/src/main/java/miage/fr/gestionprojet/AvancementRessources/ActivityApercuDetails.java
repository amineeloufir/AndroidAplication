package miage.fr.gestionprojet.AvancementRessources;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.Model;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Releve;
import miage.fr.gestionprojet.models.Travail;
import miage.fr.gestionprojet.models.dao.DaoReleve;

public class ActivityApercuDetails extends AppCompatActivity {

    private Travail travail = null;
    private TextView txtTravail;
    private ProgressBar pbNbUnite;
    private ProgressBar pbTempsPasse;
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

            pbNbUnite = (ProgressBar) findViewById(R.id.progressBarNbUnite);
            pbNbUnite.setProgress(calculerPourcentage(rel.getNbUnitesProduites(),travail.getNbUnitesCibles()));

            pbTempsPasse = (ProgressBar) findViewById(R.id.progressBarTempsPasse);
            pbTempsPasse.setProgress(calculerPourcentage(rel.getNbSemainesPassees(),travail.getNbSemaine()));

        }


    }

    private int calculerPourcentage(double valeurReleve, double valeurCible){
        int result = (int) (valeurReleve/valeurCible)*100;
        return result;
    }
}
