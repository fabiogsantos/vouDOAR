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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import br.com.tcc.tecdam.voudoar.campanha.ui.activity.ListaCampanhasActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_LOGIN_GOOGLE = 1000;
    public static final String LOG_SIGN_IN = "SignIn";
    public static final String WEB_CLIENT_SERVER_BACKEND = "303342344584-9jt20tb3n1ussajui5cvvb9rq1rj8rcl.apps.googleusercontent.com";

    private NavigationView navigationView;
    private View headerView;

    private CallbackManager callbackManager;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

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

    @Override
    protected void onStart() {
        super.onStart();
        //updateProfileUserUI();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    private void configuraNavigatorBar() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
    }

    private void configuraLogout() {
        if (headerView == null) {
            configuraNavigatorBar();
        }

        Button logout = headerView.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logoutGoogle();
                //logoutFacebook();
                logoutFirebaseAuth();
                clearProfileUserUI();
            }
        });
    }

    private void configuraLoginFacebook() {
        if (headerView == null) {
            configuraNavigatorBar();
        }

        // Callback registration
        callbackManager = CallbackManager.Factory.create();

        //LoginButton loginButtonFacebook = (LoginButton) headerView.findViewById(R.id.login_facebook_button);
        //loginButtonFacebook.setReadPermissions("email", "public_profile");
        // If using in a fragment
        //loginButtonFacebook.setFragment(this);

        Button loginButtonFacebook = headerView.findViewById(R.id.login_facebook_button);
        loginButtonFacebook.setOnClickListener(this);

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final AuthCredential credential = getAuthCredential(loginResult);
                firebaseAuthWithCredential(credential);
            }

            @Override
            public void onCancel() {
                // App code
                Log.w("SignInFacebook", "signInResult: canceled");
                //Toast.makeText(MainActivity.this,"Login Facebook cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.w("SignInFacebook", "signInResult:failed code=" + exception.getMessage());
                Toast.makeText(MainActivity.this,exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        if (accessToken != null && !accessToken.isExpired()) {
//            firebaseAuthWithFacebook(accessToken);
//        }
    }

    @NonNull
    private AuthCredential getAuthCredential(LoginResult loginResult) {
        //Toast.makeText(MainActivity.this,"Login Facebook sucesso", Toast.LENGTH_SHORT).show();
        Log.d(LOG_SIGN_IN, "handleFacebookAccessId:" + loginResult.getAccessToken().getUserId());
        return FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
    }

    @NonNull
    private AuthCredential getAuthCredential(String email, String password) {
        //Toast.makeText(MainActivity.this,"Login e-Mail sucesso", Toast.LENGTH_SHORT).show();
        Log.d(LOG_SIGN_IN, "handleEmailAccess:" + email);
        return EmailAuthProvider.getCredential(email, password);
    }

    private void configuraLoginGoogle() {
        if (headerView == null) {
            configuraNavigatorBar();
        }

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateProfileUserUI();
            }
        };

        // Set the dimensions of the sign-in button.
        SignInButton loginGoogleButton = headerView.findViewById(R.id.login_google_button);
        loginGoogleButton.setSize(SignInButton.SIZE_ICON_ONLY);
        loginGoogleButton.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_SERVER_BACKEND)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateProfileUserUI(account);
    }

//    private void logoutGoogle() {
//        mGoogleSignInClient.revokeAccess()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        clearProfileUserUI();
//                    }
//                });
//    }

    private void logoutFirebaseAuth() {
        firebaseAuth.signOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_LOGIN_GOOGLE) {

            //Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //onLoginGoogleResult(task);

            //logoutFirebaseAuth();

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                final AuthCredential credential = getAuthCredential(result);
                firebaseAuthWithCredential(credential);

            } else {
                Log.w(LOG_SIGN_IN, "Sign in:failure - result: " + result);
                //Toast.makeText(MainActivity.this, "Sign in failed.",
                //        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    private AuthCredential getAuthCredential(GoogleSignInResult result) {
        GoogleSignInAccount account = result.getSignInAccount();
        Log.d(LOG_SIGN_IN, "firebaseAuthWithCredential - Id:" + account.getId());
        return GoogleAuthProvider.getCredential(account.getIdToken(), null);
    }

    private void firebaseAuthWithCredential(AuthCredential credential) {

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        // Se usuário ja conectado permite escolher outro usuário
        if (currentUser != null) {

            firebaseAuth.getCurrentUser().linkWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(LOG_SIGN_IN, "linkWithCredential:success");
                            } else {
                                Log.w(LOG_SIGN_IN, "linkWithCredential:failure", task.getException());
                                //Toast.makeText(MainActivity.this, "Authentication failed.",
                                //        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(LOG_SIGN_IN, "signInWithCredential:success");
                            } else {
                                Log.w(LOG_SIGN_IN, "signInWithCredential:failure", task.getException());
                                //Toast.makeText(MainActivity.this, "Authentication failed.",
                                //        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        //Log.d(LOG_SIGN_IN, "firebaseAuthWithGoogle:" + acct.getId());
//
//        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(LOG_SIGN_IN, "signInWithCredential:success");
//                        } else {
//                            Log.w(LOG_SIGN_IN, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

//    private void onLoginGoogleResult(Task<GoogleSignInAccount> task) {
//        try {
//            GoogleSignInAccount account = task.getResult(ApiException.class);
//
//            // Signed in successfully, show authenticated UI.
//            //updateProfileUserUI(account);
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(LOG_SIGN_IN, "signInResult:failed code=" + e.getStatusCode());
//            //clearProfileUserUI();
//        }
//        updateProfileUserUI();
//    }

//    private void firebaseAuthWithFacebook(AccessToken token) {
//
//        //UserRequest.makeUserRequest(new GetUserCallback(MainActivity.this).getCallback());
//
//        Log.d(LOG_SIGN_IN, "handleFacebookAccessToken:" + token);
//
//        //logoutFirebaseAuth();
//
//        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(LOG_SIGN_IN, "signInWithCredential:success");
//                            //FirebaseUser user = firebaseAuth.getCurrentUser();
//                            //updateProfileUserUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(LOG_SIGN_IN, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            //clearProfileUserUI();
//                        }
//                    }
//                });
//    }

//    private void logoutFacebook() {
//        LoginManager.getInstance().logOut();
//        clearProfileUserUI();
//    }

    private void updateProfileUserUI() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Log.d(LOG_SIGN_IN, "onAuthStateChanged:signed_in:" + user.getUid());
            updateProfileUserUI(user);
        } else {
            Log.d(LOG_SIGN_IN, "onAuthStateChanged:signed_out");
            clearProfileUserUI();
        }
    }

    private void clearProfileUserUI() {
        updateProfileUserUI("","",null);
    }

    private void updateProfileUserUI(FirebaseUser user) {
        if (user != null) {
            String email = user.getEmail();
            String nome = user.getDisplayName();
            Uri urlImagem = user.getPhotoUrl();

            updateProfileUserUI(email, nome, urlImagem);
        }
    }

//    private void updateProfileUserUI(GoogleSignInAccount account) {
//        if (account != null) {
//            String email = account.getEmail();
//            String nome = account.getDisplayName();
//            Uri urlImagem = account.getPhotoUrl();
//
//            updateProfileUserUI(email, nome, urlImagem);
//        }
//    }
//
//    private void updateProfileUserUI(User user) {
//        String nome   = user.getName();
//        String email = "";
//        if (user.getEmail() == null) {
//            email = getString(R.string.no_email_perm);
//        } else {
//            email = user.getEmail();
//        }
//        Uri urlImagem = user.getPicture();
//        updateProfileUserUI(email,nome,urlImagem);
//    }

    private void updateProfileUserUI(String email, String nome, Uri urlImagem) {
        if (headerView == null) {
            configuraNavigatorBar();
        }

        TextView campoEmail = headerView.findViewById(R.id.nav_header_main_email);
        TextView campoNome = headerView.findViewById(R.id.nav_header_main_nome);
        ImageView campoImagem = headerView.findViewById(R.id.nav_header_main_imageView);

        if (campoEmail != null) {
            campoEmail.setText(email);
        }

        if (campoNome != null) {
            if (! nome.isEmpty()) {
                campoNome.setText(nome);
            } else {
                campoNome.setText("Usuário Anônimo");
            }
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w("SignInGoogle", "signInResult:failed error=" + connectionResult.getErrorMessage());
        //Toast.makeText(MainActivity.this, "Connection Failed: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_google_button:
                loginGoogle();
                break;
            case R.id.login_facebook_button:
                loginFacebook();
                break;
            default:
                Toast.makeText(this, R.string.opcao_nao_acessivel, Toast.LENGTH_SHORT).show();
        }
    }

    private void loginGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_LOGIN_GOOGLE);
    }

    private void loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
    }
}
