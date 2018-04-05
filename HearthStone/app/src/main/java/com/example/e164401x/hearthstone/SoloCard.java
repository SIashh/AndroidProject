package com.example.e164401x.hearthstone;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

        final String img = b.getString("img");
        final String uriGold = b.getString("imgGold");
        System.out.println(b);
        final String type = b.getString("type");
        final String rarity = b.getString("rarity");
        final String classe = b.getString("classe");
        final ImageView i = (ImageView) findViewById(R.id.imageView);
        final ImageView imgGold = (ImageView) findViewById(R.id.imageGold);


        RequestQueue r = Volley.newRequestQueue(getApplicationContext());

        if(classe !=""){
            TextView t = (TextView) findViewById(R.id.textView);
            t.setText(classe);
            t.setTextColor(getResources().getColor(R.color.whitee));
            t.setTextSize(40);
            System.out.println("fin texte view");
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
                            System.out.println("problème");

                        }
                    }
            );
            r.add(s);
        }

        if(i.getResources() == null){
            i.setImageResource(R.mipmap.notfound);
            System.out.println("notfound");
        }

    }
}
