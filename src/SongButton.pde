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

  @Override
    void isClicked(int x, int y) {
    if (isOver(x, y)) {
      model.getSelectedSong().rewind();
      model.getSelectedSong().pause();
      model.setSelectedSong(song);
    }
  }

  void displayButton() {
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
