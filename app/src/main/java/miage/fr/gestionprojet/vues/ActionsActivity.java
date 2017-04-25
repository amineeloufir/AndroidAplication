package miage.fr.gestionprojet.vues;

import android.app.Dialog;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.adapter.ActionsAdapter;
import miage.fr.gestionprojet.models.Action;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.dao.DaoAction;
import miage.fr.gestionprojet.outils.DividerItemDecoration;
import miage.fr.gestionprojet.outils.Outils;

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
    private int year;
    private int week;
    private Date dateSaisie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Calendar c = Calendar.getInstance();
        week = c.get(Calendar.WEEK_OF_YEAR);
        year = c.get(Calendar.YEAR);
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
        }catch(Exception e){
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
        yearPlus.setOnClickListener(this);
        yearMinus.setOnClickListener(this);
        weekPlus.setOnClickListener(this);
        weekMinus.setOnClickListener(this);
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
                    dateSaisie = Outils.weekOfYearToDate(year,week);
                    refreshAdapter(DaoAction.loadActionsByDate(dateSaisie));
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
                    dateSaisie = Outils.weekOfYearToDate(year,week);
                    refreshAdapter(DaoAction.loadActionsByDate(dateSaisie));
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
        dateSaisie = Outils.weekOfYearToDate(year,week);
        refreshAdapter(DaoAction.loadActionsByDate(dateSaisie));
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
                dateSaisie = Outils.weekOfYearToDate(year,week);
                yearEditText.setText(String.valueOf(year));
                break;
            case R.id.year_minus :
                year = Integer.parseInt(yearEditText.getText().toString()) - 1;
                dateSaisie = Outils.weekOfYearToDate(year,week);
                if(year < 2000){
                    yearEditText.setError("");
                    return;
                }
                yearEditText.setText(String.valueOf(year));
                break;
        }
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
                refreshAdapter(DaoAction.loadActionsOrderByNomAndDate(dateSaisie));
                return true;
            case R.id.type:
                showPopUp("type");
                return true;
            case R.id.domain:
                showPopUp("domaine");
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
            ArrayList<String> types = getTypeTravailAffiche();
            int i = 0;
            for(i=0; i<types.size();i++){
                menu.add(0, i, 0, types.get(i));
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId()==R.id.all) {
                        refreshAdapter(DaoAction.loadActionsByDate(dateSaisie));
                    }else{
                        refreshAdapter(DaoAction.loadActionsByType(item.getTitle().toString()));
                    }
                    return true;
                }
            });
            popupMenu.show();

        }
        if(cat.equalsIgnoreCase("phase")){
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_phase, menu);
            popupMenu.setGravity(Gravity.CENTER);
            ArrayList<String> phases = getPhasesAffiches();
            int i = 0 ;
            for(i=0; i<phases.size(); i++){
                menu.add(0, i, 0, phases.get(i));
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    if(item.getItemId()==R.id.all) {
                        refreshAdapter(DaoAction.loadActionsByDate(dateSaisie));
                    }else{
                        refreshAdapter(DaoAction.loadActionsByPhaseAndDate(item.getTitle().toString(),dateSaisie));
                    }
                    return true;
                }
            });
            popupMenu.show();
        }

        if(cat.equalsIgnoreCase("domaine")){
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_domaine, menu);
            popupMenu.setGravity(Gravity.CENTER);
            ArrayList<Domaine> doms = getDomainesAffiches();
            int i = 0 ;
            for(Domaine d : doms){
                menu.add(0,(int)(long) d.getId(), 0, d.getNom());
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    if(item.getItemId()==R.id.all) {
                        refreshAdapter(DaoAction.loadActionsByDate(dateSaisie));
                    }else{
                        refreshAdapter(DaoAction.loadActionsByDomaineAndDate(item.getItemId(),dateSaisie));
                    }
                    return true;
                }
            });
            popupMenu.show();
        }

    }

    private ArrayList<String> getTypeTravailAffiche(){
        ArrayList<String> result = new ArrayList<>();
        List<Action> lstActions = DaoAction.loadActionsByDate(dateSaisie);
        for(Action a : lstActions){
            if(result.indexOf(a.getTypeTravail())<0){
                result.add(a.getTypeTravail());
            }
        }
        return result;

    }

    private ArrayList<String> getPhasesAffiches(){
        ArrayList<String> result = new ArrayList<>();
        List<Action> lstActions = DaoAction.loadActionsByDate(dateSaisie);
        for(Action a : lstActions){
            if(result.indexOf(a.getPhase())<0){
                result.add(a.getPhase());
            }
        }
        return result;

    }

    private ArrayList<Domaine> getDomainesAffiches() {
        ArrayList<Domaine> result = new ArrayList<>();
       /* List<Action> lstActions = DaoAction.loadActionsByDate(dateSaisie);
        for(Action a : lstActions){
            if(result.indexOf(a.getDomaine())<0){
                result.add(a.getDomaine());
            }
        }


   */
        return result;
    }


}
