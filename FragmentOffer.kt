package velociter.kumar.property

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_offer.*
import kotlinx.android.synthetic.main.fragment_offer.view.*
import velociter.kumar.property.data.Offer
import org.json.JSONArray
import org.json.JSONObject

class FragmentOffer :Fragment() {
     var orgId="1"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       // var view=inflater!!.inflate(R.layout.fragment_offer, container, false)

        var rootView = inflater?.inflate(R.layout.fragment_offer, container, false)


        getOffer("1")

        rootView.offerKumar.setOnClickListener {
            getOffer("1")
            rootView.offerKumar.setBackgroundResource(R.drawable.org_selected)
            rootView.offerMega.setBackgroundResource(R.drawable.org_bg)
        }
        rootView.offerMega.setOnClickListener {
            getOffer("2")
            rootView.offerKumar.setBackgroundResource(R.drawable.org_bg)
            rootView.offerMega.setBackgroundResource(R.drawable.org_selected)
        }


        return rootView

    }

    fun getOffer(id :String) {
        //creating volley string request
        val listOfOffer: ArrayList<Offer> = ArrayList()
        var url = "http://app.kumarworld.com/api/all_offers"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url,
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
                            imgComingSoon.visibility=View.GONE
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

                    view!!.recyclerOffer!!.layoutManager = LinearLayoutManager(context)
                    var adapterOffer = AdapterOffer(listOfOffer, context)
                    view!!.recyclerOffer.adapter = adapterOffer

                }
                if(status==404){
                    AlertDialog.Builder(context).apply {
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




}