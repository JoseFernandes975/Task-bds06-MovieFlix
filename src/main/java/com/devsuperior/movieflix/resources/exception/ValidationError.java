package com.devsuperior.movieflix.resources.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> erros = new ArrayList<>();

	public List<FieldMessage> getErros() {
		return erros;
	}

	public void addErros(String fieldMessage, String message) {
		erros.add(new FieldMessage(fieldMessage, message));
	}
}
