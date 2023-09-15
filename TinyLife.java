class TinyLife {
	public static boolean getCell(long world, int col, int row) {
		if (col < 0 | col > 7 | row < 0 | row > 7)
			return false;
		return PackedLong.get(world, row * 8 + col);
	}

	public static long setCell(long world, int col, int row, boolean newval) {
		if (col < 0 | col > 7 | row < 0 | row > 7)
			return world;
		return PackedLong.set(world, row * 8 + col, newval);
	}

	public static void print(long world) {
		System.out.println("-");
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				System.out.print(getCell(world, col, row) ? "#" : "_");
			}
			System.out.println();
		}
	}

	public static int countNeighbours(long world, int col, int row) {
		int count_neighbours = 0;
		if ((col + 1) < 8)
			if (PackedLong.get(world, row * 8 + col + 1))
				count_neighbours++;
		if ((col - 1) > 0)
			if (PackedLong.get(world, row * 8 + col - 1))
				count_neighbours++;
		if ((row - 1) > 0 & (col - 1) > 0)	
			if (PackedLong.get(world, (row-1) * 8 + col - 1))
				count_neighbours++;
		if ((row - 1) > 0)
			if (PackedLong.get(world, (row-1) * 8 + col))
				count_neighbours++;
		if ((row - 1) > 0 & (col + 1) < 8)
			if (PackedLong.get(world, (row-1) * 8 + col + 1))
				count_neighbours++;
		if ((row + 1) < 8 & (col - 1) > 0)
			if (PackedLong.get(world, (row+1) * 8 + col - 1))
				count_neighbours++;
		if ((row + 1) < 8)
			if (PackedLong.get(world, (row+1) * 8 + col))
				count_neighbours++;
		if ((row + 1) < 8 & (col + 1) < 8)
			if (PackedLong.get(world, (row+1) * 8 + col + 1))
				count_neighbours++;
		
		return count_neighbours;
	}

	public static boolean computeCell(long world,int col,int row) {

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
		//TODO: write a if statement to check neighbours and update nextCell
		if ((neighbours == 2 | neighbours == 3) & liveCell) {
			nextCell = true;
		}

		//A live cell with with more than three neighbours dies (overcrowding)
		//TODO: write a if statement to check neighbours and update nextCell
		if (neighbours > 3 & liveCell) {
			nextCell = false;
		}

		//A dead cell with exactly three live neighbours comes alive
		//TODO: write a if statement to check neighbours and update nextCell
		if (neighbours == 3 & liveCell == false) {
			nextCell = true;
		}

		return nextCell;
	}

	public static long nextGeneration(long world) {
		long new_world = world;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				boolean new_state = computeCell(world, col, row);
				new_world = setCell(new_world, col, row, new_state);
			}
		}
		return new_world;
	}

	public static void play(long world) throws java.io.IOException {
		int userResponse = 0;
		while (userResponse != 'q') {
			print(world);
			userResponse = System.in.read();
			world = nextGeneration(world);
	}
}

	public static void main(String[] args) throws java.io.IOException {
   		play(Long.decode(args[0]));
	}
}
