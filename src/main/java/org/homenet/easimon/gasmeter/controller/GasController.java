package org.homenet.easimon.gasmeter.controller;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Locale;

import org.homenet.easimon.gasmeter.domain.GasRecord;
import org.homenet.easimon.gasmeter.domain.GasRecordQuantizer;
import org.homenet.easimon.gasmeter.domain.GasRecordRepository;
import org.homenet.easimon.gasmeter.json.Data;
import org.homenet.easimon.gasmeter.json.Dataset;
import org.homenet.easimon.util.ZonedDateTimeTruncator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GasController {

	GasRecordRepository repository;

	private ZoneId displayZoneId;

	private Locale displayLocale;

	@Autowired
	public GasController(GasRecordRepository repository, ZoneId displayZoneId, Locale displayLocale) {
		this.repository = repository;
		this.displayZoneId = displayZoneId;
		this.displayLocale = displayLocale;
	}

	@RequestMapping("/gas")
	public Data getGas( //
			@RequestParam(value = "from", defaultValue = "2016-01-01T00:00:00+02:00[Europe/Berlin]") final String fromParam, //
			@RequestParam(value = "quantizedby", defaultValue = "WEEKS") final String quantizedByParam, //
			@RequestParam(value = "quantityUnit", defaultValue = "MONTHS") final String quantityUnitParam, //
			@RequestParam(value = "quantity", defaultValue = "12") final String quantityParam, //
			@RequestParam(value = "compareprevious", defaultValue = "false") final String comparepreviousParam) {
		// TODO: comparison interval (previous year, month ..)
		final ZonedDateTime from = ZonedDateTime.parse(fromParam);
		final TemporalUnit quantityUnit = ChronoUnit.valueOf(quantityUnitParam);
		final TemporalUnit quantizedby = ChronoUnit.valueOf(quantizedByParam);

		final int quantity = Integer.valueOf(quantityParam).intValue();
		final ZonedDateTime to = from.plus(quantity, quantityUnit);
		boolean comparePrevious = Boolean.valueOf(comparepreviousParam).booleanValue();

		List<GasRecord> quantized = getGasRecords(from, to, quantizedby);

		Dataset[] datasets = new Dataset[1 + (comparePrevious ? 1 : 0)];

		int currentYear = from.get(ChronoField.YEAR);
		datasets[0] = getGasDataset(String.valueOf(currentYear), quantized);

		if (comparePrevious) {
			final ZonedDateTime fromPreviousYear = from.minusYears(1);
			final ZonedDateTime toPreviousYear = from.plus(quantity, quantityUnit);
			List<GasRecord> quantizedPreviousYear = getGasRecords(fromPreviousYear, toPreviousYear, quantizedby);

			datasets[1] = getGasDataset(String.valueOf(currentYear - 1), quantizedPreviousYear);
		}

		String[] labels = getLabels(quantized);
		return new Data(labels, datasets);
	}

	private String[] getLabels(List<GasRecord> quantizedData) {
		final String[] labels = new String[quantizedData.size()];

		int i = 0;
		for (GasRecord record : quantizedData) {
			final String label = record.getTimestamp().atZone(displayZoneId).toLocalDateTime().toString();
			labels[i] = label;
			i++;
		}
		return labels;
	}

	private Dataset getGasDataset(final String name, List<GasRecord> quantizedData) {
		final long[] data = new long[quantizedData.size()];

		int i = 0;
		for (GasRecord record : quantizedData) {
			final long amount = record.getAmount();
			data[i] = amount;
			i++;
		}

		return new Dataset(name, data);
	}

	private List<GasRecord> getGasRecords(final ZonedDateTime from, final ZonedDateTime to,
			final TemporalUnit quantizedby) {
		List<GasRecord> records = repository.findGasRecordsByPeriod(from, to);
		return GasRecordQuantizer.quantize(records, quantizedby, displayZoneId, displayLocale);

	}

	@RequestMapping("/gas/year")
	public Data getGasCurrentYear(//
			@RequestParam(value = "compareprevious", defaultValue = "false") final String comparepreviousParam) {

		ZonedDateTime now = ZonedDateTime.ofInstant(Clock.system(displayZoneId).instant(), displayZoneId);
		final TemporalUnit quantizedby = ChronoUnit.MONTHS;

		final ZonedDateTime from = ZonedDateTimeTruncator.truncate(now, ChronoUnit.YEARS, displayLocale);
		final ZonedDateTime to = from.plus(12, ChronoUnit.MONTHS);

		List<GasRecord> quantizedCurrentYear = getGasRecords(from, to, quantizedby);

		boolean comparePrevious = Boolean.valueOf(comparepreviousParam).booleanValue();

		Dataset[] datasets = new Dataset[1 + (comparePrevious ? 1 : 0)];

		int currentYear = now.get(ChronoField.YEAR);
		datasets[0] = getGasDataset(String.valueOf(currentYear), quantizedCurrentYear);

		if (comparePrevious) {
			final ZonedDateTime fromPreviousYear = from.minusYears(1);
			final ZonedDateTime toPreviousYear = from.plus(12, ChronoUnit.MONTHS);
			List<GasRecord> quantizedPreviousYear = getGasRecords(fromPreviousYear, toPreviousYear, quantizedby);

			datasets[1] = getGasDataset(String.valueOf(currentYear - 1), quantizedPreviousYear);
		}

		String[] labels = { "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September",
				"November", "Dezember" };
		return new Data(labels, datasets);
	}

	@RequestMapping("/gas/event")
	public String registerGasEvent() {
		repository.createNormalGasRecord(Instant.now());
		return "OK";
	}
}
