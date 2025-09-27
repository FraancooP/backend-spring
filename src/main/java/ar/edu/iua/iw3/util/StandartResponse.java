package ar.edu.iua.iw3.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StandartResponse {

	private String message;
	
	@JsonIgnore
	private Throwable ex;
	
	@JsonIgnore
	private HttpStatus httpStatus;
	
	public int getCode() {
		return httpStatus.value();
	}
	
	@JsonIgnore
	private boolean devInfoEnabled;
	//La variable devINfo la pondemos en falso cuando del otro lado tengamos a una persona que no es de desarrollo y verdadero para lo contrario
	
	public String getDevInfo() {
		if(devInfoEnabled) {
			if(ex!=null) {
				//Importamos strackTrace
				return ExceptionUtils.getStackTrace(ex);
				//Deeja todo tabulado y bonito
			}else {
				return "No stack trace"; 
			}
		}else {
			return null;
		}
	}
	
	public String getMessage() {
		//Esto hace que usemos nuyestra propia version y no la de longbok
		//Lo que hacemos es que si es null reotornamos o el mensaje
		if(message!=null)
			return message;
		if(ex!=null)
			return ex.getMessage();
		return null;
	}
}

//Estructura esperada por json
/*
 * 
 * {
 * "message" : "Algo",
 * "devinfo" : "Algo"
 * }
 * */
