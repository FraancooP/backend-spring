package ar.edu.iua.iw3.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class StandartResponseBusiness implements IStandartResponseBusiness {
	
	//Esta notacion busca el valor de la variable puesta  en el aplication . properties
	@Value("${dev.info.enabled:false}")
	private boolean devInfoEnabled;

	@Override
	public StandartResponse build(HttpStatus httpStatus, Throwable ex, String message) {
		StandartResponse sr = new StandartResponse();
		sr.setDevInfoEnabled(devInfoEnabled);
		sr.setMessage(message);
		sr.setHttpStatus(httpStatus);
		sr.setEx(ex);
		return sr;
	}

}
