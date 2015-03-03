import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class StatesHotOrCold {

	public static void main(String[] args) {
		boolean win = false;
		int numGuess = 0;
		String data;
		int  num_verticies;
		String[] edges;
		int guess;
		String guessString = "";
		int randState = randInt(1,51);
		//System.out.println("Rand is: " + randState);
		int distance =-1;
		//create an empty adjacency matrix
		StatesGraph graph;

		/**
		 * Array of facts according to state
		 */
		String[] facts = {
			"Introduced the Mardi Gras to the western world",
			"First discovered in 1741",
			"The saguaro cactus blossom is the official state flower",
			"Contains over 600,000 acres of lakes and 9",
			"700 miles of streams and rivers",
			"More turkeys are raised in this state than in any other state in the United States",
			"The United States federal government owns more than 1/3 of the land in this state",
			"This is the nation's capital",
			"The world's first nuclear powered submarine was built here",
			"The nation's first scheduled steam railroad began in this state",
			"This state has the only metropolitan area in the United States whose borders encompass two national parks",
			"This state joined the Confederacy on January 19, 1861",
			"This state is the most isolated population center on the face of the earth",
			"This state forbids a citizen to give another citizen a box of candy that weighs more than 50 pounds",
			"The world's first Skyscraper was built here",
			"The first long-distance auto race in the U. S. was held May 30, 1911 in this state",
			"Spirit Lake is the largest glacier-made lake in the state",
			"The first woman mayor in the United States was elected in this state",
			"Chevrolet Corvettes are manufactured here",
			"This state was named in honor of King Louis XIV",
			"This is the only state in the United States whose name has one syllable",
			"The United States Naval Academy was founded here on October 10, 1845",
			"This state built the first subway system in the United States",
			"This state is the home of the world's largest cement plant",
			"The stapler was invented in this state",
			"Borden's Condensed Milk was first canned in this state",
			"The first successful parachute jump to be made from a moving airplane was made by Captain Berry in this state in 1912",
			"The average square mile of land contains 1.4 elk, 1.4 pronghorn antelope, and 3.3 deer",
			"This state was once called \"The Great American Desert\"",
			"In this state, the Kangaroo Rat can live its entire life without drinking a drop of liquid",
			"Of the thirteen original colonies, this state was the first to declare its independence from Mother England",
			"Each October this state hosts the world's largest international hot air balloon fiesta",
			"Dairying is this state's most important farming activity with over 18,000 cattle and or calves farms",
			"This state has the highest percent urban population in the U.S. with about 90% of the people living in an urban area",
			"Charles Karault was born and raised here",
			"Milk is the official state beverage",
			"Ermal Fraze invented the pop-top can in this state",
			"The world's first installed parking meter was in this state",
			"This state contains the deepest lake in the United States",
			"The first daily newspaper was published in this state in 1784",
			"Judge Darius Baker imposed the first jail sentence for speeding in an automobile on August 28, 1904 in this state",
			"This state entered the Union on May 23, 1788",
			"The largest underground gold mine is in this state",
			"There were more National Guard soldiers deployed from this state for the Gulf War effort than any other state",
			"This is the only state to have the flags of 6 different nations fly over it",
			"Levan, is \"navel\" spelled backwards. It is so named because it is in the middle of this state",
			"This state has the only state capital without a McDonalds",
			"This state was named for an English queen",
			"This state produces more apples than any other state in the union",
			"This state has the oldest population of any state. The median age is 40",
			"This state is home to 7,446 streams and rivers",
			"This state is home to the first official National Park"
		};
		
		try{
			 BufferedReader in = new BufferedReader(new FileReader(new File("../stateAdjacencyList.txt")));
			 data = in.readLine();
			 num_verticies = Integer.parseInt(data);
			 data = in.readLine();

			 graph = new StatesGraph(num_verticies);
			 data = in.readLine();
			 
			 while(data != null){
				 edges = data.split(" ");
				int v = Integer.parseInt(edges[0].trim());
				int e = Integer.parseInt(edges[1].trim());
				 graph.setEdge(v,e);
				 data = in.readLine();
			 }
			 System.out.println("All Items Added");
			 in.close();
			 Scanner keyboard = new Scanner(System.in);
			
				System.out.println("Enter a state name. ex. Arizona");
				guessString = keyboard.nextLine();
				guess = getGuessInt(guessString);
				while(guess == 0 ){
					System.out.println("That is not a valid state, enter a state name. ex. Arizona");
					guessString = keyboard.nextLine();
					guess = getGuessInt(guessString);
				}		
				numGuess++;
			while (win == false){
				if (guess == randState)
				{
					win = true;
				}else{

					if(guess == 7734){
						System.out.println("Hint: " + facts[randState - 1]);
					} else {

						distance = getDistance(guess, randState, graph);
						switch(distance){
						case 1:
							System.out.println("You are HOT! " + distance);
							break;
						case 2:
							System.out.println("You are warm " + distance);
							break;
						case 3:
							System.out.println("You are cold " + distance);
							break;
						default:
							System.out.println("You are Freezing! " + distance);
							break;
						}
					}
					
					System.out.println("Enter a state name. ex. Arizona (\"hint\" if you need help)");
					guessString = keyboard.nextLine();
					guess = getGuessInt(guessString);
					while(guess == 0 ){
						System.out.println("That is not a valid state, enter a state name. ex. Arizona");
						guessString = keyboard.nextLine();
						guess = getGuessInt(guessString);
					}		
					numGuess++;
				}
			}
			if(win){
				System.out.println("You Win!!!!!!!");
				System.out.println("It took you "+numGuess+" Guesses");
				highScore(numGuess);
			}
				keyboard.close();
		}catch(IOException e){
			e.printStackTrace();
		}	
	}
	
	//returns the minimum number of connections between 2 states 
	public static int getDistance(int guess, int actual, StatesGraph graph){
		List<Integer> lvl1, lvl2, lvl3;
		lvl1= graph.getEdge(guess);
		//Check 1st level depth
		if(lvl1.contains(actual)){		
			return 1;
		}else {
			//check second level depth
			for(int i = 0; i < lvl1.size(); i++){
				lvl2 = graph.getEdge(lvl1.get(i));
				if(lvl2.contains(actual)){
					return 2;
				}
			}
			//Check 3rd level depth
			for(int i = 0; i < lvl1.size(); i++){
				lvl2 = graph.getEdge(lvl1.get(i));
				for(int x = 0; x < lvl2.size(); x++){
					lvl3 = graph.getEdge(lvl2.get(x));
					if(lvl3.contains(actual)){
						return 3;
					}
				}
			}
		}
			return 4;
	}
	
	//Generates a random integer for the random states
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	//Displays the high score list and updates it if the user is in the top 10
	public static void highScore(int score){
		String newName ="";
		if( score <11){
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Congratulations! You got a high score!");
			System.out.println("Enter your Name!");
			newName = keyboard.nextLine();
			
			try{
				String line = "";
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("scoreList.temp")));
				BufferedReader in = new BufferedReader(new FileReader(new File("../scoreList.txt")));
				int lineNum = 1;
				String temp = "";
				while((line = in.readLine()) != null && lineNum <11){
					//replace the line with the new high score
					 if (lineNum == score) {
						 temp = line;
						 line = newName;
						 writer.println(line);
						 lineNum++;
						 //push the rest of the names down one
						 while((line = in.readLine()) != null && lineNum <11){
						   writer.println(temp);
						   temp=line;
						 }
					 }else{
					 writer.println(line);
					   lineNum++;
					 }
				}
					writer.close();
					in.close();
					// ... and finally ...
					File realName = new File("scoreList.txt");
					realName.delete(); // remove the old file
					// Rename temp file
					new File("scoreList.temp").renameTo(realName); // Rename temp file
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		try{
		 BufferedReader in = new BufferedReader(new FileReader(new File("scoreList.txt")));
		 System.out.println("The High Scores are: ");
		 for(int i = 1; i < 11; i++){
			 String data = in.readLine();
			 System.out.println("#" +i+" " + data);
		 }
		 in.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//Converts users string guess into an integer for checking
	public static int getGuessInt(String state){
		int num = 1;

			if(new String("hint").equals(state)){
				return 7734;
			}

		try{
			@SuppressWarnings("resource")
			BufferedReader names = new BufferedReader(new FileReader(new File("stateNames.txt")));
			String line = "";
			
				while((line = names.readLine()) !=null){
					if(line.equalsIgnoreCase(state)){
						return num;
					}else{
						num++;
					}
				}
			names.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return 0;
	}
}
