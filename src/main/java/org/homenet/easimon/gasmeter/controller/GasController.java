package org.homenet.easimon.gasmeter.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Locale;

import org.homenet.easimon.gasmeter.domain.GasRecord;
import org.homenet.easimon.gasmeter.domain.GasRecordQuantizer;
import org.homenet.easimon.gasmeter.domain.GasRecordRepository;
import org.homenet.easimon.gasmeter.json.Data;
import org.homenet.easimon.gasmeter.json.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GasController {

	@Autowired
	GasRecordRepository repository;

	@Autowired
	private ZoneId displayZoneId;

	@Autowired
	private Locale displayLocale;

	@RequestMapping("/gas")
	public Data getGas( //
			@RequestParam(value = "from", defaultValue = "now") final String fromParam, //
			@RequestParam(value = "quantizedby", defaultValue = "months") final String quantizedByParam, //
			@RequestParam(value = "quantity", defaultValue = "12") final String quantityParam, //
			@RequestParam(value = "compareprevious", defaultValue = "false") final String comparepreviousParam) {
		// TODO: actually evaluate parameters.
		// TODO: comparison interval (previous year, month ..)
		final ZonedDateTime from = ZonedDateTime.parse("2015-10-10T00:00:00+02:00[Europe/Berlin]");
		final TemporalUnit quantityUnit = ChronoUnit.MONTHS;
		final TemporalUnit quantizedby = ChronoUnit.WEEKS;

		final int quantity = 12;
		final ZonedDateTime to = from.plus(quantity, quantityUnit);

		List<GasRecord> records = repository.findGasRecordsByPeriod(from, to);
		List<GasRecord> quantized = GasRecordQuantizer.quantize(records, quantizedby, displayZoneId, displayLocale);

		final String[] labels = new String[quantized.size()];
		final long[] data = new long[quantized.size()];

		int i = 0;
		for (GasRecord record : quantized) {
			final String label = record.getTimestamp().atZone(displayZoneId).toLocalDateTime().toString();
			final long amount = record.getAmount();
			labels[i] = label;
			data[i] = amount;
			i++;
		}

		Dataset dataset = new Dataset("gedoens", data);
		return new Data(labels, new Dataset[] { dataset });
	}

}
