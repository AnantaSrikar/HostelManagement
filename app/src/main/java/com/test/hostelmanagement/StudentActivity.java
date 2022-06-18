package com.test.hostelmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.ISingleAccountPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalException;
import com.test.hostelmanagement.databinding.ActivityStudentBinding;

public class StudentActivity extends AppCompatActivity {
    private static final String TAG = StudentActivity.class.getSimpleName();

    private ISingleAccountPublicClientApplication mSingleAccountApp;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityStudentBinding binding;

    View headerView;
    TextView header_nav_name;
    TextView header_nav_email;

    private void performOperationOnSignOut() {
        final String signOutText = "Signed Out.";
        Toast.makeText(getApplicationContext(), signOutText, Toast.LENGTH_SHORT)
                .show();
    }

    private void loadAccount() {
        if (mSingleAccountApp == null) {
            return;
        }

        mSingleAccountApp.getCurrentAccountAsync(new ISingleAccountPublicClientApplication.CurrentAccountCallback() {
            @Override
            public void onAccountLoaded(@Nullable IAccount activeAccount) {
            }

            @Override
            public void onAccountChanged(@Nullable IAccount priorAccount, @Nullable IAccount currentAccount) {
                if (currentAccount == null) {
                    // Perform a cleanup task as the signed-in account changed.
                    performOperationOnSignOut();
                }
            }

            @Override
            public void onError(@NonNull MsalException exception) {
                Log.d(TAG, exception.toString());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        binding = ActivityStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarStudent.toolbar);
        binding.appBarStudent.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Work in Progress!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gatepass, R.id.nav_complaints)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_student);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        headerView = navigationView.getHeaderView(0);
        header_nav_name = headerView.findViewById(R.id.header_nav_name);
        header_nav_email = headerView.findViewById(R.id.header_nav_email);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        header_nav_name.setText(extras.getString("name"));
        header_nav_email.setText(extras.getString("email"));

        PublicClientApplication.createSingleAccountPublicClientApplication(getApplicationContext(),
                R.raw.auth_config_single_account, new IPublicClientApplication.ISingleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(ISingleAccountPublicClientApplication application) {
                        mSingleAccountApp = application;
                        loadAccount();
                    }
                    @Override
                    public void onError(MsalException exception) {
                        Log.d(TAG, exception.toString());
                    }
                });

        loadAccount();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student, menu);
        return true;
    }

    // Whenever three buttons on right is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:

                if (mSingleAccountApp == null){
                    Toast.makeText(getApplicationContext(), "Something went wrong, please try restarting the app!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "DEBUG: mSingleAccountApp is null");
                }
                mSingleAccountApp.signOut(new ISingleAccountPublicClientApplication.SignOutCallback() {
                    @Override
                    public void onSignOut() {
                        performOperationOnSignOut();
                    }
                    @Override
                    public void onError(@NonNull MsalException exception){
                        Log.d(TAG, exception.toString());
                    }
                });

                Toast.makeText(this, "Logging out now!", Toast.LENGTH_LONG).show();
                finish();
                break;

            case R.id.action_reportbug:
                Toast.makeText(this, "Bug report feature in progress!", Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_student);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}