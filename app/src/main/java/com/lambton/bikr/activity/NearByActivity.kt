package com.lambton.bikr.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lambton.bikr.R
import com.manojbhadane.PaymentCardView
import com.manojbhadane.PaymentCardView.OnPaymentCardEventListener

class NearByActivity : AppCompatActivity() {

    private lateinit var mPaymentCard: PaymentCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frg_payment)


        mPaymentCard = findViewById(R.id.card)

        mPaymentCard.setOnPaymentCardEventListener(object : OnPaymentCardEventListener {
            override fun onCardDetailsSubmit(month: String, year: String, cardNumber: String, cvv: String) {
                Toast.makeText(this@NearByActivity, "Card details submitted", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String) {
                Toast.makeText(this@NearByActivity, error, Toast.LENGTH_SHORT).show()
            }

            override fun onCancelClick() {
                mPaymentCard.setVisibility(View.GONE)
            }
        })


    }

//    val intent = Intent(this@HomeActivity, FireBaseLoginActivity::class.java)
//    startActivity(intent)
//    this@HomeActivity.finish()
}
