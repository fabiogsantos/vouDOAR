package br.com.tcc.tecdam.voudoar.campanha.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.Serializable;

import br.com.tcc.tecdam.voudoar.R;
import br.com.tcc.tecdam.voudoar.campanha.contrato.CampanhaMVP;
import br.com.tcc.tecdam.voudoar.campanha.presenter.CampanhaPresenterImpl;

public class NovaCampanhaActivity extends AppCompatActivity implements CampanhaMVP.ViewResource {

    public static final String KEY_PRESENTER = "KEY_PRESENTER";
    private CampanhaPresenterImpl presenter;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment currentFragment;
    private Button botaoConfirmar;
    private Button botaoVoltar;
    private SeekBar passosTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanha);

        if (presenter == null) {
            presenter = new CampanhaPresenterImpl(this);

            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            if (intent.hasExtra(CampanhaMVP.INTENT_KEY_ID_CAMPANHA)) {
                String idCampanha = intent.getStringExtra(CampanhaMVP.INTENT_KEY_ID_CAMPANHA);
                if (!idCampanha.isEmpty()) {
                    extras.putString(CampanhaMVP.INTENT_KEY_ID_CAMPANHA, idCampanha);
                }
            }
            presenter.InicializaCampanha(extras);
        } else {
            presenter.setView(this);
        }

        InicializaControles();
    }

    private void InicializaControles() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configuração para que o teclado seja apresentado sob o layout se desalinha-lo
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        passosTela      = findViewById(R.id.campanha_seekBar);
        botaoVoltar     = findViewById(R.id.campanha_button_voltar);
        botaoConfirmar  = findViewById(R.id.campanha_button_confirmar);

        passosTela.setMax(presenter.GetTotalPassos());

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.PassoAnterior();
            }
        });

        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter.EhUltimoPasso()) {
                    presenter.ConfirmaDados();
                } else {
                    presenter.ProximoPasso();
                }
            }
        });

        /*
        passosTela.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int index, boolean feito) {
                feito = presenter.IrParaPasso(index);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
              return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                return;
            }
        });
        */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_PRESENTER,presenter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter = (CampanhaPresenterImpl) savedInstanceState.getSerializable(KEY_PRESENTER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AtualizaTela();
    }

    @Override
    public void ApresentaDados() {
        presenter.MostraDados();
    }

    @Override
    public void AtualizaTela() {

        int passoAtual   = presenter.GetPassoAtual();
        int idFragment   = presenter.GetIdFragmentPassoAtual();

        Fragment findFragment  = fragmentManager.findFragmentById(idFragment);

        if ((findFragment == null) || (findFragment != currentFragment)) {
            if (findFragment == null) {
                findFragment = presenter.GetFragmentPassoAtual();
            }

            FragmentTransaction tx = fragmentManager.beginTransaction();
            tx.replace(R.id.campanha_frame, findFragment);
            tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //tx.addToBackStack(null);
            tx.commit();

            currentFragment = findFragment;
        }

        presenter.MostraDados();

        passosTela.setProgress(passoAtual);

        if (presenter.EhPrimeiroPasso()) {
            botaoVoltar.setEnabled(false);
            botaoVoltar.setBackgroundColor(getResources().getColor(R.color.gray));
            botaoVoltar.setTextColor(getResources().getColor(R.color.black));
        } else {
            botaoVoltar.setEnabled(true);
            botaoVoltar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            botaoVoltar.setTextColor(getResources().getColor(R.color.colorTextButton));
        }

        if (presenter.EhUltimoPasso()) {
            botaoConfirmar.setText(getResources().getString(R.string.botao_confirmar));
        }
        else {
            botaoConfirmar.setText(getResources().getString(R.string.botao_avancar));
        }
    }

    @Override
    public void Aviso(String mensagem) {
        Toast.makeText(this,mensagem,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Fecha() {
        this.finish();
    }

//    public static final Creator<NovaCampanhaActivity> CREATOR = new Creator<NovaCampanhaActivity>() {
//        @Override
//        public NovaCampanhaActivity createFromParcel(Parcel in) {
//            return new NovaCampanhaActivity(in);
//        }
//
//        @Override
//        public NovaCampanhaActivity[] newArray(int size) {
//            return new NovaCampanhaActivity[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    protected NovaCampanhaActivity(Parcel in) {
//        presenter = in.readParcelable(CampanhaPresenterImpl.class.getClassLoader());
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeParcelable(presenter, i);
//    }
}
