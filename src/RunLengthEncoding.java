/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {

  /**
   *  Define any variables associated with a RunLengthEncoding object here.
   *  These variables MUST be private.
   */
	
	private int height, width;
	private int life;
	private int[] world, type, length;
	List RLE = new List();
	private int count = 1;
	private int size;



  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
	  width = i;
	  height = j;
	  life = starveTime;
	  world = new int[1];
	  world[0] = Ocean.EMPTY;
  }

  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
	  width = i;
	  height = j;
	  type = runTypes;
	  length = runLengths;
	  for (int t = 0; t < length.length; t ++ ) {
		  if (type[t] == Ocean.SHARK) {
			  life = starveTime;
		  }else life = Integer.MAX_VALUE;
		  RLE.insertEnd(life, type[t], length[t]);
	  }
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as a
   *  TypeAndSize object), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
	  count = 1;
  }

  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  a TypeAndSize object, which is nothing more than a way to return two
   *  integers at once.
   *  @return the next run in the enumeration, represented by a TypeAndSize
   *          object.
   */

  public TypeAndSize nextRun() {
	  if (count < RLE.length() + 1) {
		  count ++;
		  return new TypeAndSize(Integer.parseInt(((String)RLE.typeNth(count - 1))), Integer.parseInt((String)RLE.lengthNth(count - 1)));
	  }else {
		  return null;
	  }
  }

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
	  int wTracker = 0;
	  int hTracker = 0;
	  Ocean newOcean = new Ocean(width, height, life);
	  for (int i = 0; i < RLE.length(); i ++) { //traverse the RLE list
		  int t = Integer.parseInt((String)this.RLE.typeNth(i + 1)); // t is the object type at node (i + 1)
		  int l = Integer.parseInt((String)this.RLE.lengthNth(i + 1)); //l is the length of the type at node (i + 1)
		  System.out.println(newOcean.height());
		  if (t == Ocean.SHARK) { //if the current node is SHARK
			  for (int s = 0; s < l; s ++) {
				  newOcean.addShark((wTracker + s) % newOcean.width(), hTracker + ((wTracker + s) / newOcean.width()), life);
			  }
		  }else if (t == Ocean.FISH) {
			  for (int f = 0; f < l; f ++) {
				  newOcean.addFish((wTracker + f) % newOcean.width(), hTracker + ((wTracker + f) / newOcean.width()));
			  }
		  }
		  hTracker = hTracker + ((wTracker + l) / newOcean.width());
		  wTracker = (wTracker + l) % newOcean.width();
	  }
	  return newOcean;
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

  public RunLengthEncoding(Ocean sea) {
	  for (int h = 0; h < sea.height(); h ++) {
		  for (int w = 0; w < sea.width(); w ++) {
			  
		  }
	  }
	  /*if (sea.height() != 0 && sea.width() != 0) {
		  for (int i = 0; i < sea.height() * sea.width(); i ++) {
			  if (i == sea.height() * sea.width() - 1) {
				  RLE.insertFront(sea.sharkFeeding(i % sea.width(), i / sea.width()), sea.cellContents(i % sea.width(), i / sea.width()), size);
			  }else {
				  if (sea.sharkFeeding(i % sea.width(), i / sea.width()) == sea.sharkFeeding((i + 1) % sea.width(), (i + 1) / sea.width())
					  && sea.cellContents(i % sea.width(), i / sea.width()) == sea.cellContents((i + 1) % sea.width(), (i + 1) / sea.width())) {
					  size ++;
				  }else {
					  if (i == sea.height() * sea.width() - 1) {
						  RLE.insertEnd(sea.sharkFeeding((i + 1) % sea.width(), (i + 1) / sea.width()), sea.cellContents((i + 1) % sea.width(), (i + 1) / sea.width()), 1);
					  }else {
						  RLE.insertEnd(sea.sharkFeeding(i % sea.width(), i / sea.width()), sea.cellContents(i % sea.width(), i / sea.width()), size);
						  size = 1;
					  }
				  }
			  }
			  check();
		  }
	  }*/
  }

  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    check();
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  private void check() {
  }

}