package apse503;

import java.sql.ResultSet;
import java.sql.SQLException;
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

	// Create a new rating from an old rating
	// Mostly for testing purposes, but could serve other uses ;-)
	public Rating(Rating originalRating) {

		this.comment = originalRating.comment;
		this.rating = originalRating.rating;
		this.rating_id = originalRating.rating_id;
		this.user_id = originalRating.user_id;
		this.method_id = originalRating.method_id;
		this.dateTime = originalRating.dateTime;
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

	// TODO implement persistence code
	public boolean save() {
		if (null == sql)
			setUpDataSource();

		// Don't save if the rating is invalid
		if (!this.isValid())
			return false;

		if (this.isSaved()) {
			// UPDATE ROW IN TABLE
			// Return true if sql executed properly
		} else {
			String insert;
			insert = "INSERT INTO rating" + "(" + "comment," + "rating,"
					+ "rating_id," + "user_id," + "method_id," + "date_time"
					+ ") " + "VALUES" + "(" + "'" + this.comment + "',"
					+ this.rating + "," + this.user_id + "," + this.rating_id
					+ "," + this.method_id + "," + this.dateTime + ");"
					+ "select last_insert_id() as id;";

			try {
				// INSERT the rating into the table
				sql.execute(insert);
				ResultSet results = sql.getResultSet();

				// Set the attribute to the new ID number
				if (!results.next())
					return false;

				this.id = results.getInt("id");
				return true;
			} catch (SQLException e) {
				// TODO log exception
				e.printStackTrace();
			}
		}
		// If the code ever gets to this point,
		// something went horribly, horribly wrong
		return false;
	}

	public boolean isSaved() {
		// not unsaved means saved
		return this.id != UNSAVED_ID;
	}
}
