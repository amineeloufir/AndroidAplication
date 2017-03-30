package miage.fr.gestionprojet.vues;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.ActionsAdapter;
import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.outils.DividerItemDecoration;

public class ActionsActivity extends AppCompatActivity implements View.OnClickListener, ActionsAdapter.ActionClicked{
    private String initial;
    private RecyclerView mRecyclerView;
    private ImageButton yearPlus;
    private ImageButton yearMinus;
    private ImageButton weekPlus;
    private ImageButton weekMinus;
    private EditText yearEditText;
    private EditText weekEditText;
    private TextView mEmptyView;
    private FloatingActionButton sendEmail;
    private int year = 2017;
    private int week = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar!=null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

        try {
            initial = getIntent().getStringExtra(ActivityDetailsProjet.EXTRA_INITIAL);
        } catch(Exception e) {
            e.printStackTrace();
            finish();
        }

        mEmptyView = (TextView) findViewById(R.id.emptyView);
        mRecyclerView = (RecyclerView) findViewById(R.id.actionRecycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        yearPlus = (ImageButton) findViewById(R.id.year_plus);
        yearMinus = (ImageButton) findViewById(R.id.year_minus);
        weekPlus = (ImageButton) findViewById(R.id.week_plus);
        weekMinus = (ImageButton) findViewById(R.id.week_minus);
        sendEmail = (FloatingActionButton) findViewById(R.id.sendEmail);


        yearPlus.setOnClickListener(this);
        yearMinus.setOnClickListener(this);
        weekPlus.setOnClickListener(this);
        weekMinus.setOnClickListener(this);
        sendEmail.setOnClickListener(this);
        yearEditText = (EditText) findViewById(R.id.edit_text_year);
        weekEditText = (EditText) findViewById(R.id.edit_text_week);
        yearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    year = Integer.parseInt(editable.toString());
                    loadActionsByDate(year, week);
                }catch(Exception e){
                    e.printStackTrace();
                    yearEditText.setError("");
                }
            }
        });

        weekEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    week = Integer.parseInt(editable.toString());
                    loadActionsByDate(year, week);
                }catch(Exception e){
                    e.printStackTrace();
                    weekEditText.setError("");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadActionsByDate(2017, 20);
    }

    private void loadActionsByDate(int year, int week) {
        //getAll
        List<Action> actions = new Select().from(Action.class).execute();
        refreshAdapter(actions);
    }

    private void refreshAdapter(List<Action> actions){
        if(actions != null && actions.size() > 0) {
            mEmptyView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            ActionsAdapter adapter = new ActionsAdapter(actions);
            adapter.setmListener(this);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        yearEditText.setError(null);
        weekEditText.setError(null);
        switch (view.getId()){
            case R.id.week_minus:
                week = (Integer.parseInt(weekEditText.getText().toString()) - 1) % 53;
                weekEditText.setText(String.valueOf(week));
                break;
            case R.id.week_plus:
                week = (Integer.parseInt(weekEditText.getText().toString()) + 1) % 53;
                weekEditText.setText(String.valueOf(week));
                break;
            case R.id.year_plus:
                year = (Integer.parseInt(yearEditText.getText().toString()) + 1);
                yearEditText.setText(String.valueOf(year));
                break;
            case R.id.year_minus :
                week = Integer.parseInt(weekEditText.getText().toString()) - 1;
                if(year < 2000){
                    yearEditText.setError("");
                    return;
                }
                yearEditText.setText(String.valueOf(year));
                break;
            case R.id.sendEmail:
                sendEmailAction();
                break;
        }
    }

    private void sendEmailAction() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.planning_detaille_mail_subject));
        email.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.planning_detaille_mail_text));
        email.setType("message/rfc822");



//        File file = createPdf();

//        email.putExtra(Intent.EXTRA_STREAM, file);

//        .putParcelableArrayListExtra(Intent.EXTRA_STREAM, pdfFiles);

        try {
            startActivityForResult(Intent.createChooser(email, getResources().getString(R.string.planning_detaille_select_email_application)), 0);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private File createPdf() {

        return new File("");

/*        PdfDocument document = new PdfDocument();

        // crate a page info with attributes as below
// page number, height and width
// i have used height and width to that of pdf content view
        int pageNumber = 1;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(30,
                30, pageNumber).create();



// create a new page from the PageInfo
        PdfDocument.Page page = document.startPage(pageInfo);

// repaint the user's text into the page
//        content.draw(page.getCanvas());

// do final processing of the page
        document.finishPage(page);

// saving pdf document to sdcard
        String pdfName = "pdfdemo.pdf";

// all created files will be saved at path /sdcard/PDFDemo_AndroidSRC/
//        File outputFile = new File("/sdcard/PDFDemo_AndroidSRC/", pdfName);
        File outputFile = new File(Environment.getDataDirectory(), pdfName);

        try {
            outputFile.createNewFile();
            OutputStream out = new FileOutputStream(outputFile);
            document.writeTo(out);
            document.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile;


//        new File("file:///test.txt");
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
////        if (! file.canRead()) {
////            Toast.makeText(this, "impossible d'ouvrir le pdf généré", Toast.LENGTH_LONG).show();
////            return;
////        }*/
    }

    @Override
    public void SelectedAction(Action action) {
        showDialog(action);
    }

    private void showDialog(final Action action){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_details_action);
        TextView phase = (TextView) dialog.findViewById(R.id.phase);
        TextView name = (TextView) dialog.findViewById(R.id.name);
        TextView dtDebut = (TextView) dialog.findViewById(R.id.dtDebut);
        TextView dtFin = (TextView) dialog.findViewById(R.id.dtFin);
        TextView nbJr = (TextView) dialog.findViewById(R.id.nbJr);
        TextView estimation = (TextView) dialog.findViewById(R.id.estimation);
        phase.setText(action.getPhase());
        name.setText(action.getCode());
        dtDebut.setText("Date Debut : "+ action.getDtDeb());
        dtFin.setText("Date Fin Prevue: "+ action.getDtFinPrevue());
        nbJr.setText("Nombre de jour prevu : " +action.getNbJoursPrevus());
        estimation.setText("Cout par jour : "+action.getCoutParJour());

        Button dialogButton = (Button) dialog.findViewById(R.id.cancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nom:
                return true;
            case R.id.type:
                showPopUp("type");
                return true;
            case R.id.domain:
                return true;
            case R.id.phase:
                showPopUp("phase");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPopUp(String cat){
        ContextThemeWrapper wrapper = new ContextThemeWrapper(this, R.style.MyPopupMenu);
        PopupMenu popupMenu = new PopupMenu(wrapper, mEmptyView);
        Menu menu = popupMenu.getMenu();
        if(cat.equalsIgnoreCase("type")){
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_type, menu);
            popupMenu.setGravity(Gravity.CENTER);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.projet:
                            loadActionsByType("Projet");
                            return true;
                        case R.id.material:
                            loadActionsByType("Materiel");
                            return true;
                        case R.id.analyse:
                            loadActionsByType("Analyse");
                            return true;
                        case R.id.all:
                            loadActionsByDate(year, week);
                            return true;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
        if(cat.equalsIgnoreCase("phase")){
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_phase, menu);
            popupMenu.setGravity(Gravity.CENTER);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.all:
                            loadActionsByDate(year, week);
                            return true;
                        case R.id.phase1:
                            loadActionsByPhase("1");
                            return true;
                        case R.id.phase2:
                            loadActionsByPhase("2");
                            return true;
                        case R.id.phase3:
                            loadActionsByPhase("3");
                            return true;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }

    }

    private void loadActionsByType(String type) {
        List<Action> actions = new Select().from(Action.class)
                .where("typeTravail = ?", type)
                .execute();
        refreshAdapter(actions);
    }

    private void loadActionsByPhase(String phase) {
        List<Action> actions = new Select().from(Action.class)
                .where("phase = ?", phase)
                .execute();
        refreshAdapter(actions);
    }
}
