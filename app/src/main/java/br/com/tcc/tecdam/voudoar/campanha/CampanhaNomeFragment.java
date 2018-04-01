package br.com.tcc.tecdam.voudoar.campanha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.tcc.tecdam.voudoar.R;

public class CampanhaNomeFragment extends Fragment {

    public CampanhaNomeFragment() {
        // Required empty public constructor
    }

    public static CampanhaNomeFragment newInstance(String param1, String param2) {
        CampanhaNomeFragment fragment = new CampanhaNomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_campanha_nome, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
