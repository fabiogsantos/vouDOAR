package br.com.tcc.tecdam.voudoar.campanha;

import android.content.res.Resources;
import android.os.Bundle;
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

import java.util.Arrays;
import java.util.List;

import br.com.tcc.tecdam.voudoar.R;

public class CampanhaActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private List<Integer> listFragments = Arrays.asList(R.layout.fragment_campanha_tipo,
                                                        R.layout.fragment_campanha_nome,
                                                        R.layout.fragment_campanha_sobre,
                                                        R.layout.fragment_campanha_periodo);
    private int itemListCurrentFragment = 0;
    private int idCurrentFragment;
    private Fragment currentFlagment;
    private Button botaoConfirmar;
    private Button botaoVoltar;
    private SeekBar passosTela;
    private boolean habilitaBotaoConfirmar;
    private boolean primeiraPagina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanha);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        idCurrentFragment = listFragments.get(itemListCurrentFragment);

        passosTela      = findViewById(R.id.campanha_seekBar);
        botaoVoltar     = findViewById(R.id.campanha_button_voltar);
        botaoConfirmar  = findViewById(R.id.campanha_button_confirmar);

        passosTela.setMax(listFragments.size()-1);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaginaAnterior();
            }
        });

        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (habilitaBotaoConfirmar) {
                    ConfirmaDados();
                } else{
                    ProximaPagina();
                }
            }
        });

        passosTela.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                b = IrParaPagina(i);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        AtualizaTela();
    }

    private void ConfirmaDados() {
        Toast.makeText(this,"Campanha confirmada!",Toast.LENGTH_SHORT).show();
        finish();
    }

    private void AtualizaTela() {
        fragmentManager = getSupportFragmentManager();

        Fragment findFragment  = fragmentManager.findFragmentById(idCurrentFragment);
        if ((findFragment != null) && (findFragment == currentFlagment)) {
            return;
        }

        currentFlagment = findFragment;

        if (currentFlagment == null) {
            switch (idCurrentFragment) {
                case R.layout.fragment_campanha_tipo:
                    currentFlagment = CampanhaTipoFragment.newInstance();
                    break;
                case R.layout.fragment_campanha_nome:
                    currentFlagment = CampanhaNomeFragment.newInstance();
                    break;
                case R.layout.fragment_campanha_sobre:
                    currentFlagment = CampanhaSobreFragment.newInstance();
                    break;
                case R.layout.fragment_campanha_periodo:
                    currentFlagment = CampanhaPeriodoFragment.newInstance();
                    break;
                default:
                    throw new RuntimeException("layout do fragment da campanha com id "+idCurrentFragment+" não reconhecido!");
            }

        }

        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.campanha_frame,currentFlagment);
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tx.addToBackStack(null);
        tx.commit();

        passosTela.setProgress(itemListCurrentFragment);

        habilitaBotaoConfirmar = (itemListCurrentFragment == listFragments.size()-1);
        primeiraPagina         = (itemListCurrentFragment == 0);

        if (primeiraPagina) {
            botaoVoltar.setEnabled(false);
            botaoVoltar.setBackgroundColor(getResources().getColor(R.color.gray));
            botaoVoltar.setTextColor(getResources().getColor(R.color.black));
        } else {
            botaoVoltar.setEnabled(true);
            botaoVoltar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            botaoVoltar.setTextColor(getResources().getColor(R.color.colorTextButton));
        }

        if (habilitaBotaoConfirmar) {
            botaoConfirmar.setText(getResources().getString(R.string.botao_confirmar));
        }
        else {
            botaoConfirmar.setText(getResources().getString(R.string.botao_avancar));
        }

    }

    private boolean IrParaPagina(int index) {
        boolean operacaoOk = true;

        if (index != itemListCurrentFragment) {
            if (index >= 0 && index <= listFragments.size()-1){
                itemListCurrentFragment = index;
            } else {
                operacaoOk = false;
            }
        }

        if (operacaoOk) {
            idCurrentFragment = listFragments.get(itemListCurrentFragment);
            AtualizaTela();
        }

        return operacaoOk;
    }

    private void ProximaPagina() {
        if (itemListCurrentFragment < listFragments.size()-1) {
            itemListCurrentFragment++;
            idCurrentFragment = listFragments.get(itemListCurrentFragment);
            AtualizaTela();
        }
    }

    private void PaginaAnterior() {
        if (itemListCurrentFragment > 0) {
            itemListCurrentFragment--;
            idCurrentFragment = listFragments.get(itemListCurrentFragment);
            AtualizaTela();
        }
    }

}
