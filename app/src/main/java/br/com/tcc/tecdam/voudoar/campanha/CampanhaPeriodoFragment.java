package br.com.tcc.tecdam.voudoar.campanha;


import android.app.DatePickerDialog;
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

import java.util.Calendar;
import java.util.Date;

import br.com.tcc.tecdam.voudoar.R;
import br.com.tcc.tecdam.voudoar.util.DataUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampanhaPeriodoFragment extends CampanhaFragment {

    DatePicker datePickerDataInicial;
    DatePickerDialog.OnDateSetListener listenerDataFinal;

    RelativeLayout groupDataFinal;
    Switch switchDataFinal;
    TextView textDataFinal;
    TextView valorDataFinal;

    public static CampanhaPeriodoFragment newInstance() {
        return new CampanhaPeriodoFragment();
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
                Date data = new Date(year,monthOfYear,dayOfMonth);
                updateLabelDataFinal(data);
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
                    datePickerDataInicial.setMaxDate(DEFAULT_MAX_DATE);
                }
            }
        });

        return view;
    }

    private void ShowDatePickerDialog() {

        Date dataFinal = DataUtil.toDate(valorDataFinal.getText().toString());
        if (dataFinal == null) {
          dataFinal = new Date(datePickerDataInicial.getMaxDate());
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listenerDataFinal,
                dataFinal.getYear(), dataFinal.getMonth(), dataFinal.getDay());

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

    private void updateLabelDataFinal(Date data) {
        valorDataFinal.setText(DataUtil.toString(data));
        datePickerDataInicial.setMaxDate(data.getTime());
    }
}
