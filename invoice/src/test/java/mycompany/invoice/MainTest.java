package mycompany.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import entity.Position;
import junit.framework.Assert;
import service.Calculator;
import service.InvoiceService;
import service.PositionLinkDeleteService;

public class MainTest {

	@Test
	public void testCountBrutto()
	{
		Assert.assertEquals(Calculator.countBruttoSumOfPosition(new BigDecimal(4.89),new BigDecimal(27),new BigDecimal(0.23)),new BigDecimal(162.4).setScale(2,BigDecimal.ROUND_HALF_EVEN));
	}
	
	@Test
	public void testFormatNewInvoiceNumber()
	{
		Assert.assertEquals(InvoiceService.invoiceNumberToString(InvoiceService.formatNewInvoiceNumber(new BigDecimal(2019.000003))),"FV/2019/000004");
	}
	
	@Test
	public void testFormatExistingInvoiceNumber()
	{
		Assert.assertEquals(InvoiceService.formatExistingInvoiceNumber(new BigDecimal(2019.000503).setScale(6,BigDecimal.ROUND_HALF_EVEN)),"FV/2019/000503");
	}
	
}
