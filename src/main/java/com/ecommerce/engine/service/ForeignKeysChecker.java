package com.ecommerce.engine.service;

import com.ecommerce.engine.config.exception.ApplicationException;
import com.ecommerce.engine.config.exception.ErrorCode;
import com.ecommerce.engine.util.TranslationUtils;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
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
        String tablesWithIdCondition = getTablesWithIdCondition();

        List<TableData> queryResult = getTableData(tablesWithIdCondition);
        fillTableDataMapFromQuery(queryResult);
    }

    public void checkUsages(String tableName, Object recordId, String... excludeUsageTable) {
        List<TableData> tableDataList = tableDataMap.get(tableName);
        if (CollectionUtils.isEmpty(tableDataList)) {
            return;
        }

        List<UsageData> usages = tableDataList.stream()
                .map(tableData -> checkUsageInTable(tableData, recordId, excludeUsageTable))
                .filter(Objects::nonNull)
                .toList();

        if (!usages.isEmpty()) {
            String translatedTableName =
                    StringUtils.capitalize(TranslationUtils.getMessage(TranslationUtils.TABLE_NAME_KEY + tableName));
            String translatedUsages =
                    usages.stream().map(Objects::toString).collect(Collectors.joining("; ", "[", "]"));

            throw new ApplicationException(
                    ErrorCode.RECORD_HAS_USAGES,
                    TranslationUtils.getMessage(
                            "exception.foundUsage", translatedTableName, recordId, translatedUsages));
        }
    }

    private String getTablesWithIdCondition() {
        String sql =
                """
                SELECT table_name, column_name
                FROM information_schema.key_column_usage
                WHERE constraint_name IN
                      (SELECT constraint_name
                       FROM information_schema.table_constraints
                       WHERE constraint_type = 'PRIMARY KEY');
                """;

        List<TableData> idQueryResult = jdbcTemplate.query(
                sql, (rs, rowNum) -> new TableData(rs.getString("table_name"), null, rs.getString("column_name")));

        List<String> tablesWithId = idQueryResult.stream()
                .filter(tableData -> tableData.columnName.equals("id"))
                .map(TableData::tableName)
                .distinct()
                .toList();

        return tablesWithId.stream().map(table -> "'" + table + "'").collect(Collectors.joining(", ", "(", ")"));
    }

    private List<TableData> getTableData(String tablesWithIdCondition) {
        String sql =
                """
                SELECT ccu.table_name,
                       tc.table_name AS usage_table,
                       kcu.column_name
                FROM information_schema.table_constraints AS tc
                         JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name
                         JOIN information_schema.constraint_column_usage AS ccu ON tc.constraint_name = ccu.constraint_name
                WHERE tc.constraint_type = 'FOREIGN KEY' AND tc.table_name IN ?;
                """;

        // Unfortunately, jdbc template doesn't support list parameters.
        // And I use a question mark so that IDEA highlights the line as a sql query
        sql = sql.replace("?", tablesWithIdCondition);

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new TableData(
                        rs.getString("table_name"), rs.getString("usage_table"), rs.getString("column_name")));
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

    @Nullable private UsageData checkUsageInTable(TableData tableData, Object recordId, String... excludeUsageTable) {
        String usageTable = tableData.usageTable;

        if (ArrayUtils.contains(excludeUsageTable, usageTable)) {
            return null;
        }

        String sql = SEARCH_PATTERN.formatted(usageTable, tableData.columnName);
        List<String> foundIds;
        try {
            foundIds = jdbcTemplate.queryForList(sql, String.class, recordId);
        } catch (BadSqlGrammarException e) {
            throw new ApplicationException(
                    ErrorCode.BAD_SQL_GRAMMAR,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Cannot find usages for table %s due to it doesn't contain an id column!".formatted(usageTable),
                    e);
        }

        if (CollectionUtils.isEmpty(foundIds)) {
            return null;
        }

        return new UsageData(recordId, tableData.tableName, usageTable, foundIds);
    }

    private record TableData(String tableName, String usageTable, String columnName) {}

    private record UsageData(Object recordId, String tableName, String usageTable, List<String> foundIds) {

        @Override
        public String toString() {
            String translatedUsageTable = TranslationUtils.getMessage(TranslationUtils.TABLE_NAME_KEY + usageTable);
            return TranslationUtils.getMessage("exception.usageInfo", translatedUsageTable, foundIds);
        }
    }
}
