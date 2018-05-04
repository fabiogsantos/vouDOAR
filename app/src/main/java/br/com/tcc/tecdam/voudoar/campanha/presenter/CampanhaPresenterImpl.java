package br.com.tcc.tecdam.voudoar.campanha.presenter;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.tcc.tecdam.voudoar.R;
import br.com.tcc.tecdam.voudoar.campanha.contrato.CampanhaMVP;
import br.com.tcc.tecdam.voudoar.campanha.ui.activity.NovaCampanhaActivity;
import br.com.tcc.tecdam.voudoar.campanha.ui.fragment.CampanhaNomeFragment;
import br.com.tcc.tecdam.voudoar.campanha.ui.fragment.CampanhaPeriodoFragment;
import br.com.tcc.tecdam.voudoar.campanha.ui.fragment.CampanhaSobreFragment;
import br.com.tcc.tecdam.voudoar.campanha.ui.fragment.CampanhaTipoFragment;
import br.com.tcc.tecdam.voudoar.dao.VouDoarDAO;
import br.com.tcc.tecdam.voudoar.domain.Campanha;
import br.com.tcc.tecdam.voudoar.util.DataUtil;

/**
 * Created by fabio.goncalves on 09/04/2018.
 */

public class CampanhaPresenterImpl implements CampanhaMVP.PresenterResource, Serializable {

    private final List<Integer> listFragments = Arrays.asList(
            R.layout.fragment_campanha_tipo,
            R.layout.fragment_campanha_nome,
            R.layout.fragment_campanha_sobre,
            R.layout.fragment_campanha_periodo);

    private NovaCampanhaActivity novaCampanhaActivity;
    private CampanhaMVP.ViewResource viewResource;

    private Campanha campanha;

    private int ordPassoAtual = 0;

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

    public CampanhaPresenterImpl(NovaCampanhaActivity activity) {
        novaCampanhaActivity = activity;
        if (activity instanceof CampanhaMVP.ViewResource) {
            viewResource = activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + activity.getString(R.string.aviso_implementar_campanhamvp_viewresource));
        }
    }

    public void SalvaDados() {

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
            campanha.setDataInicio(DataUtil.toDate(
                    campoDataInicio.getYear(),
                    campoDataInicio.getMonth(),
                    campoDataInicio.getDayOfMonth()));
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
    }

    private void CarregaCampos() {
        campoTitulo          = novaCampanhaActivity.findViewById(R.id.campanha_nome_edit);
        campoTipo            = novaCampanhaActivity.findViewById(R.id.campanha_tipo_spinner);
        campoDataInicio      = novaCampanhaActivity.findViewById(R.id.campanha_periodo_datepicker);
        campoDataFinal       = novaCampanhaActivity.findViewById(R.id.campanha_periodo_valordatafinal);
        campoUsaDataFinal    = novaCampanhaActivity.findViewById(R.id.campanha_periodo_switch);
        campoObjetivo        = novaCampanhaActivity.findViewById(R.id.campanha_sobre_edit_objetivo);
        campoAtividades      = novaCampanhaActivity.findViewById(R.id.campanha_sobre_edit_atividades);
        //campoSobreProblema = novaCampanhaActivity.findViewById(R.id.campanha_sobre_edit_sobreproblema);
        //campoSobreSolucao  = novaCampanhaActivity.findViewById(R.id.campanha_sobre_edit_sobresolucao);
        //campoPublicoAlvo   = novaCampanhaActivity.findViewById(R.id.campanha_sobre_edit_publicoalvo);
        campoAreaAtuacao     = novaCampanhaActivity.findViewById(R.id.campanha_sobre_edit_areaatuacao);
    }

    public void MostraDados() {
        if (campanha == null) {
            return;
        }

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
            Date dataInicio = campanha.getDataInicio();
            if (dataInicio == null) {
                dataInicio = new Date(System.currentTimeMillis());
            }

            Calendar calendar = DataUtil.toCalendar(dataInicio);
            campoDataInicio.updateDate(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
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
                campoTitulo.setError(novaCampanhaActivity.getString(R.string.aviso_valor_obrigatorio));
                validado = false;
            }
        }

        //campanha.setFrase(campoSlogan);

        if (campoTipo != null) {
            if (campoTipo.getSelectedItemPosition() < 1) {
                viewResource.Aviso(novaCampanhaActivity.getString(R.string.aviso_escolha_tipo_campanha));
                validado = false;
            }
        }

        if (campoObjetivo != null) {
            if (campoObjetivo.length() == 0) {
                campoObjetivo.setError(novaCampanhaActivity.getString(R.string.aviso_valor_obrigatorio));
                validado = false;
            }
        }

        return validado;
    }

    public void ConfirmaDados() {
        SalvaDados();
        if (campanha != null) {
            if (ValidacaoCampanha()) {
                VouDoarDAO db = new VouDoarDAO(novaCampanhaActivity);
                db.gravaCampanha(campanha);
                db.close();
                viewResource.Aviso(novaCampanhaActivity.getString(R.string.aviso_campanha_confirmada));
                viewResource.Fecha();
            }
        } else {
            viewResource.Aviso(novaCampanhaActivity.getString(R.string.aviso_campanha_incompleta));
        }
    }

    @Override
    public void InicializaCampanha(Bundle extras) {
        if (extras != null) {
            String idCampanha = "";
            campanha = null;
            if (extras.containsKey(CampanhaMVP.INTENT_KEY_CAMPANHA)) {
                campanha = (Campanha) extras.getSerializable(CampanhaMVP.INTENT_KEY_CAMPANHA);
            }
            if (extras.containsKey(CampanhaMVP.INTENT_KEY_ID_CAMPANHA)) {
                idCampanha = extras.getString(CampanhaMVP.INTENT_KEY_ID_CAMPANHA, "");
            }
            if (campanha == null) {
                // Informado um id ja cadastrado
                if (! idCampanha.isEmpty()) {
                    VouDoarDAO db = new VouDoarDAO(novaCampanhaActivity);
                    campanha = db.buscaCampanha(idCampanha);
                    db.close();
                    if (campanha == null) {
                        viewResource.Aviso(novaCampanhaActivity.getString(R.string.campanha_nao_encontrada));
                        viewResource.Fecha();
                    }
                } else {
                    // Cria nova campanha
                    campanha = new Campanha();
                }
            }
        } else {
            campanha = new Campanha();
        }
    }

    @Override
    public int GetTotalPassos() {
        return (listFragments.size()-1);
    }

    @Override
    public int GetPassoAtual() {
        return ordPassoAtual;
    }

    @Override
    public int GetIdFragmentPassoAtual() {
        return listFragments.get(GetPassoAtual());
    }

    @Override
    public Fragment GetFragmentPassoAtual() {
        Fragment fragment = null;
        int idFragment = GetIdFragmentPassoAtual();
        switch (idFragment) {
            case R.layout.fragment_campanha_tipo:
                fragment = CampanhaTipoFragment.newInstance();
                break;
            case R.layout.fragment_campanha_nome:
                fragment = CampanhaNomeFragment.newInstance();
                break;
            case R.layout.fragment_campanha_sobre:
                fragment = CampanhaSobreFragment.newInstance();
                break;
            case R.layout.fragment_campanha_periodo:
                fragment = CampanhaPeriodoFragment.newInstance();
                break;
            default:
                throw new RuntimeException(String.format(novaCampanhaActivity.getString(R.string.layout_fragment_nao_reconhecido), (Object) new String[]{String.valueOf(idFragment)}));
        }
        return fragment;
    }

    @Override
    public boolean EhPrimeiroPasso() {
        return (GetPassoAtual() == 0);
    }

    @Override
    public boolean EhUltimoPasso() {
        return (GetPassoAtual() == GetTotalPassos());
    }

    @Override
    public void PassoAnterior() {
        if (ordPassoAtual > 0) {
            if (ValidacaoCampanha()) {
                SalvaDados();
                ordPassoAtual--;
                viewResource.AtualizaTela();
            }
        }
    }

    @Override
    public void ProximoPasso() {
        if (GetPassoAtual() < GetTotalPassos()) {
            if (ValidacaoCampanha()) {
                SalvaDados();
                ordPassoAtual++;
                viewResource.AtualizaTela();
            }
        }
    }

    @Override
    public boolean IrParaPasso(int passo) {
        boolean operacaoOk = true;

        if (passo != GetPassoAtual()) {
            if (passo >= 0 && passo <= GetTotalPassos()){
                if (ValidacaoCampanha()) {
                    SalvaDados();
                    ordPassoAtual = passo;
                } else {
                    operacaoOk = false;
                }
            } else {
                operacaoOk = false;
            }
        }

        if (operacaoOk) {
            viewResource.AtualizaTela();
        }

        return operacaoOk;
    }

    public void setView(NovaCampanhaActivity view) {
        this.novaCampanhaActivity = view;
    }

//    public static final Creator<CampanhaPresenterImpl> CREATOR = new Creator<CampanhaPresenterImpl>() {
//        @Override
//        public CampanhaPresenterImpl createFromParcel(Parcel in) {
//            return new CampanhaPresenterImpl(in);
//        }
//
//        @Override
//        public CampanhaPresenterImpl[] newArray(int size) {
//            return new CampanhaPresenterImpl[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    protected CampanhaPresenterImpl(Parcel in) {
//        novaCampanhaActivity = in.readParcelable(NovaCampanhaActivity.class.getClassLoader());
//        campanha = in.readParcelable(Campanha.class.getClassLoader());
//        ordPassoAtual = in.readInt();
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeParcelable(novaCampanhaActivity, i);
//        parcel.writeParcelable(campanha, i);
//        parcel.writeInt(ordPassoAtual);
//    }
}
