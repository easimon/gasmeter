package org.homenet.easimon.gasmeter.spring;

import org.homenet.easimon.gasmeter.domain.GasRecordRepository;
import org.homenet.easimon.gasmeter.json.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GasController {

	@Autowired
	GasRecordRepository repository;

	@RequestMapping("/gas")
	public Data getGas( //
			@RequestParam(value = "from", defaultValue = "now") String from, //
			@RequestParam(value = "quantizedby", defaultValue = "months") String quantizer, //
			@RequestParam(value = "quantity", defaultValue = "12") String quantity //
	) {
		return null;
	}

}
