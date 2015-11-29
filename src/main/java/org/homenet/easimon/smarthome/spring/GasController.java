package org.homenet.easimon.smarthome.spring;

import java.util.ArrayList;
import java.util.List;

import org.homenet.easimon.smarthome.domain.GasRecordEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GasController {

	@RequestMapping("/gas")
	public GasRecordEntity[] getGas() {
		List<GasRecordEntity> result = new ArrayList<GasRecordEntity>();
		
		
		
		return result.toArray(new GasRecordEntity[0]);
	}
	
	
}
