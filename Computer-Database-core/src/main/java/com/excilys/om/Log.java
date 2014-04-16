package com.excilys.om;

import org.joda.time.LocalDate;

public class Log 
{
	private Long id;
	private Type typeLog;
	private String operation;
	private LocalDate dateLog;
	public enum Type {create, retrieve, find, delete, update, size, error};
	
	public static class Builder
	{
		Log log;
		
		private Builder()
		{
			log = new Log();
		}
		
		public Builder id(Long id)
		{
			if(id!=null)
				this.log.id=id;
			
			return this;
		}
		
		public Builder typeLog(Type typeLog)
		{
			this.log.typeLog = typeLog;
			return this;
		}
		
		public Builder operation(String operation)
		{
			this.log.operation = operation;
			return this;
		}
		
		public Builder dateLog(LocalDate dateLog)
		{
			this.log.dateLog = dateLog;
			return this;
		}
		
		public Log build()
		{
			return this.log;
		}
	}
	
	public Log(){}

	public static Builder builder() 
	{
	   return new Builder();
	}
	
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Type getTypeLog() 
	{
		return typeLog;
	}

	public void setTypeLog(Type typeLog) 
	{
		this.typeLog = typeLog;
	}

	public String getOperation() 
	{
		return operation;
	}

	public void setOperation(String operation) 
	{
		this.operation = operation;
	}

	public LocalDate getDateLog() 
	{
		return dateLog;
	}

	public void setDateLog(LocalDate dateLog) 
	{
		this.dateLog = dateLog;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateLog == null) ? 0 : dateLog.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((typeLog == null) ? 0 : typeLog.hashCode());
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
		Log other = (Log) obj;
		if (dateLog == null) {
			if (other.dateLog != null)
				return false;
		} else if (!dateLog.equals(other.dateLog))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (typeLog == null) {
			if (other.typeLog != null)
				return false;
		} else if (!typeLog.equals(other.typeLog))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "Log [id=" + id + ", typeLog=" + typeLog + ", operation="
				+ operation + ", dateLog=" + dateLog + "]";
	}
}
