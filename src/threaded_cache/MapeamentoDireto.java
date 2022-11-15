package threaded_cache;

import java.util.ArrayList;

public class MapeamentoDireto implements Runnable{
	
	private ArrayList<Integer> enderecos;
	private Boolean[] cache_valueBit;
	private String[] cache_tag;
	
	public MapeamentoDireto(ArrayList<Integer> enderecos) {		
		
		Boolean[] cache_valueBit = new Boolean[1024];
		String[] cache_tag = new String[1024];
		
		for (int i = 0; i < 1024; i++) {
			cache_valueBit[i] = false;
			cache_tag[i] = "";
		}
		
		this.enderecos = enderecos;
		this.cache_valueBit = cache_valueBit;
		this.cache_tag = cache_tag;
		
	}
	
	public Integer[] cacheRead (Integer address) {
		
		String addressBinary = Integer.toBinaryString(address);
		addressBinary = SetZerosLeft.run(addressBinary);
		String line = addressBinary.substring(2, 12);
		String tag = addressBinary.substring(0, 2);
		
		Integer lineDec = Integer.parseInt(line, 2);
		Integer[] result = {0, 0}; // acerto e falha
		
		if (cache_valueBit[lineDec] == false) {
			cache_valueBit[lineDec] = true;
			cache_tag[lineDec] = tag;
			result[1] = 1;
		} else {
			if (cache_tag[lineDec].equals(tag)) {
				result[0] = 1;
			} else {				
				cache_tag[lineDec] = tag;
				result[1] = 1;
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
		
		System.out.printf("Acertos Mapeamento Direto: %.2f%% %n", porc_acertos);
		System.out.printf("Falhas Mapeamento Direto: %.2f%% %n", porc_falhas);
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		runCache();		
	}
	
}
