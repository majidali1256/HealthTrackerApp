package com.healthtracker.app.ui.documents;

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
public final class DocumentVaultViewModel_Factory implements Factory<DocumentVaultViewModel> {
  private final Provider<HealthRepository> healthRepositoryProvider;

  public DocumentVaultViewModel_Factory(Provider<HealthRepository> healthRepositoryProvider) {
    this.healthRepositoryProvider = healthRepositoryProvider;
  }

  @Override
  public DocumentVaultViewModel get() {
    return newInstance(healthRepositoryProvider.get());
  }

  public static DocumentVaultViewModel_Factory create(
      Provider<HealthRepository> healthRepositoryProvider) {
    return new DocumentVaultViewModel_Factory(healthRepositoryProvider);
  }

  public static DocumentVaultViewModel newInstance(HealthRepository healthRepository) {
    return new DocumentVaultViewModel(healthRepository);
  }
}
