/**
 * NameRecord.java
 *
 * Bruce Gavins
 * MSI-5030
 * Final Project
 *
 * This class encapsulates the data for a single name and its popularity ranks over various decades
 * The class stores the name and its rank for each decade from 1900 to 2000.
 */
public class NameRecord {
    //Constants for the data structure
    public static final int START = 1900; // Starting year for the dataset
    public static final int DECADES = 11; //Number of decades the dataset covers (1900-2000)

    private String name; // the name represented by this record
    private int[] ranks; // an array to store rank values for each decade

    /**
     * Constructor takes data line from the file and parses it to set up the NameRecord object
     * takes as parameter a string containing the the name and rank data
     */
    public NameRecord(String line) {
	//Initializes the ranks array
	ranks = new int[DECADES];

	//Splits the line by spaces for readability.
	String[] parts = line.split(" ");

	//The first part is the name
	name = parts[0];

	//The remaining parts are the ranks for each decade
	for(int i = 0; i < DECADES && i + 1 < parts.length; i++) {
	    try {
		ranks[i] = Integer.parseInt(parts[i+1]);
	    }
	    catch(NumberFormatException e) {
		//handles possible parsing errors from data transfer
		ranks[i] = 0;
	    }
	}
    }

    /**
     * Getter: gets the name from the record
     * returns name as a string
     */
    public String getName() {
	return name;
    }

    /**
     * Getter: gets the rank of the name for the specified decade
     * takes parameter of decade (0 for 1900, 1 for 1910, etc)
     * returns the rank of the name during that decade (0 if not in top 1000)
     */
    public int getRank(int decade) {
	if(decade >= 0 && decade < DECADES) {
	    return ranks[decade];
	} else {
	    return 0; //returns 0 for invalid decade inputs
	}
    }

    /**
     * Finds the year in which this name was most popular
     * returns the actual year when the name had its best rank
     */
    public int bestYear() {
	int bestRank = Integer.MAX_VALUE;
	int bestDecade = 0;

	//Find the decade with the lowest non-zero rank (i.e. 1 is the most popular)
	for(int i = 0; i < DECADES; i++) {
	    if(ranks[i] > 0 && ranks[i] < bestRank) {
		bestRank = ranks[i];
		bestDecade = i;
	    }
	}

	//Converts decade index from array to actual year
	return START + (bestDecade * 10);
    }

    /**
     * Plots the popularity of the name over time
     * uses StdDraw to create the visualization of the name's popularity
     */
    public void plot() {

	//Sets canvas size
	int leftMargin = 50;
	int rightMargin = 50;
	int topMargin = 50;
	int bottomMargin = 100;
	
        //Sets up the drawing window
        StdDraw.setXscale(START - leftMargin, START + (DECADES * 10) +rightMargin);
        StdDraw.setYscale(1100+ bottomMargin, 0 - topMargin); //rank 1 at top, 1100 at bottom.

	//enables rescaling to create true points on the grap representation (not stretched ovals)
	StdDraw.enableDoubleBuffering();
	StdDraw.setPenRadius(); //Resets pen radius to default
        
        // Draw title
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);
        StdDraw.text((START + (DECADES * 10))/2, 30- topMargin, "Popularity of \"" + name + "\" Over Time");
        
        // Draw axes and labels
        StdDraw.setPenRadius(0.002);
        
        // X-axis
        StdDraw.line(START - 10, 1050 + bottomMargin/2, START + (DECADES * 10) + 10, 1050 + bottomMargin/2);
        
         // Y-axis
        StdDraw.line(START - 10, 0, START - 10, 1050 + bottomMargin/2);
       
         // X-axis tick marks and labels
        StdDraw.setPenRadius(0.001);
	for (int i = 0; i < DECADES; i++) {
	    int year = START + (i * 10);
	    StdDraw.line(year, 1050 + bottomMargin/2, year, 1055 + bottomMargin/2);
	    // Use angled text for x-axis labels to prevent overlap
	    StdDraw.text(year, 1075 + bottomMargin/2, Integer.toString(year), 45);
	}
        
        // Y-axis tick marks and labels
        for (int rank = 1; rank <= 1000; rank += 200) {
            StdDraw.line(START - 10, rank, START - 15, rank);
            StdDraw.text(START - 25, rank, Integer.toString(rank));
        }
        
        // Grid lines
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.setPenRadius(0.001);
        
        // Horizontal grid lines
        for (int rank = 200; rank <= 1000; rank += 200) {
            StdDraw.line(START - 10, rank, START + (DECADES * 10) + 10, rank);
        }
        
        // Vertical grid lines
        for (int i = 0; i < DECADES; i++) {
            int year = START + (i * 10);
            StdDraw.line(year, 0, year, 1050 + bottomMargin/2);
        }

        // Chooses a random color for this the name's plotting for user readability
        String[] colors = {"BLACK", "BLUE", "CYAN", "DARK_GRAY", "GRAY", "GREEN", 
                          "LIGHT_GRAY", "MAGENTA", "ORANGE", "PINK", "RED", "YELLOW"};
        int colorIndex = (int)(Math.random() * colors.length);
        
        // Sets the color based on the random index
        switch (colors[colorIndex]) {
            case "BLACK": StdDraw.setPenColor(StdDraw.BLACK); break;
            case "BLUE": StdDraw.setPenColor(StdDraw.BLUE); break;
            case "CYAN": StdDraw.setPenColor(StdDraw.CYAN); break;
            case "DARK_GRAY": StdDraw.setPenColor(StdDraw.DARK_GRAY); break;
            case "GRAY": StdDraw.setPenColor(StdDraw.GRAY); break;
            case "GREEN": StdDraw.setPenColor(StdDraw.GREEN); break;
            case "LIGHT_GRAY": StdDraw.setPenColor(StdDraw.LIGHT_GRAY); break;
            case "MAGENTA": StdDraw.setPenColor(StdDraw.MAGENTA); break;
            case "ORANGE": StdDraw.setPenColor(StdDraw.ORANGE); break;
            case "PINK": StdDraw.setPenColor(StdDraw.PINK); break;
            case "RED": StdDraw.setPenColor(StdDraw.RED); break;
            case "YELLOW": StdDraw.setPenColor(StdDraw.YELLOW); break;
            default: StdDraw.setPenColor(StdDraw.BLUE); break;
        }

        // Plots the name
        StdDraw.setPenRadius(0.005);
        StdDraw.text(START + (DECADES * 10) + 5, getRank(DECADES - 1), name);

        // Draws lines connecting the points
        for(int i = 0; i < DECADES - 1; i++) {
            int year1 = START + (i * 10);
            int year2 = START + ((i + 1) * 10);
            int rank1 = ranks[i];
            int rank2 = ranks[i + 1];

            // checks to make sure if both ranks are non-zero before drawing
            if(rank1 > 0 && rank2 > 0) {
                StdDraw.line(year1, rank1, year2, rank2);
            }
            // If one end is zero, but other is not, indicate with a point
            else if (rank1 > 0) {
                StdDraw.filledCircle(year1, rank1, 3);
            } else if (rank2 > 0) {
                StdDraw.filledCircle(year2, rank2, 3);
            }
        }

        // Draws points at each decade
        for (int i = 0; i < DECADES; i++) {
            int year = START + (i * 10);
            int rank = ranks[i];
            if (rank > 0) {
                StdDraw.filledCircle(year, rank, 5);
            }
        }

	//brings up the plot window for the user
	StdDraw.show();
    }
}
