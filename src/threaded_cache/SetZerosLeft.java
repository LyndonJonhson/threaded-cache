package threaded_cache;

public class SetZerosLeft {
	
	public static String run (String binaryNumber) {
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < (14 - binaryNumber.length()); i++) {
			sb.append("0");
		}
		sb.append(binaryNumber);
		return sb.toString();
	}
	
}
