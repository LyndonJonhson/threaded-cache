package threaded_cache;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		
		ArrayList<Integer> enderecos = LeituraArquivo.ler("enderecos.dat");
		
		System.out.println(Thread.currentThread().getName());
		
		Thread md = new Thread(new MapeamentoDireto(enderecos));
		md.start();
		
		Thread mta = new Thread(new MapeamentoTotalmenteAssociativo(enderecos));
		mta.start();
		
		Thread ma2v = new Thread(new MapeamentoAssociativo2Vias(enderecos));
		ma2v.start();
		
		Thread ma4v = new Thread(new MapeamentoAssociativo4Vias(enderecos));
		ma4v.start();
	
	}
	
}
