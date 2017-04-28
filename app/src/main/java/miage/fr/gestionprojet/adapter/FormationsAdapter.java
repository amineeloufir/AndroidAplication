package miage.fr.gestionprojet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Formation;

/**
 * Created by Romain on 28/04/2017.
 */

public class FormationsAdapter extends RecyclerView.Adapter<FormationsAdapter.FormationViewHolder> implements View.OnClickListener {

    private List<Formation> formationsData;
    private FormationClickListener formationClickListener;

    public FormationsAdapter(List<Formation> formations) {
        formationsData = formations;
    }

    @Override
    public FormationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_formation, parent, false);
        FormationViewHolder formationViewHolder = new FormationViewHolder(view);
        formationViewHolder.formationContainer.setOnClickListener(this);
        return null;
    }

    @Override
    public void onBindViewHolder(FormationViewHolder holder, int position) {
        Formation formation = formationsData.get(position);

        holder.formationName.setText(formation.getAction().getCode());
        holder.formationPhase.setText(formation.getAction().getPhase());
        holder.formationPercentage.setText(((int) formation.getAvancementTotal())+"%");
        holder.formationProgressBar.setProgress((int) formation.getAvancementTotal());
    }

    @Override
    public int getItemCount() {
        return formationsData.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Formation formation = formationsData.get(position);
        if (formationClickListener != null) {
            formationClickListener.onFormationClick(formation);
        }
    }

    public static class FormationViewHolder extends RecyclerView.ViewHolder {

        TextView formationPhase;
        TextView formationName;
        TextView formationPercentage;
        ProgressBar formationProgressBar;
        LinearLayout formationContainer;

        public FormationViewHolder(View itemView) {
            super(itemView);

            formationName = (TextView) itemView.findViewById(R.id.formationName);
            formationPhase = (TextView) itemView.findViewById(R.id.formationPhase);
            formationPercentage = (TextView) itemView.findViewById(R.id.formationPercentage);
            formationProgressBar = (ProgressBar) itemView.findViewById(R.id.formationProgressBar);
            formationContainer = (LinearLayout) itemView.findViewById(R.id.formationContainer);
        }
    }

    public interface FormationClickListener {
        void onFormationClick(Formation formation);
    }
}
