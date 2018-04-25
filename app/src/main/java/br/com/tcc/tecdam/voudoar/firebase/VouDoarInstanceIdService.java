package br.com.tcc.tecdam.voudoar.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by fabio.goncalves on 24/04/2018.
 */

public class VouDoarInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("token firebase", "Refreshed token: " + refreshedToken);

        // Envia o token de ID da instância para o servidor de aplicativos.
        enviaTokenParaServidor(refreshedToken);
    }

    private void enviaTokenParaServidor(String refreshedToken) {
    }
}
