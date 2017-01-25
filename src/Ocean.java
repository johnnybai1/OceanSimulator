/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */
  
  private int world[][];
  private int counter[][];
  private int width, height;
  private int life;
  private int count = 0;


  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having width i and
   *  height j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public Ocean(int i, int j, int starveTime) {
	  width = i;
	  height = j;
	  life = starveTime;
	  world = new int[j][i];
	  counter = new int[j][i];
	  for (int h = 0; h < height; h ++) {
		  for (int w = 0; w < width; w ++) {
			  world[h][w] = EMPTY;
		  }
	  }
  }

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */
  
  public int wrapY(int y) {
	  while (y < 0) {
		  y += height;
	  }
	  return y % height;
  }
  
  public int wrapX(int x) {
	  while (x < 0) {
		  x += width;
	  }
	  return x % width;
  }

  public int width() {
	  return this.width;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
	  return this.height;
  }

  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */

  public int starveTime() {
	  return life;
  }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
	  if (world[wrapY(y)][wrapX(x)] == EMPTY) {
		  world[wrapY(y)][wrapX(x)] = FISH;
	  }
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
	  if (world[wrapY(y)][wrapX(x)] == EMPTY) {
		  world[wrapY(y)][wrapX(x)] = SHARK;
		  counter[wrapY(y)][wrapX(x)] = life;
	  }
  }

  /**
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) {
    return world[wrapY(y)][wrapX(x)];
  }
  
  private int fishCount(int x, int y) {
	  int fish = 0;
	  for (int xm = -1; xm < 2; xm ++) {
		  for (int ym = -1; ym < 2; ym ++) {
			  if (cellContents(x + xm , y + ym) == FISH) {
				  fish ++;
			  }
		  }
	  }
	  return fish;
	  
  }
  
  private int sharkCount(int x, int y) {
	  int shark = 0;
	  for (int xm = -1; xm < 2; xm ++) {
		  for (int ym = -1; ym < 2; ym ++) {
			  if (cellContents(x + xm , y + ym) == SHARK) {
				  shark ++;
			  }
		  }
	  }
	  return shark;
  }
  

  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */

  public Ocean timeStep() {
	  Ocean newWorld = new Ocean(width, height, life);
	  for (int h = 0; h < height; h ++) {
		  for (int w = 0; w < width; w ++) {
			  if (world[h][w] == SHARK) {
				  if (fishCount(w, h) > 0) {
					 newWorld.addShark(w, h);
				  }else if (fishCount(w, h) == 0) {
					  newWorld.addShark(w, h);
					  newWorld.counter[h][w] = counter[h][w] - count - 1;
					  if (newWorld.counter[h][w] < 0) {
						  newWorld.world[h][w] = EMPTY;
					  }
				  }
			  }if (world[h][w] == FISH) {
				  if (sharkCount(w, h) == 0) {
					  newWorld.addFish(w, h);
				  }else if (sharkCount(w, h) > 1) {
					  newWorld.addShark(w, h);
				  }else if (sharkCount(w, h) == 1) {
					  newWorld.world[h][w] = EMPTY;
				  }
			  }if (world[h][w] == EMPTY) {
				  if (fishCount(w, h) < 2) {
					  newWorld.world[h][w] = EMPTY;
				  }else if (fishCount(w, h) > 1 && sharkCount(w, h) < 2) {
					  newWorld.addFish(w, h);
				  }else if (fishCount(w, h) > 1 && sharkCount(w, h) > 1) {
					  newWorld.addShark(w, h);
				  }
			  }
		  }
	  }
	  count ++;
	  return newWorld;
  }

  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param feeding is an integer that indicates the shark's hunger.  You may
   *         encode it any way you want; for instance, "feeding" may be the
   *         last timestep the shark was fed, or the amount of time that has
   *         passed since the shark was last fed, or the amount of time left
   *         before the shark will starve.  It's up to you, but be consistent.
   */

  public void addShark(int x, int y, int feeding) {
	  if (world[wrapY(y)][wrapX(x)] == EMPTY) {
		  world[y][x] = SHARK;
		  feeding = life;
	  }
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int sharkFeeding(int x, int y) {
	  return counter[wrapY(y)][wrapX(x)];
  }

}