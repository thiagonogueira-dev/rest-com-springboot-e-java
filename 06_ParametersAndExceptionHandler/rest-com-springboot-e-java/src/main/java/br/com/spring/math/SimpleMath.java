package br.com.spring.math;

public class SimpleMath {
	public Double sum(Double numberOne, Double numberTwo){
		return numberOne + numberTwo;
	}
	
	public Double subtraction( Double numberOne,Double numberTwo) {
		return numberOne - numberTwo;
	}
	
	public Double multiplication(Double numberOne, Double numberTwo)  {
		return numberOne * numberTwo;
	}
	
	public Double division(Double numberOne, Double numberTwo) {
		return numberOne / numberTwo;
	}
	
	public Double average(Double numberOne, Double numberTwo) {
		
		return( numberOne + numberTwo ) / 2;
	}
	
	public Double sqrt(Double numberOne) {
		//if(.(numberOne) < 0) {
		//	throw new UnsupportedMathOperationException("Não é possível calcular a raiz quadrada de números negativos");
		//}
		
		return Math.sqrt(numberOne);
	}

	
	
}
