package com.healthtracker.app.ui.medications;

import com.google.firebase.auth.FirebaseAuth;
import com.healthtracker.app.data.repository.HealthRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AddMedicationBottomSheet_MembersInjector implements MembersInjector<AddMedicationBottomSheet> {
  private final Provider<HealthRepository> healthRepositoryProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public AddMedicationBottomSheet_MembersInjector(
      Provider<HealthRepository> healthRepositoryProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.healthRepositoryProvider = healthRepositoryProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<AddMedicationBottomSheet> create(
      Provider<HealthRepository> healthRepositoryProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new AddMedicationBottomSheet_MembersInjector(healthRepositoryProvider, firebaseAuthProvider);
  }

  @Override
  public void injectMembers(AddMedicationBottomSheet instance) {
    injectHealthRepository(instance, healthRepositoryProvider.get());
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.medications.AddMedicationBottomSheet.healthRepository")
  public static void injectHealthRepository(AddMedicationBottomSheet instance,
      HealthRepository healthRepository) {
    instance.healthRepository = healthRepository;
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.medications.AddMedicationBottomSheet.firebaseAuth")
  public static void injectFirebaseAuth(AddMedicationBottomSheet instance,
      FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
