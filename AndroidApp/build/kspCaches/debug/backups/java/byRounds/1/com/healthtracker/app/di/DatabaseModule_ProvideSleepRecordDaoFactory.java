package com.healthtracker.app.di;

import com.healthtracker.app.data.local.AppDatabase;
import com.healthtracker.app.data.local.dao.SleepRecordDao;
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
public final class DatabaseModule_ProvideSleepRecordDaoFactory implements Factory<SleepRecordDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideSleepRecordDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SleepRecordDao get() {
    return provideSleepRecordDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideSleepRecordDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideSleepRecordDaoFactory(databaseProvider);
  }

  public static SleepRecordDao provideSleepRecordDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSleepRecordDao(database));
  }
}
