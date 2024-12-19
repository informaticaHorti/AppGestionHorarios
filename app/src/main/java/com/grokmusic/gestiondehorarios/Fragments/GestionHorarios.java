package com.grokmusic.gestiondehorarios.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class GestionHorarios extends Fragment {

    private CalendarView picker;
    private TextView actualMonth;
    private Button prev, next;
    private  Context context;
    private NavigationView navigationView;
    private Activity main;
    private TextView nombreUsr;
    private SharedPreferences prefs;
    public GestionHorarios() {
        // Required empty public constructor
    }
    public GestionHorarios(Activity activity, NavigationView navigation) {
        navigationView = navigation;
        main = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gestion_horarios, container, false);
        bind(view);

        context = view.getContext();
        prefs =  getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String nombre = utils.getUsuarioActual(prefs);
        nombreUsr.setText("Usuario Actual: "+nombre);
        picker.setFirstDayOfWeek(Calendar.MONDAY);
        picker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String f = i+"-"+(i1+1)+"-"+i2;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(f);
                    String fecha = sdf.format(date);
                    utils.changeFragment(getActivity(),new fragmentCreaHorarioUsuario(fecha),"Gestion de Horarios");
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        return view;
    }

    private void bind(View view){
        picker = view.findViewById(R.id.calendar);
        nombreUsr = view.findViewById(R.id.usuarioActual);
    }

    private void changeFragment(Fragment fragment) {
        ((AppCompatActivity)getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame,fragment)
                .addToBackStack("GestionHorarios")
                .commit();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Insercion de Horarios");
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
    private  void setFechas(SharedPreferences prefs){
        ArrayList<String> horarios = utils.getHorarios(getContext(),Integer.parseInt(utils.getIdUsuarioActual(prefs)),12,2024);
    }

}