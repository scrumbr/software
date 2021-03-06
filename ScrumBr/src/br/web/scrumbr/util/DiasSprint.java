package br.web.scrumbr.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;

import de.jollyday.Holiday;
import de.jollyday.HolidayManager;

public class DiasSprint {

	public static void main(String[] args) {

	}

	public static List<LocalDate> diasUteis(DateTime dataInicial,
			DateTime dataFinal) {

		DateTime feriado = new DateTime(2012, 12, 25, 12, 0);

		HolidayManager gerenciadorDeFeriados = HolidayManager
				.getInstance(de.jollyday.HolidayCalendar.BRAZIL);
		Set<Holiday> feriados = gerenciadorDeFeriados
				.getHolidays(new DateTime().getYear());

		Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();
		for (Holiday h : feriados) {
			dataDosFeriados.add(new LocalDate(h.getDate(), ISOChronology
					.getInstance()));
			System.out.println(dataDosFeriados);
		}

		// popula com os feriados brasileiros
		HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(
				dataDosFeriados);

		LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays(
				"BR", calendarioDeFeriados);

		DateCalculator<LocalDate> calendario = LocalDateKitCalculatorsFactory
				.getDefaultInstance().getDateCalculator("BR",
						HolidayHandlerType.FORWARD);

		// true - s�bado
		System.out.println(calendario
				.isNonWorkingDay(new LocalDate(dataInicial)));

		// false
		System.out
				.println(calendario.isNonWorkingDay(new LocalDate(dataFinal)));

		// true - natal
		System.out.println(calendario.isNonWorkingDay(new LocalDate(feriado)));

		int diasNaoUteis = 0;

		LocalDate dataInicialTemporaria = new LocalDate(dataInicial);
		LocalDate dataFinalTemporaria = new LocalDate(dataFinal);
		List<LocalDate> listUteis = new ArrayList<LocalDate>();

		while (!dataInicialTemporaria.isAfter(dataFinalTemporaria)) {
			if (!calendario.isNonWorkingDay(dataInicialTemporaria)) {
				diasNaoUteis++;
				listUteis.add(dataInicialTemporaria);
				System.out.println(dataInicialTemporaria);
			}
			dataInicialTemporaria = dataInicialTemporaria.plusDays(1);
		}
		System.out.println(diasNaoUteis);
		return listUteis;
	}
}
