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

public class FamilyActivity extends AppCompatActivity {

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

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                                      focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
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


        final ArrayList<Word> family = new ArrayList<Word>();

        family.add(new Word("Father","Nanna",R.drawable.family_father,R.raw.father));
        family.add(new Word("Mother", "Amma",R.drawable.family_mother,R.raw.mother));
        family.add(new Word("Son","Koduku",R.drawable.family_son,R.raw.koduku));
        family.add(new Word("Daughter","Kuturu",R.drawable.family_daughter,R.raw.daughter));
        family.add(new Word("Sister","Akka/Chelli",R.drawable.family_younger_sister,R.raw.sister));
        family.add(new Word("Brother","Anna/Tammudu",R.drawable.family_younger_brother,R.raw.brother));
        family.add(new Word("Grandfather","Tathayya",R.drawable.family_grandfather,R.raw.gr_father));
        family.add(new Word("GrandMother","Ammamma/Nannamma",R.drawable.family_grandmother,R.raw.gr_mother));
        family.add(new Word("Aunty", "Pinni",R.drawable.family_older_sister,R.raw.aunty));
        family.add(new Word("Uncle","baabai",R.drawable.family_older_brother,R.raw.uncle));


        WordAdapter familyAdapter = new WordAdapter(this,family,R.color.category_family);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(familyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Word family1 = family.get(position);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                               AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                mediaPlayer = MediaPlayer.create(getApplicationContext(),family1.getVoiceId());
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
