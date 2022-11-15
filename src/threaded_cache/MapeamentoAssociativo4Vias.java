package threaded_cache;

import java.util.ArrayList;

public class MapeamentoAssociativo4Vias implements Runnable {
	
	private ArrayList<Integer> enderecos;
	private Boolean[][] cache_valueBit;
	private String[][] cache_tag;
	private Boolean[][] cache_repoBit;
	
	
	public MapeamentoAssociativo4Vias(ArrayList<Integer> enderecos) {
		
		Boolean[][] cache_valueBit = new Boolean[256][];
		String[][] cache_tag = new String[256][];
		Boolean[][] cache_repoBit = new Boolean[256][];
		
		for (int i = 0; i < 256; i++) {
			cache_valueBit[i] = new Boolean[4];
			cache_tag[i] = new String[4];
			cache_repoBit[i] = new Boolean[4];
			for (int j = 0; j < 4; j++) {
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
		String set = addressBinary.substring(4, 12);
		String tag = addressBinary.substring(0, 4);
		
		Integer setDec = Integer.parseInt(set, 2);
		Integer[] result = {0, 0}; // acerto e falha
		Integer[] repoBit_aux = {3, 2, 1};
			
		if (cache_valueBit[setDec][0] == true && cache_valueBit[setDec][1] == true && cache_valueBit[setDec][2] == true && cache_valueBit[setDec][3] == true) {
			if (cache_tag[setDec][0].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][1].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][2].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][3].equals(tag)) {
				result[0] = 1;
			} else if (cache_repoBit[setDec][0] == true) {
				cache_tag[setDec][0] = tag;
				cache_repoBit[setDec][0] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 0;
				result[1] = 1;
			} else if (cache_repoBit[setDec][1] == true) {
				cache_tag[setDec][1] = tag;
				cache_repoBit[setDec][1] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 1;
				result[1] = 1;
			} else if (cache_repoBit[setDec][2] == true) {
				cache_tag[setDec][2] = tag;
				cache_repoBit[setDec][2] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 2;
				result[1] = 1;
			} else {
				cache_tag[setDec][3] = tag;
				cache_repoBit[setDec][3] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 3;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][0] == true && cache_valueBit[setDec][1] == true && cache_valueBit[setDec][2] == true) {
			if (cache_tag[setDec][0].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][1].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][2].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][3] = true;
				cache_tag[setDec][3] = tag;
				cache_repoBit[setDec][3] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 3;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][0] == true && cache_valueBit[setDec][1] == true && cache_valueBit[setDec][3] == true) {
			if (cache_tag[setDec][0].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][1].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][3].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][2] = true;
				cache_tag[setDec][2] = tag;
				cache_repoBit[setDec][2] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 2;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][0] == true && cache_valueBit[setDec][2] == true && cache_valueBit[setDec][3] == true) {
			if (cache_tag[setDec][0].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][2].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][3].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][1] = true;
				cache_tag[setDec][1] = tag;
				cache_repoBit[setDec][1] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 1;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][1] == true && cache_valueBit[setDec][2] == true && cache_valueBit[setDec][3] == true) {
			if (cache_tag[setDec][1].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][2].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][3].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][0] = true;
				cache_tag[setDec][0] = tag;
				cache_repoBit[setDec][0] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 0;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][0] == true && cache_valueBit[setDec][1] == true) {
			if (cache_tag[setDec][0].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][1].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][2] = true;
				cache_tag[setDec][2] = tag;
				cache_repoBit[setDec][2] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 2;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][0] == true && cache_valueBit[setDec][2] == true) {
			if (cache_tag[setDec][0].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][2].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][1] = true;
				cache_tag[setDec][1] = tag;
				cache_repoBit[setDec][1] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 1;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][0] == true && cache_valueBit[setDec][3] == true) {
			if (cache_tag[setDec][0].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][3].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][1] = true;
				cache_tag[setDec][1] = tag;
				cache_repoBit[setDec][1] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 1;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][1] == true && cache_valueBit[setDec][2] == true) {
			if (cache_tag[setDec][1].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][2].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][0] = true;
				cache_tag[setDec][0] = tag;
				cache_repoBit[setDec][0] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 0;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][1] == true && cache_valueBit[setDec][3] == true) {
			if (cache_tag[setDec][1].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][3].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][0] = true;
				cache_tag[setDec][0] = tag;
				cache_repoBit[setDec][0] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 0;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][1] == true && cache_valueBit[setDec][3] == true) {
			if (cache_tag[setDec][1].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][3].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][0] = true;
				cache_tag[setDec][0] = tag;
				cache_repoBit[setDec][0] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 0;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][2] == true && cache_valueBit[setDec][3] == true) {
			if (cache_tag[setDec][2].equals(tag)) {
				result[0] = 1;
			} else if (cache_tag[setDec][3].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][0] = true;
				cache_tag[setDec][0] = tag;
				cache_repoBit[setDec][0] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 0;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][0] == true) {
			if (cache_tag[setDec][0].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][1] = true;
				cache_tag[setDec][1] = tag;
				cache_repoBit[setDec][1] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 1;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][1] == true) {
			if (cache_tag[setDec][1].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][0] = true;
				cache_tag[setDec][0] = tag;
				cache_repoBit[setDec][0] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 0;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][2] == true) {
			if (cache_tag[setDec][2].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][0] = true;
				cache_tag[setDec][0] = tag;
				cache_repoBit[setDec][0] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 0;
				result[1] = 1;
			}
		} else if (cache_valueBit[setDec][3] == true) {
			if (cache_tag[setDec][3].equals(tag)) {
				result[0] = 1;
			} else {
				cache_valueBit[setDec][0] = true;
				cache_tag[setDec][0] = tag;
				cache_repoBit[setDec][0] = false;
				cache_repoBit[setDec][repoBit_aux[0]] = true;
				repoBit_aux[0] = repoBit_aux[1];
				repoBit_aux[1] = repoBit_aux[2];
				repoBit_aux[2] = 0;
				result[1] = 1;
			}
		} else {
			cache_valueBit[setDec][0] = true;
			cache_tag[setDec][0] = tag;
			cache_repoBit[setDec][0] = false;
			cache_repoBit[setDec][repoBit_aux[0]] = true;
			repoBit_aux[0] = repoBit_aux[1];
			repoBit_aux[1] = repoBit_aux[2];
			repoBit_aux[2] = 0;
			result[1] = 1;
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
		
		System.out.printf("Acertos Mapeamento Associativo 4 Vias: %.2f%% %n", porc_acertos);
		System.out.printf("Falhas Mapeamento Associativo 4 Vias: %.2f%% %n", porc_falhas);
		
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		runCache();			
	}
	
}
