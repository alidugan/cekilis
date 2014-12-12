package com.dugan.cekilis;

import java.util.*;

public class Raffle
{
	static Random random = new Random();
	
	static ArrayList<String> setPairsAli(ArrayList<String> inputs)
	{
		ArrayList<String> peers = new ArrayList();
		Vector<String> tmp = new Vector();
		for (String party : inputs){
			tmp.add(party);
		}

		for (int i=0; i<inputs.size();++i) {
			int p = random.nextInt(inputs.size());
			if ((i == p) || ("_x_".equals(tmp.elementAt(p)))) {
				--i;
				continue;
			}
			peers.add(inputs.get(p));
			tmp.setElementAt("_x_",p);

		}
		return peers;
	}
	

	static ArrayList<String> setPairsNazim(ArrayList<String> inputs)
	{
		int size = inputs.size();
		int[] es = new int[size];

		
		int count1 = 0;
		boolean again1 = true;
		while (again1)
		{
			again1 = false;
			for (int i=0; i < size; i++)
			{
				boolean again = true;
				int r=-1;
				int count=0;
				while (again)
				{
					count++;
					again = false;
					r = random.nextInt(size);
					if (r == i)
					{
						if (count < 10) again = true;
						else again1 = true;
					}

					for (int j=0; j < i; j++)
					{
						if (es[j] == r)
						{
							again = true;
							break;
						}
					}
				}
				es[i] = r;
			}

			count1 = 0;
			for (int i=0; i < size; i++)
			{
				if (es[es[i]] == i) count1++;
			}
			if (count1 > 2) again1 = true;

		}
		
		ArrayList<String> peers = new ArrayList();
		for (int i=0; i<size; ++i) {
			peers.add(inputs.get(es[i]));
		}
		
		return peers;
	}

}
