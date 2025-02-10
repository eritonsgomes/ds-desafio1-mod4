package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SaleSummaryMinProjection;

public class SaleSummaryMinDTO {

	private final String sellerName;
	private final Double total;

	public SaleSummaryMinDTO(String sellerName, Double total) {
		this.sellerName = sellerName;
		this.total = total;
	}

	public SaleSummaryMinDTO(SaleSummaryMinProjection saleSummaryMinProjection) {
		sellerName = saleSummaryMinProjection.getSellerName();
		total = saleSummaryMinProjection.getTotal();
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}

}
