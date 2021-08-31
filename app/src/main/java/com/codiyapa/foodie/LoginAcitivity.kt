package com.codiyapa.foodie

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_acitivity.*
import java.util.concurrent.TimeUnit


@Suppress("DEPRECATION")
class LoginAcitivity : AppCompatActivity() {


    private val auth = FirebaseAuth.getInstance()
    private val RC_Sign = 123
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_acitivity)
        ccp.registerCarrierNumberEditText(editText_carrierNumber)

        send_otp.setOnClickListener {
            val intent = Intent(this, OtpActivity::class.java)
            intent.putExtra("number", "${ccp.fullNumberWithPlus}")
            startActivity(intent)
            finish()
        }

        mail_signIn.setOnClickListener {
            signIn_with_mail()
        }
        google_signIn.setOnClickListener {
            signInwith_Gmail()
        }
        facebook_signIn.setOnClickListener {
            signInwith_facebook()
        }
    }


    private fun signInwith_facebook() {
        val providers = arrayListOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.FacebookBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .build(), RC_Sign
        )
    }

    private fun signIn_phone() {

    }

    private fun signInwith_Gmail() {
        val providers = arrayListOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .build(), RC_Sign
        )
    }


    private fun signIn_with_mail() {
        val providers = arrayListOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setTheme(R.style.AuthUi)
                .setLogo(R.drawable.burger).setAvailableProviders(providers)
                .build(), RC_Sign
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_Sign) {
            var response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                if (response == null) {
                    finish()
                }
                if (response?.error?.errorCode == ErrorCodes.NO_NETWORK) {
                    return
                }
                if (response?.error?.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(
                        this,
                        response?.error?.errorCode.toString(),
                        Toast.LENGTH_LONG
                    )
                }
                return
            }
        }
    }
}