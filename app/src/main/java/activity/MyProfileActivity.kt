package activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.level1.R

class MyProfileActivity : AppCompatActivity() {

    private lateinit var nameTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Program search all elements which need and add to vars.
        setContentView(R.layout.my_profile)

        //In this part program take info from auth_activity. It's firstname and second name.
        nameTV = findViewById(R.id.tv_name)
        //Program set this firstname and second name in user profile.
        nameTV.text = intent.getStringExtra("name")
    }
}