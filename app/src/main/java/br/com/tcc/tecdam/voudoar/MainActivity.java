package br.com.tcc.tecdam.voudoar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import br.com.tcc.tecdam.voudoar.campanha.ui.activity.ListaCampanhasActivity;
import br.com.tcc.tecdam.voudoar.facebook.callbacks.GetUserCallback;
import br.com.tcc.tecdam.voudoar.facebook.entities.User;
import br.com.tcc.tecdam.voudoar.facebook.requests.UserRequest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GetUserCallback.IGetUserResponse {

    private static final int RC_LOGIN_GOOGLE = 1000;
    private GoogleSignInClient mGoogleSignInClient;
    private NavigationView navigationView;
    private CallbackManager callbackManager;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        configuraNavigatorBar();

        configuraLoginGoogle();

        configuraLoginFacebook();

        configuraLogout();

        chamaListaCampanha();
    }

    private void configuraLogout() {
        if (headerView == null) {
            configuraNavigatorBar();
        }

        Button logout = headerView.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutGoogle();
                logoutFacebook();
            }
        });
    }

    private void logoutFacebook() {
        LoginManager.getInstance().logOut();
        clearProfileUserUI();
    }

    private void configuraLoginFacebook() {
        if (headerView == null) {
            configuraNavigatorBar();
        }

        LoginButton loginButton = (LoginButton) headerView.findViewById(R.id.login_facebook_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        //loginButton.setFragment(this);

        // Callback registration
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this,"Sucesso login Facebook", Toast.LENGTH_SHORT).show();
                onLoginFacebookResult();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(MainActivity.this,"Login Facebook cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.w("SignInFacebook", "signInResult:failed code=" + exception.getMessage());
                Toast.makeText(MainActivity.this,exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            onLoginFacebookResult();
        }
    }

    private void configuraNavigatorBar() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
    }

    private void configuraLoginGoogle() {
        if (headerView == null) {
            configuraNavigatorBar();
        }

        // Set the dimensions of the sign-in button.
        SignInButton loginGoogleButton = headerView.findViewById(R.id.login_google_button);
        loginGoogleButton.setSize(SignInButton.SIZE_ICON_ONLY);
        loginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginGoogle();
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateProfileUserUI(account);
    }

    private void loginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_LOGIN_GOOGLE);
    }

    private void logoutGoogle() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        clearProfileUserUI();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_LOGIN_GOOGLE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            onLoginGoogleResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onLoginGoogleResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateProfileUserUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SignIn", "signInResult:failed code=" + e.getStatusCode());
            //updateProfileUserUI(null);
        }
    }

    private void onLoginFacebookResult() {
        UserRequest.makeUserRequest(new GetUserCallback(MainActivity.this).getCallback());
    }

    private void clearProfileUserUI() {
        updateProfileUserUI("","",null);
    }

    private void updateProfileUserUI(GoogleSignInAccount account) {
        String email  = account.getEmail();
        String nome   = account.getDisplayName();
        Uri urlImagem = account.getPhotoUrl();

        updateProfileUserUI(email,nome,urlImagem);
    }

    private void updateProfileUserUI(User user) {
        String nome   = user.getName();
        String email = "";
        if (user.getEmail() == null) {
            email = getString(R.string.no_email_perm);
        } else {
            email = user.getEmail();
        }
        Uri urlImagem = user.getPicture();
        updateProfileUserUI(email,nome,urlImagem);
    }

    private void updateProfileUserUI(String email, String nome, Uri urlImagem) {
        if (headerView == null) {
            configuraNavigatorBar();
        }

        TextView campoEmail = headerView.findViewById(R.id.nav_header_main_email);
        TextView campoNome = headerView.findViewById(R.id.nav_header_main_nome);
        ImageView campoImagem = headerView.findViewById(R.id.nav_header_main_imageView);

        if (campoEmail != null) {
            campoEmail.setText(email);
        } else {
            campoEmail.setText("Anônimo");
        }

        if (campoNome != null) {
            campoNome.setText(nome);
        }

        if (campoImagem != null) {
            if (urlImagem == null) {
                campoImagem.setImageResource(R.drawable.person);
            } else {
                Picasso.with(this)
                        .load(urlImagem)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading_error)
                        .into(campoImagem);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_campanhas_sociais) {
            chamaListaCampanha();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void chamaListaCampanha() {
        Intent intent = new Intent(MainActivity.this, ListaCampanhasActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCompleted(User user) {
        updateProfileUserUI(user);
    }
}
