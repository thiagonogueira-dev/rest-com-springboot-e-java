package br.com.spring;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.exceptions.UnsupportedMathOperationException;

@RestController
public class MathController {
	
	private static final String template = "Hello, %s!";
	private static final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		
		return convertToDouble(numberOne) + convertToDouble(numberTwo);
	}
	
	@RequestMapping(value = "/sub/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double subtraction(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		
		return convertToDouble(numberOne) - convertToDouble(numberTwo);
	}
	
	@RequestMapping(value = "/mult/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double multiplication(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		
		return convertToDouble(numberOne) * convertToDouble(numberTwo);
	}
	
	@RequestMapping(value = "/div/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double division(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		if(convertToDouble(numberTwo) == 0) {
			throw new UnsupportedMathOperationException("O divisor informado é igual a zero");
		}
			
		return convertToDouble(numberOne) / convertToDouble(numberTwo);
	}
	
	@RequestMapping(value = "/avg/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double average(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		
		return ( convertToDouble(numberOne) + convertToDouble(numberTwo) ) / 2;
	}
	
	@RequestMapping(value = "/sqrt/{numberOne}",
			method = RequestMethod.GET)
	public Double sqrt(
			@PathVariable(value = "numberOne") String numberOne
			) throws Exception {
		if(!isNumeric(numberOne)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		if(convertToDouble(numberOne) < 0) {
			throw new UnsupportedMathOperationException("Não é possível calcular a raiz quadrada de números negativos");
		}
		
		return Math.sqrt(convertToDouble(numberOne));
	}

	
	private Double convertToDouble(String strNumber) {
		if(strNumber == null) return 0D;
		// BR 10,25 US 10.25
		String number = strNumber.replaceAll(",", ".");
		if(isNumeric(number)) 
			return Double.parseDouble(number);
		return null;
	}

	private boolean isNumeric(String strNumber) {
		if(strNumber == null) 
			return false;
		String number = strNumber.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
