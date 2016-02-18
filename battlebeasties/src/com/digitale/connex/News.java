package com.digitale.connex;

public class News  {
	   private String   _title;
	   private String  _text;
	   private String _date;
	
	   
	   public News(  ) {
	   }
	   
	   public News(String title, 
	              String  text,
	              String date)
	              {
	      set_title(title);
	      set_text(text);
	      set_date(date);
	   }

	public void set_title(String _title) {
		this._title = _title;
	}

	public String get_title() {
		return _title;
	}

	public void set_text(String _text) {
		this._text = _text;
	}

	public String get_text() {
		return _text;
	}

	public void set_date(String _date) {
		this._date = _date;
	}

	public String get_date() {
		return _date;
	}

	}