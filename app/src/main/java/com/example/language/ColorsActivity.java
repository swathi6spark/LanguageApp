package com.example.language;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
                                               new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT
                               || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){

                mediaPlayer.pause();
                mediaPlayer.seekTo(0);

            }else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();

            }else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }


        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);



        final ArrayList<Word> colors = new ArrayList<Word>();

        colors.add(new Word("Red","Yerupu",R.drawable.color_red,R.raw.red));
        colors.add(new Word("Green","Aakupacha",R.drawable.color_green,R.raw.green));
        colors.add(new Word("Brown","Godhuma Rangu",R.drawable.color_brown,R.raw.brown));
        colors.add(new Word("Grey","Boodidha Rangu",R.drawable.color_gray,R.raw.grey));
        colors.add(new Word("Black", "Nalupu",R.drawable.color_black,R.raw.black));
        colors.add(new Word("White","Telupu", R.drawable.color_white,R.raw.white));
        colors.add(new Word("Blue","Neelam",R.drawable.color_black,R.raw.blue));
        colors.add(new Word("Yellow","Pasupu", R.drawable.color_mustard_yellow,R.raw.yellow));
        colors.add(new Word("Orange","Kaashayam Rngu",R.drawable.color_dusty_yellow,R.raw.orange));
        colors.add(new Word("Pink","Gulabi Rangu",R.drawable.color_red,R.raw.pink));

        WordAdapter adapter = new WordAdapter(this,colors,R.color.category_colors);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Word color = colors.get(position);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                                   AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), color.getVoiceId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }

            }
        });







    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


    private void releaseMediaPlayer(){
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }
}
