package view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication2.R
import com.example.myapplication2.util.Parsers
import com.example.myapplication2.view.MyProfile

class AuthActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mainView: ConstraintLayout
    private lateinit var btnReg: Button
    private lateinit var btnSignIn: Button
    private lateinit var emailField: EditText
    private lateinit var passField: EditText
    private lateinit var sPref: SharedPreferences
    private lateinit var users: SharedPreferences
    private lateinit var rememberBtn: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth)
        //Program search all buttons and set listener.
        btnReg = findViewById(R.id.btn_registration)
        btnReg.setOnClickListener(this)
        btnSignIn = findViewById(R.id.btn_signIn)
        btnSignIn.setOnClickListener(this)
        rememberBtn = findViewById(R.id.btn_remember)

        //Program create file with user data as shared preference.
        users = getSharedPreferences("Users", MODE_PRIVATE)

        //Program search all elements which need and add to vars.
        mainView = findViewById(R.id.auth_activity)
        emailField = findViewById(R.id.et_email)
        passField = findViewById(R.id.et_password)
        btnSignIn = findViewById(R.id.btn_signIn)

        loadText()
    }

    override fun onClick(v: View) {
        val intent = Intent(this, MyProfile::class.java)
        when (v.id) {
            R.id.btn_registration -> {
                regUser(intent)
            }
            R.id.btn_signIn -> {
                checkPass(intent)
            }
            else -> {}
        }
    }

    /**This fun check password.
     * If password correct - program chance activity and user see new layout(profile).
     *
     * @param intent - program send parsed mail (scnd and frst name to next activity) if password
     * correct with this intent.*/
    private fun checkPass(intent: Intent) {
        if (!allChecks())
            return
        val userName = Parsers().parseMail(emailField.text.toString())
        val userData = users.getString(userName, "")?.split("&")?.last()
        if (userData.equals(passField.text.toString())) {
            intent.putExtra("name", userName)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        } else {
            passField.error = ("Incorrect password.")
            return
        }
    }

    /**This fun create new user if current mail don't used before.
     * If it's happens - program auto change layout for user to him profile first time.
     * Next time user need put "Sigh in" when user use correct e-mail and password. */
    private fun regUser(intent: Intent) {
        if (emailCheck() && passwordCheck()) {
            val userName = Parsers().parseMail(emailField.text.toString())
            val ed: SharedPreferences.Editor = users.edit()
            val validTest = users.getString(userName, "")?.split("&")?.first().toString()
            if (validTest == emailField.text.toString()) {
                emailField.error = ("User with this e-mail is now registered.")
                return
            } else {
                ed.putString(userName, "${emailField.text}&${passField.text}")
                ed.apply()
                intent.putExtra("name", userName)
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
            emailField.error = ("Invalid email.")
            passField.error = ("Invalid password.")
            return false
        }
        //When e-mail didn't used before.
        if (!emailCheck() && passwordCheck()) {
            emailField.error = ("Invalid email.")
            return false
        }
        //When password is wrong for this e-mail.
        if (emailCheck() && !passwordCheck()) {
            passField.error = ("Invalid password.")
            return false
        }
        return true
    }

    /**This fun test password as correct input.
     * If it's empty or have incorrect symbols - program back false.*/
    private fun passwordCheck(): Boolean {
        //Right now only if empty field.
        if (passField.text.isNotEmpty())
            return true
        return false
    }

    /**This fun test e-mail.
     * If it's empty or have incorrect symbols - program back false.*/
    private fun emailCheck(): Boolean {
        //Program test are field is empty or not. Also text must have "@" and ".".
        if (emailField.text.contains("@") &&
            emailField.text.contains(".") &&
            emailField.text.isNotEmpty()
        )
            return true
        return false
    }

    /**Fun saving current text in the fields of e-mail and password in the shared pref.*/
    private fun saveText() {
        sPref = getPreferences(MODE_PRIVATE)
        val ed: SharedPreferences.Editor = sPref.edit()
        ed.putString("e_mail", emailField.text.toString())
        ed.putString("password", passField.text.toString())
        ed.apply()
    }

    /**Fun loading  text to the fields of e-mail and password from the shared pref.*/
    private fun loadText() {
        sPref = getPreferences(MODE_PRIVATE)
        var savedText = sPref.getString("e_mail", "")
        emailField.setText(savedText)
        savedText = sPref.getString("password", "")
        passField.setText(savedText)
    }

    override fun onStop() {
        super.onStop()
        if (rememberBtn.isChecked)
            saveText()
    }
}

