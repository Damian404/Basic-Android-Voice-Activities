package com.example.damiancurran.microphone;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private Button openMic;
    private TextView showVoiceText;
    private final int REQ_CODE_SPEECH_OUTPUT = 143;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent2 = getIntent();

        String omg2 = "";
        omg2 += intent2.getStringExtra(SearchManager.QUERY);

        Log.i("omg2", omg2);

        openMic = (Button) findViewById(R.id.button);
        showVoiceText = (TextView) findViewById(R.id.showVoiceOutput);

        openMic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                btnToOpenMic();
            }
        });
    }

    private void btnToOpenMic(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak now");

        try{
            startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);
        }
        catch(ActivityNotFoundException tim){
            //just put a toast if Google mic is not opened
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQ_CODE_SPEECH_OUTPUT: {
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    showVoiceText.setText(voiceInText.get(0));
                    Log.d("output", voiceInText.get(0));
                    //String hello = voiceInText.get(1).toString();
                    //Log.d("printing first hello ", hello);

                    String word = "";
                    word += voiceInText.get(0).length();

                    int iter = word.charAt(0) - '0';

                    String test = "";

                    for(int i=0; i < iter; i++)
                    {
                        String test2 = "";
                        test2 += voiceInText.get(0).charAt(i);

                        if(Character.isDigit(voiceInText.get(0).charAt(i))){

                            test += voiceInText.get(0).charAt(i);
                        }
                    }

                    Log.i("characters collected ", test);

                    if(voiceInText.get(1).equals("1234"))
                    {
                        showVoiceText.setText("got number");
                    }
                }
                break;
            }
        }
    }
}
