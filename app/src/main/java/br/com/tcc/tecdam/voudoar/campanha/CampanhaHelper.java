package br.com.tcc.tecdam.voudoar.campanha;

import android.support.design.widget.TextInputEditText;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import br.com.tcc.tecdam.voudoar.R;
import br.com.tcc.tecdam.voudoar.domain.Campanha;
import br.com.tcc.tecdam.voudoar.util.DataUtil;

/**
 * Created by fabio.goncalves on 09/04/2018.
 */

public class CampanhaHelper {

    private final CampanhaActivity campanhaActivity;

    private TextInputEditText campoTitulo;
    private Spinner campoTipo;
    private DatePicker campoDataInicio;
    private TextView campoDataFinal;
    private Switch campoUsaDataFinal;
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
            if (campoUsaDataFinal.isChecked()) {
                campanha.setDataFinal(DataUtil.toDate(campoDataFinal.getText().toString()));
            } else {
                campanha.setDataFinal(null);
            }
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
        campoDataFinal       = campanhaActivity.findViewById(R.id.campanha_periodo_valordatafinal);
        campoUsaDataFinal    = campanhaActivity.findViewById(R.id.campanha_periodo_switch);
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
            campoDataInicio.updateDate(campanha.getDataInicio().getYear(),
                    campanha.getDataInicio().getMonth(),
                    campanha.getDataInicio().getDay());
        }

        if (campoDataFinal != null) {
            if (campanha.getDataFinal() != null) {
                campoUsaDataFinal.setChecked(true);
                campoDataFinal.setText(DataUtil.toString(campanha.getDataFinal()));
            } else {
                campoUsaDataFinal.setChecked(false);
                campoDataFinal.setText("");
            }
        }

        if (campoObjetivo != null) {
            campoObjetivo.setText(campanha.getObjetivo());
        }

        if (campoAtividades != null) {
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

    public boolean ValidacaoCampanha() {

        boolean validado = true;

        CarregaCampos();

        //campanha.setId(campoId);

        if (campoTitulo != null) {
            if (campoTitulo.length() == 0) {
                campoTitulo.setError("Obrigatório!");
                validado = false;
            }
        }

        //campanha.setFrase(campoSlogan);

        if (campoTipo != null) {
            if (campoTipo.getSelectedItemPosition() < 1) {
                Toast.makeText(campanhaActivity,"Escolha o tipo da campanha!",Toast.LENGTH_SHORT).show();
                validado = false;
            }
        }

        if (campoObjetivo != null) {
            if (campoObjetivo.length() == 0) {
                campoObjetivo.setError("Obrigatório!");
                validado = false;
            }
        }

        return validado;
    }
}
