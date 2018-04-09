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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.com.tcc.tecdam.voudoar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampanhaPeriodoFragment extends Fragment {

    Calendar myCalendar = Calendar.getInstance();
    DatePicker datePickerDataInicial;
    DatePickerDialog.OnDateSetListener listenerDate;

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

        listenerDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        datePickerDataInicial = (DatePicker) view.findViewById(R.id.campanha_periodo_datepicker);

        //final Long DEFAULT_MIN_DATE = datePickerDataInicial.getMinDate();
        final Long DEFAULT_MAX_DATE = datePickerDataInicial.getMaxDate();

        groupDataFinal = (RelativeLayout) view.findViewById(R.id.campanha_periodo_groupdatafinal);
        textDataFinal   = (TextView) view.findViewById(R.id.campanha_periodo_textdatafinal);
        valorDataFinal  = (TextView) view.findViewById(R.id.campanha_periodo_valordatafinal);
        switchDataFinal     = (Switch) view.findViewById(R.id.campanha_periodo_switch);

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
                    myCalendar.setTime(Calendar.getInstance().getTime());
                    datePickerDataInicial.setMaxDate(DEFAULT_MAX_DATE);
                }
            }
        });

        return view;
    }

    private void ShowDatePickerDialog() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listenerDate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

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

    private void updateLabel() {
        final Locale localeBrasil = new Locale("pt", "BR");
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, localeBrasil);
        valorDataFinal.setText(sdf.format(myCalendar.getTime()));
        datePickerDataInicial.setMaxDate(myCalendar.getTimeInMillis());
    }
}
