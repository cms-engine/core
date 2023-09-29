package com.ecommerce.engine.service;

import jakarta.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class ForeignKeysChecker implements ApplicationListener<ContextRefreshedEvent> {

    private static final String SEARCH_PATTERN = "select id from %s where %s = ?";
    private final JdbcTemplate jdbcTemplate;
    private final Map<String, List<TableData>> tableDataMap = new HashMap<>();

    @Override
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        String sql =
                """
                SELECT ccu.table_name,
                       tc.table_name AS usage_table,
                       kcu.column_name
                FROM information_schema.table_constraints AS tc
                         JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name
                         JOIN information_schema.constraint_column_usage AS ccu ON tc.constraint_name = ccu.constraint_name
                WHERE tc.constraint_type = 'FOREIGN KEY';
                """;

        List<TableData> queryResult = jdbcTemplate.query(sql, new TableDataRowMapper());
        fillTableDataMapFromQuery(queryResult);
    }

    private void fillTableDataMapFromQuery(List<TableData> queryResult) {
        queryResult.forEach(tableData -> {
            String tableName = tableData.tableName;

            List<TableData> tableDataList = new ArrayList<>();
            tableDataList.add(tableData);

            tableDataMap.merge(tableName, tableDataList, (tableData1, tableData2) -> {
                tableData1.addAll(tableData2);
                return tableData1;
            });
        });
    }

    public void checkUsages(String tableName, Object recordId, String... excludeUsageTable) {
        List<TableData> tableDataList = tableDataMap.get(tableName);
        if (CollectionUtils.isEmpty(tableDataList)) {
            return;
        }

        tableDataList.forEach(tableData -> checkUsageInTable(tableData, recordId, excludeUsageTable));
    }

    private void checkUsageInTable(TableData tableData, Object recordId, String... excludeUsageTable) {
        if (ArrayUtils.contains(excludeUsageTable, tableData.usageTable)) {
            return;
        }

        String sql = SEARCH_PATTERN.formatted(tableData.usageTable, tableData.columnName);
        List<String> foundIds;
        try {
            foundIds = jdbcTemplate.queryForList(sql, String.class, recordId);
        } catch (Exception e) {
            return;
        }

        System.out.println("Record %s of table %s is used in table %s, records: %s"
                .formatted(recordId, tableData.tableName, tableData.usageTable, foundIds));
    }

    private static class TableDataRowMapper implements RowMapper<TableData> {
        @Override
        public TableData mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            return new TableData(rs.getString("table_name"), rs.getString("usage_table"), rs.getString("column_name"));
        }
    }

    private record TableData(String tableName, String usageTable, String columnName) {}
}
