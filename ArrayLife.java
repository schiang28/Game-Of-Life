package uk.ac.cam.yhc49.prejava.ex3;

class ArrayLife {
	public static boolean getCell(boolean[][] world, int col, int row) {
        if (row < 0 || row > world.length - 1) return false;
        if (col < 0 || col > world[row].length - 1) return false;
        return world[row][col];
	}

	public static void setCell(boolean[][] world, int col, int row, boolean value) {
        // if row and column are out of bounds, return
		if (row < 0 || row > world.length - 1 || col < 0 || col > world[row].length - 1)
            return;
        world[row][col] = value;
    }

	public static void print(boolean[][] world) {
		System.out.println("-");
		for (int row = 0; row < world.length; row++) {
			for (int col = 0; col < world[row].length; col++) {
				System.out.print(getCell(world, col, row) ? "#" : "_");
			}
			System.out.println();
		}
	}

	public static int countNeighbours(boolean[][] world, int col, int row) {
		int count_neighbours = 0;
		if ((col + 1) < world[row].length)
			if (getCell(world, col+1, row))
				count_neighbours++;
		if ((col - 1) >= 0)
			if (getCell(world, col-1, row))
				count_neighbours++;
		if ((row - 1) >= 0 & (col - 1) >= 0)	
			if (getCell(world, col-1, row-1))
				count_neighbours++;
		if ((row - 1) >= 0)
			if (getCell(world, col, row-1))
				count_neighbours++;
		if ((row - 1) >= 0 & (col + 1) < world[row].length)
			if (getCell(world, col+1, row-1))
				count_neighbours++;
		if ((row + 1) < world.length & (col - 1) >= 0)
			if (getCell(world, col-1, row+1))
				count_neighbours++;
		if ((row + 1) < world.length)
			if (getCell(world, col, row+1))
				count_neighbours++;
		if ((row + 1) < world.length & (col + 1) < world[row].length)
			if (getCell(world, col+1, row+1))
				count_neighbours++;
		
		return count_neighbours;
	}

	public static boolean computeCell(boolean[][] world, int col, int row) {

		// liveCell is true if the cell at position (col,row) in world is live
		boolean liveCell = getCell(world, col, row);

		// neighbours is the number of live neighbours to cell (col,row)
		int neighbours = countNeighbours(world, col, row);

		// we will return this value at the end of the method to indicate whether 
		// cell (col,row) should be live in the next generation
		boolean nextCell = false;

		//A live cell with less than two neighbours dies (underpopulation)
		if (neighbours < 2) {
			nextCell = false;
		}

		//A live cell with two or three neighbours lives (a balanced population)
		if ((neighbours == 2 | neighbours == 3) & liveCell) {
			nextCell = true;
		}

		//A live cell with with more than three neighbours dies (overcrowding)
		if (neighbours > 3 & liveCell) {
			nextCell = false;
		}

		//A dead cell with exactly three live neighbours comes alive
		if (neighbours == 3 & liveCell == false) {
			nextCell = true;
		}

		return nextCell;
	}
 
	public static boolean[][] nextGeneration(boolean[][] world) {
		boolean[][] new_world = new boolean[world.length][];
        for(int i = 0; i < world.length; i++) {
            new_world[i] = new boolean[world[i].length];
            for(int j = 0; j < world[i].length; j++) {
                new_world[i][j] = world[i][j];
            }
        }
		for (int row = 0; row < world.length; row++) {
			for (int col = 0; col < world[row].length; col++) {
				boolean new_state = computeCell(world, col, row);
				setCell(new_world, col, row, new_state);
			}
		}
		return new_world;
	}

	public static void play(boolean[][] world) throws java.io.IOException {
		int userResponse = 0;
		while (userResponse != 'q') {
			print(world);
			userResponse = System.in.read();
			world = nextGeneration(world);
	    }
    }

	public static boolean getFromPackedLong(long packed, int position) {
        return ((packed >>> position) & 1) == 1;
    }

    public static void main(String[] args) throws java.io.IOException {
        int size = Integer.parseInt(args[0]);
        long initial = Long.decode(args[1]);
        boolean[][] world = new boolean[size][size];
        //place the long representation of the game board in the centre of "world"
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                world[i+size/2-4][j+size/2-4] = getFromPackedLong(initial,i*8+j);
            }
        }
        play(world);
    }
}