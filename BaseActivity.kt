package velociter.kumar.property

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    private var mSnackBar: Snackbar? = null
    private var connectivityReceiver: ConnectivityReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityReceiver = ConnectivityReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver,filter)
    }


    private fun showMessage(isConnected: Boolean) {



        if (!isConnected) {

            val messageToUser = "You are offline now." //TODO

            mSnackBar = Snackbar.make(findViewById(R.id.rootLayouts), messageToUser, Snackbar.LENGTH_LONG) //Assume "rootLayout" as the root layout of every activity.
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()
        } else {
            mSnackBar?.dismiss()
        }


    }

    override fun onResume() {
        super.onResume()

        ConnectivityReceiver.connectivityReceiverListener = this
    }

    /**
     * Callback will be called when there is change
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectivityReceiver)
    }
}