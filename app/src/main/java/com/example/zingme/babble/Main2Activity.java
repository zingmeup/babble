package com.example.zingme.babble;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zingme.babble.appdata.AppData;
import com.example.zingme.babble.network.Requests;
import com.example.zingme.babble.network.VolleyClass;

import java.util.ArrayList;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    private int requesterFeature;
    TextView title,description;
    EditText input;
    ImageButton send;
    ListView resultListView;
    ArrayAdapter<String> arrayAdapter;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        requesterFeature=getIntent().getIntExtra("feature", 0);
        input=findViewById(R.id.activity_input);
        title=findViewById(R.id.activity_title);
        description=findViewById(R.id.activity_description);
        send=findViewById(R.id.activity_imgBtn);
        send.setOnClickListener(micBtnListener);
        input.addTextChangedListener(textWatcher);
        resultListView=findViewById(R.id.result_listView);
        title.setText(AppData.getData().features.get(requesterFeature).getTitle());
        description.setText(AppData.getData().features.get(requesterFeature).getDescription());
        arrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, AppData.getData().resultList);
        resultListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.e("main-onTextChanged", input.getText().toString());
            if (count>0){
                send.setImageDrawable(getDrawable(R.drawable.ic_search));
                send.setOnClickListener(sendBtnListener);
            }else{
                send.setImageDrawable(getDrawable(R.drawable.ic_microphone));
                send.setOnClickListener(micBtnListener);
            }
            }

        @Override
        public void afterTextChanged(Editable s) {

        }


    };

    View.OnClickListener micBtnListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(Main2Activity.this, "mic", Toast.LENGTH_SHORT).show();
            promptSpeechInput();
        }
    };
    View.OnClickListener sendBtnListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!input.getText().toString().trim().equals("")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        VolleyClass.getInstance(getApplicationContext()).addToRequestQueue(Requests.getRequests().fetchData(input.getText().toString().trim(),requesterFeature, arrayAdapter));
                    }
                }).start();
            }

        }
    };
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    input.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public void onBackPressed() {
        AppData.getData().resultList.clear();
        super.onBackPressed();
    }
}
