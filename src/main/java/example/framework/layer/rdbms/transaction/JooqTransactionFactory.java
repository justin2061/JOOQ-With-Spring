package example.framework.layer.rdbms.transaction;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListenerProvider;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User:ChengLiang
 * Date:2016/5/30
 * Time:12:06
 */
public class JooqTransactionFactory {

    private final Configuration config = new DefaultConfiguration();

    public JooqTransactionFactory(SpringTransactionProvider springTransactionProvider,SpringExceptionTranslationExecuteListener springExceptionTranslationExecuteListener,DataSource dataSource) {
        config.set(new SpringTransactionConnectionProvider(dataSource)).
                set(SQLDialect.MYSQL).
                set(springTransactionProvider).
                set(new DefaultExecuteListenerProvider(springExceptionTranslationExecuteListener));
    }

    public JooqTransactionFactory(DataSource ds, SQLDialect dialect) {
        this(ds, dialect, new Settings().withRenderSchema(false));
    }

    public JooqTransactionFactory(DataSource ds, SQLDialect dialect,
                                  Settings settings) {
        config.set(new SpringTransactionConnectionProvider(ds)).set(dialect).set(
                settings).set(
                new DefaultExecuteListenerProvider(
                        new SpringExceptionTranslationExecuteListener()));
    }

    public DSLContext context() {
        return DSL.using(config);
    }
}
