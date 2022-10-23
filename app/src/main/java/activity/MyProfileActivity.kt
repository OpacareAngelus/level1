package activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.level1.R
import com.example.level1.databinding.MyProfileBinding

class MyProfileActivity : AppCompatActivity() {

    private lateinit var binding: MyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvName.text = intent.getStringExtra("name")
    }
}