$.formUtils.addValidator
({
	name : 'supIntroduced',
	validatorFunction : function(value, $el, config, language, $form) 
	{
		var local = document.getElementById("local").value;
		
		if($('#introducedDate').val() === "")
		{
			return true;
		}
			
		var date1 = new Date();
		date1.setFullYear(value.substring(6,10));
		
		var date2 = new Date();
		date2.setFullYear($('#introducedDate').val().substring(6,10));
		
		if(local==="fr")
		{
			date1.setMonth(value.substring(3,5));
			date1.setDate(value.substring(0,2));
			date2.setMonth($('#introducedDate').val().substring(3,5));
			date2.setDate($('#introducedDate').val().substring(0,2));
		}
		
		else
		{
			date1.setMonth(value.substring(0,2));
			date1.setDate(value.substring(3,5));
			date2.setMonth($('#introducedDate').val().substring(0,2));
			date2.setDate($('#introducedDate').val().substring(3,5));
		}
		
		return (date1 >= date2);
	},
});
	
$.formUtils.addValidator
({		
	name : 'infDiscontinued',
	validatorFunction : function(value, $el, config, language, $form) 
	{
		var local = document.getElementById("local").value;
	
		if($('#discontinuedDate').val() === "")
		{
			return true;
		}
			
		var date1 = new Date();
		date1.setFullYear(value.substring(6,10));
		
		var date2 = new Date();
		date2.setFullYear($('#discontinuedDate').val().substring(6,10));
		
	
		if(local==="fr")
		{
			date1.setMonth(value.substring(3,5));
			date1.setDate(value.substring(0,2));
			date2.setMonth($('#discontinuedDate').val().substring(3,5));
			date2.setDate($('#discontinuedDate').val().substring(0,2));
		}
		
		else
		{
			date1.setMonth(value.substring(0,2));
			date1.setDate(value.substring(3,5));
			date2.setMonth($('#discontinuedDate').val().substring(0,2));
			date2.setDate($('#discontinuedDate').val().substring(3,5));
		}
		
		return (date1 <= date2);
	},
});

$.validate();