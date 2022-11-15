package threaded_cache;

import java.util.ArrayList;

public class MapeamentoAssociativo2Vias implements Runnable {
	
	private ArrayList<Integer> enderecos;
	private Boolean[][] cache_valueBit;
	private String[][] cache_tag;
	private Boolean[][] cache_repoBit;
	
	
	public MapeamentoAssociativo2Vias(ArrayList<Integer> enderecos) {
		
		Boolean[][] cache_valueBit = new Boolean[512][];
		String[][] cache_tag = new String[512][];
		Boolean[][] cache_repoBit = new Boolean[512][];
		
		for (int i = 0; i < 512; i++) {
			cache_valueBit[i] = new Boolean[2];
			cache_tag[i] = new String[2];
			cache_repoBit[i] = new Boolean[2];
			for (int j = 0; j < 2; j++) {
				cache_valueBit[i][j] = false;
				cache_tag[i][j] = "";
				cache_repoBit[i][j] = false;
			}
		}
		
		this.enderecos = enderecos;
		this.cache_valueBit = cache_valueBit;
		this.cache_tag = cache_tag;
		this.cache_repoBit = cache_repoBit;
		
	}
	
	public Integer[] cacheRead (Integer address) {
		
		String addressBinary = Integer.toBinaryString(address);
		addressBinary = SetZerosLeft.run(addressBinary);
		String set = addressBinary.substring(3, 12);
		String tag = addressBinary.substring(0, 3);
		
		Integer setDec = Integer.parseInt(set, 2);
		Integer[] result = {0, 0}; // acerto e falha
		
		if (cache_valueBit[setDec][0] == true && cache_valueBit[setDec][1] == true) {
			if (cache_tag[setDec][0].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][1].equals(tag)) {
				result[0] = 1;
			} else if (cache_repoBit[setDec][0] == true) {
				cache_tag[setDec][0] = tag;
				cache_repoBit[setDec][0] = false;
				cache_repoBit[setDec][1] = true;
				result[1] = 1;
			} else {
				cache_tag[setDec][1] = tag;
				cache_repoBit[setDec][1] = false;
				cache_repoBit[setDec][0] = true;
				result[1] = 1;
			}
		} else {
			if (cache_valueBit[setDec][0] == true) {
				if (cache_tag[setDec][0].equals(tag)) {
					result[0] = 1;
				} else {
					cache_valueBit[setDec][1] = true;
					cache_tag[setDec][1] = tag;
					cache_repoBit[setDec][1] = false;
					cache_repoBit[setDec][0] = true;
					result[1] = 1;
				}
			} else {
				if (cache_valueBit[setDec][1] == true) {
					if (cache_tag[setDec][1].equals(tag)) {
						result[0] = 1;
					} else {
						cache_valueBit[setDec][0] = true;
						cache_tag[setDec][0] = tag;
						cache_repoBit[setDec][0] = false;
						cache_repoBit[setDec][1] = true;
						result[1] = 1;
					}
				} else {
					cache_valueBit[setDec][0] = true;
					cache_tag[setDec][0] = tag;
					cache_repoBit[setDec][0] = false;
					cache_repoBit[setDec][1] = true;
					result[1] = 1;
				}
			}
		}
		
		return result;
		
	}
	
	public void runCache () {		
		
		Integer[] result = {0, 0};
		Integer[] aux = new Integer[2];
		
		for (int i = 0; i < enderecos.size(); i++) {
			aux = cacheRead(enderecos.get(i));
			result[0] += aux[0];
			result[1] += aux[1];
		}
		
		Float porc_acertos  = ((float) result[0] / enderecos.size() * 100);
		Float porc_falhas  = (float) result[1] / enderecos.size() * 100;
		
		System.out.printf("Acertos Mapeamento Associativo 2 Vias: %.2f%% %n", porc_acertos);
		System.out.printf("Falhas Mapeamento Associativo 2 Vias: %.2f%% %n", porc_falhas);
		
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		runCache();		
	}
	
}
