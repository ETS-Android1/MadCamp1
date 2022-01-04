package com.example.madcamp1;

import static android.app.Activity.RESULT_OK;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import android.content.ClipData;
import android.content.ClipboardManager;

import static android.content.Context.CLIPBOARD_SERVICE;


public class FragmentVoiceRecord extends Fragment {
    View v;
    private Intent intent;
    private TextView log;
    private String recordedString;
    boolean fabVisible = false;

    public static final int REQUEST_CODE = 2;

    public FragmentVoiceRecord() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_voice_record, container, false);
        FloatingActionButton fab_main = v.findViewById(R.id.fab_main);
        FloatingActionButton fab_kor = v.findViewById(R.id.fab_kor);
        FloatingActionButton fab_eng = v.findViewById(R.id.fab_eng);
        TextView fab_kor_text = v.findViewById(R.id.fab_kor_text);
        TextView fab_eng_text = v.findViewById(R.id.fab_eng_text);
        log = v.findViewById(R.id.tvVoice);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fabVisible) {
                    fab_kor.show();
                    fab_eng.show();
                    fab_eng_text.setVisibility(View.VISIBLE);
                    fab_kor_text.setVisibility(View.VISIBLE);
                    fabVisible = true;
                } else {
                    fab_kor.hide();
                    fab_eng.hide();
                    fabVisible = false;
                    fab_kor_text.setVisibility(View.GONE);
                    fab_eng_text.setVisibility(View.GONE);
                }
            }
        });


        fab_kor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Microphone Activated", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                VoiceTask voiceTask = new VoiceTask("ko-KR");
                fab_kor.hide();
                fab_eng.hide();
                fabVisible = false;
                fab_kor_text.setVisibility(View.GONE);
                fab_eng_text.setVisibility(View.GONE);
                voiceTask.execute();
            }
        });

        fab_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Microphone Activated", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                VoiceTask voiceTask = new VoiceTask("en-US");
                fab_kor.hide();
                fab_eng.hide();
                fabVisible = false;
                fab_kor_text.setVisibility(View.GONE);
                fab_eng_text.setVisibility(View.GONE);
                voiceTask.execute();
            }
        });
        return v;
    }

    public class VoiceTask extends AsyncTask<String, Integer, String> {
        String str = null;
        String language = null;

        public VoiceTask(String language) {
            this.language = language;
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
                startActivityForResult(intent, REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }


        @Override
        protected void onPostExecute(String result) {
            try {

            } catch (Exception e) {
                Log.d("onActivityResult", "getImageURL exception");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            ArrayList<String> results = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String str = results.get(0);
            recordedString = str;
            Toast.makeText(v.getContext(), "필기에 성공했습니다", Toast.LENGTH_SHORT).show();

            TextView tv = v.findViewById(R.id.tvVoice);
            tv.setText(str);

            tv.setTextIsSelectable(true);

            tv.setOnClickListener(new View.OnClickListener() {
                final Context context = getActivity().getApplicationContext();

                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(tv.getText());
                    Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}