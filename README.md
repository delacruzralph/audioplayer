# audioplayer

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
