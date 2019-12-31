package com.example.language;

public class Word {

    private String mDefaultTranslation;

    private String  mTeluguTranslation;

    private int mImageResourceId  = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mVoiceId;

    private boolean noImage;

    public Word(String defaultTranslation, String teluguTranslation, int voiceId){

        mDefaultTranslation = defaultTranslation;
        mTeluguTranslation = teluguTranslation;
        mVoiceId = voiceId;
    }

    public Word(String defaultTranslation, String teluguTranslation,int imageId, int voiceId){
        mDefaultTranslation = defaultTranslation;
        mTeluguTranslation = teluguTranslation;
        mImageResourceId = imageId;
        mVoiceId = voiceId;
    }

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public String getTeluguTranslation(){
        return mTeluguTranslation;
    }

    public int getmImageResourceId(){
        return mImageResourceId;
    }

    public int getVoiceId(){
        return mVoiceId;
    }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }


}
