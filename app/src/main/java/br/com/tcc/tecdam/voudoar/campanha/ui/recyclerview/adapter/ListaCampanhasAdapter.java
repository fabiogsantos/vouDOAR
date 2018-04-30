package br.com.tcc.tecdam.voudoar.campanha.ui.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.tcc.tecdam.voudoar.R;
import br.com.tcc.tecdam.voudoar.domain.Campanha;

/**
 * Created by fabio.goncalves on 28/04/2018.
 */

public class ListaCampanhasAdapter extends RecyclerView.Adapter<ListaCampanhasAdapter.CampanhaViewHolder> {

    private Context context;
    private List<Campanha> campanhas;

    public ListaCampanhasAdapter(Context context, List<Campanha> campanhas) {
        this.context = context;
        this.campanhas = campanhas;
    }

    @Override
    public CampanhaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_lista_campanha, parent, false);
        return new CampanhaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(CampanhaViewHolder holder, int position) {
        holder.bind(campanhas.get(position));
    }

    @Override
    public int getItemCount() {
        return campanhas.size();
    }

    class CampanhaViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private ImageView imagem;

        public CampanhaViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_item_lista_campanha);
            imagem = itemView.findViewById(R.id.image_item_lista_campanha);
        }

        public void bind(Campanha campanha) {
            titulo.setText(campanha.getTitulo());
        }
    }
}
