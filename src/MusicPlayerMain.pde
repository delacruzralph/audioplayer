/*
 Final Project: Audio Player + Visualizer by Ralph Dela Cruz
 
 Summary: This project mimicks an audio player such as Apple Music or Spotify and allows
 users to use different functions such as play music, pause music, scroll through music, 
 change song, etc. Additionally there is a visualization of the audio on the screen.
 This is done through a sound library called minim that has features of FFT analysis, allowing songs to
 be analyzed for their varying frequencies. It can also detect beats and display it.
 Classes:
 - Main Project Class: Sets up the entire music player and puts the other classes
 and components together, also handles mouse/keyboard input
 - Music Player Class: Handles songs and any music player functionality including
 the display
 - Sound Visualizer Class: Handles the visualization and analysis of song's 
 frequencies
 - Button Class: Abstract Class that is the basis for all the buttons in the program
 Usage:
 1. When the program is opened, a browse file option will appear. You must select a folder
 that contains your mp3 files.
 2. Press play to listen. The song selected by default is the first song.
 3. Select the next or previous button to cycle through songs or just click
 another song title.
 4. Enjoy!
 Note:
 The libraries that need to be imported should be installed into Processing or else nothing will work.
 */

import ddf.minim.*;
import ddf.minim.analysis.*;
import java.io.File;
import java.util.*;

Button playBtn;
Button nextBtn;
Button prevBtn;
Minim minim;
Visualizer visual;
MusicPlayer model;
File folder;
String [] filenames;
float position;

void setup() {
  size(480, 720);
  background(255);
  minim = new Minim(this);
  selectFolder("Select a folder with your music library:", "libraryRead");
  model = new MusicPlayer();
  playBtn = new PlayPause(width/2, height/2);
  nextBtn = new NextButton(width*3/4, height/2);
  prevBtn = new PrevButton(width/4, height/2);
  visual = new Visualizer();
  visual.storeData();
  smooth(4);
}

void draw() {
  background(0);
  playBtn.displayButton();
  nextBtn.displayButton();
  prevBtn.displayButton();
  model.displaySongs();
  model.playSong();
  displaySlider();
  checkPosition();
  visual.storeData();
  visual.displayVisualizer();
}

void mousePressed() {
  playBtn.isClicked(mouseX + 25, mouseY + 25);
  nextBtn.isClicked(mouseX + 25, mouseY + 25);
  prevBtn.isClicked(mouseX + 25, mouseY + 25);
  for (SongButton e : model.songListText) {
    e.isClicked(mouseX, mouseY);
  }
  if (mouseX > 25 && mouseX < (width - 25) && mouseY > (height/2 - 85) && mouseY < (height/2 - 60)) {
    position = int(map(mouseX, 25, width - 25, 0, model.getSelectedSong().length()));
    model.getSelectedSong().cue((int)position);
  }
}

void checkPosition() {
  if (model.getLibrary().size() > 0 && position > 454) {
    model.nextSong();
  }
}


public void displaySlider() {
  fill(50);
  if (mouseX > 25 && mouseX < (width - 25) && mouseY > (height/2 - 85) && mouseY < (height/2 - 60)) {
    rect(25, height/2 - 80, width - 50, 15);
    fill(100);
    ellipse(mouseX, height/2 - 72.5, 15, 15);
  } else {
    rect(25, height/2 - 75, width - 50, 5);
  }
  for (AudioPlayer s : model.getLibrary()) {
    stroke(255, 0, 0);
    position = map(s.position(), 5, s.length(), 25, width - 25);
    if (model.getSelectedSong() == s) {
      noStroke();
      fill(200, 10, 10);
      ellipse(position, height/2 - 72.5, 15, 15);
      checkPosition();
    }
  }
}

void libraryRead(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    println("User selected " + selection.getAbsolutePath());
    folder = selection;
    filenames = folder.list();
    for (int i = 0; i <= filenames.length - 1; i++)
    {
      if (filenames[i].contains("mp3")) {
        model.addSong(filenames[i]);
      }
    }
  }
}
