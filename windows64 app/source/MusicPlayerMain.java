import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import java.io.File; 
import java.util.*; 
import ddf.minim.AudioPlayer; 
import ddf.minim.*; 
import ddf.minim.analysis.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MusicPlayerMain extends PApplet {

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






Button playBtn;
Button nextBtn;
Button prevBtn;
Minim minim;
Visualizer visual;
MusicPlayer model;
File folder;
String [] filenames;
float position;

public void setup() {
  
  background(255);
  minim = new Minim(this);
  selectFolder("Select a folder with your music library:", "libraryRead");
  model = new MusicPlayer();
  playBtn = new PlayPause(width/2, height/2);
  nextBtn = new NextButton(width*3/4, height/2);
  prevBtn = new PrevButton(width/4, height/2);
  visual = new Visualizer();
  visual.storeData();
  
}

public void draw() {
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

public void mousePressed() {
  playBtn.isClicked(mouseX + 25, mouseY + 25);
  nextBtn.isClicked(mouseX + 25, mouseY + 25);
  prevBtn.isClicked(mouseX + 25, mouseY + 25);
  for (SongButton e : model.songListText) {
    e.isClicked(mouseX, mouseY);
  }
  if (mouseX > 25 && mouseX < (width - 25) && mouseY > (height/2 - 85) && mouseY < (height/2 - 60)) {
    position = PApplet.parseInt(map(mouseX, 25, width - 25, 0, model.getSelectedSong().length()));
    model.getSelectedSong().cue((int)position);
  }
}

public void checkPosition() {
  if (model.getLibrary().size() > 0 && position > 454) {
    model.nextSong();
  }
}


public void displaySlider() {
  fill(50);
  if (mouseX > 25 && mouseX < (width - 25) && mouseY > (height/2 - 85) && mouseY < (height/2 - 60)) {
    rect(25, height/2 - 80, width - 50, 15);
    fill(100);
    ellipse(mouseX, height/2 - 72.5f, 15, 15);
  } else {
    rect(25, height/2 - 75, width - 50, 5);
  }
  for (AudioPlayer s : model.getLibrary()) {
    stroke(255, 0, 0);
    position = map(s.position(), 5, s.length(), 25, width - 25);
    if (model.getSelectedSong() == s) {
      noStroke();
      fill(200, 10, 10);
      ellipse(position, height/2 - 72.5f, 15, 15);
      checkPosition();
    }
  }
}

public void libraryRead(File selection) {
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
abstract class Button {  
  PShape btnOutline; 
  
  int x, y, bWidth, bHeight;

  boolean isPlaying, onHover;

  Button(int x, int y, int bWidth, int bHeight) {
    this.x = x;
    this.y = y;
    this.bWidth = bWidth;
    this.bHeight = bHeight;
    noStroke();
  }

  public boolean isOver(int x, int y) {
    return (x > this.x && x < this.x + bWidth && y > this.y && y < this.y + bHeight);
  }

  public void isClicked(int x, int y) {
    if (isOver(x, y)) {
      isPlaying = !isPlaying;
    }
  }

  public abstract void displayButton();
  
}


class MusicPlayer {
  ArrayList<AudioPlayer> library;
  ArrayList<SongButton> songListText;
  int selectedSong;

  MusicPlayer() {
    library = new ArrayList<AudioPlayer>();
  }

  public void addSong(String song) {
    library.add(minim.loadFile(song));
  }

  public void selectSong(int userSelection) {
    selectedSong = userSelection % library.size();
  }

  public ArrayList<AudioPlayer> getLibrary() {
    return library;
  }

  public AudioPlayer getSelectedSong() {
    return library.get(selectedSong);
  }

  public void setSelectedSong(int userSelect) {
    selectedSong = userSelect;
  }

  public void nextSong() {
    this.getSelectedSong().pause();
    this.getSelectedSong().rewind();
    selectedSong = (selectedSong + 1) % library.size();
  }

  public void prevSong() {
    this.getSelectedSong().pause();
    this.getSelectedSong().rewind();
    selectedSong = ((library.size() + (selectedSong - 1)) % library.size());
  }

  public void displaySongs() {
    int initialYPos = height/2 + 50;
    int offset = 52;
    songListText = new ArrayList<SongButton>();
    if (library.size() > 0) {
      for (int i = 0; i <= library.size() - 1; i++) {
        songListText.add(new SongButton(0, initialYPos + (offset * i), i));
        songListText.get(i).displayButton();
      }
    }
  }

  public void playSong() {
    if (library.size() > 0) {
      if (playBtn.isPlaying) {
        library.get(selectedSong).play();
      } else {
        library.get(selectedSong).pause();
      }
    }
  }
}
class NextButton extends Button {  
  PShape next;
  NextButton(int x, int y) {
    super(x, y, 50, 50);
    next = loadShape("skip.svg");
    btnOutline = createShape(ELLIPSE, x, y, 50, 50);
  }

  public @Override
    void isClicked(int x, int y) {
    if (isOver(x, y)) {
      model.nextSong();
    }
  }

  public void displayButton() {
    shapeMode(CENTER);
    shape(btnOutline);
    if (isOver(mouseX + 25, mouseY + 25)) {
      btnOutline.setFill(200);
    } else {
      btnOutline.setFill(255);
    }
    shape(next, x, y, 50/1.25f, 50/1.25f);
  }
}
class PlayPause extends Button {
  PShape play;
  PShape pause;

  PlayPause(int x, int y) {
    super(x, y, 50, 50);
    play = loadShape("play-btn.svg");
    pause = loadShape("pause-btn.svg");
    btnOutline = createShape(ELLIPSE, x, y, 50, 50);
  }

  public void displayButton() {
    shapeMode(CENTER);
    shape(btnOutline);
    if (isOver(mouseX + 25, mouseY + 25)) {
      btnOutline.setFill(200);
    } else {
      btnOutline.setFill(255);
    }
    if (isPlaying) {
      shape(pause, x, y, 50/1.5f, 50/1.5f);
    } else if (!isPlaying) {
      shape(play, x + 3, y, 50/1.5f, 50/1.5f);
    }
  }
}
class PrevButton extends Button {  
  PShape prev;
  PrevButton(int x, int y) {
    super(x, y, 50, 50);
    prev = loadShape("prev.svg");
    btnOutline = createShape(ELLIPSE, x, y, 50, 50);
  }

  public @Override
    void isClicked(int x, int y) {
    if (isOver(x, y)) {
      model.prevSong();
    }
  }

  public void displayButton() {
    shapeMode(CENTER);
    shape(btnOutline);
    if (isOver(mouseX + 25, mouseY + 25)) {
      btnOutline.setFill(200);
    } else {
      btnOutline.setFill(255);
    }
    shape(prev, x, y, 50/1.25f, 50/1.25f);
  }
}
class SongButton extends Button {
  String songInfo;
  int song;

  SongButton(int x, int y, int song) {
    super(x, y, width, 50);
    this.song = song;
    if (model.library.get(song).getMetaData().title().length() > 0) {
      songInfo = model.library.get(song).getMetaData().title() + " - " + model.library.get(song).getMetaData().author();
    } else {
      songInfo = model.library.get(song).getMetaData().fileName().substring(0, model.library.get(song).getMetaData().fileName().length() - 4);
    }
    btnOutline = createShape(RECT, x, y, width, 50);
  }

  @Override
    public  boolean isOver(int x, int y) {
    return (y > this.y && y < this.y + bHeight);
  }

  public @Override
    void isClicked(int x, int y) {
    if (isOver(x, y)) {
      model.getSelectedSong().rewind();
      model.getSelectedSong().pause();
      model.setSelectedSong(song);
    }
  }

  public void displayButton() {
    if (isOver(mouseX, mouseY) || song == model.selectedSong) {
      btnOutline.setFill(150);
    } else {
      btnOutline.setFill(215);
    }
    shape(btnOutline);
    fill(0);
    textAlign(CENTER);
    textSize(20);
    text(songInfo, width/2, y + 30);
  }
}



class Visualizer {
  BeatDetect beat;
  ArrayList<FFT> fftList;

  Visualizer() {
    beat = new BeatDetect();
    fftList = new ArrayList<FFT>();
  }

  public void storeData() {
    if (model.getLibrary().size() > 0 && fftList.size() < model.getLibrary().size()) {
      for (AudioPlayer s : model.getLibrary()) {
        fftList.add(new FFT(s.bufferSize(), s.sampleRate()));
      }
    }
  }

  public void displayVisualizer() {
    if (model.getLibrary().size() > 0 && fftList.size() > 0) {
      // perform a forward FFT on the samples in jingle's mix buffer,
      // which contains the mix of both the left and right channels of the file
      fftList.get(model.selectedSong).forward(model.getSelectedSong().mix);
      beat.detect(model.getSelectedSong().mix);
      if (beat.isOnset()){
        strokeWeight(5);
        stroke (0xff5dffff);
      } else {
        stroke(0xff62e1e8);
        strokeWeight(4);
      }
      for (int j = 0; j < fftList.get(model.selectedSong).specSize(); j+=2)
      {
        // draw the line for frequency band i, scaling it up a bit so we can see it
        strokeCap(SQUARE);
        line(j * width/128, height/2 - 100, j * width/128, height/2 - 100 - fftList.get(model.selectedSong).getBand(j)* 8);
      }
    }
  }
}
  public void settings() {  size(480, 720);  smooth(4); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "MusicPlayerMain" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
