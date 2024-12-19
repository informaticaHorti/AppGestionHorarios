package com.grokmusic.gestiondehorarios.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grokmusic.gestiondehorarios.Adapters.AdapterRecyclerGestTarifas;
import com.grokmusic.gestiondehorarios.Clases.Tarifas;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.util.ArrayList;

public class GestionTarifas extends Fragment {

    ArrayList<Tarifas> list = new ArrayList<Tarifas>();
    private RecyclerView recyclerView ;
    private AdapterRecyclerGestTarifas adapter;
    private Button btnAdd;
    public GestionTarifas() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gestion_tarifas, container, false);
        bind(view);
        showList(view);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.changeFragment(getActivity(),new fragmentCreaTarifa(),"Crear Tarifas");
            }
        });
        return view;
    }
    private  void bind(View view){
        btnAdd = view.findViewById(R.id.btnAddTarifa);
        recyclerView = view.findViewById(R.id.recyclerTarifas);
    }
    private void showList(View view){
        list = utils.getTarifas(view.getContext());
       // Toast.makeText(getContext(),list.get(0).getNombre(),Toast.LENGTH_LONG).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new AdapterRecyclerGestTarifas(view.getContext(), list);
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        adapter.notifyDataSetChanged();

    }
}