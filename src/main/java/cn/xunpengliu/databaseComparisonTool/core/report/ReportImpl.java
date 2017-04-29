package cn.xunpengliu.databaseComparisonTool.core.report;

import cn.xunpengliu.databaseComparisonTool.core.model.DatabaseModel;
import cn.xunpengliu.databaseComparisonTool.core.model.ReportModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableFieldModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableModel;
import cn.xunpengliu.databaseComparisonTool.util.Utils;

import java.util.*;

public class ReportImpl implements Report {
    private final String dividingLine = "*****************************************************************";
    private final String TABLE = "    ";

    @Override
    public ReportModel report(DatabaseModel sourceDb, DatabaseModel targetDb, int all) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("检查数据库:").append(sourceDb.getDbName()).append('\n');

        StringBuilder tableMost = new StringBuilder();
        StringBuilder tableLost = new StringBuilder();

        int error, warm, info;
        error = warm = info = 0;

        List<CompareTabledObj> sort = sort(sourceDb, targetDb);
        List<ReportModel> reportModels = new ArrayList<>(sort.size());

        for (CompareTabledObj compareTabledObj : sort) {
            if (compareTabledObj.getSourceTable() == null) {
                tableMost.append(compareTabledObj.getTargetTable().getTableName()).append(',');
                info++;
            } else if (compareTabledObj.getTargetTable() == null) {
                tableLost.append(compareTabledObj.getSourceTable().getTableName()).append(',');
                error++;
            } else {
                ReportModel reportModel = report(compareTabledObj.getSourceTable(), compareTabledObj.getTargetTable(), all);
                reportModels.add(reportModel);

                error += reportModel.getError();
                warm += reportModel.getWarm();
                info += reportModel.getInfo();
            }
        }

        sb.append(String.format("All result==>error:%s warm:%s info:%s\n", error, warm, info))
                .append(dividingLine).append('\n');
        if (tableLost.length() > 0) {
            sb.append("表缺失:\n").append(TABLE).append(tableLost.subSequence(0, tableLost.length() - 1)).append('\n');
        }
        if (tableMost.length() > 0) {
            sb.append("表多余:\n").append(TABLE).append(tableMost.subSequence(0, tableMost.length() - 1)).append('\n');
        }

        sb.append('\n');

        Collections.sort(reportModels, new Comparator<ReportModel>() {
            @Override
            public int compare(ReportModel o1, ReportModel o2) {
                int a = o1.getError()*100+o1.getWarm()+o1.getInfo();
                int b = o2.getError()*100+o2.getWarm()+o2.getInfo();
                return b-a;
            }
        });

        for (ReportModel reportModel : reportModels) {
            if(reportModel.getMessage() != null) {
                sb.append(reportModel.getMessage()).append('\n');
            }
        }

        ReportModel m = new ReportModel();
        m.setError(error);
        m.setWarm(warm);
        m.setInfo(info);
        m.setMessage(sb.toString());
        return m;
    }

    @Override
    public ReportModel report(TableModel source, TableModel target, int level) {
        StringBuilder sb = new StringBuilder(256);
        sb.append("检查表:").append(source.getTableName()).append('\n');
        StringBuilder message = new StringBuilder();
        StringBuilder fieldLost = new StringBuilder();
        StringBuilder fieldMore = new StringBuilder();
        int error, warm, info;
        error = warm = info = 0;

        List<CompareTableFieldObj> sort = sort(source, target);
        for (CompareTableFieldObj compareObj : sort) {
            TableFieldModel tf1 = compareObj.getSourceField();
            TableFieldModel tf2 = compareObj.getTargetField();
            if (tf1 == null) {
                fieldMore.append(tf2.getFieldName()).append(",");
                if (!tf2.isNullable()) {
                    error++;
                } else {
                    warm++;
                }
            } else if (tf2 == null) {
                fieldLost.append(tf1.getFieldName()).append(",");
                error++;
            } else {
                StringBuilder temp = new StringBuilder();
                if (!tf1.getDataType().equals(tf2.getDataType())) {
                    temp.append("    数据类型不一致 source==>").append(tf1.getDataType()).append(" target==>").append(tf2.getDataType()).append('\n');
                    error++;
                }
                if (!Utils.isEqual(tf1.getLength(), tf2.getLength())) {
                    temp.append("    数据长度不一致 source length==>").append(tf1.getLength()).append(" target length==>").append(tf2.getLength()).append('\n');
                    warm++;
                }
                if (tf1.isNullable() != tf2.isNullable()) {
                    temp.append("    source nullable==>").append(tf1.isNullable()).append(" target nullable==>").append(tf2.isNullable()).append('\n');
                    info++;
                }
                if (!Utils.isEqual(tf1.getColumnDefault(), tf2.getColumnDefault())) {
                    temp.append("    默认值不一致 source==>").append(tf1.getColumnDefault()).append(" target==>").append(tf2.getColumnDefault()).append('\n');
                    warm++;
                }
                if (temp.length() != 0) {
                    message.append("Field:").append(tf1.getFieldName()).append('\n').append(temp);
                }
            }
        }

        sb.append(String.format("Result error:%s warm:%s info:%s\n", error, warm, info));

        if (fieldLost.length() > 0) {
            sb.append("字段缺失:\n").append(TABLE).append(fieldLost.subSequence(0, fieldLost.length() - 1)).append('\n');
        }
        if (fieldMore.length() > 0) {
            sb.append("字段多余:\n").append(TABLE).append(fieldMore.subSequence(0, fieldMore.length() - 1)).append('\n');
        }

        sb.append(message);

        ReportModel m = new ReportModel();
        m.setError(error);
        m.setWarm(warm);
        m.setInfo(info);
        m.setMessage(sb.toString());
        return m;
    }

    private List<CompareTabledObj> sort(DatabaseModel sourceDb, DatabaseModel targetDb) {
        List<TableModel> sourceTables = new ArrayList<>(sourceDb.getTableModels());
        List<TableModel> targetTables = new LinkedList<>(targetDb.getTableModels());

        List<CompareTabledObj> compareTabledObjs = new ArrayList<>(sourceTables.size() / 2 + targetTables.size() / 2);
        //寻找目标数据库对应的字段，若不存在设置null
        int index = -1;
        for (TableModel tableModel : sourceTables) {
            CompareTabledObj compareTabledObj = new CompareTabledObj();
            compareTabledObj.setSourceTable(tableModel);

            index = targetTables.indexOf(tableModel);
            if (index != -1) {
                compareTabledObj.setTargetTable(targetTables.get(index));
                targetTables.remove(index);
            } else {
                compareTabledObj.setTargetTable(null);
            }
            compareTabledObjs.add(compareTabledObj);
        }
        //添加剩余目标列表
        for (TableModel targetTable : targetTables) {
            CompareTabledObj compareTabledObj = new CompareTabledObj();
            compareTabledObj.setSourceTable(null);
            compareTabledObj.setTargetTable(targetTable);

            compareTabledObjs.add(compareTabledObj);
        }
        return compareTabledObjs;
    }

    private List<CompareTableFieldObj> sort(TableModel t1, TableModel t2) {
        List<TableFieldModel> t1Fields = new ArrayList<>(t1.getFields());
        List<TableFieldModel> t2Fields = new LinkedList<>(t2.getFields());
        List<CompareTableFieldObj> compareObjs = new ArrayList<>(t1Fields.size() / 2 + t2Fields.size() / 2);
        //寻找字段1中对应的字段2，若不存在设置null
        for (TableFieldModel t1Field : t1Fields) {
            int t2Index = t2Fields.indexOf(t1Field);

            CompareTableFieldObj compareObj = new CompareTableFieldObj();
            compareObj.setSourceField(t1Field);

            if (t2Index != -1) {
                compareObj.setTargetField(t2Fields.get(t2Index));
                t2Fields.remove(t2Index);
            } else {
                compareObj.setTargetField(null);
            }
            compareObjs.add(compareObj);
        }
        //添加剩下的元素
        for (TableFieldModel t2Field : t2Fields) {
            CompareTableFieldObj compareObj = new CompareTableFieldObj();
            compareObj.setSourceField(null);
            compareObj.setTargetField(t2Field);
            compareObjs.add(compareObj);
        }
        return compareObjs;
    }

    private static class CompareTabledObj {
        private TableModel sourceTable;
        private TableModel targetTable;

        public TableModel getSourceTable() {
            return sourceTable;
        }

        public void setSourceTable(TableModel sourceTable) {
            this.sourceTable = sourceTable;
        }

        public TableModel getTargetTable() {
            return targetTable;
        }

        public void setTargetTable(TableModel targetTable) {
            this.targetTable = targetTable;
        }
    }

    private static class CompareTableFieldObj {
        private TableFieldModel sourceField;
        private TableFieldModel targetField;

        public TableFieldModel getSourceField() {
            return sourceField;
        }

        public void setSourceField(TableFieldModel sourceField) {
            this.sourceField = sourceField;
        }

        public TableFieldModel getTargetField() {
            return targetField;
        }

        public void setTargetField(TableFieldModel targetField) {
            this.targetField = targetField;
        }
    }
}
