package com.codiyapa.foodie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_otp.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private lateinit var number: String
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private  var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var storedVerificationId : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        number = intent.getStringExtra("number").toString()
        textView2.text = textView2.text.toString()+"$number"
        Log.d("number", number)
        initCallback()
        startVerification()

        verify_otp.setOnClickListener {
            val otp = otp_text.text.toString()
            if(otp.isEmpty()){
                otp_text.error = "Don't leave Empty"
            }else{
                val credential = PhoneAuthProvider.getCredential(storedVerificationId, otp)
                signup(credential)
            }
        }
    }



    private fun initCallback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                signup(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {

                if (p0 is FirebaseAuthInvalidCredentialsException)
                    Log.i("ERROR INVALID", p0.message.toString())
                else if (p0 is FirebaseTooManyRequestsException)
                    Log.i("ERROR TOO MANY REQUESTS", p0.message.toString())

                fail(p0)
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAGA", "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
//            resendToken = token
            }
        }
    }
    private fun fail(error: FirebaseException) {
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()

    }



    private fun signup(credential: PhoneAuthCredential) {
        Log.i("AAUUTTHH", "here1")
        auth.signInWithCredential(credential).addOnSuccessListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun startVerification() {
        val option = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("$number")
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()

        PhoneAuthProvider.verifyPhoneNumber(option)
    }
}