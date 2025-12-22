package com.healthtracker.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.healthtracker.app.data.local.dao.ActivityRecordDao;
import com.healthtracker.app.data.local.dao.ActivityRecordDao_Impl;
import com.healthtracker.app.data.local.dao.DocumentDao;
import com.healthtracker.app.data.local.dao.DocumentDao_Impl;
import com.healthtracker.app.data.local.dao.HealthLogDao;
import com.healthtracker.app.data.local.dao.HealthLogDao_Impl;
import com.healthtracker.app.data.local.dao.MedicationDao;
import com.healthtracker.app.data.local.dao.MedicationDao_Impl;
import com.healthtracker.app.data.local.dao.SleepRecordDao;
import com.healthtracker.app.data.local.dao.SleepRecordDao_Impl;
import com.healthtracker.app.data.local.dao.UserDao;
import com.healthtracker.app.data.local.dao.UserDao_Impl;
import com.healthtracker.app.data.local.dao.VitalRecordDao;
import com.healthtracker.app.data.local.dao.VitalRecordDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile HealthLogDao _healthLogDao;

  private volatile VitalRecordDao _vitalRecordDao;

  private volatile SleepRecordDao _sleepRecordDao;

  private volatile ActivityRecordDao _activityRecordDao;

  private volatile MedicationDao _medicationDao;

  private volatile DocumentDao _documentDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `email` TEXT NOT NULL, `displayName` TEXT NOT NULL, `profilePictureUrl` TEXT, `dateOfBirth` INTEGER, `gender` TEXT, `heightCm` REAL, `weightKg` REAL, `bloodType` TEXT, `medicalId` TEXT, `allergies` TEXT, `medicalConditions` TEXT, `organDonor` INTEGER NOT NULL, `emergencyContacts` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `health_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `type` TEXT NOT NULL, `value` REAL NOT NULL, `unit` TEXT, `title` TEXT, `description` TEXT, `barcodeData` TEXT, `durationMinutes` INTEGER, `timestamp` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_health_logs_userId` ON `health_logs` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_health_logs_timestamp` ON `health_logs` (`timestamp`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_health_logs_type` ON `health_logs` (`type`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vital_records` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `heartRate` INTEGER, `spO2` INTEGER, `systolicBp` INTEGER, `diastolicBp` INTEGER, `respiratoryRate` INTEGER, `temperatureCelsius` REAL, `bloodGlucose` REAL, `source` TEXT NOT NULL, `notes` TEXT, `timestamp` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_vital_records_userId` ON `vital_records` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_vital_records_timestamp` ON `vital_records` (`timestamp`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sleep_records` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `sleepStart` INTEGER NOT NULL, `sleepEnd` INTEGER NOT NULL, `totalMinutes` INTEGER NOT NULL, `deepSleepMinutes` INTEGER NOT NULL, `lightSleepMinutes` INTEGER NOT NULL, `remSleepMinutes` INTEGER NOT NULL, `awakeMinutes` INTEGER NOT NULL, `sleepScore` INTEGER, `timesAwakened` INTEGER NOT NULL, `source` TEXT NOT NULL, `notes` TEXT, `isSynced` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_sleep_records_userId` ON `sleep_records` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_sleep_records_sleepStart` ON `sleep_records` (`sleepStart`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `activity_records` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `date` INTEGER NOT NULL, `steps` INTEGER NOT NULL, `stepsGoal` INTEGER NOT NULL, `distanceMeters` REAL NOT NULL, `caloriesBurned` INTEGER NOT NULL, `caloriesGoal` INTEGER NOT NULL, `activeMinutes` INTEGER NOT NULL, `activeMinutesGoal` INTEGER NOT NULL, `floorsClimbed` INTEGER NOT NULL, `source` TEXT NOT NULL, `lastUpdated` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_activity_records_userId` ON `activity_records` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_activity_records_date` ON `activity_records` (`date`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `medications` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `name` TEXT NOT NULL, `dosage` TEXT NOT NULL, `dosageUnit` TEXT NOT NULL, `frequency` TEXT NOT NULL, `scheduleTimes` TEXT NOT NULL, `instructions` TEXT, `takeWithFood` INTEGER NOT NULL, `startDate` INTEGER NOT NULL, `endDate` INTEGER, `currentQuantity` INTEGER NOT NULL, `refillAt` INTEGER NOT NULL, `refillDate` INTEGER, `isActive` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_medications_userId` ON `medications` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `documents` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `category` TEXT NOT NULL, `fileName` TEXT NOT NULL, `filePath` TEXT NOT NULL, `fileSize` INTEGER NOT NULL, `mimeType` TEXT NOT NULL, `isEncrypted` INTEGER NOT NULL, `encryptionKeyAlias` TEXT, `cloudStorageUrl` TEXT, `documentDate` INTEGER, `providerName` TEXT, `tags` TEXT, `uploadedAt` INTEGER NOT NULL, `isSynced` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_documents_userId` ON `documents` (`userId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_documents_category` ON `documents` (`category`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4579ea2ed04a9c9ff9d7afec78f91f2c')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `health_logs`");
        db.execSQL("DROP TABLE IF EXISTS `vital_records`");
        db.execSQL("DROP TABLE IF EXISTS `sleep_records`");
        db.execSQL("DROP TABLE IF EXISTS `activity_records`");
        db.execSQL("DROP TABLE IF EXISTS `medications`");
        db.execSQL("DROP TABLE IF EXISTS `documents`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(17);
        _columnsUsers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("displayName", new TableInfo.Column("displayName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("profilePictureUrl", new TableInfo.Column("profilePictureUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("dateOfBirth", new TableInfo.Column("dateOfBirth", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("gender", new TableInfo.Column("gender", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("heightCm", new TableInfo.Column("heightCm", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("weightKg", new TableInfo.Column("weightKg", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("bloodType", new TableInfo.Column("bloodType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("medicalId", new TableInfo.Column("medicalId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("allergies", new TableInfo.Column("allergies", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("medicalConditions", new TableInfo.Column("medicalConditions", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("organDonor", new TableInfo.Column("organDonor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("emergencyContacts", new TableInfo.Column("emergencyContacts", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.healthtracker.app.data.local.entities.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsHealthLogs = new HashMap<String, TableInfo.Column>(11);
        _columnsHealthLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthLogs.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthLogs.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthLogs.put("value", new TableInfo.Column("value", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthLogs.put("unit", new TableInfo.Column("unit", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthLogs.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthLogs.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthLogs.put("barcodeData", new TableInfo.Column("barcodeData", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthLogs.put("durationMinutes", new TableInfo.Column("durationMinutes", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHealthLogs.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHealthLogs = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysHealthLogs.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesHealthLogs = new HashSet<TableInfo.Index>(3);
        _indicesHealthLogs.add(new TableInfo.Index("index_health_logs_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesHealthLogs.add(new TableInfo.Index("index_health_logs_timestamp", false, Arrays.asList("timestamp"), Arrays.asList("ASC")));
        _indicesHealthLogs.add(new TableInfo.Index("index_health_logs_type", false, Arrays.asList("type"), Arrays.asList("ASC")));
        final TableInfo _infoHealthLogs = new TableInfo("health_logs", _columnsHealthLogs, _foreignKeysHealthLogs, _indicesHealthLogs);
        final TableInfo _existingHealthLogs = TableInfo.read(db, "health_logs");
        if (!_infoHealthLogs.equals(_existingHealthLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "health_logs(com.healthtracker.app.data.local.entities.HealthLog).\n"
                  + " Expected:\n" + _infoHealthLogs + "\n"
                  + " Found:\n" + _existingHealthLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsVitalRecords = new HashMap<String, TableInfo.Column>(13);
        _columnsVitalRecords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("heartRate", new TableInfo.Column("heartRate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("spO2", new TableInfo.Column("spO2", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("systolicBp", new TableInfo.Column("systolicBp", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("diastolicBp", new TableInfo.Column("diastolicBp", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("respiratoryRate", new TableInfo.Column("respiratoryRate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("temperatureCelsius", new TableInfo.Column("temperatureCelsius", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("bloodGlucose", new TableInfo.Column("bloodGlucose", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitalRecords.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVitalRecords = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysVitalRecords.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesVitalRecords = new HashSet<TableInfo.Index>(2);
        _indicesVitalRecords.add(new TableInfo.Index("index_vital_records_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesVitalRecords.add(new TableInfo.Index("index_vital_records_timestamp", false, Arrays.asList("timestamp"), Arrays.asList("ASC")));
        final TableInfo _infoVitalRecords = new TableInfo("vital_records", _columnsVitalRecords, _foreignKeysVitalRecords, _indicesVitalRecords);
        final TableInfo _existingVitalRecords = TableInfo.read(db, "vital_records");
        if (!_infoVitalRecords.equals(_existingVitalRecords)) {
          return new RoomOpenHelper.ValidationResult(false, "vital_records(com.healthtracker.app.data.local.entities.VitalRecord).\n"
                  + " Expected:\n" + _infoVitalRecords + "\n"
                  + " Found:\n" + _existingVitalRecords);
        }
        final HashMap<String, TableInfo.Column> _columnsSleepRecords = new HashMap<String, TableInfo.Column>(14);
        _columnsSleepRecords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("sleepStart", new TableInfo.Column("sleepStart", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("sleepEnd", new TableInfo.Column("sleepEnd", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("totalMinutes", new TableInfo.Column("totalMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("deepSleepMinutes", new TableInfo.Column("deepSleepMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("lightSleepMinutes", new TableInfo.Column("lightSleepMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("remSleepMinutes", new TableInfo.Column("remSleepMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("awakeMinutes", new TableInfo.Column("awakeMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("sleepScore", new TableInfo.Column("sleepScore", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("timesAwakened", new TableInfo.Column("timesAwakened", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepRecords.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSleepRecords = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysSleepRecords.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesSleepRecords = new HashSet<TableInfo.Index>(2);
        _indicesSleepRecords.add(new TableInfo.Index("index_sleep_records_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesSleepRecords.add(new TableInfo.Index("index_sleep_records_sleepStart", false, Arrays.asList("sleepStart"), Arrays.asList("ASC")));
        final TableInfo _infoSleepRecords = new TableInfo("sleep_records", _columnsSleepRecords, _foreignKeysSleepRecords, _indicesSleepRecords);
        final TableInfo _existingSleepRecords = TableInfo.read(db, "sleep_records");
        if (!_infoSleepRecords.equals(_existingSleepRecords)) {
          return new RoomOpenHelper.ValidationResult(false, "sleep_records(com.healthtracker.app.data.local.entities.SleepRecord).\n"
                  + " Expected:\n" + _infoSleepRecords + "\n"
                  + " Found:\n" + _existingSleepRecords);
        }
        final HashMap<String, TableInfo.Column> _columnsActivityRecords = new HashMap<String, TableInfo.Column>(14);
        _columnsActivityRecords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("steps", new TableInfo.Column("steps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("stepsGoal", new TableInfo.Column("stepsGoal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("distanceMeters", new TableInfo.Column("distanceMeters", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("caloriesBurned", new TableInfo.Column("caloriesBurned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("caloriesGoal", new TableInfo.Column("caloriesGoal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("activeMinutes", new TableInfo.Column("activeMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("activeMinutesGoal", new TableInfo.Column("activeMinutesGoal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("floorsClimbed", new TableInfo.Column("floorsClimbed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("lastUpdated", new TableInfo.Column("lastUpdated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityRecords.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysActivityRecords = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysActivityRecords.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesActivityRecords = new HashSet<TableInfo.Index>(2);
        _indicesActivityRecords.add(new TableInfo.Index("index_activity_records_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesActivityRecords.add(new TableInfo.Index("index_activity_records_date", false, Arrays.asList("date"), Arrays.asList("ASC")));
        final TableInfo _infoActivityRecords = new TableInfo("activity_records", _columnsActivityRecords, _foreignKeysActivityRecords, _indicesActivityRecords);
        final TableInfo _existingActivityRecords = TableInfo.read(db, "activity_records");
        if (!_infoActivityRecords.equals(_existingActivityRecords)) {
          return new RoomOpenHelper.ValidationResult(false, "activity_records(com.healthtracker.app.data.local.entities.ActivityRecord).\n"
                  + " Expected:\n" + _infoActivityRecords + "\n"
                  + " Found:\n" + _existingActivityRecords);
        }
        final HashMap<String, TableInfo.Column> _columnsMedications = new HashMap<String, TableInfo.Column>(18);
        _columnsMedications.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("dosage", new TableInfo.Column("dosage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("dosageUnit", new TableInfo.Column("dosageUnit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("frequency", new TableInfo.Column("frequency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("scheduleTimes", new TableInfo.Column("scheduleTimes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("instructions", new TableInfo.Column("instructions", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("takeWithFood", new TableInfo.Column("takeWithFood", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("endDate", new TableInfo.Column("endDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("currentQuantity", new TableInfo.Column("currentQuantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("refillAt", new TableInfo.Column("refillAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("refillDate", new TableInfo.Column("refillDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedications = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMedications.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMedications = new HashSet<TableInfo.Index>(1);
        _indicesMedications.add(new TableInfo.Index("index_medications_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoMedications = new TableInfo("medications", _columnsMedications, _foreignKeysMedications, _indicesMedications);
        final TableInfo _existingMedications = TableInfo.read(db, "medications");
        if (!_infoMedications.equals(_existingMedications)) {
          return new RoomOpenHelper.ValidationResult(false, "medications(com.healthtracker.app.data.local.entities.Medication).\n"
                  + " Expected:\n" + _infoMedications + "\n"
                  + " Found:\n" + _existingMedications);
        }
        final HashMap<String, TableInfo.Column> _columnsDocuments = new HashMap<String, TableInfo.Column>(17);
        _columnsDocuments.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("fileName", new TableInfo.Column("fileName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("filePath", new TableInfo.Column("filePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("fileSize", new TableInfo.Column("fileSize", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("mimeType", new TableInfo.Column("mimeType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("isEncrypted", new TableInfo.Column("isEncrypted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("encryptionKeyAlias", new TableInfo.Column("encryptionKeyAlias", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("cloudStorageUrl", new TableInfo.Column("cloudStorageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("documentDate", new TableInfo.Column("documentDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("providerName", new TableInfo.Column("providerName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("tags", new TableInfo.Column("tags", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("uploadedAt", new TableInfo.Column("uploadedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDocuments.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDocuments = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysDocuments.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesDocuments = new HashSet<TableInfo.Index>(2);
        _indicesDocuments.add(new TableInfo.Index("index_documents_userId", false, Arrays.asList("userId"), Arrays.asList("ASC")));
        _indicesDocuments.add(new TableInfo.Index("index_documents_category", false, Arrays.asList("category"), Arrays.asList("ASC")));
        final TableInfo _infoDocuments = new TableInfo("documents", _columnsDocuments, _foreignKeysDocuments, _indicesDocuments);
        final TableInfo _existingDocuments = TableInfo.read(db, "documents");
        if (!_infoDocuments.equals(_existingDocuments)) {
          return new RoomOpenHelper.ValidationResult(false, "documents(com.healthtracker.app.data.local.entities.Document).\n"
                  + " Expected:\n" + _infoDocuments + "\n"
                  + " Found:\n" + _existingDocuments);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "4579ea2ed04a9c9ff9d7afec78f91f2c", "a453715dd116f472c6b7f7b0936cc2aa");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","health_logs","vital_records","sleep_records","activity_records","medications","documents");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `health_logs`");
      _db.execSQL("DELETE FROM `vital_records`");
      _db.execSQL("DELETE FROM `sleep_records`");
      _db.execSQL("DELETE FROM `activity_records`");
      _db.execSQL("DELETE FROM `medications`");
      _db.execSQL("DELETE FROM `documents`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(HealthLogDao.class, HealthLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VitalRecordDao.class, VitalRecordDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SleepRecordDao.class, SleepRecordDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ActivityRecordDao.class, ActivityRecordDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MedicationDao.class, MedicationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DocumentDao.class, DocumentDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public HealthLogDao healthLogDao() {
    if (_healthLogDao != null) {
      return _healthLogDao;
    } else {
      synchronized(this) {
        if(_healthLogDao == null) {
          _healthLogDao = new HealthLogDao_Impl(this);
        }
        return _healthLogDao;
      }
    }
  }

  @Override
  public VitalRecordDao vitalRecordDao() {
    if (_vitalRecordDao != null) {
      return _vitalRecordDao;
    } else {
      synchronized(this) {
        if(_vitalRecordDao == null) {
          _vitalRecordDao = new VitalRecordDao_Impl(this);
        }
        return _vitalRecordDao;
      }
    }
  }

  @Override
  public SleepRecordDao sleepRecordDao() {
    if (_sleepRecordDao != null) {
      return _sleepRecordDao;
    } else {
      synchronized(this) {
        if(_sleepRecordDao == null) {
          _sleepRecordDao = new SleepRecordDao_Impl(this);
        }
        return _sleepRecordDao;
      }
    }
  }

  @Override
  public ActivityRecordDao activityRecordDao() {
    if (_activityRecordDao != null) {
      return _activityRecordDao;
    } else {
      synchronized(this) {
        if(_activityRecordDao == null) {
          _activityRecordDao = new ActivityRecordDao_Impl(this);
        }
        return _activityRecordDao;
      }
    }
  }

  @Override
  public MedicationDao medicationDao() {
    if (_medicationDao != null) {
      return _medicationDao;
    } else {
      synchronized(this) {
        if(_medicationDao == null) {
          _medicationDao = new MedicationDao_Impl(this);
        }
        return _medicationDao;
      }
    }
  }

  @Override
  public DocumentDao documentDao() {
    if (_documentDao != null) {
      return _documentDao;
    } else {
      synchronized(this) {
        if(_documentDao == null) {
          _documentDao = new DocumentDao_Impl(this);
        }
        return _documentDao;
      }
    }
  }
}
