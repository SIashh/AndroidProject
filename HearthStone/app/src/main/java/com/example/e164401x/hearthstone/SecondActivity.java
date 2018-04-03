package com.example.e164401x.hearthstone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

//    kBfb5u2MKgmshg7opNAJHdhzn297p1Fa7egjsnw03a7NSRflzw

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scd);

        TextView titre = (TextView) findViewById(R.id.textViewTitre);
        TextView labelClasse = (TextView) findViewById(R.id.textViewClasse);
        TextView labelType = (TextView) findViewById(R.id.textViewType);
        TextView labelFaction = (TextView) findViewById(R.id.textViewFaction);
        TextView labelRace = (TextView) findViewById(R.id.textViewRace);



        final Spinner classe = (Spinner) findViewById(R.id.spinnerClasse);
        final Spinner type = (Spinner) findViewById(R.id.spinnerType);
        final Spinner faction = (Spinner) findViewById(R.id.spinnerFaction);
        final Spinner race = (Spinner) findViewById(R.id.spinnerRace);


        classe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                if (!s.equals(" ")) {
                    type.setSelection(0, true);
                    faction.setSelection(0, true);
                    race.setSelection(0, true);
                    Intent intent = new Intent(SecondActivity.this,RacePage.class);
                    intent.putExtra("classe", s);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                if (!s.equals(" ")) {
                    classe.setSelection(0, true);
                    faction.setSelection(0, true);
                    race.setSelection(0, true);
                    Intent intent = new Intent(SecondActivity.this,RacePage.class);
                    intent.putExtra("type", s);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        faction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                if (!s.equals(" ")) {
                    type.setSelection(0, true);
                    classe.setSelection(0, true);
                    race.setSelection(0, true);
                    Intent intent = new Intent(SecondActivity.this,RacePage.class);
                    intent.putExtra("faction", s);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        race.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                if (!s.equals(" ")) {
                    type.setSelection(0, true);
                    faction.setSelection(0, true);
                    classe.setSelection(0, true);
                    Intent intent = new Intent(SecondActivity.this,RacePage.class);
                    intent.putExtra("race", s);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.GET,
                        "https://omgvamp-hearthstone-v1.p.mashape.com/info",
//                        "http://site.org/form.php",
                        new Response.Listener<String>() {
                            public void onResponse(String response) {
                                try {
                                    JSONObject repObj = (JSONObject) new JSONTokener(response).nextValue();
                                    setSpinnerArray(repObj, classe, "classes");
                                    setSpinnerArray(repObj, type, "types");
                                    setSpinnerArray(repObj, faction, "factions");
                                    setSpinnerArray(repObj, race, "races");
                                } catch (JSONException je) {
                                    System.out.println(je.getMessage());
                                }
                            }},
                        new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("error");
                                System.out.println(error.getMessage());
                            }})
                {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("X-Mashape-Key", "kBfb5u2MKgmshg7opNAJHdhzn297p1Fa7egjsnw03a7NSRflzw");
                        return params;
                    }
                };
        queue.add(stringRequest);
        Toast t3 = Toast.makeText(getApplicationContext(), "yo2", Toast.LENGTH_SHORT);
        t3.show();
    }

    public void setSpinnerArray(JSONObject repObj, Spinner s, String index) {
        try {
            JSONArray arrayClasses = repObj.getJSONArray(index);
            List<String> classesList = new ArrayList<String>();
            classesList.add(" ");
            for(int i = 0; i<arrayClasses.length(); i++) {
                classesList.add(arrayClasses.get(i).toString());
            }
            ArrayAdapter<String> aa = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, classesList);
            s.setAdapter(aa);
        } catch (JSONException je) {
            System.out.println(je.getMessage());
        }

    }
}
