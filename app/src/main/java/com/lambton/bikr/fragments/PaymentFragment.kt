package com.lambton.bikr.fragments

import FirebaseNetworkCallBack
import User
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lambton.bikr.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.samples.wallet.PaymentsUtil
import com.google.android.gms.samples.wallet.microsToString
import com.google.android.gms.wallet.*
import com.lambton.bikr.firebase.FirebaseRepository
import com.manojbhadane.PaymentCardView
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToLong


class PaymentFragment : Fragment() {

    private lateinit var mPaymentCard: PaymentCardView
    private lateinit var paymentsClient: PaymentsClient
    private val shippingCost = (90 * 1000000).toLong()
private  lateinit var  googlePayButton: View
 //   private lateinit var garmentList: JSONArray
  //  private lateinit var selectedGarment: JSONObject
 val repository = FirebaseRepository()
   lateinit var  user: User
    /**
     * Arbitrarily-picked constant integer you define to track a request for payment data activity.
     *
     * @value #LOAD_PAYMENT_DATA_REQUEST_CODE
     */
    private val LOAD_PAYMENT_DATA_REQUEST_CODE = 991

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.frg_payment, container, false)
        setHasOptionsMenu(true)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Payment"
        mPaymentCard = view.findViewById(R.id.card)

        mPaymentCard.setOnPaymentCardEventListener(object :
            PaymentCardView.OnPaymentCardEventListener {
            override fun onCardDetailsSubmit(month: String, year: String, cardNumber: String, cvv: String) {
                Toast.makeText(activity, "Card details submitted", Toast.LENGTH_SHORT).show()
                user.cardNum = cardNumber
                user.expMonth = month
                user.expYear = year
                repository.updateCardDetails("123",user,object: FirebaseNetworkCallBack {
                    override fun onSuccess(response: Any) {
                       // user = response as User




                    }

                    override fun onError(excecption: String) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }})


            }

            override fun onError(error: String) {
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
            }

            override fun onCancelClick() {
                mPaymentCard.setVisibility(View.GONE)
            }
        })

        paymentsClient = PaymentsUtil.createPaymentsClient(context)


        googlePayButton = view.findViewById(R.id.googlePayButton)
        possiblyShowGooglePayButton()
        googlePayButton.setOnClickListener { requestPayment() }



        repository.getLoggedInUser("123",object: FirebaseNetworkCallBack {
            override fun onSuccess(response: Any) {
                  user = response as User




            }

            override fun onError(excecption: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }})




        return view
    }

    private fun possiblyShowGooglePayButton() {

        val isReadyToPayJson = PaymentsUtil.isReadyToPayRequest() ?: return
        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener { completedTask ->
            try {
                completedTask.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
            } catch (exception: ApiException) {
                // Process error
                Log.w("isReadyToPay failed", exception)
            }
        }
    }
    private fun setGooglePayAvailable(available: Boolean) {
        if (available) {
            googlePayButton.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                activity,
                "Unfortunately, Google Pay is not available on this device",
                Toast.LENGTH_LONG).show();
        }
    }

    private fun requestPayment() {

        // Disables the button to prevent multiple clicks.
        googlePayButton.isClickable = false

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
//        val garmentPriceMicros = (selectedGarment.getDouble("price") * 1000000).roundToLong()

        val garmentPriceMicros = (25.25 * 1000000).roundToLong()
        val price = (garmentPriceMicros + shippingCost).microsToString()

        val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(price)
        if (paymentDataRequestJson == null) {
            Log.e("RequestPayment", "Can't fetch payment data request")
            return
        }
        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        if (request != null) {
            activity?.let {
                AutoResolveHelper.resolveTask(
                    paymentsClient.loadPaymentData(request), it, LOAD_PAYMENT_DATA_REQUEST_CODE)
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            // value passed in AutoResolveHelper
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK ->
                        data?.let { intent ->
                            PaymentData.getFromIntent(intent)?.let(::handlePaymentSuccess)
                        }
                    Activity.RESULT_CANCELED -> {
                        // Nothing to do here normally - the user simply cancelled without selecting a
                        // payment method.
                    }

                    AutoResolveHelper.RESULT_ERROR -> {
                        AutoResolveHelper.getStatusFromIntent(data)?.let {
                            handleError(it.statusCode)
                        }
                    }
                }
                // Re-enables the Google Pay payment button.
                googlePayButton.isClickable = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        if (id == R.id.action_sort) {
//            Collections.sort(restaurantList, ratingComparator1)
//            restaurantList.reverse()
//        }
//        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }
    private fun handlePaymentSuccess(paymentData: PaymentData) {
        val paymentInformation = paymentData.toJson() ?: return

        try {
            // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
            val paymentMethodData = JSONObject(paymentInformation).getJSONObject("paymentMethodData")

            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".
            if (paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("type") == "PAYMENT_GATEWAY" && paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token") == "examplePaymentMethodToken") {

                AlertDialog.Builder(activity)
                    .setTitle("Warning")
                    .setMessage("Gateway name set to \"example\" - please modify " +
                            "Constants.java and replace it with your own gateway.")
                    .setPositiveButton("OK", null)
                    .create()
                    .show()
            }

            val billingName = paymentMethodData.getJSONObject("info")
                .getJSONObject("billingAddress").getString("name")
            Log.d("BillingName", billingName)
            Toast.makeText(activity,billingName, Toast.LENGTH_LONG).show()

//            Toast.makeText(activity, getString(com.google.android.gms.wallet.R.string.payments_show_name, billingName), Toast.LENGTH_LONG).show()

            // Logging token string.
            Log.d("GooglePaymentToken", paymentMethodData
                .getJSONObject("tokenizationData")
                .getString("token"))

        } catch (e: JSONException) {
            Log.e("handlePaymentSuccess", "Error: " + e.toString())
        }

    }

    /**
     * At this stage, the user has already seen a popup informing them an error occurred. Normally,
     * only logging is required.
     *
     * @param statusCode will hold the value of any constant from CommonStatusCode or one of the
     * WalletConstants.ERROR_CODE_* constants.
     * @see [
     * Wallet Constants Library](https://developers.google.com/android/reference/com/google/android/gms/wallet/WalletConstants.constant-summary)
     */
    private fun handleError(statusCode: Int) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode))
    }

//    private fun fetchRandomGarment() : JSONObject {
//        if (!::garmentList.isInitialized) {
//            garmentList = Json.readFromResources(activity, cR.raw.tshirts)
//        }
//
//        val randomIndex:Int = Math.round(Math.random() * (garmentList.length() - 1)).toInt()
//        return garmentList.getJSONObject(randomIndex)
//    }
//
//    private fun displayGarment(garment:JSONObject) {
//        detailTitle.setText(garment.getString("title"))
//        detailPrice.setText("\$${garment.getString("price")}")
//
//        val escapedHtmlText:String = Html.fromHtml(garment.getString("description")).toString()
//        detailDescription.setText(Html.fromHtml(escapedHtmlText))
//
//        val imageUri = "@drawable/${garment.getString("image")}"
//        val imageResource = resources.getIdentifier(imageUri, null, packageName)
//        detailImage.setImageResource(imageResource)
//    }
}