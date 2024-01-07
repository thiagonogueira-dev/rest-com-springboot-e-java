package br.com.spring.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.converters.NumberConverter;
import br.com.spring.exceptions.UnsupportedMathOperationException;
import br.com.spring.math.SimpleMath;

@RestController
public class MathController {
	
	private static final AtomicLong counter = new AtomicLong();
	
	private SimpleMath math = new SimpleMath();
	
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double sum(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		
		return math.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/sub/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double subtraction(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		
		return math.subtraction(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/mult/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double multiplication(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		
		if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		
		return math.multiplication(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/div/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double division(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		if(NumberConverter.convertToDouble(numberTwo) == 0) {
			throw new UnsupportedMathOperationException("O divisor informado é igual a zero");
		}
			
		return math.division(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/avg/{numberOne}/{numberTwo}",
			method = RequestMethod.GET)
	public Double average(
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		
		return math.average( NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/sqrt/{numberOne}",
			method = RequestMethod.GET)
	public Double sqrt(
			@PathVariable(value = "numberOne") String numberOne
			) throws Exception {
		if(!NumberConverter.isNumeric(numberOne)){
			throw new UnsupportedMathOperationException("Por favor, utilize valores numéricos");
		}
		if(NumberConverter.convertToDouble(numberOne) < 0) {
			throw new UnsupportedMathOperationException("Não é possível calcular a raiz quadrada de números negativos");
		}
		
		return math.sqrt(NumberConverter.convertToDouble(numberOne));
	}

}
