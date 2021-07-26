package velociter.kumar.property

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.telephony.PhoneNumberUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_contactus.*

class ActivityContactUs : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactus)
        var phoneNo = intent.getStringExtra("cellno")
        var whatsappNo = intent.getStringExtra("whatsapp")
        whatsappcall.setOnClickListener {
            openWhatsApp(whatsappNo)
        }




        tollfree.setOnClickListener {
            /*if (checkPermission(Manifest.permission.CALL_PHONE)) {
                // dial!!.isEnabled = true
                makeACall(phoneNo)
            } else {
                // dial!!.isEnabled = false
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    ActivityContactUs.MAKE_CALL_PERMISSION_REQUEST_CODE
                )
            }*/
            val intent = Intent().apply {
                action = Intent.ACTION_DIAL
                data = Uri.parse("tel:"+phoneNo)
            }
            startActivity(intent)
        }
        img_arrowback.setOnClickListener {  onBackPressed()}
    }








    private fun openWhatsApp(smsNumber:String) {

        val isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp")
        if (isWhatsappInstalled) {

            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
            sendIntent.putExtra(
                "jid",
                PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net"
            )//phone number without "+" prefix

            startActivity(sendIntent)
        } else {
            val uri = Uri.parse("market://details?id=com.whatsapp")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            Toast.makeText(
                this, "WhatsApp not Installed",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(goToMarket)
        }
    }

    private fun whatsappInstalledOrNot(uri: String): Boolean {
        val pm = packageManager
        var app_installed = false
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            app_installed = true
        } catch (e: PackageManager.NameNotFoundException) {
            app_installed = false
        }

        return app_installed
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}