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
		
		try{
			 BufferedReader in = new BufferedReader(new FileReader(new File("stateAdjacencyList.txt")));
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
					
					System.out.println("Enter a state name. ex. Arizona");
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
				BufferedReader in = new BufferedReader(new FileReader(new File("scoreList.txt")));
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
