package com.brmobsoft.fueltracker;

public class FuelItem {
	private long id;
    private long date;
    private int type;
    private long quantity;
    private long pay;
    private long odometer;
    private String dateString;

    public FuelItem(String dateString, long date, int type, long quantity, long pay, long odometer) {
        setDateString(dateString);
        setDate(date);
        setType(type);
        setQuantity(quantity);
        setPay(pay);
        setOdometer(odometer);
    }
	public FuelItem(long date, int type, long quantity, long pay, long odometer) {
        setDate(date);
        setType(type);
        setQuantity(quantity);
        setPay(pay);
        setOdometer(odometer);
	}
    public FuelItem(long rowId, long date, int type, long quantity, long pay, long odometer) {
        setId(rowId);
        setDate(date);
        setType(type);
        setQuantity(quantity);
        setPay(pay);
        setOdometer(odometer);
    }

	public long getId()					{ return id; }
	public void setId(long id)			{ this.id = id; }

    public long getDate() 			{ return date;	}
    public void setDate(long date) 	{ this.date = date; }

    public String getDateString() 			{ return dateString;	}
    public void setDateString(String dateString) 	{ this.dateString = dateString; }

    public int getType() 			{ return type;	}
    public void setType(int type) 	{ this.type = type; }

    public long getQuantity() 			{ return quantity;	}
    public void setQuantity(long quantity) 	{ this.quantity = quantity; }

    public long getPay() 			{ return pay;	}
    public void setPay(long pay) 	{ this.pay = pay; }

    public long getOdometer() 			{ return odometer;	}
    public void setOdometer(long odometer) 	{ this.odometer = odometer; }
}