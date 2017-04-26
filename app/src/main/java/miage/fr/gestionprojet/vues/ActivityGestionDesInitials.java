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


        try {
            insererDonneesTests();
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
//                    Intent intent = new Intent(ActivityGestionDesInitials.this, ChargementDonnees.class);
//                    intent.putExtra(EXTRA_INITIAL, (lstInitials.get(position)));
//                    startActivity(intent);

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

    public void insererDonneesTests() throws ParseException {

        new Delete().from(Ressource.class).execute();

        Ressource resOne = new Ressource();
        resOne.setNom("Mercereau");
        resOne.setPrenom("Nicolas");
        resOne.setEmail("nm@vif.fr");
        resOne.setEntreprise("vif");
        resOne.setInitiales("NM");
        resOne.setTelephoneFixe("0000000000");
        resOne.setTelephoneMobile("0000000000");
        resOne.save();

        Ressource resTwo = new Ressource();
        resTwo.setNom("Amouzou");
        resTwo.setPrenom("Guillaume");
        resTwo.setEmail("guillaume.amouzou@etu.univ-nantes.fr");
        resTwo.setEntreprise("Capgemini");
        resTwo.setInitiales("AG");
        resTwo.setTelephoneFixe("0000000000");
        resTwo.setTelephoneMobile("0000000000");
        resTwo.save();

        Ressource resThree = new Ressource();
        resThree.setNom("Ballenghien");
        resThree.setPrenom("Audrey");
        resThree.setEmail("audrey.ballenghien@etu.univ-nantes.fr");
        resThree.setEntreprise("Sopra Steria");
        resThree.setInitiales("BA");
        resThree.setTelephoneFixe("0000000000");
        resThree.setTelephoneMobile("0000000000");
        resThree.save();

        Ressource resFour = new Ressource();
        resFour.setNom("EL Alaoui Mostafa");
        resFour.setPrenom("Charifa");
        resFour.setEmail("charifa.el-alaoui-mostafa@etu.univ-nantes.fr");
        resFour.setEntreprise("vif");
        resFour.setInitiales("EC");
        resFour.setTelephoneFixe("0000000000");
        resFour.setTelephoneMobile("0000000000");
        resFour.save();

        Ressource resFive = new Ressource();
        resFive.setNom("Hunault");
        resFive.setPrenom("Romain");
        resFive.setEmail("romain.hunault@etu.univ-nantes.fr");
        resFive.setEntreprise("ASI");
        resFive.setInitiales("HR");
        resFive.setTelephoneFixe("0000000000");
        resFive.setTelephoneMobile("0000000000");
        resFive.save();

        Ressource resSix = new Ressource();
        resSix.setNom("El Oufir");
        resSix.setPrenom("Amine");
        resSix.setEmail("mohammed-amine.el-oufir@etu.univ-nantes.fr");
        resSix.setEntreprise("Capgemini");
        resSix.setInitiales("EA");
        resSix.setTelephoneFixe("0000000000");
        resSix.setTelephoneMobile("0000000000");
        resSix.save();


    }

    }

