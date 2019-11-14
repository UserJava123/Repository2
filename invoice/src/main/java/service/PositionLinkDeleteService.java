package service;

import java.util.List;

import entity.Position;
import javafx.scene.control.Hyperlink;

public class PositionLinkDeleteService {

	public static int findPositionByLink(List<Position> positions, Hyperlink hl)
	{
		int found = 0;

		for (entity.Position p: positions)
		{	
			if(hl.getId().equals(new Integer(p.getNr()).toString()))
			{
				found = p.getNr();
				positions.remove(p);
				return found;
			}
		}
		return -1;
	}
	
	public static void matchPositionsNumbers(List<Position> positions, int found)
	{
		for (Position p: positions)
		{
			if (p.getNr()>found)
			{
				p.setNr(p.getNr() -1);
				p.setLinkDeleteId(new Integer(p.getNr()).toString());
			}
		}
	}
}
