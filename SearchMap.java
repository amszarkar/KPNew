package velociter.kumar.property;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class SearchMap extends Fragment {
    GoogleMap map;
    Marker m1;
    ArrayList<MapLatLong> maps = new ArrayList<MapLatLong>();
    ArrayList<LatLong> listitems ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.searchmap,container,false);
        getData("1");

        final Button pune  =rootView.findViewById(R.id.bucity_pune);
        final Button mumbai=rootView.findViewById(R.id.bucity_mumbai);
        final Button bangalore=rootView.findViewById(R.id.bucity_bangalore);


        pune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pune.setBackgroundResource(R.drawable.city_oval_selected);
                bangalore.setBackgroundResource(R.drawable.city_oval);
                mumbai.setBackgroundResource(R.drawable.city_oval);
                listitems.clear();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                getData("1");
            }
        });


        mumbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pune.setBackgroundResource(R.drawable.city_oval);
               mumbai.setBackgroundResource(R.drawable.city_oval_selected);
               bangalore.setBackgroundResource(R.drawable.city_oval);
                listitems.clear();
                getData("2");
            }
        });

        bangalore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pune.setBackgroundResource(R.drawable.city_oval);
                mumbai.setBackgroundResource(R.drawable.city_oval);
                bangalore.setBackgroundResource(R.drawable.city_oval_selected);
                getData("3");
            }
        });

      /*  pune.setOnClickListener{

            // var adpter=AdapterMain(this,10)
            // recyclerView.adapter=adpter
        }
        mumbai.setOnClickListener {

            //  var adpter=AdapterMain(this,1)
            // recyclerView.adapter=adpter
        }
        bangalore.setOnClickListener {

            //  var adpter=AdapterMain(this,3)
            // recyclerView.adapter=adpter
        }*/


        return rootView;


    }

    public void getData(final String cityID){
        RequestQueue mRequestQueue= Volley.newRequestQueue(getContext());
       listitems = new ArrayList<LatLong>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://app.kumarworld.com/api/all_projects", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    //  String msg=jsonObject.getString("message");


                    if (status==200){
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObjectInner=jsonArray.getJSONObject(i);
                            String longitude=jsonObjectInner.getString("longitude");

                            String lattitude=jsonObjectInner.getString("lattitude");
                            String project_name=jsonObjectInner.getString("project_name");
                            String id=jsonObjectInner.getString("id");
                            LatLong latLong=new LatLong(lattitude,longitude,project_name,id);
                            listitems.add(latLong);
                        }


                        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                map = googleMap;
                                CameraUpdate center1= CameraUpdateFactory.newLatLng(new LatLng(18.5195700, 73.8553500));
                                CameraUpdate zoom1=CameraUpdateFactory.zoomTo(8f);
                                map.moveCamera(center1);
                                map.animateCamera(zoom1);

                                if(listitems.size()==0) {


                   /* GetProjects task = new GetProjects();
                    task.execute();*/
                                }else {
                                    for(int i1 = 0;i1<listitems.size();i1++) {
                                        try {

                                            double lat1 = Double.parseDouble(listitems.get(i1).lat);
                                            double longit = Double.parseDouble(listitems.get(i1).longs);
                                            String namee = listitems.get(i1).name;
                                            int posi = i1;
                                            MapLatLong val = new MapLatLong(lat1, longit, namee, posi);
                                            maps.add(val);
                                            MarkerOptions opt = new MarkerOptions();
                                            opt.position(new LatLng(lat1, longit))
                                                    .title(namee).isVisible();
                                            Marker m1 = map.addMarker(opt);


                                        } catch (Exception e) {

                                        }
                                    }
                                }

                                map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                    @Override
                                    public void onInfoWindowClick(Marker marker) {
                                        String text = marker.getTitle();
                                        for(int jj =0;jj<listitems.size();jj++){

                                           /* Intent intent = new Intent(getActivity(), ProjectLandingActivity.class);
                                            intent.putExtra("project_id", listitems.get(jj).id);
                                            startActivity(intent);*/

                                        }
                                    }
                                });
                            }
                        });


                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }



            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new Hashtable<String, String>();
                params.put("id", cityID);

                //returning parameters
                return params;
            }
        };

        mRequestQueue.add(stringRequest);


    }









}