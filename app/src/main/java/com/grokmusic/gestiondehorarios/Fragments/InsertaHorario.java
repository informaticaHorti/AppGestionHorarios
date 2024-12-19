package com.grokmusic.gestiondehorarios.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.grokmusic.gestiondehorarios.R;

public class InsertaHorario extends Fragment {

    private  Context context;
    private TextView fecha_insert;
    private  String f_insert;
    public InsertaHorario() {
        // Required empty public constructor
    }
    public InsertaHorario(String fech) {
        // Required empty public constructor
        f_insert=fech;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inserta_horario, container, false);
        context = view.getContext();
        bind(view);
        fecha_insert.setText("Fragment Insercion Horarios\r\nA fecha "+f_insert);
        return view;


    }

    private void bind(View view){
        fecha_insert = view.findViewById(R.id.fec_insert);

    }
    private  void setDates(){

    }
    private  void seDatePicker(){
        DatePickerDialog picker = new DatePickerDialog(context,  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            }
        },2024,11,25);
        picker.show();
    }
}