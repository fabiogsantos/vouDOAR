package br.com.tcc.tecdam.voudoar.campanha.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Calendar;
import java.util.List;

import br.com.tcc.tecdam.voudoar.R;
import br.com.tcc.tecdam.voudoar.campanha.ui.recyclerview.adapter.ListaCampanhasAdapter;
import br.com.tcc.tecdam.voudoar.dao.VouDoarDAO;
import br.com.tcc.tecdam.voudoar.domain.Campanha;

public class ListaCampanhasActivity extends AppCompatActivity{

    private ListaCampanhasAdapter listaCampanhasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_campanha);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaCampanhasActivity.this, NovaCampanhaActivity.class);
                //intent.putExtra(NovaCampanhaActivity.INTENT_KEY_ID_CAMPANHA,1);
                /*Campanha campanha = new Campanha(0, 8, "RIC 2018", "",
                        new Date(2018, 9, 01), new Date(2018, 12, 25),
                        "Distribuição de cestas básicas natalinas para famílias carentes previamente cadastradas.",
                        "Arrecadação de Alimentos em pontos de coletas e no porta a porta, montagem das cestas e entrega das Cestas as famílias!",
                        "", "PE", "Paudalho, Carpina e Recife", "", "", "", "");
                intent.putExtra(CampanhaMVP.INTENT_KEY_CAMPANHA,campanha);*/
                startActivity(intent);
            }
        });

        List<Campanha> campanhas = carregaTodasCampanhas();
        configuraRecyclerView(campanhas);
    }

    private void configuraRecyclerView(List<Campanha> campanhas) {
        listaCampanhasAdapter = new ListaCampanhasAdapter(this, campanhas);

        RecyclerView campanhaListView = findViewById(R.id.lista_campanha_recyclerview);
        campanhaListView.setAdapter(listaCampanhasAdapter);

/*        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        campanhaListView.setLayoutManager(layoutManager);*/
    }

    private List<Campanha> carregaTodasCampanhas() {
        VouDoarDAO vouDoarDAO = new VouDoarDAO(this);
        List<Campanha> campanhas = vouDoarDAO.buscaCampanhas();
        vouDoarDAO.close();

        for (int cont = 10; cont <= 20; cont++) {
            campanhas.add(new Campanha(1,"Titulo Campanha "+String.valueOf(cont), Calendar.getInstance().getTime()));
        }

        return campanhas;
    }
}
