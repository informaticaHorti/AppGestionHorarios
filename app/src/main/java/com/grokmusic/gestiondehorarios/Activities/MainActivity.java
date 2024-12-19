package com.grokmusic.gestiondehorarios.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.grokmusic.gestiondehorarios.Clases.Tarifas;
import com.grokmusic.gestiondehorarios.Clases.Usuarios;
import com.grokmusic.gestiondehorarios.Fragments.CambioUsuario;
import com.grokmusic.gestiondehorarios.Fragments.DefinirHorario;
import com.grokmusic.gestiondehorarios.Fragments.GestionHorarios;
import com.grokmusic.gestiondehorarios.Fragments.GestionNominas;
import com.grokmusic.gestiondehorarios.Fragments.GestionTarifas;
import com.grokmusic.gestiondehorarios.Fragments.GestionUsuarios;
import com.grokmusic.gestiondehorarios.R;
import com.grokmusic.gestiondehorarios.utils.utils;

import java.util.ArrayList;

import kotlinx.coroutines.Deferred;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SharedPreferences preferences;
    private Context context = MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("preferencias",MODE_PRIVATE);
        init();
        setDefaultFragment();
        setupNavigationView();
        ArrayList<Tarifas> tarifas = utils.getTarifas(this);
        ArrayList<Usuarios> usuarios = utils.getUsuarios(this);
        if(tarifas.size()>0){
            if(usuarios.size()>0){
                if(utils.getIdUsuarioActual(preferences)!=""){
                    utils.changeFragment(MainActivity.this,new GestionHorarios(),"Gestion de Horarios");
                }
            }else{
                utils.addUsuario(MainActivity.this);
            }
        }else{
            utils.addTarifa(MainActivity.this);
        }
        checkPermisos();

    }
    private void  checkPermisos() {
        int per = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int img = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (per != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);

        }
        if (img != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);

        }
    }

    private void init() {
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer_layout);
        setToolbar();
        utils.crearDbHorarios(MainActivity.this);
        utils.crearDbUsuarios(MainActivity.this);
        utils.crearDbTarifas(MainActivity.this);
        utils.crearDbHorarioDef(MainActivity.this);
    }

    private void setToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setupNavigationView() {

        /*navigationView.setNavigationItemSelectedListener(menuItem -> {
            Fragment fragment = getFragmentForMenuItem(menuItem.getItemId());
            if (fragment != null) {
                changeFragment(fragment, menuItem);
                drawerLayout.closeDrawers();
            }
            return false;
        });*/
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = getFragmentForMenuItem(item.getItemId());
                if (fragment != null) {
                    changeFragment(fragment, item);
                    drawerLayout.closeDrawers();
                }
                return false;
            }
        });

    }

    private Fragment getFragmentForMenuItem(int itemId) {
        Fragment fragment = null;
        if(itemId == R.id.menu_gestion_horarios){fragment = new GestionHorarios(this,navigationView);}
        if(itemId == R.id.menu_gestion_usuarios){fragment = new GestionUsuarios();}
        if(itemId == R.id.menu_gestion_tarifas){fragment = new GestionTarifas();}
        if(itemId == R.id.menu_gestion_nominas){fragment = new GestionNominas();}
        if(itemId == R.id.menu_cambio_usuario){fragment = new CambioUsuario();}
        if(itemId == R.id.menu_definir_hora_def){fragment = new DefinirHorario();}
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment fragment, MenuItem item) {
        // Desmarcar todos los elementos del menú
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        // Reemplazar el fragmento
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        // Marcar el elemento seleccionado
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }


    private void setDefaultFragment() {
       // changeFragment(new CambioUsuario(), navigationView.getMenu().getItem(0));
        utils.changeFragment(MainActivity.this, new CambioUsuario(),"Selecciona Usuario");
    }
}