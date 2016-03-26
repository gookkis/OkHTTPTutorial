package com.gookkis.okhttptutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //Variable
    private static String url = "http://www.gookkis.com/hello.html";
    private static String TAG = "MainActivity";
    TextView textView;
    Button buttonProses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inisialisasi textView dan button
        textView = (TextView) findViewById(R.id.text_view);
        buttonProses = (Button) findViewById(R.id.btn_proses);


        //method pada saat button proses diklik
        buttonProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Membuat intace baru
                OkHttpClient client = new OkHttpClient();

                //membuat cache agar hemat bandwith
                client.cache();

                //Membuat Request
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                // Membuat Async untuk mengambil respone
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //Masukan kode disini jika request gagal
                        Toast.makeText(getApplicationContext(), "HTTP Request Failure", Toast.LENGTH_LONG).show();

                        //kode untuk me-log error
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        //jika HTTP respone kode bukan 200
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }

                        //Merubah response body menjadi string
                        final String responseData = response.body().string();

                        // Menampilkan string dalam textview
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                textView.setText(responseData);
                            }
                        });
                    }
                });

            }
        });


    }

}
