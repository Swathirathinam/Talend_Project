
package talend_project.file_read_job_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.MDM;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.SQLike;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: File_Read_job Purpose: <br>
 * Description: <br>
 * 
 * @author Rathinam, Swathi
 * @version 8.0.1.20241016_1624-patch
 * @status
 */
public class File_Read_job implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "File_Read_job.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(File_Read_job.class);

	protected static void logIgnoredError(String message, Throwable cause) {
		log.error(message, cause);

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	public static String taskExecutionId = null;

	public static String jobExecutionId = java.util.UUID.randomUUID().toString();;

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	protected java.util.Map<String, String> defaultProperties = new java.util.HashMap<String, String>();
	protected java.util.Map<String, String> additionalProperties = new java.util.HashMap<String, String>();

	public java.util.Map<String, String> getDefaultProperties() {
		return this.defaultProperties;
	}

	public java.util.Map<String, String> getAdditionalProperties() {
		return this.additionalProperties;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "File_Read_job";
	private final String projectName = "TALEND_PROJECT";
	public Integer errorCode = null;
	private String currentComponent = "";
	public static boolean isStandaloneMS = Boolean.valueOf("false");

	private void s(final String component) {
		try {
			org.talend.metrics.DataReadTracker.setCurrentComponent(jobName, component);
		} catch (Exception | NoClassDefFoundError e) {
			// ignore
		}
	}

	private void mdc(final String subJobName, final String subJobPidPrefix) {
		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", subJobName);
		org.slf4j.MDC.put("_subJobPid", subJobPidPrefix + subJobPidCounter.getAndIncrement());
	}

	private void sh(final String componentId) {
		ok_Hash.put(componentId, false);
		start_Hash.put(componentId, System.currentTimeMillis());
	}

	{
		s("none");
	}

	private String cLabel = null;

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
			"_YUeo0J13Ee-uTKnoCo_rNA", "0.1");
	private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;

	private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;

		private String currentComponent = null;
		private String cLabel = null;

		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		private TalendException(Exception e, String errorComponent, String errorComponentLabel,
				final java.util.Map<String, Object> globalMap) {
			this(e, errorComponent, globalMap);
			this.cLabel = errorComponentLabel;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					File_Read_job.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(File_Read_job.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
						if (enableLogStash) {
							talendJobLog.addJobExceptionMessage(currentComponent, cLabel, null, e);
							talendJobLogProcess(globalMap);
						}
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tFileInputExcel_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputExcel_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputExcel_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputExcel_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputExcel_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputExcel_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tSortRow_2_SortOut_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		tSortRow_2_SortIn_error(exception, errorComponent, globalMap);

	}

	public void tSortRow_2_SortIn_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputExcel_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAggregateRow_1_AGGOUT_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		tAggregateRow_1_AGGIN_error(exception, errorComponent, globalMap);

	}

	public void tAggregateRow_1_AGGIN_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputExcel_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputExcel_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
		final static byte[] commonByteArrayLock_TALEND_PROJECT_File_Read_job = new byte[0];
		static byte[] commonByteArray_TALEND_PROJECT_File_Read_job = new byte[0];

		public Integer Id;

		public Integer getId() {
			return this.Id;
		}

		public Boolean IdIsNullable() {
			return true;
		}

		public Boolean IdIsKey() {
			return false;
		}

		public Integer IdLength() {
			return null;
		}

		public Integer IdPrecision() {
			return null;
		}

		public String IdDefault() {

			return null;

		}

		public String IdComment() {

			return "";

		}

		public String IdPattern() {

			return "";

		}

		public String IdOriginalDbColumnName() {

			return "Id";

		}

		public String Players_name;

		public String getPlayers_name() {
			return this.Players_name;
		}

		public Boolean Players_nameIsNullable() {
			return true;
		}

		public Boolean Players_nameIsKey() {
			return false;
		}

		public Integer Players_nameLength() {
			return null;
		}

		public Integer Players_namePrecision() {
			return null;
		}

		public String Players_nameDefault() {

			return null;

		}

		public String Players_nameComment() {

			return "";

		}

		public String Players_namePattern() {

			return "";

		}

		public String Players_nameOriginalDbColumnName() {

			return "Players_name";

		}

		public Integer Jersey_number;

		public Integer getJersey_number() {
			return this.Jersey_number;
		}

		public Boolean Jersey_numberIsNullable() {
			return true;
		}

		public Boolean Jersey_numberIsKey() {
			return false;
		}

		public Integer Jersey_numberLength() {
			return null;
		}

		public Integer Jersey_numberPrecision() {
			return null;
		}

		public String Jersey_numberDefault() {

			return null;

		}

		public String Jersey_numberComment() {

			return "";

		}

		public String Jersey_numberPattern() {

			return "";

		}

		public String Jersey_numberOriginalDbColumnName() {

			return "Jersey_number";

		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public Boolean CityIsNullable() {
			return true;
		}

		public Boolean CityIsKey() {
			return false;
		}

		public Integer CityLength() {
			return null;
		}

		public Integer CityPrecision() {
			return null;
		}

		public String CityDefault() {

			return null;

		}

		public String CityComment() {

			return "";

		}

		public String CityPattern() {

			return "";

		}

		public String CityOriginalDbColumnName() {

			return "City";

		}

		public Integer Max_Score;

		public Integer getMax_Score() {
			return this.Max_Score;
		}

		public Boolean Max_ScoreIsNullable() {
			return true;
		}

		public Boolean Max_ScoreIsKey() {
			return false;
		}

		public Integer Max_ScoreLength() {
			return null;
		}

		public Integer Max_ScorePrecision() {
			return null;
		}

		public String Max_ScoreDefault() {

			return null;

		}

		public String Max_ScoreComment() {

			return "";

		}

		public String Max_ScorePattern() {

			return "";

		}

		public String Max_ScoreOriginalDbColumnName() {

			return "Max_Score";

		}

		public java.util.Date Date_of_match;

		public java.util.Date getDate_of_match() {
			return this.Date_of_match;
		}

		public Boolean Date_of_matchIsNullable() {
			return true;
		}

		public Boolean Date_of_matchIsKey() {
			return false;
		}

		public Integer Date_of_matchLength() {
			return null;
		}

		public Integer Date_of_matchPrecision() {
			return null;
		}

		public String Date_of_matchDefault() {

			return null;

		}

		public String Date_of_matchComment() {

			return "";

		}

		public String Date_of_matchPattern() {

			return "dd-MM-yyyy";

		}

		public String Date_of_matchOriginalDbColumnName() {

			return "Date_of_match";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Max_Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Max_Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Max_Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Max_Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Id=" + String.valueOf(Id));
			sb.append(",Players_name=" + Players_name);
			sb.append(",Jersey_number=" + String.valueOf(Jersey_number));
			sb.append(",City=" + City);
			sb.append(",Max_Score=" + String.valueOf(Max_Score));
			sb.append(",Date_of_match=" + String.valueOf(Date_of_match));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Id == null) {
				sb.append("<null>");
			} else {
				sb.append(Id);
			}

			sb.append("|");

			if (Players_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Players_name);
			}

			sb.append("|");

			if (Jersey_number == null) {
				sb.append("<null>");
			} else {
				sb.append(Jersey_number);
			}

			sb.append("|");

			if (City == null) {
				sb.append("<null>");
			} else {
				sb.append(City);
			}

			sb.append("|");

			if (Max_Score == null) {
				sb.append("<null>");
			} else {
				sb.append(Max_Score);
			}

			sb.append("|");

			if (Date_of_match == null) {
				sb.append("<null>");
			} else {
				sb.append(Date_of_match);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class OnRowsEndStructtAggregateRow_1
			implements routines.system.IPersistableRow<OnRowsEndStructtAggregateRow_1> {
		final static byte[] commonByteArrayLock_TALEND_PROJECT_File_Read_job = new byte[0];
		static byte[] commonByteArray_TALEND_PROJECT_File_Read_job = new byte[0];

		public Integer Id;

		public Integer getId() {
			return this.Id;
		}

		public Boolean IdIsNullable() {
			return true;
		}

		public Boolean IdIsKey() {
			return false;
		}

		public Integer IdLength() {
			return null;
		}

		public Integer IdPrecision() {
			return null;
		}

		public String IdDefault() {

			return null;

		}

		public String IdComment() {

			return "";

		}

		public String IdPattern() {

			return "";

		}

		public String IdOriginalDbColumnName() {

			return "Id";

		}

		public String Players_name;

		public String getPlayers_name() {
			return this.Players_name;
		}

		public Boolean Players_nameIsNullable() {
			return true;
		}

		public Boolean Players_nameIsKey() {
			return false;
		}

		public Integer Players_nameLength() {
			return null;
		}

		public Integer Players_namePrecision() {
			return null;
		}

		public String Players_nameDefault() {

			return null;

		}

		public String Players_nameComment() {

			return "";

		}

		public String Players_namePattern() {

			return "";

		}

		public String Players_nameOriginalDbColumnName() {

			return "Players_name";

		}

		public Integer Jersey_number;

		public Integer getJersey_number() {
			return this.Jersey_number;
		}

		public Boolean Jersey_numberIsNullable() {
			return true;
		}

		public Boolean Jersey_numberIsKey() {
			return false;
		}

		public Integer Jersey_numberLength() {
			return null;
		}

		public Integer Jersey_numberPrecision() {
			return null;
		}

		public String Jersey_numberDefault() {

			return null;

		}

		public String Jersey_numberComment() {

			return "";

		}

		public String Jersey_numberPattern() {

			return "";

		}

		public String Jersey_numberOriginalDbColumnName() {

			return "Jersey_number";

		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public Boolean CityIsNullable() {
			return true;
		}

		public Boolean CityIsKey() {
			return false;
		}

		public Integer CityLength() {
			return null;
		}

		public Integer CityPrecision() {
			return null;
		}

		public String CityDefault() {

			return null;

		}

		public String CityComment() {

			return "";

		}

		public String CityPattern() {

			return "";

		}

		public String CityOriginalDbColumnName() {

			return "City";

		}

		public Integer Max_Score;

		public Integer getMax_Score() {
			return this.Max_Score;
		}

		public Boolean Max_ScoreIsNullable() {
			return true;
		}

		public Boolean Max_ScoreIsKey() {
			return false;
		}

		public Integer Max_ScoreLength() {
			return null;
		}

		public Integer Max_ScorePrecision() {
			return null;
		}

		public String Max_ScoreDefault() {

			return null;

		}

		public String Max_ScoreComment() {

			return "";

		}

		public String Max_ScorePattern() {

			return "";

		}

		public String Max_ScoreOriginalDbColumnName() {

			return "Max_Score";

		}

		public java.util.Date Date_of_match;

		public java.util.Date getDate_of_match() {
			return this.Date_of_match;
		}

		public Boolean Date_of_matchIsNullable() {
			return true;
		}

		public Boolean Date_of_matchIsKey() {
			return false;
		}

		public Integer Date_of_matchLength() {
			return null;
		}

		public Integer Date_of_matchPrecision() {
			return null;
		}

		public String Date_of_matchDefault() {

			return null;

		}

		public String Date_of_matchComment() {

			return "";

		}

		public String Date_of_matchPattern() {

			return "dd-MM-yyyy";

		}

		public String Date_of_matchOriginalDbColumnName() {

			return "Date_of_match";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Max_Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Max_Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Max_Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Max_Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Id=" + String.valueOf(Id));
			sb.append(",Players_name=" + Players_name);
			sb.append(",Jersey_number=" + String.valueOf(Jersey_number));
			sb.append(",City=" + City);
			sb.append(",Max_Score=" + String.valueOf(Max_Score));
			sb.append(",Date_of_match=" + String.valueOf(Date_of_match));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Id == null) {
				sb.append("<null>");
			} else {
				sb.append(Id);
			}

			sb.append("|");

			if (Players_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Players_name);
			}

			sb.append("|");

			if (Jersey_number == null) {
				sb.append("<null>");
			} else {
				sb.append(Jersey_number);
			}

			sb.append("|");

			if (City == null) {
				sb.append("<null>");
			} else {
				sb.append(City);
			}

			sb.append("|");

			if (Max_Score == null) {
				sb.append("<null>");
			} else {
				sb.append(Max_Score);
			}

			sb.append("|");

			if (Date_of_match == null) {
				sb.append("<null>");
			} else {
				sb.append(Date_of_match);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(OnRowsEndStructtAggregateRow_1 other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class IndianaPolisStruct implements routines.system.IPersistableRow<IndianaPolisStruct> {
		final static byte[] commonByteArrayLock_TALEND_PROJECT_File_Read_job = new byte[0];
		static byte[] commonByteArray_TALEND_PROJECT_File_Read_job = new byte[0];

		public Integer Id;

		public Integer getId() {
			return this.Id;
		}

		public Boolean IdIsNullable() {
			return true;
		}

		public Boolean IdIsKey() {
			return false;
		}

		public Integer IdLength() {
			return null;
		}

		public Integer IdPrecision() {
			return null;
		}

		public String IdDefault() {

			return null;

		}

		public String IdComment() {

			return "";

		}

		public String IdPattern() {

			return "";

		}

		public String IdOriginalDbColumnName() {

			return "Id";

		}

		public String Players_name;

		public String getPlayers_name() {
			return this.Players_name;
		}

		public Boolean Players_nameIsNullable() {
			return true;
		}

		public Boolean Players_nameIsKey() {
			return false;
		}

		public Integer Players_nameLength() {
			return null;
		}

		public Integer Players_namePrecision() {
			return null;
		}

		public String Players_nameDefault() {

			return null;

		}

		public String Players_nameComment() {

			return "";

		}

		public String Players_namePattern() {

			return "";

		}

		public String Players_nameOriginalDbColumnName() {

			return "Players_name";

		}

		public Integer Jersey_number;

		public Integer getJersey_number() {
			return this.Jersey_number;
		}

		public Boolean Jersey_numberIsNullable() {
			return true;
		}

		public Boolean Jersey_numberIsKey() {
			return false;
		}

		public Integer Jersey_numberLength() {
			return null;
		}

		public Integer Jersey_numberPrecision() {
			return null;
		}

		public String Jersey_numberDefault() {

			return null;

		}

		public String Jersey_numberComment() {

			return "";

		}

		public String Jersey_numberPattern() {

			return "";

		}

		public String Jersey_numberOriginalDbColumnName() {

			return "Jersey_number";

		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public Boolean CityIsNullable() {
			return true;
		}

		public Boolean CityIsKey() {
			return false;
		}

		public Integer CityLength() {
			return null;
		}

		public Integer CityPrecision() {
			return null;
		}

		public String CityDefault() {

			return null;

		}

		public String CityComment() {

			return "";

		}

		public String CityPattern() {

			return "";

		}

		public String CityOriginalDbColumnName() {

			return "City";

		}

		public Integer Score;

		public Integer getScore() {
			return this.Score;
		}

		public Boolean ScoreIsNullable() {
			return true;
		}

		public Boolean ScoreIsKey() {
			return false;
		}

		public Integer ScoreLength() {
			return null;
		}

		public Integer ScorePrecision() {
			return null;
		}

		public String ScoreDefault() {

			return null;

		}

		public String ScoreComment() {

			return "";

		}

		public String ScorePattern() {

			return "";

		}

		public String ScoreOriginalDbColumnName() {

			return "Score";

		}

		public java.util.Date Date_of_match;

		public java.util.Date getDate_of_match() {
			return this.Date_of_match;
		}

		public Boolean Date_of_matchIsNullable() {
			return true;
		}

		public Boolean Date_of_matchIsKey() {
			return false;
		}

		public Integer Date_of_matchLength() {
			return null;
		}

		public Integer Date_of_matchPrecision() {
			return null;
		}

		public String Date_of_matchDefault() {

			return null;

		}

		public String Date_of_matchComment() {

			return "";

		}

		public String Date_of_matchPattern() {

			return "dd-MM-yyyy";

		}

		public String Date_of_matchOriginalDbColumnName() {

			return "Date_of_match";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Id=" + String.valueOf(Id));
			sb.append(",Players_name=" + Players_name);
			sb.append(",Jersey_number=" + String.valueOf(Jersey_number));
			sb.append(",City=" + City);
			sb.append(",Score=" + String.valueOf(Score));
			sb.append(",Date_of_match=" + String.valueOf(Date_of_match));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Id == null) {
				sb.append("<null>");
			} else {
				sb.append(Id);
			}

			sb.append("|");

			if (Players_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Players_name);
			}

			sb.append("|");

			if (Jersey_number == null) {
				sb.append("<null>");
			} else {
				sb.append(Jersey_number);
			}

			sb.append("|");

			if (City == null) {
				sb.append("<null>");
			} else {
				sb.append(City);
			}

			sb.append("|");

			if (Score == null) {
				sb.append("<null>");
			} else {
				sb.append(Score);
			}

			sb.append("|");

			if (Date_of_match == null) {
				sb.append("<null>");
			} else {
				sb.append(Date_of_match);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(IndianaPolisStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class Max_scoreStruct implements routines.system.IPersistableRow<Max_scoreStruct> {
		final static byte[] commonByteArrayLock_TALEND_PROJECT_File_Read_job = new byte[0];
		static byte[] commonByteArray_TALEND_PROJECT_File_Read_job = new byte[0];

		public Integer Id;

		public Integer getId() {
			return this.Id;
		}

		public Boolean IdIsNullable() {
			return true;
		}

		public Boolean IdIsKey() {
			return false;
		}

		public Integer IdLength() {
			return null;
		}

		public Integer IdPrecision() {
			return null;
		}

		public String IdDefault() {

			return null;

		}

		public String IdComment() {

			return "";

		}

		public String IdPattern() {

			return "";

		}

		public String IdOriginalDbColumnName() {

			return "Id";

		}

		public String Players_name;

		public String getPlayers_name() {
			return this.Players_name;
		}

		public Boolean Players_nameIsNullable() {
			return true;
		}

		public Boolean Players_nameIsKey() {
			return false;
		}

		public Integer Players_nameLength() {
			return null;
		}

		public Integer Players_namePrecision() {
			return null;
		}

		public String Players_nameDefault() {

			return null;

		}

		public String Players_nameComment() {

			return "";

		}

		public String Players_namePattern() {

			return "";

		}

		public String Players_nameOriginalDbColumnName() {

			return "Players_name";

		}

		public Integer Jersey_number;

		public Integer getJersey_number() {
			return this.Jersey_number;
		}

		public Boolean Jersey_numberIsNullable() {
			return true;
		}

		public Boolean Jersey_numberIsKey() {
			return false;
		}

		public Integer Jersey_numberLength() {
			return null;
		}

		public Integer Jersey_numberPrecision() {
			return null;
		}

		public String Jersey_numberDefault() {

			return null;

		}

		public String Jersey_numberComment() {

			return "";

		}

		public String Jersey_numberPattern() {

			return "";

		}

		public String Jersey_numberOriginalDbColumnName() {

			return "Jersey_number";

		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public Boolean CityIsNullable() {
			return true;
		}

		public Boolean CityIsKey() {
			return false;
		}

		public Integer CityLength() {
			return null;
		}

		public Integer CityPrecision() {
			return null;
		}

		public String CityDefault() {

			return null;

		}

		public String CityComment() {

			return "";

		}

		public String CityPattern() {

			return "";

		}

		public String CityOriginalDbColumnName() {

			return "City";

		}

		public Integer Score;

		public Integer getScore() {
			return this.Score;
		}

		public Boolean ScoreIsNullable() {
			return true;
		}

		public Boolean ScoreIsKey() {
			return false;
		}

		public Integer ScoreLength() {
			return null;
		}

		public Integer ScorePrecision() {
			return null;
		}

		public String ScoreDefault() {

			return null;

		}

		public String ScoreComment() {

			return "";

		}

		public String ScorePattern() {

			return "";

		}

		public String ScoreOriginalDbColumnName() {

			return "Score";

		}

		public java.util.Date Date_of_match;

		public java.util.Date getDate_of_match() {
			return this.Date_of_match;
		}

		public Boolean Date_of_matchIsNullable() {
			return true;
		}

		public Boolean Date_of_matchIsKey() {
			return false;
		}

		public Integer Date_of_matchLength() {
			return null;
		}

		public Integer Date_of_matchPrecision() {
			return null;
		}

		public String Date_of_matchDefault() {

			return null;

		}

		public String Date_of_matchComment() {

			return "";

		}

		public String Date_of_matchPattern() {

			return "dd-MM-yyyy";

		}

		public String Date_of_matchOriginalDbColumnName() {

			return "Date_of_match";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Id=" + String.valueOf(Id));
			sb.append(",Players_name=" + Players_name);
			sb.append(",Jersey_number=" + String.valueOf(Jersey_number));
			sb.append(",City=" + City);
			sb.append(",Score=" + String.valueOf(Score));
			sb.append(",Date_of_match=" + String.valueOf(Date_of_match));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Id == null) {
				sb.append("<null>");
			} else {
				sb.append(Id);
			}

			sb.append("|");

			if (Players_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Players_name);
			}

			sb.append("|");

			if (Jersey_number == null) {
				sb.append("<null>");
			} else {
				sb.append(Jersey_number);
			}

			sb.append("|");

			if (City == null) {
				sb.append("<null>");
			} else {
				sb.append(City);
			}

			sb.append("|");

			if (Score == null) {
				sb.append("<null>");
			} else {
				sb.append(Score);
			}

			sb.append("|");

			if (Date_of_match == null) {
				sb.append("<null>");
			} else {
				sb.append(Date_of_match);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(Max_scoreStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_TALEND_PROJECT_File_Read_job = new byte[0];
		static byte[] commonByteArray_TALEND_PROJECT_File_Read_job = new byte[0];

		public Integer Id;

		public Integer getId() {
			return this.Id;
		}

		public Boolean IdIsNullable() {
			return true;
		}

		public Boolean IdIsKey() {
			return false;
		}

		public Integer IdLength() {
			return null;
		}

		public Integer IdPrecision() {
			return null;
		}

		public String IdDefault() {

			return null;

		}

		public String IdComment() {

			return "";

		}

		public String IdPattern() {

			return "";

		}

		public String IdOriginalDbColumnName() {

			return "Id";

		}

		public String Players_name;

		public String getPlayers_name() {
			return this.Players_name;
		}

		public Boolean Players_nameIsNullable() {
			return true;
		}

		public Boolean Players_nameIsKey() {
			return false;
		}

		public Integer Players_nameLength() {
			return null;
		}

		public Integer Players_namePrecision() {
			return null;
		}

		public String Players_nameDefault() {

			return null;

		}

		public String Players_nameComment() {

			return "";

		}

		public String Players_namePattern() {

			return "";

		}

		public String Players_nameOriginalDbColumnName() {

			return "Players_name";

		}

		public Integer Jersey_number;

		public Integer getJersey_number() {
			return this.Jersey_number;
		}

		public Boolean Jersey_numberIsNullable() {
			return true;
		}

		public Boolean Jersey_numberIsKey() {
			return false;
		}

		public Integer Jersey_numberLength() {
			return null;
		}

		public Integer Jersey_numberPrecision() {
			return null;
		}

		public String Jersey_numberDefault() {

			return null;

		}

		public String Jersey_numberComment() {

			return "";

		}

		public String Jersey_numberPattern() {

			return "";

		}

		public String Jersey_numberOriginalDbColumnName() {

			return "Jersey_number";

		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public Boolean CityIsNullable() {
			return true;
		}

		public Boolean CityIsKey() {
			return false;
		}

		public Integer CityLength() {
			return null;
		}

		public Integer CityPrecision() {
			return null;
		}

		public String CityDefault() {

			return null;

		}

		public String CityComment() {

			return "";

		}

		public String CityPattern() {

			return "";

		}

		public String CityOriginalDbColumnName() {

			return "City";

		}

		public Integer Score;

		public Integer getScore() {
			return this.Score;
		}

		public Boolean ScoreIsNullable() {
			return true;
		}

		public Boolean ScoreIsKey() {
			return false;
		}

		public Integer ScoreLength() {
			return null;
		}

		public Integer ScorePrecision() {
			return null;
		}

		public String ScoreDefault() {

			return null;

		}

		public String ScoreComment() {

			return "";

		}

		public String ScorePattern() {

			return "";

		}

		public String ScoreOriginalDbColumnName() {

			return "Score";

		}

		public java.util.Date Date_of_match;

		public java.util.Date getDate_of_match() {
			return this.Date_of_match;
		}

		public Boolean Date_of_matchIsNullable() {
			return true;
		}

		public Boolean Date_of_matchIsKey() {
			return false;
		}

		public Integer Date_of_matchLength() {
			return null;
		}

		public Integer Date_of_matchPrecision() {
			return null;
		}

		public String Date_of_matchDefault() {

			return null;

		}

		public String Date_of_matchComment() {

			return "";

		}

		public String Date_of_matchPattern() {

			return "dd-MM-yyyy";

		}

		public String Date_of_matchOriginalDbColumnName() {

			return "Date_of_match";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Id=" + String.valueOf(Id));
			sb.append(",Players_name=" + Players_name);
			sb.append(",Jersey_number=" + String.valueOf(Jersey_number));
			sb.append(",City=" + City);
			sb.append(",Score=" + String.valueOf(Score));
			sb.append(",Date_of_match=" + String.valueOf(Date_of_match));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Id == null) {
				sb.append("<null>");
			} else {
				sb.append(Id);
			}

			sb.append("|");

			if (Players_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Players_name);
			}

			sb.append("|");

			if (Jersey_number == null) {
				sb.append("<null>");
			} else {
				sb.append(Jersey_number);
			}

			sb.append("|");

			if (City == null) {
				sb.append("<null>");
			} else {
				sb.append(City);
			}

			sb.append("|");

			if (Score == null) {
				sb.append("<null>");
			} else {
				sb.append(Score);
			}

			sb.append("|");

			if (Date_of_match == null) {
				sb.append("<null>");
			} else {
				sb.append(Date_of_match);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class OnRowsEndStructtSortRow_2
			implements routines.system.IPersistableRow<OnRowsEndStructtSortRow_2> {
		final static byte[] commonByteArrayLock_TALEND_PROJECT_File_Read_job = new byte[0];
		static byte[] commonByteArray_TALEND_PROJECT_File_Read_job = new byte[0];

		public Integer Id;

		public Integer getId() {
			return this.Id;
		}

		public Boolean IdIsNullable() {
			return true;
		}

		public Boolean IdIsKey() {
			return false;
		}

		public Integer IdLength() {
			return null;
		}

		public Integer IdPrecision() {
			return null;
		}

		public String IdDefault() {

			return null;

		}

		public String IdComment() {

			return "";

		}

		public String IdPattern() {

			return "";

		}

		public String IdOriginalDbColumnName() {

			return "Id";

		}

		public String Players_name;

		public String getPlayers_name() {
			return this.Players_name;
		}

		public Boolean Players_nameIsNullable() {
			return true;
		}

		public Boolean Players_nameIsKey() {
			return false;
		}

		public Integer Players_nameLength() {
			return null;
		}

		public Integer Players_namePrecision() {
			return null;
		}

		public String Players_nameDefault() {

			return null;

		}

		public String Players_nameComment() {

			return "";

		}

		public String Players_namePattern() {

			return "";

		}

		public String Players_nameOriginalDbColumnName() {

			return "Players_name";

		}

		public Integer Jersey_number;

		public Integer getJersey_number() {
			return this.Jersey_number;
		}

		public Boolean Jersey_numberIsNullable() {
			return true;
		}

		public Boolean Jersey_numberIsKey() {
			return false;
		}

		public Integer Jersey_numberLength() {
			return null;
		}

		public Integer Jersey_numberPrecision() {
			return null;
		}

		public String Jersey_numberDefault() {

			return null;

		}

		public String Jersey_numberComment() {

			return "";

		}

		public String Jersey_numberPattern() {

			return "";

		}

		public String Jersey_numberOriginalDbColumnName() {

			return "Jersey_number";

		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public Boolean CityIsNullable() {
			return true;
		}

		public Boolean CityIsKey() {
			return false;
		}

		public Integer CityLength() {
			return null;
		}

		public Integer CityPrecision() {
			return null;
		}

		public String CityDefault() {

			return null;

		}

		public String CityComment() {

			return "";

		}

		public String CityPattern() {

			return "";

		}

		public String CityOriginalDbColumnName() {

			return "City";

		}

		public Integer Score;

		public Integer getScore() {
			return this.Score;
		}

		public Boolean ScoreIsNullable() {
			return true;
		}

		public Boolean ScoreIsKey() {
			return false;
		}

		public Integer ScoreLength() {
			return null;
		}

		public Integer ScorePrecision() {
			return null;
		}

		public String ScoreDefault() {

			return null;

		}

		public String ScoreComment() {

			return "";

		}

		public String ScorePattern() {

			return "";

		}

		public String ScoreOriginalDbColumnName() {

			return "Score";

		}

		public java.util.Date Date_of_match;

		public java.util.Date getDate_of_match() {
			return this.Date_of_match;
		}

		public Boolean Date_of_matchIsNullable() {
			return true;
		}

		public Boolean Date_of_matchIsKey() {
			return false;
		}

		public Integer Date_of_matchLength() {
			return null;
		}

		public Integer Date_of_matchPrecision() {
			return null;
		}

		public String Date_of_matchDefault() {

			return null;

		}

		public String Date_of_matchComment() {

			return "";

		}

		public String Date_of_matchPattern() {

			return "dd-MM-yyyy";

		}

		public String Date_of_matchOriginalDbColumnName() {

			return "Date_of_match";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Id=" + String.valueOf(Id));
			sb.append(",Players_name=" + Players_name);
			sb.append(",Jersey_number=" + String.valueOf(Jersey_number));
			sb.append(",City=" + City);
			sb.append(",Score=" + String.valueOf(Score));
			sb.append(",Date_of_match=" + String.valueOf(Date_of_match));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Id == null) {
				sb.append("<null>");
			} else {
				sb.append(Id);
			}

			sb.append("|");

			if (Players_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Players_name);
			}

			sb.append("|");

			if (Jersey_number == null) {
				sb.append("<null>");
			} else {
				sb.append(Jersey_number);
			}

			sb.append("|");

			if (City == null) {
				sb.append("<null>");
			} else {
				sb.append(City);
			}

			sb.append("|");

			if (Score == null) {
				sb.append("<null>");
			} else {
				sb.append(Score);
			}

			sb.append("|");

			if (Date_of_match == null) {
				sb.append("<null>");
			} else {
				sb.append(Date_of_match);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(OnRowsEndStructtSortRow_2 other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_TALEND_PROJECT_File_Read_job = new byte[0];
		static byte[] commonByteArray_TALEND_PROJECT_File_Read_job = new byte[0];

		public Integer Id;

		public Integer getId() {
			return this.Id;
		}

		public Boolean IdIsNullable() {
			return true;
		}

		public Boolean IdIsKey() {
			return false;
		}

		public Integer IdLength() {
			return null;
		}

		public Integer IdPrecision() {
			return null;
		}

		public String IdDefault() {

			return null;

		}

		public String IdComment() {

			return "";

		}

		public String IdPattern() {

			return "";

		}

		public String IdOriginalDbColumnName() {

			return "Id";

		}

		public String Players_name;

		public String getPlayers_name() {
			return this.Players_name;
		}

		public Boolean Players_nameIsNullable() {
			return true;
		}

		public Boolean Players_nameIsKey() {
			return false;
		}

		public Integer Players_nameLength() {
			return null;
		}

		public Integer Players_namePrecision() {
			return null;
		}

		public String Players_nameDefault() {

			return null;

		}

		public String Players_nameComment() {

			return "";

		}

		public String Players_namePattern() {

			return "";

		}

		public String Players_nameOriginalDbColumnName() {

			return "Players_name";

		}

		public Integer Jersey_number;

		public Integer getJersey_number() {
			return this.Jersey_number;
		}

		public Boolean Jersey_numberIsNullable() {
			return true;
		}

		public Boolean Jersey_numberIsKey() {
			return false;
		}

		public Integer Jersey_numberLength() {
			return null;
		}

		public Integer Jersey_numberPrecision() {
			return null;
		}

		public String Jersey_numberDefault() {

			return null;

		}

		public String Jersey_numberComment() {

			return "";

		}

		public String Jersey_numberPattern() {

			return "";

		}

		public String Jersey_numberOriginalDbColumnName() {

			return "Jersey_number";

		}

		public String City;

		public String getCity() {
			return this.City;
		}

		public Boolean CityIsNullable() {
			return true;
		}

		public Boolean CityIsKey() {
			return false;
		}

		public Integer CityLength() {
			return null;
		}

		public Integer CityPrecision() {
			return null;
		}

		public String CityDefault() {

			return null;

		}

		public String CityComment() {

			return "";

		}

		public String CityPattern() {

			return "";

		}

		public String CityOriginalDbColumnName() {

			return "City";

		}

		public Integer Score;

		public Integer getScore() {
			return this.Score;
		}

		public Boolean ScoreIsNullable() {
			return true;
		}

		public Boolean ScoreIsKey() {
			return false;
		}

		public Integer ScoreLength() {
			return null;
		}

		public Integer ScorePrecision() {
			return null;
		}

		public String ScoreDefault() {

			return null;

		}

		public String ScoreComment() {

			return "";

		}

		public String ScorePattern() {

			return "";

		}

		public String ScoreOriginalDbColumnName() {

			return "Score";

		}

		public java.util.Date Date_of_match;

		public java.util.Date getDate_of_match() {
			return this.Date_of_match;
		}

		public Boolean Date_of_matchIsNullable() {
			return true;
		}

		public Boolean Date_of_matchIsKey() {
			return false;
		}

		public Integer Date_of_matchLength() {
			return null;
		}

		public Integer Date_of_matchPrecision() {
			return null;
		}

		public String Date_of_matchDefault() {

			return null;

		}

		public String Date_of_matchComment() {

			return "";

		}

		public String Date_of_matchPattern() {

			return "dd-MM-yyyy";

		}

		public String Date_of_matchOriginalDbColumnName() {

			return "Date_of_match";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALEND_PROJECT_File_Read_job.length) {
					if (length < 1024 && commonByteArray_TALEND_PROJECT_File_Read_job.length == 0) {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[1024];
					} else {
						commonByteArray_TALEND_PROJECT_File_Read_job = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length);
				strReturn = new String(commonByteArray_TALEND_PROJECT_File_Read_job, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALEND_PROJECT_File_Read_job) {

				try {

					int length = 0;

					this.Id = readInteger(dis);

					this.Players_name = readString(dis);

					this.Jersey_number = readInteger(dis);

					this.City = readString(dis);

					this.Score = readInteger(dis);

					this.Date_of_match = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.Id, dos);

				// String

				writeString(this.Players_name, dos);

				// Integer

				writeInteger(this.Jersey_number, dos);

				// String

				writeString(this.City, dos);

				// Integer

				writeInteger(this.Score, dos);

				// java.util.Date

				writeDate(this.Date_of_match, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Id=" + String.valueOf(Id));
			sb.append(",Players_name=" + Players_name);
			sb.append(",Jersey_number=" + String.valueOf(Jersey_number));
			sb.append(",City=" + City);
			sb.append(",Score=" + String.valueOf(Score));
			sb.append(",Date_of_match=" + String.valueOf(Date_of_match));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (Id == null) {
				sb.append("<null>");
			} else {
				sb.append(Id);
			}

			sb.append("|");

			if (Players_name == null) {
				sb.append("<null>");
			} else {
				sb.append(Players_name);
			}

			sb.append("|");

			if (Jersey_number == null) {
				sb.append("<null>");
			} else {
				sb.append(Jersey_number);
			}

			sb.append("|");

			if (City == null) {
				sb.append("<null>");
			} else {
				sb.append(City);
			}

			sb.append("|");

			if (Score == null) {
				sb.append("<null>");
			} else {
				sb.append(Score);
			}

			sb.append("|");

			if (Date_of_match == null) {
				sb.append("<null>");
			} else {
				sb.append(Date_of_match);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputExcel_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputExcel_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdc("tFileInputExcel_1", "oLifq7_");

		String currentVirtualComponent = null;

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();
				row2Struct row2 = new row2Struct();
				IndianaPolisStruct IndianaPolis = new IndianaPolisStruct();
				Max_scoreStruct Max_score = new Max_scoreStruct();
				row3Struct row3 = new row3Struct();

				/**
				 * [tSortRow_2_SortOut begin ] start
				 */

				sh("tSortRow_2_SortOut");

				currentVirtualComponent = "tSortRow_2";

				s(currentComponent = "tSortRow_2_SortOut");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");

				int tos_count_tSortRow_2_SortOut = 0;

				if (log.isDebugEnabled())
					log.debug("tSortRow_2_SortOut - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tSortRow_2_SortOut {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tSortRow_2_SortOut = new StringBuilder();
							log4jParamters_tSortRow_2_SortOut.append("Parameters:");
							log4jParamters_tSortRow_2_SortOut.append("DESTINATION" + " = " + "tSortRow_2");
							log4jParamters_tSortRow_2_SortOut.append(" | ");
							log4jParamters_tSortRow_2_SortOut.append("EXTERNAL" + " = " + "false");
							log4jParamters_tSortRow_2_SortOut.append(" | ");
							log4jParamters_tSortRow_2_SortOut.append("CRITERIA" + " = " + "[{ORDER=" + ("asc")
									+ ", COLNAME=" + ("Id") + ", SORT=" + ("num") + "}]");
							log4jParamters_tSortRow_2_SortOut.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tSortRow_2_SortOut - " + (log4jParamters_tSortRow_2_SortOut));
						}
					}
					new BytesLimit65535_tSortRow_2_SortOut().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tSortRow_2_SortOut", "tSortRow_2_SortOut", "tSortOut");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				class Comparablerow1Struct extends row1Struct implements Comparable<Comparablerow1Struct> {

					public int compareTo(Comparablerow1Struct other) {

						if (this.Id == null && other.Id != null) {
							return -1;

						} else if (this.Id != null && other.Id == null) {
							return 1;

						} else if (this.Id != null && other.Id != null) {
							if (!this.Id.equals(other.Id)) {
								return this.Id.compareTo(other.Id);
							}
						}
						return 0;
					}
				}

				java.util.List<Comparablerow1Struct> list_tSortRow_2_SortOut = new java.util.ArrayList<Comparablerow1Struct>();

				/**
				 * [tSortRow_2_SortOut begin ] stop
				 */

				/**
				 * [tFileInputExcel_1 begin ] start
				 */

				sh("tFileInputExcel_1");

				s(currentComponent = "tFileInputExcel_1");

				int tos_count_tFileInputExcel_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileInputExcel_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileInputExcel_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileInputExcel_1 = new StringBuilder();
							log4jParamters_tFileInputExcel_1.append("Parameters:");
							log4jParamters_tFileInputExcel_1.append("VERSION_2007" + " = " + "true");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("FILENAME" + " = "
									+ "\"C:/Users/HP/Documents/Talend_workspace/Players_list.xlsx\"");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("PASSWORD" + " = "
									+ String.valueOf(
											"enc:routine.encryption.key.v1:3fp6QHNi3G23KILPJRbnY5zmnN5S/7lV9+S9Ow==")
											.substring(0, 4)
									+ "...");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("ALL_SHEETS" + " = " + "true");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("HEADER" + " = " + "1");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("FOOTER" + " = " + "0");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("LIMIT" + " = " + "");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("AFFECT_EACH_SHEET" + " = " + "false");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("FIRST_COLUMN" + " = " + "1");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("LAST_COLUMN" + " = " + "");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("DIE_ON_ERROR" + " = " + "true");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("TRIMALL" + " = " + "false");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("TRIMSELECT" + " = " + "[{TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Id") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Players_name") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN="
									+ ("Jersey_number") + "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("City")
									+ "}, {TRIM=" + ("false") + ", SCHEMA_COLUMN=" + ("Score") + "}, {TRIM=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Date_of_match") + "}]");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("CONVERTDATETOSTRING" + " = " + "false");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("ENCODING" + " = " + "\"ISO-8859-15\"");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("STOPREAD_ON_EMPTYROW" + " = " + "false");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("GENERATION_MODE" + " = " + "USER_MODE");
							log4jParamters_tFileInputExcel_1.append(" | ");
							log4jParamters_tFileInputExcel_1.append("CONFIGURE_INFLATION_RATIO" + " = " + "false");
							log4jParamters_tFileInputExcel_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileInputExcel_1 - " + (log4jParamters_tFileInputExcel_1));
						}
					}
					new BytesLimit65535_tFileInputExcel_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileInputExcel_1", "tFileInputExcel_1", "tFileInputExcel");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				final String decryptedPassword_tFileInputExcel_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:5LfTjoZUL487A2Rr8FQhrLg+q+PFToWkYKQCUA==");
				String password_tFileInputExcel_1 = decryptedPassword_tFileInputExcel_1;
				if (password_tFileInputExcel_1.isEmpty()) {
					password_tFileInputExcel_1 = null;
				}
				class RegexUtil_tFileInputExcel_1 {

					public java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> getSheets(
							org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, String oneSheetName,
							boolean useRegex) {

						java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> list = new java.util.ArrayList<org.apache.poi.xssf.usermodel.XSSFSheet>();

						if (useRegex) {// this part process the regex issue

							java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(oneSheetName);
							for (org.apache.poi.ss.usermodel.Sheet sheet : workbook) {
								String sheetName = sheet.getSheetName();
								java.util.regex.Matcher matcher = pattern.matcher(sheetName);
								if (matcher.matches()) {
									if (sheet != null) {
										list.add((org.apache.poi.xssf.usermodel.XSSFSheet) sheet);
									}
								}
							}

						} else {
							org.apache.poi.xssf.usermodel.XSSFSheet sheet = (org.apache.poi.xssf.usermodel.XSSFSheet) workbook
									.getSheet(oneSheetName);
							if (sheet != null) {
								list.add(sheet);
							}

						}

						return list;
					}

					public java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> getSheets(
							org.apache.poi.xssf.usermodel.XSSFWorkbook workbook, int index, boolean useRegex) {
						java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> list = new java.util.ArrayList<org.apache.poi.xssf.usermodel.XSSFSheet>();
						org.apache.poi.xssf.usermodel.XSSFSheet sheet = (org.apache.poi.xssf.usermodel.XSSFSheet) workbook
								.getSheetAt(index);
						if (sheet != null) {
							list.add(sheet);
						}
						return list;
					}

				}
				RegexUtil_tFileInputExcel_1 regexUtil_tFileInputExcel_1 = new RegexUtil_tFileInputExcel_1();

				Object source_tFileInputExcel_1 = "C:/Users/HP/Documents/Talend_workspace/Players_list.xlsx";
				org.apache.poi.xssf.usermodel.XSSFWorkbook workbook_tFileInputExcel_1 = null;

				if (source_tFileInputExcel_1 instanceof String) {
					workbook_tFileInputExcel_1 = (org.apache.poi.xssf.usermodel.XSSFWorkbook) org.apache.poi.ss.usermodel.WorkbookFactory
							.create(new java.io.File((String) source_tFileInputExcel_1), password_tFileInputExcel_1,
									true);
				} else if (source_tFileInputExcel_1 instanceof java.io.InputStream) {
					workbook_tFileInputExcel_1 = (org.apache.poi.xssf.usermodel.XSSFWorkbook) org.apache.poi.ss.usermodel.WorkbookFactory
							.create((java.io.InputStream) source_tFileInputExcel_1, password_tFileInputExcel_1);
				} else {
					workbook_tFileInputExcel_1 = null;
					throw new java.lang.Exception("The data source should be specified as Inputstream or File Path!");
				}
				try {

					java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> sheetList_tFileInputExcel_1 = new java.util.ArrayList<org.apache.poi.xssf.usermodel.XSSFSheet>();
					for (org.apache.poi.ss.usermodel.Sheet sheet_tFileInputExcel_1 : workbook_tFileInputExcel_1) {
						sheetList_tFileInputExcel_1
								.add((org.apache.poi.xssf.usermodel.XSSFSheet) sheet_tFileInputExcel_1);
					}
					if (sheetList_tFileInputExcel_1.size() <= 0) {
						throw new RuntimeException("Special sheets not exist!");
					}

					java.util.List<org.apache.poi.xssf.usermodel.XSSFSheet> sheetList_FilterNull_tFileInputExcel_1 = new java.util.ArrayList<org.apache.poi.xssf.usermodel.XSSFSheet>();
					for (org.apache.poi.xssf.usermodel.XSSFSheet sheet_FilterNull_tFileInputExcel_1 : sheetList_tFileInputExcel_1) {
						if (sheet_FilterNull_tFileInputExcel_1 != null
								&& sheetList_FilterNull_tFileInputExcel_1.iterator() != null
								&& sheet_FilterNull_tFileInputExcel_1.iterator().hasNext()) {
							sheetList_FilterNull_tFileInputExcel_1.add(sheet_FilterNull_tFileInputExcel_1);
						}
					}
					sheetList_tFileInputExcel_1 = sheetList_FilterNull_tFileInputExcel_1;
					int nb_line_tFileInputExcel_1 = 0;
					if (sheetList_tFileInputExcel_1.size() > 0) {

						int begin_line_tFileInputExcel_1 = 1;

						int footer_input_tFileInputExcel_1 = 0;

						int end_line_tFileInputExcel_1 = 0;
						for (org.apache.poi.xssf.usermodel.XSSFSheet sheet_tFileInputExcel_1 : sheetList_tFileInputExcel_1) {
							end_line_tFileInputExcel_1 += (sheet_tFileInputExcel_1.getLastRowNum() + 1);
						}
						end_line_tFileInputExcel_1 -= footer_input_tFileInputExcel_1;
						int limit_tFileInputExcel_1 = -1;
						int start_column_tFileInputExcel_1 = 1 - 1;
						int end_column_tFileInputExcel_1 = -1;

						org.apache.poi.xssf.usermodel.XSSFRow row_tFileInputExcel_1 = null;
						org.apache.poi.xssf.usermodel.XSSFSheet sheet_tFileInputExcel_1 = sheetList_tFileInputExcel_1
								.get(0);
						int rowCount_tFileInputExcel_1 = 0;
						int sheetIndex_tFileInputExcel_1 = 0;
						int currentRows_tFileInputExcel_1 = (sheetList_tFileInputExcel_1.get(0).getLastRowNum() + 1);

						// for the number format
						java.text.DecimalFormat df_tFileInputExcel_1 = new java.text.DecimalFormat(
								"#.####################################");
						char decimalChar_tFileInputExcel_1 = df_tFileInputExcel_1.getDecimalFormatSymbols()
								.getDecimalSeparator();
						log.debug("tFileInputExcel_1 - Retrieving records from the datasource.");

						for (int i_tFileInputExcel_1 = begin_line_tFileInputExcel_1; i_tFileInputExcel_1 < end_line_tFileInputExcel_1; i_tFileInputExcel_1++) {

							int emptyColumnCount_tFileInputExcel_1 = 0;

							if (limit_tFileInputExcel_1 != -1 && nb_line_tFileInputExcel_1 >= limit_tFileInputExcel_1) {
								break;
							}

							while (i_tFileInputExcel_1 >= rowCount_tFileInputExcel_1 + currentRows_tFileInputExcel_1) {
								rowCount_tFileInputExcel_1 += currentRows_tFileInputExcel_1;
								sheet_tFileInputExcel_1 = sheetList_tFileInputExcel_1
										.get(++sheetIndex_tFileInputExcel_1);
								currentRows_tFileInputExcel_1 = (sheet_tFileInputExcel_1.getLastRowNum() + 1);
							}
							globalMap.put("tFileInputExcel_1_CURRENT_SHEET", sheet_tFileInputExcel_1.getSheetName());
							if (rowCount_tFileInputExcel_1 <= i_tFileInputExcel_1) {
								row_tFileInputExcel_1 = sheet_tFileInputExcel_1
										.getRow(i_tFileInputExcel_1 - rowCount_tFileInputExcel_1);
							}
							row1 = null;
							int tempRowLength_tFileInputExcel_1 = 6;

							int columnIndex_tFileInputExcel_1 = 0;

							String[] temp_row_tFileInputExcel_1 = new String[tempRowLength_tFileInputExcel_1];
							int excel_end_column_tFileInputExcel_1;
							if (row_tFileInputExcel_1 == null) {
								excel_end_column_tFileInputExcel_1 = 0;
							} else {
								excel_end_column_tFileInputExcel_1 = row_tFileInputExcel_1.getLastCellNum();
							}
							int actual_end_column_tFileInputExcel_1;
							if (end_column_tFileInputExcel_1 == -1) {
								actual_end_column_tFileInputExcel_1 = excel_end_column_tFileInputExcel_1;
							} else {
								actual_end_column_tFileInputExcel_1 = end_column_tFileInputExcel_1 > excel_end_column_tFileInputExcel_1
										? excel_end_column_tFileInputExcel_1
										: end_column_tFileInputExcel_1;
							}
							org.apache.poi.ss.formula.eval.NumberEval ne_tFileInputExcel_1 = null;
							for (int i = 0; i < tempRowLength_tFileInputExcel_1; i++) {
								if (i + start_column_tFileInputExcel_1 < actual_end_column_tFileInputExcel_1) {
									org.apache.poi.ss.usermodel.Cell cell_tFileInputExcel_1 = row_tFileInputExcel_1
											.getCell(i + start_column_tFileInputExcel_1);
									if (cell_tFileInputExcel_1 != null) {
										switch (cell_tFileInputExcel_1.getCellType()) {
										case STRING:
											temp_row_tFileInputExcel_1[i] = cell_tFileInputExcel_1
													.getRichStringCellValue().getString();
											break;
										case NUMERIC:
											if (org.apache.poi.ss.usermodel.DateUtil
													.isCellDateFormatted(cell_tFileInputExcel_1)) {
												temp_row_tFileInputExcel_1[i] = cell_tFileInputExcel_1
														.getDateCellValue().toString();
											} else {
												temp_row_tFileInputExcel_1[i] = df_tFileInputExcel_1
														.format(cell_tFileInputExcel_1.getNumericCellValue());
											}
											break;
										case BOOLEAN:
											temp_row_tFileInputExcel_1[i] = String
													.valueOf(cell_tFileInputExcel_1.getBooleanCellValue());
											break;
										case FORMULA:
											switch (cell_tFileInputExcel_1.getCachedFormulaResultType()) {
											case STRING:
												temp_row_tFileInputExcel_1[i] = cell_tFileInputExcel_1
														.getRichStringCellValue().getString();
												break;
											case NUMERIC:
												if (org.apache.poi.ss.usermodel.DateUtil
														.isCellDateFormatted(cell_tFileInputExcel_1)) {
													temp_row_tFileInputExcel_1[i] = cell_tFileInputExcel_1
															.getDateCellValue().toString();
												} else {
													ne_tFileInputExcel_1 = new org.apache.poi.ss.formula.eval.NumberEval(
															cell_tFileInputExcel_1.getNumericCellValue());
													temp_row_tFileInputExcel_1[i] = ne_tFileInputExcel_1
															.getStringValue();
												}
												break;
											case BOOLEAN:
												temp_row_tFileInputExcel_1[i] = String
														.valueOf(cell_tFileInputExcel_1.getBooleanCellValue());
												break;
											default:
												temp_row_tFileInputExcel_1[i] = "";
											}
											break;
										default:
											temp_row_tFileInputExcel_1[i] = "";
										}
									} else {
										temp_row_tFileInputExcel_1[i] = "";
									}

								} else {
									temp_row_tFileInputExcel_1[i] = "";
								}
							}
							boolean whetherReject_tFileInputExcel_1 = false;
							row1 = new row1Struct();
							int curColNum_tFileInputExcel_1 = -1;
							String curColName_tFileInputExcel_1 = "";
							try {
								columnIndex_tFileInputExcel_1 = 0;

								if (temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1].length() > 0) {
									curColNum_tFileInputExcel_1 = columnIndex_tFileInputExcel_1
											+ start_column_tFileInputExcel_1 + 1;
									curColName_tFileInputExcel_1 = "Id";

									row1.Id = ParserUtils.parseTo_Integer(ParserUtils.parseTo_Number(
											temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1], null,
											'.' == decimalChar_tFileInputExcel_1 ? null
													: decimalChar_tFileInputExcel_1));
								} else {
									row1.Id = null;
									emptyColumnCount_tFileInputExcel_1++;
								}
								columnIndex_tFileInputExcel_1 = 1;

								if (temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1].length() > 0) {
									curColNum_tFileInputExcel_1 = columnIndex_tFileInputExcel_1
											+ start_column_tFileInputExcel_1 + 1;
									curColName_tFileInputExcel_1 = "Players_name";

									row1.Players_name = temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1];
								} else {
									row1.Players_name = null;
									emptyColumnCount_tFileInputExcel_1++;
								}
								columnIndex_tFileInputExcel_1 = 2;

								if (temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1].length() > 0) {
									curColNum_tFileInputExcel_1 = columnIndex_tFileInputExcel_1
											+ start_column_tFileInputExcel_1 + 1;
									curColName_tFileInputExcel_1 = "Jersey_number";

									row1.Jersey_number = ParserUtils.parseTo_Integer(ParserUtils.parseTo_Number(
											temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1], null,
											'.' == decimalChar_tFileInputExcel_1 ? null
													: decimalChar_tFileInputExcel_1));
								} else {
									row1.Jersey_number = null;
									emptyColumnCount_tFileInputExcel_1++;
								}
								columnIndex_tFileInputExcel_1 = 3;

								if (temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1].length() > 0) {
									curColNum_tFileInputExcel_1 = columnIndex_tFileInputExcel_1
											+ start_column_tFileInputExcel_1 + 1;
									curColName_tFileInputExcel_1 = "City";

									row1.City = temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1];
								} else {
									row1.City = null;
									emptyColumnCount_tFileInputExcel_1++;
								}
								columnIndex_tFileInputExcel_1 = 4;

								if (temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1].length() > 0) {
									curColNum_tFileInputExcel_1 = columnIndex_tFileInputExcel_1
											+ start_column_tFileInputExcel_1 + 1;
									curColName_tFileInputExcel_1 = "Score";

									row1.Score = ParserUtils.parseTo_Integer(ParserUtils.parseTo_Number(
											temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1], null,
											'.' == decimalChar_tFileInputExcel_1 ? null
													: decimalChar_tFileInputExcel_1));
								} else {
									row1.Score = null;
									emptyColumnCount_tFileInputExcel_1++;
								}
								columnIndex_tFileInputExcel_1 = 5;

								if (temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1].length() > 0) {
									curColNum_tFileInputExcel_1 = columnIndex_tFileInputExcel_1
											+ start_column_tFileInputExcel_1 + 1;
									curColName_tFileInputExcel_1 = "Date_of_match";

									if (5 < actual_end_column_tFileInputExcel_1) {
										try {
											if (row_tFileInputExcel_1
													.getCell(columnIndex_tFileInputExcel_1
															+ start_column_tFileInputExcel_1)
													.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC
													&& org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(
															row_tFileInputExcel_1.getCell(columnIndex_tFileInputExcel_1
																	+ start_column_tFileInputExcel_1))) {
												row1.Date_of_match = row_tFileInputExcel_1.getCell(
														columnIndex_tFileInputExcel_1 + start_column_tFileInputExcel_1)
														.getDateCellValue();
											} else {
												java.util.Date tempDate_tFileInputExcel_1 = ParserUtils.parseTo_Date(
														temp_row_tFileInputExcel_1[columnIndex_tFileInputExcel_1],
														"dd-MM-yyyy");
												if (tempDate_tFileInputExcel_1
														.after((new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS"))
																.parse("9999/12/31 23:59:59.999"))
														|| tempDate_tFileInputExcel_1
																.before((new SimpleDateFormat("yyyy/MM/dd"))
																		.parse("1900/01/01"))) {
													throw new RuntimeException("The cell format is not Date in ( Row. "
															+ (nb_line_tFileInputExcel_1 + 1) + " and ColumnNum. "
															+ curColNum_tFileInputExcel_1 + " )");
												} else {
													row1.Date_of_match = tempDate_tFileInputExcel_1;
												}
											}
										} catch (java.lang.Exception e) {
											globalMap.put("tFileInputExcel_1_ERROR_MESSAGE", e.getMessage());

											throw new RuntimeException("The cell format is not Date in ( Row. "
													+ (nb_line_tFileInputExcel_1 + 1) + " and ColumnNum. "
													+ curColNum_tFileInputExcel_1 + " )");
										}
									}

								} else {
									row1.Date_of_match = null;
									emptyColumnCount_tFileInputExcel_1++;
								}

								nb_line_tFileInputExcel_1++;

								log.debug("tFileInputExcel_1 - Retrieving the record " + (nb_line_tFileInputExcel_1)
										+ ".");

							} catch (java.lang.Exception e) {
								globalMap.put("tFileInputExcel_1_ERROR_MESSAGE", e.getMessage());
								whetherReject_tFileInputExcel_1 = true;
								throw (e);
							}

							/**
							 * [tFileInputExcel_1 begin ] stop
							 */

							/**
							 * [tFileInputExcel_1 main ] start
							 */

							s(currentComponent = "tFileInputExcel_1");

							tos_count_tFileInputExcel_1++;

							/**
							 * [tFileInputExcel_1 main ] stop
							 */

							/**
							 * [tFileInputExcel_1 process_data_begin ] start
							 */

							s(currentComponent = "tFileInputExcel_1");

							/**
							 * [tFileInputExcel_1 process_data_begin ] stop
							 */

// Start of branch "row1"
							if (row1 != null) {

								/**
								 * [tSortRow_2_SortOut main ] start
								 */

								currentVirtualComponent = "tSortRow_2";

								s(currentComponent = "tSortRow_2_SortOut");

								if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

										, "row1", "tFileInputExcel_1", "tFileInputExcel_1", "tFileInputExcel",
										"tSortRow_2_SortOut", "tSortRow_2_SortOut", "tSortOut"

								)) {
									talendJobLogProcess(globalMap);
								}

								if (log.isTraceEnabled()) {
									log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
								}

								Comparablerow1Struct arrayRowtSortRow_2_SortOut = new Comparablerow1Struct();

								arrayRowtSortRow_2_SortOut.Id = row1.Id;
								arrayRowtSortRow_2_SortOut.Players_name = row1.Players_name;
								arrayRowtSortRow_2_SortOut.Jersey_number = row1.Jersey_number;
								arrayRowtSortRow_2_SortOut.City = row1.City;
								arrayRowtSortRow_2_SortOut.Score = row1.Score;
								arrayRowtSortRow_2_SortOut.Date_of_match = row1.Date_of_match;
								list_tSortRow_2_SortOut.add(arrayRowtSortRow_2_SortOut);

								tos_count_tSortRow_2_SortOut++;

								/**
								 * [tSortRow_2_SortOut main ] stop
								 */

								/**
								 * [tSortRow_2_SortOut process_data_begin ] start
								 */

								currentVirtualComponent = "tSortRow_2";

								s(currentComponent = "tSortRow_2_SortOut");

								/**
								 * [tSortRow_2_SortOut process_data_begin ] stop
								 */

								/**
								 * [tSortRow_2_SortOut process_data_end ] start
								 */

								currentVirtualComponent = "tSortRow_2";

								s(currentComponent = "tSortRow_2_SortOut");

								/**
								 * [tSortRow_2_SortOut process_data_end ] stop
								 */

							} // End of branch "row1"

							/**
							 * [tFileInputExcel_1 process_data_end ] start
							 */

							s(currentComponent = "tFileInputExcel_1");

							/**
							 * [tFileInputExcel_1 process_data_end ] stop
							 */

							/**
							 * [tFileInputExcel_1 end ] start
							 */

							s(currentComponent = "tFileInputExcel_1");

						}

						log.debug("tFileInputExcel_1 - Retrieved records count: " + nb_line_tFileInputExcel_1 + " .");

					}

					globalMap.put("tFileInputExcel_1_NB_LINE", nb_line_tFileInputExcel_1);
				} finally {

					if (!(source_tFileInputExcel_1 instanceof java.io.InputStream)) {
						workbook_tFileInputExcel_1.getPackage().revert();
					}

				}

				if (log.isDebugEnabled())
					log.debug("tFileInputExcel_1 - " + ("Done."));

				ok_Hash.put("tFileInputExcel_1", true);
				end_Hash.put("tFileInputExcel_1", System.currentTimeMillis());

				/**
				 * [tFileInputExcel_1 end ] stop
				 */

				/**
				 * [tSortRow_2_SortOut end ] start
				 */

				currentVirtualComponent = "tSortRow_2";

				s(currentComponent = "tSortRow_2_SortOut");

				row1Struct[] array_tSortRow_2_SortOut = list_tSortRow_2_SortOut.toArray(new Comparablerow1Struct[0]);

				java.util.Arrays.sort(array_tSortRow_2_SortOut);

				globalMap.put("tSortRow_2", array_tSortRow_2_SortOut);

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tFileInputExcel_1", "tFileInputExcel_1", "tFileInputExcel", "tSortRow_2_SortOut",
						"tSortRow_2_SortOut", "tSortOut", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tSortRow_2_SortOut - " + ("Done."));

				ok_Hash.put("tSortRow_2_SortOut", true);
				end_Hash.put("tSortRow_2_SortOut", System.currentTimeMillis());

				/**
				 * [tSortRow_2_SortOut end ] stop
				 */

				/**
				 * [tLogRow_1 begin ] start
				 */

				sh("tLogRow_1");

				s(currentComponent = "tLogRow_1");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "IndianaPolis");

				int tos_count_tLogRow_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tLogRow_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tLogRow_1 = new StringBuilder();
							log4jParamters_tLogRow_1.append("Parameters:");
							log4jParamters_tLogRow_1.append("BASIC_MODE" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("TABLE_PRINT" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("VERTICAL" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("FIELDSEPARATOR" + " = " + "\"|\"");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("PRINT_HEADER" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("PRINT_UNIQUE_NAME" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("PRINT_COLNAMES" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("USE_FIXED_LENGTH" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("PRINT_CONTENT_WITH_LOG4J" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tLogRow_1 - " + (log4jParamters_tLogRow_1));
						}
					}
					new BytesLimit65535_tLogRow_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tLogRow_1", "tLogRow_1", "tLogRow");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				///////////////////////

				final String OUTPUT_FIELD_SEPARATOR_tLogRow_1 = "|";
				java.io.PrintStream consoleOut_tLogRow_1 = null;

				StringBuilder strBuffer_tLogRow_1 = null;
				int nb_line_tLogRow_1 = 0;
///////////////////////    			

				/**
				 * [tLogRow_1 begin ] stop
				 */

				/**
				 * [tAggregateRow_1_AGGOUT begin ] start
				 */

				sh("tAggregateRow_1_AGGOUT");

				currentVirtualComponent = "tAggregateRow_1";

				s(currentComponent = "tAggregateRow_1_AGGOUT");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "Max_score");

				int tos_count_tAggregateRow_1_AGGOUT = 0;

				if (log.isDebugEnabled())
					log.debug("tAggregateRow_1_AGGOUT - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tAggregateRow_1_AGGOUT {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tAggregateRow_1_AGGOUT = new StringBuilder();
							log4jParamters_tAggregateRow_1_AGGOUT.append("Parameters:");
							log4jParamters_tAggregateRow_1_AGGOUT.append("DESTINATION" + " = " + "tAggregateRow_1");
							log4jParamters_tAggregateRow_1_AGGOUT.append(" | ");
							log4jParamters_tAggregateRow_1_AGGOUT.append("GROUPBYS" + " = " + "[{OUTPUT_COLUMN="
									+ ("City") + ", INPUT_COLUMN=" + ("City") + "}]");
							log4jParamters_tAggregateRow_1_AGGOUT.append(" | ");
							log4jParamters_tAggregateRow_1_AGGOUT.append(
									"OPERATIONS" + " = " + "[{OUTPUT_COLUMN=" + ("Max_Score") + ", INPUT_COLUMN="
											+ ("Score") + ", IGNORE_NULL=" + ("true") + ", FUNCTION=" + ("max") + "}]");
							log4jParamters_tAggregateRow_1_AGGOUT.append(" | ");
							log4jParamters_tAggregateRow_1_AGGOUT.append("LIST_DELIMITER" + " = " + "\",\"");
							log4jParamters_tAggregateRow_1_AGGOUT.append(" | ");
							log4jParamters_tAggregateRow_1_AGGOUT.append("USE_FINANCIAL_PRECISION" + " = " + "true");
							log4jParamters_tAggregateRow_1_AGGOUT.append(" | ");
							log4jParamters_tAggregateRow_1_AGGOUT.append("CHECK_TYPE_OVERFLOW" + " = " + "false");
							log4jParamters_tAggregateRow_1_AGGOUT.append(" | ");
							log4jParamters_tAggregateRow_1_AGGOUT.append("CHECK_ULP" + " = " + "false");
							log4jParamters_tAggregateRow_1_AGGOUT.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tAggregateRow_1_AGGOUT - " + (log4jParamters_tAggregateRow_1_AGGOUT));
						}
					}
					new BytesLimit65535_tAggregateRow_1_AGGOUT().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tAggregateRow_1_AGGOUT", "tAggregateRow_1_AGGOUT", "tAggregateOut");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ------------ Seems it is not used

				java.util.Map hashAggreg_tAggregateRow_1 = new java.util.HashMap();

// ------------

				class UtilClass_tAggregateRow_1 { // G_OutBegin_AggR_144

					public double sd(Double[] data) {
						final int n = data.length;
						if (n < 2) {
							return Double.NaN;
						}
						double d1 = 0d;
						double d2 = 0d;

						for (int i = 0; i < data.length; i++) {
							d1 += (data[i] * data[i]);
							d2 += data[i];
						}

						return Math.sqrt((n * d1 - d2 * d2) / n / (n - 1));
					}

					public void checkedIADD(byte a, byte b, boolean checkTypeOverFlow, boolean checkUlp) {
						byte r = (byte) (a + b);
						if (checkTypeOverFlow && ((a ^ r) & (b ^ r)) < 0) {
							throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b),
									"'short/Short'", "'byte/Byte'"));
						}
					}

					public void checkedIADD(short a, short b, boolean checkTypeOverFlow, boolean checkUlp) {
						short r = (short) (a + b);
						if (checkTypeOverFlow && ((a ^ r) & (b ^ r)) < 0) {
							throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b),
									"'int/Integer'", "'short/Short'"));
						}
					}

					public void checkedIADD(int a, int b, boolean checkTypeOverFlow, boolean checkUlp) {
						int r = a + b;
						if (checkTypeOverFlow && ((a ^ r) & (b ^ r)) < 0) {
							throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b),
									"'long/Long'", "'int/Integer'"));
						}
					}

					public void checkedIADD(long a, long b, boolean checkTypeOverFlow, boolean checkUlp) {
						long r = a + b;
						if (checkTypeOverFlow && ((a ^ r) & (b ^ r)) < 0) {
							throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b),
									"'BigDecimal'", "'long/Long'"));
						}
					}

					public void checkedIADD(float a, float b, boolean checkTypeOverFlow, boolean checkUlp) {

						if (checkUlp) {
							float minAddedValue = Math.ulp(a);
							if (minAddedValue > Math.abs(b)) {
								throw new RuntimeException(buildPrecisionMessage(String.valueOf(a), String.valueOf(b),
										"'double' or 'BigDecimal'", "'float/Float'"));
							}
						}

						if (checkTypeOverFlow && ((double) a + (double) b > (double) Float.MAX_VALUE)
								|| ((double) a + (double) b < (double) -Float.MAX_VALUE)) {
							throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b),
									"'double' or 'BigDecimal'", "'float/Float'"));
						}
					}

					public void checkedIADD(double a, double b, boolean checkTypeOverFlow, boolean checkUlp) {

						if (checkUlp) {
							double minAddedValue = Math.ulp(a);
							if (minAddedValue > Math.abs(b)) {
								throw new RuntimeException(buildPrecisionMessage(String.valueOf(a), String.valueOf(a),
										"'BigDecimal'", "'double/Double'"));
							}
						}

						if (checkTypeOverFlow && (a + b > (double) Double.MAX_VALUE) || (a + b < -Double.MAX_VALUE)) {
							throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b),
									"'BigDecimal'", "'double/Double'"));
						}
					}

					public void checkedIADD(double a, byte b, boolean checkTypeOverFlow, boolean checkUlp) {

						if (checkTypeOverFlow && (a + b > (double) Double.MAX_VALUE) || (a + b < -Double.MAX_VALUE)) {
							throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b),
									"'BigDecimal'", "'double/Double'"));
						}
					}

					public void checkedIADD(double a, short b, boolean checkTypeOverFlow, boolean checkUlp) {

						if (checkTypeOverFlow && (a + b > (double) Double.MAX_VALUE) || (a + b < -Double.MAX_VALUE)) {
							throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b),
									"'BigDecimal'", "'double/Double'"));
						}
					}

					public void checkedIADD(double a, int b, boolean checkTypeOverFlow, boolean checkUlp) {

						if (checkTypeOverFlow && (a + b > (double) Double.MAX_VALUE) || (a + b < -Double.MAX_VALUE)) {
							throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b),
									"'BigDecimal'", "'double/Double'"));
						}
					}

					public void checkedIADD(double a, float b, boolean checkTypeOverFlow, boolean checkUlp) {

						if (checkUlp) {
							double minAddedValue = Math.ulp(a);
							if (minAddedValue > Math.abs(b)) {
								throw new RuntimeException(buildPrecisionMessage(String.valueOf(a), String.valueOf(a),
										"'BigDecimal'", "'double/Double'"));
							}
						}

						if (checkTypeOverFlow && (a + b > (double) Double.MAX_VALUE) || (a + b < -Double.MAX_VALUE)) {
							throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b),
									"'BigDecimal'", "'double/Double'"));
						}
					}

					private String buildOverflowMessage(String a, String b, String advicedTypes, String originalType) {
						return "Type overflow when adding " + b + " to " + a
								+ ", to resolve this problem, increase the precision by using " + advicedTypes
								+ " type in place of " + originalType + ".";
					}

					private String buildPrecisionMessage(String a, String b, String advicedTypes, String originalType) {
						return "The double precision is unsufficient to add the value " + b + " to " + a
								+ ", to resolve this problem, increase the precision by using " + advicedTypes
								+ " type in place of " + originalType + ".";
					}

				} // G_OutBegin_AggR_144

				UtilClass_tAggregateRow_1 utilClass_tAggregateRow_1 = new UtilClass_tAggregateRow_1();

				class AggOperationStruct_tAggregateRow_1 { // G_OutBegin_AggR_100

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					String City;
					Integer Max_Score_max;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.City == null) ? 0 : this.City.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final AggOperationStruct_tAggregateRow_1 other = (AggOperationStruct_tAggregateRow_1) obj;

						if (this.City == null) {
							if (other.City != null)
								return false;
						} else if (!this.City.equals(other.City))
							return false;

						return true;
					}

				} // G_OutBegin_AggR_100

				AggOperationStruct_tAggregateRow_1 operation_result_tAggregateRow_1 = null;
				AggOperationStruct_tAggregateRow_1 operation_finder_tAggregateRow_1 = new AggOperationStruct_tAggregateRow_1();
				java.util.Map<AggOperationStruct_tAggregateRow_1, AggOperationStruct_tAggregateRow_1> hash_tAggregateRow_1 = new java.util.HashMap<AggOperationStruct_tAggregateRow_1, AggOperationStruct_tAggregateRow_1>();

				/**
				 * [tAggregateRow_1_AGGOUT begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				sh("tMap_1");

				s(currentComponent = "tMap_1");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row2");

				int tos_count_tMap_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tMap_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tMap_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tMap_1 = new StringBuilder();
							log4jParamters_tMap_1.append("Parameters:");
							log4jParamters_tMap_1.append("LINK_STYLE" + " = " + "AUTO");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
							log4jParamters_tMap_1.append(" | ");
							log4jParamters_tMap_1.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
							log4jParamters_tMap_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tMap_1 - " + (log4jParamters_tMap_1));
						}
					}
					new BytesLimit65535_tMap_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tMap_1", "tMap_1", "tMap");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

// ###############################
// # Lookup's keys initialization
				int count_row2_tMap_1 = 0;

// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				int count_IndianaPolis_tMap_1 = 0;

				IndianaPolisStruct IndianaPolis_tmp = new IndianaPolisStruct();
				int count_Max_score_tMap_1 = 0;

				Max_scoreStruct Max_score_tmp = new Max_scoreStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tSortRow_2_SortIn begin ] start
				 */

				sh("tSortRow_2_SortIn");

				currentVirtualComponent = "tSortRow_2";

				s(currentComponent = "tSortRow_2_SortIn");

				int tos_count_tSortRow_2_SortIn = 0;

				if (log.isDebugEnabled())
					log.debug("tSortRow_2_SortIn - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tSortRow_2_SortIn {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tSortRow_2_SortIn = new StringBuilder();
							log4jParamters_tSortRow_2_SortIn.append("Parameters:");
							log4jParamters_tSortRow_2_SortIn.append("ORIGIN" + " = " + "tSortRow_2");
							log4jParamters_tSortRow_2_SortIn.append(" | ");
							log4jParamters_tSortRow_2_SortIn.append("EXTERNAL" + " = " + "false");
							log4jParamters_tSortRow_2_SortIn.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tSortRow_2_SortIn - " + (log4jParamters_tSortRow_2_SortIn));
						}
					}
					new BytesLimit65535_tSortRow_2_SortIn().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tSortRow_2_SortIn", "tSortRow_2_SortIn", "tSortIn");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				row1Struct[] array_tSortRow_2_SortIn = (row1Struct[]) globalMap.remove("tSortRow_2");

				int nb_line_tSortRow_2_SortIn = 0;

				row1Struct current_tSortRow_2_SortIn = null;

				for (int i_tSortRow_2_SortIn = 0; i_tSortRow_2_SortIn < array_tSortRow_2_SortIn.length; i_tSortRow_2_SortIn++) {
					current_tSortRow_2_SortIn = array_tSortRow_2_SortIn[i_tSortRow_2_SortIn];
					row2.Id = current_tSortRow_2_SortIn.Id;
					row2.Players_name = current_tSortRow_2_SortIn.Players_name;
					row2.Jersey_number = current_tSortRow_2_SortIn.Jersey_number;
					row2.City = current_tSortRow_2_SortIn.City;
					row2.Score = current_tSortRow_2_SortIn.Score;
					row2.Date_of_match = current_tSortRow_2_SortIn.Date_of_match;
					// increase number of line sorted
					nb_line_tSortRow_2_SortIn++;

					/**
					 * [tSortRow_2_SortIn begin ] stop
					 */

					/**
					 * [tSortRow_2_SortIn main ] start
					 */

					currentVirtualComponent = "tSortRow_2";

					s(currentComponent = "tSortRow_2_SortIn");

					tos_count_tSortRow_2_SortIn++;

					/**
					 * [tSortRow_2_SortIn main ] stop
					 */

					/**
					 * [tSortRow_2_SortIn process_data_begin ] start
					 */

					currentVirtualComponent = "tSortRow_2";

					s(currentComponent = "tSortRow_2_SortIn");

					/**
					 * [tSortRow_2_SortIn process_data_begin ] stop
					 */

					/**
					 * [tMap_1 main ] start
					 */

					s(currentComponent = "tMap_1");

					if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

							, "row2", "tSortRow_2_SortIn", "tSortRow_2_SortIn", "tSortIn", "tMap_1", "tMap_1", "tMap"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("row2 - " + (row2 == null ? "" : row2.toLogString()));
					}

					boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

					// ###############################
					// # Input tables (lookups)

					boolean rejectedInnerJoin_tMap_1 = false;
					boolean mainRowRejected_tMap_1 = false;
					// ###############################
					{ // start of Var scope

						// ###############################
						// # Vars tables

						Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
						// ###############################
						// # Output tables

						IndianaPolis = null;
						Max_score = null;

// # Output table : 'IndianaPolis'
// # Filter conditions 
						if (

						row2.City.equals("Indianapolis")

						) {
							count_IndianaPolis_tMap_1++;

							IndianaPolis_tmp.Id = row2.Id;
							IndianaPolis_tmp.Players_name = row2.Players_name;
							IndianaPolis_tmp.Jersey_number = row2.Jersey_number;
							IndianaPolis_tmp.City = row2.City;
							IndianaPolis_tmp.Score = row2.Score;
							IndianaPolis_tmp.Date_of_match = row2.Date_of_match;
							IndianaPolis = IndianaPolis_tmp;
							log.debug("tMap_1 - Outputting the record " + count_IndianaPolis_tMap_1
									+ " of the output table 'IndianaPolis'.");

						} // closing filter/reject

// # Output table : 'Max_score'
						count_Max_score_tMap_1++;

						Max_score_tmp.Id = row2.Id;
						Max_score_tmp.Players_name = row2.Players_name;
						Max_score_tmp.Jersey_number = row2.Jersey_number;
						Max_score_tmp.City = row2.City;
						Max_score_tmp.Score = row2.Score;
						Max_score_tmp.Date_of_match = row2.Date_of_match;
						Max_score = Max_score_tmp;
						log.debug("tMap_1 - Outputting the record " + count_Max_score_tMap_1
								+ " of the output table 'Max_score'.");

// ###############################

					} // end of Var scope

					rejectedInnerJoin_tMap_1 = false;

					tos_count_tMap_1++;

					/**
					 * [tMap_1 main ] stop
					 */

					/**
					 * [tMap_1 process_data_begin ] start
					 */

					s(currentComponent = "tMap_1");

					/**
					 * [tMap_1 process_data_begin ] stop
					 */

// Start of branch "IndianaPolis"
					if (IndianaPolis != null) {

						/**
						 * [tLogRow_1 main ] start
						 */

						s(currentComponent = "tLogRow_1");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "IndianaPolis", "tMap_1", "tMap_1", "tMap", "tLogRow_1", "tLogRow_1", "tLogRow"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("IndianaPolis - " + (IndianaPolis == null ? "" : IndianaPolis.toLogString()));
						}

///////////////////////		

						strBuffer_tLogRow_1 = new StringBuilder();

						if (IndianaPolis.Id != null) { //

							strBuffer_tLogRow_1.append(String.valueOf(IndianaPolis.Id));

						} //

						strBuffer_tLogRow_1.append("|");

						if (IndianaPolis.Players_name != null) { //

							strBuffer_tLogRow_1.append(String.valueOf(IndianaPolis.Players_name));

						} //

						strBuffer_tLogRow_1.append("|");

						if (IndianaPolis.Jersey_number != null) { //

							strBuffer_tLogRow_1.append(String.valueOf(IndianaPolis.Jersey_number));

						} //

						strBuffer_tLogRow_1.append("|");

						if (IndianaPolis.City != null) { //

							strBuffer_tLogRow_1.append(String.valueOf(IndianaPolis.City));

						} //

						strBuffer_tLogRow_1.append("|");

						if (IndianaPolis.Score != null) { //

							strBuffer_tLogRow_1.append(String.valueOf(IndianaPolis.Score));

						} //

						strBuffer_tLogRow_1.append("|");

						if (IndianaPolis.Date_of_match != null) { //

							strBuffer_tLogRow_1
									.append(FormatterUtils.format_Date(IndianaPolis.Date_of_match, "dd-MM-yyyy"));

						} //

						if (globalMap.get("tLogRow_CONSOLE") != null) {
							consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
						} else {
							consoleOut_tLogRow_1 = new java.io.PrintStream(
									new java.io.BufferedOutputStream(System.out));
							globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_1);
						}
						log.info("tLogRow_1 - Content of row " + (nb_line_tLogRow_1 + 1) + ": "
								+ strBuffer_tLogRow_1.toString());
						consoleOut_tLogRow_1.println(strBuffer_tLogRow_1.toString());
						consoleOut_tLogRow_1.flush();
						nb_line_tLogRow_1++;
//////

//////                    

///////////////////////    			

						tos_count_tLogRow_1++;

						/**
						 * [tLogRow_1 main ] stop
						 */

						/**
						 * [tLogRow_1 process_data_begin ] start
						 */

						s(currentComponent = "tLogRow_1");

						/**
						 * [tLogRow_1 process_data_begin ] stop
						 */

						/**
						 * [tLogRow_1 process_data_end ] start
						 */

						s(currentComponent = "tLogRow_1");

						/**
						 * [tLogRow_1 process_data_end ] stop
						 */

					} // End of branch "IndianaPolis"

// Start of branch "Max_score"
					if (Max_score != null) {

						/**
						 * [tAggregateRow_1_AGGOUT main ] start
						 */

						currentVirtualComponent = "tAggregateRow_1";

						s(currentComponent = "tAggregateRow_1_AGGOUT");

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "Max_score", "tMap_1", "tMap_1", "tMap", "tAggregateRow_1_AGGOUT",
								"tAggregateRow_1_AGGOUT", "tAggregateOut"

						)) {
							talendJobLogProcess(globalMap);
						}

						if (log.isTraceEnabled()) {
							log.trace("Max_score - " + (Max_score == null ? "" : Max_score.toLogString()));
						}

						operation_finder_tAggregateRow_1.City = Max_score.City;

						operation_finder_tAggregateRow_1.hashCodeDirty = true;

						operation_result_tAggregateRow_1 = hash_tAggregateRow_1.get(operation_finder_tAggregateRow_1);

						boolean isFirstAdd_tAggregateRow_1 = false;

						if (operation_result_tAggregateRow_1 == null) { // G_OutMain_AggR_001

							operation_result_tAggregateRow_1 = new AggOperationStruct_tAggregateRow_1();

							operation_result_tAggregateRow_1.City = operation_finder_tAggregateRow_1.City;

							isFirstAdd_tAggregateRow_1 = true;

							hash_tAggregateRow_1.put(operation_result_tAggregateRow_1,
									operation_result_tAggregateRow_1);

						} // G_OutMain_AggR_001

						if (Max_score.Score != null) { // G_OutMain_AggR_546

							if (operation_result_tAggregateRow_1.Max_Score_max == null
									|| Max_score.Score > operation_result_tAggregateRow_1.Max_Score_max

							) {
								operation_result_tAggregateRow_1.Max_Score_max = Max_score.Score;
							}

						} // G_OutMain_AggR_546

						tos_count_tAggregateRow_1_AGGOUT++;

						/**
						 * [tAggregateRow_1_AGGOUT main ] stop
						 */

						/**
						 * [tAggregateRow_1_AGGOUT process_data_begin ] start
						 */

						currentVirtualComponent = "tAggregateRow_1";

						s(currentComponent = "tAggregateRow_1_AGGOUT");

						/**
						 * [tAggregateRow_1_AGGOUT process_data_begin ] stop
						 */

						/**
						 * [tAggregateRow_1_AGGOUT process_data_end ] start
						 */

						currentVirtualComponent = "tAggregateRow_1";

						s(currentComponent = "tAggregateRow_1_AGGOUT");

						/**
						 * [tAggregateRow_1_AGGOUT process_data_end ] stop
						 */

					} // End of branch "Max_score"

					/**
					 * [tMap_1 process_data_end ] start
					 */

					s(currentComponent = "tMap_1");

					/**
					 * [tMap_1 process_data_end ] stop
					 */

					/**
					 * [tSortRow_2_SortIn process_data_end ] start
					 */

					currentVirtualComponent = "tSortRow_2";

					s(currentComponent = "tSortRow_2_SortIn");

					/**
					 * [tSortRow_2_SortIn process_data_end ] stop
					 */

					/**
					 * [tSortRow_2_SortIn end ] start
					 */

					currentVirtualComponent = "tSortRow_2";

					s(currentComponent = "tSortRow_2_SortIn");

				}

				globalMap.put("tSortRow_2_SortIn_NB_LINE", nb_line_tSortRow_2_SortIn);

				if (log.isDebugEnabled())
					log.debug("tSortRow_2_SortIn - " + ("Done."));

				ok_Hash.put("tSortRow_2_SortIn", true);
				end_Hash.put("tSortRow_2_SortIn", System.currentTimeMillis());

				/**
				 * [tSortRow_2_SortIn end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				s(currentComponent = "tMap_1");

// ###############################
// # Lookup hashes releasing
// ###############################      
				log.debug("tMap_1 - Written records count in the table 'IndianaPolis': " + count_IndianaPolis_tMap_1
						+ ".");
				log.debug("tMap_1 - Written records count in the table 'Max_score': " + count_Max_score_tMap_1 + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row2", 2, 0,
						"tSortRow_2_SortIn", "tSortRow_2_SortIn", "tSortIn", "tMap_1", "tMap_1", "tMap", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tMap_1 - " + ("Done."));

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tLogRow_1 end ] start
				 */

				s(currentComponent = "tLogRow_1");

//////
//////
				globalMap.put("tLogRow_1_NB_LINE", nb_line_tLogRow_1);
				if (log.isInfoEnabled())
					log.info("tLogRow_1 - " + ("Printed row count: ") + (nb_line_tLogRow_1) + ("."));

///////////////////////    			

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "IndianaPolis", 2, 0,
						"tMap_1", "tMap_1", "tMap", "tLogRow_1", "tLogRow_1", "tLogRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Done."));

				ok_Hash.put("tLogRow_1", true);
				end_Hash.put("tLogRow_1", System.currentTimeMillis());

				/**
				 * [tLogRow_1 end ] stop
				 */

				/**
				 * [tAggregateRow_1_AGGOUT end ] start
				 */

				currentVirtualComponent = "tAggregateRow_1";

				s(currentComponent = "tAggregateRow_1_AGGOUT");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "Max_score", 2, 0,
						"tMap_1", "tMap_1", "tMap", "tAggregateRow_1_AGGOUT", "tAggregateRow_1_AGGOUT", "tAggregateOut",
						"output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tAggregateRow_1_AGGOUT - " + ("Done."));

				ok_Hash.put("tAggregateRow_1_AGGOUT", true);
				end_Hash.put("tAggregateRow_1_AGGOUT", System.currentTimeMillis());

				/**
				 * [tAggregateRow_1_AGGOUT end ] stop
				 */

				/**
				 * [tFileOutputExcel_1 begin ] start
				 */

				sh("tFileOutputExcel_1");

				s(currentComponent = "tFileOutputExcel_1");

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row3");

				int tos_count_tFileOutputExcel_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileOutputExcel_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileOutputExcel_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileOutputExcel_1 = new StringBuilder();
							log4jParamters_tFileOutputExcel_1.append("Parameters:");
							log4jParamters_tFileOutputExcel_1.append("VERSION_2007" + " = " + "true");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("USESTREAM" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("FILENAME" + " = "
									+ "\"C:/Talend-Studio-20240527_1700-V8.0.1/Talend-Studio-20240527_1700-V8.0.1/workspace/max_scored_players.xlsx\"");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("SHEETNAME" + " = " + "\"Sheet1\"");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("INCLUDEHEADER" + " = " + "true");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("APPEND_FILE" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("FIRST_CELL_Y_ABSOLUTE" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("FONT" + " = " + "");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("IS_ALL_AUTO_SZIE" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("AUTO_SZIE_SETTING" + " = " + "[{IS_AUTO_SIZE="
									+ ("false") + ", SCHEMA_COLUMN=" + ("Id") + "}, {IS_AUTO_SIZE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Players_name") + "}, {IS_AUTO_SIZE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Jersey_number") + "}, {IS_AUTO_SIZE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("City") + "}, {IS_AUTO_SIZE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Max_Score") + "}, {IS_AUTO_SIZE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("Date_of_match") + "}]");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("PROTECT_FILE" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("CREATE" + " = " + "true");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("FLUSHONROW" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("TRUNCATE_EXCEEDING_CHARACTERS" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("ENCODING" + " = " + "\"ISO-8859-15\"");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("DELETE_EMPTYFILE" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("USE_SHARED_STRINGS_TABLE" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileOutputExcel_1 - " + (log4jParamters_tFileOutputExcel_1));
						}
					}
					new BytesLimit65535_tFileOutputExcel_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileOutputExcel_1", "tFileOutputExcel_1", "tFileOutputExcel");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				int columnIndex_tFileOutputExcel_1 = 0;
				boolean headerIsInserted_tFileOutputExcel_1 = false;

				String fileName_tFileOutputExcel_1 = "C:/Talend-Studio-20240527_1700-V8.0.1/Talend-Studio-20240527_1700-V8.0.1/workspace/max_scored_players.xlsx";
				int nb_line_tFileOutputExcel_1 = 0;
				org.talend.ExcelTool xlsxTool_tFileOutputExcel_1 = new org.talend.ExcelTool();
				xlsxTool_tFileOutputExcel_1.setUseSharedStringTable(false);

				xlsxTool_tFileOutputExcel_1.setTruncateExceedingCharacters(false);
				xlsxTool_tFileOutputExcel_1.setSheet("Sheet1");
				xlsxTool_tFileOutputExcel_1.setAppend(false, false, false);
				xlsxTool_tFileOutputExcel_1.setRecalculateFormula(false);
				xlsxTool_tFileOutputExcel_1.setXY(false, 0, 0, false);

				java.util.concurrent.ConcurrentHashMap<java.lang.Object, java.lang.Object> chm_tFileOutputExcel_1 = (java.util.concurrent.ConcurrentHashMap<java.lang.Object, java.lang.Object>) globalMap
						.get("concurrentHashMap");
				java.lang.Object lockObj_tFileOutputExcel_1 = chm_tFileOutputExcel_1
						.computeIfAbsent("EXCEL_OUTPUT_LOCK_OBJ_tFileOutputExcel_1", k -> new Object());
				synchronized (lockObj_tFileOutputExcel_1) {

					xlsxTool_tFileOutputExcel_1.prepareXlsxFile(fileName_tFileOutputExcel_1);

				}

				xlsxTool_tFileOutputExcel_1.setFont("");

				if (xlsxTool_tFileOutputExcel_1.getStartRow() == 0) {

					xlsxTool_tFileOutputExcel_1.addRow();

					xlsxTool_tFileOutputExcel_1.addCellValue("Id");

					xlsxTool_tFileOutputExcel_1.addCellValue("Players_name");

					xlsxTool_tFileOutputExcel_1.addCellValue("Jersey_number");

					xlsxTool_tFileOutputExcel_1.addCellValue("City");

					xlsxTool_tFileOutputExcel_1.addCellValue("Max_Score");

					xlsxTool_tFileOutputExcel_1.addCellValue("Date_of_match");

					nb_line_tFileOutputExcel_1++;
					headerIsInserted_tFileOutputExcel_1 = true;

				}

				/**
				 * [tFileOutputExcel_1 begin ] stop
				 */

				/**
				 * [tAggregateRow_1_AGGIN begin ] start
				 */

				sh("tAggregateRow_1_AGGIN");

				currentVirtualComponent = "tAggregateRow_1";

				s(currentComponent = "tAggregateRow_1_AGGIN");

				int tos_count_tAggregateRow_1_AGGIN = 0;

				if (log.isDebugEnabled())
					log.debug("tAggregateRow_1_AGGIN - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tAggregateRow_1_AGGIN {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tAggregateRow_1_AGGIN = new StringBuilder();
							log4jParamters_tAggregateRow_1_AGGIN.append("Parameters:");
							log4jParamters_tAggregateRow_1_AGGIN.append("ORIGIN" + " = " + "tAggregateRow_1");
							log4jParamters_tAggregateRow_1_AGGIN.append(" | ");
							log4jParamters_tAggregateRow_1_AGGIN.append("GROUPBYS" + " = " + "[{OUTPUT_COLUMN="
									+ ("City") + ", INPUT_COLUMN=" + ("City") + "}]");
							log4jParamters_tAggregateRow_1_AGGIN.append(" | ");
							log4jParamters_tAggregateRow_1_AGGIN.append(
									"OPERATIONS" + " = " + "[{OUTPUT_COLUMN=" + ("Max_Score") + ", INPUT_COLUMN="
											+ ("Score") + ", IGNORE_NULL=" + ("true") + ", FUNCTION=" + ("max") + "}]");
							log4jParamters_tAggregateRow_1_AGGIN.append(" | ");
							log4jParamters_tAggregateRow_1_AGGIN.append("LIST_DELIMITER" + " = " + "\",\"");
							log4jParamters_tAggregateRow_1_AGGIN.append(" | ");
							log4jParamters_tAggregateRow_1_AGGIN.append("USE_FINANCIAL_PRECISION" + " = " + "true");
							log4jParamters_tAggregateRow_1_AGGIN.append(" | ");
							log4jParamters_tAggregateRow_1_AGGIN.append("CHECK_TYPE_OVERFLOW" + " = " + "false");
							log4jParamters_tAggregateRow_1_AGGIN.append(" | ");
							log4jParamters_tAggregateRow_1_AGGIN.append("CHECK_ULP" + " = " + "false");
							log4jParamters_tAggregateRow_1_AGGIN.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tAggregateRow_1_AGGIN - " + (log4jParamters_tAggregateRow_1_AGGIN));
						}
					}
					new BytesLimit65535_tAggregateRow_1_AGGIN().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tAggregateRow_1_AGGIN", "tAggregateRow_1_AGGIN", "tAggregateIn");
					talendJobLogProcess(globalMap);
					s(currentComponent);
				}

				java.util.Collection<AggOperationStruct_tAggregateRow_1> values_tAggregateRow_1 = hash_tAggregateRow_1
						.values();

				globalMap.put("tAggregateRow_1_NB_LINE", values_tAggregateRow_1.size());

				if (log.isInfoEnabled())
					log.info("tAggregateRow_1_AGGIN - " + ("Retrieving the aggregation results."));
				for (AggOperationStruct_tAggregateRow_1 aggregated_row_tAggregateRow_1 : values_tAggregateRow_1) { // G_AggR_600

					/**
					 * [tAggregateRow_1_AGGIN begin ] stop
					 */

					/**
					 * [tAggregateRow_1_AGGIN main ] start
					 */

					currentVirtualComponent = "tAggregateRow_1";

					s(currentComponent = "tAggregateRow_1_AGGIN");

					row3.City = aggregated_row_tAggregateRow_1.City;

					row3.Max_Score = aggregated_row_tAggregateRow_1.Max_Score_max;
					if (log.isDebugEnabled())
						log.debug("tAggregateRow_1_AGGIN - " + ("Operation function: 'max' on the column 'Score'."));

					tos_count_tAggregateRow_1_AGGIN++;

					/**
					 * [tAggregateRow_1_AGGIN main ] stop
					 */

					/**
					 * [tAggregateRow_1_AGGIN process_data_begin ] start
					 */

					currentVirtualComponent = "tAggregateRow_1";

					s(currentComponent = "tAggregateRow_1_AGGIN");

					/**
					 * [tAggregateRow_1_AGGIN process_data_begin ] stop
					 */

					/**
					 * [tFileOutputExcel_1 main ] start
					 */

					s(currentComponent = "tFileOutputExcel_1");

					if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

							, "row3", "tAggregateRow_1_AGGIN", "tAggregateRow_1_AGGIN", "tAggregateIn",
							"tFileOutputExcel_1", "tFileOutputExcel_1", "tFileOutputExcel"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("row3 - " + (row3 == null ? "" : row3.toLogString()));
					}

					xlsxTool_tFileOutputExcel_1.addRow();

					if (row3.Id != null) {

						xlsxTool_tFileOutputExcel_1.addCellValue(Double.parseDouble(String.valueOf(row3.Id)));
					} else {
						xlsxTool_tFileOutputExcel_1.addCellNullValue();
					}

					if (row3.Players_name != null) {

						xlsxTool_tFileOutputExcel_1.addCellValue(String.valueOf(row3.Players_name));
					} else {
						xlsxTool_tFileOutputExcel_1.addCellNullValue();
					}

					if (row3.Jersey_number != null) {

						xlsxTool_tFileOutputExcel_1
								.addCellValue(Double.parseDouble(String.valueOf(row3.Jersey_number)));
					} else {
						xlsxTool_tFileOutputExcel_1.addCellNullValue();
					}

					if (row3.City != null) {

						xlsxTool_tFileOutputExcel_1.addCellValue(String.valueOf(row3.City));
					} else {
						xlsxTool_tFileOutputExcel_1.addCellNullValue();
					}

					if (row3.Max_Score != null) {

						xlsxTool_tFileOutputExcel_1.addCellValue(Double.parseDouble(String.valueOf(row3.Max_Score)));
					} else {
						xlsxTool_tFileOutputExcel_1.addCellNullValue();
					}

					if (row3.Date_of_match != null) {

						xlsxTool_tFileOutputExcel_1.addCellValue(row3.Date_of_match, "dd-MM-yyyy");
					} else {
						xlsxTool_tFileOutputExcel_1.addCellNullValue();
					}

					nb_line_tFileOutputExcel_1++;

					log.debug(
							"tFileOutputExcel_1 - Writing the record " + nb_line_tFileOutputExcel_1 + " to the file.");

					tos_count_tFileOutputExcel_1++;

					/**
					 * [tFileOutputExcel_1 main ] stop
					 */

					/**
					 * [tFileOutputExcel_1 process_data_begin ] start
					 */

					s(currentComponent = "tFileOutputExcel_1");

					/**
					 * [tFileOutputExcel_1 process_data_begin ] stop
					 */

					/**
					 * [tFileOutputExcel_1 process_data_end ] start
					 */

					s(currentComponent = "tFileOutputExcel_1");

					/**
					 * [tFileOutputExcel_1 process_data_end ] stop
					 */

					/**
					 * [tAggregateRow_1_AGGIN process_data_end ] start
					 */

					currentVirtualComponent = "tAggregateRow_1";

					s(currentComponent = "tAggregateRow_1_AGGIN");

					/**
					 * [tAggregateRow_1_AGGIN process_data_end ] stop
					 */

					/**
					 * [tAggregateRow_1_AGGIN end ] start
					 */

					currentVirtualComponent = "tAggregateRow_1";

					s(currentComponent = "tAggregateRow_1_AGGIN");

				} // G_AggR_600

				if (log.isDebugEnabled())
					log.debug("tAggregateRow_1_AGGIN - " + ("Done."));

				ok_Hash.put("tAggregateRow_1_AGGIN", true);
				end_Hash.put("tAggregateRow_1_AGGIN", System.currentTimeMillis());

				/**
				 * [tAggregateRow_1_AGGIN end ] stop
				 */

				/**
				 * [tFileOutputExcel_1 end ] start
				 */

				s(currentComponent = "tFileOutputExcel_1");

				xlsxTool_tFileOutputExcel_1.writeExcel(fileName_tFileOutputExcel_1, true);

				if (headerIsInserted_tFileOutputExcel_1 && nb_line_tFileOutputExcel_1 > 0) {
					nb_line_tFileOutputExcel_1 = nb_line_tFileOutputExcel_1 - 1;
				}
				globalMap.put("tFileOutputExcel_1_NB_LINE", nb_line_tFileOutputExcel_1);

				log.debug("tFileOutputExcel_1 - Written records count: " + nb_line_tFileOutputExcel_1 + " .");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row3", 2, 0,
						"tAggregateRow_1_AGGIN", "tAggregateRow_1_AGGIN", "tAggregateIn", "tFileOutputExcel_1",
						"tFileOutputExcel_1", "tFileOutputExcel", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tFileOutputExcel_1 - " + ("Done."));

				ok_Hash.put("tFileOutputExcel_1", true);
				end_Hash.put("tFileOutputExcel_1", System.currentTimeMillis());

				/**
				 * [tFileOutputExcel_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			te.setVirtualComponentName(currentVirtualComponent);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			// free memory for "tAggregateRow_1_AGGIN"
			globalMap.remove("tAggregateRow_1");

			// free memory for "tSortRow_2_SortIn"
			globalMap.remove("tSortRow_2");

			try {

				/**
				 * [tFileInputExcel_1 finally ] start
				 */

				s(currentComponent = "tFileInputExcel_1");

				/**
				 * [tFileInputExcel_1 finally ] stop
				 */

				/**
				 * [tSortRow_2_SortOut finally ] start
				 */

				currentVirtualComponent = "tSortRow_2";

				s(currentComponent = "tSortRow_2_SortOut");

				/**
				 * [tSortRow_2_SortOut finally ] stop
				 */

				/**
				 * [tSortRow_2_SortIn finally ] start
				 */

				currentVirtualComponent = "tSortRow_2";

				s(currentComponent = "tSortRow_2_SortIn");

				/**
				 * [tSortRow_2_SortIn finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				s(currentComponent = "tMap_1");

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tLogRow_1 finally ] start
				 */

				s(currentComponent = "tLogRow_1");

				/**
				 * [tLogRow_1 finally ] stop
				 */

				/**
				 * [tAggregateRow_1_AGGOUT finally ] start
				 */

				currentVirtualComponent = "tAggregateRow_1";

				s(currentComponent = "tAggregateRow_1_AGGOUT");

				/**
				 * [tAggregateRow_1_AGGOUT finally ] stop
				 */

				/**
				 * [tAggregateRow_1_AGGIN finally ] start
				 */

				currentVirtualComponent = "tAggregateRow_1";

				s(currentComponent = "tAggregateRow_1_AGGIN");

				/**
				 * [tAggregateRow_1_AGGIN finally ] stop
				 */

				/**
				 * [tFileOutputExcel_1 finally ] start
				 */

				s(currentComponent = "tFileOutputExcel_1");

				/**
				 * [tFileOutputExcel_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputExcel_1_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		s("none");
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [talendJobLog begin ] start
				 */

				sh("talendJobLog");

				s(currentComponent = "talendJobLog");

				int tos_count_talendJobLog = 0;

				for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
					org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder
							.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
							.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid)
							.custom("father_pid", fatherPid).custom("root_pid", rootPid);
					org.talend.logging.audit.Context log_context_talendJobLog = null;

					if (jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.sourceId(jcm.sourceId)
								.sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
								.targetId(jcm.targetId).targetLabel(jcm.targetLabel)
								.targetConnectorType(jcm.targetComponentName).connectionName(jcm.current_connector)
								.rows(jcm.row_count).duration(duration).build();
						auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
						auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).duration(duration)
								.status(jcm.status).build();
						auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
								.connectorType(jcm.component_name).connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label).build();
						auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {// log current component
																							// input line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {// log current component
																								// output/reject line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBERROR) {
						java.lang.Exception e_talendJobLog = jcm.exception;
						if (e_talendJobLog != null) {
							try (java.io.StringWriter sw_talendJobLog = new java.io.StringWriter();
									java.io.PrintWriter pw_talendJobLog = new java.io.PrintWriter(sw_talendJobLog)) {
								e_talendJobLog.printStackTrace(pw_talendJobLog);
								builder_talendJobLog.custom("stacktrace", sw_talendJobLog.getBuffer().substring(0,
										java.lang.Math.min(sw_talendJobLog.getBuffer().length(), 512)));
							}
						}

						if (jcm.extra_info != null) {
							builder_talendJobLog.connectorId(jcm.component_id).custom("extra_info", jcm.extra_info);
						}

						log_context_talendJobLog = builder_talendJobLog
								.connectorType(jcm.component_id.substring(0, jcm.component_id.lastIndexOf('_')))
								.connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label == null ? jcm.component_id : jcm.component_label)
								.build();

						auditLogger_talendJobLog.exception(log_context_talendJobLog);
					}

				}

				/**
				 * [talendJobLog begin ] stop
				 */

				/**
				 * [talendJobLog main ] start
				 */

				s(currentComponent = "talendJobLog");

				tos_count_talendJobLog++;

				/**
				 * [talendJobLog main ] stop
				 */

				/**
				 * [talendJobLog process_data_begin ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog process_data_begin ] stop
				 */

				/**
				 * [talendJobLog process_data_end ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog process_data_end ] stop
				 */

				/**
				 * [talendJobLog end ] start
				 */

				s(currentComponent = "talendJobLog");

				ok_Hash.put("talendJobLog", true);
				end_Hash.put("talendJobLog", System.currentTimeMillis());

				/**
				 * [talendJobLog end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendJobLog finally ] start
				 */

				s(currentComponent = "talendJobLog");

				/**
				 * [talendJobLog finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	private final static java.util.Properties jobInfo = new java.util.Properties();
	private final static java.util.Map<String, String> mdcInfo = new java.util.HashMap<>();
	private final static java.util.concurrent.atomic.AtomicLong subJobPidCounter = new java.util.concurrent.atomic.AtomicLong();

	public static void main(String[] args) {
		final File_Read_job File_Read_jobClass = new File_Read_job();

		int exitCode = File_Read_jobClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'File_Read_job' - Done.");
		}

		System.exit(exitCode);
	}

	private void getjobInfo() {
		final String TEMPLATE_PATH = "src/main/templates/jobInfo_template.properties";
		final String BUILD_PATH = "../jobInfo.properties";
		final String path = this.getClass().getResource("").getPath();
		if (path.lastIndexOf("target") > 0) {
			final java.io.File templateFile = new java.io.File(
					path.substring(0, path.lastIndexOf("target")).concat(TEMPLATE_PATH));
			if (templateFile.exists()) {
				readJobInfo(templateFile);
				return;
			}
		}
		readJobInfo(new java.io.File(BUILD_PATH));
	}

	private void readJobInfo(java.io.File jobInfoFile) {

		if (jobInfoFile.exists()) {
			try (java.io.InputStream is = new java.io.FileInputStream(jobInfoFile)) {
				jobInfo.load(is);
			} catch (IOException e) {

				log.debug("Read jobInfo.properties file fail: " + e.getMessage());

			}
		}
		log.info(String.format("Project name: %s\tJob name: %s\tGIT Commit ID: %s\tTalend Version: %s", projectName,
				jobName, jobInfo.getProperty("gitCommitId"), "8.0.1.20241016_1624-patch"));

	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}

		final boolean enableCBP = false;
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (!inOSGi) {
			if (org.talend.metrics.CBPClient.getInstanceForCurrentVM() == null) {
				try {
					org.talend.metrics.CBPClient.startListenIfNotStarted(enableCBP, true);
				} catch (java.lang.Exception e) {
					errorCode = 1;
					status = "failure";
					e.printStackTrace();
					return 1;
				}
			}
		}

		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (!"".equals(log4jLevel)) {

			if ("trace".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.OFF);
			}
			org.apache.logging.log4j.core.config.Configurator
					.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());

		}

		getjobInfo();
		log.info("TalendJob: 'File_Read_job' - Start.");

		java.util.Set<Object> jobInfoKeys = jobInfo.keySet();
		for (Object jobInfoKey : jobInfoKeys) {
			org.slf4j.MDC.put("_" + jobInfoKey.toString(), jobInfo.get(jobInfoKey).toString());
		}
		org.slf4j.MDC.put("_pid", pid);
		org.slf4j.MDC.put("_rootPid", rootPid);
		org.slf4j.MDC.put("_fatherPid", fatherPid);
		org.slf4j.MDC.put("_projectName", projectName);
		org.slf4j.MDC.put("_startTimestamp", java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC)
				.format(java.time.format.DateTimeFormatter.ISO_INSTANT));
		org.slf4j.MDC.put("_jobRepositoryId", "_YUeo0J13Ee-uTKnoCo_rNA");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-11-08T03:06:42.931927800Z");

		java.lang.management.RuntimeMXBean mx = java.lang.management.ManagementFactory.getRuntimeMXBean();
		String[] mxNameTable = mx.getName().split("@"); //$NON-NLS-1$
		if (mxNameTable.length == 2) {
			org.slf4j.MDC.put("_systemPid", mxNameTable[0]);
		} else {
			org.slf4j.MDC.put("_systemPid", String.valueOf(java.lang.Thread.currentThread().getId()));
		}

		if (enableLogStash) {
			java.util.Properties properties_talendJobLog = new java.util.Properties();
			properties_talendJobLog.setProperty("root.logger", "audit");
			properties_talendJobLog.setProperty("encoding", "UTF-8");
			properties_talendJobLog.setProperty("application.name", "Talend Studio");
			properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
			properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
			properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
			properties_talendJobLog.setProperty("log.appender", "file");
			properties_talendJobLog.setProperty("appender.file.path", "audit.json");
			properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
			properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
			properties_talendJobLog.setProperty("host", "false");

			System.getProperties().stringPropertyNames().stream().filter(it -> it.startsWith("audit.logger."))
					.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()),
							System.getProperty(key)));

			org.apache.logging.log4j.core.config.Configurator
					.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);

			auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
					.createJobAuditLogger(properties_talendJobLog);
		}

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		org.slf4j.MDC.put("_pid", pid);

		if (rootPid == null) {
			rootPid = pid;
		}

		org.slf4j.MDC.put("_rootPid", rootPid);

		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}
		org.slf4j.MDC.put("_fatherPid", fatherPid);

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}

		try {
			java.util.Dictionary<String, Object> jobProperties = null;
			if (inOSGi) {
				jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

				if (jobProperties != null && jobProperties.get("context") != null) {
					contextStr = (String) jobProperties.get("context");
				}

				if (jobProperties != null && jobProperties.get("taskExecutionId") != null) {
					taskExecutionId = (String) jobProperties.get("taskExecutionId");
				}

				// extract ids from parent route
				if (null == taskExecutionId || taskExecutionId.isEmpty()) {
					for (String arg : args) {
						if (arg.startsWith("--context_param")
								&& (arg.contains("taskExecutionId") || arg.contains("jobExecutionId"))) {

							String keyValue = arg.replace("--context_param", "");
							String[] parts = keyValue.split("=");
							String[] cleanParts = java.util.Arrays.stream(parts).filter(s -> !s.isEmpty())
									.toArray(String[]::new);
							if (cleanParts.length == 2) {
								String key = cleanParts[0];
								String value = cleanParts[1];
								if ("taskExecutionId".equals(key.trim()) && null != value) {
									taskExecutionId = value.trim();
								} else if ("jobExecutionId".equals(key.trim()) && null != value) {
									jobExecutionId = value.trim();
								}
							}
						}
					}
				}
			}

			// first load default key-value pairs from application.properties
			if (isStandaloneMS) {
				context.putAll(this.getDefaultProperties());
			}
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = File_Read_job.class.getClassLoader()
					.getResourceAsStream("talend_project/file_read_job_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = File_Read_job.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						if (inOSGi && jobProperties != null) {
							java.util.Enumeration<String> keys = jobProperties.keys();
							while (keys.hasMoreElements()) {
								String propKey = keys.nextElement();
								if (defaultProps.containsKey(propKey)) {
									defaultProps.put(propKey, (String) jobProperties.get(propKey));
								}
							}
						}
						context = new ContextProperties(defaultProps);
					}
					if (isStandaloneMS) {
						// override context key-value pairs if provided using --context=contextName
						defaultProps.load(inContext);
						context.putAll(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}
			// override key-value pairs if provided via --config.location=file1.file2 OR
			// --config.additional-location=file1,file2
			if (isStandaloneMS) {
				context.putAll(this.getAdditionalProperties());
			}

			// override key-value pairs if provide via command line like
			// --key1=value1,--key2=value2
			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, ContextProperties.class, parametersToEncrypt));

		org.slf4j.MDC.put("_context", contextStr);
		log.info("TalendJob: 'File_Read_job' - Started.");
		java.util.Optional.ofNullable(org.slf4j.MDC.getCopyOfContextMap()).ifPresent(mdcInfo::putAll);

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		if (enableLogStash) {
			talendJobLog.addJobStartMessage();
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tFileInputExcel_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputExcel_1) {
			globalMap.put("tFileInputExcel_1_SUBPROCESS_STATE", -1);

			e_tFileInputExcel_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out
					.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : File_Read_job");
		}
		if (enableLogStash) {
			talendJobLog.addJobEndMessage(startTime, end, status);
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		if (!inOSGi) {
			if (org.talend.metrics.CBPClient.getInstanceForCurrentVM() != null) {
				s("none");
				org.talend.metrics.CBPClient.getInstanceForCurrentVM().sendData();
			}
		}

		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");
		resumeUtil.flush();

		org.slf4j.MDC.remove("_subJobName");
		org.slf4j.MDC.remove("_subJobPid");
		org.slf4j.MDC.remove("_systemPid");
		log.info("TalendJob: 'File_Read_job' - Finished - status: " + status + " returnCode: " + returnCode);

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {
		// add CBP code for OSGI Executions
		if (null != taskExecutionId && !taskExecutionId.isEmpty()) {
			try {
				org.talend.metrics.DataReadTracker.setExecutionId(taskExecutionId, jobExecutionId, false);
				org.talend.metrics.DataReadTracker.sealCounter();
				org.talend.metrics.DataReadTracker.reset();
			} catch (Exception | NoClassDefFoundError e) {
				// ignore
			}
		}

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--context_file")) {
			String keyValue = arg.substring(15);
			String filePath = new String(java.util.Base64.getDecoder().decode(keyValue));
			java.nio.file.Path contextFile = java.nio.file.Paths.get(filePath);
			try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(contextFile)) {
				String line;
				while ((line = reader.readLine()) != null) {
					int index = -1;
					if ((index = line.indexOf('=')) > -1) {
						if (line.startsWith("--context_param")) {
							if ("id_Password".equals(context_param.getContextType(line.substring(16, index)))) {
								context_param.put(line.substring(16, index),
										routines.system.PasswordEncryptUtil.decryptPassword(line.substring(index + 1)));
							} else {
								context_param.put(line.substring(16, index), line.substring(index + 1));
							}
						} else {// --context_type
							context_param.setContextType(line.substring(15, index), line.substring(index + 1));
						}
					}
				}
			} catch (java.io.IOException e) {
				System.err.println("Could not load the context file: " + filePath);
				e.printStackTrace();
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 224223 characters generated by Talend Cloud Data Management Platform on the 8
 * November 2024 at 8:36:42 am IST
 ************************************************************************************************/