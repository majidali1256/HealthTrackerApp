package com.healthtracker.app.di;

import com.healthtracker.app.data.local.AppDatabase;
import com.healthtracker.app.data.local.dao.VitalRecordDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideVitalRecordDaoFactory implements Factory<VitalRecordDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideVitalRecordDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VitalRecordDao get() {
    return provideVitalRecordDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideVitalRecordDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideVitalRecordDaoFactory(databaseProvider);
  }

  public static VitalRecordDao provideVitalRecordDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideVitalRecordDao(database));
  }
}
