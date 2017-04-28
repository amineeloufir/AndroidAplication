package miage.fr.gestionprojet.vues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Ressource;
import miage.fr.gestionprojet.models.dao.DaoRessource;

/**
 * Created by gamouzou on 01/03/2017.
 */

public class ActivityGestionDesInitials extends AppCompatActivity {

    private ListView liste = null;
    private List<String> lstInitials = null;
    public final static String EXTRA_INITIAL = "Initial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_gestion_des_initials);




        //on récupère la liste des ressources
        DaoRessource daoRessource = new DaoRessource();
        lstInitials = daoRessource.getAllRessourceInitials();
        liste = (ListView) findViewById(R.id.listViewInitials);

        // si le nombre de ressource est supérieur à 1 on affiche une liste
        if(lstInitials.size()>0) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lstInitials);
            liste.setAdapter(adapter);

            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(ActivityGestionDesInitials.this, MainActivity.class);
                    intent.putExtra(EXTRA_INITIAL, (lstInitials.get(position)));
                    startActivity(intent);
                }
            });
        }else{
                // sinon on affiche un message indiquand qu'il n'y a aucun projet en cours
                ArrayList<String> list = new ArrayList<>(1);
                list.add("Aucune Ressource");
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
                liste.setAdapter(adapter);
        }

        }



    }

