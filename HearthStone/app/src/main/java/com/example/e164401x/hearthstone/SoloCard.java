package com.example.e164401x.hearthstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SoloCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_card);

        Bundle b = getIntent().getExtras();
        if (b == null){
            return;
        }

        final String img = b.getString("img");

        RequestQueue r = Volley.newRequestQueue(getApplicationContext());
        System.out.println("dehors");

        if (img != "") {
            System.out.println("dedans");
            StringRequest s = new StringRequest(
                    Request.Method.GET,
                    "http://square.github.io/picasso/",
                    new Response.Listener<String>() {
                        public void onResponse(String response) {
                            ImageView i = (ImageView) findViewById(R.id.imageView);
                            com.squareup.picasso.Picasso.Builder p = new com.squareup.picasso.Picasso.Builder(getApplicationContext());
                            com.squareup.picasso.Picasso pic = p.build();
                            pic.load(android.net.Uri.parse(img)).into(i);
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
        }

    }
}
