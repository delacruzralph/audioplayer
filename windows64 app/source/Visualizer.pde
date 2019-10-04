import ddf.minim.*;
import ddf.minim.analysis.*;

class Visualizer {
  BeatDetect beat;
  ArrayList<FFT> fftList;

  Visualizer() {
    beat = new BeatDetect();
    fftList = new ArrayList<FFT>();
  }

  void storeData() {
    if (model.getLibrary().size() > 0 && fftList.size() < model.getLibrary().size()) {
      for (AudioPlayer s : model.getLibrary()) {
        fftList.add(new FFT(s.bufferSize(), s.sampleRate()));
      }
    }
  }

  void displayVisualizer() {
    if (model.getLibrary().size() > 0 && fftList.size() > 0) {
      // perform a forward FFT on the samples in jingle's mix buffer,
      // which contains the mix of both the left and right channels of the file
      fftList.get(model.selectedSong).forward(model.getSelectedSong().mix);
      beat.detect(model.getSelectedSong().mix);
      if (beat.isOnset()){
        strokeWeight(5);
        stroke (#5dffff);
      } else {
        stroke(#62e1e8);
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
