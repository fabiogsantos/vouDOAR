package br.com.tcc.tecdam.voudoar.campanha;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.sql.Date;

import br.com.tcc.tecdam.voudoar.R;
import br.com.tcc.tecdam.voudoar.util.DataUtil;

public class CampanhaPeriodoFragment extends CampanhaFragment {

    private DatePicker datePickerDataInicial;
    private DatePickerDialog.OnDateSetListener listenerDataFinal;

    private RelativeLayout groupDataFinal;
    private Switch switchDataFinal;
    private TextView textDataFinal;
    private TextView valorDataFinal;

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

                Date data = DataUtil.toDate(year,monthOfYear,dayOfMonth);
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
                ShowDatePickerDialog(false);
            }
        });

        switchDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchDataFinal.isChecked()) {
                    textDataFinal.setEnabled(true);
                    valorDataFinal.setEnabled(true);

                    ShowDatePickerDialog(true);

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

    private void ShowDatePickerDialog(Boolean clickCancelDisableSwitch) {

        Date dataFinal = DataUtil.toDate(valorDataFinal.getText().toString());
        if (dataFinal == null) {
            dataFinal = DataUtil.toDate(datePickerDataInicial.getYear(),datePickerDataInicial.getMonth(),datePickerDataInicial.getDayOfMonth());
        }

        Calendar calendar = DataUtil.toCalendar(dataFinal);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listenerDataFinal,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Evento para cancelamento do calendario da data final
        if (clickCancelDisableSwitch) {
            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int button) {
                        if (button == DialogInterface.BUTTON_NEGATIVE) {
                            switchDataFinal.setChecked(false);
                        }
                    }
                }
            );
        }

        Calendar converDatePicker = java.util.Calendar.getInstance();
        converDatePicker.set(
                datePickerDataInicial.getYear(),
                datePickerDataInicial.getMonth(),
                datePickerDataInicial.getDayOfMonth());
        datePickerDialog.getDatePicker().setMinDate(converDatePicker.getTimeInMillis());
        datePickerDialog.show();
    }

    private void updateLabelDataFinal(Date data) {
        valorDataFinal.setText(DataUtil.toString(data));
        datePickerDataInicial.setMaxDate(data.getTime());
    }
}
