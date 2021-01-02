package tech.leson.yonstore.ui.success

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_success.*
import tech.leson.yonstore.R
import tech.leson.yonstore.ui.main.MainActivity

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        btnBackToOrder.setOnClickListener {
            val i = MainActivity.getIntent(this)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
    }
}
