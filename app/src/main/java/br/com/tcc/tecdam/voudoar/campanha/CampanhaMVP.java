package br.com.tcc.tecdam.voudoar.campanha;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import br.com.tcc.tecdam.voudoar.domain.Campanha;

/**
 * Created by fabio.goncalves on 16/04/2018.
 */

public interface CampanhaMVP {

    String INTENT_KEY_CAMPANHA = "Campanha";
    String INTENT_KEY_ID_CAMPANHA = Campanha.COLUMN_ID;

    interface ViewResource {
        void ApresentaDados();
        void AtualizaTela();
        void Aviso(String mensagem);
        void Fecha();
    }

    interface PresenterResource {
        void InicializaCampanha(Bundle extras);
        int GetTotalPassos();
        int GetPassoAtual();
        int GetIdFragmentPassoAtual();
        Fragment GetFragmentPassoAtual();
        boolean EhPrimeiroPasso();
        boolean EhUltimoPasso();
        void PassoAnterior();
        void ProximoPasso();
        boolean IrParaPasso(int passo);
        void MostraDados();
        void SalvaDados();
        void ConfirmaDados();
    }

    interface ModelResource {

    }
}
