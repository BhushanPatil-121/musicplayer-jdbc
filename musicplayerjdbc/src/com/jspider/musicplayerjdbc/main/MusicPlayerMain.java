package com.jspider.musicplayerjdbc.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.jspider.musicplayerjdbc.songoperation.SongOperations;

public class MusicPlayerMain {

	static boolean loop = true;
	static int choose, choose1, choose2, choose3;

	public static void main(String[] args) {
		MusicPlayerMain m = new MusicPlayerMain();
		// main method
		while (loop) {
			m.selectOptions();
		}
	}

	// displaying option
	public void selectOptions() {
		Scanner inputScanner = new Scanner(System.in);

		try {
			System.out.println("\nSelect Options");
			System.out.println(
					"|-----------------|"
				+ "\n|1.Play Song      |"
				+ "\n|2.Add/Remove Song|"
				+ "\n|3.Update Song    |"
				+ "\n|4.Exit           |"
				+ "\n|-----------------|");
			choose = inputScanner.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Please Enter Number");
		}

		// case 1 song options
		switch (choose) {
		case 1: {
			boolean stop = true;
			while (stop) {
				try {
					System.out.println("choose option");
					System.out
							.println("1.Choose Song From Given List \n2.Play all songs \n3.Play Random Song\n4.Go Back\n");
					choose1 = inputScanner.nextInt();
					stop = false;
				} catch (InputMismatchException e) {
					System.out.println("Please Enter Number\n");
					inputScanner.next();
				}
			}

			switch (choose1) {
			case 1:
				SongOperations.chooseToPlaySong();
				break;
			case 2:
				SongOperations.playAllSong();
				break;
			case 3:
				SongOperations.playRandomSong();
				break;
			case 4:
				System.out.println("Going Back.....\n\n");
				break;

			default:
				System.out.println("Invalid Choice.\n");
				break;
			}
		}
			break;

		// case 2 add/remove songs option
		case 2: {
			boolean stop = true;
			while (stop) {
				try {
					System.out.println("choose option");
					System.out.println("1.Add Song \n2.Remove songs \n3.Remove All Songs \n4.Go Back\n");
					choose2 = inputScanner.nextInt();
					stop = false;
				} catch (InputMismatchException e) {
					System.out.println("Please Enter Number\n");
					inputScanner.next();
				}
			}

			switch (choose2) {
			case 1:
				SongOperations.addSong();
				break;
			case 2:
				SongOperations.removeSong();
				break;
			case 3:
				SongOperations.removeAllSong();
				break;
			case 4:
				System.out.println("Going Back.....\n\n");
				break;

			default:
				System.out.println("Invalid Choice\n");
				break;
			}
		}
			break;

		// case 3 updating song
		case 3: {
			boolean stop = true;
			while (stop)
				try {
			
						System.out.println("\nchoose options ");
						System.out.println("1.Update Song Name\n2.Update Singer Name\n3.Update Movie Name\n4.Update Song Duration\n5.Update Whole Song\n6.Go Back\n");
						choose3 = inputScanner.nextInt();
						switch (choose3) {
						
						case 1:
							SongOperations.updateSongName();
							break;
						case 2:
							SongOperations.updateSingerName();
							break;
						case 3:
							SongOperations.updateMovieName();
							break;
						case 4:
							SongOperations.updateSongDuration();
							break;
						case 5:
							SongOperations.updateSong();
							break;
						case 6:
							System.out.println("Going Back.....\n");
							stop = false;
							break;

						default:
							System.out.println("Invalid Choice\n");
							break;
						}
					
				} catch (InputMismatchException e) {
					System.out.println("Please Enter Number\n");
					inputScanner.next();
				}

		}
			break;

		// case 4 exiting from menu
		case 4:
			System.out.println("Exting.....\nThank You ");
			loop = false;
			inputScanner.close();
			break;

		default:
			System.out.println("Invalid Choice\n");
			break;
		}
	}

}
