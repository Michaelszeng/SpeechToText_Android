package com.example.sphinx4translater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static void main(String[] args) throws Exception {

        Log.d("test", "hello");

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        /*
        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        InputStream stream = new FileInputStream(new File("Mr. BLue Sky.wav"));

        recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.format("Hypothesis: %s\n", result.getHypothesis());
        }
        recognizer.stopRecognition();
        */

        LiveSpeechRecognizer recognizer;
        try {
            recognizer = new LiveSpeechRecognizer(configuration);
            // Start recognition process pruning previously cached data.
            recognizer.startRecognition(true);

            while (true) {
                SpeechResult result = recognizer.getResult();
                Log.d("result", result.getHypothesis());
                String utterance = recognizer.getResult().getHypothesis();
                Log.d("utterance", utterance);
                if (utterance.startsWith("exit")) {
                    break;
                }
            }
            // Pause recognition process. It can be resumed then with startRecognition(false).
            recognizer.stopRecognition();
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception", String.valueOf(e));
        }

    }
}
