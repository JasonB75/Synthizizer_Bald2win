package com.superking75.synthizizer_baldwin;

//https://abhiandroid.com/ui/switch

/**
 * ALL CHALLENGES HAVE BEEN COMPETED.
 *
 *
 *
 *
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();


    /**
     * Creation of the length of all the notes that will be used later in the program.
     */
    private final int WHOLE_NOTE = 4000;
    private final int HALF_NOTE = WHOLE_NOTE/2;
    private final int QUARTER_NOTE = WHOLE_NOTE/4;
    private final int EIGHT_NOTE = WHOLE_NOTE/8;
    private final int SIXTEEN_NOTE  = WHOLE_NOTE/16;

    /**
     * Creation of the instances of the buttons, numberpickers, and switches that will be put into effect later on in the program
     */
    private Button playEbutton;
    private Button eScaleButton;
    private Button noteNumberButton;
    private Button button3;
    private NumberPicker noteNumber;
    private NumberPicker notePicker;
    private NumberPicker twinkletimes;
    private  Button button5;
    private Switch switch1;

    private MediaPlayerThread mpt; // Creation of the variable used for the .playnote() method.

    /**
     * Creation of most-recent-variables used later in the program
     */
    int timesNotePlayed = 1; //How many times will the note be played
    int notepicked = 0; // Number variable of the selected note
    int twinkleLineTwo = 1; // How many times the second line will be played
    boolean playingLine2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        /**
         * Referencing each xml view object to it's java variable.
         */
        playEbutton = (Button)findViewById(R.id.button1);
        eScaleButton = (Button)findViewById(R.id.button2);
        noteNumberButton = (Button)findViewById(R.id.noteNumberButton);
        button3= (Button)findViewById(R.id.button3);
        button5= (Button) findViewById(R.id.button5);
        //final TextView main = (TextView) findViewById(R.id.text1);
        noteNumber = (NumberPicker) findViewById(R.id.noteNumber);
        notePicker = (NumberPicker) findViewById(R.id.notePicker);
        twinkletimes = (NumberPicker)findViewById(R.id.twinkleTimePicker);
        switch1 = (Switch)findViewById(R.id.switch1);


        /**
         * Sets the features of the 3 number picker objects
         */
        twinkletimes.setMinValue(1);
        twinkletimes.setMaxValue(5);
        twinkletimes.setWrapSelectorWheel(true);

        notePicker.setMinValue(0);
        final String[] noteValues = {"a", "b", "Bb", "C", "C#", "d", "d#", "e", "f", "f#", "g", "g#", "High E", "High F#", "High G"};
        notePicker.setMaxValue(noteValues.length-1);
        notePicker.setDisplayedValues(noteValues);
        notePicker.setWrapSelectorWheel(true);

        noteNumber.setMinValue(1);
        noteNumber.setMaxValue(10);
        noteNumber.setWrapSelectorWheel(true);

        /**
         * Lists of note id's to be used in the playNote() method.
         */
        final int[] allNoteId = {R.raw.scalea,R.raw.scaleb,R.raw.scalebb,R.raw.scalec,R.raw.scalecs,R.raw.scaled,R.raw.scaleds,R.raw.scalee,R.raw.scalef,R.raw.scalefs,R.raw.scaleg,R.raw.scalegs,R.raw.scalehighe,R.raw.scalehighfs,R.raw.scalehighg};
        final int[] noteIdListE = {R.raw.scalee,R.raw.scalefs,R.raw.scalegs,R.raw.scalea,R.raw.scaleb,R.raw.scalecs,R.raw.scaleds,R.raw.scalehighe};
        final int[] twinkleNotesLine1 = {R.raw.scalea, R.raw.scalea, R.raw.scalehighe,R.raw.scalehighe,R.raw.scalehighfs,R.raw.scalehighfs,R.raw.scalehighe,R.raw.scaled,R.raw.scaled,R.raw.scalecs,R.raw.scalecs,R.raw.scaleb,R.raw.scaleb,R.raw.scalea};
        final int[] twinkleNotesLine2 = {R.raw.scalehighe,R.raw.scalehighe,R.raw.scaled,R.raw.scaled,R.raw.scalecs,R.raw.scalecs,R.raw.scaleb};
        final int[] bitesDustNotes = {R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scaleg,R.raw.scalee,R.raw.scalea,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scaleg,R.raw.scalee,R.raw.scalea,
                R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scaleg,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scalee,R.raw.scaleb,R.raw.scaleb,R.raw.scalea,R.raw.scaleb,R.raw.scalea};

        /**
         * Lists of times / note delays that line up with the specified note id's in the above arrays
         */
        final int[] twinkleTimesLine2 = {750,750,750,750,750,750,1500};
        final int[] twinkleTimesLine1 = {750,750,750,750,750,750,1500,750,750,750,750 ,750,750,1500};
       final int[] bitesDustTimes = {QUARTER_NOTE,QUARTER_NOTE,QUARTER_NOTE,SIXTEEN_NOTE,EIGHT_NOTE,EIGHT_NOTE,EIGHT_NOTE,EIGHT_NOTE,SIXTEEN_NOTE+QUARTER_NOTE,
                QUARTER_NOTE,QUARTER_NOTE,QUARTER_NOTE,SIXTEEN_NOTE,EIGHT_NOTE,EIGHT_NOTE,EIGHT_NOTE,EIGHT_NOTE,SIXTEEN_NOTE+QUARTER_NOTE,
                EIGHT_NOTE,EIGHT_NOTE,SIXTEEN_NOTE,SIXTEEN_NOTE,SIXTEEN_NOTE,SIXTEEN_NOTE+EIGHT_NOTE,EIGHT_NOTE,EIGHT_NOTE,SIXTEEN_NOTE,SIXTEEN_NOTE,
                EIGHT_NOTE,EIGHT_NOTE,SIXTEEN_NOTE,EIGHT_NOTE,SIXTEEN_NOTE+QUARTER_NOTE,

        };

        mpt = new MediaPlayerThread(MainActivity.this); // MediaPlayer object used to run the playNote() method


        /**
         * When the NumberPicker is changed this sets the appropreate variable to the times the selected note will play.
         */
        noteNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                timesNotePlayed = newVal;
                //main.setText(newVal);
            }
        });

        /**
         * When the numbePicker is changed, the appropriate variable will be set to the number of times the 2nd line of Twinkle will be played.
         */
        twinkletimes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                twinkleLineTwo = newVal;
                //main.setText(newVal);
            }
        });

        /**
         * When the numbePicker is changed, the appropriate variable will be set to the index of the specified note to be played.
         */
        notePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker
                notepicked= newVal ;
            }
        });

        /**
         * Plays the single E when pressed
         */
        playEbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpt.playNote(R.raw.scalee, 1000);
                Log.e(  TAG, "Button 1 clicked");

            }
        });

        /**
         * Plays the E scale when pressed
         * Challenge 1, 3
         */
        eScaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int stepper = 0;

                Log.e(  TAG, "Button 2 clicked");
               for (int n: noteIdListE)
                {

                    mpt.playNote(n, HALF_NOTE);

                }

            }
        });

        /**
         * References the value from 'notePicked' to the index of all avaliable notes while running through a loop. Said loop runs for the number of times specifed by 'timesNotePlayed'
         * Challenge 2, 4,
         */
        noteNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int stepper = 0;


                while (stepper < timesNotePlayed)
                {
                    mpt.playNote(allNoteId[notepicked], 1000);
                    stepper++;
                }

            }
        });




        /**
         * Plays Twinkle Twinkle Little Star. It does this with for each loops for each line of the songs, here it uses the note with it's delay to call the playNote method.
         * challenge 5, 6, 7, 8, 9, 10, 11
         */
        button3.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                int stepper = 0;
                for (int n: twinkleNotesLine1) //Line 1
                {

                    mpt.playNote(n, twinkleTimesLine1[stepper]);
                    stepper++;

                }
                if (switch1.isChecked())
                {
                stepper=0;
                    for (int n : twinkleNotesLine2) //line 2
                    {
                        mpt.playNote(n, twinkleTimesLine2[stepper]);
                        stepper++;

                    }
               }
               stepper = 0;
                for (int n: twinkleNotesLine1) // line 3
                {

                    mpt.playNote(n, twinkleTimesLine1[stepper]);
                    stepper++;

                }


            }
        });

        /**
         * When clicked this plays a section of another one bites the dust by running through each element of the
         * array containing the note id's and the corresponding note times. It put's these into the playNote method to play them
         * challenge 12
         */
        button5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int stepper = 0;
        for (int n: bitesDustNotes)
        {

            mpt.playNote(n, bitesDustTimes[stepper]);
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
