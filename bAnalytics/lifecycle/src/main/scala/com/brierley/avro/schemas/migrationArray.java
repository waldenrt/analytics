/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.brierley.avro.schemas;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class migrationArray extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 6897727092352345473L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"migrationArray\",\"namespace\":\"com.brierley.avro.schemas\",\"fields\":[{\"name\":\"fromSegment\",\"type\":\"int\",\"doc\":\"Quantile that they were in for PREVIOUS time period\"},{\"name\":\"currentSegment\",\"type\":\"int\",\"doc\":\"Quantile that they belong to for CURRENT time period\"},{\"name\":\"migrationCount\",\"type\":\"long\",\"doc\":\"Total number that moved fromSegment -> currentSegment\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  /** Quantile that they were in for PREVIOUS time period */
  @Deprecated public int fromSegment;
  /** Quantile that they belong to for CURRENT time period */
  @Deprecated public int currentSegment;
  /** Total number that moved fromSegment -> currentSegment */
  @Deprecated public long migrationCount;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public migrationArray() {}

  /**
   * All-args constructor.
   * @param fromSegment Quantile that they were in for PREVIOUS time period
   * @param currentSegment Quantile that they belong to for CURRENT time period
   * @param migrationCount Total number that moved fromSegment -> currentSegment
   */
  public migrationArray(java.lang.Integer fromSegment, java.lang.Integer currentSegment, java.lang.Long migrationCount) {
    this.fromSegment = fromSegment;
    this.currentSegment = currentSegment;
    this.migrationCount = migrationCount;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return fromSegment;
    case 1: return currentSegment;
    case 2: return migrationCount;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: fromSegment = (java.lang.Integer)value$; break;
    case 1: currentSegment = (java.lang.Integer)value$; break;
    case 2: migrationCount = (java.lang.Long)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'fromSegment' field.
   * @return Quantile that they were in for PREVIOUS time period
   */
  public java.lang.Integer getFromSegment() {
    return fromSegment;
  }

  /**
   * Sets the value of the 'fromSegment' field.
   * Quantile that they were in for PREVIOUS time period
   * @param value the value to set.
   */
  public void setFromSegment(java.lang.Integer value) {
    this.fromSegment = value;
  }

  /**
   * Gets the value of the 'currentSegment' field.
   * @return Quantile that they belong to for CURRENT time period
   */
  public java.lang.Integer getCurrentSegment() {
    return currentSegment;
  }

  /**
   * Sets the value of the 'currentSegment' field.
   * Quantile that they belong to for CURRENT time period
   * @param value the value to set.
   */
  public void setCurrentSegment(java.lang.Integer value) {
    this.currentSegment = value;
  }

  /**
   * Gets the value of the 'migrationCount' field.
   * @return Total number that moved fromSegment -> currentSegment
   */
  public java.lang.Long getMigrationCount() {
    return migrationCount;
  }

  /**
   * Sets the value of the 'migrationCount' field.
   * Total number that moved fromSegment -> currentSegment
   * @param value the value to set.
   */
  public void setMigrationCount(java.lang.Long value) {
    this.migrationCount = value;
  }

  /**
   * Creates a new migrationArray RecordBuilder.
   * @return A new migrationArray RecordBuilder
   */
  public static com.brierley.avro.schemas.migrationArray.Builder newBuilder() {
    return new com.brierley.avro.schemas.migrationArray.Builder();
  }

  /**
   * Creates a new migrationArray RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new migrationArray RecordBuilder
   */
  public static com.brierley.avro.schemas.migrationArray.Builder newBuilder(com.brierley.avro.schemas.migrationArray.Builder other) {
    return new com.brierley.avro.schemas.migrationArray.Builder(other);
  }

  /**
   * Creates a new migrationArray RecordBuilder by copying an existing migrationArray instance.
   * @param other The existing instance to copy.
   * @return A new migrationArray RecordBuilder
   */
  public static com.brierley.avro.schemas.migrationArray.Builder newBuilder(com.brierley.avro.schemas.migrationArray other) {
    return new com.brierley.avro.schemas.migrationArray.Builder(other);
  }

  /**
   * RecordBuilder for migrationArray instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<migrationArray>
    implements org.apache.avro.data.RecordBuilder<migrationArray> {

    /** Quantile that they were in for PREVIOUS time period */
    private int fromSegment;
    /** Quantile that they belong to for CURRENT time period */
    private int currentSegment;
    /** Total number that moved fromSegment -> currentSegment */
    private long migrationCount;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.brierley.avro.schemas.migrationArray.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.fromSegment)) {
        this.fromSegment = data().deepCopy(fields()[0].schema(), other.fromSegment);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.currentSegment)) {
        this.currentSegment = data().deepCopy(fields()[1].schema(), other.currentSegment);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.migrationCount)) {
        this.migrationCount = data().deepCopy(fields()[2].schema(), other.migrationCount);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing migrationArray instance
     * @param other The existing instance to copy.
     */
    private Builder(com.brierley.avro.schemas.migrationArray other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.fromSegment)) {
        this.fromSegment = data().deepCopy(fields()[0].schema(), other.fromSegment);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.currentSegment)) {
        this.currentSegment = data().deepCopy(fields()[1].schema(), other.currentSegment);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.migrationCount)) {
        this.migrationCount = data().deepCopy(fields()[2].schema(), other.migrationCount);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'fromSegment' field.
      * Quantile that they were in for PREVIOUS time period
      * @return The value.
      */
    public java.lang.Integer getFromSegment() {
      return fromSegment;
    }

    /**
      * Sets the value of the 'fromSegment' field.
      * Quantile that they were in for PREVIOUS time period
      * @param value The value of 'fromSegment'.
      * @return This builder.
      */
    public com.brierley.avro.schemas.migrationArray.Builder setFromSegment(int value) {
      validate(fields()[0], value);
      this.fromSegment = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'fromSegment' field has been set.
      * Quantile that they were in for PREVIOUS time period
      * @return True if the 'fromSegment' field has been set, false otherwise.
      */
    public boolean hasFromSegment() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'fromSegment' field.
      * Quantile that they were in for PREVIOUS time period
      * @return This builder.
      */
    public com.brierley.avro.schemas.migrationArray.Builder clearFromSegment() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'currentSegment' field.
      * Quantile that they belong to for CURRENT time period
      * @return The value.
      */
    public java.lang.Integer getCurrentSegment() {
      return currentSegment;
    }

    /**
      * Sets the value of the 'currentSegment' field.
      * Quantile that they belong to for CURRENT time period
      * @param value The value of 'currentSegment'.
      * @return This builder.
      */
    public com.brierley.avro.schemas.migrationArray.Builder setCurrentSegment(int value) {
      validate(fields()[1], value);
      this.currentSegment = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'currentSegment' field has been set.
      * Quantile that they belong to for CURRENT time period
      * @return True if the 'currentSegment' field has been set, false otherwise.
      */
    public boolean hasCurrentSegment() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'currentSegment' field.
      * Quantile that they belong to for CURRENT time period
      * @return This builder.
      */
    public com.brierley.avro.schemas.migrationArray.Builder clearCurrentSegment() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'migrationCount' field.
      * Total number that moved fromSegment -> currentSegment
      * @return The value.
      */
    public java.lang.Long getMigrationCount() {
      return migrationCount;
    }

    /**
      * Sets the value of the 'migrationCount' field.
      * Total number that moved fromSegment -> currentSegment
      * @param value The value of 'migrationCount'.
      * @return This builder.
      */
    public com.brierley.avro.schemas.migrationArray.Builder setMigrationCount(long value) {
      validate(fields()[2], value);
      this.migrationCount = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'migrationCount' field has been set.
      * Total number that moved fromSegment -> currentSegment
      * @return True if the 'migrationCount' field has been set, false otherwise.
      */
    public boolean hasMigrationCount() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'migrationCount' field.
      * Total number that moved fromSegment -> currentSegment
      * @return This builder.
      */
    public com.brierley.avro.schemas.migrationArray.Builder clearMigrationCount() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public migrationArray build() {
      try {
        migrationArray record = new migrationArray();
        record.fromSegment = fieldSetFlags()[0] ? this.fromSegment : (java.lang.Integer) defaultValue(fields()[0]);
        record.currentSegment = fieldSetFlags()[1] ? this.currentSegment : (java.lang.Integer) defaultValue(fields()[1]);
        record.migrationCount = fieldSetFlags()[2] ? this.migrationCount : (java.lang.Long) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
