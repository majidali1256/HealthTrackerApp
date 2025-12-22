package com.healthtracker.app.di;

import com.healthtracker.app.data.local.AppDatabase;
import com.healthtracker.app.data.local.dao.HealthLogDao;
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
public final class DatabaseModule_ProvideHealthLogDaoFactory implements Factory<HealthLogDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideHealthLogDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public HealthLogDao get() {
    return provideHealthLogDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideHealthLogDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideHealthLogDaoFactory(databaseProvider);
  }

  public static HealthLogDao provideHealthLogDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideHealthLogDao(database));
  }
}
