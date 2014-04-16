package com.excilys.om;

import java.util.List;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class Page<T> 
{
	private String sort;
	private String search;
	private int currentPage;
	private int noOfPages;
	private int offset;
	private int noOfRecords;
	private List<?> listeElements;
	private final static int RECORDS_PER_PAGES = 14;
	
	public Page()
	{
		this.search = "";
		this.sort = "computer.id";
		this.currentPage = 1;
	}
	
	public Page(String search, String sort, int currentPage, int nbRecords)
	{
		this.search = search;
		this.sort = sort;
		this.currentPage = currentPage;
		
		setNoOfRecords(nbRecords);
		getNoOfPages();
  	   
		if(currentPage>noOfPages)
  	   	{
			currentPage = noOfPages;
  	   	}
  	   
		getOffset();
	}
	
	public void update(int nbRecords)
	{
		setNoOfRecords(nbRecords);
		getNoOfPages();
  	   
		if(currentPage>noOfPages)
  	   	{
			currentPage = noOfPages;
  	   	}
  	   
		getOffset();
	}
	
	/* 
	 * retourne l'url permettant ou non une alert d'erreur
	 * si l'id a existe, avertissement comme quoi le computer a ete supprime
	 * sinon, retour a la page d'accueil, l'utilisateur a ete fouille dans l'url
	*/
	public static String urlVerifyIdExist(String id, String type, long lastId)
	{
		String url="index";
		
		try
		{
			long idComputer = Long.parseLong(id);
			
			if(idComputer>0 && idComputer<=lastId)
			{
				url = "index?erreur="+idComputer+"&type="+type;
			}
		}
		
		catch(NumberFormatException e){}
		
		return url;
	}
	
	public String getSort() 
	{
		return sort;
	}

	public void setSort(String sort) 
	{
		this.sort = sort;
	}

	public String getSearch() 
	{
		return search;
	}

	public void setSearch(String search) 
	{
		this.search = search;
	}
	
	public int getCurrentPage() 
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage) 
	{
		this.currentPage = currentPage;
		getOffset();
	}

	public List<?> getListeElements() 
	{
		return listeElements;
	}

	public void setListeElements(List<?> listeElements2) 
	{
		this.listeElements = listeElements2;
	}

	public int getNoOfPages()
	{
		noOfPages = (int) Math.ceil(getNoOfRecords() * 1.0 / getRecordsPerPages() );
		
		return noOfPages;
	}
		
	public void setNoOfRecords(int nbRecords)
	{
		this.noOfRecords = nbRecords;
	}
	
	public int getNoOfRecords()
	{	
		return noOfRecords; 
	}
	
	public int getOffset()
	{
		offset = (currentPage-1)*getRecordsPerPages();
		
		return offset;
	}
	
	public static int getRecordsPerPages() 
	{
		return RECORDS_PER_PAGES;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPage;
		result = prime * result
				+ ((listeElements == null) ? 0 : listeElements.hashCode());
		result = prime * result + noOfPages;
		result = prime * result + noOfRecords;
		result = prime * result + offset;
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page<?> other = (Page<?>) obj;
		if (currentPage != other.currentPage)
			return false;
		if (listeElements == null) {
			if (other.listeElements != null)
				return false;
		} else if (!listeElements.equals(other.listeElements))
			return false;
		if (noOfPages != other.noOfPages)
			return false;
		if (noOfRecords != other.noOfRecords)
			return false;
		if (offset != other.offset)
			return false;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "Page [sort=" + sort + ", search=" + search + ", currentPage="
				+ currentPage + ", noOfPages=" + noOfPages + ", offset="
				+ offset + ", noOfRecords=" + noOfRecords + ", listeElements="
				+ listeElements + "]";
	}
}
