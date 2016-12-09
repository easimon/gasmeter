package org.homenet.easimon.smarthome.spring;

import org.homenet.easimon.smarthome.json.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GasController {

	@RequestMapping("/gas")
	public Data getGas( //
			@RequestParam(value = "from", defaultValue = "now") String from, //
			@RequestParam(value = "quantizedby", defaultValue = "months") String quantizer, //
			@RequestParam(value = "quantity", defaultValue = "12") String quantity //
	) {
		return null;
	}

}
