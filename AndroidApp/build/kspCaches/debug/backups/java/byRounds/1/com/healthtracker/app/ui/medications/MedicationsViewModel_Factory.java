package com.healthtracker.app.ui.medications;

import com.healthtracker.app.data.repository.HealthRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MedicationsViewModel_Factory implements Factory<MedicationsViewModel> {
  private final Provider<HealthRepository> healthRepositoryProvider;

  public MedicationsViewModel_Factory(Provider<HealthRepository> healthRepositoryProvider) {
    this.healthRepositoryProvider = healthRepositoryProvider;
  }

  @Override
  public MedicationsViewModel get() {
    return newInstance(healthRepositoryProvider.get());
  }

  public static MedicationsViewModel_Factory create(
      Provider<HealthRepository> healthRepositoryProvider) {
    return new MedicationsViewModel_Factory(healthRepositoryProvider);
  }

  public static MedicationsViewModel newInstance(HealthRepository healthRepository) {
    return new MedicationsViewModel(healthRepository);
  }
}
