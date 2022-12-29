package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.Response;
import common.Transaction;
import enums.RegionEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.Offer;
import logic.Subscriber;

public class OffersQuaries {


	public static void getOffers(Transaction msg) {
		List<String> Alist = new ArrayList<>();
		if (msg instanceof Transaction) {
			Object obj = msg.getData();
			RegionEnum region = RegionEnum.valueOf(obj.toString());
			try {
				ResultSet rs = dbController.getInstance()
						.executeQuery("SELECT id, name, price, discount,isActive FROM ekurt.offers WHERE region='" + region.toString() + "'");
				System.out.println("Number of rows: " + rs.getRow());
				if(!(rs.next())) {
					msg.setResponse(Response.FAILED_TO_GET_OFFERS);
					return;
				}
				if (rs == null)
					msg.setResponse(Response.FAILED_TO_GET_OFFERS);
				else {
					    rs.previous();
						List<Offer> offers = new ArrayList<>();
						while (rs.next()) {
						  String id = rs.getString("id");
						  String name = rs.getString("name");
						  String price = rs.getString("price");
						  String discount = rs.getString("discount");
						  int isActive = rs.getInt("isActive");
						  if(isActive==1) {
							  Offer offer = new Offer(id, name, price, discount,"ON");
							  offers.add(offer);
							  }
						  else {
						  Offer offer = new Offer(id, name, price, discount,"OFF");
						  offers.add(offer);
						  }
						}
				msg.setResponse(Response.FOUND_OFFERS);
				msg.setData(offers);
				rs.close();}
				}catch (SQLException e) {
				e.printStackTrace();
				msg.setResponse(Response.FAILED_TO_GET_OFFERS);
				return;
			}
		} else
			msg.setResponse(Response.FAILED_TO_GET_OFFERS);
	}
}
