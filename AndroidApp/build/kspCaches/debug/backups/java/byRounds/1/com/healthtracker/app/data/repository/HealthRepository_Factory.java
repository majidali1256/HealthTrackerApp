package com.healthtracker.app.data.repository;

import com.healthtracker.app.data.local.dao.ActivityRecordDao;
import com.healthtracker.app.data.local.dao.DocumentDao;
import com.healthtracker.app.data.local.dao.HealthLogDao;
import com.healthtracker.app.data.local.dao.MedicationDao;
import com.healthtracker.app.data.local.dao.SleepRecordDao;
import com.healthtracker.app.data.local.dao.UserDao;
import com.healthtracker.app.data.local.dao.VitalRecordDao;
import com.healthtracker.app.data.remote.FirebaseService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class HealthRepository_Factory implements Factory<HealthRepository> {
  private final Provider<UserDao> userDaoProvider;

  private final Provider<HealthLogDao> healthLogDaoProvider;

  private final Provider<VitalRecordDao> vitalRecordDaoProvider;

  private final Provider<SleepRecordDao> sleepRecordDaoProvider;

  private final Provider<ActivityRecordDao> activityRecordDaoProvider;

  private final Provider<MedicationDao> medicationDaoProvider;

  private final Provider<DocumentDao> documentDaoProvider;

  private final Provider<FirebaseService> firebaseServiceProvider;

  public HealthRepository_Factory(Provider<UserDao> userDaoProvider,
      Provider<HealthLogDao> healthLogDaoProvider, Provider<VitalRecordDao> vitalRecordDaoProvider,
      Provider<SleepRecordDao> sleepRecordDaoProvider,
      Provider<ActivityRecordDao> activityRecordDaoProvider,
      Provider<MedicationDao> medicationDaoProvider, Provider<DocumentDao> documentDaoProvider,
      Provider<FirebaseService> firebaseServiceProvider) {
    this.userDaoProvider = userDaoProvider;
    this.healthLogDaoProvider = healthLogDaoProvider;
    this.vitalRecordDaoProvider = vitalRecordDaoProvider;
    this.sleepRecordDaoProvider = sleepRecordDaoProvider;
    this.activityRecordDaoProvider = activityRecordDaoProvider;
    this.medicationDaoProvider = medicationDaoProvider;
    this.documentDaoProvider = documentDaoProvider;
    this.firebaseServiceProvider = firebaseServiceProvider;
  }

  @Override
  public HealthRepository get() {
    return newInstance(userDaoProvider.get(), healthLogDaoProvider.get(), vitalRecordDaoProvider.get(), sleepRecordDaoProvider.get(), activityRecordDaoProvider.get(), medicationDaoProvider.get(), documentDaoProvider.get(), firebaseServiceProvider.get());
  }

  public static HealthRepository_Factory create(Provider<UserDao> userDaoProvider,
      Provider<HealthLogDao> healthLogDaoProvider, Provider<VitalRecordDao> vitalRecordDaoProvider,
      Provider<SleepRecordDao> sleepRecordDaoProvider,
      Provider<ActivityRecordDao> activityRecordDaoProvider,
      Provider<MedicationDao> medicationDaoProvider, Provider<DocumentDao> documentDaoProvider,
      Provider<FirebaseService> firebaseServiceProvider) {
    return new HealthRepository_Factory(userDaoProvider, healthLogDaoProvider, vitalRecordDaoProvider, sleepRecordDaoProvider, activityRecordDaoProvider, medicationDaoProvider, documentDaoProvider, firebaseServiceProvider);
  }

  public static HealthRepository newInstance(UserDao userDao, HealthLogDao healthLogDao,
      VitalRecordDao vitalRecordDao, SleepRecordDao sleepRecordDao,
      ActivityRecordDao activityRecordDao, MedicationDao medicationDao, DocumentDao documentDao,
      FirebaseService firebaseService) {
    return new HealthRepository(userDao, healthLogDao, vitalRecordDao, sleepRecordDao, activityRecordDao, medicationDao, documentDao, firebaseService);
  }
}
