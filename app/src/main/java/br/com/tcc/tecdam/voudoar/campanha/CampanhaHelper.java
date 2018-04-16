package br.com.tcc.tecdam.voudoar.campanha;

import android.support.design.widget.TextInputEditText;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.Calendar;

import br.com.tcc.tecdam.voudoar.R;
import br.com.tcc.tecdam.voudoar.domain.Campanha;

/**
 * Created by fabio.goncalves on 09/04/2018.
 */

public class CampanhaHelper {

    private final CampanhaActivity campanhaActivity;

    private TextInputEditText campoTitulo;
    private Spinner campoTipo;
    private DatePicker campoDataInicio;
    private Calendar campoDataFinal;
    private TextInputEditText campoObjetivo;
    private TextInputEditText campoAtividades;
    //private TextInputEditText campoSobreProblema;
    //private TextInputEditText campoSobreSolucao;
    //private TextInputEditText campoPublicoAlvo;
    private TextInputEditText campoAreaAtuacao;

    public CampanhaHelper(CampanhaActivity activity) {
        campanhaActivity = activity;
    }

    public Campanha getCampanha() {

        Campanha campanha = new Campanha();

        CarregaCampos();

        //campanha.setId(campoId);

        if (campoTitulo != null) {
            campanha.setTitulo(campoTitulo.getText().toString());
        }

        //campanha.setFrase(campoSlogan);

        if (campoTipo != null) {
            campanha.setTipo(campoTipo.getSelectedItemPosition());
        }

        //campanha.setImagem(campoImagem);
        //campanha.setCorFundo(campoCorFundo);

        if (campoDataInicio != null) {
            Calendar converDatePicker = java.util.Calendar.getInstance();
            converDatePicker.set(
                    campoDataInicio.getYear(),
                    campoDataInicio.getMonth(),
                    campoDataInicio.getDayOfMonth());
            campanha.setDataInicio(converDatePicker.getTime());
        }

        if (campoDataFinal != null) {
            campanha.setDataFinal(campoDataFinal.getTime());
        }

        if (campoObjetivo != null) {
            campanha.setObjetivo(campoObjetivo.getText().toString());
        }

        if (campoAreaAtuacao != null) {
            campanha.setAtividades(campoAtividades.getText().toString());
        }

        //campanha.setSobreProblema(campoSobreProblema.getText().toString());
        //campanha.setSobreSolucao(campoSobreSolucao.getText().toString());
        //campanha.setPublicoAlvo(campoPublicoAlvo.getText().toString());
        //campanha.setUfAtuacao(campoUfAtuacao);

        if (campoAreaAtuacao != null) {
            campanha.setAreaAtuacao(campoAreaAtuacao.getText().toString());
        }

        return campanha;
    }

    private void CarregaCampos() {
        campoTitulo          = campanhaActivity.findViewById(R.id.campanha_nome_edit);
        campoTipo            = campanhaActivity.findViewById(R.id.campanha_tipo_spinner);
        campoDataInicio      = campanhaActivity.findViewById(R.id.campanha_periodo_datepicker);
        //campoDataFinal       = campanhaActivity.getDataFinalCalendar();
        campoObjetivo        = campanhaActivity.findViewById(R.id.campanha_sobre_edit_objetivo);
        campoAtividades      = campanhaActivity.findViewById(R.id.campanha_sobre_edit_atividades);
        //campoSobreProblema = campanhaActivity.findViewById(R.id.campanha_sobre_edit_sobreproblema);
        //campoSobreSolucao  = campanhaActivity.findViewById(R.id.campanha_sobre_edit_sobresolucao);
        //campoPublicoAlvo   = campanhaActivity.findViewById(R.id.campanha_sobre_edit_publicoalvo);
        campoAreaAtuacao     = campanhaActivity.findViewById(R.id.campanha_sobre_edit_areaatuacao);
    }

    public void mostraCampanha(Campanha campanha) {
        CarregaCampos();

        //campanha.setId(campoId);

        if (campoTitulo != null) {
            campoTitulo.setText(campanha.getTitulo());
        }

        //campanha.setFrase(campoSlogan);

        if (campoTipo != null) {
            campoTipo.setSelection(campanha.getTipo());
        }

        //campanha.setImagem(campoImagem);
        //campanha.setCorFundo(campoCorFundo);

        if (campoDataInicio != null) {
            campoDataInicio.updateDate(campanha.getDataFinal().getYear(),
                    campanha.getDataFinal().getMonth(),
                    campanha.getDataFinal().getDay());
        }

        if (campoDataFinal != null) {
            campoDataFinal.set(campanha.getDataFinal().getYear(),
                    campanha.getDataFinal().getMonth(),
                    campanha.getDataFinal().getDay());
        }

        if (campoObjetivo != null) {
            campoObjetivo.setText(campanha.getObjetivo());
        }

        if (campoAreaAtuacao != null) {
            campoAtividades.setText(campanha.getAtividades());
        }

        //campanha.setSobreProblema(campoSobreProblema.getText().toString());
        //campanha.setSobreSolucao(campoSobreSolucao.getText().toString());
        //campanha.setPublicoAlvo(campoPublicoAlvo.getText().toString());
        //campanha.setUfAtuacao(campoUfAtuacao);

        if (campoAreaAtuacao != null) {
            campoAreaAtuacao.setText(campanha.getAreaAtuacao());
        }
    }
}
