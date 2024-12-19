package com.grokmusic.gestiondehorarios.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grokmusic.gestiondehorarios.Adapters.AdapterRecyclerCambioUsuario;
import com.grokmusic.gestiondehorarios.Clases.Usuarios;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CambioUsuario extends Fragment {

    ArrayList<Usuarios> list = new ArrayList<Usuarios>();
    private RecyclerView recyclerView ;
    private AdapterRecyclerCambioUsuario adapter;
    private Button btnAdd;
    private SharedPreferences prefs;
    public CambioUsuario() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cambio_usuarios, container, false);
        prefs = getActivity().getSharedPreferences("preferencias",MODE_PRIVATE);
        bind(view);
        showList(view);
        return view;
    }
    private  void bind(View view){
        recyclerView = view.findViewById(R.id.recyclerUser);
    }
    private void showList(View view){
        list = utils.getUsuarios(view.getContext());
       // Toast.makeText(getContext(),list.get(0).getNombre(),Toast.LENGTH_LONG).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new AdapterRecyclerCambioUsuario(view.getContext(), list,getActivity(),prefs);
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        adapter.notifyDataSetChanged();

    }
}