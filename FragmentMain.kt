package velociter.kumar.property

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import velociter.kumar.property.data.ProjectData
import org.json.JSONArray
import org.json.JSONObject
import velociter.kumar.property.data.Location
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FragmentMain :Fragment() {


    var recyclerView: RecyclerView?=null
    val listLocation: ArrayList<String> = ArrayList()
    val idlist:ArrayList<String> =ArrayList()
    var locationID=""
    //val list: ArrayList<ProjectData> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView= inflater?.inflate(R.layout.fragment_main, container, false)

       // getAllLocation("1","pune")
        getProject("1","")
        var pune: RelativeLayout =rootView.bucity_pune
        var mumbai:RelativeLayout=rootView.bucity_mumbai
        var bangalore:RelativeLayout=rootView.bucity_bangalore


        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        recyclerView=rootView.recyler_main
        recyclerView!!.startAnimation(animation)
        recyclerView!!.layoutManager = GridLayoutManager(context,1, LinearLayout.VERTICAL, false)



        pune.setOnClickListener{
            getAllLocation("1","pune")
            rootView.bucity_pune.setBackgroundResource(R.drawable.city_oval_selected)
            rootView.bucity_bangalore.setBackgroundResource(R.drawable.city_oval)
            rootView.bucity_mumbai.setBackgroundResource(R.drawable.city_oval)
            txtMumbai.text="Mumbai"
            txtbangalore.text="bangalore"
           // getProject("1","")



        }
        mumbai.setOnClickListener {
            getAllLocation("2","mumbai")
            rootView.bucity_pune.setBackgroundResource(R.drawable.city_oval)
            rootView.bucity_mumbai.setBackgroundResource(R.drawable.city_oval_selected)
            rootView. bucity_bangalore.setBackgroundResource(R.drawable.city_oval)
            txtbangalore.text="bangalore"
            txtPune.text="pune"


        }
        bangalore.setOnClickListener {
            getAllLocation("3","bangalore")
            rootView.bucity_pune.setBackgroundResource(R.drawable.city_oval)
            rootView.bucity_mumbai.setBackgroundResource(R.drawable.city_oval)
            rootView.bucity_bangalore.setBackgroundResource(R.drawable.city_oval_selected)
            txtMumbai.text="Mumbai"
            txtPune.text="pune"


        }





        return rootView

    }


    private fun getProject(cityID:String,locationID :String) {
        val queue= Volley.newRequestQueue(context)
        val list: ArrayList<ProjectData> = ArrayList()
        val req = object : StringRequest(Request.Method.POST,
            "http://app.kumarworld.com/api/all_projects",
            Response.Listener { response ->


                try {

                    //
                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    var status = jsonObj.getInt("status")
                    if (status == 200) {

                        val jsonArray: JSONArray = jsonObj.getJSONArray("data")

                        for (i in 0 until jsonArray.length()) {
                            var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                            var name = jsonInner.get("project_name")
                            var id = jsonInner.get("id")
                            var city_name = jsonInner.get("city_name")
                            var lattitude = jsonInner.get("lattitude")
                            var longitude = jsonInner.get("longitude")
                            var location = jsonInner.get("location")
                            var image = jsonInner.get("image")

                            val data = ProjectData(
                                name as String,
                                image as String,
                                id as String,
                                lattitude as String,
                                longitude as String,
                                location as String,
                                city_name as String
                            )

                            list.add(data)


                        }

                        var adpter = AdapterMain(context!!, list)
                        recyclerView!!.adapter = adpter


                    }
                }catch (e :KotlinNullPointerException){
                    e.printStackTrace()
                }

            }, Response.ErrorListener { e ->
                e.printStackTrace()
               // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
            }) {
            public override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("id", cityID)
                params.put("location_id",locationID)
                return params
            }

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded"
            }
        }
        req.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        queue!!.add(req)

    }




    fun someDialog(message:String){
        AlertDialog.Builder(context).apply {
            setTitle("Kumar Properties")
            setMessage(message)
            setNegativeButton("Ok") { dialog, which ->
                dialog.cancel()
                dialog.dismiss()
            }
            create()
            show()
        }
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }


    fun getAllLocation(cityIDss: String,cityName:String) {
        var url = "http://app.kumarworld.com/api/"

        val queue= Volley.newRequestQueue(context)

        val req = object : StringRequest(Request.Method.POST,
            "http://app.kumarworld.com/api/all_location",
            Response.Listener { response ->

                Log.e("Aaaaaaaaaaaaaaa",""+response)
                //
                try {

                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    var status = jsonObj.getInt("status")
                    if (status == 200) {

                        val jsonArray: JSONArray = jsonObj.getJSONArray("data")
                        listLocation.clear()
                        idlist.clear()
                        listLocation.add("All")
                        idlist.add("")
                        for (i in 0 until jsonArray.length()) {
                            var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                            var location_id = jsonInner.get("location_id")
                            var location = jsonInner.get("location")


                            // val locations=Location(location_id as String,location as String)
                            listLocation.add(location as String)
                            idlist.add(location_id as String)

                        }


                        val array = arrayOfNulls<String>(listLocation.size)
                        listLocation.toArray(array)

                        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)


                        // Set a title for alert dialog
                        builder.setTitle("Choose Location.")
                        builder.setItems(array, { _, which ->
                            val selected = listLocation[which]
                            try {
                                if (cityIDss.equals("1")) {
                                    if (selected.equals("All")) {
                                        txtPune.text = "Pune"
                                    } else txtPune.text = selected

                                } else if (cityIDss.equals("2")) {
                                    if (selected.equals("All")) {
                                        txtMumbai.text = "Mumbai"
                                    } else txtMumbai.text = selected
                                } else {
                                    if (selected.equals("All")) {
                                        txtbangalore.text = "Bengaluru"
                                    } else txtbangalore.text = selected
                                }

                                locationID = idlist[which] as String
                                getProject(cityIDss, locationID)
                            } catch (e: IllegalArgumentException) {

                            }
                        })

                        val dialog = builder.create()
                        dialog.show()


                    }
                }catch (e : NullPointerException){
                    e.printStackTrace()
                }

            }, Response.ErrorListener { e ->

               // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
            }) {
            public override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                    params.put("city_id", cityIDss)

                return params
            }

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded"
            }
        }
        req.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        queue!!.add(req)

    }
}