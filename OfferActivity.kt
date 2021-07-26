package velociter.kumar.property

import android.app.AlertDialog

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_offer_activty.*
import kotlinx.android.synthetic.main.fragment_offer.imgComingSoon
import kotlinx.android.synthetic.main.fragment_offer.offerKumar
import kotlinx.android.synthetic.main.fragment_offer.offerMega
import org.json.JSONArray
import org.json.JSONObject
import velociter.kumar.property.data.Offer


class OfferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_activty)


        getOffer("1")

/*

        img_arrowback.setOnClickListener {
           onBackPressed()
        }
*/

        val mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)
        /*supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        mToolbar.setNavigationOnClickListener(){onBackPressed()}*/
        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        /*supportActionBar!!.title = "Kumar Properies"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)*/
       /* val actionbar = supportActionBar

        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)*/

        /* val menuFragment = intent.getStringExtra("data")

         val fragmentManager: FragmentManager = supportFragmentManager
         val fragmentTransaction: android.support.v4.app.FragmentTransaction =
             fragmentManager.beginTransaction()

         // If menuFragment is defined, then this activity was launched with a fragment selection
         // If menuFragment is defined, then this activity was launched with a fragment selection
         if (menuFragment != null) { // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
             if (menuFragment == "notificationdata") {
                 val favoritesFragment = FragmentOffer()
                 fragmentTransaction.replace(R.id.mainFrame, favoritesFragment)
             }
         }*/

      offerKumar.setOnClickListener {
        getOffer("1");
          offerKumar.setBackgroundResource(R.drawable.org_selected)
          offerMega.setBackgroundResource(R.drawable.org_bg)
      }

        offerMega.setOnClickListener {
            getOffer("2")
            offerKumar.setBackgroundResource(R.drawable.org_bg)
            offerMega.setBackgroundResource(R.drawable.org_selected)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return  true
    }
    fun getOffer(id :String) {
        //creating volley string request
        val listOfOffer: ArrayList<Offer> = ArrayList()
        var url = "http://app.kumarworld.com/api/all_offers"
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->


                var strResp = response.toString()
                val jsonObj: JSONObject = JSONObject(strResp)
                var status = jsonObj.getInt("status")

                if (status == 200) {

                    val jsonArray: JSONArray = jsonObj.getJSONArray("data")

                    for (i in 0 until jsonArray.length()) {

                        var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                        var organization_id = jsonInner.get("organization_id") as String
                        if (organization_id.equals(id)) {
                            imgComingSoon.visibility= View.GONE
                            var project_id = jsonInner.get("project_id") as String

                            var title = jsonInner.get("title") as String
                            var valid_from = jsonInner.get("valid_from") as String
                            var valid_to = jsonInner.get("valid_to") as String
                            var terms_and_condition = jsonInner.get("terms_and_condition") as String
                            var offer_image = jsonInner.get("offer_image") as String

                            val offer = Offer(
                                project_id,
                                organization_id,
                                title,
                                valid_from,
                                valid_to,
                                terms_and_condition,
                                offer_image
                            )

                            listOfOffer.add(offer)
                        }
                        else{
                            // imgComingSoon.visibility=View.VISIBLE
                        }
                    }

                    this!!.recyclerOffernew!!.layoutManager = LinearLayoutManager(this)
                    val divider = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
                    var adapterOffer = AdapterOffer(listOfOffer, applicationContext)
                    this!!.recyclerOffernew.adapter = adapterOffer
                    this!!.recyclerOffernew.addItemDecoration(divider)

                }
                if(status==404){
                    AlertDialog.Builder(applicationContext).apply {
                        setTitle("Kumar Properties")
                        setMessage("New Offer Coming Soon")
                        setNegativeButton("Ok") { dialog, which ->
                            dialog.cancel()
                            dialog.dismiss()
                            /* activity!!.supportFragmentManager.beginTransaction()
                                 .replace(R.id.mainFrame, FragmentMain(),  FragmentMain().javaClass.simpleName)
                                 .commit()*/
                        }
                        create()
                        show()
                    }
                }


            },
            Response.ErrorListener {
                getOffer(id)
                //Toast.makeText(context, "That didn't work!", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
        queue.start()
    }

    override fun onResume() {
        super.onResume()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        var intent=Intent(this,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
