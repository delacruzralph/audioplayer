class PlayPause extends Button {
  PShape play;
  PShape pause;

  PlayPause(int x, int y) {
    super(x, y, 50, 50);
    play = loadShape("play-btn.svg");
    pause = loadShape("pause-btn.svg");
    btnOutline = createShape(ELLIPSE, x, y, 50, 50);
  }

  void displayButton() {
    shapeMode(CENTER);
    shape(btnOutline);
    if (isOver(mouseX + 25, mouseY + 25)) {
      btnOutline.setFill(200);
    } else {
      btnOutline.setFill(255);
    }
    if (isPlaying) {
      shape(pause, x, y, 50/1.5, 50/1.5);
    } else if (!isPlaying) {
      shape(play, x + 3, y, 50/1.5, 50/1.5);
    }
  }
}
