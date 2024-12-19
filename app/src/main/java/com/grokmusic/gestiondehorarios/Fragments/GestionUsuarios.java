package com.grokmusic.gestiondehorarios.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.grokmusic.gestiondehorarios.Adapters.AdapterRecyclerUsuarios;
import com.grokmusic.gestiondehorarios.Clases.Usuarios;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.util.ArrayList;

public class GestionUsuarios extends Fragment {

    ArrayList<Usuarios> list = new ArrayList<Usuarios>();
    private RecyclerView recyclerView ;
    private AdapterRecyclerUsuarios adapter;
    private Button btnAdd;
    public GestionUsuarios() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gestion_usuarios, container, false);
        bind(view);
        showList(view);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.changeFragment(getActivity(),new fragmentCreaUsuario(0,"",0,"0.00","0.00","0.00"),"Crear Usuario");
            }
        });
        return view;
    }
    private  void bind(View view){
        btnAdd = view.findViewById(R.id.btnAddUser);
        recyclerView = view.findViewById(R.id.recyclerUser);
    }
    private void showList(View view){
        list = utils.getUsuarios(view.getContext());
       // Toast.makeText(getContext(),list.get(0).getNombre(),Toast.LENGTH_LONG).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new AdapterRecyclerUsuarios(view.getContext(), list,getActivity());
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        adapter.notifyDataSetChanged();

    }
}