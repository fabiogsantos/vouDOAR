package br.com.tcc.tecdam.voudoar.campanha;

/**
 * Created by fabio.goncalves on 16/04/2018.
 */

public interface CampanhaMVP {

    public interface ViewResource {
        public void carregaDados();
    }

    public interface PresenterResource {
        public void mostraDados();
    }

    public interface ModelResource {

    }
}
