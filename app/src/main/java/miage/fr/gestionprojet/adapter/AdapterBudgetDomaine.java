package miage.fr.gestionprojet.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Domaine;
import miage.fr.gestionprojet.models.Mesure;
import miage.fr.gestionprojet.models.SaisieCharge;
import miage.fr.gestionprojet.models.dao.DaoMesure;
import miage.fr.gestionprojet.outils.Outils;
import miage.fr.gestionprojet.vues.ActivityBudget;

/**
 * Created by Audrey on 25/04/2017.
 */

public class AdapterBudgetDomaine extends ArrayAdapter<Domaine> {

    private List<Domaine> lstDomaines;
    private ActivityBudget activity;


    public AdapterBudgetDomaine(ActivityBudget context,  int resource,  List<Domaine> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.lstDomaines = objects;
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

        // on définit le text à afficher
        holder.domaine.setText(getItem(position).toString());
        return convertView;
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

