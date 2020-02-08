package top.icdat.vermilion.core.execute;

public interface SqlBuilder {

    String build();

    SqlBuilder sql(String sql);

}
