package br.com.tcc.tecdam.voudoar.campanha.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.tcc.tecdam.voudoar.R;
import br.com.tcc.tecdam.voudoar.dao.VouDoarDAO;
import br.com.tcc.tecdam.voudoar.domain.Campanha;

public class ListaCampanhaActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_campanha);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaCampanhaActivity .this, NovaCampanhaActivity.class);
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

        VouDoarDAO vouDoarDAO = new VouDoarDAO(this);
        List<Campanha> campanhas = vouDoarDAO.buscaCampanhas();
        vouDoarDAO.close();
        ArrayAdapter<Campanha> campanhaArrayAdapter = new ArrayAdapter<Campanha>(this, android.R.layout.simple_list_item_1, campanhas);
        ListView campanhaListView = findViewById(R.id.campanha_listview);
        campanhaListView.setAdapter(campanhaArrayAdapter);
    }
}
