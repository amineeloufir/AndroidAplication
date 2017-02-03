package miage.fr.gestionprojet.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import miage.fr.gestionprojet.AvancementRessources.ActivityChoixTravail;
import miage.fr.gestionprojet.Calcul;
import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Releve;
import miage.fr.gestionprojet.models.Travail;
import miage.fr.gestionprojet.models.dao.DaoReleve;

/**
 * Created by Audrey on 01/02/2017.
 */

public class AdapterTravail extends ArrayAdapter<Travail>{

    private List<Travail> lstTravaux;
    private ActivityChoixTravail activity;

    public AdapterTravail(ActivityChoixTravail context, int resource, List<Travail> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.lstTravaux = objects;

    }

    @Override
    public int getCount() {
        return lstTravaux.size();
    }

    @Override
    public Travail getItem(int position) {
        return lstTravaux.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // on récupère la vue à laquelle doit être ajouter l'image
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_view_layout_travail, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // on définit le text à afficher
        holder.travail.setText(getItem(position).toString());

        //on récupère la première lettre du domaine associé au travail
        String firstLetter = String.valueOf(getItem(position).getDomaine().getNom().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(getItem(position));
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        // on ajoute l'image de l'initial du domaine
        holder.imageView.setImageDrawable(drawable);

        // on affiche l'état d'avancment du travail
        DaoReleve dao = new DaoReleve();
        Releve rel = dao.getDernierReleveByTravail(getItem(position));
        holder.avancement.setProgress(Calcul.calculerPourcentage(rel.getNbUnitesProduites(),getItem(position).getNbUnitesCibles()));

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView travail;
        private ProgressBar avancement;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.icon_travail);
            travail = (TextView) v.findViewById(R.id.label);
            avancement = (ProgressBar) v.findViewById(R.id.progress_bar_travail_crit);
        }
    }

}
