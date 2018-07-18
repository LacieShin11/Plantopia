package plantopia.sungshin.plantopia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun signInBtnOnClicked(view: View) {
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}
