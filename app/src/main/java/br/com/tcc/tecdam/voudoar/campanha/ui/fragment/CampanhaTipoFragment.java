package br.com.tcc.tecdam.voudoar.campanha.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.com.tcc.tecdam.voudoar.R;

public class CampanhaTipoFragment extends CampanhaFragment {

    Spinner campanha_tipo_spinner;
    ArrayAdapter<String> adapter;

    String[] ITEMS = {"Escolha o Tipo",
                      "Capacitação Profissional",
                      "Cultura e Arte",
                      "Defesa dos Direitos Humanos",
                      "Educação",
                      "Esportes",
                      "Meio Ambiente e Proteção dos Animais",
                      "Saúde",
                      "Serviços Sociais",
                      "Outros" };

    public static CampanhaTipoFragment newInstance() {
        CampanhaTipoFragment fragment = new CampanhaTipoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_campanha_tipo, container, false);

        campanha_tipo_spinner = (Spinner) view.findViewById(R.id.campanha_tipo_spinner);
        campanha_tipo_spinner.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
}
