/**
 * This class is generated by jOOQ
 */
package com.rbkmoney.dudoser.domain.tables;


import com.rbkmoney.dudoser.domain.Dudos;
import com.rbkmoney.dudoser.domain.Keys;
import com.rbkmoney.dudoser.domain.tables.records.JavaobjectRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * Table for saving java objects
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Javaobject extends TableImpl<JavaobjectRecord> {

    private static final long serialVersionUID = 544196615;

    /**
     * The reference instance of <code>dudos.javaobject</code>
     */
    public static final Javaobject JAVAOBJECT = new Javaobject();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JavaobjectRecord> getRecordType() {
        return JavaobjectRecord.class;
    }

    /**
     * The column <code>dudos.javaobject.id</code>.
     */
    public final TableField<JavaobjectRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('dudos.javaobject_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>dudos.javaobject.source_id</code>.
     */
    public final TableField<JavaobjectRecord, String> SOURCE_ID = createField("source_id", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>dudos.javaobject.object</code>.
     */
    public final TableField<JavaobjectRecord, byte[]> OBJECT = createField("object", org.jooq.impl.SQLDataType.BLOB, this, "");

    /**
     * Create a <code>dudos.javaobject</code> table reference
     */
    public Javaobject() {
        this("javaobject", null);
    }

    /**
     * Create an aliased <code>dudos.javaobject</code> table reference
     */
    public Javaobject(String alias) {
        this(alias, JAVAOBJECT);
    }

    private Javaobject(String alias, Table<JavaobjectRecord> aliased) {
        this(alias, aliased, null);
    }

    private Javaobject(String alias, Table<JavaobjectRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Table for saving java objects");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Dudos.DUDOS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<JavaobjectRecord, Long> getIdentity() {
        return Keys.IDENTITY_JAVAOBJECT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JavaobjectRecord> getPrimaryKey() {
        return Keys.JAVAOBJECT_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JavaobjectRecord>> getKeys() {
        return Arrays.<UniqueKey<JavaobjectRecord>>asList(Keys.JAVAOBJECT_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Javaobject as(String alias) {
        return new Javaobject(alias, this);
    }

    /**
     * Rename this table
     */
    public Javaobject rename(String name) {
        return new Javaobject(name, null);
    }
}
