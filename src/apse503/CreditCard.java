package apse503;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreditCard extends PersistenceClass {

	public String card_type, card_number, card_holder_name;

	public int card_id, card_expiry_month, card_expiry_year, card_code;

	public CreditCard() {
		this.card_type = "";
		this.card_number = "";
		this.card_holder_name = "";
		this.card_id = -1;
		this.card_expiry_month = -1;
		this.card_expiry_year = -1;
		this.card_code = -1;

	}

	public CreditCard(String cardNumber, String cardType,
			String cardHolderName, int cardId, int cardExpiryMonth,
			int cardExpiryYear, int cardCode) {
		this.card_number = cardNumber;
		this.card_type = cardType;
		this.card_holder_name = cardHolderName;
		this.card_id = cardId;
		this.card_expiry_month = cardExpiryMonth;
		this.card_expiry_year = cardExpiryYear;
		this.card_code = cardCode;

	}

	public boolean isValid() {
		setUpDataSource();

		try {
			sql.execute("SELECT * FROM credit_cards WHERE card_type = "
					+ this.card_type + " and card_number = " + this.card_number
					+ " and card_holder_name = " + this.card_holder_name
					+ " and card_expiry_month = " + this.card_expiry_month
					+ " and card_expiry_year = " + this.card_expiry_year
					+ " and card_code = " + this.card_code);
			ResultSet results = sql.getResultSet();
			if (!results.next()) {
				closeDataSource();
				return false;
			} else {
				closeDataSource();
				return true;
			}
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		closeDataSource();
		return false;
	}

	// TODO implement persistence code
	public boolean save() {

		return false;
	}

	public ArrayList<CreditCard> getAll() {
		setUpDataSource();
		try {
			sql.execute("SELECT * FROM credit_cards");
			ResultSet results = sql.getResultSet();

			ArrayList<CreditCard> cc = new ArrayList<CreditCard>();
			CreditCard tmp;
			while (results.next()) {
				tmp = new CreditCard();
				tmp.id = results.getInt("card_id");
				tmp.card_type = results.getString("card_type");
				tmp.card_number = results.getString("card_number");
				tmp.card_holder_name = results.getString("card_holder_name");
				tmp.card_expiry_month = results.getInt("card_expiry_month");
				tmp.card_expiry_year = results.getInt("card_expiry_year");
				tmp.card_code = results.getInt("card_code");
				cc.add(tmp);
			}
			closeDataSource();
			return cc;
		} catch (SQLException e) {
			// TODO log exception
			e.printStackTrace();
		}
		closeDataSource();
		return null;
	}
}
