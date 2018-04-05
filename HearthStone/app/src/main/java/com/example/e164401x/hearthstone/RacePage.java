package com.example.e164401x.hearthstone;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RacePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_page);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            return;
        }

        String classe = extras.getString("classe");
        String type = extras.getString("type");
        String faction = extras.getString("faction");
        String race = extras.getString("race");
        String uri = "";

        final ListView l = (ListView) findViewById(R.id.list);
        final ArrayList<JSONObject> liste = new ArrayList<JSONObject>();
//        final ArrayList<String> liste = new ArrayList<String>();

        final Activity my = this;

        if (classe != null){
            uri = "classes/"+classe;
        }
        else if (type != null){
            uri = "types/"+type;
        }
        else if(faction !=null){
            uri = "factions/"+faction;
        }
        else if (race != null){
            uri = "races/"+race;
        }
        else{
            System.out.println("Aucun spinner sélectionné");
        }

        final RequestQueue r = Volley.newRequestQueue(getApplicationContext());
        StringRequest s = new StringRequest(
                Request.Method.GET,
                "https://omgvamp-hearthstone-v1.p.mashape.com/cards/"+uri,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try{
                            JSONArray json = new JSONArray(response);
                            System.out.println(json);
                            for(int i=0; i<json.length();i++){
//                                liste.add(json.getJSONObject(i).get("name").toString());
                                liste.add(json.getJSONObject(i));
                            }
//                            ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, liste);
//                            l.setAdapter(aa);

                            l.setAdapter(new MonAdapteur(my,liste));
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }},
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        System.out.println("non");

                    }})
        {
            public Map<String,String> getHeaders(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-Mashape-Key", "kBfb5u2MKgmshg7opNAJHdhzn297p1Fa7egjsnw03a7NSRflzw");
                return params;
            }
        };

        r.add(s);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long lg) {

                Gson gson = new Gson();
                String json = gson.toJson(adapterView.getItemAtPosition(i));
                try{
                    JSONObject j = new JSONObject(json);
                    StringRequest s2 = new StringRequest(
                            Request.Method.GET,
                            "https://omgvamp-hearthstone-v1.p.mashape.com/cards/"+j.getJSONObject("nameValuePairs").get("cardId").toString(),
                            new Response.Listener<String>() {
                                public void onResponse(String response) {
                                    try{
                                        JSONArray json = new JSONArray(response);
//                                    System.out.println("test");
//                                    System.out.println(json);
                                        Intent i = new Intent(RacePage.this,SoloCard.class);
                                        i.putExtra("json",json.toString());
                                        i.putExtra("name",json.getJSONObject(0).get("name").toString());
                                        try {
                                            i.putExtra("img", json.getJSONObject(0).get("img").toString());
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                        try {
                                            i.putExtra("imgGold", json.getJSONObject(0).get("imgGold").toString());
                                        }catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                        i.putExtra("type",json.getJSONObject(0).get("type").toString());
                                        i.putExtra("classe",json.getJSONObject(0).get("playerClass").toString());
                                        try{
                                            i.putExtra("rarity",json.getJSONObject(0).get("rarity").toString());
                                        }catch(JSONException e){
                                            e.printStackTrace();
                                        }
                                        startActivity(i);

                                    }catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }},
                            new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println(error);
                                    System.out.println("non");

                                }})
                    {
                        public Map<String,String> getHeaders(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("X-Mashape-Key", "kBfb5u2MKgmshg7opNAJHdhzn297p1Fa7egjsnw03a7NSRflzw");
                            return params;
                        }
                    };
                    r.add(s2);
                }catch(JSONException e ){
                    e.printStackTrace();
                }

            }
        });
    }
}

class MonAdapteur extends ArrayAdapter{

    public MonAdapteur(Activity context, ArrayList<JSONObject> cartes){
        super(context, R.layout.activity_race_page,cartes);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View newView;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        newView = inflater.inflate(R.layout.listelayout,null);

        Object carte = getItem(position);
        Gson gson = new Gson();
        String json = gson.toJson(carte);
        try{
            final JSONObject j = new JSONObject(json);
            TextView name = (TextView) newView.findViewById(R.id.name);
            name.setText(j.getJSONObject("nameValuePairs").get("name").toString());
            TextView rarity = (TextView) newView.findViewById(R.id.type);
            System.out.println("test");
            System.out.println(carte);
            rarity.setText(j.getJSONObject("nameValuePairs").get("type").toString());
            ImageView arrow = (ImageView) newView.findViewById(R.id.arrow);
            arrow.setBackgroundResource(R.mipmap.arrow);


            final ImageView image = (ImageView) newView.findViewById(R.id.image);
            image.setBackgroundResource(R.mipmap.image);

            RequestQueue r = Volley.newRequestQueue(getContext());
            StringRequest s = new StringRequest(
                    Request.Method.GET,
                    "http://square.github.io/picasso/",
                    new Response.Listener<String>() {
                        public void onResponse(String response) {
                            com.squareup.picasso.Picasso.Builder p = new com.squareup.picasso.Picasso.Builder(getContext());
                            com.squareup.picasso.Picasso pic = p.build();
                            try{
                                pic.load(android.net.Uri.parse(j.getJSONObject("nameValuePairs").get("img").toString())).into(image);

                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                            System.out.println("fini");
                        }
                    },
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.getMessage());
                            System.out.println("problème");

                        }
                    }
            );
            r.add(s);



        }catch(JSONException e){
            e.printStackTrace();
        }


        return (newView);
    }
}
