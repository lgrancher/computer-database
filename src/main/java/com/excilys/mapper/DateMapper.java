package com.excilys.mapper;

import org.joda.time.LocalDate;

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
	
	public static String localDateToString(LocalDate local)
	{
		String introduced = "";
		
		if(local!=null)
		{
			introduced = local.toString();
		}
		
		return introduced;		
	}
	
	public static LocalDate stringToLocalDate(String chaine)
	{
		LocalDate local = new LocalDate(0);
		
		if(chaine!="")
		{
			local = new LocalDate(chaine);
		}
		
		return local;
	}
}
