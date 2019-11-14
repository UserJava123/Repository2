package service;

import java.math.BigDecimal;
import java.util.List;

import entity.Position;

public class Calculator {

	//dodac tutaj obliczanie brutto, netto, vat z pozycji
	
	public static BigDecimal countNettoSumOfPosition(BigDecimal price, BigDecimal quantity)
	{
		return price.multiply(quantity).setScale(2,BigDecimal.ROUND_HALF_EVEN);
	}
	
	public static BigDecimal countVatSumOfPosition(BigDecimal price, BigDecimal quantity, BigDecimal vat)
	{
		return price.multiply(quantity).multiply(vat).setScale(2,BigDecimal.ROUND_HALF_EVEN);
	}
	
	public static BigDecimal countBruttoSumOfPosition(BigDecimal price, BigDecimal quantity, BigDecimal vat)
	{
		return countNettoSumOfPosition(price,quantity).add(countVatSumOfPosition(price,quantity,vat)).setScale(2,BigDecimal.ROUND_HALF_EVEN);
	}
	
	public static BigDecimal countNettoSum(List<entity.Position> positions)
	{
		BigDecimal sumNetto = new BigDecimal(0);

		for(Position p: positions)
		{
			sumNetto = sumNetto.add(p.getNettoSum());
		}
		
		sumNetto = sumNetto.setScale(2,BigDecimal.ROUND_HALF_EVEN);
		return sumNetto;
	}
	
	public static BigDecimal countBruttoSum(List<entity.Position> positions)
	{
		BigDecimal sumBrutto = new BigDecimal(0);
		
		for(Position p: positions)
		{
			sumBrutto = sumBrutto.add(p.getBruttoSum());
		}

		sumBrutto = sumBrutto.setScale(2,BigDecimal.ROUND_HALF_EVEN);
		return sumBrutto;
	}
	
	public static BigDecimal countVatSum(List<entity.Position> positions){

		BigDecimal sumVat = new BigDecimal(0);
		
		for(Position p: positions)
		{
			sumVat = sumVat.add(p.getVatSum());
		}

		sumVat = sumVat.setScale(2,BigDecimal.ROUND_HALF_EVEN);
		return sumVat;
	}
}
