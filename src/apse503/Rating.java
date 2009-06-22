package apse503;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Rating extends PersistenceClass {

	public String comment;

	public int rating, rating_id, user_id, method_id;

	public Date dateTime;

	public Rating() {
		this.comment = "";
		this.rating = -1;
		this.rating_id = -1;
		this.user_id = -1;
		this.method_id = -1;
		this.dateTime = null;
	}

	public Rating(String comment, int rating,
			int ratingId, int userId, int methodId,
			Date dateTime) {
		this.comment = comment;
		this.rating = rating;
		this.rating_id = ratingId;
		this.user_id = userId;
		this.method_id = methodId;
		this.dateTime = dateTime;

	}

	public boolean isValid() {
		boolean valid = true;

		valid = valid && comment.length() > 0;
		valid = valid && rating > 0;
		valid = valid && rating_id > 0;
		valid = valid && user_id > 0;
		valid = valid && method_id > 0;
		return valid;
	}
	
	public boolean save() {
		setUpDataSource();
		
		// Don't save if the user is invalid
		//if (!this.isValid()) return false;
		if (this.isSaved()) {
			// TODO implement update code
			String query;
			query  = "UPDATE rating SET ";
			query += "date_time=NOW()";
			query += "rating='" + this.rating+"',";
			query += "comment='" + this.comment+"',";
			query += "method_id='" + this.method_id+"',";
			query += "user_id='" + this.user_id+"',"
			      + "WHERE rating_id=" + this.id +";";
			try {
				
				sql.execute(query);
				ResultSet results = sql.getResultSet();
				closeDataSource();
				if(!results.next()) return false;
				return true;
			} catch (SQLException e) {
				// TODO log exception
				e.printStackTrace();
			}
		} else {
			String query;
			query  = "INSERT INTO rating ";
			query += "(date_time,rating,comment,method_id,user_id) ";
			query += "VALUES ";
			query += "(NOW()," + this.rating + ",'" + this.comment + "'," + this.method_id + "," + this.user_id + ")"; 
			String getid =  "select last_insert_id() as rating_id"; // grab the id of this new user
			try {
				//System.out.println(query);
				// INSERT the user into the table
				sql.execute(query);
				sql.execute(getid);
				ResultSet results = sql.getResultSet();
				
				// Set the attribute to the new ID number
				if(!results.next()) {
					closeDataSource();
					return false;
				}
				this.id = results.getInt("rating_id");
				closeDataSource();
				return true;
			} catch (SQLException e) {
				// TODO log exception
				e.printStackTrace();
			}
		}
		// If the code ever gets to this point, 
		// something went horribly, horribly wrong
		closeDataSource();
		return false;
	}

	public boolean isSaved() {
		// not unsaved means saved
		return this.id != UNSAVED_ID;
	}
	
	//gets all ratings for a method
	public ArrayList<Rating> getAll() {
		setUpDataSource();
		try {
			sql.execute("SELECT * FROM rating where method_id = " + this.method_id);
			ResultSet results = sql.getResultSet();

			ArrayList<Rating> ratings = new ArrayList<Rating>();
			Rating tmp;
			while(results.next())
			{
				tmp = new Rating();				
				tmp.rating_id = results.getInt("rating_id");
				tmp.dateTime = results.getDate("date_time");
				tmp.rating = results.getInt("rating");
				tmp.comment = results.getString("comment");
				tmp.method_id = results.getInt("method_id");
				tmp.user_id = results.getInt("user_id");
				ratings.add(tmp);
			}
			closeDataSource();
			return ratings;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		closeDataSource();
		return null;
	}
	
	//gets the average rating for a method
	public double getAverageRating(){
		
		double average = 0;
		setUpDataSource();
		
		try {
			sql.execute("SELECT AVG(rating) AS average from rating where method_id="+ this.method_id);
			ResultSet results = sql.getResultSet();
			results.next();
			average = results.getDouble("average");
			closeDataSource();
			//if (average != null)
				return average;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		closeDataSource();
		return 0;
	}
	
	public static double roundNearestHalf(double in){
		double afterDecimal = in - Math.floor(in);
		double out = Math.floor(in);
		if(afterDecimal >= 0.75) out = out + 1.0;
		else if(afterDecimal >= 0.25) out = out + 0.5;
		return out;
	}
	
	//gets the total number of ratings for a method
	public int getRatingsCount(){
		
		int total = 0;
		setUpDataSource();
		
		try {
			sql.execute("SELECT COUNT(rating) AS total from rating where method_id="+ this.method_id);
			ResultSet results = sql.getResultSet();
			results.next();
			total = results.getInt("total");
			closeDataSource();
			return total;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		closeDataSource();
		return 0;
	}

	
	public Rating getMyReview(int user_id){
		
		String query = null;
		
		setUpDataSource();
		
		try {
			query = "SELECT rating, comment from rating where method_id="+ this.method_id + " and user_id =" + user_id;
			
			sql.execute(query);
			ResultSet results = sql.getResultSet();
			if(results.next()) {
				this.rating = results.getInt("rating");
				this.comment = results.getString("comment");
				closeDataSource();
				return this;
			}
		} catch (SQLException e) {
			System.err.println("QUERY: " + query);
			e.printStackTrace();
		}

		closeDataSource();
		return null;
	}
}
