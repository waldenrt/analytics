/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.brierley.avro.schemas;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Balor extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -8461570446767059209L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Balor\",\"namespace\":\"com.brierley.avro.schemas\",\"fields\":[{\"name\":\"jobKey\",\"type\":\"string\",\"doc\":\"unique job identifier to find results in hbase\"},{\"name\":\"numRecords\",\"type\":\"long\",\"doc\":\"Total number of records in the given dataset used to calculate the cadence\"},{\"name\":\"completionTime\",\"type\":\"string\",\"doc\":\"The time the spark job finished and sent the record.\"},{\"name\":\"minDateBalor\",\"type\":\"string\",\"doc\":\"Beginning date after trimming datafile to match cadence.  Whole month/week\"},{\"name\":\"maxDateBalor\",\"type\":\"string\",\"doc\":\"Last date after trimming datafile to match cadence.  Whole month/week\"},{\"name\":\"balorSets\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"TimePeriodData\",\"fields\":[{\"name\":\"timePeriod\",\"type\":\"int\",\"doc\":\"The Balor time period, calculated ascending\"},{\"name\":\"newCustCount\",\"type\":\"long\",\"doc\":\"The count of customers that are considered new in the given Balor time period.  To be considered new the cust_id must not appear in the previous 2 cadence periods\"},{\"name\":\"newTxnCount\",\"type\":\"long\",\"doc\":\"The number of transactions made by NEW customers in the given Balor time period.\"},{\"name\":\"newTxnAmt\",\"type\":\"double\",\"doc\":\"The total spend of transactions made by NEW customers in the given Balor time period.\"},{\"name\":\"newDiscAmt\",\"type\":\"double\",\"doc\":\"The total discount amount of transactions made by NEW customers in the given Balor time period.\"},{\"name\":\"newItemQty\",\"type\":\"double\",\"doc\":\"The total number of items purchased by all NEW customers in the given Balor time period.\"},{\"name\":\"reactCustCount\",\"type\":\"long\",\"doc\":\"The count of customers that are considered REACTIVATED in the given Balor time period.  To be considered reactivated the cust_id must appear in the current and t-2 time period\"},{\"name\":\"reactTxnCount\",\"type\":\"long\",\"doc\":\"The number of transactions made by REACTIVATED customers in the given Balor time period.\"},{\"name\":\"reactTxnAmt\",\"type\":\"double\",\"doc\":\"The total spend of transactions made by REACTIVATED customers in the given Balor time period.\"},{\"name\":\"reactDiscAmt\",\"type\":\"double\",\"doc\":\"The total discount amount of transactions made by REACTIVATED customers in the given Balor time period.\"},{\"name\":\"reactItemQty\",\"type\":\"double\",\"doc\":\"The total number of items purchased by all REACTIVATED customers in the given Balor time period.\"},{\"name\":\"returnCustCount\",\"type\":\"long\",\"doc\":\"The count of customers that are considered RETURNING in the given Balor time period.  To be considered returning the cust_id must appear in the 2 most recent time periods, or all 3\"},{\"name\":\"returnTxnCount\",\"type\":\"long\",\"doc\":\"The number of transactions made by RETURNING customers in the given Balor time period.\"},{\"name\":\"returnTxnAmt\",\"type\":\"double\",\"doc\":\"The total spend of transactions made by RETURNING customers in the given Balor time period.\"},{\"name\":\"returnDiscAmt\",\"type\":\"double\",\"doc\":\"The total discount amount of transactions made by RETURNING customers in the given Balor time period.\"},{\"name\":\"returnItemQty\",\"type\":\"double\",\"doc\":\"The total number of items purchased by all RETURNING customers in the given Balor time period.\"},{\"name\":\"lapsedCustCount\",\"type\":\"long\",\"doc\":\"The count of customers that are considered LAPSED in the given Balor time period.  To be considered lapsed the cust_id cannot appear in the current time period\"},{\"name\":\"lapsedTxnCount\",\"type\":\"long\",\"doc\":\"The number of transactions made by LAPSED customers in the given Balor time period.\"},{\"name\":\"lapsedTxnAmt\",\"type\":\"double\",\"doc\":\"The total spend of transactions made by LAPSED customers in the given Balor time period.\"},{\"name\":\"lapsedDiscAmt\",\"type\":\"double\",\"doc\":\"The total discount amount of transactions made by LAPSED customers in the given Balor time period.\"},{\"name\":\"lapsedItemQty\",\"type\":\"double\",\"doc\":\"The total number of items purchased by all LAPSED customers in the given Balor time period.\"},{\"name\":\"custBalor\",\"type\":\"double\",\"doc\":\"The balor ratio for customer counts. ratio calculated: (new + reactivated) / lapsed\"},{\"name\":\"txnBalor\",\"type\":\"double\",\"doc\":\"The balor ratio for transaction counts per customer segment. ratio calculated: (new + reactivated) / lapsed\"},{\"name\":\"spendBalor\",\"type\":\"double\",\"doc\":\"The balor ratio for the total spend for each customer segment. ratio calculated: (new + reactivated) / lapsed\"}]}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  /** unique job identifier to find results in hbase */
  @Deprecated public java.lang.CharSequence jobKey;
  /** Total number of records in the given dataset used to calculate the cadence */
  @Deprecated public long numRecords;
  /** The time the spark job finished and sent the record. */
  @Deprecated public java.lang.CharSequence completionTime;
  /** Beginning date after trimming datafile to match cadence.  Whole month/week */
  @Deprecated public java.lang.CharSequence minDateBalor;
  /** Last date after trimming datafile to match cadence.  Whole month/week */
  @Deprecated public java.lang.CharSequence maxDateBalor;
  @Deprecated public java.util.List<com.brierley.avro.schemas.TimePeriodData> balorSets;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Balor() {}

  /**
   * All-args constructor.
   * @param jobKey unique job identifier to find results in hbase
   * @param numRecords Total number of records in the given dataset used to calculate the cadence
   * @param completionTime The time the spark job finished and sent the record.
   * @param minDateBalor Beginning date after trimming datafile to match cadence.  Whole month/week
   * @param maxDateBalor Last date after trimming datafile to match cadence.  Whole month/week
   * @param balorSets The new value for balorSets
   */
  public Balor(java.lang.CharSequence jobKey, java.lang.Long numRecords, java.lang.CharSequence completionTime, java.lang.CharSequence minDateBalor, java.lang.CharSequence maxDateBalor, java.util.List<com.brierley.avro.schemas.TimePeriodData> balorSets) {
    this.jobKey = jobKey;
    this.numRecords = numRecords;
    this.completionTime = completionTime;
    this.minDateBalor = minDateBalor;
    this.maxDateBalor = maxDateBalor;
    this.balorSets = balorSets;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return jobKey;
    case 1: return numRecords;
    case 2: return completionTime;
    case 3: return minDateBalor;
    case 4: return maxDateBalor;
    case 5: return balorSets;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: jobKey = (java.lang.CharSequence)value$; break;
    case 1: numRecords = (java.lang.Long)value$; break;
    case 2: completionTime = (java.lang.CharSequence)value$; break;
    case 3: minDateBalor = (java.lang.CharSequence)value$; break;
    case 4: maxDateBalor = (java.lang.CharSequence)value$; break;
    case 5: balorSets = (java.util.List<com.brierley.avro.schemas.TimePeriodData>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'jobKey' field.
   * @return unique job identifier to find results in hbase
   */
  public java.lang.CharSequence getJobKey() {
    return jobKey;
  }

  /**
   * Sets the value of the 'jobKey' field.
   * unique job identifier to find results in hbase
   * @param value the value to set.
   */
  public void setJobKey(java.lang.CharSequence value) {
    this.jobKey = value;
  }

  /**
   * Gets the value of the 'numRecords' field.
   * @return Total number of records in the given dataset used to calculate the cadence
   */
  public java.lang.Long getNumRecords() {
    return numRecords;
  }

  /**
   * Sets the value of the 'numRecords' field.
   * Total number of records in the given dataset used to calculate the cadence
   * @param value the value to set.
   */
  public void setNumRecords(java.lang.Long value) {
    this.numRecords = value;
  }

  /**
   * Gets the value of the 'completionTime' field.
   * @return The time the spark job finished and sent the record.
   */
  public java.lang.CharSequence getCompletionTime() {
    return completionTime;
  }

  /**
   * Sets the value of the 'completionTime' field.
   * The time the spark job finished and sent the record.
   * @param value the value to set.
   */
  public void setCompletionTime(java.lang.CharSequence value) {
    this.completionTime = value;
  }

  /**
   * Gets the value of the 'minDateBalor' field.
   * @return Beginning date after trimming datafile to match cadence.  Whole month/week
   */
  public java.lang.CharSequence getMinDateBalor() {
    return minDateBalor;
  }

  /**
   * Sets the value of the 'minDateBalor' field.
   * Beginning date after trimming datafile to match cadence.  Whole month/week
   * @param value the value to set.
   */
  public void setMinDateBalor(java.lang.CharSequence value) {
    this.minDateBalor = value;
  }

  /**
   * Gets the value of the 'maxDateBalor' field.
   * @return Last date after trimming datafile to match cadence.  Whole month/week
   */
  public java.lang.CharSequence getMaxDateBalor() {
    return maxDateBalor;
  }

  /**
   * Sets the value of the 'maxDateBalor' field.
   * Last date after trimming datafile to match cadence.  Whole month/week
   * @param value the value to set.
   */
  public void setMaxDateBalor(java.lang.CharSequence value) {
    this.maxDateBalor = value;
  }

  /**
   * Gets the value of the 'balorSets' field.
   * @return The value of the 'balorSets' field.
   */
  public java.util.List<com.brierley.avro.schemas.TimePeriodData> getBalorSets() {
    return balorSets;
  }

  /**
   * Sets the value of the 'balorSets' field.
   * @param value the value to set.
   */
  public void setBalorSets(java.util.List<com.brierley.avro.schemas.TimePeriodData> value) {
    this.balorSets = value;
  }

  /**
   * Creates a new Balor RecordBuilder.
   * @return A new Balor RecordBuilder
   */
  public static com.brierley.avro.schemas.Balor.Builder newBuilder() {
    return new com.brierley.avro.schemas.Balor.Builder();
  }

  /**
   * Creates a new Balor RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Balor RecordBuilder
   */
  public static com.brierley.avro.schemas.Balor.Builder newBuilder(com.brierley.avro.schemas.Balor.Builder other) {
    return new com.brierley.avro.schemas.Balor.Builder(other);
  }

  /**
   * Creates a new Balor RecordBuilder by copying an existing Balor instance.
   * @param other The existing instance to copy.
   * @return A new Balor RecordBuilder
   */
  public static com.brierley.avro.schemas.Balor.Builder newBuilder(com.brierley.avro.schemas.Balor other) {
    return new com.brierley.avro.schemas.Balor.Builder(other);
  }

  /**
   * RecordBuilder for Balor instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Balor>
    implements org.apache.avro.data.RecordBuilder<Balor> {

    /** unique job identifier to find results in hbase */
    private java.lang.CharSequence jobKey;
    /** Total number of records in the given dataset used to calculate the cadence */
    private long numRecords;
    /** The time the spark job finished and sent the record. */
    private java.lang.CharSequence completionTime;
    /** Beginning date after trimming datafile to match cadence.  Whole month/week */
    private java.lang.CharSequence minDateBalor;
    /** Last date after trimming datafile to match cadence.  Whole month/week */
    private java.lang.CharSequence maxDateBalor;
    private java.util.List<com.brierley.avro.schemas.TimePeriodData> balorSets;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.brierley.avro.schemas.Balor.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.jobKey)) {
        this.jobKey = data().deepCopy(fields()[0].schema(), other.jobKey);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.numRecords)) {
        this.numRecords = data().deepCopy(fields()[1].schema(), other.numRecords);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.completionTime)) {
        this.completionTime = data().deepCopy(fields()[2].schema(), other.completionTime);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.minDateBalor)) {
        this.minDateBalor = data().deepCopy(fields()[3].schema(), other.minDateBalor);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.maxDateBalor)) {
        this.maxDateBalor = data().deepCopy(fields()[4].schema(), other.maxDateBalor);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.balorSets)) {
        this.balorSets = data().deepCopy(fields()[5].schema(), other.balorSets);
        fieldSetFlags()[5] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Balor instance
     * @param other The existing instance to copy.
     */
    private Builder(com.brierley.avro.schemas.Balor other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.jobKey)) {
        this.jobKey = data().deepCopy(fields()[0].schema(), other.jobKey);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.numRecords)) {
        this.numRecords = data().deepCopy(fields()[1].schema(), other.numRecords);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.completionTime)) {
        this.completionTime = data().deepCopy(fields()[2].schema(), other.completionTime);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.minDateBalor)) {
        this.minDateBalor = data().deepCopy(fields()[3].schema(), other.minDateBalor);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.maxDateBalor)) {
        this.maxDateBalor = data().deepCopy(fields()[4].schema(), other.maxDateBalor);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.balorSets)) {
        this.balorSets = data().deepCopy(fields()[5].schema(), other.balorSets);
        fieldSetFlags()[5] = true;
      }
    }

    /**
      * Gets the value of the 'jobKey' field.
      * unique job identifier to find results in hbase
      * @return The value.
      */
    public java.lang.CharSequence getJobKey() {
      return jobKey;
    }

    /**
      * Sets the value of the 'jobKey' field.
      * unique job identifier to find results in hbase
      * @param value The value of 'jobKey'.
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder setJobKey(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.jobKey = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'jobKey' field has been set.
      * unique job identifier to find results in hbase
      * @return True if the 'jobKey' field has been set, false otherwise.
      */
    public boolean hasJobKey() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'jobKey' field.
      * unique job identifier to find results in hbase
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder clearJobKey() {
      jobKey = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'numRecords' field.
      * Total number of records in the given dataset used to calculate the cadence
      * @return The value.
      */
    public java.lang.Long getNumRecords() {
      return numRecords;
    }

    /**
      * Sets the value of the 'numRecords' field.
      * Total number of records in the given dataset used to calculate the cadence
      * @param value The value of 'numRecords'.
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder setNumRecords(long value) {
      validate(fields()[1], value);
      this.numRecords = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'numRecords' field has been set.
      * Total number of records in the given dataset used to calculate the cadence
      * @return True if the 'numRecords' field has been set, false otherwise.
      */
    public boolean hasNumRecords() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'numRecords' field.
      * Total number of records in the given dataset used to calculate the cadence
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder clearNumRecords() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'completionTime' field.
      * The time the spark job finished and sent the record.
      * @return The value.
      */
    public java.lang.CharSequence getCompletionTime() {
      return completionTime;
    }

    /**
      * Sets the value of the 'completionTime' field.
      * The time the spark job finished and sent the record.
      * @param value The value of 'completionTime'.
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder setCompletionTime(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.completionTime = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'completionTime' field has been set.
      * The time the spark job finished and sent the record.
      * @return True if the 'completionTime' field has been set, false otherwise.
      */
    public boolean hasCompletionTime() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'completionTime' field.
      * The time the spark job finished and sent the record.
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder clearCompletionTime() {
      completionTime = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'minDateBalor' field.
      * Beginning date after trimming datafile to match cadence.  Whole month/week
      * @return The value.
      */
    public java.lang.CharSequence getMinDateBalor() {
      return minDateBalor;
    }

    /**
      * Sets the value of the 'minDateBalor' field.
      * Beginning date after trimming datafile to match cadence.  Whole month/week
      * @param value The value of 'minDateBalor'.
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder setMinDateBalor(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.minDateBalor = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'minDateBalor' field has been set.
      * Beginning date after trimming datafile to match cadence.  Whole month/week
      * @return True if the 'minDateBalor' field has been set, false otherwise.
      */
    public boolean hasMinDateBalor() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'minDateBalor' field.
      * Beginning date after trimming datafile to match cadence.  Whole month/week
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder clearMinDateBalor() {
      minDateBalor = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'maxDateBalor' field.
      * Last date after trimming datafile to match cadence.  Whole month/week
      * @return The value.
      */
    public java.lang.CharSequence getMaxDateBalor() {
      return maxDateBalor;
    }

    /**
      * Sets the value of the 'maxDateBalor' field.
      * Last date after trimming datafile to match cadence.  Whole month/week
      * @param value The value of 'maxDateBalor'.
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder setMaxDateBalor(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.maxDateBalor = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'maxDateBalor' field has been set.
      * Last date after trimming datafile to match cadence.  Whole month/week
      * @return True if the 'maxDateBalor' field has been set, false otherwise.
      */
    public boolean hasMaxDateBalor() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'maxDateBalor' field.
      * Last date after trimming datafile to match cadence.  Whole month/week
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder clearMaxDateBalor() {
      maxDateBalor = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'balorSets' field.
      * @return The value.
      */
    public java.util.List<com.brierley.avro.schemas.TimePeriodData> getBalorSets() {
      return balorSets;
    }

    /**
      * Sets the value of the 'balorSets' field.
      * @param value The value of 'balorSets'.
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder setBalorSets(java.util.List<com.brierley.avro.schemas.TimePeriodData> value) {
      validate(fields()[5], value);
      this.balorSets = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'balorSets' field has been set.
      * @return True if the 'balorSets' field has been set, false otherwise.
      */
    public boolean hasBalorSets() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'balorSets' field.
      * @return This builder.
      */
    public com.brierley.avro.schemas.Balor.Builder clearBalorSets() {
      balorSets = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    public Balor build() {
      try {
        Balor record = new Balor();
        record.jobKey = fieldSetFlags()[0] ? this.jobKey : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.numRecords = fieldSetFlags()[1] ? this.numRecords : (java.lang.Long) defaultValue(fields()[1]);
        record.completionTime = fieldSetFlags()[2] ? this.completionTime : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.minDateBalor = fieldSetFlags()[3] ? this.minDateBalor : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.maxDateBalor = fieldSetFlags()[4] ? this.maxDateBalor : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.balorSets = fieldSetFlags()[5] ? this.balorSets : (java.util.List<com.brierley.avro.schemas.TimePeriodData>) defaultValue(fields()[5]);
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
