package com.aadesh.passwordmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val executor = Executors.newSingleThreadExecutor()
        val activity: FragmentActivity = this

        val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if( errorCode == BiometricPrompt.ERROR_LOCKOUT_PERMANENT) {
                    activity.runOnUiThread {
                        Toast.makeText(
                            activity,
                            "You have been permanently banned from using this app!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

                else if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    activity.runOnUiThread {
                        Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT).show()
                    }
                }


                else if (errorCode == BiometricPrompt.ERROR_HW_UNAVAILABLE) {
                    activity.runOnUiThread {
                        Toast.makeText(activity, "Fingerprint Hardware Unavailable ", Toast.LENGTH_SHORT).show()
                    }
                }

                else if (errorCode == BiometricPrompt.ERROR_HW_NOT_PRESENT) {
                    activity.runOnUiThread {
                        Toast.makeText(activity, "Fingerprint Hardware not Available on your Device ", Toast.LENGTH_SHORT).show()
                    }
                }

                else if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                    activity.runOnUiThread {
                        Toast.makeText(activity, "Canceled ", Toast.LENGTH_SHORT).show()
                    }
                }



                else {
                    Toast.makeText(activity, "Please Register fingerprint and try again", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                val intent = Intent(this@MainActivity, PasswordManager::class.java)
                startActivity(intent)
                activity.runOnUiThread {
                    Toast.makeText(activity, "Fingerprint Login Successful", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                activity.runOnUiThread {
                    Toast.makeText(activity, "Fingerprint not Recognized", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint Login")
            .setSubtitle("Use Fingerprint for Easier Access")
            .setDescription("Confirm Fingerprint to Continue")
            .setNegativeButtonText("Cancel")
            .build()

        findViewById<Button>(R.id.authentication_button).setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }
    }
}
