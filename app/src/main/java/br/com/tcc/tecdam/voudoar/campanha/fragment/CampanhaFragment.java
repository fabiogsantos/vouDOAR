package br.com.tcc.tecdam.voudoar.campanha.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import br.com.tcc.tecdam.voudoar.R;
import br.com.tcc.tecdam.voudoar.campanha.contrato.CampanhaMVP;

/**
 * Created by fabio.goncalves on 16/04/2018.
 */

public abstract class CampanhaFragment extends Fragment {

    CampanhaMVP.ViewResource activity;

    @Override
    public void onResume() {
        super.onResume();
        activity.ApresentaDados();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CampanhaMVP.ViewResource) {
            activity = (CampanhaMVP.ViewResource) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.aviso_implementar_campanhamvp_viewresource));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

}
