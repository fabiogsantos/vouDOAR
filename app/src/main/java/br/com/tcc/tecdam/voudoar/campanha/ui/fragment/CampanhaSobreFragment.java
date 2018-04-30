package br.com.tcc.tecdam.voudoar.campanha.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.tcc.tecdam.voudoar.R;

public class CampanhaSobreFragment extends CampanhaFragment {

    public static CampanhaSobreFragment newInstance() {
        return new CampanhaSobreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campanha_sobre, container, false);
    }
}
