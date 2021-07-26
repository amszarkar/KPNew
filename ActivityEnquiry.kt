package velociter.kumar.property

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_enqiry.*
import velociter.kumar.property.data.ProjectData
import org.json.JSONObject

class ActivityEnquiry :AppCompatActivity() {
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_enqiry)

        progressBar = findViewById(R.id.progressBar)
        progressBar.max = 100

        var proID=intent.getStringExtra("proID")
        btnSubmit.setOnClickListener {
            if (isValidates()){

                progressBar.visibility = View.VISIBLE

               postEnquiry(proID!!)
            }
        }
        img_arrowback.setOnClickListener { onBackPressed() }
    }

    fun postEnquiry(project_id:String) {
       //   (parameters -> name,,mobile_no,,)
        val queue=Volley.newRequestQueue(this)
        val list: ArrayList<ProjectData> = ArrayList()
        val req = object : StringRequest(Request.Method.POST,
            "http://app.kumarworld.com/api/enquiry",
            Response.Listener { response ->
                Log.e("wqw",""+response)

                //
                var strResp = response.toString()
                val jsonObj: JSONObject = JSONObject(strResp)
                var status=jsonObj.getInt("status")
                if (status==200) {

                    progressBar.visibility = View.GONE
                    AlertDialog.Builder(this).apply {
                        setTitle("Kumar Properties")
                        setMessage("Enquiry Submitted  ")
                        setNegativeButton("Ok"){ dialog, which ->
                            onBackPressed()
                            dialog.cancel()
                            dialog.dismiss()
                        }
                        create()
                        show()
                    }
                }

            }, Response.ErrorListener { e ->

                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }) {
            public override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("name", txtName.text.toString())
                params.put("email", txtEmail.text.toString())
                params.put("message", txtmsg.text.toString())
                params.put("mobile_no",txtPhoneNo.text.toString())
                params.put("project_id", project_id)
                return params
            }

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded";
            }
        }
        req.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        queue!!.add(req)
    }



    fun isValidates():Boolean{
        if(txtName.text.trim().length==0) {
            someDialog("Please Enter Name! ")
        }
        else if (!txtEmail.text.equals("") && !(Patterns.EMAIL_ADDRESS.matcher(txtEmail.text.toString()).matches())){
            someDialog("Please Enter Valid Email ! ")
        }

        else if(txtPhoneNo.text.trim().length==0 ) {
            someDialog("Please Enter Valid Phone Number ! ")
        }else if(txtmsg.text.trim().length==0){
            someDialog("Please Enter Masseage ! ")
        }
        else{
            return true
        }
        return false
    }


    fun someDialog(message:String){
        AlertDialog.Builder(this).apply {
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


    override fun onBackPressed() {
        super.onBackPressed()
    }
}