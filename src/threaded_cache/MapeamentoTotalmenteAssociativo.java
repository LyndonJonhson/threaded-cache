package threaded_cache;

import java.util.ArrayList;

public class MapeamentoTotalmenteAssociativo implements Runnable {
	
	private ArrayList<Integer> enderecos;
	private Boolean[] cache_valueBit;
	private String[] cache_tag;
	private Integer lastLineFilled;
	
	public MapeamentoTotalmenteAssociativo(ArrayList<Integer> enderecos) {
		
		Boolean[] cache_valueBit = new Boolean[1024];
		String[] cache_tag = new String[1024];
		
		for (int i = 0; i < 1024; i++) {
			cache_valueBit[i] = false;
			cache_tag[i] = "";
		}
		
		this.enderecos = enderecos;
		this.cache_valueBit = cache_valueBit;
		this.cache_tag = cache_tag;
		this.lastLineFilled = 1023;
		
	}
	
	public Integer[] cacheRead (Integer address) {
	
		String addressBinary = Integer.toBinaryString(address);
		addressBinary = SetZerosLeft.run(addressBinary);
		String tag = addressBinary.substring(0, 2);
	
		Integer[] result = {0, 0}; // acerto e falha
		
		for (int i = 0; i < 1024; i++) {
			if (cache_valueBit[i] == true) {
				if (cache_tag[i].equals(tag)) {
					result[0] = 1;
					return result;
				}
			} else {				
				cache_valueBit[i] = true;
				cache_tag[i] = tag;
				lastLineFilled = i;
				result[1] = 1;
				return result;
			}			
		}
		
		if (lastLineFilled < 1023) {
			cache_valueBit[lastLineFilled + 1] = true;
			cache_tag[lastLineFilled + 1] = tag;
			lastLineFilled += 1;
			result[1] = 1;
			
		} else {
			cache_valueBit[0] = true;
			cache_tag[0] = tag;
			lastLineFilled = 0;
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
		
		System.out.printf("Acertos Mapeamento Totalmente Associativo: %.2f%% %n", porc_acertos);
		System.out.printf("Falhas Mapeamento Totalmente Associativo: %.2f%% %n", porc_falhas);
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		runCache();		
	}
	
}
