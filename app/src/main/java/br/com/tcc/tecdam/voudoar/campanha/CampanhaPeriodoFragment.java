package br.com.tcc.tecdam.voudoar.campanha;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.com.tcc.tecdam.voudoar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampanhaPeriodoFragment extends Fragment {

    private OnFragmentInteraction fragmentInteraction;

    DatePicker datePickerDataInicial;
    DatePickerDialog.OnDateSetListener listenerDataFinal;

    Calendar dataFinalCalendar = Calendar.getInstance();

    RelativeLayout groupDataFinal;
    Switch switchDataFinal;
    TextView textDataFinal;
    TextView valorDataFinal;

    public static CampanhaPeriodoFragment newInstance() {
        CampanhaPeriodoFragment fragment = new CampanhaPeriodoFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_campanha_periodo, container, false);

        listenerDataFinal = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                dataFinalCalendar.set(Calendar.YEAR, year);
                dataFinalCalendar.set(Calendar.MONTH, monthOfYear);
                dataFinalCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDataFinal();
            }
        };

        datePickerDataInicial = view.findViewById(R.id.campanha_periodo_datepicker);

        datePickerDataInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar converDatePicker = java.util.Calendar.getInstance();
                converDatePicker.set(
                        datePickerDataInicial.getYear(),
                        datePickerDataInicial.getMonth(),
                        datePickerDataInicial.getDayOfMonth());
                fragmentInteraction.setDataInicial(converDatePicker);
            }
        });

        //final Long DEFAULT_MIN_DATE = datePickerDataInicial.getMinDate();
        final Long DEFAULT_MAX_DATE = datePickerDataInicial.getMaxDate();

        groupDataFinal = view.findViewById(R.id.campanha_periodo_groupdatafinal);
        textDataFinal = view.findViewById(R.id.campanha_periodo_textdatafinal);
        valorDataFinal = view.findViewById(R.id.campanha_periodo_valordatafinal);
        switchDataFinal = view.findViewById(R.id.campanha_periodo_switch);

        groupDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePickerDialog();
            }
        });

        switchDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchDataFinal.isChecked()) {
                    textDataFinal.setEnabled(true);
                    valorDataFinal.setEnabled(true);

                    ShowDatePickerDialog();

                } else {
                    textDataFinal.setEnabled(false);
                    valorDataFinal.setEnabled(false);
                    valorDataFinal.setText("");
                    dataFinalCalendar.setTime(Calendar.getInstance().getTime());
                    datePickerDataInicial.setMaxDate(DEFAULT_MAX_DATE);
                    fragmentInteraction.setDataFinal(null);
                }
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        fragmentInteraction.pegaDados();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteraction) {
            fragmentInteraction = (OnFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " necessário implementar CampanhaPeriodoFragment.OnFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteraction = null;
    }

    private void ShowDatePickerDialog() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listenerDataFinal, dataFinalCalendar
                .get(Calendar.YEAR), dataFinalCalendar.get(Calendar.MONTH),
                dataFinalCalendar.get(Calendar.DAY_OF_MONTH));

        //datePickerDataInicial.getDayOfMonth()
        Calendar converDatePicker = java.util.Calendar.getInstance();
        converDatePicker.set(
                datePickerDataInicial.getYear(),
                datePickerDataInicial.getMonth(),
                datePickerDataInicial.getDayOfMonth());
        datePickerDialog.getDatePicker().

                setMinDate(converDatePicker.getTimeInMillis());
        datePickerDialog.show();

    }

    private void updateLabelDataFinal() {
        final Locale localeBrasil = new Locale("pt", "BR");
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, localeBrasil);
        valorDataFinal.setText(sdf.format(dataFinalCalendar.getTime()));
        datePickerDataInicial.setMaxDate(dataFinalCalendar.getTimeInMillis());
        fragmentInteraction.setDataFinal(dataFinalCalendar);
    }

    public interface OnFragmentInteraction {
        void setDataInicial(Calendar data);
        void setDataFinal(Calendar data);
        void pegaDados();
    }
}
