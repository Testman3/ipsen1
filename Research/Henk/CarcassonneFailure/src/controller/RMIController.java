package controller;

import java.util.ArrayList;

import model.RMIInterface;
import model.Speler;

public class RMIController implements RMIInterface{

	private ArrayList<Speler> alSpelers = new ArrayList<Speler>();
	
	
	
	@Override
	public void pakKaart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void plaatsKaart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void plaatsHorigen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void volgendeTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPlayer(Speler speler) {
		alSpelers.add(speler);
		
	}

	@Override
	public void removePlayer(int ID) {
		System.out.println(alSpelers.get(ID).getNaam() + " has tried to remove themselves from the ArrayList");
		alSpelers.set(ID, null);
		
		
	}

	//Onderstaande operaties worden uitgevoerd in een try blok zodat we de nullpointerexception kunnen opvangen en afhandelen
	@Override
	public String playerList() {
		String spelerlijst ="";
		for (int i = 0; i < alSpelers.size(); i++) {
			try {
			spelerlijst = spelerlijst + alSpelers.get(i).getNaam() + " ";
			} catch(java.lang.NullPointerException e1){
				System.out.println("null found, skipping this index position");;}
		}
		return spelerlijst;
	}

	
	//Onderstaande operaties worden uitgevoerd in een try blok zodat we de nullpointerexception kunnen opvangen en afhandelen
	@Override
	public int getPlayerID(String naam){
		
		int playerID = 3;
		for (int i = 0; i < alSpelers.size(); i++){
			try{

			
			if (alSpelers.get(i).getNaam().contains(naam)){
				playerID = i;
				System.out.println(playerID);
				return playerID;
			}
			else{System.out.println("fuck me");
		}
			System.out.println(alSpelers.get(i).getNaam());
			}catch (java.lang.NullPointerException e1){
				System.out.println("null found, skipping this index position");
			}
			
	}
		return playerID;

}
}
