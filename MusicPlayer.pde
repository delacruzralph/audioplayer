import ddf.minim.AudioPlayer;

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
