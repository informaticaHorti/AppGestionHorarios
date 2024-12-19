package com.grokmusic.gestiondehorarios.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.grokmusic.gestiondehorarios.Adapters.AdapterRecyclerNominas;
import com.grokmusic.gestiondehorarios.Clases.MesesNomina;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.utils.utDb;
import com.grokmusic.gestiondehorarios.utils.utils;
import java.util.ArrayList;


public class GestionNominas extends Fragment {


    private  Context context;
    private NavigationView navigationView;
    private Activity main;
    private RecyclerView recyclerView;
    private AdapterRecyclerNominas adapter;
    private TextView nombreUsr;
    private SharedPreferences prefs;
    public GestionNominas() {
        // Required empty public constructor
    }
    public GestionNominas(Activity activity, NavigationView navigation) {
        navigationView = navigation;
        main = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gestion_nominas, container, false);
        bind(view);

        context = view.getContext();
        prefs =  getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String nombre = utils.getUsuarioActual(prefs);
        nombreUsr.setText("Usuario Actual: "+nombre);
        ArrayList<MesesNomina> nominas = utDb.getNominas(getContext(),utils.getIdUsuarioActual(prefs));
        if(nominas != null){
            if(nominas.size()>0){
                setRecyclerView(view,nominas);
            }
        }
        return view;
    }

    private void bind(View view){
        nombreUsr = view.findViewById(R.id.usuarioActual);
        recyclerView = view.findViewById(R.id.recyclerNominas);
    }

    private  void setRecyclerView(View view,ArrayList<MesesNomina> arrayList){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new AdapterRecyclerNominas(view.getContext(), arrayList,getActivity(),utils.getIdUsuarioActual(prefs),utils.getUsuarioActual(prefs));
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        adapter.notifyDataSetChanged();
    }

}