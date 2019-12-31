package com.example.language;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);

            } else if (focusChange==AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();

            }else if (focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }

        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


         final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("One","Okati", R.drawable.number_one,R.raw.number_one));
        words.add(new Word("Two","Rendu", R.drawable.number_two,R.raw.number_two));
        words.add(new Word("Three", "Moodu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("Four","Naalugu",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("Five", "Aiyadu", R.drawable.number_five,R.raw.number_five));
        words.add(new Word("Six","Aaru", R.drawable.number_six,R.raw.number_six));
        words.add(new Word("Seven","Yedu",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("Eight", "Yenimidi",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","Tommidi",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("Ten","Padi",R.drawable.number_ten,R.raw.number_ten));


        /**words.add("one");
        words.add("two");
        words.add("three");
        words.add("four");
        words.add("five");
        words.add("six");
        words.add("seven");
        words.add("eight")
        words.add("nine");
        words.add("ten");*/

        WordAdapter adapter = new WordAdapter(this,words,R.color.category_numbers);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Word word = words.get(position);

                releaseMediaPlayer();


                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC
                              ,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){



                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this,word.getVoiceId());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }







            }
        });


       /*while(index<words.size()){
         TextView wordView = new TextView(this);
         wordView.setText(words.get(index));
         rootView.addView(wordView);
         index++;
       }*/


       /*for(int index = 0;index<words.size();index++){

           TextView wordView = new TextView(this);
           wordView.setText(words.get(index));
           rootView.addView(wordView);
       }


  for(int index = 0; index<3; index++){
      Log.v("NumbersActivity", "Index: "+ index + " value: "+ words.get(index));
  }*/


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if (mMediaPlayer != null) {

            mMediaPlayer.release();

            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }

    }
}
