package miage.fr.gestionprojet;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

public class ActivityConnexion extends FragmentActivity {

    private Button btnConnect;
    private Button btnDeconnect;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        btnConnect = (Button) findViewById(R.id.buttonConnexion);
        btnDeconnect = (Button) findViewById(R.id.buttonDeconnexion);




    }
}
