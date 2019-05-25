package au.edu.jcu.cp3406.paranoidandroid.game;

import android.content.res.AssetManager;

import au.edu.jcu.cp3406.paranoidandroid.game.sound.SoundManager;

public class Game
{
    private final String SOUND_PATH = "Sounds/";
    private final String SPRITE_PATH = "Sprites/";

    private AssetManager assetManager;
    private SoundManager soundManager;

    public Game(AssetManager assetManager)
    {
        this.assetManager = assetManager;

        soundManager = new SoundManager(assetManager, SOUND_PATH);
    }
}
