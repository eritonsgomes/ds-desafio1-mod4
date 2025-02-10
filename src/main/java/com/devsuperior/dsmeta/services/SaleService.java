package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSummaryMinProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static com.devsuperior.dsmeta.utils.DateTimeUtil.*;

@Service
public class SaleService {

	private final SaleRepository repository;

	@Autowired
	public SaleService(SaleRepository repository) {
		this.repository = repository;
	}

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public SaleReportMinDTO searchSaleReportBySellerId(Long id) {
		Sale result = repository.searchById(id);
		SaleReportMinDTO response = null;

		if (Objects.nonNull(result)) {
			response = new SaleReportMinDTO(result);
		}

		return response;
	}

	public Page<SaleReportMinDTO> findSaleReportBetweenDatesAndSellerName(String minDate, String maxDate, String name,
		Pageable pageable) {
		LocalDate initialDate = getInitialDate(minDate);
		LocalDate finalDate = getFinalDate(maxDate);

		validateYearDays(initialDate, finalDate);

		Page<Sale> saleList = repository.searchReportBySellerNameAndDateBetween(name, initialDate, finalDate, pageable);

		return saleList.map(SaleReportMinDTO::new);
	}

	public Page<SaleSummaryMinDTO> findSaleSummaryBetweenDatesOrderBySellerName(String minDate, String maxDate,
		Pageable pageable) {
		LocalDate initialDate = getInitialDate(minDate);
		LocalDate finalDate = getFinalDate(maxDate);

		validateYearDays(initialDate, finalDate);

		Page<SaleSummaryMinProjection> saleList = repository.searchSaleSummaryByDateBetween(initialDate, finalDate,
			pageable);

		return saleList.map(SaleSummaryMinDTO::new);
	}

}
