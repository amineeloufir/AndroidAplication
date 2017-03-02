package miage.fr.gestionprojet.vues;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.activeandroid.Model;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.models.Mesure;
import miage.fr.gestionprojet.models.SaisieCharge;
import miage.fr.gestionprojet.models.dao.DaoMesure;
import miage.fr.gestionprojet.outils.Calcul;
import miage.fr.gestionprojet.R;

public class ActivityDetailsIndicateursSaisieCharge extends AppCompatActivity {

    private SaisieCharge saisieCharge = null;
    private TextView txtTravail;
    public final static String EXTRA_INITIAL = "initial";
    public String initialUtilisateur =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_indicateurs_saisie_charge);

        Intent intent = getIntent();
        long id = intent.getLongExtra(ActivityIndicateursSaisieCharge.SAISIECHARGE,0);
        initialUtilisateur = intent.getStringExtra(ActivityIndicateursSaisieCharge.EXTRA_INITIAL);


        if(id > 0){
            saisieCharge = Model.load(SaisieCharge.class, id);
            DaoMesure dao = new DaoMesure();
            Mesure mesure = dao.getLastMesureBySaisieCharge(saisieCharge);
            txtTravail = (TextView) findViewById(R.id.textViewSaisieCharge);
            txtTravail.setText(saisieCharge.getDomaine().getNom());

            int progression = Calcul.calculerPourcentage(mesure.getNbUnitesMesures(),saisieCharge.getNbUnitesCibles());
            CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progressBarAvancement);
            circularProgressBar.setProgress(progression);

            TextView txtPrct = (TextView) findViewById(R.id.textViewPrct);
            txtPrct.setText(progression+"%");

            ListView lstViewIndicateur = (ListView) findViewById(R.id.ListViewDetailsSaisieCharge);
            List<String> indicateurs = new ArrayList<>();
            indicateurs.add("Nombre d'unités produites"+mesure.getNbUnitesMesures()+"/"+saisieCharge.getNbUnitesCibles());
            //indicateurs.add("Charge réalisée:"+(saisieCharge.get()-mesure.getChargeRestante())+"/"+saisieCharge.getTempsTotalEstime());
            //indicateurs.add("Semaines écoulées"+(saisieCharge.getNbSemaine()-mesure.getNbSemainesRestantes())+"/"+saisieCharge.getNbSemaine());
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, indicateurs);
            lstViewIndicateur.setAdapter(adapter);

        }


    }

    //ajout du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.initial_utilisateur, menu);
        menu.findItem(R.id.initial_utilisateur).setTitle(initialUtilisateur);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.initial_utilisateur) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
