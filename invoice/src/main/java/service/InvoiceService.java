package service;

import java.math.BigDecimal;
import java.time.LocalDate;

import entity.Invoice;
import javafx.scene.control.TextField;

public class InvoiceService {

	public static BigDecimal formatNewInvoiceNumber(BigDecimal nr)
	{
		nr = nr.subtract(new BigDecimal(LocalDate.now().getYear())).setScale(6,BigDecimal.ROUND_HALF_EVEN);
		nr = nr.add(new BigDecimal(0.000001)).setScale(6,BigDecimal.ROUND_HALF_EVEN);
		return nr;
	}
	
	public static String invoiceNumberToString(BigDecimal nr)
	{
		return "FV/" + LocalDate.now().getYear() + "/" + nr.toString().substring(2);
	}
	
	public static String formatExistingInvoiceNumber(BigDecimal nr)
	{
		return "FV" + "/" + nr.toString().replace('.', '/');
	}
	
	public static String getOriginalInvoiceNumber(String s) 
	{
		if (s.startsWith("FV")) s = s.substring(2);
		else if (s.startsWith("FV/")) s = s.substring(3);
		s = s.replace('/', '.');
		return s;
	}
	
}
