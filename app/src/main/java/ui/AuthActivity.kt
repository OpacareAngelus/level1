package ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.level1.R
import util.Parsers

class AuthActivity : AppCompatActivity() {

        private lateinit var btnReg: Button
        private lateinit var tvSignIn: TextView
        private lateinit var emailED: EditText
        private lateinit var passED: EditText
        private lateinit var sPref: SharedPreferences
        private lateinit var users: SharedPreferences
        private lateinit var rememberCB: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO read about ViewBinding and DataBinding(?)
        setContentView(R.layout.activity_auth)
        //Program search all buttons and set listener.
        btnReg = findViewById(R.id.btn_registration)
        tvSignIn = findViewById(R.id.tv_sign_in)
        rememberCB = findViewById(R.id.btn_remember)

        //Program create file with user data as shared preference.
        users = getSharedPreferences("Users", MODE_PRIVATE) //Move "Users" to constants

        //Program search all elements which need and add to vars.
        emailED = findViewById(R.id.et_email)
        passED = findViewById(R.id.et_password)
        tvSignIn = findViewById(R.id.tv_sign_in)

        loadText()
        setListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("AuthActivity", "onSaveInstanceState")
        outState.putString("e_mail", emailED.text.toString())
        outState.putString("password", passED.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("AuthActivity", "savedInstanceState")

        var savedText = savedInstanceState.getString("e_mail", "")
        emailED.setText(savedText)
        savedText = savedInstanceState.getString("password", "")
        passED.setText(savedText)
    }

    override fun onStop() {
        super.onStop()
        if (rememberCB.isChecked) saveText()
    }

    private fun setListeners() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btnReg.setOnClickListener {
            val intent = Intent(this, MyProfileActivity::class.java)
            regUser(intent)
        }
        tvSignIn.setOnClickListener {
            val intent = Intent(this, MyProfileActivity::class.java)
            checkPass(intent)
        }
    }

    /**This fun check password.
     * If password correct - program chance activity and user see new layout(profile).
     *
     * @param intent - program send parsed mail (second and first name to next activity) if password
     * correct with this intent.*/
    private fun checkPass(intent: Intent) {
        if (!allChecks())
            return
        val userName = Parsers.parseMail(emailED.text.toString())
        val userData = users.getString(userName, "")?.split("&")?.last()
        if (userData.equals(passED.text.toString())) {
            intent.putExtra("name", userName)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        } else {
            passED.error = "Incorrect password."
            return
        }
    }

    /**This fun create new user if current mail don't used before.
     * If it's happens - program auto change layout for user to him profile first time.
     * Next time user need put "Sigh in" when user use correct e-mail and password. */
    private fun regUser(intent: Intent) {
        if (emailCheck() && passwordCheck()) {
            val userName = Parsers.parseMail(emailED.text.toString())
            val ed: SharedPreferences.Editor = users.edit()
            val validTest = users.getString(userName, "")?.split("&")?.first().toString()
            if (validTest == emailED.text.toString()) {
                emailED.error =
                    getString(R.string.this_mail_using)
                return
            } else {
                ed.putString(userName, "${emailED.text}&${passED.text}")
                ed.apply()
                intent.putExtra(getString(R.string.name), userName)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        } else {
            allChecks()
        }
    }

    /**This fun is part of decomposition. Here all checks for errors.*/
    private fun allChecks(): Boolean {
        //When e-mail didn't used before and sure password wrong too.
        if (!emailCheck() && !passwordCheck()) {
            emailED.error = getString(R.string.invalid_email)
            passED.error = getString(R.string.invalid_password)
            return false
        }
        //When e-mail didn't used before.
        if (!emailCheck() && passwordCheck()) {
            emailED.error = getString(R.string.invalid_email)
            return false
        }
        //When password is wrong for this e-mail.
        if (emailCheck() && !passwordCheck()) {
            passED.error = getString(R.string.invalid_password)
            return false
        }
        return true
    }

    /**This fun test password as correct input.
     * If it's empty or have incorrect symbols - program back false.*/
    private fun passwordCheck(): Boolean {
        return passED.text.isNotEmpty()
    }

    /**This fun test e-mail.
     * If it's empty or have incorrect symbols - program back false.*/
    private fun emailCheck(): Boolean {
        //Program test are field is empty or not. Also text must have "@" and ".".
        return emailED.text.contains("@") &&
                emailED.text.contains(".") &&
                emailED.text.split("@").first().contains(".") &&
                emailED.text.isNotEmpty()

    }

    /**Fun saving current text in the fields of e-mail and password in the shared pref.*/
    private fun saveText() {
        sPref = getPreferences(MODE_PRIVATE)
        val ed: SharedPreferences.Editor = sPref.edit()
        ed.putString("e_mail", emailED.text.toString())
        ed.putString("password", passED.text.toString())
        ed.apply()
    }

    /**Fun loading  text to the fields of e-mail and password from the shared pref.*/
    private fun loadText() {
        sPref = getPreferences(MODE_PRIVATE)
        var savedText = sPref.getString("e_mail", "")
        emailED.setText(savedText)
        savedText = sPref.getString("password", "")
        passED.setText(savedText)
    }
}

