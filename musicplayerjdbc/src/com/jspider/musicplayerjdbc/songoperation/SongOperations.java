package com.jspider.musicplayerjdbc.songoperation;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

import com.jspider.musicplayerjdbc.songs.Songs;

public class SongOperations {
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	private static FileReader fileReader;
	private static Properties properties;
	private static int result;
	private static String query;
	private static Scanner scanner = new Scanner(System.in);
	private static String filePath = "D:\\WEJA1\\musicplayerjdbc\\resources\\db_info.properties";
	

	private static void openConnection() {
		try {
			fileReader = new FileReader(filePath);
			properties = new Properties();
			properties.load(fileReader);
			Class.forName(properties.getProperty("driverPath"));
			connection = DriverManager.getConnection(properties.getProperty("dburl"), properties);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addSong() {
		try {
			String songnamString;
			boolean loop=true;
			int resultTotal=0;
			openConnection();
			while (loop) {
				query = "insert into musicplayer.songs values (?,?,?,?)";
				preparedStatement = connection.prepareStatement(query);
				Songs songs = new Songs();
				System.out.println("\nAdding Song.....");
				boolean stop = true;
				System.out.print("Enter Song Name('0' to go back):- ");
				while (stop) {
					songnamString=scanner.nextLine().toLowerCase();
					songs.setSongName(songnamString);
					if (songs.getSongName().equals("0")) {
						if (result>0) {
							System.out.println(resultTotal + " Song Added Sucessfully ");
						}
						System.out.println("Going Back..");
						return;
					}
					if (songs.getSongName().trim().length() <= 0) {
						continue;
					} else {
						stop = false;
					}
				}
				preparedStatement.setString(1, songs.getSongName());
				System.out.print("Enter Singer Name:- ");
				songs.setSingerName(scanner.nextLine().toLowerCase());
				preparedStatement.setString(2, songs.getSingerName());
				System.out.print("Enter Movie Name:- ");
				songs.setMovieName(scanner.nextLine().toLowerCase());
				preparedStatement.setString(3, songs.getMovieName());
				System.out.print("Enter Song Duration :- ");
				songs.setSongDuration(scanner.nextDouble());
				preparedStatement.setDouble(4, songs.getSongDuration());
				result = preparedStatement.executeUpdate();
				resultTotal=resultTotal+result;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public static void removeSong() {
		try {
			openConnection();
			query = "select * from musicplayer.songs";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			Songs songs = new Songs();
			System.out.println("\nSong List...\n");
			while (resultSet.next()) {
				songs.setSongName(resultSet.getString(1));
				songs.setSingerName(resultSet.getString(2));
				songs.setMovieName(resultSet.getNString(3));
				songs.setSongDuration(resultSet.getDouble(4));
				System.out.println(songs);
				System.out.println();
			}
			if (songs.getSingerName() == null) {
				System.out.println("No Song Present In list add song to remove");
				return;
			}
			System.out.println("Removing Song....");
			query = "delete from musicplayer.songs where songname in (?)";

			preparedStatement = connection.prepareStatement(query);
			System.out.print("Enter Song Name('0' to go back):- ");
			String nameString = scanner.nextLine().toLowerCase();
			preparedStatement.setString(1, nameString);
			if (nameString.equals("0")) {
				System.out.println("Going Back..");
				return;
			}
			result = preparedStatement.executeUpdate();
			
			if (result == 0) {
				System.out.println("Song Not Found In List");
			} else {
				System.out.println(result + " Song Remove Sucessfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	
	public static void removeAllSong() {
		try {
			openConnection();
			query = "truncate  musicplayer.songs";
			preparedStatement = connection.prepareStatement(query);
			System.out.print("Enter 1 To Remove All songs('0' to go back):- ");
			int input = scanner.nextInt();
			if (input==0) {
				System.out.println("Going Back..");
				return;
			}else {
				result = preparedStatement.executeUpdate();
				System.out.println("Removing All Songs");
				System.out.println("Song Removed Sucessfully");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	public static void chooseToPlaySong() {
		try {
			openConnection();
			query = "select * from musicplayer.songs";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			Songs songs = new Songs();
			System.out.println("\nSong List...\n");
			while (resultSet.next()) {
				songs.setSongName(resultSet.getString(1));
				songs.setSingerName(resultSet.getString(2));
				songs.setMovieName(resultSet.getNString(3));
				songs.setSongDuration(resultSet.getDouble(4));
				System.out.println(songs);
				System.out.println();
			}
			if (songs.getSingerName() == null) {
				System.out.println("No Song Present In list add song to play");
				return;
			}
			
			query = "select * from musicplayer.songs where songname = ?";

			preparedStatement = connection.prepareStatement(query);
			System.out.print("Enter Song Name('0' to go back):- ");
			String nameString = scanner.nextLine().toLowerCase();
			preparedStatement.setString(1, nameString);
			if (nameString.equals("0")) {
				System.out.println("Going Back..");
				return;
			}
			Songs songs1 = new Songs();
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("\nPlaying Song....");
				System.out.println(resultSet.getString(1));
				songs1.setSongName(resultSet.getString(1));
				songs1.setSingerName(resultSet.getString(2));
				songs1.setMovieName(resultSet.getNString(3));
				songs1.setSongDuration(resultSet.getDouble(4));
				System.out.println(songs1);
				break;
			}
			if (songs1.getSongName()==null) {
				System.out.println("Song Not Found");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("Song Not Found");
		} finally {
			closeConnection();
		}

	}
	
	public static void playAllSong() {
		try {
			openConnection();
			query = "select * from musicplayer.songs";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			Songs songs = new Songs();
			System.out.println("\nSong List...\n");
			while (resultSet.next()) {
				songs.setSongName(resultSet.getString(1));
				System.out.println("Playing "+songs.getSongName()+" song....");
				songs.setSingerName(resultSet.getString(2));
				songs.setMovieName(resultSet.getNString(3));
				songs.setSongDuration(resultSet.getDouble(4));
				System.out.println(songs);
				System.out.println();
				Thread.sleep(5000);	
			}
			if (songs.getSingerName() == null) {
				System.out.println("No Song Present In list add song to play");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeConnection();
		}
	}

	public static void playRandomSong() {
		try {
			openConnection();
			query = "select * from musicplayer.songs order by songname";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			Songs songs = new Songs();
			while (resultSet.next()) {
				songs.setSongName(resultSet.getString(1));
				System.out.println("Playing "+songs.getSongName()+" song....");
				songs.setSingerName(resultSet.getString(2));
				songs.setMovieName(resultSet.getNString(3));
				songs.setSongDuration(resultSet.getDouble(4));
				System.out.println(songs);
				System.out.println();
				break;	
			}
			if (songs.getSingerName() == null) {
				System.out.println("No Song Present In list add song to play");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeConnection();
		}
	}
	
	public static void updateSongName() {
		try {
			String songNameString="",newSongNameString=" ";
			openConnection();
			query = "select * from musicplayer.songs";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			Songs songs = new Songs();
			System.out.println("\nSong List...\n");
			while (resultSet.next()) {
				songs.setSongName(resultSet.getString(1));
				songs.setSingerName(resultSet.getString(2));
				songs.setMovieName(resultSet.getNString(3));
				songs.setSongDuration(resultSet.getDouble(4));
				System.out.println(songs);
				System.out.println();
			}
			if (songs.getSingerName() == null) {
				System.out.println("No Song Present In list add song to update");
				return;
			}
			query = "update musicplayer.songs set songname=? where songname=?";
			preparedStatement = connection.prepareStatement(query);
			System.out.println("Updating Song Name.....");
			boolean stop=true,stop1 = true;
			while (stop) {
				System.out.print("Enter Song Name('0' to go back):- ");
				songNameString= scanner.nextLine().toLowerCase();
				songs.setSongName(songNameString);
				if (songs.getSongName().equals("0")) {
					System.out.println("Going Back..");
					return;
				}
				if (songs.getSongName().trim().length() <= 0) {
					System.out.println("(Song Name Can Not Be Null) Enter Song Name Again..");
					continue;
				} else {
					stop = false;
				}
			}
			while (stop1) {
				System.out.print("Enter New Song Name('0' to go back):- ");
				newSongNameString= scanner.nextLine().toLowerCase();
				songs.setSongName(newSongNameString);
				if (songs.getSongName().equals("0")) {
					System.out.println("Going Back..");
					return;
				}
				if (songs.getSongName().trim().length() <= 0) {
					System.out.println("(Song Name Can Not Be Null) Enter Song Name Again..");
					continue;
				} else {
					stop1 = false;
				}
			}
			
			preparedStatement.setString(1, newSongNameString);
			preparedStatement.setString(2, songNameString);
			result = preparedStatement.executeUpdate();
			if (result == 0) {
				System.out.println("Song Not Found In List");
			}
			else {
				System.out.println(result + " Song Name Updated Sucessfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	
	public static void updateSingerName() {
		try {
			String songNameString="";
			openConnection();
			query = "select * from musicplayer.songs";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			Songs songs = new Songs();
			System.out.println("\nSong List...\n");
			while (resultSet.next()) {
				songs.setSongName(resultSet.getString(1));
				songs.setSingerName(resultSet.getString(2));
				songs.setMovieName(resultSet.getNString(3));
				songs.setSongDuration(resultSet.getDouble(4));
				System.out.println(songs);
				System.out.println();
			}
			if (songs.getSingerName() == null) {
				System.out.println("No Song Present In list add song to update");
				return;
			}
			query = "update musicplayer.songs set singername=? where songname = ?";
			preparedStatement = connection.prepareStatement(query);
			System.out.println("Updating Singer Name.....");
			boolean stop = true;
			while (stop) {
				System.out.print("Enter Song Name('0' to go back):- ");
				songNameString= scanner.nextLine().toLowerCase();
				songs.setSongName(songNameString);
				if (songs.getSongName().equals("0")) {
					System.out.println("Going Back..");
					return;
				}
				if (songs.getSongName().trim().length() <= 0) {
					System.out.println("(Song Name Can Not Be Null) Enter Song Name Again..");
					continue;
				} else {
					stop = false;
				}
			}
			System.out.print("Enter New Singer Name:- ");
			String newSingerNameString=scanner.nextLine();
			preparedStatement.setString(1, newSingerNameString);
			preparedStatement.setString(2, songNameString);
			result = preparedStatement.executeUpdate();
			if (result == 0) {
				System.out.println("Song Not Found In List");
			} else {
				System.out.println(result + " Singer Name Updated Sucessfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	public static void updateMovieName() {
		try {
			String songNameString="";
			openConnection();
			query = "select * from musicplayer.songs";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			Songs songs = new Songs();
			System.out.println("\nSong List...\n");
			while (resultSet.next()) {
				songs.setSongName(resultSet.getString(1));
				songs.setSingerName(resultSet.getString(2));
				songs.setMovieName(resultSet.getNString(3));
				songs.setSongDuration(resultSet.getDouble(4));
				System.out.println(songs);
				System.out.println();
			}
			if (songs.getSingerName() == null) {
				System.out.println("No Song Present In list add song to update");
				return;
			}
			query = "update musicplayer.songs set moviename=? where songname = ?";
			preparedStatement = connection.prepareStatement(query);
			System.out.println("Updating Movie Name.....");
			boolean stop = true;
			while (stop) {
				System.out.print("Enter Song Name('0' to go back):- ");
				songNameString= scanner.nextLine().toLowerCase();
				songs.setSongName(songNameString);
				if (songs.getSongName().equals("0")) {
					System.out.println("Going Back..");
					return;
				}
				if (songs.getSongName().trim().length() <= 0) {
					System.out.println("(Song Name Can Not Be Null) Enter Song Name Again..");
					continue;
				} else {
					stop = false;
				}
			}
			System.out.print("Enter New Movie Name:- ");
			String newMovieNameString=scanner.nextLine();
			preparedStatement.setString(1, newMovieNameString);
			preparedStatement.setString(2, songNameString);
			result = preparedStatement.executeUpdate();
			if (result == 0) {
				System.out.println("Song Not Found In List");
			} else {
				System.out.println(result + " Movie Name Updated Sucessfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	
	public static void updateSongDuration() {
		try {
			String songNameString="";
			openConnection();
			query = "select * from musicplayer.songs";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			Songs songs = new Songs();
			System.out.println("\nSong List...\n");
			while (resultSet.next()) {
				songs.setSongName(resultSet.getString(1));
				songs.setSingerName(resultSet.getString(2));
				songs.setMovieName(resultSet.getNString(3));
				songs.setSongDuration(resultSet.getDouble(4));
				System.out.println(songs);
				System.out.println();
			}
			if (songs.getSingerName() == null) {
				System.out.println("No Song Present In list add song to update");
				return;
			}
			query = "update musicplayer.songs set songduration=? where songname = ?";
			preparedStatement = connection.prepareStatement(query);
			System.out.println("Updating Movie Name.....");
			boolean stop = true;
			while (stop) {
				System.out.print("Enter Song Name('0' to go back):- ");
				songNameString= scanner.nextLine().toLowerCase();
				songs.setSongName(songNameString);
				if (songs.getSongName().equals("0")) {
					System.out.println("Going Back..");
					return;
				}
				if (songs.getSongName().trim().length() <= 0) {
					System.out.println("(Song Name Can Not Be Null) Enter Song Name Again..");
					continue;
				} else {
					stop = false;
				}
			}
			System.out.print("Enter New Song Duration :- ");
			String newSongDurationString=scanner.nextLine();
			preparedStatement.setString(1, newSongDurationString);
			preparedStatement.setString(2, songNameString);
			result = preparedStatement.executeUpdate();
			if (result == 0) {
				System.out.println("Song Not Found In List");
			} else {
				System.out.println(result + " Song Duration Updated Sucessfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	
	public static void updateSong() {
		try {
			String songNameString="";
			openConnection();
			query = "select * from musicplayer.songs";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			Songs songs = new Songs();
			System.out.println("\nSong List...\n");
			while (resultSet.next()) {
				songs.setSongName(resultSet.getString(1));
				songs.setSingerName(resultSet.getString(2));
				songs.setMovieName(resultSet.getNString(3));
				songs.setSongDuration(resultSet.getDouble(4));
				System.out.println(songs);
				System.out.println();
			}
			if (songs.getSingerName() == null) {
				System.out.println("No Song Present In list add song to update");
				return;
			}
			query = "update musicplayer.songs set songname=?, singername=?, moviename=?,songduration=? where songname = ?";
			preparedStatement = connection.prepareStatement(query);
			System.out.println("Updating Song.....");
			boolean stop = true;
			while (stop) {
				System.out.print("Enter Song Name('0' to go back):- ");
				songNameString= scanner.nextLine().toLowerCase();
				songs.setSongName(songNameString);
				if (songs.getSongName().equals("0")) {
					System.out.println("Going Back..");
					return;
				}
				if (songs.getSongName().trim().length() <= 0) {
					System.out.println("(Song Name Can Not Be Null) Enter Song Name Again..");
					continue;
				} else {
					stop = false;
				}
			}
			
			preparedStatement.setString(1, songs.getSongName());
			System.out.print("Enter Singer Name:- ");
			songs.setSingerName(scanner.nextLine().toLowerCase());
			preparedStatement.setString(2, songs.getSingerName());
			System.out.print("Enter Movie Name:- ");
			songs.setMovieName(scanner.nextLine().toLowerCase());
			preparedStatement.setString(3, songs.getMovieName());
			System.out.print("Enter Song Duration :- ");
			songs.setSongDuration(scanner.nextDouble());
			preparedStatement.setDouble(4, songs.getSongDuration());
			preparedStatement.setString(5, songNameString);
			result = preparedStatement.executeUpdate();
			if (result == 0) {
				System.out.println("Song Not Found In List");
			} else {
				System.out.println(result + " Song Remove Sucessfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	
}
