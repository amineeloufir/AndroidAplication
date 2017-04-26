package miage.fr.gestionprojet.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.dao.DaoAction;
import miage.fr.gestionprojet.outils.Outils;
import miage.fr.gestionprojet.vues.ActivityBudget;

/**
 * Created by Audrey on 25/04/2017.
 */

public class AdapterBudgetDomaine extends ArrayAdapter<Domaine> {

    private List<Domaine> lstDomaines;
    private ActivityBudget activity;
    private ArrayList<Integer> lstNbActionsRealisees;
    private ArrayList<Integer> lstNbActions;


    public AdapterBudgetDomaine(ActivityBudget context,  int resource,  List<Domaine> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.lstDomaines = objects;
        chargerNbAction();
    }


    @Override
    public int getCount() {
        return lstDomaines.size();
    }

    @Override
    public Domaine getItem(int position) {
        return lstDomaines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterBudgetDomaine.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // on récupère la vue à laquelle doit être ajouter l'image
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lst_view_budget, parent, false);
            holder = new AdapterBudgetDomaine.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AdapterBudgetDomaine.ViewHolder) convertView.getTag();
        }

        // on définit le texte à afficher
        holder.domaine.setText(getItem(position).toString());
        holder.nbActionRealisees.setText(Integer.valueOf(this.lstNbActionsRealisees.get(position))+"/"+Integer.valueOf(this.lstNbActionsRealisees.get(position)));
        holder.avancement.setProgress(Outils.calculerPourcentage(this.lstNbActionsRealisees.get(position),this.lstNbActionsRealisees.get(position)));
        return convertView;
    }

    private void chargerNbAction(){
        this.lstNbActions = new ArrayList<>();
        this.lstNbActionsRealisees = new ArrayList<>();
        int i = 0;
        HashMap<String, Integer> results= DaoAction.getNbActionRealiseeGroupByDomaine();
        if(results.size()>0){
            for(i = 0; i<lstDomaines.size();i++){
                this.lstNbActionsRealisees.add(i,results.get(this.lstDomaines.get(i).getId()));
            }

        }

        results= DaoAction.getNbActionTotalGroupByDomaine();
        if(results.size()>0){
            for(i = 0; i<lstDomaines.size();i++){
                this.lstNbActions.add(i,results.get(this.lstDomaines.get(i).getId()));
            }

        }
    }
    private class ViewHolder {
        private TextView domaine;
        private TextView nbActionRealisees;
        private ProgressBar avancement;

        public ViewHolder(View v) {
            domaine = (TextView) v.findViewById(R.id.typeAffiche);
            nbActionRealisees = (TextView) v.findViewById(R.id.nbActionRealisees);
            avancement = (ProgressBar) v.findViewById(R.id.progress_bar_budget);
        }
    }
}

