package au.edu.jcu.cp3406.paranoidandroid.game.sound;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

public class SoundManager
{
    private SoundPool soundPool;
    private boolean isLoaded;

    private int sounds[];

    public SoundManager(AssetManager assetManager, String assetPath)
    {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sounds = new int[Sound.values().length];

        for(Sound sound : Sound.values())
        {
            String fileName = sound.toString().toLowerCase().concat(".mp3");

            try
            {
                AssetFileDescriptor assetFileDescriptor = assetManager.openFd(assetPath + fileName);
                sounds[sound.ordinal()] = soundPool.load(assetFileDescriptor, 0);
            }
            catch (IOException e)
            {
                System.out.println(String.format("Incorrect Path or Asset %s Does not Exist", fileName));
                e.printStackTrace();
            }
        }

        isLoaded = true;
    }

    public void play(Sound sound)
    {
        if(!isLoaded) return;
        soundPool.play(sounds[sound.ordinal()], 1, 1, 1, 0, 1);
    }

}
