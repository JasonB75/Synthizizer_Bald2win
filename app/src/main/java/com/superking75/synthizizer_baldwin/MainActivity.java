package com.superking75.synthizizer_baldwin;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private final int WHOLE_NOTE = 4000;
    private final int HALF_NOTE = WHOLE_NOTE/2;
    private final int QUARTER_NOTE = WHOLE_NOTE/4;
    private final int EIGHT_NOTE = WHOLE_NOTE/8;
    private final int SIXTEEN_NOTE  = WHOLE_NOTE/16;

    private Button button1;
    private Button button2;
    private Button noteNumberButton;
    private Button button3;
    private NumberPicker noteNumber;
    private NumberPicker notePicker;
    private NumberPicker twinkletimes;
    private  Button button5;

    private MediaPlayer mpA;
    private MediaPlayer mpB;
    private MediaPlayer mpBb;
    private MediaPlayer mpC;
    private MediaPlayer mpCs;
    private MediaPlayer mpD;
    private MediaPlayer mpDs;
    private MediaPlayer mpE;
    private MediaPlayer mpF;
    private MediaPlayer mpFs;
    private MediaPlayer mpG;
    private MediaPlayer mpGs;
    private MediaPlayer mpHighE;
    private MediaPlayer mpHighF;
    private MediaPlayer mpHighFs;
    private MediaPlayer mpHighG;
    private MediaPlayerThread mpt;
    int noteNoteNumber = 1;
    int notepicked = 0;
    int twinkleLineTwo = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        noteNumberButton = (Button)findViewById(R.id.noteNumberButton);
        button3= (Button)findViewById(R.id.button3);
        button5= (Button) findViewById(R.id.button5);
        final TextView main = (TextView) findViewById(R.id.text1);
        noteNumber = (NumberPicker) findViewById(R.id.np);
        notePicker = (NumberPicker) findViewById(R.id.note);
        twinkletimes = (NumberPicker)findViewById(R.id.twinkle);
        twinkletimes.setMinValue(1);
        twinkletimes.setMaxValue(5);
        twinkletimes.setWrapSelectorWheel(true);
        final String[] noteValues = {"a", "b", "Bb", "C", "C#", "d", "d#", "e", "f", "f#", "g", "g#", "High E", "High F#", "High G"};
        notePicker.setMinValue(0);
        notePicker.setMaxValue(noteValues.length-1);
        notePicker.setDisplayedValues(noteValues);
        notePicker.setWrapSelectorWheel(true);

        final MediaPlayer[] noteList ={mpA, mpB, mpBb, mpC, mpCs, mpD, mpDs, mpE, mpF, mpFs,mpG, mpGs, mpHighE, mpHighFs, mpHighG};
        final int[] allNoteId = {R.raw.scalea,R.raw.scaleb,R.raw.scalebb,R.raw.scalec,R.raw.scalecs,R.raw.scaled,R.raw.scaleds,R.raw.scalee,R.raw.scalef,R.raw.scalefs,R.raw.scaleg,R.raw.scalegs,R.raw.scalehighe,R.raw.scalehighfs,R.raw.scalehighg};
        final int[] noteIdListE = {R.raw.scalee,R.raw.scalefs,R.raw.scalegs,R.raw.scalea,R.raw.scaleb,R.raw.scalecs,R.raw.scaleds,R.raw.scalehighe};
        //High E, High E, D, D, C Sharp, C Sharp, B
        final int[] twinkleList1 = {R.raw.scalea, R.raw.scalea, R.raw.scalehighe,R.raw.scalehighe,R.raw.scalehighfs,R.raw.scalehighfs,R.raw.scalehighe,R.raw.scaled,R.raw.scaled,R.raw.scalecs,R.raw.scalecs,R.raw.scaleb,R.raw.scaleb,R.raw.scalea};
        final int[] twinkleList2 = {R.raw.scalehighe,R.raw.scalehighe,R.raw.scaled,R.raw.scaled,R.raw.scalecs,R.raw.scalecs,R.raw.scaleb};
        final int[] twinkleTimes2 = {750,750,750,750,750,750,1500};
        final int[] twinkleTimes = {750,750,750,750,750,750,1500,750,750,750,750,750,750,1500};

        final int[] bitesDust = {R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scaleg,R.raw.scalee,R.raw.scalea,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scaleg,R.raw.scalee,R.raw.scalea,
                R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scaleg,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scaleb,R.raw.scaleb,R.raw.scalea,R.raw.scaleb,R.raw.scalea};
        final int[] bitesNums = {QUARTER_NOTE,QUARTER_NOTE,QUARTER_NOTE,SIXTEEN_NOTE,EIGHT_NOTE,EIGHT_NOTE,EIGHT_NOTE,EIGHT_NOTE,SIXTEEN_NOTE+QUARTER_NOTE,
                QUARTER_NOTE,QUARTER_NOTE,QUARTER_NOTE,SIXTEEN_NOTE,EIGHT_NOTE,EIGHT_NOTE,EIGHT_NOTE,EIGHT_NOTE,SIXTEEN_NOTE+QUARTER_NOTE,
                EIGHT_NOTE,EIGHT_NOTE,SIXTEEN_NOTE,SIXTEEN_NOTE,SIXTEEN_NOTE,SIXTEEN_NOTE+EIGHT_NOTE,EIGHT_NOTE,EIGHT_NOTE,SIXTEEN_NOTE,SIXTEEN_NOTE,
                EIGHT_NOTE,EIGHT_NOTE,SIXTEEN_NOTE,EIGHT_NOTE,SIXTEEN_NOTE+QUARTER_NOTE,

        };


        mpt = new MediaPlayerThread(MainActivity.this);

        //fi
        // nal MediaPlayer[] twinkleArray = {mpA, mpB};


        noteNumber.setMinValue(1);
        noteNumber.setMaxValue(10);
        noteNumber.setWrapSelectorWheel(true);


        noteNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                noteNoteNumber = newVal;
                //main.setText(newVal);
            }
        });
        twinkletimes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                twinkleLineTwo = newVal;
                //main.setText(newVal);
            }
        });

        notePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker
                notepicked= newVal ;
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpE.seekTo(0);
                Log.e(  TAG, "Button 1 clicked");
                mpE.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int stepper = 0;

                Log.e(  TAG, "Button 2 clicked");
               for (int n: noteIdListE)
                {

                    mpt.playNote(n, 1000);

                }

            }
        });

        noteNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stepper = 0;


                while (stepper < noteNoteNumber)
                {
                    mpt.playNote(allNoteId[notepicked], 1000);
                   // noteList[notepicked].seekTo(0);
                    //noteList[notepicked].start();
                   // delayPlaying(WHOLE_NOTE);
                  //  temp[notepicked].stop();
                    stepper++;

                }

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                int stepper = 0;
                for (int n: twinkleList1)
                {

                    mpt.playNote(n, twinkleTimes[stepper]);
                    stepper++;

                }

                stepper=0;
                //for(int i = 0; i< twinkleLineTwo;i++)
                //{
                    for (int n : twinkleList2) {

                        mpt.playNote(n, twinkleTimes2[stepper]);
                        stepper++;

                    }
             //   }
               stepper = 0;
                for (int n: twinkleList1)
                {

                    mpt.playNote(n, twinkleTimes[stepper]);
                    stepper++;

                }


            }
        });
button5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int stepper = 0;
        for (int n: bitesDust)
        {

            mpt.playNote(n, bitesNums[stepper]);
            stepper++;

        }


    }
});

    }





    private void delayPlaying(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Log.e("SynthesizerActivity","Audio playback interrupted");
        }
    }

}
