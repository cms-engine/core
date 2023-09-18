package com.ecommerce.engine.model;

import java.util.List;

public record SearchResponse<D>(int page, int number, int totalNumber, List<D> data) {}
