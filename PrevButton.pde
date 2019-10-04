class PrevButton extends Button {  
  PShape prev;
  PrevButton(int x, int y) {
    super(x, y, 50, 50);
    prev = loadShape("prev.svg");
    btnOutline = createShape(ELLIPSE, x, y, 50, 50);
  }

  @Override
    void isClicked(int x, int y) {
    if (isOver(x, y)) {
      model.prevSong();
    }
  }

  void displayButton() {
    shapeMode(CENTER);
    shape(btnOutline);
    if (isOver(mouseX + 25, mouseY + 25)) {
      btnOutline.setFill(200);
    } else {
      btnOutline.setFill(255);
    }
    shape(prev, x, y, 50/1.25, 50/1.25);
  }
}
