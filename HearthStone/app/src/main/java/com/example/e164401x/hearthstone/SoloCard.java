package com.example.e164401x.hearthstone;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static android.R.color.white;

public class SoloCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_card);

        Bundle b = getIntent().getExtras();
        if (b == null){
            return;
        }

        final String name = b.getString("name");
        final String img = b.getString("img");
        final String uriGold = b.getString("imgGold");
        System.out.println(b);
        final String type = b.getString("type");
        final String cardSet = b.getString("cardSet");
        final String classe = b.getString("classe");
        final ImageView i = (ImageView) findViewById(R.id.imageView);
        final ImageView imgGold = (ImageView) findViewById(R.id.imageGold);


        RequestQueue r = Volley.newRequestQueue(getApplicationContext());

        if(classe !=""){
            TextView t = (TextView) findViewById(R.id.classe);
            t.setText(classe);
            t.setTextColor(getResources().getColor(R.color.mySpinnerTextColor));
            t.setTextSize(40);
            t.setGravity(Gravity.CENTER);
            System.out.println("fin texte view");
        }

        if(name !=""){
            TextView t = (TextView) findViewById(R.id.name);
            t.setText(name);
            t.setGravity(Gravity.CENTER);
            t.setTextColor(getResources().getColor(R.color.mySpinnerTextColor));
            t.setTextSize(40);
        }

        if(type !=""){
            TextView t = (TextView) findViewById(R.id.type);
            t.setText(type);
            t.setGravity(Gravity.CENTER);
            t.setTextColor(getResources().getColor(R.color.mySpinnerTextColor));
            t.setTextSize(25);
        }

        if(cardSet !=""){
            TextView t = (TextView) findViewById(R.id.cardSet);
            t.setText(cardSet);
            t.setGravity(Gravity.CENTER);
            t.setTextColor(getResources().getColor(R.color.mySpinnerTextColor));
            t.setTextSize(25);
        }


        if (img != null) {
            System.out.println("dans le chargement");
            StringRequest s = new StringRequest(
                    Request.Method.GET,
                    "http://square.github.io/picasso/",
                    new Response.Listener<String>() {
                        public void onResponse(String response) {
                            com.squareup.picasso.Picasso.Builder p = new com.squareup.picasso.Picasso.Builder(getApplicationContext());
                            com.squareup.picasso.Picasso pic = p.build();
                            pic.load(android.net.Uri.parse(img)).into(i);

                            com.squareup.picasso.Picasso.Builder p2 = new com.squareup.picasso.Picasso.Builder(getApplicationContext());
                            com.squareup.picasso.Picasso pic2 = p2.build();
                            pic2.load(android.net.Uri.parse(uriGold)).into(imgGold);
                            System.out.println("fini");
                        }
                    },
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.getMessage());
                            Toast t = Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);

                        }
                    }
            );
            r.add(s);
        }
    }
}
