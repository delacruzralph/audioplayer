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

  boolean isOver(int x, int y) {
    return (x > this.x && x < this.x + bWidth && y > this.y && y < this.y + bHeight);
  }

  void isClicked(int x, int y) {
    if (isOver(x, y)) {
      isPlaying = !isPlaying;
    }
  }

  abstract void displayButton();
  
}
