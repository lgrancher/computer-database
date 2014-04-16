package com.excilys.mapper;

import java.util.Locale;
import java.util.ResourceBundle;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.i18n.LocaleContextHolder;

public class DateMapper
{	
	public static LocalDate sqlToLocalDate(java.sql.Date sql)
	{
		LocalDate local = null;
		
		if(sql!=null)
		{
			local = new LocalDate(sql);
		}
		
		return local;
	}
	
	public static java.sql.Date localDateToSql(LocalDate local)
	{
		return new java.sql.Date(local.toDate().getTime());
	}
	
	// base vers DTO
	public static String localDateToString(LocalDate local)
	{
		String introduced = "";

		if(local!=null)
		{
			introduced = local.toString();
		}

		return introduced;	
	}
	
	// DTO vers base
	public static LocalDate stringToLocalDate(String chaine)
	{
		LocalDate local = new LocalDate(0);
	
		if(!chaine.equals(""))
		{
			local = new LocalDate(DateMapper.formatWebVersDB(chaine));
		}
		
		return local;
	}
	
	// yyyy-MM-dd vers dd-MM-yyyy ou MM-dd-yyyy
	public static String formatDBVersWeb(String date)
	{
		if(!date.equals(""))
		{
			DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
			LocalDate localDate = dtf.parseLocalDate(date);
	
			Locale locale = LocaleContextHolder.getLocale();
			ResourceBundle properties = ResourceBundle.getBundle("langage", locale);
			String format = properties.getString("date");
			
			date = localDate.toString(format);	
		}
		
		return date;
	}
	
	// dd-MM-yyyy ou MM-dd-yyyy vers yyyy-MM-dd
	public static String formatWebVersDB(String chaine)
	{
		Locale locale = LocaleContextHolder.getLocale();
		ResourceBundle properties = ResourceBundle.getBundle("langage", locale);
		String date = properties.getString("date");
		
		return LocalDate.parse(chaine, DateTimeFormat.forPattern(date)).toString();
	}
}
