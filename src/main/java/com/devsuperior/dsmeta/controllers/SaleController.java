package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryMinDTO;
import com.devsuperior.dsmeta.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleReportMinDTO> findById(@PathVariable Long id) {
		SaleReportMinDTO dto = service.searchSaleReportBySellerId(id);

		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportMinDTO>> getReport(
		@RequestParam(name = "minDate" , required = false) String minDate,
		@RequestParam(name = "maxDate", required = false) String maxDate,
		@RequestParam(name = "name", defaultValue = "") String name,
		Pageable pageable
	) {
		Page<SaleReportMinDTO> resp = service.findSaleReportBetweenDatesAndSellerName(minDate, maxDate, name, pageable);

		return ResponseEntity.ok(resp);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<Page<SaleSummaryMinDTO>> getSummary(
		@RequestParam(name = "minDate" , required = false) String minDate,
		@RequestParam(name = "maxDate", required = false) String maxDate,
		Pageable pageable
	) {
		Page<SaleSummaryMinDTO> resp = service.findSaleSummaryBetweenDatesOrderBySellerName(minDate, maxDate, pageable);

		return ResponseEntity.ok(resp);
	}
}
