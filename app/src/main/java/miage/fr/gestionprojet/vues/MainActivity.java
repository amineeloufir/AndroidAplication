package miage.fr.gestionprojet.vues;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Projet;
import miage.fr.gestionprojet.models.dao.DaoProjet;

public class MainActivity  extends AppCompatActivity {

    private ListView liste = null;
    private List<Projet> lstProjets = null;
    public final static String EXTRA_PROJET = "projetChoisi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        


        setContentView(R.layout.activity_main);

        //on récupère la liste des projet dont la date de fin n'est passé
        DaoProjet daoProjet = new DaoProjet();
        lstProjets = daoProjet.getProjetEnCours(new Date());
        liste = (ListView) findViewById(R.id.listViewProjet);

        // si le nombre de projet en cours est supérieur à 1 on affiche une liste
        if(lstProjets.size()>1) {
            final ArrayAdapter<Projet> adapter = new ArrayAdapter<Projet>(this, android.R.layout.simple_list_item_1, lstProjets);
            liste.setAdapter(adapter);

            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(MainActivity.this, ActivityDetailsProjet.class);
                    intent.putExtra(EXTRA_PROJET, (lstProjets.get(position).getId()));
                    startActivity(intent);
                }
            });
        }else{

            // sinon, on affiche directement les détails du projet en cours
            if(lstProjets.size()==1) {
                Intent intent = new Intent(MainActivity.this, ActivityDetailsProjet.class);
                intent.putExtra(EXTRA_PROJET, (lstProjets.get(0).getId()));
                startActivity(intent);
            }else{

                // sinon on affiche un message indiquand qu'il n'y a aucun projet en cours
                ArrayList<String> list = new ArrayList<>(1);
                list.add("Aucun projet en cours");
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
                liste.setAdapter(adapter);
            }
        }

    }







}