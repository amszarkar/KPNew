package velociter.kumar.property

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.sun.jna.IntegerType

import kotlinx.android.synthetic.main.activity_project_landing.*
import kotlinx.android.synthetic.main.demo_layout.*
import velociter.kumar.property.data.PriceArea
import velociter.kumar.property.data.ProjectDetails
import org.json.JSONArray
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class ProjectLandingActivity : BaseActivity() {

    var menu:Menu?=null
    val mProjectData: ArrayList<ProjectDetails> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_layout)


        val animation = AnimationUtils.loadAnimation(this, R.anim.left_to_right)
        projectLandingMain.startAnimation(animation)
        val project_id=intent.getStringExtra("project_id")
        progressBar.visibility=View.VISIBLE
        project_id?.let { getProjectDetails(it) }









/*

        liveview.setOnClickListener {
            //val mapi = SearchMap()
            supportFragmentManager.beginTransaction()
                .replace(R.id.projectLandingMain, SearchMap(),  SearchMap().javaClass.simpleName)
                .addToBackStack(null)
                .commit()
        }
*/




    }

     fun buDetails(view:View){

         val buSelected= view
         val intent = Intent(this,ActivityDemo::class.java) //not application context

         if (mProjectData.size==1){

    when (buSelected.id) {




        R.id.about -> {

            intent.putExtra("input", "about project")
            intent.putExtra("aboutData", mProjectData.get(0).mProDesc)
            startActivity(intent)
            /*val aboutFragment=FragmentAboutProject()
                var bundle = Bundle()
                bundle.putString("about",mProjectData.get(0).mProDesc)
                aboutFragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                .replace(R.id.projectLandingMain, aboutFragment,  aboutFragment.javaClass.simpleName)
                .addToBackStack(null)
                .commit()*/


        }


        R.id.amenities -> {
            intent.putExtra("input", "amenities")
            intent.putExtra("amenities1", mProjectData.get(0).getmCommAmi())
            intent.putExtra("amenities2", mProjectData.get(0).getmSpeAmi())
            startActivity(intent)
            /*  amenities.isClickable=true
                supportFragmentManager.beginTransaction()
                    .add(R.id.projectLandingMain, FragmentAminites(),  FragmentAminites().javaClass.simpleName)
                    .addToBackStack(null)
                    .commit()*/
        }


        R.id.gallery -> {
            if (mProjectData.get(0).mCostSheet.size == 1) {
                openNewTabWindow(
                    "http://docs.google.com/gview?embedded=true&url=" + mProjectData.get(0).mCostSheet.get(
                        0
                    )
                )
            } else {
                var intentFloorPlan = Intent(this, ActivityFloorPlan::class.java)
                intentFloorPlan.putExtra("floorplan", mProjectData.get(0).mCostSheet)
                intentFloorPlan.putExtra("tital", "Cost Sheet")
                startActivity(intentFloorPlan)
            }


            /* supportFragmentManager.beginTransaction()
                    .add(R.id.projectLandingMain, SearchMap(),  SearchMap().javaClass.simpleName)
                    .addToBackStack(null)
                    .commit()*/
        }

        R.id.floorplan -> {
            if (mProjectData.get(0).mFloorPlan.size == 1) {
                openNewTabWindow(
                    "http://docs.google.com/gview?embedded=true&url=" + mProjectData.get(0).mFloorPlan.get(
                        0
                    )
                )
            } else {
                var intentFloorPlan = Intent(this, ActivityFloorPlan::class.java)
                intentFloorPlan.putExtra("floorplan", mProjectData.get(0).mFloorPlan)
                intentFloorPlan.putExtra("tital", "Floor Plan")
                startActivity(intentFloorPlan)
            }

            /*supportFragmentManager.beginTransaction()
                    .replace(R.id.projectLandingMain, SearchMap(),  SearchMap().javaClass.simpleName)
                    .addToBackStack(null)
                    .commit()*/
        }

        R.id.locationmap -> {

            var uri = String.format(
                Locale.ENGLISH,
                "http://maps.google.com/maps?daddr=%f,%f (%s)",
                mProjectData.get(0).mProLat.toDouble(),
                mProjectData.get(0).mProLong.toDouble(),
                mProjectData.get(0).mProName
            );
            var location = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            location.setPackage("com.google.android.apps.maps")
            try {
                startActivity(location)
            } catch (ex: ActivityNotFoundException) {
                try {
                    var unrestrictedIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    startActivity(unrestrictedIntent)
                } catch (innerEx: ActivityNotFoundException) {
                    Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show()
                }
            }
        }

        R.id.liveview -> {
            if (mProjectData.get(0).mCamera_feed.equals("")) {
                someDialog("Live View Not Available")
            } else {
                var ip: String = ""
                var pass: String = ""
                var user: String = ""
                try {
                    var cam = mProjectData.get(0).mCamera_feed
                    var arr = cam.split("=")
                    ip = arr[0]
                    user = arr[1]
                    pass = arr[2]

                    var live = Intent(this, PlayerActivity::class.java)
                    if (!pass.isNullOrBlank() && !ip.isNullOrBlank() && !user.isNullOrBlank()) {
                        live.putExtra("ip", ip)
                        live.putExtra("user", user)
                        live.putExtra("pass", pass)
                        startActivity(live)
                    } else {
                        someDialog("Live View Not Available")
                    }

                } catch (e: IndexOutOfBoundsException) {
                    val liveFragment = FragmentLiveView()
                    var bundle = Bundle()
                    bundle.putString("liveview", mProjectData.get(0).mCamera_feed)
                    liveFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.projectLandingMain, liveFragment, liveFragment.javaClass.simpleName)
                        .addToBackStack(null)
                        .commit()
                }


                /*  val liveFragment=FragmentLiveView()
                    var bundle = Bundle()
                    bundle.putString("liveview",mProjectData.get(0).mCamera_feed)
                    liveFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.projectLandingMain, liveFragment,  liveFragment.javaClass.simpleName)
                        .addToBackStack(null)
                        .commit()*/
            }

        }

        R.id.enquiry -> {
            var enquiryintent = Intent(this, ActivityEnquiry::class.java)
            enquiryintent.putExtra("proID", mProjectData.get(0).mProID)
            startActivity(enquiryintent)
        }

        R.id.website -> {
            openNewTabWindow(mProjectData.get(0).getmWebsite())
        }

        R.id.contactus -> {
            var contactintent = Intent(this, ActivityContactUs::class.java)
            contactintent.putExtra("whatsapp", mProjectData.get(0).mWhatsapp)
            contactintent.putExtra("cellno", mProjectData.get(0).mPhone)
            startActivity(contactintent)
        }


    }
    }


    }



     fun getProjectDetails(proId:String){
       // http://app.kumarworld.com/api/project_details (parametrs -> project_id)


        val queue=Volley.newRequestQueue(this)

        val req = @SuppressLint("LongLogTag")
        object : StringRequest(Request.Method.POST,
            "http://app.kumarworld.com/api/project_details",
            Response.Listener { response ->
                rootLayouts.isClickable=true
                progressBar.visibility=View.GONE
                var strResp = response.toString()
                val jsonObj: JSONObject = JSONObject(strResp)
                val jsonArray: JSONArray = jsonObj.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)

                     var mProID      = jsonInner.get("project_id") as String
                     var mProName   = jsonInner.get("project_name") as String
                     var mAddress   = jsonInner.get("address") as String
                     var mPinCode   = jsonInner.get("pin_code") as String
                     var mCat       = jsonInner.get("category") as String
                     var mProType    = jsonInner.get("property_type") as String
                     var mProStatus  = jsonInner.get("project_status") as String
                     var mProImage   = jsonInner.get("landing_image") as String
                     var mProLat   = jsonInner.get("lattitude") as String
                     var mProLong  = jsonInner.get("longitude") as String
                     var mYoutube  = jsonInner.get("youtube") as String
                     var mWebsite  = jsonInner.get("website") as String
                     var mReraNum  = jsonInner.get("rera_number") as String
                     var mRubeId  = jsonInner.get("rqube_id") as String
                     var mSpeAmi  = jsonInner.get("specific_amenities") as String
                     var mCommAmi  = jsonInner.get("common_amenities") as String
                     var mProDesc  = jsonInner.get("project_description") as String
                     var mFb        = jsonInner.get("facebook") as String
                     var mOrgName  = jsonInner.get("org_name") as String

                     var mTwitter  = jsonInner.get("twitter") as String
                     var mInsta   = jsonInner.get("instagram") as String
                     var mSms      = jsonInner.get("sms_contact") as String
                     var mLinkedIn =jsonInner.get("linkedin") as String
                     var mWhatsapp  = jsonInner.get("whatsapp_contact") as String
                     var mProLocation = jsonInner.get("location") as String
                     var mCityName   = jsonInner.get("city_name") as String
                     var mAiBot     = jsonInner.get("ai_bot") as String
                     var mCamera   =jsonInner.get("camera_feed")as String
                     var mPhone    =jsonInner.get("toll_free_no") as String




                    cat_titdesc.text=mCat






                    val jsonArrayGallery: JSONArray = jsonInner.getJSONArray("gallary_image")
                    val mGallaryImg: ArrayList<String> = ArrayList()
                    for (i in 0 until jsonArrayGallery.length()){
                        var img=jsonArrayGallery.get(i) as String
                        mGallaryImg.add(img)
                    }


                    val jsonArrayRera: JSONArray = jsonInner.getJSONArray("rera_certificate")
                    val mReraCerti: ArrayList<String> = ArrayList()
                    for (j in 0 until jsonArrayRera.length()){
                        var img=jsonArrayRera.get(j) as String
                        mReraCerti.add(img)
                    }




                    val jsonArrayFlorePlane: JSONArray = jsonInner.getJSONArray("floor_plan")
                    val mFloorPlan: ArrayList<String> = ArrayList()
                    for (k in 0 until jsonArrayFlorePlane.length()){
                        var img=jsonArrayFlorePlane.get(k) as String
                        mFloorPlan.add(img)
                    }




                    val jsonArrayCost: JSONArray = jsonInner.getJSONArray("cost_sheet")
                    val mCostSheet: ArrayList<String> = ArrayList()
                    for (l in 0 until jsonArrayCost.length()){
                        var img=jsonArrayCost.get(l) as String
                        mCostSheet.add(img)
                    }



                    val jsonArrayArea: JSONArray = jsonInner.getJSONArray("get_price_area")
                    val mPriceArea: ArrayList<PriceArea> = ArrayList()
                    for (m in 0 until jsonArrayArea.length()){
                        var jsonAreaPrice: JSONObject = jsonArrayArea.getJSONObject(m)

                        var mPriceFrom=jsonAreaPrice.get("price_from")as String
                        var mAreaFrom=jsonAreaPrice.get("area_from")as String
                        var mAreaTo=jsonAreaPrice.get("area_to")as String
                        var mPriceSuffix=jsonAreaPrice.get("suffix_string")as String
                        var mFlatType=jsonAreaPrice.get("flat_type")as String


                        Log.e("mmmmmmmmmmmmmmmmmmmmmmm",""+mFlatType)

                        val priceArea=PriceArea(mPriceFrom,mAreaFrom,mAreaTo,mPriceSuffix,mFlatType)

                        mPriceArea.add(priceArea)
                    }



                    val projectDetails=ProjectDetails(
                            mProID,mProName,mAddress,mPinCode,mCat , mProType , mProStatus  ,mProImage , mProLat   ,mProLong , mYoutube
                          , mWebsite , mReraNum  , mRubeId  , mSpeAmi , mCommAmi , mProDesc, mFb   , mOrgName   , mTwitter  , mInsta   , mSms,mLinkedIn
                          , mWhatsapp, mProLocation, mCityName, mAiBot  ,mCamera,mPhone,mGallaryImg,mReraCerti,mFloorPlan,mCostSheet,mPriceArea )
                    mProjectData.add(projectDetails)

                }

                Picasso.get().load(mProjectData.get(0).mProImage). placeholder( R.drawable.progress_animation ).into(expandedImage)
                val mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
                mToolbar.title=mProjectData.get(0).mProName
                setSupportActionBar(mToolbar)
                txtReraNo.text=mProjectData.get(0).mReraNum
                pro_name.text=mProjectData.get(0).mProName
                pro_loc.text=mProjectData.get(0).mProLocation+","+mProjectData.get(0).mCityName
                if (mProjectData.get(0).mOrgName.equals("KUMAR PROPERTIES")){

                    val imgResId = R.drawable.kp_new
                    icon1.setImageResource(imgResId)

                }else if (mProjectData.get(0).mOrgName.equals("MEGAPOLIS")){
                    val imgResId = R.drawable.megapolis_new
                    icon1.setImageResource(imgResId)
                }

                if (mProjectData.get(0).mPriceArea.size==0 ){
                    linearFlatType.visibility=View.GONE
                    linearArea.visibility=View.GONE
                    linearPrice.visibility=View.GONE
                    cardContactUs.visibility=View.VISIBLE
                }
                else{
                    //Flat type
                    val bhk: ArrayList<String> = ArrayList()
                    var x:String=""
                    for (i in 0 until mProjectData.get(0).mPriceArea.size){
                        bhk.add(mProjectData.get(0).mPriceArea.get(i).mFlatType)
                     //   Log.e("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,""+)
                        //x=x+","+mProjectData.get(0).mPriceArea.get(i).mFlatType
                    }
                   // bhk.sort()
                    val set = HashSet<String>(bhk)
                    bhk.clear()
                    bhk.addAll(set)
                    bhk.sort()
                    Log.e("aaaaaaaaaaaaaaaaaaaaaaaaa",""+bhk.toString())
                    for (i in 0 until bhk.size){
                        x=x+bhk.get(i)+","
                    }

                    val zz=removeLastChar(x)
                    txtFlatType.text=zz

                    //Area

                    var y:String=""
                    var z:String=""
                    val area: ArrayList<String> = ArrayList()
                    for (i in 0 until mProjectData.get(0).mPriceArea.size){
                        area.add(mProjectData.get(0).mPriceArea.get(i).mFlatType+" - "+mProjectData.get(0).mPriceArea.get(i).mAreaFrom+"  to "+mProjectData.get(0).mPriceArea.get(i).mAreaTo+"  "+"\n")
                        //y=y+mProjectData.get(0).mPriceArea.get(i).mFlatType+" - "+mProjectData.get(0).mPriceArea.get(i).mAreaFrom+"  to "+mProjectData.get(0).mPriceArea.get(i).mAreaTo+"  "+"\n"
                        var p = mProjectData.get(0).mPriceArea.get(i).mPriceFrom
                        var n=p.toInt()

                        if ( mProjectData.get(0).mPriceArea.get(i).mPriceFrom.equals("00")){
                            linearPrice.visibility=View.GONE
                            cardContactUs.visibility=View.VISIBLE

                        }else{
                            if (n < 10000000) {
                                val y = n / 1000 * 0.01
                                val decimal = BigDecimal(y).setScale(2, RoundingMode.HALF_EVEN)
                                try{
                                    z=z+mProjectData.get(0).mPriceArea.get(i).mFlatType+" - "+decimal+" Lac(s) " +mProjectData.get(0).mPriceArea.get(i).mPriceSuffix+"\n"

                                }catch (e : StringIndexOutOfBoundsException){
                                    z=z+mProjectData.get(0).mPriceArea.get(i).mFlatType+" - "+decimal+" Lac(s) " +mProjectData.get(0).mPriceArea.get(i).mPriceSuffix+"\n"

                                }

                            } else {
                                val y = n / 100000 * 0.01
                                val decimal = BigDecimal(y).setScale(2, RoundingMode.HALF_EVEN)
                                try {
                                    z=z+mProjectData.get(0).mPriceArea.get(i).mFlatType+" - "+decimal+" Cr " +mProjectData.get(0).mPriceArea.get(i).mPriceSuffix+"\n"

                                }catch (e :StringIndexOutOfBoundsException){
                                    z=z+mProjectData.get(0).mPriceArea.get(i).mFlatType+" - "+decimal+" Cr " +mProjectData.get(0).mPriceArea.get(i).mPriceSuffix+"\n"

                                }

                            }
                            txtPriceFrom.text=z
                        }

                    }
                    val set2=HashSet<String>(area)
                    area.clear()
                    area.addAll(set2)
                    area.sort()
                    for (i in 0 until area.size){
                        y=y+area[i]
                    }
                    txtPrice.text=y


                }



                val fab = findViewById<View>(R.id.fab) as FloatingActionButton
                fab.setOnClickListener { _->
                    /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()*/
                    if (mProjectData.get(0).getmAiBot().equals("")){
                        var intent=Intent(this,AI_Bot::class.java)
                        intent.putExtra("aibot","<script src=\"https://www.kenyt.ai/botapp/ChatbotUI/dist/js/bot-loader.js\" type=\"text/javascript\" data-bot=\"Kumar_Properties\"></script>")
                        startActivity(intent)
                    }else{
                        var intent=Intent(this,AI_Bot::class.java)
                        intent.putExtra("aibot",mProjectData.get(0).getmAiBot())
                        startActivity(intent)
                    }

                }

                val mAppBarLayout = findViewById<View>(R.id.app_bar) as AppBarLayout
                mAppBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
                    internal var isShow = false
                    internal var scrollRange = -1

                    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.totalScrollRange
                        }
                        if (scrollRange + verticalOffset == 0) {
                            isShow = true

                        } else if (isShow) {
                            isShow = false

                        }
                    }
                })

                getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
                getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
                mToolbar.setNavigationOnClickListener(){onBackPressed()}


                var recyclerView:RecyclerView=recyclergallary
                recyclerView.layoutManager=LinearLayoutManager(this,LinearLayout.HORIZONTAL,false)
                var adpter=AdapterGallery(this,mProjectData.get(0).mGallaryImg)
                recyclerView.adapter=adpter





                //Social on Click

                instagram.setOnClickListener { val uri = Uri.parse(mProjectData.get(0).mInsta)
                    val likeIng = Intent(Intent.ACTION_VIEW, uri)

                    likeIng.setPackage("com.instagram.android")

                    try {
                        startActivity(likeIng)
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(mProjectData.get(0).mInsta)
                            )
                        )
                    } }

                fb.setOnClickListener {
                    val uri = Uri.parse(mProjectData.get(0).mFb)
                    val likeIng = Intent(Intent.ACTION_VIEW, uri)

                    likeIng.setPackage("com.facebook.android")

                    try {
                        startActivity(likeIng)
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(mProjectData.get(0).mFb)
                            )
                        )
                    }
                }

                tw.setOnClickListener {

                    val webIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(mProjectData.get(0).getmTwitter())

                    )
                    startActivity(webIntent)

                }

                youtube.setOnClickListener {
                    watchYoutubeVideo(mProjectData.get(0).getmYoutube())
                }

                linkedin.setOnClickListener {  openNewTabWindow(mProjectData.get(0).getmLinkedIn()) }


                call.setOnClickListener {

                    val intent = Intent().apply {
                        action = Intent.ACTION_DIAL
                        data = Uri.parse("tel:"+ mProjectData.get(0).mPhone)
                    }
                    startActivity(intent)



                }



                linearRera.setOnClickListener {
                    if (mProjectData.get(0).mReraCerti.size==1){
                        openNewTabWindow("http://docs.google.com/gview?embedded=true&url=" + mProjectData.get(0).mReraCerti.get(0))
                    }
                    else{
                        var intentFloorPlan= Intent(this,ActivityFloorPlan::class.java)
                        intentFloorPlan.putExtra("floorplan",mProjectData.get(0).getmReraCerti())
                        intentFloorPlan.putExtra("tital","Rera certificate")
                        startActivity(intentFloorPlan)
                    }

                }







                }, Response.ErrorListener { e ->
                progressBar.visibility=View.GONE
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }) {
            public override fun getParams(): Map<String, String> {

                val params = HashMap<String, String>()
                progressBar.visibility=View.VISIBLE
                params.put("project_id", proId)
                Log.e("aaaaaaaaaaaaaaaaaaaaaaa",""+proId)
                return params
            }

            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded"
            }
        }
        req.retryPolicy = DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        queue!!.add(req)




    }



    fun openNewTabWindow(urls: String) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        startActivity(intents)
    }

    fun removeLastChar(s: String?): String? {
        return if (s == null || s.length == 0)
            null
        else
            s.substring(0, s.length - 1)
    }




    private fun hideOption(id: Int) {
        val item = menu!!.findItem(id)
        item.isVisible = false
    }

    private fun showOption(id: Int) {
        val item = menu!!.findItem(id)
        item.isVisible = false
    }





    fun watchYoutubeVideo(id: String) {

       // val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://user/channel$separate1"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(id)
                  //  startActivity(webIntent)
        )
        startActivity(webIntent)


    }




    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MAKE_CALL_PERMISSION_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    companion object {
        private val MAKE_CALL_PERMISSION_REQUEST_CODE = 1
    }


    fun someDialog(message:String){
        AlertDialog.Builder(this).apply {
            setTitle("Kumar Properties")
            setMessage(message)
            setNegativeButton("Ok") { dialog, _->
                dialog.cancel()
                dialog.dismiss()
            }
            create()
            show()
        }
    }
}
