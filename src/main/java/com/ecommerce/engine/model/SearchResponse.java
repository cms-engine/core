package com.ecommerce.engine.model;

import java.util.List;

public record SearchResponse(int page, int number, int totalNumber, List<?> data) {}
