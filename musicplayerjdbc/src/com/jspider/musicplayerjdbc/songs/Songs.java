package com.jspider.musicplayerjdbc.songs;

public class Songs {
//		private int songId;
		private String songName;
		private String singerName;
		private String movieName;
		private double songDuration;

		
//
//		public int getSongId() {
//			return songId;
//		}
//
//		public void setId(int songId) {
//			this.songId = songId;
//		}

		public String getSongName() {
			return songName;
		}

		public void setSongName(String songName) {
			this.songName = songName;
		}

		public String getSingerName() {
			return singerName;
		}

		public void setSingerName(String singerName) {
			this.singerName = singerName;
		}

		public String getMovieName() {
			return movieName;
		}

		public void setMovieName(String movieName) {
			this.movieName = movieName;
		}

		public double getSongDuration() {
			return songDuration;
		}

		public void setSongDuration(double songDuration) {
			this.songDuration = songDuration;
		}


		@Override
		public String toString() {
			return "Song Name: " + this.songName + "\nSong Duration: " + this.songDuration + "\nSinger Name: "
					+ this.singerName + "\nMovie Name: " + this.movieName;
		}

}
