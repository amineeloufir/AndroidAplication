package miage.fr.gestionprojet.vues;

import com.activeandroid.query.Delete;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;

import com.google.api.services.sheets.v4.SheetsScopes;

import com.google.api.services.sheets.v4.model.*;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import miage.fr.gestionprojet.models.Formation;
import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Projet;
import miage.fr.gestionprojet.models.Ressource;
import miage.fr.gestionprojet.models.dao.DaoAction;

import miage.fr.gestionprojet.models.dao.DaoDomaine;
import miage.fr.gestionprojet.models.dao.DaoProjet;
import miage.fr.gestionprojet.models.dao.DaoRessource;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ChargementDonnees extends Activity implements EasyPermissions.PermissionCallbacks {
    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    private Button mCallApiButton;
    ProgressDialog mProgress;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;


    private static final String BUTTON_TEXT = "Charger la base de données ";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {SheetsScopes.SPREADSHEETS_READONLY};

    /**
     * Create the main activity.
     *
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout activityLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        activityLayout.setLayoutParams(lp);
        activityLayout.setOrientation(LinearLayout.VERTICAL);
        activityLayout.setPadding(16, 16, 16, 16);

        ViewGroup.LayoutParams tlp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        mCallApiButton = new Button(this);
        mCallApiButton.setText(BUTTON_TEXT);
        mCallApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallApiButton.setEnabled(false);
                mOutputText.setText("");
                getResultsFromApi();
                mCallApiButton.setEnabled(true);
            }
        });
        activityLayout.addView(mCallApiButton);

        mOutputText = new TextView(this);
        mOutputText.setLayoutParams(tlp);
        mOutputText.setPadding(16, 16, 16, 16);
        mOutputText.setVerticalScrollBarEnabled(true);
        mOutputText.setMovementMethod(new ScrollingMovementMethod());
        mOutputText.setText(
                "Clicker sur \'" + BUTTON_TEXT + "\' pour charger ou mettre à jour les données .");
        activityLayout.addView(mOutputText);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("préparation de la base de données  ...");

        setContentView(activityLayout);

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
    }


    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            mOutputText.setText("No network connection available.");
        } else {
            new MakeRequestTask(mCredential).execute();
        }
    }

    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    mOutputText.setText(
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     *
     * @param requestCode  The request code passed in
     *                     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     *
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                ChargementDonnees.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /**
     * An asynchronous task that handles the Google Sheets API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    public class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.sheets.v4.Sheets mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Big Follow")
                    .build();
        }

        /**
         * Background task to call Google Sheets API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of names and majors of students in a sample spreadsheet:
         * https://docs.google.com/spreadsheets/d/1yw_8OO4oFYR6Q25KH0KE4LOr86UfwoNl_E6hGgq2UD4/edit
         *
         * @return List of names and majors
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException, ParseException {
            /*tables des feuilles à parcourir
            HashMap<String,String>feuilles= new HashMap<>();
            feuilles.put("rangeActions","Liste des actions projet!A3:Z");
            feuilles.put("rangeRessources","Ressources!A2:Z");
            */
            String spreadsheetId = "1yw_8OO4oFYR6Q25KH0KE4LOr86UfwoNl_E6hGgq2UD4";
            String rangeActions = "Liste des actions projet!A3:Z";
            String rangeDcConso = "DC et détails conso!A5:Z";

            String rangeRessources = "Ressources!A2:Z";
            String rangeFormation = "Indicateurs formation!A3:Z";

            List<String> results = new ArrayList<String>();

            ValueRange responseAction = this.mService.spreadsheets().values()
                    .get(spreadsheetId, rangeActions)
                    .execute();

            ValueRange responseDcConso = this.mService.spreadsheets().values()
                    .get(spreadsheetId, rangeDcConso)
                    .execute();

            ValueRange responseressources = this.mService.spreadsheets().values()
                    .get(spreadsheetId, rangeRessources)
                    .execute();
            ValueRange responseformation = this.mService.spreadsheets().values()
                    .get(spreadsheetId, rangeFormation)
                    .execute();
            List<List<Object>> values = responseAction.getValues();

            List<List<Object>> valuesDcConso = responseDcConso.getValues();

            mProgress.setProgress(1/3);
            if (values != null && valuesDcConso != null) {


               initialiserAction(reglerDonnees(values),reglerDonnees(valuesDcConso));

            }
            mProgress.setProgress(2/3);
            List<List<Object>> valuesressources = responseressources.getValues();
            if (valuesressources != null) {
              initialiserressource(reglerDonnees(valuesressources));


           }
            List<List<Object>> valuesformation = responseformation.getValues();
            mProgress.setProgress(3/3);
            if (valuesformation != null) {
                intialiserFormation(reglerDonnees(valuesformation));


            }

            for (List row : valuesformation) {
                results.add(row.get(6) + ", " + row.get(8));

            }
            return results;
        }

        /*

            homogéner les données
         */
        public List<List<Object>> reglerDonnees(List<List<Object>> values) {
            for (List row : values) {
                int indexe = 26 - row.size();
                for (int i = 0; i < indexe; i++) {
                    row.add("");

                }

            }
            return values;
        }


        public Date chainetoDate(String s) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date resultat;
            if (s.equals("") || s.equals(null) || s.equals("NON PREVU")) {
                resultat = sdf.parse("00/00/0000");

            } else {
                resultat = sdf.parse(s);
            }
            ;
            return resultat;
        }


        public Boolean chainetoBoolean(String s) {
            Boolean resultat;
            if (s.equals("1")) {
                resultat = true;

            } else {
                resultat = false;
            }
            ;
            return resultat;
        }

        public int chainetoint(String s) {
            int resultat;
            if (s.equals("")) {
                resultat = 0;

            } else {
                resultat = new Integer(s);
            }
            ;
            return resultat;
        }

        public Float chainetofloat(String s) {
            Float resultat;
            if (s.equals("") || (s == null) || s.equals("-")) {
                resultat = 0.0f;

            } else {
                resultat = Float.parseFloat(s.replace(',', '.'));
            }
            ;
            return resultat;
        }

        public void initialiserressource(List<List<Object>> values) {
            new Delete().from(Ressource.class).execute();

            Ressource resource = new Ressource();
            for (List row : values) {
                resource.setNom(row.get(2).toString());
                resource.setEmail(row.get(5).toString());
                resource.setEntreprise(row.get(3).toString());
                resource.setFonction(row.get(4).toString());
                resource.setInformationsDiverses(row.get(8).toString());
                resource.setInitiales(row.get(0).toString());
                resource.setPrenom(row.get(1).toString());
                resource.setTelephoneFixe(row.get(6).toString());
                resource.setTelephoneMobile(row.get(7).toString());
            }
        }

        public void initialiserAction(List<List<Object>> values,List<List<Object>> valuesDcConso) throws ParseException {
            new Delete().from(Action.class).execute();
            new Delete().from(Domaine.class).execute();
            new Delete().from(Projet.class).execute();
            /*

             */
            Projet projet = new Projet();

            projet.setNom("Projet processus de dév");
            Date datedeb = chainetoDate("10/01/2017");
            Date datefinproj = chainetoDate("10/06/2017");
            projet.setDateDebut(datedeb);
            projet.setDateFinInitiale(datefinproj);
            projet.setDateFinReelle(datefinproj);
            projet.setDescription("projet M2 MIAGE ");

            projet.save();
            /*

             */
            for (List row : values) {
                Action action = new Action();
                action.setCode(row.get(5).toString());
                action.setOrdre(chainetoint(row.get(1).toString()));
                action.setTarif(row.get(2).toString());

                action.setTypeTravail(row.get(0).toString());
                action.setPhase(row.get(4).toString());
                action.setCode(row.get(5).toString());

                Domaine domaine = DaoDomaine.getByName(row.get(3).toString());
                if(domaine == null) {
                    domaine = new Domaine(row.get(3).toString(), "description demo", projet);
                    domaine.save();
                }

                Ressource respOuv = DaoRessource.getRessourceByInitial(row.get(13).toString());
                if(respOuv==null){
                    respOuv = new Ressource();
                    respOuv.setInitiales(row.get(13).toString());
                }
                action.setRespOuv(respOuv);

                Ressource respOeu = DaoRessource.getRessourceByInitial(row.get(12).toString());
                if(respOeu==null){
                    respOeu = new Ressource();
                    respOeu.setInitiales(row.get(12).toString());
                }
                action.setRespOuv(respOuv);

                action.setDomaine(domaine);

                action.setApparaitrePlanning(chainetoBoolean(row.get(6).toString()));
                action.setTypeFacturation(row.get(7).toString());
                action.setNbJoursPrevus(chainetofloat(row.get(8).toString()));
                action.setCoutParJour(chainetofloat(row.get(11).toString()));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                Date datedebut = chainetoDate(row.get(9).toString());
                Date datefin = chainetoDate(row.get(10).toString());
                action.setDtDeb(datedebut);
                action.setDtFinPrevue(datefin);
                action.setDtFinReelle(datefin);

                for (List row_Dc : valuesDcConso) {

                    if(action.getCode().equals(row_Dc.get(5).toString())){

                        if(row_Dc.get(20).toString() ==null || row_Dc.get(20).toString().length()==0){
                            action.setEcartProjete(0);
                        }else {
                            action.setEcartProjete(chainetofloat(row_Dc.get(20).toString()));
                        }

                        if(row_Dc.get(18).toString() ==null || row_Dc.get(18).toString().length()==0){
                            action.setResteAFaire(0);
                        }else {
                            action.setResteAFaire(chainetofloat(row_Dc.get(18).toString()));
                        }
                    }
                }

               /*  */
                action.save();
            }

        }
        public void intialiserFormation(List<List<Object>> values) {
            new Delete().from(Formation.class).execute();

            Formation formation = new Formation();
            ArrayList<Action> actionList = new ArrayList<>();


            for (List row : values) {


                formation.setAvancementObjectif(chainetofloat(row.get(8).toString().replace('%', ' ')));
                formation.setAvancementTotal(chainetofloat(row.get(6).toString().replace('%', ' ')));
                formation.setAvancementPreRequis(chainetofloat(row.get(7).toString().replace('%', ' ')));

                formation.setAvancementPostFormation(chainetofloat(row.get(9).toString().replace('%', ' ')));
                ArrayList<Action> arrayAction= (ArrayList<Action>) DaoAction.loadAll();
                ArrayList<Projet> projets = (ArrayList<Projet>) DaoProjet.loadAll();

                actionList = DaoAction.getActionbyCode(row.get(5).toString());
                Action action = new Action();
                if (actionList.size() == 0) {
                    action = new Action();
                    action.save();
                } else {
                    action = actionList.get(0);
                }

                formation.setAction(action);


            }


        }

        @Override
        protected void onPreExecute() {
            mOutputText.setText("");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            mProgress.hide();
            if (output == null || output.size() == 0) {
                mOutputText.setText("No results returned.");
            } else {
                output.add(0, "Data retrieved using the Google Sheets API:");
                mOutputText.setText(TextUtils.join("\n", output));

            }
            Intent intentInitial = getIntent();
            String initialUtilisateur = intentInitial.getStringExtra(ActivityGestionDesInitials.EXTRA_INITIAL);
            Intent intent = new Intent(ChargementDonnees.this, MainActivity.class);
            intent.putExtra(ActivityGestionDesInitials.EXTRA_INITIAL, initialUtilisateur);
            startActivity(intent);
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            ChargementDonnees.REQUEST_AUTHORIZATION);
                } else {
                    mOutputText.setText("The following error occurred:\n"
                            + mLastError.getMessage());
                }

            } else {
                mOutputText.setText("Request cancelled.");
            }
        }
    }
}