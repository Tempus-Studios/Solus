package com.tempus.solus.util.audio;

import com.tempus.solus.Engine;
import com.tempus.solus.Solus;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class AudioPlayer {
    private static final Logger logger = Logger.getLogger(Solus.class.getName());
    private List<Music> playlist = new ArrayList<>();
    private Music currentMusic;

    public AudioPlayer() {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        try {
            this.initPlaylist();
            currentMusic = playlist.get(0);
        } catch (SlickException ex) {
            logger.severe("ERROR LOADING MUSIC PATHS");
            logger.severe(ex.getMessage());
        }
    }
    private void initPlaylist() throws SlickException {
        playlist.add(new Music("/res/audio/Locus.ogg"));

        for(int i = 0; i < playlist.size(); i++) {
            playlist.get(i).setVolume(Engine.MUSIC_VOLUME);
        }
    }
    public void setMusic(int index) {
        currentMusic = playlist.get(index);
    }
    public void loopCurrentMusic() {
        currentMusic.loop();
    }
    public void endCurrentMusic() {
        currentMusic.stop();
    }

}

