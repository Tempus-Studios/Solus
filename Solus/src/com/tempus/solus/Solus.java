package com.tempus.solus;

import com.tempus.solus.util.thread.ThreadPool;

import java.awt.EventQueue;

import java.io.IOException;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
 * Locus is a 2D side-scroller game, similar to Super Mario Bros, but with greatly
 * enhanced combat, weapons systems, and several other features. The entire project was created by
 * Luke Bodwell and Shubhankar Naru, including all programming, art, music, sound effects, animation,
 * level design, etc. Locus is also Luke and Shubhankar's Senior Project at Falmuoth High School.
 *
 * Copyright 2017 Luke Bodwell and Shubhankar Naru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Solus {
    private static final Logger logger = Logger.getLogger(Solus.class.getName());
    public static final String GAME_VERSION = "In-Dev";
    public static String LOG_PATH = "res/logs/default.log";

    public Solus() {
        //TODO
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler("res/game_log.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        logger.info("Starting Locus " + GAME_VERSION);
        ThreadPool threadPool = new ThreadPool(2);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                threadPool.runTask(new Engine());
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("Closing Locus ");
            }
        });
    }
}